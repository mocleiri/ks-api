package org.kuali.student.enrollment.class1.lrc.service.impl;

import org.kuali.student.enrollment.class1.lrc.dao.ResultValueDao;
import org.kuali.student.enrollment.class1.lrc.dao.ResultValuesGroupDao;
import org.kuali.student.enrollment.class1.lrc.model.ResultValueEntity;
import org.kuali.student.enrollment.class1.lrc.model.ResultValuesGroupEntity;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class LRCServiceImpl implements LRCService {

    private ResultValuesGroupDao resultValuesGroupDao;
    private ResultValueDao resultValueDao;

    @Override
    public ResultValuesGroupInfo getResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByIdList(@WebParam(name = "resultValuesGroupIdList") List<String> resultValuesGroupIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ResultValuesGroupEntity> entities = resultValuesGroupDao.findByIds(resultValuesGroupIdList);
        List<ResultValuesGroupInfo> resultValuesGroupInfos = new ArrayList<ResultValuesGroupInfo>();
        for (ResultValuesGroupEntity entity : entities){
            resultValuesGroupInfos.add(entity.toDto());
        }
        return resultValuesGroupInfos;
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<String> getResultValuesGroupIdsByType(@WebParam(name = "resultValuesGroupTypeKey") String resultValuesGroupTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public ResultValuesGroupInfo createResultValuesGroup(@WebParam(name = "resultGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public ResultValuesGroupInfo updateResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "resultValuesGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public StatusInfo deleteResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<ValidationResultInfo> validateResultValuesGroup(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public ResultValueInfo getResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<ResultValueInfo> getResultValuesByIdList(@WebParam(name = "resultValueIdList") List<String> resultValueIdList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ResultValueEntity> entities = resultValueDao.findByIds(resultValueIdList);
        List<ResultValueInfo> infos = new ArrayList<ResultValueInfo>();
        for (ResultValueEntity entity : entities){
            infos.add(entity.toDto());
        }
        return infos;
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public ResultValueInfo createResultValue(@WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public ResultValueInfo updateResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public StatusInfo deleteResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<ValidationResultInfo> validateResultValue(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public ResultScaleInfo getResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<ResultValueInfo> getResultValuesForScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(@WebParam(name = "context") ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(@WebParam(name = "entryKey") String entryKey, @WebParam(name = "context") ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public StateProcessInfo getProcessByKey(@WebParam(name = "processKey") String processKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<String> getProcessByObjectType(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public StateInfo getState(@WebParam(name = "processKey") String processKey, @WebParam(name = "stateKey") String stateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<StateInfo> getStatesByProcess(@WebParam(name = "processKey") String processKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<StateInfo> getInitialValidStates(@WebParam(name = "processKey") String processKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public StateInfo getNextHappyState(@WebParam(name = "processKey") String processKey, @WebParam(name = "currentStateKey") String currentStateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public TypeInfo getType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(@WebParam(name = "refObjectURI") String refObjectURI, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relatedRefObjectURI") String relatedRefObjectURI, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relationTypeKey") String relationTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

    public ResultValuesGroupDao getResultValuesGroupDao() {
        return resultValuesGroupDao;
    }

    public void setResultValuesGroupDao(ResultValuesGroupDao resultValuesGroupDao) {
        this.resultValuesGroupDao = resultValuesGroupDao;
    }

    public ResultValueDao getResultValueDao() {
        return resultValueDao;
    }

    public void setResultValueDao(ResultValueDao resultValueDao) {
        this.resultValueDao = resultValueDao;
    }

}
