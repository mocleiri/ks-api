package org.apache.torque.mojo;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;
import org.apache.maven.shared.filtering.MavenFileFilter;
import org.apache.maven.shared.filtering.MavenFileFilterRequest;
import org.apache.maven.shared.filtering.MavenFilteringException;
import org.apache.torque.engine.database.model.Database;
import org.apache.torque.engine.database.model.Table;
import org.codehaus.plexus.util.FileUtils;
import org.kuali.core.db.torque.Utils;
import org.kuali.db.DatabaseType;
import org.kuali.db.JDBCConfiguration;
import org.kuali.db.JDBCUtils;
import org.kuali.db.SQLExecutor;
import org.kuali.db.Transaction;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import static org.apache.commons.lang.StringUtils.*;

/**
 * Execute the SQL files generated to create a schema and import data
 * 
 * @goal import
 */
public class ImportMojo extends AbstractMojo {
	Utils utils = new Utils();
	JDBCUtils jdbcUtils = new JDBCUtils();

	/**
	 * Call {@link #setOrderFile(String)} with this value to sort in ascendant order the sql files.
	 */
	public static final String FILE_SORTING_ASC = "ascending";

	/**
	 * Call {@link #setOrderFile(String)} with this value to sort in descendant order the sql files.
	 */
	public static final String FILE_SORTING_DSC = "descending";

	// ////////////////////////// User Info ///////////////////////////////////

	/**
	 * Spring style resource entries that point to one or more schema XML files
	 * 
	 * @parameter expression="${schemas}"
	 */
	private List<String> schemas;

	/**
	 * The type of database we are targeting eg oracle, mysql etc
	 * 
	 * @since 1.0
	 * @parameter expression="${targetDatabase}"
	 * @required
	 */
	private String targetDatabase;

	/**
	 * If no username/password is supplied, use the artifact id of the project as the username/password
	 * 
	 * @since 1.0
	 * @parameter expression="${useArtifactIdForCredentials}" default-value="false"
	 */
	private boolean useArtifactIdForCredentials;

	/**
	 * If useArtifactIdForCredentials is true, and trim artifactId is true, any hyphens in the artifact id are trimmed
	 * out and if the artifactId ends in "-db" the trailing "-db" is trimmed off.
	 * 
	 * @since 1.0
	 * @parameter expression="${trimArtifactId}" default-value="true"
	 */
	private boolean trimArtifactId;

	/**
	 * Database username. If not given, it will be looked up through <code>settings.xml</code>'s server with
	 * <code>${settingsKey}</code> as key.
	 * 
	 * @since 1.0
	 * @parameter expression="${username}"
	 */
	private String username;

	/**
	 * Database password. If not given, it will be looked up through <code>settings.xml</code>'s server with
	 * <code>${settingsKey}</code> as key.
	 * 
	 * @since 1.0
	 * @parameter expression="${password}"
	 */
	private String password;

	/**
	 * Ignore the password and use anonymous access. This may be useful for databases like MySQL which do not allow
	 * empty password parameters in the connection initialization.
	 * 
	 * @since 1.4
	 * @parameter default-value="false"
	 */
	private boolean enableAnonymousPassword;

	/**
	 * Additional key=value pairs separated by comma to be passed into JDBC driver.
	 * 
	 * @since 1.0
	 * @parameter expression="${driverProperties}" default-value = ""
	 */
	private String driverProperties;

	/**
	 * @parameter expression="${settings}"
	 * @required
	 * @since 1.0
	 * @readonly
	 */
	private Settings settings;

	/**
	 * Server's <code>id</code> in <code>settings.xml</code> to look up username and password. Defaults to
	 * <code>${url}</code> if not given.
	 * 
	 * @since 1.0
	 * @parameter expression="${settingsKey}"
	 */
	private String settingsKey;

	/**
	 * Skip execution when there is an error obtaining a connection. This is a special case to support databases, such
	 * as embedded Derby, that can shutdown the database via the URL (i.e. <code>shutdown=true</code>).
	 * 
	 * @since 1.1
	 * @parameter expression="${skipOnConnectionError}" default-value="false"
	 */
	private boolean skipOnConnectionError;

	/**
	 * Setting this parameter to <code>true</code> will force the execution of this mojo, even if it would get skipped
	 * usually.
	 * 
	 * @parameter expression="${forceOpenJpaExecution}" default-value=false
	 * @required
	 */
	private boolean forceMojoExecution;

	/**
	 * The Maven Project Object
	 * 
	 * @parameter default-value="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;

	/**
	 * @parameter default-value="${session}"
	 * @required
	 * @readonly
	 */
	private MavenSession mavenSession;

	// ////////////////////////////// Source info /////////////////////////////

	/**
	 * SQL input commands separated by <code>${delimiter}</code>.
	 * 
	 * @since 1.0
	 * @parameter expression="${sqlCommand}" default-value=""
	 */
	private String sqlCommand = "";

	/**
	 * List of files containing SQL statements to load.
	 * 
	 * @since 1.0
	 * @parameter
	 */
	private File[] srcFiles;

	/**
	 * File(s) containing SQL statements to load.
	 * 
	 * @since 1.0
	 * @parameter
	 */
	private Fileset fileset;

	/**
	 * When <code>true</code>, skip the execution.
	 * 
	 * @since 1.0
	 * @parameter default-value="false"
	 */
	private boolean skip;

	// //////////////////////////////// Database info /////////////////////////
	/**
	 * Database URL.
	 * 
	 * @parameter expression="${url}"
	 * @required
	 * @since 1.0-beta-1
	 */
	private String url;

	/**
	 * Database driver classname.
	 * 
	 * @since 1.0
	 * @parameter expression="${driver}"
	 * @required
	 */
	private String driver;

	// //////////////////////////// Operation Configuration ////////////////////
	/**
	 * Set to <code>true</code> to execute none-transactional SQL.
	 * 
	 * @since 1.0
	 * @parameter expression="${autocommit}" default-value="false"
	 */
	private boolean autocommit;

	/**
	 * Action to perform if an error is found. Possible values are <code>abort</code> and <code>continue</code>.
	 * 
	 * @since 1.0
	 * @parameter expression="${onError}" default-value="abort"
	 */
	private String onError = SQLExecutor.ON_ERROR_ABORT;

	// //////////////////////////// Parser Configuration ////////////////////

	/**
	 * Set to false to skip importing data
	 * 
	 * @since 1.0
	 * @parameter expression="${importData}" default-value="true"
	 */
	private boolean importData;

	/**
	 * Set to false to skip importing the schema
	 * 
	 * @since 1.0
	 * @parameter expression="${importSchema}" default-value="true"
	 */
	private boolean importSchema;

	/**
	 * Set to false to skip importing the schema constraints
	 * 
	 * @since 1.0
	 * @parameter expression="${importSchemaConstraints}" default-value="true"
	 */
	private boolean importSchemaConstraints;

	/**
	 * Set the delimiter that separates SQL statements.
	 * 
	 * @since 1.0
	 * @parameter expression="${delimiter}" default-value="/"
	 */
	private String delimiter = "/";

	/**
	 * <p>
	 * The delimiter type takes two values - "normal" and "row". Normal means that any occurrence of the delimiter
	 * terminate the SQL command whereas with row, only a line containing just the delimiter is recognized as the end of
	 * the command.
	 * </p>
	 * <p>
	 * For example, set this to "go" and delimiterType to "row" for Sybase ASE or MS SQL Server.
	 * </p>
	 * 
	 * @since 1.2
	 * @parameter expression="${delimiterType}" default-value="row"
	 */
	private String delimiterType = DelimiterType.ROW;

	/**
	 * Set the order in which the SQL files will be executed. Possible values are <code>ascending</code> and
	 * <code>descending</code>. Any other value means that no sorting will be performed.
	 * 
	 * @since 1.1
	 * @parameter expression="${orderFile}" default-value="ascending"
	 */
	private String orderFile = "ascending";

	/**
	 * Keep the format of an SQL block.
	 * 
	 * @since 1.1
	 * @parameter expression="${keepFormat}" default-value="true"
	 */
	private boolean keepFormat = true;

	// /////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Print SQL results.
	 * 
	 * @parameter
	 * @since 1.3
	 */
	private boolean printResultSet = false;

	/**
	 * Print header columns.
	 */
	private boolean showheaders = true;

	/**
	 * Dump the SQL exection's output to a file. Default is stdout.
	 * 
	 * @parameter
	 * @since 1.3
	 */
	private File outputFile;

	/**
	 * @parameter default-value=","
	 * @since 1.4
	 */
	private String outputDelimiter;

	/**
	 * Encoding to use when reading SQL statements from a file.
	 * 
	 * @parameter expression="${encoding}" default-value= "${project.build.sourceEncoding}"
	 * @since 1.1
	 */
	private String encoding = "";

	/**
	 * Append to an existing file or overwrite it?
	 */
	private boolean append = false;

	/**
	 * Argument to Statement.setEscapeProcessing If you want the driver to use regular SQL syntax then set this to
	 * false.
	 * 
	 * @since 1.4
	 * @parameter expression="${escapeProcessing}" default-value="true"
	 */
	private boolean escapeProcessing = true;

	// //////////////////////////////// Internal properties//////////////////////

	/**
	 * number of successful executed statements
	 */
	private int successfulStatements = 0;

	/**
	 * number of total executed statements
	 */
	private int totalStatements = 0;

	/**
	 * Database connection
	 */
	private Connection conn = null;

	/**
	 * SQL transactions to perform
	 */
	private Vector<Transaction> transactions = new Vector<Transaction>();

	/**
	 * @component role="org.apache.maven.shared.filtering.MavenFileFilter"
	 * @since 1.4
	 */
	private MavenFileFilter fileFilter;

	/**
	 * Set to true if you want to filter the srcFiles using system-, user- and project properties
	 * 
	 * @parameter
	 * @since 1.4
	 */
	private boolean enableFiltering;

	/**
	 * Interpolator especially for braceless expressions
	 */
	// private Interpolator interpolator = new RegexBasedInterpolator("\\$([^\\s;)]+?)", "(?=[\\s;)])");

	/**
	 * Add a SQL transaction to execute
	 * 
	 * @return a new SqlExecMojo.Transaction
	 */
	public Transaction createTransaction() {
		Transaction t = new Transaction();
		transactions.addElement(t);
		return t;
	}

	/**
	 * Set an inline SQL command to execute. NB: Properties are not expanded in this text.
	 * 
	 * @param sql
	 *            the sql statement to add
	 */
	public void addText(String sql) {
		this.sqlCommand += sql;
	}

	/**
	 * Set the file encoding to use on the SQL files read in
	 * 
	 * @param encoding
	 *            the encoding to use on the files
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Set the delimiter that separates SQL statements. Defaults to &quot;;&quot;;
	 * 
	 * @param delimiter
	 *            the new delimiter
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Set the delimiter type: "normal" or "row" (default "normal").
	 * 
	 * @param delimiterType
	 *            the new delimiterType
	 */
	public void setDelimiterType(String delimiterType) {
		this.delimiterType = delimiterType;
	}

	/**
	 * Print result sets from the statements; optional, default false
	 * 
	 * @param print
	 *            <code>true</code> to print the resultset, otherwise <code>false</code>
	 */
	public void setPrintResultSet(boolean print) {
		this.printResultSet = print;
	}

	/**
	 * Print headers for result sets from the statements; optional, default true.
	 * 
	 * @param showheaders
	 *            <code>true</code> to show the headers, otherwise <code>false</code>
	 */
	public void setShowheaders(boolean showheaders) {
		this.showheaders = showheaders;
	}

	/**
	 * Set the output file;
	 * 
	 * @param output
	 *            the output file
	 */
	public void setOutputFile(File output) {
		this.outputFile = output;
	}

	/**
	 * whether output should be appended to or overwrite an existing file. Defaults to false.
	 * 
	 * @param append
	 *            <code>true</code> to append, otherwise <code>false</code> to overwrite
	 */
	public void setAppend(boolean append) {
		this.append = append;
	}

	/**
	 * whether or not format should be preserved. Defaults to false.
	 * 
	 * @param keepformat
	 *            The keepformat to set
	 */
	public void setKeepFormat(boolean keepformat) {
		this.keepFormat = keepformat;
	}

	/**
	 * Set escape processing for statements.
	 * 
	 * @param enable
	 *            <code>true</code> to escape, otherwiser <code>false</code>
	 */
	public void setEscapeProcessing(boolean enable) {
		escapeProcessing = enable;
	}

	/**
	 * <p>
	 * Determine if the mojo execution should get skipped.
	 * </p>
	 * This is the case if:
	 * <ul>
	 * <li>{@link #skip} is <code>true</code></li>
	 * <li>if the mojo gets executed on a project with packaging type 'pom' and {@link #forceMojoExecution} is
	 * <code>false</code></li>
	 * </ul>
	 * 
	 * @return <code>true</code> if the mojo execution should be skipped.
	 */
	protected boolean skipMojo() {
		if (skip) {
			getLog().info("Skip sql execution");
			return true;
		}

		if (!forceMojoExecution && project != null && "pom".equals(project.getPackaging())) {
			getLog().info("Skipping sql execution for project with packaging type 'pom'");
			return true;
		}

		return false;
	}

	/**
	 * Attempt to automatically detect the correct JDBC driver and database type (oracle, mysql, h2, derby, etc) given a
	 * JDBC url
	 */
	protected void updateConfiguration() {
		JDBCConfiguration config = jdbcUtils.getDatabaseConfiguration(url);
		if (config.equals(JDBCConfiguration.UNKNOWN_CONFIG)) {
			return;
		}

		if (isBlank(driver)) {
			driver = config.getDriver();
		}

		if (isBlank(targetDatabase)) {
			targetDatabase = config.getType().toString().toLowerCase();
		}
	}

	/**
	 * Validate that some essential configuration items are present
	 */
	protected void validateConfiguration() throws MojoExecutionException {
		if (isBlank(driver)) {
			throw new MojoExecutionException("No database driver. Specify one in the plugin configuration.");
		}

		if (isBlank(url)) {
			throw new MojoExecutionException("No database url. Specify one in the plugin configuration.");
		}

		try {
			DatabaseType.valueOf(targetDatabase.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new MojoExecutionException("Database type of '" + targetDatabase + "' is invalid.  Valid values: " + org.springframework.util.StringUtils.arrayToCommaDelimitedString(DatabaseType.values()));
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new MojoExecutionException("Can't load driver class " + driver + ". Be sure to include it as a plugin dependency.");
		}
	}

	/**
	 * Load the sql file and then execute it
	 * 
	 * @throws MojoExecutionException
	 */
	public void execute() throws MojoExecutionException {

		if (skipMojo()) {
			getLog().info("------------------------------------------------------------------------");
			getLog().info("Skipping SQL execution");
			getLog().info("------------------------------------------------------------------------");
			return;
		}

		updateConfiguration();
		validateConfiguration();
		updateCredentials();

		getLog().info("------------------------------------------------------------------------");
		getLog().info("Executing " + getTargetDatabase() + " SQL");
		getLog().info("------------------------------------------------------------------------");

		if (schemas != null) {
			addSchemaXMLResourcesToTransactions();
		} else if (fileset == null) {
			fileset = new Fileset();
			fileset.setBasedir(project.getBuild().getDirectory() + "/generated-sql/impex/" + getTargetDatabase());
			fileset.setIncludes(new String[] { "*.sql" });
		}

		successfulStatements = 0;

		totalStatements = 0;

		addCommandToTransactions();

		addFilesToTransactions();

		addFileSetToTransactions();

		sortTransactions();

		try {
			conn = getConnection();
		} catch (SQLException e) {
			if (!this.skipOnConnectionError) {
				throw new MojoExecutionException(e.getMessage(), e);
			} else {
				// error on get connection and user asked to skip the rest
				return;
			}
		}

		try {
			for (Transaction t : transactions) {
				getLog().debug("Resource Location: " + t.getResourceLocation());
			}
			SQLExecutor executor = new SQLExecutor();
			BeanUtils.copyProperties(executor, this);
			executor.addListener(new MojoDatabaseListener(getLog()));
			executor.runTransactions(transactions);
		} catch (Exception e) {
			throw new MojoExecutionException("Error executing SQL", e);
		}

	}

	/**
	 * Add sql command to transactions list.
	 */
	protected void addCommandToTransactions() {
		if (!isEmpty(sqlCommand)) {
			createTransaction().addText(sqlCommand.trim());
		}
	}

	/**
	 * Add user sql fileset to transation list
	 * 
	 */
	protected void addFileSetToTransactions() {
		String[] includedFiles;
		if (fileset != null) {
			fileset.scan();
			includedFiles = fileset.getIncludedFiles();
		} else {
			includedFiles = new String[0];
		}

		for (int j = 0; j < includedFiles.length; j++) {
			createTransaction().setResourceLocation(new File(fileset.getBasedir(), includedFiles[j]).getAbsolutePath());
		}
	}

	protected String getDefaultSchemaLocation() {
		String schema = project.getArtifactId();
		return "classpath:" + schema + "-schema.xml";
	}

	protected boolean defaultSchemaExists() {
		DefaultResourceLoader loader = new DefaultResourceLoader();
		Resource resource = loader.getResource(getDefaultSchemaLocation());
		return resource.exists();
	}

	protected boolean isNullOrEmpty(Collection<?> c) {
		if (c == null) {
			return true;
		}
		if (c.size() == 0) {
			return true;
		}
		return false;
	}

	protected void addDefaultSchema() {
		// They supplied a list of schemas. Only update schemas in their list
		if (!isNullOrEmpty(getSchemas())) {
			return;
		}
		// The default schema does not exist
		if (!defaultSchemaExists()) {
			return;
		}
		// We are not importing a schema
		if (!isImportSchema()) {
			return;
		}

		// Add the default schema to the list
		List<String> schemas = getSchemas();
		if (schemas == null) {
			schemas = new ArrayList<String>();
		}
		schemas.add(getDefaultSchemaLocation());
		setSchemas(schemas);
	}

	protected void addSchemaXMLResourcesToTransactions() throws MojoExecutionException {
		addDefaultSchema();
		if (isNullOrEmpty(getSchemas())) {
			return;
		}

		try {
			List<Database> databases = new Utils().getDatabases(getSchemas(), getTargetDatabase());
			for (String schemaXMLResource : getSchemas()) {
				getLog().info("Adding " + schemaXMLResource);
			}
			for (Database database : databases) {
				handleDatabase(database);
			}
		} catch (IOException e) {
			throw new MojoExecutionException("Error obtaining databases: " + e);
		}
	}

	protected void handleDatabase(Database database) {
		DefaultResourceLoader loader = new DefaultResourceLoader();

		if (isImportSchema()) {
			String schemaSQL = "classpath:sql/" + getTargetDatabase() + "/" + database.getName() + "-schema.sql";
			createTransaction().setResourceLocation(schemaSQL);
		}
		if (isImportData()) {
			List<?> tables = database.getTables();
			for (Object object : tables) {
				Table table = (Table) object;
				String location = "classpath:sql/" + getTargetDatabase() + "/" + table.getName() + ".sql";
				Resource resource = loader.getResource(location);
				if (!resource.exists()) {
					getLog().debug("Skipping " + location + " because it does not exist");
					continue;
				} else {
					createTransaction().setResourceLocation(location);
					getLog().debug("Adding " + location);
				}
			}
		}
		if (isImportSchemaConstraints()) {
			String schemaConstraintsSQL = "classpath:sql/" + getTargetDatabase() + "/" + database.getName() + "-schema-constraints.sql";
			createTransaction().setResourceLocation(schemaConstraintsSQL);
		}
	}

	/**
	 * Add user input of srcFiles to transaction list.
	 * 
	 * @throws MojoExecutionException
	 */
	protected void addFilesToTransactions() throws MojoExecutionException {
		File[] files = getSrcFiles();

		MavenFileFilterRequest request = new MavenFileFilterRequest();
		request.setEncoding(encoding);
		request.setMavenSession(mavenSession);
		request.setMavenProject(project);
		request.setFiltering(enableFiltering);
		for (int i = 0; files != null && i < files.length; ++i) {
			if (files[i] != null && !files[i].exists()) {
				throw new MojoExecutionException(files[i].getPath() + " not found.");
			}

			File sourceFile = files[i];
			String basename = FileUtils.basename(sourceFile.getName());
			String extension = FileUtils.extension(sourceFile.getName());
			File targetFile = FileUtils.createTempFile(basename, extension, null);
			if (!getLog().isDebugEnabled()) {
				targetFile.deleteOnExit();
			}

			request.setFrom(sourceFile);
			request.setTo(targetFile);

			try {
				fileFilter.copyFile(request);
			} catch (MavenFilteringException e) {
				throw new MojoExecutionException(e.getMessage());
			}

			createTransaction().setResourceLocation(targetFile.getAbsolutePath());
		}
	}

	/**
	 * Sort the transaction list.
	 */
	protected void sortTransactions() {
		if (FILE_SORTING_ASC.equalsIgnoreCase(this.orderFile)) {
			Collections.sort(transactions);
		} else if (FILE_SORTING_DSC.equalsIgnoreCase(this.orderFile)) {
			Collections.sort(transactions, Collections.reverseOrder());
		}
	}

	protected String convertNullToEmpty(String s) {
		if (s == null) {
			return "";
		} else {
			return s;
		}
	}

	/**
	 * Load username/password from settings.xml if user has not set them in JVM properties
	 * 
	 * @throws MojoExecutionException
	 */
	protected void updateCredentials() throws MojoExecutionException {
		if (settingsKey == null) {
			// Use the JDBC url as a key into settings.xml by default
			settingsKey = getUrl();
		}
		Server server = settings.getServer(settingsKey);
		updateUsername(server);
		updatePassword(server);
	}

	protected void updatePassword(Server server) {
		// They already gave us a password, don't mess with it
		if (getPassword() != null) {
			return;
		}
		if (server != null) {
			// We've successfully located a server in settings.xml, use the password from that
			setPassword(server.getPassword());
		} else if (useArtifactIdForCredentials) {
			// No password was explicitly set, no server was located in settings.xml and they've asked for the password
			// to default to the artifact id
			if (isTrimArtifactId()) {
				setPassword(trimArtifactId(project.getArtifactId()));
			} else {
				setPassword(project.getArtifactId());
			}
		}
		// If it is null convert it to the empty string
		setPassword(convertNullToEmpty(getPassword()));
	}

	protected void updateUsername(Server server) {
		getLog().info("username='" + getUsername() + '"');
		// They already gave us a password, don't mess with it
		if (getUsername() != null) {
			return;
		}
		if (server != null) {
			// We've successfully located a server in settings.xml, use the username from that
			setUsername(server.getUsername());
		} else if (useArtifactIdForCredentials) {
			// No password was explicitly set, no server was located in settings.xml and they've asked for the username
			// to default to the artifact id
			if (isTrimArtifactId()) {
				setUsername(trimArtifactId(project.getArtifactId()));
			} else {
				setUsername(project.getArtifactId());
			}
		}
		// If it is null convert it to the empty string
		setUsername(convertNullToEmpty(getUsername()));
		getLog().info("username='" + getUsername() + '"');
	}

	protected String trimArtifactId(String artifactId) {
		String s = StringUtils.remove(artifactId, "-db");
		return StringUtils.remove(s, "-");
	}

	/**
	 * Creates a new Connection as using the driver, url, userid and password specified.
	 * 
	 * The calling method is responsible for closing the connection.
	 * 
	 * @return Connection the newly created connection.
	 * @throws MojoExecutionException
	 *             if the UserId/Password/Url is not set or there is no suitable driver or the driver fails to load.
	 * @throws SQLException
	 *             if there is problem getting connection with valid url
	 * 
	 */
	protected Connection getConnection() throws MojoExecutionException, SQLException {
		getLog().info("URL: " + getUrl());
		getLog().info("Username: " + getUsername());
		getLog().info("Driver: " + getDriver());
		Properties info = new Properties();
		info.put("user", getUsername());

		if (!enableAnonymousPassword) {
			info.put("password", getPassword());
		}

		info.putAll(this.getDriverProperties());

		Driver driverInstance = null;

		try {
			Class<?> dc = Class.forName(getDriver());
			driverInstance = (Driver) dc.newInstance();
		} catch (ClassNotFoundException e) {
			throw new MojoExecutionException("Driver class not found: " + getDriver(), e);
		} catch (Exception e) {
			throw new MojoExecutionException("Failure loading driver: " + getDriver(), e);
		}

		Connection conn = driverInstance.connect(getUrl(), info);

		if (conn == null) {
			// Driver doesn't understand the URL
			throw new SQLException("No suitable Driver for " + getUrl());
		}

		conn.setAutoCommit(autocommit);
		return conn;
	}

	/**
	 * parse driverProperties into Properties set
	 * 
	 * @return the driver properties
	 * @throws MojoExecutionException
	 */
	protected Properties getDriverProperties() throws MojoExecutionException {
		Properties properties = new Properties();

		if (!isEmpty(this.driverProperties)) {
			String[] tokens = split(this.driverProperties, ",");
			for (int i = 0; i < tokens.length; ++i) {
				String[] keyValueTokens = split(tokens[i].trim(), "=");
				if (keyValueTokens.length != 2) {
					throw new MojoExecutionException("Invalid JDBC Driver properties: " + this.driverProperties);
				}

				properties.setProperty(keyValueTokens[0], keyValueTokens[1]);

			}
		}

		return properties;
	}

	//
	// helper accessors for unit test purposes
	//

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return this.driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	void setAutocommit(boolean autocommit) {
		this.autocommit = autocommit;
	}

	void setFileset(Fileset fileset) {
		this.fileset = fileset;
	}

	public File[] getSrcFiles() {
		return this.srcFiles;
	}

	public void setSrcFiles(File[] files) {
		this.srcFiles = files;
	}

	public String getOrderFile() {
		return this.orderFile;
	}

	public void setOrderFile(String orderFile) {
		if (FILE_SORTING_ASC.equalsIgnoreCase(orderFile)) {
			this.orderFile = FILE_SORTING_ASC;
		} else if (FILE_SORTING_DSC.equalsIgnoreCase(orderFile)) {
			this.orderFile = FILE_SORTING_DSC;
		} else {
			throw new IllegalArgumentException(orderFile + " is not a valid value for orderFile, only '" + FILE_SORTING_ASC + "' or '" + FILE_SORTING_DSC + "'.");
		}
	}

	/**
	 * Number of SQL statements executed so far that caused errors.
	 * 
	 * @return the number
	 */
	public int getSuccessfulStatements() {
		return successfulStatements;
	}

	/**
	 * Number of SQL statements executed so far, including the ones that caused errors.
	 * 
	 * @return the number
	 */
	public int getTotalStatements() {
		return totalStatements;
	}

	public String getOnError() {
		return this.onError;
	}

	public void setOnError(String action) {
		if (SQLExecutor.ON_ERROR_ABORT.equalsIgnoreCase(action)) {
			this.onError = SQLExecutor.ON_ERROR_ABORT;
		} else if (SQLExecutor.ON_ERROR_CONTINUE.equalsIgnoreCase(action)) {
			this.onError = SQLExecutor.ON_ERROR_CONTINUE;
		} else if (SQLExecutor.ON_ERROR_ABORT_AFTER.equalsIgnoreCase(action)) {
			this.onError = SQLExecutor.ON_ERROR_ABORT_AFTER;
		} else {
			throw new IllegalArgumentException(action + " is not a valid value for onError, only '" + SQLExecutor.ON_ERROR_ABORT + "', '" + SQLExecutor.ON_ERROR_ABORT_AFTER + "', or '" + SQLExecutor.ON_ERROR_CONTINUE + "'.");
		}
	}

	void setSettings(Settings settings) {
		this.settings = settings;
	}

	void setSettingsKey(String key) {
		this.settingsKey = key;
	}

	void setSkip(boolean skip) {
		this.skip = skip;
	}

	public void setDriverProperties(String driverProperties) {
		this.driverProperties = driverProperties;
	}

	public String getSqlCommand() {
		return sqlCommand;
	}

	public void setSqlCommand(String sqlCommand) {
		this.sqlCommand = sqlCommand;
	}

	public Vector<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Vector<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void setFileFilter(MavenFileFilter filter) {
		this.fileFilter = filter;
	}

	public String getTargetDatabase() {
		return targetDatabase;
	}

	public void setTargetDatabase(String targetDatabase) {
		this.targetDatabase = targetDatabase;
	}

	public String getEncoding() {
		return encoding;
	}

	public boolean isImportData() {
		return importData;
	}

	public void setImportData(boolean importData) {
		this.importData = importData;
	}

	public boolean isImportSchema() {
		return importSchema;
	}

	public void setImportSchema(boolean importSchema) {
		this.importSchema = importSchema;
	}

	public boolean isImportSchemaConstraints() {
		return importSchemaConstraints;
	}

	public void setImportSchemaConstraints(boolean importSchemaConstraints) {
		this.importSchemaConstraints = importSchemaConstraints;
	}

	public String getOutputDelimiter() {
		return outputDelimiter;
	}

	public void setOutputDelimiter(String outputDelimiter) {
		this.outputDelimiter = outputDelimiter;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public String getDelimiterType() {
		return delimiterType;
	}

	public boolean isKeepFormat() {
		return keepFormat;
	}

	public boolean isShowheaders() {
		return showheaders;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public boolean isAppend() {
		return append;
	}

	public boolean isEscapeProcessing() {
		return escapeProcessing;
	}

	public boolean isPrintResultSet() {
		return printResultSet;
	}

	public List<String> getSchemas() {
		return schemas;
	}

	public void setSchemas(List<String> schemas) {
		this.schemas = schemas;
	}

	public boolean isUseArtifactIdForCredentials() {
		return useArtifactIdForCredentials;
	}

	public void setUseArtifactIdForCredentials(boolean useArtifactIdForCredentials) {
		this.useArtifactIdForCredentials = useArtifactIdForCredentials;
	}

	public boolean isTrimArtifactId() {
		return trimArtifactId;
	}

	public void setTrimArtifactId(boolean trimArtifactId) {
		this.trimArtifactId = trimArtifactId;
	}
}
