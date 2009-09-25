/**
 * 
 */
package org.kuali.student.lum.kim.permission.role;

import java.util.List;

import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.role.service.impl.RouteLogDerivedRoleTypeServiceImpl;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.bo.impl.KimAttributes;
import org.kuali.rice.kim.bo.role.dto.RoleMembershipInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;

/**
 * @author delyea
 *
 */
public class KSRouteLogDerivedRoleTypeServiceImpl extends RouteLogDerivedRoleTypeServiceImpl {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(KSRouteLogDerivedRoleTypeServiceImpl.class);

	protected void addDocumentNumberToQualification(AttributeSet qualification) {
		// if qualification already has document id then no need to look it up
		if (!qualification.containsKey(KimAttributes.DOCUMENT_NUMBER)) {
			// document id is not contained inside qualification so look it up using clu id and document type name
			if (!qualification.containsKey(KualiStudentKimAttributes.QUALIFICATION_CLU_ID)) {
				throw new RuntimeException("Cannot find qualification for key '" + KualiStudentKimAttributes.QUALIFICATION_CLU_ID + "'");
			}
			try {
				String documentTypeName = qualification.get(KimAttributes.DOCUMENT_TYPE_NAME);
				String appId = qualification.get(KualiStudentKimAttributes.QUALIFICATION_CLU_ID);
				DocumentDetailDTO docDetail = KEWServiceLocator.getWorkflowUtilityService().getDocumentDetailFromAppId(documentTypeName, appId);
				if (docDetail == null) {
					throw new RuntimeException("No valid document instance found for document type name '" + documentTypeName + "' and Application Id '" + appId + "'");
				}
				qualification.put(KimAttributes.DOCUMENT_NUMBER, Long.toString(docDetail.getRouteHeaderId()));
			} catch (WorkflowException e) {
				String errorMessage = "Workflow Exception: " + e.getLocalizedMessage();
				LOG.error(errorMessage, e);
				throw new RuntimeException(e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.role.service.impl.RouteLogDerivedRoleTypeServiceImpl#getRoleMembersFromApplicationRole(java.lang.String, java.lang.String, org.kuali.rice.kim.bo.types.dto.AttributeSet)
	 */
	@Override
	public List<RoleMembershipInfo> getRoleMembersFromApplicationRole(
			String namespaceCode, String roleName, AttributeSet qualification) {
		addDocumentNumberToQualification(qualification);
		return super.getRoleMembersFromApplicationRole(namespaceCode, roleName,
				qualification);
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.role.service.impl.RouteLogDerivedRoleTypeServiceImpl#hasApplicationRole(java.lang.String, java.util.List, java.lang.String, java.lang.String, org.kuali.rice.kim.bo.types.dto.AttributeSet)
	 */
	@Override
	public boolean hasApplicationRole(String principalId,
			List<String> groupIds, String namespaceCode, String roleName,
			AttributeSet qualification) {
		addDocumentNumberToQualification(qualification);
		return super.hasApplicationRole(principalId, groupIds, namespaceCode, roleName,
				qualification);
	}

}
