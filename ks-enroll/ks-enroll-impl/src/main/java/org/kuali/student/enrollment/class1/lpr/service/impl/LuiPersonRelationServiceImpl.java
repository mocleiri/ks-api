/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.class1.lpr.dao.*;
import org.kuali.student.enrollment.class1.lpr.model.*;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.lpr.dto.*;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dao.StateDao;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author sambit
 */
@WebService(name = "LuiPersonRelationService", serviceName = "LuiPersonRelationService", portName = "LuiPersonRelationService", targetNamespace = "http://student.kuali.org/wsdl/lpr")
@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class LuiPersonRelationServiceImpl implements LuiPersonRelationService {

    private LprDao lprDao;
    private LuiDao luiDao;
    private LprRosterDao lprRosterDao;
    private LprTransactionDao lprTransDao; 
    private LprTransactionItemDao lprTransItemDao;
    private StateDao stateDao;
    private LprTypeDao lprTypeDao;
    private StateService stateService;
    private LprRosterEntryDao lprRosterEntryDao;

    public void setLprTransItemDao(LprTransactionItemDao lprTransItemDao) {
        this.lprTransItemDao = lprTransItemDao;
    }

    public void setStateDao(StateDao stateDao) {
        this.stateDao = stateDao;
    }

    public LprTransactionDao getLprTransDao() {
        return lprTransDao;
    }

    public void setLprTransDao(LprTransactionDao lprTransDao) {
        this.lprTransDao = lprTransDao;
    }

    public LprDao getLprDao() {
        return lprDao;
    }

    public StateDao getLprStateDao() {
        return stateDao;
    }

    public LprTypeDao getLprTypeDao() {
        return lprTypeDao;
    }

    public LprRosterEntryDao getLprRosterEntryDao() {
        return lprRosterEntryDao;
    }

    public void setLprRosterEntryDao(LprRosterEntryDao lprRosterEntryDao) {
        this.lprRosterEntryDao = lprRosterEntryDao;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public void setLprDao(LprDao lprDao) {
        this.lprDao = lprDao;
    }

    public void setLprStateDao(StateDao stateDao) {
        this.stateDao = stateDao;
    }

    public void setLprTypeDao(LprTypeDao lprTypeDao) {
        this.lprTypeDao = lprTypeDao;
    }

    public void setLuiDao(LuiDao luiDao) {
        this.luiDao = luiDao;
    }

    public void setLprRosterDao(LprRosterDao lprRosterDao) {
        this.lprRosterDao = lprRosterDao;
    }

    private LuiPersonRelationInfo getLprsByLuiPersonAndState(String personId, String luiId, String stateKey, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    private LuiPersonRelationEntity toCluForCreate(LuiPersonRelationInfo luiPersonRelationInfo) {
        LuiPersonRelationEntity lpr = new LuiPersonRelationEntity(luiPersonRelationInfo);
        if (null != luiPersonRelationInfo.getStateKey()) {
            lpr.setPersonRelationState(stateDao.find(luiPersonRelationInfo.getStateKey()));
        }
        if (null != luiPersonRelationInfo.getTypeKey()) {
            lpr.setPersonRelationType(lprTypeDao.find(luiPersonRelationInfo.getTypeKey()));
        }

        // TODO - Attributes?
        return lpr;
    }

    @Transactional(readOnly = false)
    private String createLprFromLprTransactionItem(LprTransactionItemInfo lprTransactionItemInfo, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiPersonRelationInfo luiPersonRelation = new LuiPersonRelationInfo();
        luiPersonRelation.setCommitmentPercent(100.00F);
        luiPersonRelation.setEffectiveDate(new Date());
        luiPersonRelation.setLuiId(lprTransactionItemInfo.getNewLuiId());
        luiPersonRelation.setPersonId(lprTransactionItemInfo.getPersonId());
        luiPersonRelation.setStateKey(LuiPersonRelationServiceConstants.REGISTERED_STATE_KEY);
        luiPersonRelation.setTypeKey(LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY);
        String createdLpr;
        try {
            createdLpr = createLpr(lprTransactionItemInfo.getPersonId(), lprTransactionItemInfo.getNewLuiId(), LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY, luiPersonRelation, context);

        } catch (DisabledIdentifierException e) {
            throw new OperationFailedException(e.getMessage(), e);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException(e.getMessage(), e);
        }

        return createdLpr;
    }

    private void _checkForMissingParameter(Object param, String paramName) throws MissingParameterException {
        if (null == param) {
            throw new MissingParameterException("Parameter '" + paramName + "' cannot be null");
        }
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LuiPersonRelationEntity> luiPersonRelations = lprDao.getByLuiId(luiId);
        List<LuiPersonRelationInfo> dtos = new ArrayList<LuiPersonRelationInfo>();
        for (LuiPersonRelationEntity entity : luiPersonRelations) {
            dtos.add(entity.toDto());
        }
        return dtos;
    }

    @Override
    @Transactional
    public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList,
                String relationState, String luiPersonRelationTypeKey,
                LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, DisabledIdentifierException,
                DoesNotExistException, InvalidParameterException, MissingParameterException,
                OperationFailedException, PermissionDeniedException, ReadOnlyException {
        String lprId;
        List<String> lprIdList = new ArrayList<String>();
        for (String luiId : luiIdList) {
            lprId = createLpr(personId, luiId, luiPersonRelationTypeKey, luiPersonRelationInfo, context);
            lprIdList.add(lprId);
        }
        return lprIdList;
    }

    @Override
    public LuiPersonRelationInfo getLpr(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        LuiPersonRelationEntity lpr = lprDao.find(luiPersonRelationId);
        return null != lpr ? lpr.toDto() : null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByIdList(List<String> luiPersonRelationIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationInfo> lprInfos = new ArrayList<LuiPersonRelationInfo>();
        List<LuiPersonRelationEntity> lprEntities = lprDao.findByIds(luiPersonRelationIdList);
        for (LuiPersonRelationEntity lprEntity : lprEntities) {
            LuiPersonRelationInfo lprInfo = lprEntity.toDto();
            lprInfos.add(lprInfo);
        }
        return lprInfos;
    }

    @Override
    public List<String> getLuiIdsByPerson(String personId, String luiPersonRelationType, String relationState, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getPersonIdsByLui(String luiId, String luiPersonRelationType, String relationState, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> returnVals = new ArrayList<String>();

        returnVals.addAll(lprDao.getPersonIdsByLui(luiId, luiPersonRelationType, relationState));
        return returnVals;

    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByLuiAndPerson(String personId, String luiId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationEntity> entityList = lprDao.getLprByLuiAndPerson(personId, luiId);

        List<LuiPersonRelationInfo> infoList = new ArrayList<LuiPersonRelationInfo>();
        if (entityList != null && !entityList.isEmpty()) {
            for (LuiPersonRelationEntity entity : entityList) {
                infoList.add(entity.toDto());
            }

        }
        return infoList;

    }

    @Override
    public List<String> getLprIdsByLuiAndPerson(String personId, String luiId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> returnVals = new ArrayList<String>();

        returnVals.addAll(lprDao.getLprIdsByLuiAndPerson(personId, luiId));
        return returnVals;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPerson(String personId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationEntity> entityList = lprDao.getLprsByPerson(personId);

        List<LuiPersonRelationInfo> infoList = new ArrayList<LuiPersonRelationInfo>();
        if (entityList != null && !entityList.isEmpty()) {
            for (LuiPersonRelationEntity entity : entityList) {
                infoList.add(entity.toDto());
            }

        }
        return infoList;
    }

    @Override
    public List<String> getLprIdsByPerson(String personId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getLprIdsByLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateLpr(String validationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForLprIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional
    public String createLpr(String personId, String luiId, String luiPersonRelationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // make sure params are consistent with lprInfo:
        luiPersonRelationInfo.setPersonId(personId);
        luiPersonRelationInfo.setLuiId(luiId);
        luiPersonRelationInfo.setTypeKey(luiPersonRelationType);

        LuiPersonRelationEntity lpr = toCluForCreate(luiPersonRelationInfo);
        lprDao.persist(lpr);
        return lpr.getId();
    }

    @Override
    public List<String> createBulkRelationshipsForLui(String luiId, List<String> personIdList, String relationState, String luiPersonRelationType, LuiPersonRelationInfo luiPersonRelationInfo,
            ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional
    public LuiPersonRelationInfo updateLpr(String luiPersonRelationId, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException {
        LuiPersonRelationEntity lprEntity = lprDao.find(luiPersonRelationId);

        if (lprEntity != null) {
            LuiPersonRelationEntity modifiedLpr = new LuiPersonRelationEntity(luiPersonRelationInfo);

            if (luiPersonRelationInfo.getStateKey() != null) {
                modifiedLpr.setPersonRelationState(stateDao.find(luiPersonRelationInfo.getStateKey()));
            }

            if (luiPersonRelationInfo.getTypeKey() != null) {
                modifiedLpr.setPersonRelationType(lprTypeDao.find(luiPersonRelationInfo.getTypeKey()));
            }

            lprDao.merge(modifiedLpr);
            return lprDao.find(modifiedLpr.getId()).toDto();
        } else {
            throw new DoesNotExistException(luiPersonRelationId);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteLpr(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        _checkForMissingParameter(luiPersonRelationId, "luiPersonRelationId");

        LuiPersonRelationEntity lprEntity = lprDao.find(luiPersonRelationId);
        if (null != lprEntity) {
            lprDao.remove(lprEntity);
        } else {
            throw new DoesNotExistException("LPR entity does not exist for id '" + luiPersonRelationId + "'; cannot delete");
        }

        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException,
            DoesNotExistException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getProcessByObjectType(String objectTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        StateInfo stateInfo = stateService.getState(processKey, stateKey, context);
        return stateInfo;
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return stateService.getInitialValidStates(processKey, context);
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> searchForLprs(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLprTransaction(java.lang.String,
     *      org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LprTransactionInfo getLprTransaction(String lprTransactionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        LprTransactionEntity transactionEntity = lprTransDao.find(lprTransactionId);

        return transactionEntity.toDto();
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#deleteLprTransaction(java.lang.String,
     *      org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteLprTransaction(String lprTransactionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        StatusInfo status = new StatusInfo();

        LprTransactionEntity lprTrans = lprTransDao.find(lprTransactionId);
        if (null != lprTrans) {

            lprTransDao.remove(lprTrans);

            status.setSuccess(Boolean.TRUE);

        } else
            status.setSuccess(Boolean.FALSE);

        return status;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLprsByPersonForAtp(java.lang.String,
     *      java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtp(String personId, String atpId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public LprRosterInfo updateLprRoster(String lprRosterId, LprRosterInfo lprRosterInfo, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        LprRosterEntity lprRosterEntity = lprRosterDao.find(lprRosterId);

        if (lprRosterEntity == null) {
            throw new DoesNotExistException(lprRosterId);
        }

        LprRosterEntity modifiedLprRoster = new LprRosterEntity(lprRosterInfo);
        if (lprRosterInfo.getAssociatedLuiIds() != null && !lprRosterInfo.getAssociatedLuiIds().isEmpty()){
            List<LuiEntity> luiEntities = luiDao.findByIds(lprRosterInfo.getAssociatedLuiIds());
            modifiedLprRoster.setAssociatedLuis(luiEntities);
        }

        if (lprRosterInfo.getStateKey() != null){
            modifiedLprRoster.setLprRosterState(findState(LuiPersonRelationServiceConstants.LPRROSTER_GRADING_POCESS_KEY, lprRosterInfo.getStateKey(), context));
        }

        if (lprRosterInfo.getTypeKey() != null){
            modifiedLprRoster.setLprRosterType(lprTypeDao.find(lprRosterInfo.getTypeKey()));
        }

        lprRosterDao.merge(modifiedLprRoster);

        LprRosterEntity entity = lprRosterDao.find(modifiedLprRoster.getId());
        LprRosterInfo info = entity.toDto();
        return info;
    }

    private StateEntity findState(String processKey, String stateKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        StateEntity state = null;
        try {
            StateInfo stInfo = getState(processKey, stateKey, context);
            if (stInfo != null) {
                state = new StateEntity(stInfo);
                return state;
            } else
                throw new OperationFailedException("The state does not exist. processKey " + processKey + " and stateKey: " + stateKey);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("The state does not exist. processKey " + processKey + " and stateKey: " + stateKey);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public String createLprRoster(LprRosterInfo lprRosterInfo, ContextInfo context) throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException, DisabledIdentifierException,
            ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        LprRosterEntity rosterEntity = new LprRosterEntity(lprRosterInfo);
        rosterEntity.setId(UUIDHelper.genStringUUID());

        if (lprRosterInfo.getStateKey() != null) {
            rosterEntity.setLprRosterState(stateDao.find(lprRosterInfo.getStateKey()));
        }
        if (lprRosterInfo.getTypeKey() != null) {
            rosterEntity.setLprRosterType(lprTypeDao.find(lprRosterInfo.getTypeKey()));
        }

        if (lprRosterInfo.getAssociatedLuiIds() != null) {
            List<LuiEntity> luiEntities = luiDao.findByIds(lprRosterInfo.getAssociatedLuiIds());
            rosterEntity.setAssociatedLuis(luiEntities);
        }

        if (rosterEntity.getAttributes() != null) {
            for (LprRosterAttributeEntity attribute : rosterEntity.getAttributes()) {
                if (StringUtils.isEmpty(attribute.getId())) {
                    attribute.setId(UUIDHelper.genStringUUID());
                }
            }
        }

        lprRosterDao.persist(rosterEntity);

        rosterEntity = lprRosterDao.find(rosterEntity.getId());

        return rosterEntity.getId();
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteLprRoster(String lprRosterId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        StatusInfo status = new StatusInfo();

        LprRosterEntity entity = lprRosterDao.find(lprRosterId);

        if (entity != null) {
            status.setSuccess(true);
        } else {
            status.setSuccess(false);
        }

        /**
         * FIXME : Remove entries from KSEN_LPRROSTER_LUI_RELTN, attributes and
         * desc
         */

        lprRosterDao.remove(entity);
        return status;
    }

    @Override
    public List<LprRosterEntryInfo> getEntriesForLprRoster(String lprRosterId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<LprRosterEntryEntity> entities = lprRosterEntryDao.getLprRosterEntriesForLprRoster(lprRosterId);

        List<LprRosterEntryInfo> infos = new ArrayList<LprRosterEntryInfo>();
        if (entities != null) {
            for (LprRosterEntryEntity entry : entities) {
                infos.add(entry.toDto());
            }
        }

        return infos;

    }

    @Override
    public List<LprRosterEntryInfo> getLprRosterEntriesByIdList(List<String> lprRosterEntryIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<LprRosterEntryEntity> entities = lprRosterEntryDao.findByIds(lprRosterEntryIdList);

        List<LprRosterEntryInfo> infos = new ArrayList<LprRosterEntryInfo>();
        if (entities != null) {
            for (LprRosterEntryEntity entry : entities) {
                infos.add(entry.toDto());
            }
        }

        return infos;
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLuiAndRosterType(String luiId, String lprRosterTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<LprRosterEntity> entities = lprRosterDao.getLprRostersByLuiAndRosterType(luiId, lprRosterTypeKey);
        List<LprRosterInfo> lprRosterInfoList = new ArrayList();
        if (entities != null) {
            for (LprRosterEntity lprRosterEntity : entities) {
                lprRosterInfoList.add(lprRosterEntity.toDto());
            }
        }

        return lprRosterInfoList;
    }

    @Override
    public List<LprRosterInfo> getLprRostersByLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<LprRosterEntity> entities = lprRosterDao.getLprRostersByLui(luiId);
        List<LprRosterInfo> lprRosterInfoList = new ArrayList();
        if (entities != null) {
            for (LprRosterEntity lprRosterEntity : entities) {
                lprRosterInfoList.add(lprRosterEntity.toDto());
            }
        }

        return lprRosterInfoList;
    }

    @Override
    public LprRosterInfo getLprRoster(String lprRosterId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        LprRosterEntity entity = lprRosterDao.find(lprRosterId);
        if (entity == null) {
            return null;
        }

        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false)
    public String createLprRosterEntry(LprRosterEntryInfo lprRosterEntryInfo, ContextInfo context) throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        LprRosterEntryEntity rosterEntity = new LprRosterEntryEntity(lprRosterEntryInfo);
        rosterEntity.setId(UUIDHelper.genStringUUID());
        rosterEntity.setEffectiveDate(lprRosterEntryInfo.getEffectiveDate());
        rosterEntity.setExpirationDate(lprRosterEntryInfo.getExpirationDate());
        rosterEntity.setLprId(lprRosterEntryInfo.getLprId());
        rosterEntity.setLprRosterId(lprRosterEntryInfo.getLprRosterId());

        if (lprRosterEntryInfo.getStateKey() != null) {
            rosterEntity.setLprEntryRelationState(stateDao.find(lprRosterEntryInfo.getStateKey()));
        }
        if (lprRosterEntryInfo.getTypeKey() != null) {
            rosterEntity.setLprEntryRelationType(lprTypeDao.find(lprRosterEntryInfo.getTypeKey()));
        }

        if (rosterEntity.getAttributes() != null) {
            for (LprRosterEntryAttributeEntity attribute : rosterEntity.getAttributes()) {
                if (StringUtils.isEmpty(attribute.getId())) {
                    attribute.setId(UUIDHelper.genStringUUID());
                }
                if (attribute.getOwner() == null) {
                    attribute.setOwner(rosterEntity);
                }
            }
        }

        lprRosterEntryDao.persist(rosterEntity);

        rosterEntity = lprRosterEntryDao.find(rosterEntity.getId());

        return rosterEntity.getId();

    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteLprRosterEntry(String lprRosterEntryId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        StatusInfo status = new StatusInfo();

        LprRosterEntryEntity entity = lprRosterEntryDao.find(lprRosterEntryId);

        if (entity != null) {
            status.setSuccess(true);
        } else {
            status.setSuccess(false);
        }

        lprRosterEntryDao.remove(entity);
        return status;
    }

    @Override
    public StatusInfo insertLprRosterEntryInPosition(String lprRosterEntryId, Integer position, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo reorderLprRosterEntries(List<String> lprRosterEntryIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public LprTransactionInfo createLprTransaction(LprTransactionInfo lprTransactionInfo, ContextInfo context) throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        LprTransactionEntity lprTransactionEntity = new LprTransactionEntity(lprTransactionInfo);
        if (lprTransactionEntity.getId() == null) {
            lprTransactionEntity.setId(UUIDHelper.genStringUUID());
        }
        if (null != lprTransactionInfo.getStateKey()) {
            lprTransactionEntity.setLprTransState(stateDao.find(lprTransactionInfo.getStateKey()));
        }

        if (null != lprTransactionInfo.getTypeKey()) {
            lprTransactionEntity.setLprTransType(lprTypeDao.find(lprTransactionInfo.getTypeKey()));
        }
        if (null != lprTransactionInfo.getDescr()) {
            lprTransactionEntity.setDescr(new LprRichTextEntity(lprTransactionInfo.getDescr()));
        }

        List<LprTransactionItemEntity> lprTransItemEntities = new ArrayList<LprTransactionItemEntity>();

        for (LprTransactionItemInfo lprTransItemInfo : lprTransactionInfo.getLprTransactionItems()) {

            LprTransactionItemEntity lprTransItemEntity = createLprTransactionItem(lprTransItemInfo, context);

            lprTransItemEntities.add(lprTransItemEntity);
        }

        lprTransactionEntity.setLprTransactionItems(lprTransItemEntities);
        LprTransactionEntity existing = lprTransDao.find(lprTransactionEntity.getId());

        if (existing != null) {
            throw new AlreadyExistsException();
        }

        lprTransDao.persist(lprTransactionEntity);

        LprTransactionEntity retrived = lprTransDao.find(lprTransactionEntity.getId());

        LprTransactionInfo info = null;
        if (retrived != null) {
            info = retrived.toDto();
        }

        return info;

    }

    @Override
    @Transactional(readOnly = false)
    public LprTransactionInfo createLprTransactionFromExisting(String lprTransactionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        LprTransactionEntity existingLprTransactionEntity = lprTransDao.find(lprTransactionId);
        LprTransactionEntity newLprTransactionEntity = new LprTransactionEntity();
        if (existingLprTransactionEntity != null) {
            newLprTransactionEntity.setId(UUIDHelper.genStringUUID());
            newLprTransactionEntity.setAttributes(existingLprTransactionEntity.getAttributes());
            newLprTransactionEntity.setDescr(existingLprTransactionEntity.getDescr());
            List<LprTransactionItemEntity> newItems = new ArrayList(existingLprTransactionEntity.getLprTransactionItems().size());
            for (LprTransactionItemEntity existingItem : existingLprTransactionEntity.getLprTransactionItems()) {
                LprTransactionItemEntity newItem = new LprTransactionItemEntity();
                newItem.setId(UUIDHelper.genStringUUID());
                newItem.setExistingLuiId(lprTransactionId);
                newItem.setLprTransactionItemState(stateDao.find(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY));
                newItem.setLprTransactionItemType(existingItem.getLprTransactionItemType());
                newItem.setNewLuiId(existingItem.getNewLuiId());
                newItem.setPersonId(existingItem.getPersonId());
                newItem.setDescr(existingItem.getDescr());
            }
            newLprTransactionEntity.setLprTransactionItems(newItems);
            newLprTransactionEntity.setLprTransState(stateDao.find(LuiPersonRelationServiceConstants.LPRTRANS_NEW_STATE_KEY));
            newLprTransactionEntity.setLprTransType(existingLprTransactionEntity.getLprTransType());
            newLprTransactionEntity.setRequestingPersonId(existingLprTransactionEntity.getRequestingPersonId());
            lprTransDao.persist(newLprTransactionEntity);

        } else {
            throw new DoesNotExistException("Could not find any LPR Transaction for id : " + lprTransactionId);
        }
        LprTransactionEntity retrived = lprTransDao.find(newLprTransactionEntity.getId());
        LprTransactionInfo info = null;
        if (retrived != null) {
            info = retrived.toDto();
        } else {
            throw new OperationFailedException("");
        }
        return info;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForPersonByLui(String personId, String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateLprTransaction(String lprTransactionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public LprTransactionInfo processLprTransaction(String lprTransactionId, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        LprTransactionInfo lprTransaction = getLprTransaction(lprTransactionId, context);

        for (LprTransactionItemInfo lprTransactionItemInfo : lprTransaction.getLprTransactionItems()) {
            LprTransactionItemResultInfo lprTransResultInfo = new LprTransactionItemResultInfo();
            if (lprTransactionItemInfo.getTypeKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY)
                    || lprTransactionItemInfo.getTypeKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_ADD_TO_WAITLIST_TYPE_KEY)) {
                String lprCreated = createLprFromLprTransactionItem(lprTransactionItemInfo, context);

                lprTransResultInfo.setResultingLprId(lprCreated);

            } else if (lprTransactionItemInfo.getTypeKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_DROP_TYPE_KEY)) {
                LuiPersonRelationInfo toBeDroppedLPR = getLprsByLuiPersonAndState(lprTransactionItemInfo.getPersonId(), lprTransactionItemInfo.getExistingLuiId(),
                        LuiPersonRelationServiceConstants.REGISTERED_STATE_KEY, context);

                deleteLpr(toBeDroppedLPR.getId(), context);
                lprTransResultInfo.setResultingLprId(toBeDroppedLPR.getId());

            } else if (lprTransactionItemInfo.getTypeKey().equals(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_SWAP_TYPE_KEY)) {

                LuiPersonRelationInfo toBeDroppedLPR = getLprsByLuiPersonAndState(lprTransactionItemInfo.getPersonId(), lprTransactionItemInfo.getExistingLuiId(),
                        LuiPersonRelationServiceConstants.REGISTERED_STATE_KEY, context);
                deleteLpr(toBeDroppedLPR.getId(), context);
                String lprCreated = createLprFromLprTransactionItem(lprTransactionItemInfo, context);

                lprTransResultInfo.setResultingLprId(lprCreated);

            } else {

                throw new OperationFailedException("The LPR Transaction Item did not have one of the supported type ");
            }
            lprTransResultInfo.setStatus("SUCCESS");
            lprTransactionItemInfo.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY);
            lprTransactionItemInfo.setLprTransactionItemResult(lprTransResultInfo);

        }
        try {
            lprTransaction.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY);
            updateLprTransaction(lprTransactionId, lprTransaction, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException(ex.getMessage());
        }
        return lprTransaction;
    }

    @Override
    public List<String> getLprTransactionIdsForPerson(String personId, List<String> lprTypes, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsByIdList(List<String> lprIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForPersonByAtp(String atpKey, String personId, List<String> lprTransactionStates, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<LprTransactionItemEntity> lprTransItems = lprTransItemDao.getLprTransactionItemByPerson(personId);
        List<LprTransactionInfo> lprTransInfos = new ArrayList<LprTransactionInfo>();

        for (LprTransactionItemEntity lprTransItem : lprTransItems) {
            LuiInfo lui = luiDao.find(lprTransItem.getNewLuiId()).toDto();
            if (lui.getAtpKey().equals(atpKey)) {

                LprTransactionEntity lprTransEntity = lprTransDao.getByLprTransactionItemId(lprTransItem.getId());

                if (lprTransactionStates.contains(lprTransEntity.getLprTransState().getId()))
                    lprTransInfos.add(lprTransEntity.toDto());

            }
        }

        return lprTransInfos;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForLpr(String lprId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {


        List<LprTransactionItemEntity> lprTransItems = lprTransItemDao.getLprTransactionItemsByLpr(lprId);
        List<LprTransactionEntity> lprTrans = new ArrayList<LprTransactionEntity>();
        for (LprTransactionItemEntity lprTransItem : lprTransItems) {

            lprTrans.add(lprTransDao.getByLprTransactionItemId(lprTransItem.getId()));
        }
        List<LprTransactionInfo> lprTransInfos = new ArrayList<LprTransactionInfo>();

        for (LprTransactionEntity lprTransEntity : lprTrans) {
            lprTransInfos.add(lprTransEntity.toDto());
        }
        return lprTransInfos;
    }

    @Override
    public List<LprTransactionInfo> getLprTransactionsForLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Transactional(readOnly = false)
    @Override
    public LprTransactionInfo updateLprTransaction(String lprTransactionId, LprTransactionInfo lprTransactionInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LprTransactionEntity lprTrans = lprTransDao.find(lprTransactionId);
        
        if (null != lprTrans) {
            LprTransactionEntity modifiedLprTrans = new LprTransactionEntity(lprTransactionInfo);
            if (lprTransactionInfo.getStateKey() != null)
                modifiedLprTrans.setLprTransState(stateDao.find(lprTransactionInfo.getStateKey()));
            if (lprTransactionInfo.getTypeKey() != null)
                modifiedLprTrans.setLprTransType(lprTypeDao.find(lprTransactionInfo.getTypeKey()));

         
            List<LprTransactionItemEntity> lprTransItemEntityList = processLprTransactionItemsModification (lprTransactionInfo, lprTrans, context);
         
            modifiedLprTrans.setLprTransactionItems(lprTransItemEntityList);
            lprTransDao.merge(modifiedLprTrans);
            LprTransactionEntity lprTransToReturn = lprTransDao.find(modifiedLprTrans.getId());
            return lprTransToReturn.toDto();

        } else
            throw new DoesNotExistException(lprTransactionId);
    }

    @Transactional(readOnly = false)
    private List<LprTransactionItemEntity> processLprTransactionItemsModification(LprTransactionInfo modifiedTransactionInfo, LprTransactionEntity originalLprTransEntity, ContextInfo context) {
        List<LprTransactionItemEntity> modifiedLprTransItemEntities = new ArrayList<LprTransactionItemEntity>();
        LprTransactionInfo originalLprTransInfo = originalLprTransEntity.toDto();
        List<String> deletedItems = new ArrayList<String>();
        //Assume all original items are deleted until matched
        for (LprTransactionItemInfo originalLprTransItemInfo : originalLprTransInfo.getLprTransactionItems()) {
            deletedItems.add(originalLprTransItemInfo.getId());
        }

        for (LprTransactionItemInfo modifiedTransactionItemInfo : modifiedTransactionInfo.getLprTransactionItems()) {
            boolean existingItem = false;
            if(modifiedTransactionItemInfo.getId() != null){
                for (LprTransactionItemInfo originalLprTransItemInfo : originalLprTransInfo.getLprTransactionItems()) {
                    if(originalLprTransItemInfo.getId().equals(modifiedTransactionItemInfo.getId())){
                        existingItem = true;
                        deletedItems.remove(originalLprTransItemInfo.getId());
                    }
                }
                if (existingItem) {
                    LprTransactionItemEntity modifiedLprItemEntity = new LprTransactionItemEntity(modifiedTransactionItemInfo);
                    if (null != modifiedTransactionItemInfo.getStateKey()) {
                        modifiedLprItemEntity.setLprTransactionItemState(stateDao.find(modifiedTransactionItemInfo.getStateKey()));
                    }
                    if (null != modifiedTransactionItemInfo.getTypeKey()) {
                        modifiedLprItemEntity.setLprTransactionItemType(lprTypeDao.find(modifiedTransactionItemInfo.getTypeKey()));
                    }
                    if (null != modifiedTransactionItemInfo.getDescr()) {
                        modifiedLprItemEntity.setDescr(new LprRichTextEntity(modifiedTransactionItemInfo.getDescr()));
                    }
                    lprTransItemDao.merge(modifiedLprItemEntity);
                    modifiedLprTransItemEntities.add(modifiedLprItemEntity);
                }
            }

            if(!existingItem || modifiedTransactionItemInfo.getId() == null){
                modifiedLprTransItemEntities.add(createLprTransactionItem(modifiedTransactionItemInfo, context));
            }
        }

        for(String id: deletedItems){
            lprTransItemDao.remove( lprTransItemDao.find(id) );
        }


        return modifiedLprTransItemEntities;

    }
    @Transactional(readOnly = false)
    private LprTransactionItemEntity createLprTransactionItem(LprTransactionItemInfo lprTransactionItemInfo, ContextInfo context) {
        LprTransactionItemEntity lprTransItemEntity = new LprTransactionItemEntity(lprTransactionItemInfo);
        if (lprTransItemEntity.getId() == null) {
            lprTransItemEntity.setId(UUIDHelper.genStringUUID());
        }
        if (null != lprTransactionItemInfo.getStateKey()) {
            lprTransItemEntity.setLprTransactionItemState(stateDao.find(lprTransactionItemInfo.getStateKey()));
        }

        if (null != lprTransactionItemInfo.getTypeKey()) {
            lprTransItemEntity.setLprTransactionItemType(lprTypeDao.find(lprTransactionItemInfo.getTypeKey()));
        }
        if (null != lprTransactionItemInfo.getDescr()) {
            lprTransItemEntity.setDescr(new LprRichTextEntity(lprTransactionItemInfo.getDescr()));
        }

        lprTransItemDao.persist(lprTransItemEntity);

        return lprTransItemDao.find(lprTransItemEntity.getId());
    }

    @Override
    public List<LprTransactionInfo> searchForLprTransactions(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForLprTransactionIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LprRosterInfo> searchForLprRosters(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> searchForLprRosterIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonAndTypeForAtp(String personId, String atpKey, String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<LuiPersonRelationEntity> entityList = lprDao.getLprsByPersonAndType(personId, typeKey);
        List<LuiPersonRelationInfo> infoList = new ArrayList<LuiPersonRelationInfo>();
        for (LuiPersonRelationEntity entity : entityList) {
            LuiEntity lui = luiDao.find(entity.getLuiId());
            if (StringUtils.equals(lui.getAtpKey(), atpKey)) {
                infoList.add(entity.toDto());
            }
        }

        return infoList;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtpAndLuiType(String personId, String atpKey, String luiTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationEntity> entityList = lprDao.getLprsByPerson(personId);

        List<LuiPersonRelationInfo> infoList = new ArrayList<LuiPersonRelationInfo>();
        for (LuiPersonRelationEntity entity : entityList) {
            LuiEntity lui = luiDao.find(entity.getLuiId());
            if ((lui.getAtpKey().equals(atpKey)) && (lui.getLuiType().getId().equals(luiTypeKey))) {
                infoList.add(entity.toDto());
            }
        }

        return infoList;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonForAtpAndPersonType(String personId, String atpKey, String personTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByTypeAndLui(String typeKey, String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<LuiPersonRelationInfo> getLprsByPersonAndLuiType(String personId, String luiTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationEntity> entityList = lprDao.getLprsByPerson(personId);

        List<LuiPersonRelationInfo> infoList = new ArrayList<LuiPersonRelationInfo>();
        for (LuiPersonRelationEntity entity : entityList) {
            LuiEntity lui = luiDao.find(entity.getLuiId());
            if ((lui.getLuiType().getId().equals(luiTypeKey))) {
                infoList.add(entity.toDto());
            }
        }

        return infoList;
    }

}