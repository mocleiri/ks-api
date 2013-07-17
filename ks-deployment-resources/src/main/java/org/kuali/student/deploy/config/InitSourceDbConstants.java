package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.jdbc.config.JdbcConfigConstants;
import org.kuali.common.util.Str;
import org.kuali.common.util.metainf.MetaInfConfigConstants;
import org.kuali.student.deploy.spring.DeployProjectConstants;

public class InitSourceDbConstants {

	// Shorthand for GroupId + ArtifactId
	private static final String GA = DeployProjectConstants.GROUP_ID + ":" + DeployProjectConstants.ARTIFACT_ID;

	// Config related to initializing something
	// TODO Move to kuali-impex-export?
	public static final String INITIALIZE = "initialize";

	// Config related to initializing a source database
	// TODO Move to kuali-impex-export?
	public static final String INIT_SOURCE_DB_CONTEXT_ID = Str.getId(SourceDbConstants.SOURCE_DB, INITIALIZE);

	// Fully qualified config id related to initializing KS_SOURCE_DB
	public static final String INIT_SOURCE_DB_CONFIG_ID = Str.getId(GA, INIT_SOURCE_DB_CONTEXT_ID);

	// The complete list of configId's needed to initialize KS_SOURCE_DB
	public static final List<String> INIT_SOURCE_DB_CONFIG_IDS = getInitSourceDbConfigIds();

	protected static List<String> getInitSourceDbConfigIds() {
		List<String> ids = new ArrayList<String>();

		// We are connecting to a database so we need whatever it is JDBC needs
		ids.addAll(JdbcConfigConstants.CONFIG_IDS);

		// Re-use properties from org.kuali.common:kuali-util:metainf:sql that helped create the .resources files
		ids.addAll(MetaInfConfigConstants.SQL_CONFIG_IDS);

		// KS specific config for connecting to Amazon RDS
		ids.add(INIT_SOURCE_DB_CONFIG_ID);
		return Collections.unmodifiableList(ids);
	}

}
