package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.util.DefaultProjectContext;
import org.kuali.common.util.ProjectUtils;

public class InitializeSourceDbProjectContext extends DefaultProjectContext {

	public InitializeSourceDbProjectContext() {
		super(ProjectConstants.GROUP_ID, ProjectConstants.ARTIFACT_ID);
	}

	@Override
	public List<String> getPropertyLocations() {
		String prefix = ProjectUtils.getClassPathPrefix(ProjectConstants.GROUP_ID_BASE, ProjectConstants.KS_SOURCE_DB);
		List<String> list = new ArrayList<String>();
		list.add(prefix + "/common.properties");
		list.add(prefix + "/initialize.properties");
		return list;
	}

}
