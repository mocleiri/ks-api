package org.kuali.student.core.organization.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.persistence.NoResultException;

import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.enumerable.dto.EnumeratedValue;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.organization.dao.OrganizationDao;
import org.kuali.student.core.organization.dto.OrgCriteria;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationCriteria;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationCriteria;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.entity.Org;
import org.kuali.student.core.organization.entity.OrgHierarchy;
import org.kuali.student.core.organization.entity.OrgOrgRelation;
import org.kuali.student.core.organization.entity.OrgOrgRelationType;
import org.kuali.student.core.organization.entity.OrgPersonRelation;
import org.kuali.student.core.organization.entity.OrgPersonRelationType;
import org.kuali.student.core.organization.entity.OrgPositionRestriction;
import org.kuali.student.core.organization.entity.OrgType;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResult;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.core.organization.service.OrganizationService", serviceName = "OrganizationService", portName = "OrganizationService", targetNamespace = "http://org.kuali.student/core/organization")
@Transactional
public class OrganizationServiceImpl implements OrganizationService {


	private OrganizationDao organizationDao;

	@Override
	public OrgPositionRestrictionInfo addPositionRestrictionToOrg(String orgId,
			String orgPersonRelationTypeKey,
			OrgPositionRestrictionInfo orgPositionRestrictionInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		//Check Missing params
		checkForMissingParameter(orgId, "orgId");
		checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");
		checkForMissingParameter(orgPositionRestrictionInfo, "orgPositionRestrictionInfo");

		//Set all the values on OrgOrgRelationInfo
		orgPositionRestrictionInfo.setOrgId(orgId);
		orgPositionRestrictionInfo.setOrgPersonRelationTypeKey(orgPersonRelationTypeKey);

		OrgPositionRestriction orgPositionRestriction = null;

		//Create a new persistence entity from the Info
		try {
			orgPositionRestriction = OrganizationAssembler.toOrgPositionRestriction(false, orgPositionRestrictionInfo, organizationDao);
		} catch (VersionMismatchException e) {
		}

		//Persist the positionRestriction
		organizationDao.create(orgPositionRestriction);

		//Copy back to an OrgOrgRelationInfo and return
		OrgPositionRestrictionInfo createdOrgPositionRestrictionInfo = OrganizationAssembler.toOrgPositionRestrictionInfo(orgPositionRestriction);
		return createdOrgPositionRestrictionInfo;
	}

	@Override
	public OrgOrgRelationInfo createOrgOrgRelation(String orgId,
			String relatedOrgId, String orgOrgRelationTypeKey,
			OrgOrgRelationInfo orgOrgRelationInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, PermissionDeniedException,
			OperationFailedException {

		//Check Missing params
		checkForMissingParameter(orgId, "orgId");
		checkForMissingParameter(relatedOrgId, "relatedOrgId");
		checkForMissingParameter(orgOrgRelationTypeKey, "orgOrgRelationTypeKey");
		checkForMissingParameter(orgOrgRelationInfo, "orgOrgRelationInfo");

		//Set all the values on OrgOrgRelationInfo
		orgOrgRelationInfo.setOrgId(orgId);
		orgOrgRelationInfo.setRelatedOrgId(relatedOrgId);
		orgOrgRelationInfo.setType(orgOrgRelationTypeKey);

		OrgOrgRelation orgOrgRelation = null;

		//Create a new persistence entity from the orgInfo
		try {
			orgOrgRelation = OrganizationAssembler.toOrgOrgRelation(false, orgOrgRelationInfo, organizationDao);
		} catch (VersionMismatchException e) {
		}

		//Persist the orgOrgRelation
		organizationDao.create(orgOrgRelation);

		//Copy back to an OrgOrgRelationInfo and return
		OrgOrgRelationInfo createdOrgOrgRelationInfo = OrganizationAssembler.toOrgOrgRelationInfo(orgOrgRelation);
		return createdOrgOrgRelationInfo;
	}

	@Override
	public OrgPersonRelationInfo createOrgPersonRelation(String orgId,
			String personId, String orgPersonRelationTypeKey,
			OrgPersonRelationInfo orgPersonRelationInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, PermissionDeniedException,
			OperationFailedException {

		//Check Missing params
		checkForMissingParameter(orgId, "orgId");
		checkForMissingParameter(personId, "personId");
		checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");
		checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");

		//Make sure that only valid org person relations are done
		if(!organizationDao.validatePositionRestriction(orgId,orgPersonRelationTypeKey)){
			throw new InvalidParameterException("There is no Position for this relationship");
		}

		//Set all the values on OrgOrgRelationInfo
		orgPersonRelationInfo.setOrgId(orgId);
		orgPersonRelationInfo.setPersonId(personId);
		orgPersonRelationInfo.setType(orgPersonRelationTypeKey);

		OrgPersonRelation orgPersonRelation = null;

		//Create a new persistence entity from the orgInfo
		try {
			orgPersonRelation = OrganizationAssembler.toOrgPersonRelation(false, orgPersonRelationInfo, organizationDao);
		} catch (VersionMismatchException e) {
		}

		//Persist the orgPersonRelation
		organizationDao.create(orgPersonRelation);

		//Copy back to an orgPersonRelationInfo and return
		OrgPersonRelationInfo createdOrgPersonRelationInfo = OrganizationAssembler.toOrgPersonRelationInfo(orgPersonRelation);
		return createdOrgPersonRelationInfo;
	}

	@Override
	public OrgInfo createOrganization(String orgTypeKey, OrgInfo orgInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		//Check Missing params
		checkForMissingParameter(orgTypeKey, "orgTypeKey");
		checkForMissingParameter(orgInfo, "orgInfo");

		//Set all the values on orgInfo
		orgInfo.setType(orgTypeKey);

		Org org = null;

		//Create a new persistence entity from the orgInfo
		try {
			org = OrganizationAssembler.toOrg(false, orgInfo, organizationDao);
		} catch (DoesNotExistException e) {
			//TODO DoesNotExistException exception should be thrown by the service?
		} catch (VersionMismatchException e) {
		}

		//Persist the org
		organizationDao.create(org);

		//Copy back to an orgInfo and return
		OrgInfo createdOrgInfo = OrganizationAssembler.toOrgInfo(org);
		return createdOrgInfo;

	}

	@Override
	public StatusInfo deleteOrganization(String orgId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		checkForMissingParameter(orgId, "orgId");

		Org org = organizationDao.fetch(Org.class, orgId);

		if(org==null){
			throw new DoesNotExistException("Org does not exist for id: "+orgId);
		}

		organizationDao.delete(org);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);
		return statusInfo;
	}

	@Override
	public List<String> getAllDescendants(String orgId, String orgHierarchy)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Exception Handling
		checkForMissingParameter(orgId, "orgId");
		checkForMissingParameter(orgId, "orgHierarchy");

		List<String> descendants = this.organizationDao.getAllDescendants(orgId, orgHierarchy);
		return descendants;
	}

	@Override
	public List<OrgPersonRelationInfo> getAllOrgPersonRelationsByOrg(
			String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(orgId, "orgId");

		return OrganizationAssembler.toOrgPersonRelationInfos(organizationDao.getAllOrgPersonRelationsByOrg(orgId));
	}

	@Override
	public List<OrgPersonRelationInfo> getAllOrgPersonRelationsByPerson(
			String personId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(personId, "personId");

		List<OrgPersonRelation> orgPersonRelationInfo = organizationDao.getAllOrgPersonRelationsByPerson(personId);
		return OrganizationAssembler.toOrgPersonRelationInfos(orgPersonRelationInfo);
	}

	@Override
	public List<String> getAncestors(String orgId, String orgHierarchy)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(orgId, "orgId");
		checkForMissingParameter(orgHierarchy, "orgHierarchy");

		List<String> ancestors = this.organizationDao.getAncestors(orgId, orgHierarchy);
		return ancestors;
	}

	@Override
	public List<OrgHierarchyInfo> getOrgHierarchies()
			throws OperationFailedException {
		//TODO flush out exceptions
		return OrganizationAssembler.toOrgHierarchyInfos(organizationDao.find(OrgHierarchy.class));

	}

	@Override
	public OrgHierarchyInfo getOrgHierarchy(String orgHierarchyKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(orgHierarchyKey, "orgHierarchyKey");

		return OrganizationAssembler.toOrgHierarchyInfo(organizationDao.fetch(OrgHierarchy.class, orgHierarchyKey));
	}

	@Override
	public OrgOrgRelationInfo getOrgOrgRelation(String orgOrgRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(orgOrgRelationId, "orgOrgRelationId");

		return OrganizationAssembler.toOrgOrgRelationInfo(organizationDao.fetch(OrgOrgRelation.class, orgOrgRelationId));

	}

	@Override
	public OrgOrgRelationTypeInfo getOrgOrgRelationType(
			String orgOrgRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(orgOrgRelationTypeKey, "orgOrgRelationTypeKey");

		return OrganizationAssembler.toOrgOrgRelationTypeInfo(organizationDao.fetch(OrgOrgRelationType.class, orgOrgRelationTypeKey));
	}

	@Override
	public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypes()
			throws OperationFailedException {
		List<OrgOrgRelationType> orgOrgRelationTypes = organizationDao.find(OrgOrgRelationType.class);
		return OrganizationAssembler.toOrgOrgRelationTypeInfos(orgOrgRelationTypes);
	}

	@Override
	public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypesForOrgHierarchy(
			String orgHierarchyKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(orgHierarchyKey, "orgHierarchyKey");

		List<OrgOrgRelationType> orgOrgRelationTypes = organizationDao.getOrgOrgRelationTypesForOrgHierarchy(orgHierarchyKey);
		return OrganizationAssembler.toOrgOrgRelationTypeInfos(orgOrgRelationTypes);
	}

	@Override
	public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypesForOrgType(
			String orgTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(orgTypeKey, "orgTypeKey");

		List<OrgOrgRelationType> orgOrgRelationTypes = organizationDao.getOrgOrgRelationTypesForOrgType(orgTypeKey);
		return OrganizationAssembler.toOrgOrgRelationTypeInfos(orgOrgRelationTypes);
	}

	@Override
	public List<OrgOrgRelationInfo> getOrgOrgRelationsByIdList(
			List<String> orgOrgRelationIdList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(orgOrgRelationIdList, "orgOrgRelationIdList");

		List<OrgOrgRelation> orgOrgRelations = organizationDao.getOrgOrgRelationsByIdList(orgOrgRelationIdList);
		return OrganizationAssembler.toOrgOrgRelationInfos(orgOrgRelations);
	}

	@Override
	public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Flush out exceptions
		checkForMissingParameter(orgId, "orgId");

		List<OrgOrgRelation> orgOrgRelations = organizationDao.getOrgOrgRelationsByOrg(orgId);
		return OrganizationAssembler.toOrgOrgRelationInfos(orgOrgRelations);
	}

	@Override
	public List<OrgOrgRelationInfo> getOrgOrgRelationsByRelatedOrg(
			String relatedOrgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(relatedOrgId, "relatedOrgId");

		List<OrgOrgRelation> orgOrgRelations = organizationDao.getOrgOrgRelationsByRelatedOrg(relatedOrgId);
		return OrganizationAssembler.toOrgOrgRelationInfos(orgOrgRelations);
	}

	@Override
	public OrgPersonRelationInfo getOrgPersonRelation(String orgPersonRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(orgPersonRelationId, "orgPersonRelationId");

		OrgPersonRelation opr = organizationDao.fetch(OrgPersonRelation.class, orgPersonRelationId);
		return OrganizationAssembler.toOrgPersonRelationInfo(opr);
	}

	@Override
	public OrgPersonRelationTypeInfo getOrgPersonRelationType(
			String orgPersonRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");

		OrgPersonRelationType oprt = organizationDao.fetch(OrgPersonRelationType.class, orgPersonRelationTypeKey);
		return OrganizationAssembler.toOrgPersonRelationTypeInfo(oprt);
	}

	@Override
	public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypes()
			throws OperationFailedException {
		List<OrgPersonRelationType> oprts = organizationDao.find(OrgPersonRelationType.class);
		return OrganizationAssembler.toOrgPersonRelationTypeInfos(oprts);
	}

	@Override
	public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypesForOrgType(
			String orgTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(orgTypeKey, "orgTypeKey");

		List<OrgPersonRelationType> oprts = organizationDao.getOrgPersonRelationTypesForOrgType(orgTypeKey);
		return OrganizationAssembler.toOrgPersonRelationTypeInfos(oprts);
	}

	@Override
	public List<OrgPersonRelationInfo> getOrgPersonRelationsByIdList(
			List<String> orgPersonRelationIdList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(orgPersonRelationIdList, "orgPersonRelationIdList");

		List<OrgPersonRelation> oprts = organizationDao.getOrgPersonRelationsByIdList(orgPersonRelationIdList);
		return OrganizationAssembler.toOrgPersonRelationInfos(oprts);
	}

	@Override
	public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(String orgId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(orgId, "orgId");

		List<OrgPersonRelation> relations = organizationDao.getAllOrgPersonRelationsByOrg(orgId);
		return OrganizationAssembler.toOrgPersonRelationInfos(relations);
	}

	@Override
	public List<OrgPersonRelationInfo> getOrgPersonRelationsByPerson(
			String personId, String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(personId, "personId");
		checkForMissingParameter(orgId, "orgId");

		List<OrgPersonRelation> oprts = organizationDao.getOrgPersonRelationsByPerson(personId, orgId);
		return OrganizationAssembler.toOrgPersonRelationInfos(oprts);
	}

	@Override
	public OrgTypeInfo getOrgType(String orgTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(orgTypeKey, "orgTypeKey");

		return OrganizationAssembler.toOrgTypeInfo(organizationDao.fetch(OrgType.class, orgTypeKey));
	}

	@Override
	public List<OrgTypeInfo> getOrgTypes() throws OperationFailedException {
		return OrganizationAssembler.toOrgTypeInfos(organizationDao.find(OrgType.class));
	}

	@Override
	public OrgInfo getOrganization(String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(orgId, "orgId");

		return OrganizationAssembler.toOrgInfo(organizationDao.fetch(Org.class, orgId));
	}

	@Override
	public List<OrgInfo> getOrganizationsByIdList(List<String> orgIdList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(orgIdList, "orgIdList");

		List<Org> orgs = this.organizationDao.getOrganizationsByIdList(orgIdList);
		return OrganizationAssembler.toOrgInfos(orgs);
	}

	@Override
	public List<String> getPersonIdsForOrgByRelationType(String orgId,
			String orgPersonRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(orgId, "orgId");
		checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");

		return organizationDao.getPersonIdsForOrgByRelationType(orgId, orgPersonRelationTypeKey);
	}

	@Override
	public List<OrgPositionRestrictionInfo> getPositionRestrictionsByOrg(
			String orgId) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, PermissionDeniedException,
			OperationFailedException {
		checkForMissingParameter(orgId, "orgId");

		List<OrgPositionRestriction> restrictions = organizationDao.getPositionRestrictionsByOrg(orgId);
		return OrganizationAssembler.toOrgPositionRestrictionInfos(restrictions);
	}

	@Override
	public Boolean hasOrgOrgRelation(String orgId, String comparisonOrgId,
			String orgOrgRelationTypeKey) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		//Check Missing params
		checkForMissingParameter(orgId, "orgId");
		checkForMissingParameter(comparisonOrgId, "comparisonOrgId");
		checkForMissingParameter(orgOrgRelationTypeKey, "orgOrgRelationTypeKey");

		boolean result = organizationDao.hasOrgOrgRelation(orgId, comparisonOrgId,
				orgOrgRelationTypeKey);
		return new Boolean(result);
	}

	@Override
	public Boolean hasOrgPersonRelation(String orgId, String personId,
			String orgPersonRelationTypeKey) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(orgId, "orgId");
		checkForMissingParameter(personId, "personId");
		checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");

		return organizationDao.hasOrgPersonRelation(orgId, personId, orgPersonRelationTypeKey);
	}

	@Override
	public Boolean isDescendant(String orgId, String descendantOrgId,
			String orgHierarchy) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(orgId, "orgId");
		checkForMissingParameter(descendantOrgId, "descendantOrgId");
		checkForMissingParameter(orgHierarchy, "orgHierarchy");
		
		// get ancestors of the descendant, as it will be more efficient in most cases
		List<String> ancestors = organizationDao.getAncestors(descendantOrgId, orgHierarchy);
		boolean result = ancestors.contains(orgId);
		return new Boolean(result);
	}

	@Override
	public StatusInfo removeOrgOrgRelation(String orgOrgRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(orgOrgRelationId, "orgOrgRelationId");

		organizationDao.delete(OrgOrgRelation.class, orgOrgRelationId);
		return new StatusInfo();
	}

	@Override
	public StatusInfo removeOrgPersonRelation(String orgPersonRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(orgPersonRelationId, "orgPersonRelationId");

		organizationDao.delete(OrgPersonRelation.class, orgPersonRelationId);
		return new StatusInfo();
	}

	@Override
	public StatusInfo removePositionRestrictionFromOrg(String orgId,
			String orgPersonRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(orgId, "orgId");
		checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");
		OrgPositionRestriction opr = null;
		try {
			opr = organizationDao.getPositionRestrictionByOrgAndPersonRelationTypeKey(orgId, orgPersonRelationTypeKey);
			if (opr == null) {
				throw new DoesNotExistException();
			}
		} catch (NoResultException e) {
			throw new DoesNotExistException();
		}
		organizationDao.delete(opr);
		return new StatusInfo();
	}

	@Override
	public List<String> searchForOrgOrgRelations(
			OrgOrgRelationCriteria orgOrgRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		checkForMissingParameter(orgOrgRelationCriteria, "orgOrgRelationCriteria");

		return null;
	}

	@Override
	public List<String> searchForOrgPersonRelations(
			OrgPersonRelationCriteria orgPersonRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		checkForMissingParameter(orgPersonRelationCriteria, "orgPersonRelationCriteria");

		return null;
	}

	@Override
	public List<String> searchForOrganizations(OrgCriteria orgCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		checkForMissingParameter(orgCriteria, "orgCriteria");

		return null;
	}

	@Override
	public List<Result> searchForResults(String searchTypeKey,
			List<QueryParamValue> queryParamValues)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		checkForMissingParameter(searchTypeKey, "searchTypeKey");
		checkForMissingParameter(queryParamValues, "queryParamValues");

		return null;
	}

	@Override
	public OrgOrgRelationInfo updateOrgOrgRelation(String orgOrgRelationId,
			OrgOrgRelationInfo orgOrgRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		//Check Missing params
		checkForMissingParameter(orgOrgRelationId, "orgOrgRelationId");
		checkForMissingParameter(orgOrgRelationId, "orgOrgRelationId");

		//Set all the values on OrgOrgRelationInfo
		orgOrgRelationInfo.setId(orgOrgRelationId);

		OrgOrgRelation orgOrgRelation = null;

		//Update the persistence entity from the Info
		orgOrgRelation = OrganizationAssembler.toOrgOrgRelation(true, orgOrgRelationInfo, organizationDao);

		//Update the orgOrgRelation
		OrgOrgRelation updatedOrgOrgRelation = organizationDao.update(orgOrgRelation);

		//Copy back to an OrgOrgRelationInfo and return
		OrgOrgRelationInfo updatedOrgOrgRelationInfo = OrganizationAssembler.toOrgOrgRelationInfo(updatedOrgOrgRelation);
		return updatedOrgOrgRelationInfo;

	}

	@Override
	public OrgPersonRelationInfo updateOrgPersonRelation(
			String orgPersonRelationId,
			OrgPersonRelationInfo orgPersonRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		//Check Missing params
		checkForMissingParameter(orgPersonRelationId, "orgPersonRelationId");
		checkForMissingParameter(orgPersonRelationInfo, "orgPersonRelationInfo");

		//Make sure that only valid org person relations are done
		if(!organizationDao.validatePositionRestriction(orgPersonRelationInfo.getOrgId(),orgPersonRelationInfo.getType())){
			throw new InvalidParameterException("There is no Position for this relationship");
		}

		//Set all the values on OrgPersonRelationInfo
		orgPersonRelationInfo.setId(orgPersonRelationId);

		OrgPersonRelation orgPersonRelation = null;

		//Update persistence entity from the orgInfo
		orgPersonRelation = OrganizationAssembler.toOrgPersonRelation(true, orgPersonRelationInfo, organizationDao);

		//Update the orgPersonRelation
		orgPersonRelation = organizationDao.update(orgPersonRelation);

		//Copy back to an orgPersonRelationInfo and return
		OrgPersonRelationInfo createdOrgPersonRelationInfo = OrganizationAssembler.toOrgPersonRelationInfo(orgPersonRelation);
		return createdOrgPersonRelationInfo;
	}

	@Override
	public OrgInfo updateOrganization(String orgId, OrgInfo orgInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {

		//Check Missing params
		checkForMissingParameter(orgId, "orgId");
		checkForMissingParameter(orgInfo, "orgInfo");

		//Set all the values on orgInfo
		orgInfo.setId(orgId);

		Org org = null;

		//Update persistence entity from the orgInfo
		org = OrganizationAssembler.toOrg(true, orgInfo, organizationDao);

		//Update the org
		Org updatedOrg = organizationDao.update(org);

		//Copy back to an orgInfo and return
		OrgInfo updatedOrgInfo = OrganizationAssembler.toOrgInfo(updatedOrg);
		return updatedOrgInfo;

	}

	@Override
	public OrgPositionRestrictionInfo updatePositionRestrictionForOrg(
			String orgId, String orgPersonRelationTypeKey,
			OrgPositionRestrictionInfo orgPositionRestrictionInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {

		//Check Missing params
		checkForMissingParameter(orgId, "orgId");
		checkForMissingParameter(orgPersonRelationTypeKey, "orgPersonRelationTypeKey");
		checkForMissingParameter(orgPositionRestrictionInfo, "orgPositionRestrictionInfo");

		//Set all the values on OrgOrgRelationInfo
		orgPositionRestrictionInfo.setOrgId(orgId);
		orgPositionRestrictionInfo.setOrgPersonRelationTypeKey(orgPersonRelationTypeKey);

		OrgPositionRestriction orgPositionRestriction = null;

		//Update persistence entity from the Info
		orgPositionRestriction = OrganizationAssembler.toOrgPositionRestriction(true, orgPositionRestrictionInfo, organizationDao);

		//Update the positionRestriction
		OrgPositionRestriction updated = organizationDao.update(orgPositionRestriction);

		//Copy back to an OrgOrgRelationInfo and return
		OrgPositionRestrictionInfo updatedOrgPositionRestrictionInfo = OrganizationAssembler.toOrgPositionRestrictionInfo(updated);
		return updatedOrgPositionRestrictionInfo;
	}

	@Override
	public List<ValidationResult> validateOrg(String validationType,
			OrgInfo orgInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(orgInfo, "orgInfo");

		return null;
	}

	@Override
	public List<ValidationResult> validateOrgOrgRelation(String validationType,
			OrgOrgRelationInfo orgOrgRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(orgOrgRelationInfo, "orgOrgRelationInfo");

		return null;
	}

	@Override
	public List<ValidationResult> validateOrgPersonRelation(
			String validationType, OrgPersonRelationInfo orgPersonRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		checkForMissingParameter(validationType, "validationType");

		return null;
	}

	@Override
	public List<ValidationResult> validateOrgPositionRestriction(
			String validationType,
			OrgPositionRestrictionInfo orgPositionRestrictionInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(orgPositionRestrictionInfo, "orgPositionRestrictionInfo");

		return null;
	}

	@Override
	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
//		checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
//		
//		JAXBContext context;
//		try {
//			context = JAXBContext.newInstance(SearchTypeInfo.class);
//			Unmarshaller unmarshaller = context.createUnmarshaller();
//			ClassPathResource cpr = new ClassPathResource("organization-searchTypeInfo.xml");
//			SearchTypeInfo s = (SearchTypeInfo)unmarshaller.unmarshal(cpr.getFile());
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
		return null;
	}

	@Override
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");

		return null;
	}

	@Override
	public List<SearchResultTypeInfo> getSearchResultTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchTypeInfo getSearchType(String searchTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		checkForMissingParameter(searchTypeKey, "searchTypeKey");

		return null;
	}

	@Override
	public List<SearchTypeInfo> getSearchTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");

		return null;
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");

		return null;
	}

	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	@Override
	public List<EnumeratedValue> getEnumeration(String enumerationKey,
			String enumContextKey, String contextValue, Date contextDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectStructure getObjectStructure(String objectTypeKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getObjectTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateObject(String objectTypeKey, String stateKey,
			String info) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateStructureData(String objectTypeKey, String stateKey,
			String info) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<OrgTreeInfo> getOrgTree(String rootOrgId,
			String orgHierarchyId, int maxLevels) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(rootOrgId, "rootOrgId");
		checkForMissingParameter(orgHierarchyId, "orgHierarchyId");

		List<OrgTreeInfo> results = new ArrayList<OrgTreeInfo>();
		Org rootOrg = organizationDao.fetch(Org.class, rootOrgId);
		results.add(new OrgTreeInfo(rootOrgId,null,rootOrg.getLongName()));
		results.addAll(parseOrgTree(rootOrgId, orgHierarchyId, maxLevels,0));
		return results;
	}

	private List<OrgTreeInfo> parseOrgTree(String rootOrgId,
			String orgHierarchyId, int maxLevels, int currentLevel) {
		List<OrgTreeInfo> results = new ArrayList<OrgTreeInfo>();
		if(maxLevels==0||currentLevel<maxLevels){
			List<OrgTreeInfo> orgTreeInfos = this.organizationDao.getOrgTreeInfo(rootOrgId,orgHierarchyId);
			for(OrgTreeInfo orgTreeInfo:orgTreeInfos){
				results.addAll(parseOrgTree(orgTreeInfo.getOrgId(),orgHierarchyId, maxLevels, currentLevel+1));
			}
			results.addAll(orgTreeInfos);
		}
		return results;
	}

	/**
	 * Check for missing parameter and thow localized exception if missing
	 *
	 * @param param
	 * @param parameter name
	 * @throws MissingParameterException
	 */
	private void checkForMissingParameter(Object param, String paramName)
			throws MissingParameterException {
		if (param == null) {
			throw new MissingParameterException(paramName + " can not be null");
		}
	}


}
