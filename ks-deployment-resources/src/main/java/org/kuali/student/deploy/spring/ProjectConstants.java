package org.kuali.student.deploy.spring;

import org.kuali.common.util.ProjectUtils;

public abstract class ProjectConstants {

	public static final String GROUP_ID = org.kuali.common.util.ProjectConstants.STUDENT_GROUP_ID;
	public static final String ARTIFACT_ID = "ks-deployment-resources";
	public static final String SOURCE_DB_ARTIFACT_ID = "ks-source-db";

	@Deprecated
	public static final String GROUP_ID_BASE = GROUP_ID;
	public static final String KS_SOURCE_DB = "ks-source-db";
	public static final String KS_SOURCE_DB_RESOURCE_PREFIX = ProjectUtils.getClassPathPrefix(GROUP_ID, ProjectConstants.KS_SOURCE_DB);
	public static final String KS_SOURCE_DB_COMMON = KS_SOURCE_DB_RESOURCE_PREFIX + "/common.properties";

}
