package org.apache.torque.mojo;

import java.io.IOException;
import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.db.DatabaseCommand;
import org.kuali.db.SQLGenerator;
import org.kuali.db.Transaction;

/**
 * Runs a command that performs a single operation on a database (create,drop etc)
 */
public abstract class SingleDBACommandMojo extends AbstractDBACommandMojo {

	public abstract DatabaseCommand getCommand();

	@Override
	protected void configureTransactions() throws MojoExecutionException {
		Properties properties = getContextProperties();
		SQLGenerator generator = new SQLGenerator(properties, url, getCommand());
		try {
			generator.setEncoding(getEncoding());
			String sql = generator.getSQL();
			Transaction t = new Transaction();
			t.addText(sql);
			t.setDescription(getTransactionDescription(getCommand()));
			transactions.add(t);
		} catch (IOException e) {
			throw new MojoExecutionException("Error configuring transactions", e);
		}
	}
}
