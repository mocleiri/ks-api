/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.ui.requirements.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.enumerable.dto.EnumeratedValue;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.LrTypeInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.LuStatementTypeInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.service.LuService;

public class LuServiceMock implements LuService {

	private Map<String, Object> map = new HashMap<String, Object>();
	
	public void addObject(String key, Object value) {
		this.map.put(key, value);
	}
	
	public void addAll(Map<String, Object> map) {
		this.map.putAll(map);
	}
	
	public void clear() {
		this.map.clear();
	}
	
	@Override
	public StatusInfo addCluResourceRequirement(String resourceTypeKey,
			String cluId) throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo addCluSetToCluSet(String cluSetId, String addedCluSetId)
			throws CircularReferenceException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException {
		return null;
	}

	@Override
	public StatusInfo addCluToCluSet(String cluId, String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, UnsupportedActionException {
		return null;
	}

	@Override
	public StatusInfo addLrScaleToClu(String lrScaleTypeKey, String lrTypeKey,
			String cluId) throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException {
		return null;
	}

	@Override
	public StatusInfo addLrScaleToLui(String lrScaleTypeKey, String lrTypeKey,
			String luiId) throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException {
		return null;
	}

	@Override
	public StatusInfo addLuStatementToClu(String cluId, String luStatementId)
			throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo addOutcomeLoToClu(String loId, String cluId)
			throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo addOutcomeLoToLui(String loId, String luiId)
			throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException {
		return null;
	}

	@Override
	public CluInfo createClu(String luTypeKey, CluInfo cluInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public CluCluRelationInfo createCluCluRelation(String cluId,
			String relatedCluId, String luLuRelationTypeKey,
			CluCluRelationInfo cluCluRelationInfo)
			throws AlreadyExistsException, CircularReferenceException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public CluSetInfo createEnumeratedCluSet(String cluSetName,
			CluSetInfo cluSetInfo) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, DoesNotExistException {
		return null;
	}

	@Override
	public LuDocRelationInfo createLuDocRelationForClu(
			String luDocRelationType, String documentId, String cluId,
			LuDocRelationInfo luDocRelationInfo) throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public LuiInfo createLui(String cluId, String atpKey, LuiInfo luiInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public LuiLuiRelationInfo createLuiLuiRelation(String luiId,
			String relatedLuiId, String luLuRelationTypeKey,
			LuiLuiRelationInfo luiLuiRelationInfo)
			throws AlreadyExistsException, CircularReferenceException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public LuStatementInfo createLuStatement(String luStatementType,
			LuStatementInfo luStatementInfo) throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public ReqComponentInfo createReqComponent(String reqComponentType,
			ReqComponentInfo reqComponentInfo) throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo deleteClu(String cluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			DependentObjectsExistException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo deleteCluCluRelation(String cluCluRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo deleteCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo deleteLuDocRelation(String luDocRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo deleteLui(String luiId)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo deleteLuStatement(String luStatementId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo deleteReqComponent(String reqComponentId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public List<String> getAllCluIdsInCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public List<CluInfo> getAllClusInCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public List<String> getAllowedLrScaleTypesForLuType(String luTypeKey,
			String lrTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<String> getAllowedLrTypesForLuType(String luTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<String> getAllowedLuLuRelationTypeInfosByCluId(String cluId,
			String relatedCluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<String> getAllowedLuLuRelationTypeInfosByLuiId(String luiId,
			String relatedLuiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<String> getAllowedLuLuRelationTypeInfosForLuType(
			String luTypeKey, String relatedLuTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public CluInfo getClu(String cluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return (CluInfo) map.get(cluId);
	}

	@Override
	public CluCluRelationInfo getCluCluRelation(String cluCluRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<CluCluRelationInfo> getCluCluRelationsByClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<String> getCluIdsByLoId(String loId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<String> getCluIdsByLuType(String luTypeKey, String luState)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<String> getCluIdsByRelation(String relatedCluId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<String> getCluIdsFromCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public List<CluInfo> getClusByIdList(List<String> cluIdList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<CluInfo> getClusByLuType(String luTypeKey, String luState)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<CluInfo> getClusByRelation(String relatedCluId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<String> getCluSetIdsFromCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public CluSetInfo getCluSetInfo(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return (CluSetInfo) map.get(cluSetId);
	}

	@Override
	public List<CluSetInfo> getCluSetInfoByIdList(List<String> cluSetIdList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public List<CluInfo> getClusFromCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public List<String> getClusUsingComponent(String reqComponentId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<String> getLoIdsByClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<String> getLoIdsByLui(String luiId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<String> getLrScaleTypesForClu(String cluId, String lrTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<String> getLrScaleTypesForLui(String luiId, String lrTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public LrTypeInfo getLrType(String lrTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<LrTypeInfo> getLrTypes() throws OperationFailedException {
		return null;
	}

	@Override
	public List<String> getLrTypesForClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<String> getLrTypesForLui(String luiId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public LuDocRelationInfo getLuDocRelation(String luDocRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<LuDocRelationInfo> getLuDocRelationsByClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<LuDocRelationInfo> getLuDocRelationsByDocument(String documentId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<LuDocRelationInfo> getLuDocRelationsByIdList(
			List<String> luDocRelationIdList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<LuDocRelationInfo> getLuDocRelationsByType(
			String luDocRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public LuDocRelationTypeInfo getLuDocRelationType(
			String luDocRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<LuDocRelationTypeInfo> getLuDocRelationTypes()
			throws OperationFailedException {
		return null;
	}

	@Override
	public LuiInfo getLui(String luiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<String> getLuiIdsByCluId(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<String> getLuiIdsByLoId(String loId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<String> getLuiIdsByRelation(String relatedLuiId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<LuiLuiRelationInfo> getLuiLuiRelations(String luiId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<LuiInfo> getLuisByIdList(List<String> luiIdList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<LuiInfo> getLuisByRelation(String relatedLuiId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public LuLuRelationTypeInfo getLuLuRelationTypeInfo(
			String luLuRelationTypeKey) throws OperationFailedException,
			MissingParameterException, DoesNotExistException {
		return null;
	}

	@Override
	public List<LuLuRelationTypeInfo> getLuLuRelationTypeInfos()
			throws OperationFailedException {
		return null;
	}

	@Override
	public LuStatementInfo getLuStatement(String luStatementId)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return (LuStatementInfo) map.get(luStatementId);
	}

	@Override
    public List<LuStatementInfo> getLuStatements(List<String> luStatementIdList)
		    throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		List<LuStatementInfo> list = new ArrayList<LuStatementInfo>();
		for(String id : luStatementIdList) {
			LuStatementInfo stmt = (LuStatementInfo) map.get(id);
			list.add(stmt);
		}
		return list;
    }
    
	@Override
	public List<LuStatementInfo> getLuStatementsByType(String luStatementTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<LuStatementInfo> getLuStatementsForClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public LuStatementTypeInfo getLuStatementType(String luStatementTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return (LuStatementTypeInfo) map.get(luStatementTypeKey);
	}

	@Override
	public List<LuStatementTypeInfo> getLuStatementTypes()
			throws OperationFailedException {
		return null;
	}

	@Override
	public List<LuStatementTypeInfo> getLuStatementTypesForLuStatementType(
			String luStatementTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public LuTypeInfo getLuType(String luTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<LuTypeInfo> getLuTypes() throws OperationFailedException {
		return null;
	}

	@Override
	public List<String> getRelatedCluIdsByCluId(String cluId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<CluInfo> getRelatedClusByCluId(String cluId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<String> getRelatedLuiIdsByLuiId(String luiId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<LuiInfo> getRelatedLuisByLuiId(String luiId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public ReqComponentInfo getReqComponent(String reqComponentId)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		return (ReqComponentInfo) map.get(reqComponentId);
	}

	@Override
    public List<ReqComponentInfo> getReqComponents(List<String> reqComponentIdList) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
    {
		List<ReqComponentInfo> list = new ArrayList<ReqComponentInfo>();
		for(String id : reqComponentIdList) {
			ReqComponentInfo req = (ReqComponentInfo) map.get(id);
			list.add(req);
		}
		return list;
    }
	
	@Override
	public List<ReqComponentInfo> getReqComponentsByType(
			String reqComponentTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<ReqComponentInfo> getReqComponentsUsingClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public ReqComponentTypeInfo getReqComponentType(String reqComponentTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return (ReqComponentTypeInfo) map.get(reqComponentTypeKey);
	}

	@Override
	public List<ReqComponentTypeInfo> getReqComponentTypes()
			throws OperationFailedException {
		return null;
	}

	@Override
	public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType(
			String luStatementTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<String> getResourceRequirementsForCluId(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<LuStatementInfo> getStatementsUsingComponent(
			String reqComponentId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public Boolean isCluInCluSet(String cluId, String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo removeCluFromCluSet(String cluId, String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, UnsupportedActionException {
		return null;
	}

	@Override
	public StatusInfo removeCluResourceRequirement(String resourceTypeKey,
			String cluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo removeCluSetFromCluSet(String cluSetId,
			String removedCluSetId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException {
		return null;
	}

	@Override
	public StatusInfo removeLrScaleFromClu(String lrScaleTypeKey,
			String lrTypeKey, String cluId)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo removeLrScaleFromLui(String lrScaleTypeKey,
			String lrTypeKey, String luiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo removeLuStatementFromClu(String cluId,
			String luStatementId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo removeOutcomeLoFromClu(String loId, String cluId)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public StatusInfo removeOutcomeLoFromLui(String loId, String luiId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public CluInfo updateClu(String cluId, CluInfo cluInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		return null;
	}

	@Override
	public CluCluRelationInfo updateCluCluRelation(String cluCluRelationId,
			CluCluRelationInfo cluCluRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		return null;
	}

	@Override
	public CluSetInfo updateCluSet(String cluSetId, CluSetInfo cluSetInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException, CircularReferenceException,
			UnsupportedActionException {
		return null;
	}

	@Override
	public CluInfo updateCluState(String cluId, String luState)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public LuDocRelationInfo updateLuDocRelation(String luDocRelationId,
			LuDocRelationInfo luDocRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		return null;
	}

	@Override
	public LuiInfo updateLui(String luiId, LuiInfo luiInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		return null;
	}

	@Override
	public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId,
			LuiLuiRelationInfo luiLuiRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		return null;
	}

	@Override
	public LuiInfo updateLuiState(String luiId, String luState)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public LuStatementInfo updateLuStatement(String luStatementId,
			LuStatementInfo luStatementInfo) throws CircularReferenceException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		return null;
	}

	@Override
	public ReqComponentInfo updateReqComponent(String reqComponentId,
			ReqComponentInfo reqComponentInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		return null;
	}

	@Override
	public List<ValidationResultContainer> validateClu(String validationType,
			CluInfo cluInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<ValidationResultContainer> validateCluCluRelation(
			String validationType, CluCluRelationInfo cluCluRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<ValidationResultContainer> validateLuDocRelation(
			String validationType, LuDocRelationInfo luDocRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<ValidationResultContainer> validateLuStatement(
			String validationType, LuStatementInfo luStatementInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<ValidationResultContainer> validateReqComponent(
			String validationType, ReqComponentInfo reqComponentInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public ObjectStructure getObjectStructure(String objectTypeKey) {
		return null;
	}

	@Override
	public List<String> getObjectTypes() {
		return null;
	}

	@Override
	public boolean validateObject(String objectTypeKey, String stateKey,
			String info) {
		return false;
	}

	@Override
	public boolean validateStructureData(String objectTypeKey, String stateKey,
			String info) {
		return false;
	}

	@Override
	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
			throws OperationFailedException {
		return null;
	}

	@Override
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<SearchResultTypeInfo> getSearchResultTypes()
			throws OperationFailedException {
		return null;
	}

	@Override
	public SearchTypeInfo getSearchType(String searchTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return null;
	}

	@Override
	public List<SearchTypeInfo> getSearchTypes()
			throws OperationFailedException {
		return null;
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return null;
	}

	@Override
	public List<Result> searchForResults(String searchTypeKey,
			List<QueryParamValue> queryParamValues)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public List<EnumeratedValue> getEnumeration(String enumerationKey,
			String enumContextKey, String contextValue, Date contextDate) {
		return null;
	}
}
