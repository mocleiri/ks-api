package org.kuali.db;

import static org.apache.commons.lang.StringUtils.*;
import static org.kuali.db.DatabaseType.*;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 *
 */
public class DatabaseUtils {

	ApplicationContext context = new ClassPathXmlApplicationContext("org/kuali/db/spring-config.xml");

	@SuppressWarnings("unchecked")
	List<JDBCConfig> databaseConfigs = (List<JDBCConfig>) context.getBean("databaseConfigs");

	/**
	 * Given a JDBC url, this tries to locate the corresponding DatabaseConfig object
	 * 
	 * @param url
	 *            jdbc url
	 * @return DatabaseConfig
	 */
	public JDBCConfig getDatabaseConfig(String url) {
		Validate.isTrue(isNotEmpty(url));

		for (JDBCConfig databaseConfig : databaseConfigs) {
			String urlFragment = databaseConfig.getUrlFragment();
			if (url.contains(urlFragment)) {
				return databaseConfig;
			}
		}
		return JDBCConfig.UNKNOWN_CONFIG;
	}

	/**
	 * Given a JDBC connection URL, extract only the database name.
	 * 
	 * @param url
	 *            a JDBC connection URL
	 * @return the database name
	 */
	public String getDatabaseName(String url) {
		int leftIndex = url.lastIndexOf("/");
		if (leftIndex == -1) {
			leftIndex = url.lastIndexOf(":");
		}
		leftIndex++;

		int rightIndex = url.length();
		if (url.indexOf("?") != -1) {
			rightIndex = url.indexOf("?");
		} else if (url.indexOf(";") != -1) {
			rightIndex = url.indexOf(";");
		}

		return url.substring(leftIndex, rightIndex);
	}

	/**
	 * Given a JDBC connection URL, generate a new connection URL to connect directly to the database server itself (ie:
	 * no database specified).
	 * 
	 * @param url
	 *            a JDBC connection URL
	 * @return a new JDBC connection URL to connect directly to the database server
	 */
	public String getServerUrl(String url) {
		int rightIndex = url.length();
		if (url.lastIndexOf("/") != -1) {
			rightIndex = url.lastIndexOf("/");
		} else if (url.lastIndexOf(":") != -1) {
			rightIndex = url.lastIndexOf(":");
		}

		String baseUrl = url.substring(0, rightIndex);

		// TODO This next line is pretty ugly, but it works for nearly every postgresql server.
		// If we have to add another exception to this for another database server, then I highly recommend refactoring
		// this to a more elegant solution.
		if (POSTGRESQL.equals(getDatabaseConfig(url).getType())) {
			baseUrl += "/postgres";
		}

		String options = "";
		int optionsIndex = url.indexOf("?");
		if (optionsIndex == -1) {
			optionsIndex = url.indexOf(";");
		}
		if (optionsIndex != -1) {
			options = url.substring(optionsIndex);
		}

		return baseUrl + options;
	}
}
