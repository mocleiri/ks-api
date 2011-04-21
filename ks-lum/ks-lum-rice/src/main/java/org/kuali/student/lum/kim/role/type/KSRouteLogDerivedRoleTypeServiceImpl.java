/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.kim.role.type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.xml.dto.AttributeSet;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kim.bo.Role;
import org.kuali.rice.kim.bo.role.dto.RoleMembershipInfo;
import org.kuali.rice.kim.service.support.impl.KimDerivedRoleTypeServiceBase;
import org.kuali.rice.kim.util.KimConstants;
import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.lum.kim.KimQualificationHelper;

/**
 *
 */
public class KSRouteLogDerivedRoleTypeServiceImpl extends KimDerivedRoleTypeServiceBase {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(KSRouteLogDerivedRoleTypeServiceImpl.class);

    public static final String INITIATOR_ROLE_NAME = "Initiator";
    public static final String INITIATOR_OR_REVIEWER_ROLE_NAME = "Initiator or Reviewer";
    public static final String ROUTER_ROLE_NAME = "Router";

    private boolean checkFutureRequests = false;
	protected Set<List<String>> newRequiredAttributes = new HashSet<List<String>>();

	{
		checkRequiredAttributes = true;
        // add document number as one required attribute set
		List<String> listOne = new ArrayList<String>();
		listOne.add( KimConstants.AttributeConstants.DOCUMENT_NUMBER );
		newRequiredAttributes.add(listOne);
        // add document type name and KEW application id as one required attribute set
		List<String> listTwo = new ArrayList<String>();
		listTwo.add( KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME );
		listTwo.add( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID );
		newRequiredAttributes.add(listTwo);
        // add object id and object type as one required attribute set
		List<String> listThree = new ArrayList<String>();
		listThree.add( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID );
		listThree.add( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_TYPE );
		newRequiredAttributes.add(listThree);
        // add each proposal reference type as a required attribute set
        for (String proposalReferenceType : StudentIdentityConstants.QUALIFICATION_PROPOSAL_ID_REF_TYPES) {
            List<String> tempList = new ArrayList<String>();
            tempList.add( proposalReferenceType );
            newRequiredAttributes.add(tempList);
        }
	}

	/** 
	 * The part about where the receivedAttributes list being empty does not return errors is copied from Rice base class.
	 * 
	 * @see org.kuali.rice.kim.service.support.impl.KimTypeServiceBase#validateRequiredAttributesAgainstReceived(org.kuali.rice.kim.bo.types.dto.AttributeSet)
	 **/
	@Override
	protected void validateRequiredAttributesAgainstReceived(AttributeSet receivedAttributes){
		KimQualificationHelper.validateRequiredAttributesAgainstReceived(newRequiredAttributes, receivedAttributes, isCheckFutureRequests(), COMMA_SEPARATOR);
        super.validateRequiredAttributesAgainstReceived(receivedAttributes);
	}

    @Override
    public AttributeSet translateInputAttributeSet(AttributeSet qualification) {
        return KimQualificationHelper.translateInputAttributeSet(super.translateInputAttributeSet(qualification));
    }

	protected Long getDocumentNumber(AttributeSet qualification) throws WorkflowException {
		// first check for a valid document id passed in
		String documentId = qualification.get( KimConstants.AttributeConstants.DOCUMENT_NUMBER );
        if (StringUtils.isNotEmpty(documentId)) {
            return Long.valueOf(documentId);
        } else {
            LOG.warn("Could not find workflow document id in qualification list:");
            LOG.warn(qualification.formattedDump(20));
            return null;
        }
//		if (StringUtils.isNotEmpty(documentId)) {
//			return Long.valueOf(documentId);
//		}
//		// if no document id passed in get the document via the id and document type name
//		String documentTypeName = qualification.get( KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME );
//		if (StringUtils.isEmpty(documentTypeName)) {
//			String ksObjectType = qualification.get( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_TYPE );
//			if (StringUtils.equals(ksObjectType, "referenceType.clu.proposal")) {
//	            documentTypeName = "kuali.proposal.type.course.create";
//			}
//		}
//		String appId = qualification.get( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID );
//		LOG.info("Checking for document id using document type '" + documentTypeName + "' and application id '" + appId + "' with qualifications: " + qualification.toString());
//		DocumentDetailDTO docDetail = getWorkflowUtility().getDocumentDetailFromAppId(documentTypeName, appId);
//		if (docDetail == null) {
//			throw new RuntimeException("No valid document instance found for document type name '" + documentTypeName + "' and Application Id '" + appId + "'");
//		}
//		return docDetail.getRouteHeaderId();
	}

	public boolean isCheckFutureRequests() {
		return checkFutureRequests;
	}

	public void setCheckFutureRequests(boolean checkFutureRequests) {
		this.checkFutureRequests = checkFutureRequests;
	}

	/**
	 *	- qualifier is document number
	 *	- the roles that will be of this type are KR-WKFLW Initiator and KR-WKFLW Initiator or Reviewer, KR-WKFLW Router
	 *	- only the initiator of the document in question gets the KR-WKFLW Initiator role
	 *	- user who routed the document according to the route log should get the KR-WKFLW Router role
	 *	- users who are authorized by the route log, 
	 *		i.e. initiators, people who have taken action, people with a pending action request, 
	 *		or people who will receive an action request for the document in question get the KR-WKFLW Initiator or Reviewer Role 
	 * 
	 * @see org.kuali.rice.kim.service.support.impl.KimRoleTypeServiceBase#getRoleMembersFromApplicationRole(String, String, AttributeSet)
	 */
	@Override
    public List<RoleMembershipInfo> getRoleMembersFromApplicationRole(String namespaceCode, String roleName, AttributeSet paramQualification) {
		List<RoleMembershipInfo> members = new ArrayList<RoleMembershipInfo>();
		validateRequiredAttributesAgainstReceived(paramQualification);
		AttributeSet qualification = translateInputAttributeSet(paramQualification);
		Long documentNumber = null;
		try {
			documentNumber = getDocumentNumber(qualification);
			if (documentNumber != null) {
				if (INITIATOR_ROLE_NAME.equals(roleName)) {
				    String principalId = getWorkflowUtility().getDocumentInitiatorPrincipalId(documentNumber);
	                members.add( new RoleMembershipInfo(null/*roleId*/, null, principalId, Role.PRINCIPAL_MEMBER_TYPE, null) );
				} else if(INITIATOR_OR_REVIEWER_ROLE_NAME.equals(roleName)) {
					String[] ids = getWorkflowUtility().getPrincipalIdsInRouteLog(documentNumber, isCheckFutureRequests());
					if (ids != null) {
					    for ( String id : ids ) {
					    	if ( StringUtils.isNotBlank(id) ) {
					    		members.add( new RoleMembershipInfo(null/*roleId*/, null, id, Role.PRINCIPAL_MEMBER_TYPE, null) );
					    	}
					    }
					}
				} else if(ROUTER_ROLE_NAME.equals(roleName)) {
				    String principalId = getWorkflowUtility().getDocumentRoutedByPrincipalId(documentNumber);
	                members.add( new RoleMembershipInfo(null/*roleId*/, null, principalId, Role.PRINCIPAL_MEMBER_TYPE, null) );
				}
			}
		} catch (WorkflowException wex) {
			LOG.error("Workflow Error: " + wex.getLocalizedMessage(),wex);
			throw new RuntimeException("Error in getting principal Ids in route log for document number: "+documentNumber+" :"+wex.getLocalizedMessage(),wex);
		}
		return members;
	}

	/***
	 * @see org.kuali.rice.kim.service.support.impl.KimRoleTypeServiceBase#hasApplicationRole(java.lang.String, java.util.List, java.lang.String, java.lang.String, org.kuali.rice.kim.bo.types.dto.AttributeSet)
	 */
	@Override
	public boolean hasApplicationRole(
			String principalId, List<String> groupIds, String namespaceCode, String roleName, AttributeSet paramQualification){
        validateRequiredAttributesAgainstReceived(paramQualification);
        AttributeSet qualification = translateInputAttributeSet(paramQualification);
        boolean isUserInRouteLog = false;
		Long documentNumber = null;
		try {
			documentNumber = getDocumentNumber(qualification);
			if (documentNumber != null) {
				if (INITIATOR_ROLE_NAME.equals(roleName)){
					isUserInRouteLog = principalId.equals(getWorkflowUtility().getDocumentInitiatorPrincipalId(documentNumber));
				} else if(INITIATOR_OR_REVIEWER_ROLE_NAME.equals(roleName)){
					isUserInRouteLog = getWorkflowUtility().isUserInRouteLog(documentNumber, principalId, isCheckFutureRequests());
				} else if(ROUTER_ROLE_NAME.equals(roleName)){
					isUserInRouteLog = principalId.equals(getWorkflowUtility().getDocumentRoutedByPrincipalId(documentNumber));
				}
			}
		} catch (WorkflowException wex) {
			LOG.error("Workflow Error: " + wex.getLocalizedMessage(),wex);
			throw new RuntimeException("Error in determining whether the principal Id: "+principalId+" is in route log for document number: "+documentNumber+" :"+wex.getLocalizedMessage(),wex);
		}
        return isUserInRouteLog;
	}

	/**
	 * Returns false, as the Route Log changes often enough that role membership is highly volatile
	 * 
	 * @see org.kuali.rice.kim.service.support.impl.KimRoleTypeServiceBase#shouldCacheRoleMembershipResults(java.lang.String, java.lang.String)
	 */
//	@Override
	public boolean shouldCacheRoleMembershipResults(String namespaceCode, String roleName) {
		return false;
	}

	protected WorkflowUtility getWorkflowUtility() {
		return KEWServiceLocator.getWorkflowUtilityService();
	}

}
