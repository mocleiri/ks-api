package org.kuali.student.lum.workflow.qualifierresolver;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;

public class SenateCommitteeQualifierResolver extends
		AbstractOrgQualifierResolver {
	@Override
	public List<AttributeSet> resolve(RouteContext routeContext) {
		List<AttributeSet> returnAttrSetList = new ArrayList<AttributeSet>();
		AttributeSet attributeSet = new AttributeSet();
		attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG, "COC");
		attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG_ID, "141");
		returnAttrSetList.add(attributeSet);
		return returnAttrSetList;
	}
}
