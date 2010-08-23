package org.apache.torque.mojo;

import java.io.IOException;
import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.db.DatabaseCommand;
import org.kuali.db.SQLGenerator;
import org.kuali.db.Transaction;

import static org.apache.commons.lang.StringUtils.*;

/**
 * Runs a command that performs an operation on a database (create,drop etc)
 */
public abstract class AbstractDatabaseCommandMojo extends AbstractSQLExecutorMojo {

	public abstract DatabaseCommand getCommand();

	/**
	 * The database to create
	 * 
	 * @parameter expression="${database}" default-value="${project.artifactId}"
	 * @required
	 */
	String database;

	/**
	 * The password to use when accessing this database
	 * 
	 * @parameter expression="${databasePassword}" default-value="${project.artifactId}"
	 */
	String databasePassword;

	protected void updateConfiguration() throws MojoExecutionException {
		super.updateConfiguration();
		if (project.getArtifactId().equals(database)) {
			database = getTrimmedArtifactId();
		}
		if (project.getArtifactId().equals(databasePassword)) {
			databasePassword = getTrimmedArtifactId();
		}
	}

	protected void validateConfiguration() throws MojoExecutionException {
		super.validateConfiguration();
		if (isEmpty(database)) {
			throw new MojoExecutionException("Must specify a database to " + getCommand());
		}

		if (isEmpty(username) || isEmpty(password)) {
			throw new MojoExecutionException("username and password must be specified to " + getCommand() + " a database");
		}
	}

	@Override
	protected void configureTransactions() throws MojoExecutionException {
		Properties properties = getContextProperties();
		properties.put("database", getDatabase());
		properties.put("databasePassword", getDatabasePassword());
		SQLGenerator generator = new SQLGenerator(properties, url, getCommand());
		try {
			generator.setEncoding(getEncoding());
			String sql = generator.getSQL();
			Transaction t = new Transaction();
			t.addText(sql);
			transactions.add(t);
		} catch (IOException e) {
			throw new MojoExecutionException("Error generating SQL", e);
		}
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String schema) {
		this.database = schema;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

}
