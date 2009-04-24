/**
 *
 */
package org.kuali.student.lum.lrc.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.lrc.dao.LrcDao;
import org.kuali.student.lum.lrc.dto.CredentialInfo;
import org.kuali.student.lum.lrc.dto.CredentialTypeInfo;
import org.kuali.student.lum.lrc.dto.CreditInfo;
import org.kuali.student.lum.lrc.dto.CreditTypeInfo;
import org.kuali.student.lum.lrc.dto.GradeInfo;
import org.kuali.student.lum.lrc.dto.GradeTypeInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.lum.lrc.dto.ScaleInfo;
import org.kuali.student.lum.lrc.dto.StatusInfo;
import org.kuali.student.lum.lrc.entity.Credit;
import org.kuali.student.lum.lrc.service.LrcService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lindholm
 *
 */
@WebService(endpointInterface = "org.kuali.student.lum.lrc.service.LrcService", serviceName = "LrcService", portName = "LrcService", targetNamespace = "http://student.kuali.org/lum/lrc")
@Transactional
public class LrcServiceImpl implements LrcService {
	private LrcDao lrcDao;

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#compareGrades(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String compareGrades(String gradeKey, String scaleKey,
			String compareGradeKey, String compareScaleKey)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#createResultComponent(java.lang.String, org.kuali.student.lum.lrc.dto.ResultComponentInfo)
	 */
	@Override
	public ResultComponentInfo createResultComponent(
			String resultComponentTypeKey,
			ResultComponentInfo resultComponentInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#deleteResultComponent(java.lang.String)
	 */
	@Override
	public StatusInfo deleteResultComponent(String resultComponentId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredential(java.lang.String)
	 */
	@Override
	public CredentialInfo getCredential(String credentialKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialKeysByCredentialType(java.lang.String)
	 */
	@Override
	public List<String> getCredentialKeysByCredentialType(
			String credentialTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialType(java.lang.String)
	 */
	@Override
	public CredentialTypeInfo getCredentialType(String credentialTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialTypes()
	 */
	@Override
	public List<CredentialTypeInfo> getCredentialTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredentialsByKeyList(java.util.List)
	 */
	@Override
	public List<CredentialInfo> getCredentialsByKeyList(
			List<String> credentialKeyList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCredit(java.lang.String)
	 */
	@Override
	public CreditInfo getCredit(String creditKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(creditKey, "creditKey");
		Credit credit = lrcDao.fetch(Credit.class, creditKey);

		return LrcServiceAssembler.toCreditInfo(credit);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCreditKeysByCreditType(java.lang.String)
	 */
	@Override
	public List<String> getCreditKeysByCreditType(String creditTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCreditType(java.lang.String)
	 */
	@Override
	public CreditTypeInfo getCreditType(String creditTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCreditTypes()
	 */
	@Override
	public List<CreditTypeInfo> getCreditTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getCreditsByKeyList(java.util.List)
	 */
	@Override
	public List<CreditInfo> getCreditsByKeyList(List<String> creditKeyList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(creditKeyList, "creditKeyList");
	    checkForEmptyList(creditKeyList, "creditKeyList");
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getGrade(java.lang.String)
	 */
	@Override
	public GradeInfo getGrade(String gradeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getGradeKeysByGradeType(java.lang.String)
	 */
	@Override
	public List<String> getGradeKeysByGradeType(String gradeTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getGradeType(java.lang.String)
	 */
	@Override
	public GradeTypeInfo getGradeType(String gradeTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getGradeTypes()
	 */
	@Override
	public List<GradeTypeInfo> getGradeTypes() throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getGradesByKeyList(java.util.List)
	 */
	@Override
	public List<GradeInfo> getGradesByKeyList(List<String> gradeKeyList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getGradesByScale(java.lang.String)
	 */
	@Override
	public List<GradeInfo> getGradesByScale(String scale)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getResultComponent(java.lang.String)
	 */
	@Override
	public ResultComponentInfo getResultComponent(String resultComponentId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getResultComponentIdsByResult(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getResultComponentIdsByResult(String resultValueId,
			String resultComponentTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getResultComponentIdsByResultComponentType(java.lang.String)
	 */
	@Override
	public List<String> getResultComponentIdsByResultComponentType(
			String resultComponentTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getResultComponentType(java.lang.String)
	 */
	@Override
	public ResultComponentTypeInfo getResultComponentType(
			String resultComponentTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getResultComponentTypes()
	 */
	@Override
	public List<ResultComponentTypeInfo> getResultComponentTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#getScale(java.lang.String)
	 */
	@Override
	public ScaleInfo getScale(String scaleKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#translateGrade(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<GradeInfo> translateGrade(String gradeKey, String scaleKey,
			String translateScaleKey) throws InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lrc.service.LrcService#updateResultComponent(java.lang.String, org.kuali.student.lum.lrc.dto.ResultComponentInfo)
	 */
	@Override
	public ResultComponentInfo updateResultComponent(String resultComponentId,
			ResultComponentInfo resultComponentInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the lrcDao
	 */
	public LrcDao getLrcDao() {
		return lrcDao;
	}

	/**
	 * @param lrcDao the lrcDao to set
	 */
	public void setLrcDao(LrcDao lrcDao) {
		this.lrcDao = lrcDao;
	}

    /**
     * Check for missing parameter and throw localized exception if missing
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

    /**
     * @param param
     * @param paramName
     * @throws MissingParameterException
     */
    private void checkForEmptyList(Object param, String paramName)
            throws MissingParameterException {
        if (param != null && param instanceof List && ((List<?>)param).size() == 0) {
            throw new MissingParameterException(paramName + " can not be an empty list");
        }
    }

}
