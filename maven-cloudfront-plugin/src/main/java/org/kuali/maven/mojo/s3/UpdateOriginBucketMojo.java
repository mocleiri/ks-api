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
 * each "directory" in the bucket and stores the html under a key in such a way that a regular http request for a directory
 * returns the html instead of the "object does not exist" XML Amazon would normally return. For example: The url
 * "http://www.mybucket.com/foo/bar" returns an html page containing a listing of all the files and directories under "foo/bar"
 * in the bucket.
 * </p>
 * 
 * <p>
 * If a directory contains an object with a key that is the same as the default object, the plugin copies the object to a key
 * representing the directory structure. For example, the url "http://www.mybucket.com/foo/bar/index.html" represents an object
 * in an S3 bucket under the key "foo/bar/index.html". The plugin will copy the object from the key "foo/bar/index.html" to the
 * key "foo/bar/". This causes the url "http://www.mybucket.com/foo/bar/" to return the same content as the url
 * "http://www.mybucket.com/foo/bar/index.html"
 * </p>
 * 
 * <p>
 * It also generates a listing for the default object at the root of the bucket hierarchy unless a default object already
 * exists.
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
     * 
     * @parameter expression="${copyDefaultObjectWithSlash}" default-value="true"
     */
    private boolean copyDefaultObjectWithDelimiter;

    /**
     * 
     * @parameter expression="${copyDefaultObjectWithSlash}" default-value="false"
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
     * The name of the key for the default object for the Cloud Front distribution. If this default object already exists, the
     * plugin will not modify it. If there is no object in the bucket under this key, the plugin will generate a directory
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
            recurse(client, getPrefix(), getDelimiter());
        } catch (Exception e) {
            throw new MojoExecutionException("Unexpected error: ", e);
        }
    }

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

    protected NumberFormat getNumberFormatInstance() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        nf.setGroupingUsed(false);
        return nf;
    }

    protected SimpleDateFormat getSimpleDateFormatInstance() {
        SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
        sdf.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        return sdf;
    }

    protected String getIndexHtml(ObjectListing objectListing) {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
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

    protected String getUpOneDirectoryPrefix(String prefix, String delimiter) {
        if (prefix.endsWith(delimiter)) {
            prefix = prefix.substring(0, prefix.length() - 1);
        }
        int pos = prefix.lastIndexOf(delimiter);
        if (pos == -1) {
            return delimiter;
        } else {
            return prefix.substring(0, pos + 1);
        }
    }

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

    protected boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

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

    protected Tag getTableRowTag(int row) {
        if ((row % 2) == 0) {
            return new Tag("tr", "table-tr-odd");
        } else {
            return new Tag("tr");
        }
    }

    protected String getTableCell(String content, ColumnDecorator decorator) {
        Tag td = new Tag("td", decorator.getTableDataClass());
        return html.getTag(td, content);
    }

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

    protected String getTableRows(List<String[]> data, List<ColumnDecorator> columnDecorators) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.size(); i++) {
            sb.append(getTableRow(i, data.get(i), columnDecorators));
        }
        return sb.toString();
    }

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

    protected List<ColumnDecorator> getColumnDecorators() {
        List<ColumnDecorator> columnDecorators = new ArrayList<ColumnDecorator>();
        columnDecorators.add(new ColumnDecorator("image-column", "sort-header", ""));
        columnDecorators.add(new ColumnDecorator("name-column", "sort-header", "Name"));
        columnDecorators.add(new ColumnDecorator("last-modified-column", "sort-header", "Last Modified"));
        columnDecorators.add(new ColumnDecorator("size-column", "sort-header", "Size"));
        return columnDecorators;
    }

    protected void addDisplayRows(List<DisplayRow> displayRows, List<String[]> data) {
        for (DisplayRow displayRow : displayRows) {
            addDisplayRow(displayRow, data);
        }
    }

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
        String dest = getUpOneDirectoryPrefix(prefix, delimiter);
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

    protected void updateDirectory(AmazonS3Client client, String html, String prefix, String delimiter, String defaultObjectKey, ObjectListing objectListing) throws IOException {
        // Place the html we generated under a key without a trailing slash, eg "foo/bar"
        // This causes the url http://www.mybucket.com/foo/bar to respond with html instead of AWS xml
        // The html generated by the maven-site-plugin does not render correctly if the url does not end with a slash
        // eg "http://www.mybucket.com/foo/bar/" works great, but "http://www.mybucket.com/foo/bar" does not
        // That is why we return our internally generated html if someone types in the url without a trailing slash
        // instead of the "real" index.html placed into this directory by the client
        String trimmedPrefix = getTrimmedPrefix(prefix, delimiter);

        if (isOurDefaultObject(client, objectListing, defaultObjectKey, delimiter)) {
            // The defaultObject was generated by this plugin, we are free to overwrite it
            // Copy the html we generated to "foo/bar/" and "foo/bar"
            client.putObject(getPutIndexObjectRequest(html, prefix));
            client.putObject(getPutIndexObjectRequest(html, trimmedPrefix));
        } else {
            // The default object was not generated by this plugin
            if (isCopyDefaultObjectWithDelimiter()) {
                // Copy the contents of the clients default object to "foo/bar/"
                // This allows the url "http://www.mybucket.com/foo/bar/" to return the default object
                client.copyObject(getCopyObjectRequest(getBucket(), defaultObjectKey, prefix));
            }
            if (isCopyDefaultObjectWithoutDelimiter()) {
                // Copy the contents of the clients default object to "foo/bar"
                // This allows the url "http://www.mybucket.com/foo/bar" to return the default object
                client.copyObject(getCopyObjectRequest(getBucket(), defaultObjectKey, trimmedPrefix));
            }
        }
    }

    protected void handleRoot(boolean isRoot, AmazonS3Client client, String html, ObjectListing objectListing, String defaultObjectKey, String delimiter, String prefix) throws IOException {
        if (!isRoot) {
            return;
        }

        boolean isOurDefaultObject = isOurDefaultObject(client, objectListing, defaultObjectKey, delimiter);
        if (isOurDefaultObject) {
            client.putObject(getPutIndexObjectRequest(html, defaultObjectKey));
        }
    }

    protected void recurse(AmazonS3Client client, String prefix, String delimiter) throws IOException {
        ListObjectsRequest request = new ListObjectsRequest(getBucket(), prefix, null, delimiter, Integer.MAX_VALUE);
        ObjectListing objectListing = client.listObjects(request);
        List<String[]> data = getData(objectListing, prefix, delimiter);
        String html = getHtml(data, prefix, delimiter);
        String defaultObjectKey = (prefix == null) ? getDefaultObject() : prefix + getDefaultObject();
        // Is this the root of the bucket?
        boolean isRoot = prefix == null;
        handleRoot(isRoot, client, html, objectListing, defaultObjectKey, delimiter, prefix);

        // If this is not the root, there is more to do
        if (!isRoot) {
            updateDirectory(client, html, prefix, delimiter, defaultObjectKey, objectListing);
        }

        List<String> commonPrefixes = objectListing.getCommonPrefixes();
        for (String commonPrefix : commonPrefixes) {
            recurse(client, commonPrefix, delimiter);
        }
    }

    protected boolean isExistingObject(ObjectListing objectListing, String key) {
        List<S3ObjectSummary> summaries = objectListing.getObjectSummaries();
        for (S3ObjectSummary summary : summaries) {
            if (key.equals(summary.getKey())) {
                return true;
            }
        }
        return false;
    }

    protected boolean isOurDefaultObject(AmazonS3Client client, ObjectListing objectListing, String defaultObjectKey, String delimiter) {
        if (!isExistingObject(objectListing, defaultObjectKey)) {
            // We are in the root, and there is no default object, we will create one
            return true;
        }
        // There is a default object, but we need to check to see if it was created by this plugin
        // If it was, we will update it with new content
        // If not, we'll leave it alone
        S3Object object = client.getObject(getBucket(), defaultObjectKey);
        ObjectMetadata metadata = object.getObjectMetadata();
        Map<String, String> userMetadata = metadata.getUserMetadata();
        String value = userMetadata.get(S3_INDEX_METADATA_KEY);
        IOUtils.closeQuietly(object.getObjectContent());
        boolean isOurDefaultObject = "true".equals(value);
        // If it is our default object, we need to update it, otherwise, we'll leave it alone
        return isOurDefaultObject;
    }

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
