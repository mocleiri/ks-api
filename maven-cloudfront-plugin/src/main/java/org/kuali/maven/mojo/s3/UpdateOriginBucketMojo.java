package org.kuali.maven.mojo.s3;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * <p>
 * This mojo updates a bucket serving as an origin for a Cloud Front distribution. It generates an html directory listing for
 * each "directory" in the bucket and stores the html under a key in the bucket such that a regular http request for a directory
 * returns the html instead of the XML for "object does not exist" Amazon would normally return. For example: The url
 * "http://www.mybucket.com/foo/bar" returns an html page containing a listing of all the files and directories under "foo/bar"
 * in the bucket.
 * </p>
 * 
 * <p>
 * If a directory contains an object with a key that is the same as the default object, the plugin copies the object to a key
 * representing the directory structure. For example, the url "http://www.mybucket.com/foo/bar/index.html" represents an object
 * in an S3 bucket under the key "foo/bar/index.html". This plugin will copy the object from the key "foo/bar/index.html" to the
 * key "foo/bar/". This causes the url "http://www.mybucket.com/foo/bar/" to return the same content as the url
 * "http://www.mybucket.com/foo/bar/index.html"
 * </p>
 * 
 * <p>
 * It also generates an html directory listing at the root of the bucket hierarchy and places that html into the bucket as the
 * default object, unless a default object already exists.
 * </p>
 * 
 * @goal updateoriginbucket
 */
public class UpdateOriginBucketMojo extends S3Mojo {
    private static final String S3_INDEX_METADATA_KEY = "maven-cloudfront-plugin-index";
    private static final String S3_INDEX_CONTENT_TYPE = "text/html";
    NumberFormat nf = getNumberFormatInstance();
    SimpleDateFormat dateFormatter;
    HtmlUtils html = new HtmlUtils();

    /**
     * If true, "foo/bar/index.html" will get copied to "foo/bar/"
     * 
     * @parameter expression="${copyDefaultObjectWithDelimiter}" default-value="true"
     */
    private boolean copyDefaultObjectWithDelimiter;

    /**
     * If true, "foo/bar/index.html" will get copied to "foo/bar". This is defaulted to false because the relative pathing in
     * the html generated by the maven-site-plugin for its css and images does not render correctly from a url without the
     * trailing slash.
     * 
     * @parameter expression="${copyDefaultObjectWithoutDelimiter}" default-value="false"
     */
    private boolean copyDefaultObjectWithoutDelimiter;

    /**
     * The stylesheet to use for the directory listing
     * 
     * @parameter expression="${css}" default-value="http://s3browse.ks.kuali.org/css/style.css"
     */
    private String css;

    /**
     * Image representing a file
     * 
     * @parameter expression="${fileImage}" default-value="http://s3browse.ks.kuali.org/images/page_white.png"
     */
    private String fileImage;

    /**
     * Image representing a directory
     * 
     * @parameter expression="${directoryImage}" default-value="http://s3browse.ks.kuali.org/images/folder.png"
     */
    private String directoryImage;

    /**
     * When displaying the last modified timestamp, use this timezone
     * 
     * @parameter expression="${timezone}" default-value="GMT"
     */
    private String timezone;

    /**
     * When displaying the last modified timestamp use this format
     * 
     * @parameter expression="${dateFormat}" default-value="EEE, dd MMM yyyy HH:mm:ss z"
     */
    private String dateFormat;

    /**
     * The key containing the default object for the Cloud Front distribution. If this default object already exists, the plugin
     * will not modify it. If there is no object in the bucket under this key, this plugin will generate an html directory
     * listing and place the directory listing into the bucket under this key.
     * 
     * @parameter expression="${defaultObject}" default-value="index.html";
     */
    private String defaultObject;

    @Override
    public void executeMojo() throws MojoExecutionException, MojoFailureException {
        dateFormatter = getSimpleDateFormatInstance();
        try {
            updateCredentials();
            validateCredentials();
            AWSCredentials credentials = getCredentials();
            AmazonS3Client client = new AmazonS3Client(credentials);
            recurse(client, getBucket(), getPrefix(), getDelimiter());
        } catch (Exception e) {
            throw new MojoExecutionException("Unexpected error: ", e);
        }
    }

    /**
     * Create a PutObjectRequest for some html generated by this mojo. The PutObjectRequest sets the content type to
     * S3_INDEX_CONTENT_TYPE, sets the ACL to PublicRead, and adds some custom metadata so we can positively identify it as an
     * object created by this plugin
     */
    protected PutObjectRequest getPutIndexObjectRequest(String html, String key) throws IOException {
        InputStream in = new ByteArrayInputStream(html.getBytes());
        ObjectMetadata om = new ObjectMetadata();
        String contentType = S3_INDEX_CONTENT_TYPE;
        om.setContentType(contentType);
        om.setContentLength(html.length());
        om.addUserMetadata(S3_INDEX_METADATA_KEY, "true");
        PutObjectRequest request = new PutObjectRequest(getBucket(), key, in, om);
        request.setCannedAcl(CannedAccessControlList.PublicRead);
        return request;
    }

    /**
     * Return a NumberFormat that does not using grouping and always displays one fraction digit. This is used to display the
     * size of S3 objects in kilobytes
     */
    protected NumberFormat getNumberFormatInstance() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        nf.setGroupingUsed(false);
        return nf;
    }

    /**
     * Return a SimpleDateFormat object initialized with the date format and timezone supplied to the mojo
     */
    protected SimpleDateFormat getSimpleDateFormatInstance() {
        SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
        sdf.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        return sdf;
    }

    /**
     * Trim the prefix off of the text we display for this object.<br>
     * Display "style.css" instead of "css/style.css"
     */
    protected String getShow(String key, String prefix) {
        if (prefix == null) {
            return key;
        }
        int index = prefix.length();
        String s = key.substring(index);
        return s;
    }

    /**
     * If prefix is "foo/" and delimiter is "/" return "/"<br>
     * If prefix is "foo/bar/" and delimiter is "/" return "foo/"
     */
    protected String getUpOneDirectoryDest(String prefix, String delimiter) {
        if (prefix.endsWith(delimiter)) {
            prefix = prefix.substring(0, prefix.length() - 1);
        }
        int pos = prefix.lastIndexOf(delimiter);
        if (pos == -1) {
            return delimiter;
        } else {
            return delimiter + prefix.substring(0, pos + 1);
        }
    }

    /**
     * Convert the ObjectListing into a List of String arrays. Each array in the list represents one row in the html table we
     * will be generating
     */
    protected List<String[]> getData(ObjectListing objectListing, String prefix, String delimiter) {
        DisplayRow upOneDirectory = getUpOneDirectoryDisplayRow(prefix, delimiter);
        List<DisplayRow> objectDisplayRows = getObjectDisplayRows(objectListing, prefix, delimiter);
        List<DisplayRow> directoryDisplayRows = getDirectoryDisplayRows(objectListing, prefix, delimiter);
        List<String[]> data = new ArrayList<String[]>();
        addDisplayRow(upOneDirectory, data);
        addDisplayRows(directoryDisplayRows, data);
        addDisplayRows(objectDisplayRows, data);
        return data;
    }

    /**
     * If the list is null or contains no entries, return true, false otherwise
     */
    protected boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    /**
     * If prefix is null, return the delimiter.<br>
     * If prefix is "/" and delimiter is "foo/bar" return "/foo/bar"<br>
     * If prefix is "/" and delimiter is "foo/bar/" return "/foo/bar"
     */
    protected String getDirectory(String prefix, String delimiter) {
        if (prefix == null) {
            return delimiter;
        }
        if (prefix.endsWith(delimiter)) {
            return delimiter + prefix.substring(0, prefix.length() - delimiter.length());
        } else {
            return delimiter + prefix;
        }
    }

    /**
     * Generate the full html page
     */
    protected String getHtml(List<String[]> data, String prefix, String delimiter) {
        String directory = getDirectory(prefix, delimiter);

        Tag html = new Tag("html");
        Tag title = new Tag("title");
        Tag head = new Tag("head");
        Tag body = new Tag("body");
        Tag div1 = new Tag("div", "title");
        Tag span1 = new Tag("span", null, "title");
        Tag div2 = new Tag("div", "data");
        Tag div3 = new Tag("div", "footer", "footer-left");
        Tag span2 = new Tag("span", null, "footer-text");

        StringBuffer sb = new StringBuffer();
        sb.append(this.html.openTag(html));
        sb.append(this.html.getTag(title, "Directory listing for " + directory));
        sb.append(this.html.openTag(head));
        sb.append(this.html.getIndentedContent("<link href=\"" + getCss() + "\" rel=\"stylesheet\" type=\"text/css\"/>\n"));
        sb.append(this.html.closeTag(head));
        sb.append(this.html.openTag(body));
        sb.append(this.html.openTag(div1));
        sb.append(this.html.getTag(span1, "Directory listing for " + directory));
        sb.append(this.html.closeTag(div1));
        sb.append(this.html.getIndentedContent("<hr>\n"));
        sb.append(this.html.openTag(div2));
        sb.append(getHtmlTable(data, getColumnDecorators()));
        sb.append(this.html.closeTag(div2));
        sb.append(this.html.getIndentedContent("<hr>\n"));
        sb.append(this.html.openTag(div3));
        sb.append(this.html.getTag(span2, getPoweredBy()));
        sb.append(this.html.closeTag(div3));
        sb.append(this.html.closeTag(body));
        sb.append(this.html.closeTag(html));
        return sb.toString();
    }

    /**
     * Show some text about this plugin
     */
    protected String getPoweredBy() {
        PluginDescriptor descriptor = (PluginDescriptor) this.getPluginContext().get("pluginDescriptor");
        if (descriptor == null) {
            // Maven 2.2.1 is returning a null descriptor
            return "Listing generated by the maven-cloudfront-plugin";
        } else {
            String name = descriptor.getArtifactId();
            String version = descriptor.getVersion();
            return "Listing generated by the " + name + " v" + version;
        }
    }

    /**
     * Generate the table representing a directory listing
     */
    protected String getHtmlTable(List<String[]> data, List<ColumnDecorator> columnDecorators) {
        if (isEmpty(data)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Tag table = new Tag("table", "mainTable");
        Tag thead = new Tag("thead");
        Tag tr = new Tag("tr");
        Tag tbody = new Tag("tbody");
        sb.append(html.openTag(table));
        sb.append(html.openTag(thead));
        sb.append(html.openTag(tr));
        sb.append(getTableHeaders(columnDecorators));
        sb.append(html.closeTag(tr));
        sb.append(html.closeTag(thead));
        sb.append(html.openTag(tbody));
        sb.append(getTableRows(data, columnDecorators));
        sb.append(html.closeTag(tbody));
        sb.append(html.closeTag(table));
        return sb.toString();
    }

    /**
     * Alternate the styling of each row
     */
    protected Tag getTableRowTag(int row) {
        if ((row % 2) == 0) {
            return new Tag("tr", "table-tr-odd");
        } else {
            return new Tag("tr");
        }
    }

    /**
     * Generate html representing the contents of one table cell
     */
    protected String getTableCell(String content, ColumnDecorator decorator) {
        Tag td = new Tag("td", decorator.getTableDataClass());
        return html.getTag(td, content);
    }

    /**
     * Generate an html table row for the String[]
     */
    protected String getTableRow(int row, String[] data, List<ColumnDecorator> columnDecorators) {
        StringBuffer sb = new StringBuffer();
        Tag tr = getTableRowTag(row);
        sb.append(html.openTag(tr));
        for (int i = 0; i < data.length; i++) {
            sb.append(getTableCell(data[i], columnDecorators.get(i)));
        }
        sb.append(html.closeTag(tr));
        return sb.toString();
    }

    /**
     * Generate a table row for each String[] in the list
     */
    protected String getTableRows(List<String[]> data, List<ColumnDecorator> columnDecorators) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.size(); i++) {
            sb.append(getTableRow(i, data.get(i), columnDecorators));
        }
        return sb.toString();
    }

    /**
     * Generate the html for the th tags from a list of ColumnDecorator objects
     */
    protected String getTableHeaders(List<ColumnDecorator> columnDecorators) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < columnDecorators.size(); i++) {
            ColumnDecorator decorator = columnDecorators.get(i);
            Tag th = new Tag("th", decorator.getTableDataClass());
            sb.append(html.openTag(th));
            sb.append(html.getTag(new Tag("span", decorator.getSpanClass()), decorator.getColumnTitle()));
            sb.append(html.closeTag(th));
        }
        return sb.toString();
    }

    /**
     * Decorators for the columns in the table
     */
    protected List<ColumnDecorator> getColumnDecorators() {
        List<ColumnDecorator> columnDecorators = new ArrayList<ColumnDecorator>();
        columnDecorators.add(new ColumnDecorator("image-column", "sort-header", ""));
        columnDecorators.add(new ColumnDecorator("name-column", "sort-header", "Name"));
        columnDecorators.add(new ColumnDecorator("last-modified-column", "sort-header", "Last Modified"));
        columnDecorators.add(new ColumnDecorator("size-column", "sort-header", "Size"));
        return columnDecorators;
    }

    /**
     * Convert each DisplayRow object in the list to a String[] and add the String[] to the list of data
     */
    protected void addDisplayRows(List<DisplayRow> displayRows, List<String[]> data) {
        for (DisplayRow displayRow : displayRows) {
            addDisplayRow(displayRow, data);
        }
    }

    /**
     * Convert a DisplayRow object to a String[]
     */
    protected void addDisplayRow(DisplayRow displayRow, List<String[]> data) {
        if (displayRow == null) {
            return;
        }
        String[] row = new String[4];
        row[0] = displayRow.getImage();
        row[1] = displayRow.getAhref();
        row[2] = displayRow.getLastModified();
        row[3] = displayRow.getSize();
        data.add(row);
    }

    /**
     * Convert a commonPrefix into a DisplayRow object for the UI
     */
    protected DisplayRow getUpOneDirectoryDisplayRow(String prefix, String delimiter) {
        if (StringUtils.isEmpty(prefix)) {
            return null;
        }

        // Create some UI friendly strings
        String image = "";
        String show = ".." + delimiter;
        String dest = getUpOneDirectoryDest(prefix, delimiter);
        String ahref = html.getHref(dest, show);
        String date = "";
        String size = "";

        // Store them in an object
        DisplayRow displayRow = new DisplayRow();
        displayRow.setImage(image);
        displayRow.setAhref(ahref);
        displayRow.setLastModified(date);
        displayRow.setSize(size);
        return displayRow;
    }

    /**
     * Convert a commonPrefix into a DisplayRow object for the UI
     */
    protected DisplayRow getDisplayRow(String commonPrefix, String prefix, String delimiter) {

        // Create some UI friendly strings
        String image = html.getImage(getDirectoryImage());
        String show = getShow(commonPrefix, prefix);
        String dest = delimiter + commonPrefix;
        String ahref = html.getHref(dest, show);
        String date = "-";
        String size = "-";

        // Store them in an object
        DisplayRow displayRow = new DisplayRow();
        displayRow.setImage(image);
        displayRow.setAhref(ahref);
        displayRow.setLastModified(date);
        displayRow.setSize(size);
        return displayRow;
    }

    protected List<DisplayRow> getDirectoryDisplayRows(ObjectListing objectListing, String prefix, String delimiter) {
        List<DisplayRow> displayRows = new ArrayList<DisplayRow>();
        for (String commonPrefix : objectListing.getCommonPrefixes()) {
            DisplayRow displayRow = getDisplayRow(commonPrefix, prefix, delimiter);
            if (displayRow == null) {
                continue;
            }
            displayRows.add(displayRow);
        }
        return displayRows;
    }

    /**
     * Convert "foo/bar/css/" into "foo/bar/css"<br>
     * Convert "foo/bar/css" into "foo/bar"<br>
     */
    protected String getTrimmedPrefix(String prefix, String delimiter) {
        int pos = prefix.lastIndexOf(delimiter);
        if (pos == -1) {
            return prefix;
        }
        return prefix.substring(0, pos);
    }

    protected boolean isDirectory(S3ObjectSummary summary, List<String> commonPrefixes, String prefix, String delimiter) {
        String key = summary.getKey();
        if (key.equals(prefix)) {
            return true;
        }
        for (String commonPrefix : commonPrefixes) {
            if (key.equals(commonPrefix)) {
                return true;
            }
            String trimmedPrefix = getTrimmedPrefix(commonPrefix, delimiter);
            if (key.equals(trimmedPrefix)) {
                return true;
            }
        }
        return false;
    }

    protected List<DisplayRow> getObjectDisplayRows(ObjectListing objectListing, String prefix, String delimiter) {
        List<DisplayRow> displayRows = new ArrayList<DisplayRow>();
        for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
            if (isDirectory(summary, objectListing.getCommonPrefixes(), prefix, delimiter)) {
                continue;
            }
            DisplayRow displayRow = getDisplayRow(summary, prefix, delimiter);
            if (displayRow == null) {
                continue;
            }
            displayRows.add(displayRow);
        }
        return displayRows;
    }

    /**
     * Convert an S3ObjectSummary into a DisplayRow object for the UI
     */
    protected DisplayRow getDisplayRow(S3ObjectSummary summary, String prefix, String delimiter) {
        String key = summary.getKey();

        // Create some UI friendly strings
        String image = html.getImage(getFileImage());
        String show = getShow(key, prefix);
        String dest = delimiter + key;
        String ahref = html.getHref(dest, show);
        String date = dateFormatter.format(summary.getLastModified());
        String size = nf.format((summary.getSize() / 1024D)) + " KiB";

        // Store them in an object
        DisplayRow displayRow = new DisplayRow();
        displayRow.setImage(image);
        displayRow.setAhref(ahref);
        displayRow.setLastModified(date);
        displayRow.setSize(size);
        return displayRow;
    }

    /**
     * Create an object in the bucket under a key that lets a normal http request function correctly with CloudFront / S3.<br>
     * Either use the client's object or upload some html created by this plugin<br>
     */
    protected void updateDirectory(AmazonS3Client client, String html, String bucket, String copyToKey, ObjectListing objectListing, String defaultObjectKey, boolean isCopyIfExists) throws IOException {
        boolean containsDefaultObject = isExistingObject(objectListing, defaultObjectKey);
        if (containsDefaultObject && isCopyIfExists) {
            // Copy the contents of the clients default object
            client.copyObject(getCopyObjectRequest(bucket, defaultObjectKey, copyToKey));
        } else {
            // Upload our custom content
            client.putObject(getPutIndexObjectRequest(html, copyToKey));
        }
    }

    /**
     * Update this S3 "directory".
     */
    protected void updateDirectory(AmazonS3Client client, String bucket, String html, String prefix, String delimiter, String defaultObjectKey, ObjectListing objectListing) throws IOException {
        String trimmedPrefix = getTrimmedPrefix(prefix, delimiter);

        // Handle "http://www.mybucket.com/foo/bar/"
        updateDirectory(client, html, bucket, prefix, objectListing, defaultObjectKey, isCopyDefaultObjectWithDelimiter());

        // Handle "http://www.mybucket.com/foo/bar"
        updateDirectory(client, html, bucket, trimmedPrefix, objectListing, defaultObjectKey, isCopyDefaultObjectWithoutDelimiter());
    }

    /**
     * If this is the root of the bucket and the default object was created by this plugin, overwrite the default object with
     * newly generated html. Otherwise, do nothing.
     */
    protected void handleRoot(boolean isRoot, AmazonS3Client client, String bucket, String html, ObjectListing objectListing, String defaultObjectKey, String delimiter, String prefix) throws IOException {
        if (!isRoot) {
            return;
        }

        boolean isCreateOrUpdateDefaultObject = isCreateOrUpdateDefaultObject(client, bucket, objectListing, defaultObjectKey);
        if (!isCreateOrUpdateDefaultObject) {
            return;
        }

        // Update the default object
        client.putObject(getPutIndexObjectRequest(html, defaultObjectKey));
    }

    /**
     * Recurse the hierarchy of a bucket starting at "prefix" and create entries in the bucket corresponding to the directory
     * structure of the hierarchy
     */
    protected void recurse(AmazonS3Client client, String bucket, String prefix, String delimiter) throws IOException {
        ListObjectsRequest request = new ListObjectsRequest(bucket, prefix, null, delimiter, 1000);
        ObjectListing objectListing = client.listObjects(request);
        List<String[]> data = getData(objectListing, prefix, delimiter);
        String html = getHtml(data, prefix, delimiter);
        String defaultObjectKey = (prefix == null) ? getDefaultObject() : prefix + getDefaultObject();
        // Is this the root of the bucket?
        boolean isRoot = prefix == null;
        handleRoot(isRoot, client, bucket, html, objectListing, defaultObjectKey, delimiter, prefix);

        // If this is not the root, there is more to do
        if (!isRoot) {
            updateDirectory(client, bucket, html, prefix, delimiter, defaultObjectKey, objectListing);
        }

        // Recurse down the hierarchy
        List<String> commonPrefixes = objectListing.getCommonPrefixes();
        for (String commonPrefix : commonPrefixes) {
            recurse(client, bucket, commonPrefix, delimiter);
        }
    }

    /**
     * Return true if the ObjectListing contains an object under "key"
     */
    protected boolean isExistingObject(ObjectListing objectListing, String key) {
        List<S3ObjectSummary> summaries = objectListing.getObjectSummaries();
        for (S3ObjectSummary summary : summaries) {
            if (key.equals(summary.getKey())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if there is no object in the ObjectListing under defaultObjectKey.<br>
     * Return true if the object in the ObjectListing was created by this plugin.<br>
     * Return false otherwise.<br>
     */
    protected boolean isCreateOrUpdateDefaultObject(AmazonS3Client client, String bucket, ObjectListing objectListing, String defaultObjectKey) {
        if (!isExistingObject(objectListing, defaultObjectKey)) {
            // There is no default object, we are free to create one
            return true;
        }
        // There is a default object, but if it was created by this plugin, we still need to update it
        S3Object s3Object = client.getObject(bucket, defaultObjectKey);
        boolean isOurDefaultObject = isOurObject(s3Object);
        IOUtils.closeQuietly(s3Object.getObjectContent());
        if (isOurDefaultObject) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return true if this S3Object was created by this plugin. This is is done by checking the metadata attached to this object
     * for the presence of a custom value.
     */
    protected boolean isOurObject(S3Object s3Object) {
        ObjectMetadata metadata = s3Object.getObjectMetadata();
        Map<String, String> userMetadata = metadata.getUserMetadata();
        String value = userMetadata.get(S3_INDEX_METADATA_KEY);
        boolean isOurObject = "true".equals(value);
        return isOurObject;
    }

    /**
     * Create a CopyObjectRequest with an ACL set to PublicRead
     */
    protected CopyObjectRequest getCopyObjectRequest(String bucket, String sourceKey, String destKey) {
        CopyObjectRequest request = new CopyObjectRequest(bucket, sourceKey, bucket, destKey);
        request.setCannedAccessControlList(CannedAccessControlList.PublicRead);
        return request;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDefaultObject() {
        return defaultObject;
    }

    public void setDefaultObject(String defaultCloudFrontObject) {
        this.defaultObject = defaultCloudFrontObject;
    }

    public String getFileImage() {
        return fileImage;
    }

    public void setFileImage(String fileImage) {
        this.fileImage = fileImage;
    }

    public String getDirectoryImage() {
        return directoryImage;
    }

    public void setDirectoryImage(String directoryImage) {
        this.directoryImage = directoryImage;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public boolean isCopyDefaultObjectWithDelimiter() {
        return copyDefaultObjectWithDelimiter;
    }

    public void setCopyDefaultObjectWithDelimiter(boolean copyDefaultObjectWithDelimiter) {
        this.copyDefaultObjectWithDelimiter = copyDefaultObjectWithDelimiter;
    }

    public boolean isCopyDefaultObjectWithoutDelimiter() {
        return copyDefaultObjectWithoutDelimiter;
    }

    public void setCopyDefaultObjectWithoutDelimiter(boolean copyDefaultObjectWithoutDelimiter) {
        this.copyDefaultObjectWithoutDelimiter = copyDefaultObjectWithoutDelimiter;
    }

}
