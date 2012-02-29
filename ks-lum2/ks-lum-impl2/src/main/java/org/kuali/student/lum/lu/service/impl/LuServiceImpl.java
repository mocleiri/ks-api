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

package org.kuali.student.lum.lu.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jws.WebService;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r1.common.entity.Amount;
import org.kuali.student.r1.common.entity.TimeAmount;
import org.kuali.student.r1.common.entity.Version;
import org.kuali.student.r1.common.entity.VersionEntity;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r1.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchParam;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.r1.common.search.dto.SearchResultRow;
import org.kuali.student.r1.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchTypeInfo;
import org.kuali.student.r1.common.search.service.SearchDispatcher;
import org.kuali.student.r1.common.search.service.SearchManager;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.validator.Validator;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.r2.lum.clu.dto.AccreditationInfo;
import org.kuali.student.r2.lum.clu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluFeeRecordInfo;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.clu.dto.CluLoRelationInfo;
import org.kuali.student.r1.lum.lu.dto.CluLoRelationTypeInfo;
import org.kuali.student.r2.lum.clu.dto.CluPublicationInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r1.lum.lu.dto.CluResultTypeInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetTreeViewInfo;
import org.kuali.student.r1.lum.lu.dto.CluSetTypeInfo;
import org.kuali.student.r1.lum.lu.dto.DeliveryMethodTypeInfo;
import org.kuali.student.r2.lum.clu.dto.FieldInfo;
import org.kuali.student.r1.lum.lu.dto.InstructionalFormatTypeInfo;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r1.lum.lu.dto.LuCodeTypeInfo;
import org.kuali.student.r1.lum.lu.dto.LuLuRelationTypeInfo;
import org.kuali.student.r1.lum.lu.dto.LuPublicationTypeInfo;
import org.kuali.student.r1.lum.lu.dto.LuTypeInfo;
import org.kuali.student.r1.lum.lu.dto.LuiInfo;
import org.kuali.student.r1.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.kuali.student.r2.lum.clu.dto.ResultOptionInfo;
import org.kuali.student.r1.lum.lu.dto.ResultUsageTypeInfo;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluAccounting;
import org.kuali.student.lum.lu.entity.CluAccountingAttribute;
import org.kuali.student.lum.lu.entity.CluAccreditation;
import org.kuali.student.lum.lu.entity.CluAccreditationAttribute;
import org.kuali.student.lum.lu.entity.CluAdminOrg;
import org.kuali.student.lum.lu.entity.CluAdminOrgAttribute;
import org.kuali.student.lum.lu.entity.CluAtpTypeKey;
import org.kuali.student.lum.lu.entity.CluAttribute;
import org.kuali.student.lum.lu.entity.CluCampusLocation;
import org.kuali.student.lum.lu.entity.CluCluRelation;
import org.kuali.student.lum.lu.entity.CluCluRelationAttribute;
import org.kuali.student.lum.lu.entity.CluFee;
import org.kuali.student.lum.lu.entity.CluIdentifier;
import org.kuali.student.lum.lu.entity.CluInstructor;
import org.kuali.student.lum.lu.entity.CluInstructorAttribute;
import org.kuali.student.lum.lu.entity.CluLoRelation;
import org.kuali.student.lum.lu.entity.CluLoRelationAttribute;
import org.kuali.student.lum.lu.entity.CluLoRelationType;
import org.kuali.student.lum.lu.entity.CluPublication;
import org.kuali.student.lum.lu.entity.CluPublicationAttribute;
import org.kuali.student.lum.lu.entity.CluPublicationType;
import org.kuali.student.lum.lu.entity.CluPublicationVariant;
import org.kuali.student.lum.lu.entity.CluResult;
import org.kuali.student.lum.lu.entity.CluResultType;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.CluSetAttribute;
import org.kuali.student.lum.lu.entity.CluSetJoinVersionIndClu;
import org.kuali.student.lum.lu.entity.CluSetType;
import org.kuali.student.lum.lu.entity.DeliveryMethodType;
import org.kuali.student.lum.lu.entity.InstructionalFormatType;
import org.kuali.student.lum.lu.entity.LuCode;
import org.kuali.student.lum.lu.entity.LuCodeAttribute;
import org.kuali.student.lum.lu.entity.LuCodeType;
import org.kuali.student.lum.lu.entity.LuLuRelationType;
import org.kuali.student.lum.lu.entity.LuPublicationType;
import org.kuali.student.lum.lu.entity.LuRichText;
import org.kuali.student.lum.lu.entity.LuType;
import org.kuali.student.lum.lu.entity.Lui;
import org.kuali.student.lum.lu.entity.LuiAttribute;
import org.kuali.student.lum.lu.entity.LuiLuiRelation;
import org.kuali.student.lum.lu.entity.LuiLuiRelationAttribute;
import org.kuali.student.lum.lu.entity.MembershipQuery;
import org.kuali.student.lum.lu.entity.ResultOption;
import org.kuali.student.lum.lu.entity.ResultUsageType;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.r2.lum.clu.service.CluService", serviceName = "LuService", portName = "LuService", targetNamespace = "http://student.kuali.org/wsdl/lu")
@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class LuServiceImpl implements CluService {

	private static final String SEARCH_KEY_DEPENDENCY_ANALYSIS = "lu.search.dependencyAnalysis";
	private static final String SEARCH_KEY_BROWSE_PROGRAM = "lu.search.browseProgram";
	private static final String SEARCH_KEY_BROWSE_VARIATIONS = "lu.search.browseVariations";
	private static final String SEARCH_KEY_RESULT_COMPONENT = "lrc.search.resultComponent";
	
	final Logger logger = Logger.getLogger(LuServiceImpl.class);

	private LuDao luDao;
	private SearchManager searchManager;
	private SearchDispatcher searchDispatcher;
	private DictionaryService dictionaryServiceDelegate;
	private ValidatorFactory validatorFactory;

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

	public void setDictionaryServiceDelegate(
			DictionaryService dictionaryServiceDelegate) {
		this.dictionaryServiceDelegate = dictionaryServiceDelegate;
	}

	public DictionaryService getDictionaryServiceDelegate() {
		return dictionaryServiceDelegate;
	}


	/**************************************************************************
	 * SETUP OPERATION *
	 **************************************************************************/

	/* TODO KSCM
	@Override
	public List<DeliveryMethodTypeInfo> getDeliveryMethodTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		return LuServiceAssembler.toDeliveryMethodTypeInfos(luDao
				.find(DeliveryMethodType.class));
	}

	@Override
	public DeliveryMethodTypeInfo getDeliveryMethodType(String deliveryMethodTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(deliveryMethodTypeKey, "deliveryMethodTypeKey");

		return LuServiceAssembler.toDeliveryMethodTypeInfo(luDao.fetch(
				DeliveryMethodType.class, deliveryMethodTypeKey));
	}

	@Override
	public List<InstructionalFormatTypeInfo> getInstructionalFormatTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		return LuServiceAssembler.toInstructionalFormatTypeInfos(luDao
				.find(InstructionalFormatType.class));
	}

	@Override
	public InstructionalFormatTypeInfo getInstructionalFormatType(String instructionalFormatTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(instructionalFormatTypeKey,
				"instructionalFormatTypeKey");

		return LuServiceAssembler.toInstructionalFormatTypeInfo(luDao.fetch(
				InstructionalFormatType.class, instructionalFormatTypeKey));
	}

	@Override
	public List<LuTypeInfo> getLuTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		return LuServiceAssembler.toLuTypeInfos(luDao.find(LuType.class));
	}

	@Override
	public LuTypeInfo getLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");

		return LuServiceAssembler.toLuTypeInfo(luDao.fetch(LuType.class,
				luTypeKey));
	}

	@Override
	public LuCodeTypeInfo getLuCodeType(String luCodeTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(luCodeTypeKey, "luCodeTypeKey");
		return LuServiceAssembler.toLuCodeTypeInfo(luDao.fetch(
				LuCodeType.class, luCodeTypeKey));
	}

	@Override
	public List<LuCodeTypeInfo> getLuCodeTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		return LuServiceAssembler.toLuCodeTypeInfos(luDao
				.find(LuCodeType.class));
	}

	@Override
	public List<LuLuRelationTypeInfo> getLuLuRelationTypes(ContextInfo contextInfo) throws OperationFailedException, UnsupportedOperationException {
		return LuServiceAssembler.toLuLuRelationTypeInfos(luDao
				.find(LuLuRelationType.class));
	}

	@Override
	public LuLuRelationTypeInfo getLuLuRelationType(String cluCluRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluCluRelationTypeKey, "luLuRelationTypeKey");

		LuLuRelationType luLuRelationType = luDao.fetch(LuLuRelationType.class,
		        cluCluRelationTypeKey);
		return LuServiceAssembler.toLuLuRelationTypeInfo(luLuRelationType);
	}*/

	@Override
	public List<String> getAllowedLuLuRelationTypesForLuType(String luTypeKey, String relatedLuTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");
		checkForMissingParameter(relatedLuTypeKey, "relatedLuTypeKey");

		return luDao.getAllowedLuLuRelationTypesForLuType(luTypeKey,
				relatedLuTypeKey);
	}

	/* TODO KSCM
	@Override
	public List<LuPublicationTypeInfo> getLuPublicationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		return LuServiceAssembler.toLuPublicationTypeInfos(luDao
				.find(LuPublicationType.class));
	}

	@Override
	public LuPublicationTypeInfo getLuPublicationType(String luPublicationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(luPublicationTypeKey, "luPublicationTypeKey");

		return LuServiceAssembler.toLuPublicationTypeInfo(luDao.fetch(
				LuPublicationType.class, luPublicationTypeKey));
	}*/

	@Override
	public List<String> getLuPublicationTypesForLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		throw new UnsupportedOperationException("getLuPublicationTypesForLuType");
	}

	/* TODO KSCM
	@Override
	public List<CluResultTypeInfo> getCluResultTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		return LuServiceAssembler.toCluResultTypeInfos(luDao
				.find(CluResultType.class));
	}

	@Override
	public CluResultTypeInfo getCluResultType(String cluResultTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		return LuServiceAssembler.toCluResultTypeInfo(luDao.fetch(
				CluResultType.class, cluResultTypeKey));
	}

	@Override
	public List<CluResultTypeInfo> getCluResultTypesForLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");
		return LuServiceAssembler.toCluResultTypeInfos((luDao
				.getAllowedCluResultTypesForLuType(luTypeKey)));
	}

	@Override
	public List<ResultUsageTypeInfo> getResultUsageTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		return LuServiceAssembler.toResultUsageTypeInfos(luDao
				.find(ResultUsageType.class));
	}

	@Override
	public ResultUsageTypeInfo getResultUsageType(String resultUsageTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(resultUsageTypeKey, "resultUsageTypeKey");
		return LuServiceAssembler.toResultUsageTypeInfo(luDao.fetch(
				ResultUsageType.class, resultUsageTypeKey));
	}*/

	@Override
	public List<String> getAllowedResultUsageTypesForLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");

		return luDao.getAllowedResultUsageTypesForLuType(luTypeKey);
	}

	@Override
	public List<String> getAllowedResultComponentTypesForResultUsageType(String resultUsageTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(resultUsageTypeKey, "resultUsageTypeKey");

		return luDao
				.getAllowedResultComponentTypesForResultUsageType(resultUsageTypeKey);
	}

	/* TODO KSCM
	@Override
	public CluLoRelationTypeInfo getCluLoRelationType(String cluLoRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluLoRelationTypeKey, "cluLoRelationTypeKey");

		CluLoRelationType cluLoRelationType = luDao.fetch(
				CluLoRelationType.class, cluLoRelationTypeKey);
		return LuServiceAssembler.toCluLoRelationTypeInfo(cluLoRelationType);
	}*/

	/* TODO KSCM
	@Override
	public List<CluLoRelationTypeInfo> getCluLoRelationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		return LuServiceAssembler.toCluLoRelationTypeInfos(luDao
				.find(CluLoRelationType.class));
	}*/

	@Override
	public List<String> getAllowedCluLoRelationTypesForLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(luTypeKey, luTypeKey);

		return luDao.getAllowedCluLoRelationTypesForLuType(luTypeKey);
	}

	/* TODO KSCM
	@Override
	public List<CluSetTypeInfo> getCluSetTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		return LuServiceAssembler.toCluSetTypeInfos(luDao
				.find(CluSetType.class));
	}*/

	/* TODO KSCM
	@Override
	public CluSetTypeInfo getCluSetType(String cluSetTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluSetTypeKey, "cluSetTypeKey");
		return LuServiceAssembler.toCluSetTypeInfo(luDao.fetch(
				CluSetType.class, cluSetTypeKey));
	}*/

	/**************************************************************************
	 * READ OPERATION *
	 **************************************************************************/

	// **** Core **********
	@Override
	public CluInfo getClu(String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(cluId, "cluId");

		Clu clu = luDao.fetch(Clu.class, cluId);
		return LuServiceAssembler.toCluInfo(clu);
	}

	@Override
	public List<CluInfo> getClusByIds(List<String> cluIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluIds, "cluIdList");
		checkForEmptyList(cluIds, "cluIdList");
		List<Clu> clus = luDao.getClusByIdList(cluIds);
		return LuServiceAssembler.toCluInfos(clus);
	}

	@Override
	public List<CluInfo> getClusByLuType(String luTypeKey, String luState, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");
		checkForMissingParameter(luState, "lustate");
		List<Clu> clus = luDao.getClusByLuType(luTypeKey, luState);
		return LuServiceAssembler.toCluInfos(clus);
	}

	@Override
	public List<String> getCluIdsByLuType(String luTypeKey, String luState, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");
		checkForMissingParameter(luState, "luState");
		List<Clu> clus = luDao.getClusByLuType(luTypeKey, luState);
		List<String> ids = new ArrayList<String>(clus.size());
		for (Clu clu : clus) {
			ids.add(clu.getId());
		}
		return ids;
	}

	// ****** Relations

	@Override
	public List<String> getAllowedCluCluRelationTypesByClu(String cluId, String relatedCluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(relatedCluId, "relatedCluId");

		return luDao.getAllowedLuLuRelationTypesByCluId(cluId, relatedCluId);
	}

	@Override
	public List<CluInfo> getClusByRelatedCluAndRelationType(String relatedCluId, String cluCLuRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(relatedCluId, "relatedCluId");
		checkForMissingParameter(cluCLuRelationTypeKey, "luLuRelationTypeKey");

		List<Clu> clus = luDao.getClusByRelation(relatedCluId,
		        cluCLuRelationTypeKey);
		List<CluInfo> result = LuServiceAssembler.toCluInfos(clus);
		return result;

	}

	@Override
	public List<String> getCluIdsByRelatedCluAndRelationType(String relatedCluId, String cluCluRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(relatedCluId, "relatedCluId");
		checkForMissingParameter(cluCluRelationTypeKey, "luLuRelationTypeKey");

        List<String> cluIds = luDao.getCluIdsByRelatedCluId(relatedCluId, cluCluRelationTypeKey);
        return cluIds;
	}

	@Override
	public List<CluInfo> getRelatedClusByCluAndRelationType(String cluId, String cluCluRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(cluCluRelationTypeKey, "luLuRelationTypeKey");
		List<Clu> relatedClus = luDao.getRelatedClusByCluId(cluId,
		        cluCluRelationTypeKey);
		return LuServiceAssembler.toCluInfos(relatedClus);
	}

	@Override
	public List<String> getRelatedCluIdsByCluAndRelationType(String cluId, String cluCluRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(cluCluRelationTypeKey, "luLuRelationTypeKey");
		List<String> relatedCluIds = luDao.getRelatedCluIdsByCluId(cluId,
		        cluCluRelationTypeKey);
		return relatedCluIds;
	}

	@Override
	public CluCluRelationInfo getCluCluRelation(String cluCluRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluCluRelationId, "cluCluRelationId");
		return LuServiceAssembler.toCluCluRelationInfo(luDao.fetch(
				CluCluRelation.class, cluCluRelationId));
	}

	@Override
	public List<CluCluRelationInfo> getCluCluRelationsByClu(String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluId, "cluId");
		List<CluCluRelation> cluCluRelations = luDao
				.getCluCluRelationsByClu(cluId);
		return LuServiceAssembler.toCluCluRelationInfos(cluCluRelations);
	}

	// **** Publication
	@Override
	public List<CluPublicationInfo> getCluPublicationsByClu(String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	      checkForMissingParameter(cluId, "cluId");
	      List<CluPublication> cluPublications = luDao.getCluPublicationsByCluId(cluId);
	      return LuServiceAssembler.toCluPublicationInfos(cluPublications);
	}

	@Override
	public List<CluPublicationInfo> getCluPublicationsByType(String luPublicationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	      checkForMissingParameter(luPublicationTypeKey, "luPublicationTypeKey");
	      List<CluPublication> cluPublications = luDao.getCluPublicationsByType(luPublicationTypeKey);
	      return LuServiceAssembler.toCluPublicationInfos(cluPublications);
	}

	@Override
	public CluPublicationInfo getCluPublication(String cluPublicationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	      checkForMissingParameter(cluPublicationId, "cluPublicationId");
	      CluPublication cluPublication = luDao.fetch(CluPublication.class, cluPublicationId);
	      return LuServiceAssembler.toCluPublicationInfo(cluPublication);
	}

	// **** Results

	@Override
	public CluResultInfo getCluResult(String cluResultId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(cluResultId, "cluResultId");

		CluResult cluResult = luDao.fetch(CluResult.class, cluResultId);
		return LuServiceAssembler.toCluResultInfo(cluResult);
	}

	@Override
	public List<CluResultInfo> getCluResultByClu(String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

		checkForMissingParameter(cluId, "cluId");

		return LuServiceAssembler.toCluResultInfos(luDao
				.getCluResultByClu(cluId));
	}

	@Override
	public List<String> getCluIdsByResultUsageType(String resultUsageTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return luDao.getCluIdsByResultUsageType(resultUsageTypeKey);
	}

	@Override
	public List<String> getCluIdsByResultComponent(String resultComponentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return luDao.getCluIdsByResultComponentId(resultComponentId);
	}

	// **** Learning Objectives

	@Override
	public CluLoRelationInfo getCluLoRelation(String cluLoRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(cluLoRelationId, "cluLoRelationId");

		CluLoRelation reltn = luDao.fetch(CluLoRelation.class, cluLoRelationId);
		return LuServiceAssembler.toCluLoRelationInfo(reltn);

	}

	@Override
	public List<CluLoRelationInfo> getCluLoRelationsByClu(String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(cluId, "cluId");
		List<CluLoRelation> cluLoRelations = luDao
				.getCluLoRelationsByClu(cluId);
		return LuServiceAssembler.toCluLoRelationInfos(cluLoRelations);

	}

	@Override
	public List<CluLoRelationInfo> getCluLoRelationsByLo(String loId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(loId, "loId");
		List<CluLoRelation> cluLoRelations = luDao.getCluLoRelationsByLo(loId);
		return LuServiceAssembler.toCluLoRelationInfos(cluLoRelations);
	}

	// *** Resources

	@Override
	public List<String> getResourceRequirementsForClu(String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	      throw new UnsupportedOperationException("Method not yet implemented!");
	}

	// *** Sets

	@Override
	public CluSetInfo getCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		CluSetInfo cluSetInfo = LuServiceAssembler.toCluSetInfo(cluSet);
		setMembershipQuerySearchResult(cluSetInfo);
		return cluSetInfo;
	}

	@Override
	public CluSetTreeViewInfo getCluSetTreeView(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(cluSetId, "cluSetId");
		CluSetInfo cluSet = getCluSet(cluSetId, contextInfo);
		if (cluSet == null) {
			return null;
		}

		CluSetTreeViewInfo cluSetTreeView = new CluSetTreeViewInfo();
		getCluSetTreeViewHelper(cluSet, cluSetTreeView, contextInfo);
		return cluSetTreeView;
	}

	/**
	 * Go through the list of CluSets and retrieve all the information regarding child
	 * Clu Sets and associated Clus
	 *
	 * @param cluSetInfo
	 * @param cluSetTreeViewInfo
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	private void getCluSetTreeViewHelper(CluSetInfo cluSetInfo,	CluSetTreeViewInfo cluSetTreeViewInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		cluSetTreeViewInfo.setName(cluSetInfo.getName());
		cluSetTreeViewInfo.setDescr(cluSetInfo.getDescr());
		cluSetTreeViewInfo.setEffectiveDate(cluSetInfo.getEffectiveDate());
		cluSetTreeViewInfo.setExpirationDate(cluSetInfo.getExpirationDate());
		cluSetTreeViewInfo.setAdminOrg(cluSetInfo.getAdminOrg());
		cluSetTreeViewInfo.setIsReusable(cluSetInfo.getIsReusable());
		cluSetTreeViewInfo.setIsReferenceable(cluSetInfo.getIsReferenceable());
		cluSetTreeViewInfo.setMeta(cluSetInfo.getMeta());
		cluSetTreeViewInfo.setAttributes(cluSetInfo.getAttributes());
		cluSetTreeViewInfo.setType(cluSetInfo.getType());
		cluSetTreeViewInfo.setState(cluSetInfo.getState());
		cluSetTreeViewInfo.setId(cluSetInfo.getId());

		if (!cluSetInfo.getCluSetIds().isEmpty()) {
			for (String cluSetId : cluSetInfo.getCluSetIds()) {
				CluSetInfo subCluSet = getCluSet(cluSetId, contextInfo);
				List<CluSetTreeViewInfo> cluSets =
                    cluSetTreeViewInfo.getCluSets() == null ?
                            new ArrayList<CluSetTreeViewInfo>(0) : cluSetTreeViewInfo.getCluSets();

                CluSetTreeViewInfo subCluSetTreeViewInfo = new CluSetTreeViewInfo();
                getCluSetTreeViewHelper(subCluSet, subCluSetTreeViewInfo, contextInfo);
                cluSets.add(subCluSetTreeViewInfo);

                cluSetTreeViewInfo.setCluSets(cluSets);
			}
		}
		List<CluInfo> clus = new ArrayList<CluInfo>();
		for (String cluId : cluSetInfo.getCluIds()) {
			if(cluId!=null){
                //Optimized version of clu translation. It seems like for now we only need the following information.
                //If more information is needed, then appropriate method in assembler has to be used.
                Clu clu = luDao.getCurrentCluVersion(cluId);
                CluInfo cluInfo = new CluInfo();
                cluInfo.setId(clu.getId());
                cluInfo.setType(clu.getLuType().getId());
                cluInfo.setOfficialIdentifier(LuServiceAssembler.toCluIdentifierInfo(clu.getOfficialIdentifier()));
				clus.add(cluInfo);
			}
		}
		cluSetTreeViewInfo.setClus(clus);
	}

	@Override
	public List<CluSetInfo> getCluSetsByIds(List<String> cluSetIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluSetIds, "cluSetIdList");
		checkForEmptyList(cluSetIds, "cluSetIdList");
		List<CluSet> cluSets = luDao.getCluSetInfoByIdList(cluSetIds);
		return LuServiceAssembler.toCluSetInfos(cluSets);
	}

	@Override
	public List<String> getCluSetIdsFromCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		List<String> ids = new ArrayList<String>(cluSet.getCluVerIndIds().size());
		if(cluSet.getCluSets()!=null){
			for (CluSet cluSet2 : cluSet.getCluSets()) {
				ids.add(cluSet2.getId());
			}
		}
		return ids;
	}

	@Override
	public Boolean isCluSetDynamic(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	      throw new UnsupportedOperationException("Method not yet implemented!");
	}

	@Override
	public List<CluInfo> getClusFromCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		List<CluInfo> clus = new ArrayList<CluInfo>(cluSet.getCluVerIndIds().size());
		for (CluSetJoinVersionIndClu cluSetJnClu : cluSet.getCluVerIndIds()) {
			clus.add(LuServiceAssembler.toCluInfo(luDao.getCurrentCluVersion(cluSetJnClu.getCluVersionIndId())));
		}
		return clus;
	}

	@Override
	public List<String> getCluIdsFromCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		List<String> ids = new ArrayList<String>(cluSet.getCluVerIndIds().size());
		for (CluSetJoinVersionIndClu cluSetJnClu : cluSet.getCluVerIndIds()) {
			ids.add(cluSetJnClu.getCluVersionIndId());
		}
		return ids;
	}

	@Override
	public List<CluInfo> getAllClusInCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		List<String> cluIndIds = new ArrayList<String>();
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		findClusInCluSet(cluIndIds, cluSet);
		List<CluInfo> infos = new ArrayList<CluInfo>(cluIndIds.size());
		for (String cluIndId : cluIndIds) {
			infos.add(LuServiceAssembler.toCluInfo(luDao.getCurrentCluVersion(cluIndId)));
		}
		return infos;
	}

	@Override
	public List<String> getAllCluIdsInCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		List<String> ids = new ArrayList<String>();
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		findClusInCluSet(ids, cluSet);
		return ids;
	}

	@Override
	public Boolean isCluInCluSet(String cluId, String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(cluSetId, "cluSetId");
		return luDao.isCluInCluSet(cluId, cluSetId);
	}

	// ******** LUI OPERATIONS
	// *** Core

	@Override
	public LuiInfo getLui(String luiId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {

		checkForMissingParameter(luiId, "luiId");

		Lui lui = luDao.fetch(Lui.class, luiId);
		return LuServiceAssembler.toLuiInfo(lui);
	}

	@Override
	public List<LuiInfo> getLuisByIds(List<String> luiIdList, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
		checkForMissingParameter(luiIdList, "luiIdList");
		checkForEmptyList(luiIdList, "luiIdList");
		List<Lui> luis = luDao.getLuisByIdList(luiIdList);
		return LuServiceAssembler.toLuiInfos(luis);
	}

	@Override
	public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
	      throw new UnsupportedOperationException("Method not yet implemented!");
	}

	@Override
	public List<String> getLuiIdsByCluId(String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {

		checkForMissingParameter(cluId, "cluId");

		return luDao.getLuiIdsByCluId(cluId);
	}

	@Override
	public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {

		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(atpKey, "atpKey");
		return luDao.getLuiIdsInAtpByCluId(cluId, atpKey);
	}

	// *** Relations

	@Override
	public List<String> getAllowedLuLuRelationTypesByLuiId(String luiId, String relatedLuiId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {

		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(relatedLuiId, "relatedLuiId");

		return luDao.getAllowedLuLuRelationTypesByLuiId(luiId, relatedLuiId);
	}

	@Override
	public List<LuiInfo> getLuisByRelation(String luiId, String luLuRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");

		return LuServiceAssembler.toLuiInfos(luDao.getLuisByRelationType(luiId,
				luLuRelationTypeKey));
	}

	@Override
	public List<String> getLuiIdsByRelation(String luiId, String luLuRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");

		return luDao.getLuiIdsByRelationType(luiId, luLuRelationTypeKey);
	}

	@Override
	public List<LuiInfo> getRelatedLuisByLuiId(String luiId, String luLuRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
		List<Lui> relatedLuis = luDao.getRelatedLuisByLuiId(luiId,
				luLuRelationTypeKey);
		return LuServiceAssembler.toLuiInfos(relatedLuis);
	}

	@Override
	public List<String> getRelatedLuiIdsByLuiId(String luiId, String luLuRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
		List<String> relatedLuiIds = luDao.getRelatedLuiIdsByLuiId(luiId,
				luLuRelationTypeKey);
		return relatedLuiIds;
	}

	@Override
	public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
		checkForMissingParameter(luiLuiRelationId, "luiLuiRelationId");
		LuiLuiRelation luiLuiRelation = luDao.fetch(LuiLuiRelation.class,
				luiLuiRelationId);
		return LuServiceAssembler.toLuiLuiRelationInfo(luiLuiRelation);
	}

	@Override
	public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
		checkForMissingParameter(luiId, "luiId");
		List<LuiLuiRelation> entities = luDao.getLuiLuiRelations(luiId);
		return LuServiceAssembler.toLuiLuiRelationInfos(entities);
	}

	/**************************************************************************
	 * MAINTENANCE OPERATIONS *
	 **************************************************************************/

	@Override
	public List<ValidationResultInfo> validateClu(String validationTypeKey, CluInfo cluInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(validationTypeKey, "validationType");
		checkForMissingParameter(cluInfo, "cluInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluInfo.class.getName(), contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(cluInfo, objStructure, contextInfo);
        
        return validationResults;
	}

	@Override
	@Transactional(readOnly=false)
	public CluInfo createClu(String luTypeKey, CluInfo cluInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, AlreadyExistsException {
		Clu clu = toCluForCreate(luTypeKey,cluInfo, contextInfo);
		//Set current (since this is brand new and every verIndId needs one current)
		if(clu.getVersion() == null){
			clu.setVersion(new Version());
		}
		clu.getVersion().setCurrentVersionStart(new Date());
		luDao.create(clu);
		return LuServiceAssembler.toCluInfo(clu);
	}
	
	public Clu toCluForCreate(String luTypeKey, CluInfo cluInfo, ContextInfo contextInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");
		checkForMissingParameter(cluInfo, "cluInfo");

		// Validate CLU
		List<ValidationResultInfo> val = validateClu("SYSTEM", cluInfo, contextInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!", val);
		}

		Clu clu = new Clu();

		LuType luType = luDao.fetch(LuType.class, luTypeKey);
		clu.setLuType(luType);

		if (cluInfo.getOfficialIdentifier() != null) {
			clu.setOfficialIdentifier(LuServiceAssembler.createOfficialIdentifier(cluInfo, luDao));
		}
		clu.setAlternateIdentifiers(LuServiceAssembler.createAlternateIdentifiers(cluInfo, luDao));
		if (cluInfo.getDescr() != null) {
		    // TODO KSCM LuRichText descr = LuServiceAssembler.toRichText(LuRichText.class, cluInfo.getDescr());
		    //if (descr.getPlain() != null || descr.getFormatted() != null) {
		    //    clu.setDescr(descr);
		    //}
		}

		if (clu.getAdminOrgs() == null) {
			clu.setAdminOrgs(new ArrayList<CluAdminOrg>(0));
		}
		List<CluAdminOrg> adminOrgs = clu.getAdminOrgs();
		for (AdminOrgInfo orgInfo : cluInfo.getAdminOrgs()) {
			CluAdminOrg instructor = new CluAdminOrg();
			BeanUtils.copyProperties(orgInfo, instructor,
					new String[] { "attributes" });
			// TODO KSCM instructor.setAttributes(LuServiceAssembler.toGenericAttributes(
			//		CluAdminOrgAttribute.class, orgInfo.getAttributes(),
			//		instructor, luDao));
			instructor.setClu(clu);
			adminOrgs.add(instructor);
		}

		if (cluInfo.getPrimaryInstructor() != null) {
			CluInstructor primaryInstructor = new CluInstructor();
			BeanUtils.copyProperties(cluInfo.getPrimaryInstructor(),
					primaryInstructor, new String[] { "attributes" });
			// TODO KSCM primaryInstructor.setAttributes(LuServiceAssembler
			//		.toGenericAttributes(CluInstructorAttribute.class, cluInfo
			//				.getPrimaryInstructor().getAttributes(),
			//				primaryInstructor, luDao));
			clu.setPrimaryInstructor(primaryInstructor);
		}

		if (clu.getInstructors() == null) {
			clu.setInstructors(new ArrayList<CluInstructor>(0));
		}
		List<CluInstructor> instructors = clu.getInstructors();
		for (CluInstructorInfo instructorInfo : cluInfo.getInstructors()) {
			CluInstructor instructor = new CluInstructor();
			BeanUtils.copyProperties(instructorInfo, instructor,
					new String[] { "attributes" });
			// TODO KSCM instructor.setAttributes(LuServiceAssembler.toGenericAttributes(
			//		CluInstructorAttribute.class, instructorInfo
			//				.getAttributes(), instructor, luDao));
			instructors.add(instructor);
		}

		if (cluInfo.getStdDuration() != null) {
			clu.setStdDuration(LuServiceAssembler.toTimeAmount(cluInfo
					.getStdDuration()));
		}

		if (clu.getLuCodes() == null) {
			clu.setLuCodes(new ArrayList<LuCode>(0));
		}
		List<LuCode> luCodes = clu.getLuCodes();
		for (LuCodeInfo luCodeInfo : cluInfo.getLuCodes()) {
			LuCode luCode = new LuCode();
			// TODO KSCM luCode.setAttributes(LuServiceAssembler.toGenericAttributes(
			//		LuCodeAttribute.class, luCodeInfo.getAttributes(), luCode,
			//		luDao));
			BeanUtils.copyProperties(luCodeInfo, luCode, new String[] {
					"attributes", "metaInfo" });
			luCode.setDescr(luCodeInfo.getDescr());
			luCode.setClu(clu);
			luCodes.add(luCode);
		}

		if (clu.getOfferedAtpTypes() == null) {
			clu.setOfferedAtpTypes(new ArrayList<CluAtpTypeKey>(0));
		}
		List<CluAtpTypeKey> offeredAtpTypes = clu.getOfferedAtpTypes();
		for (String atpTypeKey : cluInfo.getOfferedAtpTypes()) {
			CluAtpTypeKey cluAtpTypeKey = new CluAtpTypeKey();
			cluAtpTypeKey.setAtpTypeKey(atpTypeKey);
			cluAtpTypeKey.setClu(clu);
			offeredAtpTypes.add(cluAtpTypeKey);
		}

		// FEE INFO
		if (cluInfo.getFeeInfo() != null) {
			CluFee cluFee = null;
			try {
				cluFee = LuServiceAssembler.toCluFee(clu, false, cluInfo
						.getFeeInfo(), luDao);
			} catch (VersionMismatchException e) {
				// Version Mismatch Should Happen only for updates
			}
			clu.setFee(cluFee);
		}

		if (cluInfo.getAccountingInfo() != null) {
			CluAccounting cluAccounting = new CluAccounting();
			// TODO KSCM cluAccounting.setAttributes(LuServiceAssembler.toGenericAttributes(
			//		CluAccountingAttribute.class, cluInfo.getAccountingInfo()
			//				.getAttributes(), cluAccounting, luDao));
			cluAccounting.setAffiliatedOrgs(LuServiceAssembler
					.toAffiliatedOrgs(false, cluAccounting.getAffiliatedOrgs(),
							cluInfo.getAccountingInfo().getAffiliatedOrgs(),
							luDao));
			clu.setAccounting(cluAccounting);
		}

		// TODO KSCM clu.setAttributes(LuServiceAssembler.toGenericAttributes(
		//		CluAttribute.class, cluInfo.getAttributes(), clu, luDao));


		if (cluInfo.getIntensity() != null) {
			clu.setIntensity(LuServiceAssembler
					.toAmount(cluInfo.getIntensity()));
		}

		if (clu.getCampusLocations() == null) {
			clu.setCampusLocations(new ArrayList<CluCampusLocation>(0));
		}
		List<CluCampusLocation> locations = clu.getCampusLocations();
		for (String locationName : cluInfo.getCampusLocations()) {
			CluCampusLocation location = new CluCampusLocation();
			location.setCampusLocation(locationName);
			location.setClu(clu);
			locations.add(location);
		}

		if (clu.getAccreditations() == null) {
			clu.setAccreditations(new ArrayList<CluAccreditation>(0));
		}
		List<CluAccreditation> accreditations = clu.getAccreditations();
		for (AccreditationInfo accreditationInfo : cluInfo.getAccreditations()) {
			CluAccreditation accreditation = new CluAccreditation();
			BeanUtils.copyProperties(accreditationInfo, accreditation,
					new String[] { "attributes" });
			// TODO KSCM accreditation.setAttributes(LuServiceAssembler.toGenericAttributes(
			//		CluAccreditationAttribute.class, accreditationInfo
			//				.getAttributes(), accreditation, luDao));
			accreditations.add(accreditation);
		}
		
		// Now copy all not standard properties
		BeanUtils.copyProperties(cluInfo, clu, new String[] { "luType",
				"officialIdentifier", "alternateIdentifiers", "descr",
				"luCodes", "primaryInstructor", "instructors", "stdDuration",
				"offeredAtpTypes", "feeInfo", "accountingInfo", "attributes",
				"metaInfo", "versionInfo", "intensity",
				"campusLocations", "accreditations",
				"adminOrgs" });

		return clu;
	}

	@Override
	@Transactional(readOnly=false)
	public CluInfo updateClu(String cluId, CluInfo cluInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(cluInfo, "cluInfo");

		// Validate CLU
		List<ValidationResultInfo> val = validateClu("SYSTEM", cluInfo, contextInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!", val);
		}

		Clu clu = luDao.fetch(Clu.class, cluId);

		if (!String.valueOf(clu.getVersionNumber()).equals(
				cluInfo.getMeta().getVersionInd())) {
			throw new VersionMismatchException(
					"Clu to be updated is not the current version");
		}

		LuType luType = luDao.fetch(LuType.class, cluInfo.getType());
		clu.setLuType(luType);

		if (cluInfo.getOfficialIdentifier() != null) {
		    LuServiceAssembler.updateOfficialIdentifier(clu, cluInfo, luDao);
		} else if (clu.getOfficialIdentifier() != null) {
			luDao.delete(clu.getOfficialIdentifier());
		}

		// Update the list of Alternate Identifiers
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, CluIdentifier> oldAltIdMap = new HashMap<String, CluIdentifier>();
		LuServiceAssembler.updateAlternateIdentifier(oldAltIdMap, clu, cluInfo, luDao);
		// Now delete anything left over
		for (Entry<String, CluIdentifier> entry : oldAltIdMap.entrySet()) {
			luDao.delete(entry.getValue());
		}

		if (cluInfo.getDescr() != null && (cluInfo.getDescr().getPlain() != null || cluInfo.getDescr().getFormatted() != null)) {
			if (clu.getDescr() == null) {
				clu.setDescr(new LuRichText());
			}
			BeanUtils.copyProperties(cluInfo.getDescr(), clu.getDescr());
		} else if (clu.getDescr() != null) {
			luDao.delete(clu.getDescr());
			clu.setDescr(null);//TODO is the is the best method of doing this? what if the user passes in a new made up id, does that mean we have orphaned richtexts?
		}

		if (cluInfo.getPrimaryInstructor() != null) {
			if (clu.getPrimaryInstructor() == null) {
				clu.setPrimaryInstructor(new CluInstructor());
			}
			BeanUtils.copyProperties(cluInfo.getPrimaryInstructor(), clu
					.getPrimaryInstructor(), new String[] { "attributes" });
			// TODO KSCM clu.getPrimaryInstructor().setAttributes(
			//		LuServiceAssembler.toGenericAttributes(
			//				CluInstructorAttribute.class, cluInfo
			//						.getPrimaryInstructor().getAttributes(),
			//				clu.getPrimaryInstructor(), luDao));
		} else if (clu.getPrimaryInstructor() != null) {
			luDao.delete(clu.getPrimaryInstructor());
		}

		// Update the List of instructors
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, CluInstructor> oldInstructorMap = new HashMap<String, CluInstructor>();
		for (CluInstructor cluInstructor : clu.getInstructors()) {
			oldInstructorMap.put(cluInstructor.getOrgId() + "_"
					+ cluInstructor.getPersonId(), cluInstructor);
		}
		clu.getInstructors().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list
		// otherwise create a new entry
		for (CluInstructorInfo instructorInfo : cluInfo.getInstructors()) {
			CluInstructor cluInstructor = oldInstructorMap
					.remove(instructorInfo.getOrgId() + "_"
							+ instructorInfo.getPersonId());
			if (cluInstructor == null) {
				cluInstructor = new CluInstructor();
			}
			// Do Copy
			BeanUtils.copyProperties(instructorInfo, cluInstructor,
					new String[] { "attributes" });
			// TODO KSCM cluInstructor.setAttributes(LuServiceAssembler.toGenericAttributes(
			//		CluInstructorAttribute.class, instructorInfo
			//				.getAttributes(), cluInstructor, luDao));
			clu.getInstructors().add(cluInstructor);
		}

		// Now delete anything left over
		for (Entry<String, CluInstructor> entry : oldInstructorMap.entrySet()) {
			luDao.delete(entry.getValue());
		}

		if (cluInfo.getStdDuration() != null) {
			if (clu.getStdDuration() == null) {
				clu.setStdDuration(new TimeAmount());
			}
			BeanUtils.copyProperties(cluInfo.getStdDuration(), clu
					.getStdDuration());
		} else if (clu.getStdDuration() != null) {
			luDao.delete(clu.getStdDuration());
		}

		// Update the LuCodes
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, LuCode> oldLuCodeMap = new HashMap<String, LuCode>();
		for (LuCode luCode : clu.getLuCodes()) {
			oldLuCodeMap.put(luCode.getId(), luCode);
		}
		clu.getLuCodes().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list
		// otherwise create a new entry
		for (LuCodeInfo luCodeInfo : cluInfo.getLuCodes()) {
			LuCode luCode = oldLuCodeMap.remove(luCodeInfo.getId());
			if (luCode == null) {
				luCode = new LuCode();
			} else {
				if (!String.valueOf(luCode.getVersionNumber()).equals(
						luCodeInfo.getMeta().getVersionInd())) {
					throw new VersionMismatchException(
							"LuCode to be updated is not the current version");
				}
			}
			// Do Copy
			// TODO KSCM luCode.setAttributes(LuServiceAssembler.toGenericAttributes(
			//		LuCodeAttribute.class, luCodeInfo.getAttributes(), luCode,
			//		luDao));
			BeanUtils.copyProperties(luCodeInfo, luCode, new String[] {
					"attributes", "metaInfo" });
			luCode.setDescr(luCodeInfo.getDescr());
			luCode.setClu(clu);
			clu.getLuCodes().add(luCode);
		}

		// Now delete anything left over
		for (Entry<String, LuCode> entry : oldLuCodeMap.entrySet()) {
			luDao.delete(entry.getValue());
		}

		// Update the list of AtpTypeKeys
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, CluAtpTypeKey> oldOfferedAtpTypesMap = new HashMap<String, CluAtpTypeKey>();
		for (CluAtpTypeKey cluAtpTypeKey : clu.getOfferedAtpTypes()) {
			oldOfferedAtpTypesMap.put(cluAtpTypeKey.getAtpTypeKey(),
					cluAtpTypeKey);
		}
		clu.getOfferedAtpTypes().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list
		// otherwise create a new entry
		for (String atpTypeKey : cluInfo.getOfferedAtpTypes()) {
			CluAtpTypeKey cluAtpTypeKey = oldOfferedAtpTypesMap
					.remove(atpTypeKey);
			if (cluAtpTypeKey == null) {
				cluAtpTypeKey = new CluAtpTypeKey();
			}
			// Do Copy
			cluAtpTypeKey.setAtpTypeKey(atpTypeKey);
			cluAtpTypeKey.setClu(clu);
			clu.getOfferedAtpTypes().add(cluAtpTypeKey);
		}

		// Now delete anything left over
		for (Entry<String, CluAtpTypeKey> entry : oldOfferedAtpTypesMap
				.entrySet()) {
			luDao.delete(entry.getValue());
		}

		if (cluInfo.getFeeInfo() != null) {
			if (clu.getFee() == null) {
				clu.setFee(LuServiceAssembler.toCluFee(clu, false, cluInfo
						.getFeeInfo(), luDao));
			} else {
				clu.setFee(LuServiceAssembler.toCluFee(clu, true, cluInfo
						.getFeeInfo(), luDao));
			}
		} else if (clu.getFee() != null) {
			luDao.delete(clu.getFee());
			clu.setFee(null);
		}

		if (cluInfo.getAccountingInfo() != null) {
			if (clu.getAccounting() == null) {
				clu.setAccounting(new CluAccounting());
			}
			// TODO KSCM clu.getAccounting().setAttributes(
			//		LuServiceAssembler.toGenericAttributes(
			//				CluAccountingAttribute.class, cluInfo
			//						.getAccountingInfo().getAttributes(), clu
			//						.getAccounting(), luDao));
			clu.getAccounting().setAffiliatedOrgs(LuServiceAssembler
					.toAffiliatedOrgs(true, clu.getAccounting().getAffiliatedOrgs(),
							cluInfo.getAccountingInfo().getAffiliatedOrgs(),
							luDao));

		} else if (clu.getAccounting() != null) {
			clu.setAccounting(null);
		}

		// TODO KSCM clu.setAttributes(LuServiceAssembler.toGenericAttributes(
		//		CluAttribute.class, cluInfo.getAttributes(), clu, luDao));

		if (cluInfo.getIntensity() != null) {
			if (clu.getIntensity() == null) {
				clu.setIntensity(new Amount());
			}
			BeanUtils
					.copyProperties(cluInfo.getIntensity(), clu.getIntensity());
		} else if (clu.getIntensity() != null) {
			luDao.delete(clu.getIntensity());
		}

		// Update the list of campusLocations
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, CluCampusLocation> oldLocationsMap = new HashMap<String, CluCampusLocation>();
		for (CluCampusLocation campus : clu.getCampusLocations()) {
			oldLocationsMap.put(campus.getCampusLocation(), campus);
		}
		clu.getCampusLocations().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list
		// otherwise create a new entry
		for (String locationName : cluInfo.getCampusLocations()) {
			CluCampusLocation location = oldLocationsMap.remove(locationName);
			if (location == null) {
				location = new CluCampusLocation();
			}
			// Do Copy
			location.setCampusLocation(locationName);
			location.setClu(clu);
			clu.getCampusLocations().add(location);
		}

		// Now delete anything left over
		for (Entry<String, CluCampusLocation> entry : oldLocationsMap
				.entrySet()) {
			luDao.delete(entry.getValue());
		}

		// Update the List of accreditations
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, CluAccreditation> oldAccreditationMap = new HashMap<String, CluAccreditation>();
		for (CluAccreditation cluAccreditation : clu.getAccreditations()) {
			oldAccreditationMap.put(cluAccreditation.getId(),
					cluAccreditation);
		}
		clu.getAccreditations().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list
		// otherwise create a new entry
		for (AccreditationInfo accreditationInfo : cluInfo.getAccreditations()) {
			CluAccreditation cluAccreditation = null;
			if(accreditationInfo.getId()!=null){
				cluAccreditation = oldAccreditationMap.remove(accreditationInfo.getId());
			}
					
			if (cluAccreditation == null) {
				cluAccreditation = new CluAccreditation();
			}
			// Do Copy
			BeanUtils.copyProperties(accreditationInfo, cluAccreditation,
					new String[] { "attributes" });
			// TODO KSCM cluAccreditation.setAttributes(LuServiceAssembler
			//		.toGenericAttributes(CluAccreditationAttribute.class,
			//				accreditationInfo.getAttributes(),
			//				cluAccreditation, luDao));
			clu.getAccreditations().add(cluAccreditation);
		}

		// Now delete anything left over
		for (Entry<String, CluAccreditation> entry : oldAccreditationMap
				.entrySet()) {
			luDao.delete(entry.getValue());
		}

		// Update the List of alternate admin orgs
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, CluAdminOrg> oldAdminOrgsMap = new HashMap<String, CluAdminOrg>();
		if(clu.getAdminOrgs()!=null){
			for (CluAdminOrg cluOrg : clu.getAdminOrgs()) {
				oldAdminOrgsMap.put(cluOrg.getId(), cluOrg);
			}
		}
		clu.setAdminOrgs(new ArrayList<CluAdminOrg>());

		// Loop through the new list, if the item exists already update and
		// remove from the list
		// otherwise create a new entry
		for (AdminOrgInfo orgInfo : cluInfo.getAdminOrgs()) {
			CluAdminOrg cluOrg = null;
			if(orgInfo.getId() != null){
				cluOrg = oldAdminOrgsMap.remove(orgInfo.getId());
			}
			
			if (cluOrg == null) {
				cluOrg = new CluAdminOrg();
			}
			
			// Do Copy
			BeanUtils.copyProperties(orgInfo, cluOrg,
					new String[] { "attributes","id" });
			// TODO KSCM cluOrg.setAttributes(LuServiceAssembler.toGenericAttributes(
			//		CluAdminOrgAttribute.class, orgInfo.getAttributes(),
			//		cluOrg, luDao));
			cluOrg.setClu(clu);
			clu.getAdminOrgs().add(cluOrg);
		}

		for (Entry<String, CluAdminOrg> entry : oldAdminOrgsMap.entrySet()) {
			luDao.delete(entry.getValue());
		}

		// Now copy all not standard properties
		BeanUtils.copyProperties(cluInfo, clu, new String[] { "luType",
				"officialIdentifier", "alternateIdentifiers", "descr",
				"luCodes", "primaryInstructor", "instructors", "stdDuration",
				"offeredAtpTypes", "feeInfo", "accountingInfo", "attributes",
				"metaInfo","intensity",
				"campusLocations", "accreditations",
				"adminOrgs" });
		Clu updated = null;
		try {
			updated = luDao.update(clu);
		} catch (Exception e) {
			logger.error("Exception occured: ", e);
		}
		return LuServiceAssembler.toCluInfo(updated);
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo deleteClu(String cluId, ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluId, "cluId");

		luDao.delete(Clu.class, cluId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	@Transactional(readOnly=false)
	public CluInfo updateCluState(String cluId, String luState, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
		// Check Missing params
		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(luState, "luState");
		Clu clu = luDao.fetch(Clu.class, cluId);
		clu.setState(luState);
		Clu updated = luDao.update(clu);
		return LuServiceAssembler.toCluInfo(updated);
	}

	@Override
	public List<ValidationResultInfo> validateCluCluRelation(String validationTypeKey, String cluId, String relatedCluId, String cluCluRelationTypeKey, CluCluRelationInfo cluCluRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(validationTypeKey, "validationType");
		checkForMissingParameter(cluCluRelationInfo, "cluCluRelationInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluCluRelationInfo.class.getName(), contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(cluCluRelationInfo, objStructure, contextInfo);
        
        return validationResults;
	}

	@Override
	@Transactional(readOnly=false)
	public CluCluRelationInfo createCluCluRelation(String cluId, String relatedCluId, String cluCluRelationTypeKey, CluCluRelationInfo cluCluRelationInfo, ContextInfo contextInfo) throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, AlreadyExistsException {
		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(relatedCluId, "relatedCluId");
		checkForMissingParameter(cluCluRelationTypeKey, "luLuRelationTypeKey");
		checkForMissingParameter(cluCluRelationInfo, "cluCluRelationInfo");

		if (cluId.equals(relatedCluId)) {
			throw new CircularRelationshipException(
					"Can not relate a Clu to itself");
		}

		// Validate CluCluRelationInfo
		// TODO KSCM List<ValidationResultInfo> val = validateCluCluRelation("SYSTEM", cluCluRelationInfo);
		//if(null != val && val.size() > 0) {
		//	throw new DataValidationErrorException("Validation error!", val);
		//}


		Clu clu = luDao.fetch(Clu.class, cluId);
		Clu relatedClu = luDao.fetch(Clu.class, relatedCluId);

		CluCluRelation cluCluRelation = new CluCluRelation();
		BeanUtils.copyProperties(cluCluRelationInfo, cluCluRelation,
				new String[] { "cluId", "relatedCluId",
						"isCluRelationRequired", "attributes", "metaInfo" });

		cluCluRelation.setClu(clu);
		cluCluRelation.setRelatedClu(relatedClu);
		cluCluRelation.setCluRelationRequired(cluCluRelationInfo
				.getIsCluRelationRequired() == null ? true : cluCluRelationInfo
				.getIsCluRelationRequired()); // TODO maybe this is unnecessary,
		// contract specifies not null
		// TODO KSCM cluCluRelation.setAttributes(LuServiceAssembler.toGenericAttributes(
		//		CluCluRelationAttribute.class, cluCluRelationInfo
		//				.getAttributes(), cluCluRelation, luDao));

		LuLuRelationType luLuRelationType = luDao.fetch(LuLuRelationType.class,
		        cluCluRelationTypeKey);

		cluCluRelation.setLuLuRelationType(luLuRelationType);

		luDao.create(cluCluRelation);

		return LuServiceAssembler.toCluCluRelationInfo(cluCluRelation);
	}

	@Override
	@Transactional(readOnly=false)
	public CluCluRelationInfo updateCluCluRelation(String cluCluRelationId, CluCluRelationInfo cluCluRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
		checkForMissingParameter(cluCluRelationId, "cluCluRelationId");
		checkForMissingParameter(cluCluRelationInfo, "cluCluRelationInfo");

		// Validate CluCluRelationInfo
		// TODO KSCM List<ValidationResultInfo> val = validateCluCluRelation("SYSTEM", cluCluRelationInfo);
		//if(null != val && val.size() > 0) {
		//	throw new DataValidationErrorException("Validation error!", val);
		//}

		final CluCluRelation cluCluRelation = luDao.fetch(CluCluRelation.class,
				cluCluRelationId);
		BeanUtils.copyProperties(cluCluRelationInfo, cluCluRelation,
				new String[] { "cluId", "relatedCluId",
						"isCluRelationRequired", "attributes", "metaInfo" });

		cluCluRelation.setClu(luDao.fetch(Clu.class, cluCluRelationInfo
				.getCluId()));
		cluCluRelation.setRelatedClu(luDao.fetch(Clu.class, cluCluRelationInfo
				.getRelatedCluId()));
		cluCluRelation.setCluRelationRequired(cluCluRelationInfo
				.getIsCluRelationRequired() == null ? true : cluCluRelationInfo
				.getIsCluRelationRequired()); // TODO maybe this is unnecessary,
		// contract specifies not null
		// TODO KSCM cluCluRelation.setAttributes(LuServiceAssembler.toGenericAttributes(
		//		CluCluRelationAttribute.class, cluCluRelationInfo
		//				.getAttributes(), cluCluRelation, luDao));

		cluCluRelation.setLuLuRelationType(luDao.fetch(LuLuRelationType.class,
				cluCluRelationInfo.getType()));

		final CluCluRelation update = luDao.update(cluCluRelation);

		return LuServiceAssembler.toCluCluRelationInfo(update);
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo deleteCluCluRelation(String cluCluRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluCluRelationId, "cluCluRelationId");

		luDao.delete(CluCluRelation.class, cluCluRelationId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	public List<ValidationResultInfo> validateCluPublication(String validationTypeKey, String cluId, String luPublicationTypeKey, CluPublicationInfo cluPublicationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(validationTypeKey, "validationType");
		checkForMissingParameter(cluPublicationInfo, "cluPublicationInfo");
		
        ObjectStructureDefinition objStructure = this.getObjectStructure(CluPublicationInfo.class.getName(), contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(cluPublicationInfo, objStructure, contextInfo);
        return validationResults;
	}

	@Override
	@Transactional(readOnly=false)
	public CluPublicationInfo createCluPublication(String cluId, String luPublicationTypeKey, CluPublicationInfo cluPublicationInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, AlreadyExistsException, DoesNotExistException {
		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(luPublicationTypeKey, "luPublicationType");
		checkForMissingParameter(cluPublicationInfo, "cluPublicationInfo");
		
		// Validate CLU
		/* TODO KSCM
		List<ValidationResultInfo> val;
		try {
			val = validateCluPublication("SYSTEM", cluPublicationInfo);
			if(null != val && val.size() > 0) {
				throw new DataValidationErrorException("Validation error!", val);
			}
		} catch (DoesNotExistException e) {
			throw new OperationFailedException("Error creating clu",e);
		} */

		
		CluPublication cluPub = new CluPublication();
		Clu clu;
		try {
			clu = luDao.fetch(Clu.class, cluId);
		} catch (DoesNotExistException e) {
			throw new InvalidParameterException("Clu does not exist for id:"+cluId);
		}
		
		CluPublicationType type;
		try{
			type = luDao.fetch(CluPublicationType.class, luPublicationTypeKey);
		} catch (DoesNotExistException e) {
			throw new InvalidParameterException("CluPublication Type does not exist for id:" + luPublicationTypeKey);
		}
		
		cluPub.setClu(clu);
		cluPub.setId(cluPublicationInfo.getId());
		cluPub.setEndCycle(cluPublicationInfo.getEndCycle());
		cluPub.setStartCycle(cluPublicationInfo.getStartCycle());
		cluPub.setEffectiveDate(cluPublicationInfo.getEffectiveDate());
		cluPub.setExpirationDate(cluPublicationInfo.getExpirationDate());
		cluPub.setState(cluPublicationInfo.getState());
		cluPub.setType(type);
		// TODO KSCM cluPub.setAttributes(LuServiceAssembler.toGenericAttributes(CluPublicationAttribute.class, cluPublicationInfo.getAttributes(), cluPub, luDao));
		cluPub.setVariants(LuServiceAssembler.toCluPublicationVariants(cluPublicationInfo.getVariants(), cluPub, luDao));

        luDao.create(cluPub);

		return LuServiceAssembler.toCluPublicationInfo(cluPub);
	}

	@Override
	@Transactional(readOnly=false)
	public CluPublicationInfo updateCluPublication(String cluPublicationId, CluPublicationInfo cluPublicationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
		checkForMissingParameter(cluPublicationId, "cluPublicationId");
		checkForMissingParameter(cluPublicationInfo, "cluPublicationInfo");
		
		// Validate CLU
		/* TODO KSCM
		List<ValidationResultInfo> val;
		try {
			val = validateCluPublication("SYSTEM", cluPublicationInfo);
			if(null != val && val.size() > 0) {
				throw new DataValidationErrorException("Validation error!", val);
			}
		} catch (DoesNotExistException e) {
			throw new OperationFailedException("Error creating clu",e);
		}*/
		
		CluPublication cluPub = luDao.fetch(CluPublication.class, cluPublicationId);
		
		if (!String.valueOf(cluPub.getVersionNumber()).equals(
				cluPublicationInfo.getMeta().getVersionInd())) {
			throw new VersionMismatchException(
					"CluPublication to be updated is not the current version");
		}
		
		Clu clu;
		try {
			clu = luDao.fetch(Clu.class, cluPublicationInfo.getCluId());
		} catch (DoesNotExistException e) {
			throw new InvalidParameterException("Clu does not exist for id:"+cluPublicationInfo.getCluId());
		}
		
		CluPublicationType type;
		try{
			type = luDao.fetch(CluPublicationType.class, cluPublicationInfo.getType());
		} catch (DoesNotExistException e) {
			throw new InvalidParameterException("CluPublication Type does not exist for id:" + cluPublicationInfo.getType());
		}

        // Update the list of variants
        // Get a map of Id->object of all the currently persisted objects in the
        // list
        Map<String, CluPublicationVariant> oldVariantMap = new HashMap<String, CluPublicationVariant>();
        for (CluPublicationVariant variant : cluPub.getVariants()) {
            oldVariantMap.put(variant.getKey(), variant);
        }
        cluPub.getVariants().clear();

        // Loop through the new list, if the item exists already update and
        // remove from the list otherwise create a new entry
        CluPublicationVariant variant = null;
        for (FieldInfo fieldInfo : cluPublicationInfo.getVariants()) {
            if (!oldVariantMap.containsKey(fieldInfo.getId())) {
                // New variant key
                variant = new CluPublicationVariant();
                variant.setKey(fieldInfo.getId());
                variant.setValue(fieldInfo.getValue());
            } else {
                // Update existing variant
                variant = oldVariantMap.get(fieldInfo.getId());
                variant.setValue(fieldInfo.getValue());
                oldVariantMap.remove(fieldInfo.getId());
            }

            cluPub.getVariants().add(variant);
        }

        // Now delete anything left over
        for (Entry<String, CluPublicationVariant> entry : oldVariantMap.entrySet()) {
            luDao.delete(entry.getValue());
        }
       
		cluPub.setClu(clu);
		cluPub.setEndCycle(cluPublicationInfo.getEndCycle());
		cluPub.setStartCycle(cluPublicationInfo.getStartCycle());
		cluPub.setEffectiveDate(cluPublicationInfo.getEffectiveDate());
		cluPub.setExpirationDate(cluPublicationInfo.getExpirationDate());
		cluPub.setState(cluPublicationInfo.getState());
		cluPub.setType(type);
		// TODO KSCM cluPub.setAttributes(LuServiceAssembler.toGenericAttributes(CluPublicationAttribute.class, cluPublicationInfo.getAttributes(), cluPub, luDao));

        CluPublication updated = luDao.update(cluPub);

		return LuServiceAssembler.toCluPublicationInfo(updated);
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo deleteCluPublication(String cluPublicationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluPublicationId, "cluPublicationId");

		luDao.delete(CluPublication.class, cluPublicationId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;	}

	@Override
	public List<ValidationResultInfo> validateCluResult(String validationTypeKey, String cluId, String cluResultTypeKey, CluResultInfo cluResultInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(validationTypeKey, "validationType");
		checkForMissingParameter(cluResultInfo, "cluResultInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluResultInfo.class.getName(), contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(cluResultInfo, objStructure, contextInfo);
        return validationResults;
	}

	@Override
	@Transactional(readOnly=false)
	public CluResultInfo createCluResult(String cluId, String cluResultTypeKey, CluResultInfo cluResultInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, AlreadyExistsException {

		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(cluResultTypeKey, "cluResultTypeKey");
		checkForMissingParameter(cluResultInfo, "cluResultInfo");

		// Validate CluResult
		/* TODO KSCM
		List<ValidationResultInfo> val = validateCluResult("SYSTEM", cluResultInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!", val);
		}*/

		cluResultInfo.setType(cluResultTypeKey);
		cluResultInfo.setCluId(cluId);

		List<ResultOption> resOptList = new ArrayList<ResultOption>();
		for (ResultOptionInfo resOptInfo : cluResultInfo.getResultOptions()) {
			ResultOption resOpt = new ResultOption();
			BeanUtils.copyProperties(resOptInfo, resOpt, new String[] { "id",
					"metaInfo", "resultUsageType", "desc" });

			if(resOptInfo.getResultUsageTypeKey() != null) {
				ResultUsageType resUsageType = luDao.fetch(ResultUsageType.class,
						resOptInfo.getResultUsageTypeKey());
				resOpt.setResultUsageType(resUsageType);
			}
			// TODO KSCM resOpt.setDesc(LuServiceAssembler.toRichText(LuRichText.class, resOptInfo.getDescr()));
			luDao.create(resOpt);
			resOptList.add(resOpt);
		}

		CluResult cluResult = new CluResult();
		BeanUtils.copyProperties(cluResultInfo, cluResult, new String[] { "id",
				"desc", "resultOptions", "metaInfo" });

		// TODO KSCM cluResult.setDesc(LuServiceAssembler
		//		.toRichText(LuRichText.class, cluResultInfo.getDescr()));
		cluResult.setResultOptions(resOptList);

		Clu clu = luDao.fetch(Clu.class, cluId);
		cluResult.setClu(clu);

		CluResultType type = luDao.fetch(CluResultType.class, cluResultTypeKey);
		cluResult.setCluResultType(type);

		luDao.create(cluResult);

		return LuServiceAssembler.toCluResultInfo(cluResult);
	}

	@Override
	@Transactional(readOnly=false)
	public CluResultInfo updateCluResult(String cluResultId, CluResultInfo cluResultInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

		checkForMissingParameter(cluResultId, "cluResultId");
		checkForMissingParameter(cluResultInfo, "cluResultInfo");

		// Validate CluResult
		/* TODO KSCM
		List<ValidationResultInfo> val = validateCluResult("SYSTEM", cluResultInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!", val);
		}*/

		CluResult result = luDao.fetch(CluResult.class, cluResultId);
		if (!String.valueOf(result.getVersionNumber()).equals(
				cluResultInfo.getMeta().getVersionInd())) {
			throw new VersionMismatchException(
					"CluResult to be updated is not the current version");
		}

		// Update the list of resultoptions
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, ResultOption> oldResultOptionMap = new HashMap<String, ResultOption>();
		for (ResultOption opt : result.getResultOptions()) {
			oldResultOptionMap.put(opt.getId(), opt);
		}
		result.getResultOptions().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list otherwise create a new entry
		for (ResultOptionInfo resOptInfo : cluResultInfo.getResultOptions()) {
			ResultOption opt = oldResultOptionMap.remove(resOptInfo.getId());
			if (opt == null) {
				// New result option
				opt = new ResultOption();
				// Copy properties
				BeanUtils.copyProperties(resOptInfo, opt, new String[] {
						"resultUsageType", "desc" });
			} else {
				// Get existing result option
				opt = luDao.fetch(ResultOption.class, resOptInfo.getId());
				// Copy properties
				BeanUtils.copyProperties(resOptInfo, opt, new String[] {
						"id", "resultUsageType", "desc" });
			}
			if(resOptInfo.getResultUsageTypeKey() != null && !resOptInfo.getResultUsageTypeKey().isEmpty()) {
				ResultUsageType resUsageType = luDao.fetch(ResultUsageType.class,
						resOptInfo.getResultUsageTypeKey());
				opt.setResultUsageType(resUsageType);
			}
			// TODO KSCM opt.setDesc(LuServiceAssembler.toRichText(LuRichText.class, resOptInfo.getDescr()));
			result.getResultOptions().add(opt);
		}

		// Now delete anything left over
		for (Entry<String, ResultOption> entry : oldResultOptionMap.entrySet()) {
			luDao.delete(entry.getValue());
		}

		BeanUtils.copyProperties(cluResultInfo, result, new String[] { "id",
				"desc", "resultOptions" });

		// TODO KSCM result.setDesc(LuServiceAssembler.toRichText(LuRichText.class, cluResultInfo.getDescr()));
		CluResultType type = luDao.fetch(CluResultType.class, cluResultInfo.getType());
		result.setCluResultType(type);

		CluResult updated = luDao.update(result);

		return LuServiceAssembler.toCluResultInfo(updated);
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo deleteCluResult(String cluResultId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(cluResultId, "cluResultId");

		luDao.delete(CluResult.class, cluResultId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	public List<ValidationResultInfo> validateCluLoRelation(String validationTypeKey, String cluId, String loId, String cluLoRelationTypeKey, CluLoRelationInfo cluLoRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(validationTypeKey, "validationType");
		checkForMissingParameter(cluLoRelationInfo, "cluLoRelationInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluLoRelation.class.getName(), contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(cluLoRelationInfo, objStructure, contextInfo);
        return validationResults;
	}

	@Override
	@Transactional(readOnly=false)
	public CluLoRelationInfo createCluLoRelation(String cluId, String loId, String cluLoRelationTypeKey, CluLoRelationInfo cluLoRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, AlreadyExistsException {
		checkForMissingParameter(loId, "loId");
		checkForMissingParameter(cluId, "cluId");
		checkForEmptyList(cluLoRelationTypeKey, "cluLoRelationType");
		checkForEmptyList(cluLoRelationInfo, "cluLoRelationInfo");

		// Validate CluLoRelation
		// TODO KSCM List<ValidationResultInfo> val = validateCluLoRelation("SYSTEM", cluLoRelationInfo);
		//if(null != val && val.size() > 0) {
		//	throw new DataValidationErrorException("Validation error!", val);
		//}

		Clu clu = luDao.fetch(Clu.class, cluId);
		if (clu == null) {
			throw new DoesNotExistException("Clu does not exist for id: "
					+ cluId);
		}
		
		CluLoRelationType cluLoRelationTypeEntity = luDao.fetch(CluLoRelationType.class, cluLoRelationTypeKey);
		if (cluLoRelationTypeEntity == null) {
			throw new DoesNotExistException("CluLoRelationType does not exist for id: "
					+ cluLoRelationTypeKey);
		}

		// Check to see if this relation already exists
		List<CluLoRelation> reltns = luDao.getCluLoRelationsByCludIdAndLoId(
				cluId, loId);
		if (reltns.size() > 0) {
			throw new AlreadyExistsException(
					"Relation already exists for cluId:" + cluId + " and Lo:"
							+ loId);
		}

		CluLoRelation cluLoRelation = new CluLoRelation();
		BeanUtils.copyProperties(cluLoRelationInfo, cluLoRelation,
				new String[] { "cluId", "attributes", "metaInfo", "type" });

		cluLoRelation.setClu(clu);
		// TODO KSCM cluLoRelation.setAttributes(LuServiceAssembler.toGenericAttributes(
		//		CluLoRelationAttribute.class,
		//		cluLoRelationInfo.getAttributes(), cluLoRelation, luDao));
		cluLoRelation.setType(cluLoRelationTypeEntity);
		
		luDao.create(cluLoRelation);

		return LuServiceAssembler.toCluLoRelationInfo(cluLoRelation);
	}

	@Override
	@Transactional(readOnly=false)
	public CluLoRelationInfo updateCluLoRelation(String cluLoRelationId, CluLoRelationInfo cluLoRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
		checkForMissingParameter(cluLoRelationId, "cluLoRelationId");
		checkForMissingParameter(cluLoRelationInfo, "cluLoRelationInfo");

		// Validate CluLoRelation
		// TODO KSCM List<ValidationResultInfo> val = validateCluLoRelation("SYSTEM", cluLoRelationInfo);
		//if(null != val && val.size() > 0) {
		//	throw new DataValidationErrorException("Validation error!", val);
		//}

		CluLoRelation reltn = luDao.fetch(CluLoRelation.class, cluLoRelationId);

		if (!String.valueOf(reltn.getVersionNumber()).equals(
				cluLoRelationInfo.getMeta().getVersionInd())) {
			throw new VersionMismatchException(
					"CluLoRelation to be updated is not the current version");
		}

		Clu clu = luDao.fetch(Clu.class, cluLoRelationInfo.getCluId());
		if (clu == null) {
			throw new DoesNotExistException("Clu does not exist for id: "
					+ cluLoRelationInfo.getCluId());
		}

		CluLoRelationType cluLoRelationTypeEntity = luDao.fetch(CluLoRelationType.class, cluLoRelationInfo.getType());
		if (cluLoRelationTypeEntity == null) {
			throw new DoesNotExistException("CluLoRelationType does not exist for id: "
					+ cluLoRelationInfo.getType());
		}
		
		BeanUtils.copyProperties(cluLoRelationInfo, reltn, new String[] {
				"cluId", "attributes", "metaInfo", "type"});

		reltn.setClu(clu);
		// TODO KSCM reltn.setAttributes(LuServiceAssembler.toGenericAttributes(
		//		CluLoRelationAttribute.class,
		//		cluLoRelationInfo.getAttributes(), reltn, luDao));
		reltn.setType(cluLoRelationTypeEntity);
		CluLoRelation updated = luDao.update(reltn);

		return LuServiceAssembler.toCluLoRelationInfo(updated);
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo deleteCluLoRelation(String cluLoRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(cluLoRelationId, "cluLoRelationId");

		CluLoRelation reltn = luDao.fetch(CluLoRelation.class, cluLoRelationId);
		if (reltn == null) {
			throw new DoesNotExistException(
					"CluLoRelation does not exist for id: " + cluLoRelationId);
		}

		luDao.delete(CluLoRelation.class, cluLoRelationId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo addCluResourceRequirement(String resourceTypeKey, String cluId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	      throw new UnsupportedOperationException("Method not yet implemented!");
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo removeCluResourceRequirement(String resourceTypeKey, String cluId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResultInfo> validateCluSet(String validationTypeKey, String cluSetTypeKey, CluSetInfo cluSetInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(validationTypeKey, "validationType");
		checkForMissingParameter(cluSetInfo, "cluSetInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluSetInfo.class.getName(), contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(cluSetInfo, objStructure, contextInfo);
        return validationResults;
	}

	@Override
	@Transactional(readOnly=false)
	public CluSetInfo createCluSet(String cluSetTypeKey, CluSetInfo cluSetInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException, AlreadyExistsException {

		checkForMissingParameter(cluSetTypeKey, "cluSetType");
		checkForMissingParameter(cluSetInfo, "cluSetInfo");

		cluSetInfo.setType(cluSetTypeKey);

		validateCluSet(cluSetInfo);

		// Validate CluSet
		/* TODO KSCM
		List<ValidationResultInfo> val;
		try {
			val = validateCluSet("SYSTEM", cluSetInfo);
		} catch (DoesNotExistException e) {
			throw new DataValidationErrorException("Validation error! " + e.getMessage());
		}
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!", val);
		}*/

		List<String> cluIdList = getMembershipQuerySearchResult(cluSetInfo.getMembershipQuery());

		CluSet cluSet = null;
		try {
			cluSet = LuServiceAssembler.toCluSetEntity(cluSetInfo, this.luDao);
		} catch (DoesNotExistException e) {
			throw new DataValidationErrorException("Creating CluSet entity failed. Clu or CluSet does not exist: " + e.getMessage());
		}

		cluSet = luDao.create(cluSet);

		CluSetInfo newCluSetInfo = LuServiceAssembler.toCluSetInfo(cluSet);

		if(cluIdList != null) {
			newCluSetInfo.getCluIds().addAll(cluIdList);
		}

		return newCluSetInfo;
	}

	private void setMembershipQuerySearchResult(CluSetInfo cluSetInfo) throws MissingParameterException {
		if(cluSetInfo.getMembershipQuery() == null) {
			return;
		}
		List<String> cluIds = getMembershipQuerySearchResult(cluSetInfo.getMembershipQuery());
		cluSetInfo.getCluIds().addAll(cluIds);
	}

	private List<String> getMembershipQuerySearchResult(MembershipQueryInfo query) throws MissingParameterException {
		if(query == null) {
			return null;
		}
		SearchRequest sr = new SearchRequest();
		sr.setSearchKey(query.getSearchTypeKey());
		sr.setParams(query.getQueryParamValueList());

		SearchResult result = search(sr);

		Set<String> cluIds = new HashSet<String>();
		List<SearchResultRow> rows = result.getRows();
		for(SearchResultRow row : rows) {
			List<SearchResultCell> cells = row.getCells();
			for(SearchResultCell cell : cells) {
				if(cell.getKey().equals("lu.resultColumn.luOptionalVersionIndId")&&cell.getValue()!=null) {
					cluIds.add(cell.getValue());
				}
			}
		}
		return new ArrayList<String>(cluIds);
	}

	private void validateCluSet(CluSetInfo cluSetInfo) throws UnsupportedActionException {
		MembershipQueryInfo mqInfo = cluSetInfo.getMembershipQuery();

		if (cluSetInfo.getType() == null) {
			throw new UnsupportedActionException("CluSet type cannot be null. CluSet id="+cluSetInfo.getId());
		}
		else if(mqInfo != null && mqInfo.getSearchTypeKey() != null && !mqInfo.getSearchTypeKey().isEmpty() &&
				(cluSetInfo.getCluIds().size() > 0 || cluSetInfo.getCluSetIds().size() > 0)) {
			throw new UnsupportedActionException("Dynamic CluSet cannot contain Clus and/or CluSets. CluSet id="+cluSetInfo.getId());
		}
		else if (cluSetInfo.getCluIds().size() > 0 && cluSetInfo.getCluSetIds().size() > 0) {
			throw new UnsupportedActionException("CluSet cannot contain both Clus and CluSets. CluSet id="+cluSetInfo.getId());
		}
	}

	@Override
	@Transactional(readOnly=false)
	public CluSetInfo updateCluSet(String cluSetId, CluSetInfo cluSetInfo, ContextInfo contextInfo) throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException, VersionMismatchException {

		// Check Missing params
		checkForMissingParameter(cluSetId, "cluSetId");
		checkForMissingParameter(cluSetInfo, "cluSetInfo");

		// Validate CluSet
		/* TODO KSCM
		List<ValidationResultInfo> val = validateCluSet("SYSTEM", cluSetInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!", val);
		}*/

		cluSetInfo.setId(cluSetId);

		validateCluSet(cluSetInfo);

		List<String> cluIdList = getMembershipQuerySearchResult(cluSetInfo.getMembershipQuery());

		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

		if (!cluSetInfo.getType().equals(cluSet.getType())) {
			throw new UnsupportedActionException("CluSet type is set at creation time and cannot be updated. CluSet id="+cluSetId);
		}

		if (!String.valueOf(cluSet.getVersionNumber()).equals(
				cluSetInfo.getMeta().getVersionInd())) {
			throw new VersionMismatchException(
					"CluSet (id=" + cluSetId +
					") to be updated is not the current version " +
					"(version=" + cluSetInfo.getMeta().getVersionInd() +
					"), current version="+cluSet.getVersionNumber());
		}

		// update the cluIds
		Map<String, CluSetJoinVersionIndClu> oldClus = new HashMap<String, CluSetJoinVersionIndClu>();
		for(CluSetJoinVersionIndClu join:cluSet.getCluVerIndIds()){
			oldClus.put(join.getCluVersionIndId(), join);
		}

		cluSet.getCluVerIndIds().clear();
		// Loop through the new list, if the item exists already update and remove from the list otherwise create a new entry
		for (String newCluId : cluSetInfo.getCluIds()) {
			CluSetJoinVersionIndClu join = oldClus.remove(newCluId);
			if (join == null) {
				join = new CluSetJoinVersionIndClu();
				join.setCluSet(cluSet);
				join.setCluVersionIndId(newCluId);
			}
			cluSet.getCluVerIndIds().add(join);
		}

		// Now delete anything left over
		for (Entry<String, CluSetJoinVersionIndClu> entry : oldClus.entrySet()) {
			luDao.delete(entry.getValue());
		}

        // clean up existing wrappers if any
        if (cluSetInfo.getId() != null) {
            CluSetInfo originalCluSet = getCluSet(cluSetInfo.getId(), contextInfo);
            List<CluSetInfo> origSubCSs = null;
            List<String> origSubCSIds = originalCluSet.getCluSetIds();
            if (origSubCSIds != null && !origSubCSIds.isEmpty()) {
                // TODO KSCM origSubCSs = getCluSetInfoByIdList(origSubCSIds);
            }
            if (origSubCSs != null) {
                for (CluSetInfo origSubCS : origSubCSs) {
                    if (!origSubCS.getIsReusable()) {
                        deleteCluSet(origSubCS.getId(), contextInfo);
                    }
                }
            }
        }

		// update the cluSetIds
		if(cluSet.getCluSets()==null){
			cluSet.setCluSets(new ArrayList<CluSet>());
		}
		cluSet.setCluSets(null);
		if(!cluSetInfo.getCluSetIds().isEmpty()) {
			Set<String> newCluSetIds = new HashSet<String>(cluSetInfo.getCluSetIds());
			if(cluSet.getCluSets()!=null){
				for (Iterator<CluSet> i = cluSet.getCluSets().iterator(); i.hasNext();) {
					if (!newCluSetIds.remove(i.next().getId())) {
						i.remove();
					}
				}
			}
			List<CluSet> cluSetList = luDao.getCluSetInfoByIdList(new ArrayList<String>(newCluSetIds));
			cluSet.setCluSets(cluSetList);
		}

		BeanUtils.copyProperties(cluSetInfo, cluSet, new String[] { "descr",
				"attributes", "metaInfo", "membershipQuery" });
		// TODO KSCM cluSet.setAttributes(LuServiceAssembler.toGenericAttributes(
		//		CluSetAttribute.class, cluSetInfo.getAttributes(), cluSet, luDao));
		// TODO KSCM cluSet.setDescr(LuServiceAssembler.toRichText(LuRichText.class, cluSetInfo.getDescr()));

		MembershipQuery mq = LuServiceAssembler.toMembershipQueryEntity(cluSetInfo.getMembershipQuery());
		cluSet.setMembershipQuery(mq);

		CluSet updated = luDao.update(cluSet);

		CluSetInfo updatedCluSetInfo = LuServiceAssembler.toCluSetInfo(updated);

		if(cluIdList != null) {
			updatedCluSetInfo.getCluIds().addAll(cluIdList);
		}

		return updatedCluSetInfo;
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo deleteCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(cluSetId, "cluSetId");

		luDao.delete(CluSet.class, cluSetId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo addCluSetToCluSet(String cluSetId, String addedCluSetId, ContextInfo contextInfo) throws CircularRelationshipException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
		checkForMissingParameter(cluSetId, "cluSetId");
		checkForMissingParameter(addedCluSetId, "addedCluSetId");

		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

		checkCluSetAlreadyAdded(cluSet, addedCluSetId);

		CluSet addedCluSet = luDao.fetch(CluSet.class, addedCluSetId);

		checkCluSetCircularReference(addedCluSet, cluSetId);

		if(cluSet.getCluSets()==null){
			cluSet.setCluSets(new ArrayList<CluSet>());
		}
		cluSet.getCluSets().add(addedCluSet);

		luDao.update(cluSet);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo removeCluSetFromCluSet(String cluSetId, String removedCluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {

		checkForMissingParameter(cluSetId, "cluSetId");
		checkForMissingParameter(removedCluSetId, "removedCluSetId");

		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		if(cluSet.getCluSets()!=null){
			for (Iterator<CluSet> i = cluSet.getCluSets().iterator(); i.hasNext();) {
				CluSet childCluSet = i.next();
				if (childCluSet.getId().equals(removedCluSetId)) {
					i.remove();
					luDao.update(cluSet);
					StatusInfo statusInfo = new StatusInfo();
					statusInfo.setSuccess(true);
	
					return statusInfo;
				}
			}
		}

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(false);
		statusInfo.setMessage("CluSet does not contain CluSet:"
				+ removedCluSetId);

		return statusInfo;
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo addCluToCluSet(String cluId, String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {

		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(cluSetId, "cluSetId");

		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

		checkCluAlreadyAdded(cluSet, cluId);
		
		try{
			luDao.getCurrentCluVersionInfo(cluId, CluServiceConstants.CLU_NAMESPACE_URI);
		}catch(NoResultException e){
			throw new DoesNotExistException();
		}
		
		CluSetJoinVersionIndClu join = new CluSetJoinVersionIndClu();
		join.setCluSet(cluSet);
		join.setCluVersionIndId(cluId);
		
		cluSet.getCluVerIndIds().add(join);

		luDao.update(cluSet);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo removeCluFromCluSet(String cluId, String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {

		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(cluSetId, "cluSetId");

		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

		for (Iterator<CluSetJoinVersionIndClu> i = cluSet.getCluVerIndIds().iterator(); i.hasNext();) {
			CluSetJoinVersionIndClu join = i.next();
			if (join.getCluVersionIndId().equals(cluId)) {
				i.remove();
				luDao.delete(join);
				luDao.update(cluSet);
				StatusInfo statusInfo = new StatusInfo();
				statusInfo.setSuccess(true);

				return statusInfo;
			}
		}

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(false);
		statusInfo.setMessage("Clu set does not contain Clu:" + cluId);

		return statusInfo;
	}

	@Override
	public List<ValidationResultInfo> validateLui(String validationType, LuiInfo luiInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(luiInfo, "luiInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(LuiInfo.class.getName(), contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(luiInfo, objStructure, contextInfo);
        return validationResults;
	}

	@Override
	@Transactional(readOnly=false)
	public LuiInfo createLui(String cluId, String atpKey, LuiInfo luiInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedOperationException {
		checkForMissingParameter(cluId, "cludId");
		checkForMissingParameter(atpKey, "atpKey");
		checkForMissingParameter(luiInfo, "luiInfo");

		// Validate Lui
		List<ValidationResultInfo> val = validateLui("SYSTEM", luiInfo, contextInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!", val);
		}

		Lui lui = new Lui();
		luiInfo.setCluId(cluId);
		luiInfo.setAtpId(atpKey);

		try {
			lui = LuServiceAssembler.toLui(false, luiInfo, luDao);
		} catch (VersionMismatchException vme) {
		}

		luDao.create(lui);

		return LuServiceAssembler.toLuiInfo(lui);
	}

	@Override
	@Transactional(readOnly=false)
	public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, UnsupportedOperationException {

		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(luiInfo, "luiInfo");

		// Validate Lui
		List<ValidationResultInfo> val = validateLui("SYSTEM", luiInfo, contextInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!", val);
		}

		Lui lui = luDao.fetch(Lui.class, luiId);

		if (!String.valueOf(lui.getVersionNumber()).equals(
				luiInfo.getMetaInfo().getVersionInd())) {
			throw new VersionMismatchException(
					"Lui to be updated is not the current version");
		}

		Clu clu = luDao.fetch(Clu.class, luiInfo.getCluId());
		lui.setClu(clu);

		lui.setAttributes(LuServiceAssembler.toGenericAttributes(
				LuiAttribute.class, luiInfo.getAttributes(), lui, luDao));

		// Now copy standard properties
		BeanUtils.copyProperties(luiInfo, lui, new String[] { "cluId",
				"attributes" });

		Lui updated = luDao.update(lui);

		return LuServiceAssembler.toLuiInfo(updated);
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo deleteLui(String luiId, ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedOperationException {

		checkForMissingParameter(luiId, "luiId");

		luDao.delete(Lui.class, luiId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	@Transactional(readOnly=false)
	public LuiInfo updateLuiState(String luiId, String luiState, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedOperationException {

		// check for missing params
		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(luiState, "luiState");
		Lui lui = luDao.fetch(Lui.class, luiId);
		lui.setState(luiState);
		Lui updated = luDao.update(lui);
		return LuServiceAssembler.toLuiInfo(updated);
	}

	@Override
	public List<ValidationResultInfo> validateLuiLuiRelation(String validationType, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(luiLuiRelationInfo, "luiLuiRelationInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(LuiLuiRelation.class.getName(), contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(luiLuiRelationInfo, objStructure, contextInfo);
        return validationResults;
	}

	@Override
	@Transactional(readOnly=false)
	public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId, String luLuRelationTypeKey, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo) throws AlreadyExistsException, CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedOperationException {
		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(relatedLuiId, "relatedLuiId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
		checkForMissingParameter(luiLuiRelationInfo, "luiLuiRelationInfo");

		// Validate LuiLuiRelation
		List<ValidationResultInfo> val = validateLuiLuiRelation("SYSTEM", luiLuiRelationInfo, contextInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!", val);
		}

		if (luiId.equals(relatedLuiId)) {
			throw new CircularRelationshipException(
					"Can not relate a Lui to itself");
		}

		Lui lui = luDao.fetch(Lui.class, luiId);
		Lui relatedLui = luDao.fetch(Lui.class, relatedLuiId);

		LuiLuiRelation luiLuiRelation = new LuiLuiRelation();
		BeanUtils.copyProperties(luiLuiRelationInfo, luiLuiRelation,
				new String[] { "luiId", "relatedLuiId", "attributes",
						"metaInfo" });

		luiLuiRelation.setLui(lui);
		luiLuiRelation.setRelatedLui(relatedLui);
		luiLuiRelation.setAttributes(LuServiceAssembler.toGenericAttributes(
				LuiLuiRelationAttribute.class, luiLuiRelationInfo
						.getAttributes(), luiLuiRelation, luDao));

		LuLuRelationType luLuRelationType = luDao.fetch(LuLuRelationType.class,
				luLuRelationTypeKey);

		luiLuiRelation.setLuLuRelationType(luLuRelationType);

		luDao.create(luiLuiRelation);

		return LuServiceAssembler.toLuiLuiRelationInfo(luiLuiRelation);
	}

	@Override
	@Transactional(readOnly=false)
	public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, UnsupportedOperationException {

		checkForMissingParameter(luiLuiRelationId, "luiLuiRelationId");
		checkForMissingParameter(luiLuiRelationInfo, "luiLuiRelationInfo");

		// Validate LuiLuiRelation
		List<ValidationResultInfo> val = validateLuiLuiRelation("SYSTEM", luiLuiRelationInfo, contextInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!", val);
		}

		LuiLuiRelation luiLuiRelation = luDao.fetch(LuiLuiRelation.class,
				luiLuiRelationId);

		if (!String.valueOf(luiLuiRelation.getVersionNumber()).equals(
				luiLuiRelationInfo.getMetaInfo().getVersionInd())) {
			throw new VersionMismatchException(
					"LuiLuiRelation to be updated is not the current version");
		}

		BeanUtils.copyProperties(luiLuiRelationInfo, luiLuiRelation,
				new String[] { "luiId", "relatedLuiId", "attributes",
						"metaInfo" });

		if (!luiLuiRelationInfo.getLuiId().equals(
				luiLuiRelation.getLui().getId())) {
			luiLuiRelation.setLui(luDao.fetch(Lui.class, luiLuiRelationInfo
					.getLuiId()));
		}

		if (!luiLuiRelationInfo.getRelatedLuiId().equals(
				luiLuiRelation.getRelatedLui().getId())) {
			luiLuiRelation.setRelatedLui(luDao.fetch(Lui.class,
					luiLuiRelationInfo.getRelatedLuiId()));
		}

		luiLuiRelation.setAttributes(LuServiceAssembler.toGenericAttributes(
				LuiLuiRelationAttribute.class, luiLuiRelationInfo
						.getAttributes(), luiLuiRelation, luDao));

		if (!luiLuiRelationInfo.getType().equals(
				luiLuiRelation.getLuLuRelationType().getId())) {
			luiLuiRelation.setLuLuRelationType(luDao.fetch(
					LuLuRelationType.class, luiLuiRelationInfo.getType()));
		}

		LuiLuiRelation updated = luDao.update(luiLuiRelation);

		return LuServiceAssembler.toLuiLuiRelationInfo(updated);
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedOperationException {

		checkForMissingParameter(luiLuiRelationId, "luiLuiRelationId");

		luDao.delete(LuiLuiRelation.class, luiLuiRelationId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	/**************************************************************************
	 * SEARCH OPERATIONS *
	 **************************************************************************/

	/* TODO KSCM
	@Override
	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		return searchManager.getSearchCriteriaType(searchCriteriaTypeKey);
	}

	@Override
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
			throws OperationFailedException {
		return searchManager.getSearchCriteriaTypes();
	}

	@Override
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
		return searchManager.getSearchResultType(searchResultTypeKey);
	}

	@Override
	public List<SearchResultTypeInfo> getSearchResultTypes()
			throws OperationFailedException {
		return searchManager.getSearchResultTypes();
	}

	@Override
	public SearchTypeInfo getSearchType(String searchTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(searchTypeKey, "searchTypeKey");
		return searchManager.getSearchType(searchTypeKey);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypes()
			throws OperationFailedException {
		return searchManager.getSearchTypes();
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
		return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
		return searchManager.getSearchTypesByResult(searchResultTypeKey);
	}*/

	private void checkCluAlreadyAdded(CluSet cluSet, String cluId)
			throws OperationFailedException {
		for (CluSetJoinVersionIndClu join : cluSet.getCluVerIndIds()) {
			if (join.getCluVersionIndId().equals(cluId)) {
				throw new OperationFailedException("CluSet already contains Clu (id='" + cluId + "')");
			}
		}
	}

	private void checkCluSetAlreadyAdded(CluSet cluSet, String cluSetIdToAdd)
			throws OperationFailedException {
		if(cluSet.getCluSets()!=null){
			for (CluSet childCluSet : cluSet.getCluSets()) {
				if (childCluSet.getId().equals(cluSetIdToAdd)) {
					throw new OperationFailedException("CluSet (id=" + cluSet.getId() +
							") already contains CluSet (id='" + cluSetIdToAdd + "')");
				}
			}
		}
	}

	private void checkCluSetCircularReference(CluSet addedCluSet, String hostCluSetId)
			throws CircularRelationshipException {
		if (addedCluSet.getId().equals(hostCluSetId)) {
			throw new CircularRelationshipException(
					"Cannot add a CluSet (id=" + hostCluSetId + ") to ifself");
		}
		if(addedCluSet.getCluSets()!=null){
			for (CluSet childSet : addedCluSet.getCluSets()) {
				if (childSet.getId().equals(hostCluSetId)) {
					throw new CircularRelationshipException(
							"CluSet (id=" + hostCluSetId +
							") already contains this CluSet (id=" +
							childSet.getId() + ")");
				}
				checkCluSetCircularReference(childSet, hostCluSetId);
			}
		}
	}

	private void findClusInCluSet(List<String> clus, CluSet parentCluSet)
			throws DoesNotExistException {
        List<String> processedCluSetIds = new ArrayList<String>();
        doFindClusInCluSet(processedCluSetIds, clus, parentCluSet);
	}
	
	private void doFindClusInCluSet(List<String> processedCluSetIds, 
	        List<String> clus, CluSet parentCluSet) {
        for (CluSetJoinVersionIndClu join : parentCluSet.getCluVerIndIds()) {
            if (!clus.contains(join.getCluVersionIndId())) {
                clus.add(join.getCluVersionIndId());
            }
        }
        if(parentCluSet.getCluSets()!=null){
            for (CluSet cluSet : parentCluSet.getCluSets()) {
                // This condition avoids infinite recursion problem
                if (!processedCluSetIds.contains(cluSet.getId())) {
                    processedCluSetIds.add(cluSet.getId());
                    doFindClusInCluSet(processedCluSetIds, clus, cluSet);
                }
            }
        }
	}

	@Override
	public ObjectStructureDefinition getObjectStructure(String objectTypeKey, ContextInfo contextInfo) throws UnsupportedOperationException {
		return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
	}

	@Override
	public List<String> getObjectTypes(ContextInfo contextInfo) throws UnsupportedOperationException {
		return dictionaryServiceDelegate.getObjectTypes();
	}

	public LuDao getLuDao() {
		return luDao;
	}

	public void setLuDao(LuDao luDao) {
		this.luDao = luDao;
	}

	@Override
	public SearchResult search(SearchRequest searchRequest) throws MissingParameterException {
        checkForMissingParameter(searchRequest, "searchRequest");
        
        if(SEARCH_KEY_DEPENDENCY_ANALYSIS.equals(searchRequest.getSearchKey())){
        	String cluId = null;
    		for(SearchParam param:searchRequest.getParams()){
    			if("lu.queryParam.luOptionalCluId".equals(param.getKey())){
    				cluId = (String)param.getValue();
    				break;
    			}
    		}
        	try {
				return doDependencyAnalysisSearch(cluId);
			} catch (DoesNotExistException e) {
				throw new RuntimeException("Error performing search");//FIXME should be more checked service exceptions thrown
			}
        }else if(SEARCH_KEY_BROWSE_PROGRAM.equals(searchRequest.getSearchKey())){
        	return doBrowseProgramSearch();
        }
        return searchManager.search(searchRequest, luDao);
	}

	private SearchResult doBrowseProgramSearch() throws MissingParameterException {
		//This is our main result
		SearchResult programSearchResults = searchManager.search(new SearchRequest(SEARCH_KEY_BROWSE_PROGRAM), luDao);
		
		//These variations need to be mapped back to the program search results
		SearchResult variationSearchResults = searchManager.search(new SearchRequest(SEARCH_KEY_BROWSE_VARIATIONS), luDao);
		
		//Get a mapping of program id to variation long name mapping:
		Map<String,List<String>> variationMapping = new HashMap<String,List<String>>();
		for(SearchResultRow row:variationSearchResults.getRows()){
			String programId = null;
			String variationLongName = null;
			for(SearchResultCell cell:row.getCells()){
				if("lu.resultColumn.cluId".equals(cell.getKey())){
					programId = cell.getValue();
				}else if("lu.resultColumn.luOptionalLongName".equals(cell.getKey())){
					variationLongName = cell.getValue();
				}
			}
			List<String> variationLongNames = variationMapping.get(programId);
			if(variationLongNames == null){
				variationLongNames = new ArrayList<String>();
				variationMapping.put(programId, variationLongNames);
			}
			variationLongNames.add(variationLongName);
		}
		
		
		//The result component types need to be mapped back as well
		SearchRequest resultComponentSearchRequest = new SearchRequest(SEARCH_KEY_RESULT_COMPONENT);
		resultComponentSearchRequest.addParam("lrc.queryParam.resultComponent.type", "kuali.resultComponentType.degree");
		SearchResult resultComponentSearchResults = searchDispatcher.dispatchSearch(resultComponentSearchRequest);
		
		//Get a mapping of result type id to result type name:
		Map<String,String> resultComponentMapping = new HashMap<String,String>();
		for(SearchResultRow row:resultComponentSearchResults.getRows()){
			String resultComponentTypeId = null;
			String resultComponentTypeName = null;
			for(SearchResultCell cell:row.getCells()){
				if("lrc.resultColumn.resultComponent.id".equals(cell.getKey())){
					resultComponentTypeId = cell.getValue();
				}else if("lrc.resultColumn.resultComponent.name".equals(cell.getKey())){
					resultComponentTypeName = cell.getValue();
				}
			}
			resultComponentMapping.put(resultComponentTypeId, resultComponentTypeName);
		}
		
		Map<String, Set<SearchResultCell>> orgIdToCellMapping = new HashMap<String, Set<SearchResultCell>>();
		Map<String, Set<SearchResultCell>> resultComponentToCellMapping = new HashMap<String, Set<SearchResultCell>>(); 
		Map<String, Set<SearchResultCell>> campusToCellMapping = new HashMap<String, Set<SearchResultCell>>();
		Map<String, SearchResultCell> progIdToOrgCellMapping = new HashMap<String, SearchResultCell>(); 
		Map<String, SearchResultCell> progIdToResultComponentCellMapping = new HashMap<String, SearchResultCell>(); 
		Map<String, SearchResultCell> progIdToCampusCellMapping = new HashMap<String, SearchResultCell>();
		
		
		//We need to reduce the programSearchResults, translating variations, result options, etc and creating a mapping for org id translation
		for(Iterator<SearchResultRow> rowIter = programSearchResults.getRows().iterator();rowIter.hasNext();){
			SearchResultRow row = rowIter.next();
			String programId = null;
			String orgId = null;
			String resultComponentName = null;
			String campusCode = null;
			SearchResultCell orgCell = null;
			SearchResultCell resultComponentCell = null;
			SearchResultCell variationCell = null;
			SearchResultCell campusLocationCell = null;
			
			for(SearchResultCell cell:row.getCells()){
				if("lu.resultColumn.cluId".equals(cell.getKey())){
					programId = cell.getValue();
				}else if("lu.resultColumn.luOptionalAdminOrg".equals(cell.getKey())){
					orgId = cell.getValue();
					orgCell = cell;
				}else if("lu.resultColumn.resultComponentId".equals(cell.getKey())){
					resultComponentName = resultComponentMapping.get(cell.getValue());
					resultComponentCell = cell;
				}else if("lu.resultColumn.variationId".equals(cell.getKey())){
					variationCell = cell;
				}else if("lu.resultColumn.luOptionalCampusLocation".equals(cell.getKey())){
					campusLocationCell = cell;
					campusCode = cell.getValue();
				}
			}
			if(!progIdToOrgCellMapping.containsKey(programId)){
				//Add in the Variations
				List<String> variations = variationMapping.get(programId);
				variationCell.setValue("");
				if(variations!=null){
					for(Iterator<String> variationIter = variations.iterator();variationIter.hasNext();){
						String variation = variationIter.next();
						if(variationIter.hasNext()){
							variation += "<br/>";
						}
						variationCell.setValue(variationCell.getValue()+variation);
					}
				}

				//Add the cell to the org id mapping
				Set<SearchResultCell> orgCells = orgIdToCellMapping.get(orgId);
				if(orgCells == null){
					orgCells = new HashSet<SearchResultCell>();
					orgIdToCellMapping.put(orgId, orgCells);
				}
				orgCells.add(orgCell);
				orgCell.setValue(null);
				
								
				//Add this to the map
				Set<SearchResultCell> campusCells = campusToCellMapping.get(campusCode);
				if(campusCells == null){
					campusCells = new HashSet<SearchResultCell>();
					campusToCellMapping.put(campusCode, campusCells);
				}
				campusCells.add(campusLocationCell);
				campusLocationCell.setValue(null);
				
				//Add this to the map
				Set<SearchResultCell> resultCells = resultComponentToCellMapping.get(resultComponentName);
				if(resultCells == null){
					resultCells = new HashSet<SearchResultCell>();
					resultComponentToCellMapping.put(resultComponentName, resultCells);
				}
				resultCells.add(resultComponentCell);
				resultComponentCell.setValue(null);
				
				progIdToOrgCellMapping.put(programId, orgCell);
				progIdToResultComponentCellMapping.put(programId, resultComponentCell);
				progIdToCampusCellMapping.put(programId, campusLocationCell);
			}else{
				//this row already exists so we need to concatenate the result component and add the org id
				//Get the result component row
				Set<SearchResultCell> resultCells = resultComponentToCellMapping.get(resultComponentName);
				if(resultCells == null){
					resultCells = new HashSet<SearchResultCell>();
					resultComponentToCellMapping.put(resultComponentName, resultCells);
				}
				resultCells.add(progIdToResultComponentCellMapping.get(programId));
				
				//Add a new mapping to the org cell for this org id
				Set<SearchResultCell> orgCells = orgIdToCellMapping.get(orgId);
				if(orgCells == null){
					orgCells = new HashSet<SearchResultCell>();
					orgIdToCellMapping.put(orgId, orgCells);
				}
				orgCells.add(progIdToOrgCellMapping.get(programId));
				
				//Concatenate the campus location
				Set<SearchResultCell> campusCells = campusToCellMapping.get(campusCode);
				if(campusCells == null){
					campusCells = new HashSet<SearchResultCell>();
					campusToCellMapping.put(campusCode, campusCells);
				}
				campusCells.add(progIdToCampusCellMapping.get(programId));
				
				//Remove this row from results
				rowIter.remove();
			}
		}
		
		if(!resultComponentToCellMapping.isEmpty()){
			List<String> resultComponentNames = new ArrayList<String>(resultComponentToCellMapping.keySet());
			Collections.sort(resultComponentNames);
			for(String resultComponentName:resultComponentNames){
				//Concatenate resultComponent names in the holder cells
				Set<SearchResultCell> cells = resultComponentToCellMapping.get(resultComponentName);
				if(cells!=null){
					for(SearchResultCell cell:cells){
						if(cell.getValue()==null){
							cell.setValue(resultComponentName);
						}else{
							cell.setValue(cell.getValue()+"<br/>"+resultComponentName);
						}
					}
				}
			}
		}
		
		if(!campusToCellMapping.isEmpty()){
			List<String> campusCodes = new ArrayList<String>(campusToCellMapping.keySet());
			Collections.sort(campusCodes);
			for(String campusCode:campusCodes){
				//Concatenate campus code names in the holder cells
				Set<SearchResultCell> cells = campusToCellMapping.get(campusCode);
				if(cells!=null){
					for(SearchResultCell cell:cells){
						if(cell.getValue()==null){
							cell.setValue(campusCode);
						}else{
							cell.setValue(cell.getValue()+"<br/>"+campusCode);
						}
					}
				}
			}
		}
		
		//Use the org search to Translate the orgIds into Org names and update the holder cells
		if(!orgIdToCellMapping.isEmpty()){
			//Perform the Org search
			SearchRequest orgIdTranslationSearchRequest = new SearchRequest("org.search.generic");
			orgIdTranslationSearchRequest.addParam("org.queryParam.orgOptionalIds", new ArrayList<String>(orgIdToCellMapping.keySet()));
			orgIdTranslationSearchRequest.setSortColumn("org.resultColumn.orgShortName");
			SearchResult orgIdTranslationSearchResult = searchDispatcher.dispatchSearch(orgIdTranslationSearchRequest);
			
			//For each translation, update the result cell with the translated org name
			for(SearchResultRow row:orgIdTranslationSearchResult.getRows()){
				
				//Get Params
				String orgId="";
				String orgName="";
				for(SearchResultCell cell:row.getCells()){
					if("org.resultColumn.orgId".equals(cell.getKey())){
						orgId = cell.getValue();
						continue;
					}else if("org.resultColumn.orgShortName".equals(cell.getKey())){
						orgName = cell.getValue();
					}
				}
				
				//Concatenate org names in the holder cells
				Set<SearchResultCell> cells = orgIdToCellMapping.get(orgId);
				if(cells!=null){
					for(SearchResultCell cell:cells){
						if(cell.getValue()==null){
							cell.setValue(orgName);
						}else{
							cell.setValue(cell.getValue()+"<br/>"+orgName);
						}
					}
				}
			}
		}

		return programSearchResults;
	}

	private SearchResult doDependencyAnalysisSearch(String cluId) throws MissingParameterException, DoesNotExistException {

		checkForMissingParameter(cluId, "cluId");

		Clu triggerClu = luDao.fetch(Clu.class, cluId);
		
		List<String> cluVersionIndIds = new ArrayList<String>();
		cluVersionIndIds.add(triggerClu.getVersion().getVersionIndId());
		
		//Find all clusets that contain this course
		List<CluSet> cluSets = luDao.getCluSetsByCluVersionIndId(cluVersionIndIds);
		
		//Get a mapping of clusetId to cluset for easy referencing
		Map<String, CluSet> cluSetMap = new HashMap<String, CluSet>();
		if(cluSets!=null){
			for(CluSet cluSet:cluSets){
				cluSetMap.put(cluSet.getId(), cluSet);
			}
		}
		
		//Execute all dynamic queries to see if the target clu is in the cluset and add those clusets
		List<CluSet> dynamicCluSets = luDao.getAllDynamicCluSets();
		if(dynamicCluSets!=null){
			for(CluSet cluSet:dynamicCluSets){
				MembershipQueryInfo queryInfo = LuServiceAssembler.toMembershipQueryInfo(cluSet.getMembershipQuery());
				List<String> memberCluVersionIndIds = getMembershipQuerySearchResult(queryInfo);
				if(memberCluVersionIndIds!=null){
					for(String cluVersionIndId:cluVersionIndIds){
						if(memberCluVersionIndIds.contains(cluVersionIndId)){
							cluSetMap.put(cluSet.getId(),cluSet);
							break;
						}
					}
				}
			}
		}		
		//TODO Is it possible we need to search up the cluset hierarchies?
		//	If Cluset A contains clu 1 and cluset B contains cluset A, do we also return cluset B as a dependency?
		
		//Now we have the clu id and the list of clusets that the id appears in,
		//We need to do a statement service search to see what statements use these as 
		//dependencies
		SearchRequest statementSearchRequest = new SearchRequest("stmt.search.dependencyAnalysis");
		
		statementSearchRequest.addParam("stmt.queryParam.cluSetIds", new ArrayList<String>(cluSetMap.keySet()));
		statementSearchRequest.addParam("stmt.queryParam.cluVersionIndIds", cluVersionIndIds);
		
		SearchResult statementSearchResult = searchDispatcher.dispatchSearch(statementSearchRequest);
		
		//Create a search result for the return value
		SearchResult searchResult = new SearchResult();
		
		Map<String,List<SearchResultCell>> orgIdToCellMapping = new HashMap<String,List<SearchResultCell>>();
		
		//Now we need to take the statement ids and find the clus that relate to them
		//We will also transform the search result from the statement search result to 
		//the dependency analysis search result
		Set<String> processed = new HashSet<String>();
		for(SearchResultRow stmtRow:statementSearchResult.getRows()){

			//Determine result column values
			String refObjId = null;
			String statementType = null;
			String statementTypeName = null;
			String rootId = null;
			String requirementComponentIds = null;
			
			for(SearchResultCell stmtCell:stmtRow.getCells()){
				if("stmt.resultColumn.refObjId".equals(stmtCell.getKey())){
					refObjId = stmtCell.getValue();
					continue;
				}else if("stmt.resultColumn.statementTypeId".equals(stmtCell.getKey())){
					statementType = stmtCell.getValue();
					continue;
				}else if("stmt.resultColumn.statementTypeName".equals(stmtCell.getKey())){
					statementTypeName = stmtCell.getValue();
					continue;
				}else if("stmt.resultColumn.rootId".equals(stmtCell.getKey())){
					rootId = stmtCell.getValue();
					continue;
				}else if("stmt.resultColumn.requirementComponentIds".equals(stmtCell.getKey())){
					requirementComponentIds = stmtCell.getValue();
				}
			}
			
			//Find the clu
			Clu clu = luDao.fetch(Clu.class, refObjId);

			//Program statements are attached to dummy clus, so look up the parent program
			if("kuali.lu.type.Requirement".equals(clu.getLuType().getId())){
				
				List<Clu> clus = luDao.getClusByRelatedCluId(clu.getId(), "kuali.lu.lu.relation.type.hasProgramRequirement");
				
				rootId = clu.getId();

				if(clus==null||clus.size()==0){
					throw new RuntimeException("Statement Dependency clu found, but no parent Program exists");
				}else if(clus.size()>1){
					throw new RuntimeException("Statement Dependency clu can only have one parent Program relation");
				}
				clu = clus.get(0);
			}

			//Only process clus that are not active and that we have not already processed
			String rowId = clu.getId()+"|"+statementType+"|"+rootId;
			
			if("Active".equals(clu.getState()) && !processed.contains(rowId)){
				
				processed.add(rowId);
				
				SearchResultRow resultRow = new SearchResultRow();
				
				//Map the result cells
				resultRow.addCell("lu.resultColumn.cluId",clu.getId());
				resultRow.addCell("lu.resultColumn.cluType",clu.getLuType().getId());
				resultRow.addCell("lu.resultColumn.luOptionalCode",clu.getOfficialIdentifier().getCode());
				resultRow.addCell("lu.resultColumn.luOptionalShortName",clu.getOfficialIdentifier().getShortName());
				resultRow.addCell("lu.resultColumn.luOptionalLongName",clu.getOfficialIdentifier().getLongName());
				resultRow.addCell("lu.resultColumn.luOptionalDependencyType",statementType);
				resultRow.addCell("lu.resultColumn.luOptionalDependencyTypeName",statementTypeName);	
				resultRow.addCell("lu.resultColumn.luOptionalDependencyRootId",rootId);
				resultRow.addCell("lu.resultColumn.luOptionalDependencyRequirementComponentIds",requirementComponentIds);
				
				//Make a holder cell for the org names, to be populated later
				SearchResultCell orgIdsCell = new SearchResultCell("lu.resultColumn.luOptionalOversightCommitteeIds",null);
				resultRow.getCells().add(orgIdsCell);

				//Make a holder cell for the org ids, to be populated later
				SearchResultCell orgNamesCell = new SearchResultCell("lu.resultColumn.luOptionalOversightCommitteeNames",null);
				resultRow.getCells().add(orgNamesCell);
				
				//For each curriculum oversight committee we want to look up the Org Name
				//We're going to save a mapping of the org id to a holder cell so we can make just one org 
				//service call with all the org ids, and update the holder cells later.
				boolean differentAdminOrg = true;
				for(CluAdminOrg adminOrg:clu.getAdminOrgs()){
					if("kuali.adminOrg.type.CurriculumOversight".equals(adminOrg.getType()) || 
					   "kuali.adminOrg.type.CurriculumOversightUnit".equals(adminOrg.getType())){
						
						//Add the cell to the mapping for that perticular org id
						List<SearchResultCell> cells = orgIdToCellMapping.get(adminOrg.getOrgId());
						if(cells == null){
							cells = new ArrayList<SearchResultCell>();
							orgIdToCellMapping.put(adminOrg.getOrgId(), cells);
						}
						cells.add(orgNamesCell);
						
						//Add the orgid to the orgIds cell so there is a comma delimited list of org ids
						if(orgIdsCell.getValue()==null){
							orgIdsCell.setValue(adminOrg.getId());
						}else{
							orgIdsCell.setValue(orgIdsCell.getValue()+","+adminOrg.getId());
						}
						
						for(CluAdminOrg triggerAdminOrg:triggerClu.getAdminOrgs()){
							if(triggerAdminOrg.getOrgId().equals(adminOrg.getOrgId())){
								differentAdminOrg = false;
							}
						}
					}
				}
				resultRow.addCell("lu.resultColumn.luOptionalDependencyRequirementDifferentAdminOrg", String.valueOf(differentAdminOrg));
				
				//Add the result row
				searchResult.getRows().add(resultRow);
			}
		}
		
		//Use the org search to Translate the orgIds into Org names and update the holder cells
		if(!orgIdToCellMapping.isEmpty()){
			//Perform the Org search
			SearchRequest orgIdTranslationSearchRequest = new SearchRequest("org.search.generic");
			orgIdTranslationSearchRequest.addParam("org.queryParam.orgOptionalIds", new ArrayList<String>(orgIdToCellMapping.keySet()));
			SearchResult orgIdTranslationSearchResult = searchDispatcher.dispatchSearch(orgIdTranslationSearchRequest);
			
			//For each translation, update the result cell with the translated org name
			for(SearchResultRow row:orgIdTranslationSearchResult.getRows()){
				
				//Get Params
				String orgId="";
				String orgName="";
				for(SearchResultCell cell:row.getCells()){
					if("org.resultColumn.orgId".equals(cell.getKey())){
						orgId = cell.getValue();
						continue;
					}else if("org.resultColumn.orgShortName".equals(cell.getKey())){
						orgName = cell.getValue();
					}
				}
				
				//Concatenate org names in the holder cells
				List<SearchResultCell> cells = orgIdToCellMapping.get(orgId);
				if(cells!=null){
					for(SearchResultCell cell:cells){
						if(cell.getValue()==null){
							cell.setValue(orgName);
						}else{
							cell.setValue(cell.getValue()+", "+orgName);
						}
					}
				}
			}
		}
		
		//Add in CluSets and ignore ones named AdHoc
		for(CluSet cluSet:cluSetMap.values()){
			if(!"AdHock".equals(cluSet.getName())){

				SearchResultRow resultRow = new SearchResultRow();
				
				resultRow.addCell("lu.resultColumn.cluId",cluSet.getId());
				resultRow.addCell("lu.resultColumn.luOptionalShortName",cluSet.getName());
				resultRow.addCell("lu.resultColumn.luOptionalLongName",cluSet.getName());
				resultRow.addCell("lu.resultColumn.luOptionalDependencyType","cluSet");
				resultRow.addCell("lu.resultColumn.luOptionalDependencyTypeName", "Course Set");			

				searchResult.getRows().add(resultRow);
			}
		}
		
		//Get any joints here and add them into the results
		List<Clu> joints = luDao.getClusByRelation(cluId,"kuali.lu.relation.type.co-located");
		if(joints!=null){
			for(Clu clu:joints){
				
				SearchResultRow resultRow = new SearchResultRow();
				
				resultRow.addCell("lu.resultColumn.cluId", clu.getId());
				resultRow.addCell("lu.resultColumn.luOptionalCode", clu.getOfficialIdentifier().getCode());
				resultRow.addCell("lu.resultColumn.luOptionalShortName", clu.getOfficialIdentifier().getShortName());
				resultRow.addCell("lu.resultColumn.luOptionalLongName", clu.getOfficialIdentifier().getLongName());	
				resultRow.addCell("lu.resultColumn.luOptionalDependencyType", "joint");
				resultRow.addCell("lu.resultColumn.luOptionalDependencyTypeName", "jointly offered");
				
				searchResult.getRows().add(resultRow);
			}
		}
		
		//Lookup cross-listings and add to the results
		for(CluIdentifier altId:triggerClu.getAlternateIdentifiers()){
			if("kuali.lu.type.CreditCourse.identifier.crosslisting".equals(altId.getType())){
				SearchResultRow resultRow = new SearchResultRow();
				
				resultRow.addCell("lu.resultColumn.luOptionalCode", altId.getCode());
				resultRow.addCell("lu.resultColumn.luOptionalShortName", altId.getShortName());
				resultRow.addCell("lu.resultColumn.luOptionalLongName", altId.getLongName());	
				resultRow.addCell("lu.resultColumn.luOptionalDependencyType", "crossListed");
				resultRow.addCell("lu.resultColumn.luOptionalDependencyTypeName", "cross-listed");		
				
				searchResult.getRows().add(resultRow);
			}
		}

		//Sort results by Code
		Collections.sort(searchResult.getRows(), new SearchResultRowComparator("lu.resultColumn.luOptionalCode"));
		
		return searchResult;
	}
	
	public class SearchResultRowComparator implements Comparator<SearchResultRow>{
		private String sortColumn;
		
		SearchResultRowComparator(String sortColumn){
			super();
			this.sortColumn = sortColumn;
		}
		
		@Override
		public int compare(SearchResultRow o1, SearchResultRow o2) {
			String o1SortValue = null;
			String o2SortValue = null;
			for(SearchResultCell cell:o1.getCells()){
				if(sortColumn.equals(cell.getKey())){
					o1SortValue = cell.getValue();
					break;
				}
			}
			for(SearchResultCell cell:o2.getCells()){
				if(sortColumn.equals(cell.getKey())){
					o2SortValue = cell.getValue();
					break;
				}
			}
			if(o1SortValue!=null){
				if(o2SortValue==null){
					return 1;
				}
				return o1SortValue.compareTo(o2SortValue);
			}if(o2SortValue==null){
				return 0;
			}
			return -1;
		}
		
	}

	/**
	 * Check for missing parameter and throw localized exception if missing
	 *
	 * @param param
	 * @param parameter
	 *            name
	 * @throws MissingParameterException
	 */
	private void checkForMissingParameter(Object param, String paramName)
			throws MissingParameterException {
		if (param == null) {
			throw new MissingParameterException(paramName + " can not be null");
		}
	}

	/**
	 * @param param
	 * @param paramName
	 * @throws MissingParameterException
	 */
	private void checkForEmptyList(Object param, String paramName)
			throws MissingParameterException {
		if (param != null && param instanceof List<?>
				&& ((List<?>) param).size() == 0) {
			throw new MissingParameterException(paramName
					+ " can not be an empty list");
		}
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo addCluSetsToCluSet(String cluSetId, List<String> addedCluSetIds, ContextInfo contextInfo) throws CircularRelationshipException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {

		checkForMissingParameter(cluSetId, "cluSetId");
		checkForMissingParameter(addedCluSetIds, "cluSetIdList");

		// Check that CluSet exists
		luDao.fetch(CluSet.class, cluSetId);

		for(String cluSetIdToAdd : addedCluSetIds) {
			StatusInfo status = addCluSetToCluSet(cluSetId, cluSetIdToAdd, contextInfo);
			if (!status.getIsSuccess()) {
				return status;
			}
		}

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	@Transactional(readOnly=false)
	public StatusInfo addClusToCluSet(List<String> cluSetIds, String cluSetId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {

		checkForMissingParameter(cluSetIds, "cluIdList");
		checkForMissingParameter(cluSetId, "cluSetId");
		
		for(String cluId : cluSetIds) {
			StatusInfo status = addCluToCluSet(cluId, cluSetId, contextInfo);
			if (!status.getIsSuccess()) {
				return status;
			}
		}

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	public ValidatorFactory getValidatorFactory() {
		return validatorFactory;
	}

	public void setValidatorFactory(ValidatorFactory validatorFactory) {
		this.validatorFactory = validatorFactory;
	}

	/********* Versioning Methods ***************************/
	
	@Override
    @Transactional(readOnly=false)
	public CluInfo createNewCluVersion(String cluId, String versionComment, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {	    
		Clu latestClu;
		Clu currentClu; 
		try{
			latestClu = luDao.getLatestCluVersion(null); // TODO KSCM versionIndCluId
		}catch(NoResultException e){
			throw new DoesNotExistException("There are no matching versions of this clu", e);
		}
		try{
			currentClu = luDao.getCurrentCluVersion(null); // TODO KSCM versionIndCluId
		}catch(NoResultException e){
			throw new DoesNotExistException("There is no current version of this clu. Only current clus can be versioned. Use setCurrentCluVersion to make a clu current.", e);
		}
		
	    CluInfo cluInfo = LuServiceAssembler.toCluInfo(currentClu);
	    
	    // Reset the Clu
	    clearCluIds(cluInfo);
	    
	    // Create the new Clu Version	    
	    CluInfo newClu = null;
	    
        try {
    		Clu clu = toCluForCreate(cluInfo.getType(), cluInfo, contextInfo);
    	    //Set the Version data
    		Version version = new Version();
    		version.setSequenceNumber(latestClu.getVersion().getSequenceNumber() + 1);
    		// TODO KSCM version.setVersionIndId(versionIndCluId);
    		version.setCurrentVersionStart(null);
    		version.setCurrentVersionEnd(null);
    		version.setVersionComment(versionComment);
    		version.setVersionedFromId(currentClu.getId());
    		clu.setVersion(version);
    		luDao.create(clu);
            newClu = LuServiceAssembler.toCluInfo(clu); 
        } catch (AlreadyExistsException e) {
            throw new OperationFailedException("Error creating a new clu version", e);
        }
	    
	    return newClu;
	}

    private void clearCluIds(CluInfo clu) {
	    // Clear out all ids so a copy can be made
        clu.setState(DtoConstants.STATE_DRAFT);// TODO check if this should be set from outside
    	clu.setId(null);
	    	    	    
	    if(clu.getAccountingInfo()!=null){
	    	clu.getAccountingInfo().setId(null);
	    
		    for(AffiliatedOrgInfo affiliatedOrg:clu.getAccountingInfo().getAffiliatedOrgs()){
		    	affiliatedOrg.setId(null);
		    }
	    }
	    for(AccreditationInfo accredation:clu.getAccreditations()){
	    	accredation.setId(null);
	    }
	    for(AdminOrgInfo adminOrg:clu.getAdminOrgs()){
	    	adminOrg.setId(null);
	    }
	    for(CluIdentifierInfo alternateIdentifier:clu.getAlternateIdentifiers()){
	    	alternateIdentifier.setId(null);
	    }
	    if(clu.getFeeInfo()!=null){
		    clu.getFeeInfo().setId(null);
		    for(CluFeeRecordInfo cluFeeRecord:clu.getFeeInfo().getCluFeeRecords()){
		    	cluFeeRecord.setId(null);
		    	for(AffiliatedOrgInfo affiliatedOrg:cluFeeRecord.getAffiliatedOrgs()){
		    		affiliatedOrg.setId(null);
		    	}
		    	for(CurrencyAmountInfo feeAmount:cluFeeRecord.getFeeAmounts()){
		    		feeAmount.setId(null);
		    	}
		    }
	    }
	    for(LuCodeInfo luCode:clu.getLuCodes()){
	    	luCode.setId(null);
	    }
	    if(clu.getOfficialIdentifier()!=null){
	    	clu.getOfficialIdentifier().setId(null);
	    }
	}

	/**
	 * This method sets the CLU with ID of cluVersionId as the current version and will set the version end date of the previously current version to currentVersionStart or now() if null.  This will NOT update state of the current or previously current CLU.  All state changes must be handled either by the business service or from the client application. 
	 * 
	 * @param currentVersionStart if set to null, will default the current version start to the time when this method is called.
	 * You can set this to a future date as well. 
	 */
	@Override
	@Transactional(readOnly=false)
    public StatusInfo setCurrentCluVersion(String cluVersionId, Date currentVersionStart, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException {
        //Check params
		Date currentDbDate = new Date();//FIXME, this should be DB time
		if(currentVersionStart!=null&&currentVersionStart.compareTo(currentDbDate)<0){
			throw new InvalidParameterException("currentVersionStart must be in the future.");
		}
		//Default the currentVersionStart to the current date
		if(currentVersionStart==null){
			currentVersionStart = currentDbDate;
		}
		
		//get the clu we are setting as current 
		Clu clu = luDao.fetch(Clu.class, cluVersionId);
		String versionIndId = clu.getVersion().getVersionIndId();

		Clu oldClu = null;
		try{
			oldClu = luDao.getCurrentCluVersion(versionIndId);
		}catch(NoResultException e){}
		
		//Check that the clu you are trying to version has a sequence number greater than the current clu
		if(oldClu!=null){
			if(clu.getVersion().getSequenceNumber()<=oldClu.getVersion().getSequenceNumber()){
				throw new OperationFailedException("Clu to make current must have been versioned from the current Clu");
			}
		}else{
			//Ignore the start date set if this is the first version (it will be set to the current time to avoid weird time problems)
			currentVersionStart = currentDbDate;
		}
		
		
		//Get any clus that are set to become current in the future, and clear their current dates
		// TODO KSCM List<VersionDisplayInfo> versionsInFuture = luDao.getVersionsInDateRange(versionIndId, null, currentDbDate, null);
//		for(VersionDisplayInfo versionInFuture:versionsInFuture){
//			if(oldClu==null || !versionInFuture.getId().equals(oldClu.getId())){
//				VersionEntity futureClu = luDao.fetch(Clu.class, versionInFuture.getId());
//				futureClu.getVersion().setCurrentVersionStart(null);
//				futureClu.getVersion().setCurrentVersionEnd(null);
//				futureClu = luDao.update(futureClu);
//			}
//		}
		
		//If there is a current clu, set its end date to the new clu's start date
		if(oldClu!=null){
			oldClu.getVersion().setCurrentVersionEnd(currentVersionStart);
			oldClu = luDao.update(oldClu);
		}
		
		//Set the startdate of the new current clu
		clu.getVersion().setCurrentVersionStart(currentVersionStart);
		clu.getVersion().setCurrentVersionEnd(null);
		clu = luDao.update(clu);
		
		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);
        return statusInfo;
    }   
	
    @Override
    public VersionDisplayInfo getLatestVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		VersionDisplayInfo versionInfo = null;
		if(CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectUri)){
        	try{
        	 // TODO KSCM versionInfo = luDao.getLatestVersion(refObjectId, refObjectUri);
        	}catch(NoResultException e){
        		throw new DoesNotExistException();
        	}
        }else{
        	throw new UnsupportedOperationException("This method does not know how to handle object type:"+refObjectUri);
        }
		return versionInfo;
	}

	@Override
    public VersionDisplayInfo getCurrentVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		VersionDisplayInfo versionInfo = null;
		if(CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectUri)){
        	try{
        	 // TODO KSCM versionInfo = luDao.getCurrentCluVersionInfo(refObjectId, refObjectUri);
        	}catch(NoResultException e){
        		throw new DoesNotExistException();
        	}
        }else{
        	throw new UnsupportedOperationException("This method does not know how to handle object type:"+refObjectUri);
        }
		return versionInfo;
	}

    @Override
    public VersionDisplayInfo getCurrentVersionOnDate(String refObjectUri, String refObjectId, Date date, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		VersionDisplayInfo versionInfo = null;
		if(CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectUri)){
        	try{
        	 // TODO KSCM versionInfo = luDao.getCurrentVersionOnDate(refObjectId, refObjectUri, date);
        	}catch(NoResultException e){
        		throw new DoesNotExistException();
        	}
        }else{
        	throw new UnsupportedOperationException("This method does not know how to handle object type:"+refObjectUri);
        }
		return versionInfo;
    }

    @Override
    public VersionDisplayInfo getFirstVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		VersionDisplayInfo versionInfo = null;
		if(CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectUri)){
        	try{
        	 // TODO KSCM versionInfo = luDao.getFirstVersion(refObjectId, refObjectUri);
        	}catch(NoResultException e){
        		throw new DoesNotExistException();
        	}
        }else{
        	throw new UnsupportedOperationException("This method does not know how to handle object type:"+refObjectUri);
        }
		return versionInfo;
    }

    @Override
    public VersionDisplayInfo getVersionBySequenceNumber(String refObjectUri, String refObjectId, Long sequence, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		VersionDisplayInfo versionInfo = null;
		if(CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectUri)){
        	try{
        	 // TODO KSCM versionInfo = luDao.getVersionBySequenceNumber(refObjectId, refObjectUri, sequence);
        	}catch(NoResultException e){
        		throw new DoesNotExistException();
        	}
        }else{
        	throw new UnsupportedOperationException("This method does not know how to handle object type:"+refObjectUri);
        }
		return versionInfo;
    }

    @Override
    public List<VersionDisplayInfo> getVersions(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	List<VersionDisplayInfo> versionInfos = null;
		if(CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectUri)){
       		// TODO KSCM versionInfos = luDao.getVersions(refObjectId, refObjectUri);
       		if(versionInfos==null){
       			versionInfos = Collections.<VersionDisplayInfo>emptyList();
       		}
        }else{
        	throw new UnsupportedOperationException("This method does not know how to handle object type:"+refObjectUri);
        }
		return versionInfos;
    }

    @Override
    public List<VersionDisplayInfo> getVersionsInDateRange(String refObjectUri, String refObjectId, Date from, Date to, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	List<VersionDisplayInfo> versionInfos = null;
		if(CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectUri)){
    		// TODO KSCM versionInfos = luDao.getVersionsInDateRange(refObjectId, refObjectUri, from, to);
       		if(versionInfos==null){
       			versionInfos = Collections.<VersionDisplayInfo>emptyList();
       		}
        }else{
        	throw new UnsupportedOperationException("This method does not know how to handle object type:"+refObjectUri);
        }
		return versionInfos;
    }

	public void setSearchDispatcher(SearchDispatcher searchDispatcher) {
		this.searchDispatcher = searchDispatcher;
	}

    @Override
    public List<TypeInfo> getCluCluRelationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getDeliveryMethodTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getDeliveryMethodType(String deliveryMethodTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getInstructionalFormatTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getInstructionalFormatType(String instructionalFormatTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getLuTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getLuCodeTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getLuCodeType(String luCodeTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getLuLuRelationType(String cluCluRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getLuPublicationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getLuPublicationType(String luPublicationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getCluResultTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getCluResultType(String cluResultTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getCluResultTypesForLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getResultUsageTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getResultUsageType(String resultUsageTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getCluLoRelationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getCluLoRelationType(String cluLoRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getCluSetTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getCluSetType(String cluSetTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getLuLuRelationTypes(ContextInfo contextInfo) throws OperationFailedException, UnsupportedOperationException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getSearchCriteriaType(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getSearchCriteriaTypes() throws OperationFailedException, UnsupportedOperationException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getSearchResultType(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getSearchResultTypes() throws OperationFailedException, UnsupportedOperationException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getSearchTypes() throws OperationFailedException, UnsupportedOperationException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getSearchTypesByResult(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
        // TODO NWUuser - THIS METHOD NEEDS JAVADOCS
        return null;
    }
}
