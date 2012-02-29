package org.kuali.student.lum.lrc.service;

import java.util.List;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.dto_rice.ValidationResultInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.lum.lrc.dto.ResultValueInfo;

/**
 *
 */
public abstract class LRCServiceDecorator implements LrcService {

    private LrcService nextDecorator;
    
    public LrcService getNextDecorator()
			throws OperationFailedException {
		if (null == nextDecorator) {
			throw new OperationFailedException("Misconfigured application: nextDecorator is null");
		}
        return nextDecorator;
    }
    public void setNextDecorator(LrcService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public ResultValuesGroupInfo getResultValuesGroup(String resultValuesGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroup(resultValuesGroupId, context);
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByIdList(List<String> resultValuesGroupIdList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupsByIdList(resultValuesGroupIdList, context);
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(String resultValueId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupsByResultValue(resultValueId, context);
    }

    @Override
    public List<String> getResultValuesGroupIdsByType(String resultValuesGroupTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupIdsByType(resultValuesGroupTypeKey, context);
    }

    @Override
    public ResultValuesGroupInfo createResultValuesGroup(ResultValuesGroupInfo gradeValuesGroupInfo, ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().createResultValuesGroup(gradeValuesGroupInfo, context);
    }

    @Override
    public ResultValuesGroupInfo updateResultValuesGroup(String resultValuesGroupKey,
            ResultValuesGroupInfo gradeValuesGroupInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        return getNextDecorator().updateResultValuesGroup(resultValuesGroupKey, gradeValuesGroupInfo, context);
    }

    @Override
    public StatusInfo deleteResultValuesGroup(String resultValuesGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteResultValuesGroup(resultValuesGroupId, context);
    }

    @Override
    public ResultValueInfo getResultValue(String resultValueId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValue(resultValueId, context);
    }

    @Override
    public List<ResultValueInfo> getResultValuesByIdList(List<String> resultValueIdList, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesByIdList(resultValueIdList, context);
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroup(String resultValuesGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesForResultValuesGroup(resultValuesGroupId, context);
    }

    @Override
    public ResultValueInfo createResultValue(ResultValueInfo resultValueInfo, ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().createResultValue(resultValueInfo, context);
    }

    @Override
    public ResultValueInfo updateResultValue(String resultValueId, ResultValueInfo resultValueInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        return getNextDecorator().updateResultValue(resultValueId, resultValueInfo, context);
    }

    @Override
    public StatusInfo deleteResultValue(String resultValueId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deleteResultValue(resultValueId, context);
    }

    @Override
    public List<ValidationResultInfo> validateResultValue(String validationType, ResultValueInfo resultValueInfo,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return getNextDecorator().validateResultValue(validationType, resultValueInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateResultValuesGroup(String validationType,
            ResultValuesGroupInfo gradeValuesGroupInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateResultValuesGroup(validationType, gradeValuesGroupInfo, context);
    }

    @Override
    public ResultScaleInfo getResultScale(String resultScaleId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getResultScale(resultScaleId, context);
    }

    @Override
    public List<ResultValueInfo> getResultValuesForScale(String resultScaleKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
		   OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesForScale(resultScaleKey, context);
    }
}
