package org.kuali.student.lum.workflow.qualifierresolver;

import java.util.List;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.core.search.dto.Result;

public class DepartmentCommitteeQualifierResolver extends
		AbstractOrgQualifierResolver {


	@Override
	public List<AttributeSet> resolve(RouteContext routeContext) {
		List<AttributeSet> attributeSets = super.resolve(routeContext);
		String orgId = null;
		if (attributeSets.size() > 0 && attributeSets.get(0).size() > 0) {
			orgId = getAttribute(attributeSets, KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
		}
		List<Result> results = relatedOrgsFromOrgId(orgId,KUALI_ORG_TYPE_CURRICULUM_CHILD,KUALI_ORG_COC);
		return attributeSetFromSearchResult(results,KualiStudentKimAttributes.QUALIFICATION_DEPARTMENT,KualiStudentKimAttributes.QUALIFICATION_DEPARTMENT_ID);
	}



}
