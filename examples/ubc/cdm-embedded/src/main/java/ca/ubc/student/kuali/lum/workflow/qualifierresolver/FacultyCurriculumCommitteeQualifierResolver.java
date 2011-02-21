package ca.ubc.student.kuali.lum.workflow.qualifierresolver;

import java.util.List;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.lum.workflow.qualifierresolver.AbstractCocOrgQualifierResolver;

public class FacultyCurriculumCommitteeQualifierResolver extends AbstractCocOrgQualifierResolver{

	@Override
	public List<AttributeSet> resolve(RouteContext routeContext) {
		List<AttributeSet> attributeSets = super.resolve(routeContext);
		String orgId = null;
		if (attributeSets.size() > 0 && attributeSets.get(0).size() > 0) {
			orgId = getAttribute(attributeSets, KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
		}
		return cocAttributeSetsFromAncestors(orgId,"kuali.org.Faculty","faculty","facultyId");
	}
}


