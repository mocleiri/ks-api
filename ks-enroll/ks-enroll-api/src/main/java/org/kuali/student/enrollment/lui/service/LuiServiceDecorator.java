package org.kuali.student.enrollment.lui.service;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import javax.jws.WebParam;


public class LuiServiceDecorator implements LuiService
{	
    private LuiService nextDecorator;

    public LuiService getNextDecorator()
        throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }

        return nextDecorator;
    }

    public void setNextDecorator(LuiService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }


    @Override
    public LuiInfo getLui(String luiId, ContextInfo context)
        throws DoesNotExistException, InvalidParameterException,
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getLui(luiId, context);
    }

    @Override
    public List<LuiInfo> getLuisByIds(List<String> luiIds, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getLuisByIds(luiIds, context);
    }

    @Override
    public List<String> getLuiIdsByType(String luiTypeKey, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getLuiIdsByType(luiTypeKey, context);
    }

    @Override
    public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpId, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getLuisInAtpByCluId(cluId, atpId, context);
    }

    @Override
    public List<String> getLuiIdsByCluId(String cluId, ContextInfo context)
        throws DoesNotExistException, InvalidParameterException,
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getLuiIdsByCluId(cluId, context);
    }

    @Override
    public List<String> getLuiIdsByAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException
        {
            return getNextDecorator().getLuiIdsByAtp(atpId, context);
        }

    @Override
    public List<String> getLuiIdsInAtpByCluId(String cluId, String atpId, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getLuiIdsInAtpByCluId(cluId, atpId, context);
    }

    @Override
    public List<LuiInfo> getLuisByRelation(String relatedLuiId, String luLuRelationTypeKey, ContextInfo context)
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException {

        return getNextDecorator().getLuisByRelation(relatedLuiId, luLuRelationTypeKey, context);
    }

    @Override
    public List<String> getLuiIdsByRelation(String relatedLuiId, String luLuRelationTypeKey, ContextInfo context)
    throws InvalidParameterException, MissingParameterException,
    OperationFailedException {

        return getNextDecorator().getLuiIdsByRelation(relatedLuiId, luLuRelationTypeKey, context);
    }

    @Override
    public List<LuiInfo> getRelatedLuisByLuiId(String luiId, String luLuRelationTypeKey, ContextInfo context)
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException {

        return getNextDecorator().getRelatedLuisByLuiId(luiId, luLuRelationTypeKey, context);
    }

    @Override
    public List<String> getRelatedLuiIdsByLuiId(String luiId, String luLuRelationTypeKey, ContextInfo context)
        throws InvalidParameterException, MissingParameterException,
               OperationFailedException {

        return getNextDecorator().getRelatedLuiIdsByLuiId(luiId, luLuRelationTypeKey, context);
    }

    @Override
    public List<String> searchForLuiIds(QueryByCriteria criteria, ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForLuiIds(criteria, context);
    }

    @Override
    public List<LuiInfo> searchForLuis(QueryByCriteria criteria, ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForLuis(criteria, context);
    }

    @Override
    public List<ValidationResultInfo> validateLui(String validationType, LuiInfo luiInfo, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().validateLui(validationType,luiInfo, context);
    }

    @Override
    public LuiInfo createLui(String cluId, String atpId, LuiInfo luiInfo, ContextInfo context) 
        throws AlreadyExistsException, DataValidationErrorException, 
               DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {

        return getNextDecorator().createLui(cluId,atpId,luiInfo, context);
    }

    @Override
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo context)
        throws DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException,
               VersionMismatchException {

        return getNextDecorator().updateLui(luiId,luiInfo, context);
    }

    @Override
    public StatusInfo deleteLui(String luiId, ContextInfo context)
        throws DependentObjectsExistException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().deleteLui(luiId, context);
    }

    @Override
    public LuiInfo updateLuiState(String luiId, String luState, ContextInfo context) 
        throws DataValidationErrorException, DoesNotExistException, 
               InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().updateLuiState(luiId, luState, context);
    }

    @Override
    public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getLuiLuiRelation(luiLuiRelationId, context);
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByIds(List<String> luiLuiRelationIds, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getLuiLuiRelationsByIds(luiLuiRelationIds, context);
    }

    @Override
    public List<String> getLuiLuiRelationIdsByType(String luiLuiRelationTypeKey, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getLuiLuiRelationIdsByType(luiLuiRelationTypeKey, context);
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getLuiLuiRelationsByLui(luiId, context);
    }

    @Override
    public List<String> searchForLuiLuiRelationIds(QueryByCriteria criteria, ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForLuiLuiRelationIds(criteria, context);
    }

    @Override
    public List<LuiLuiRelationInfo> searchForLuiLuiRelations(QueryByCriteria criteria, ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForLuiLuiRelations(criteria, context);
    }

    @Override
    public List<ValidationResultInfo> validateLuiLuiRelation(String validationType, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().validateLuiLuiRelation(validationType, luiLuiRelationInfo, context);
    }

    @Override
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId, String luLuRelationTypeKey, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context)
        throws AlreadyExistsException, CircularRelationshipException,
               DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().createLuiLuiRelation(luiId, relatedLuiId, luLuRelationTypeKey, luiLuiRelationInfo, context);
    }

    @Override
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context)
        throws DataValidationErrorException, DoesNotExistException,
               InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException,
               VersionMismatchException {

        return getNextDecorator().updateLuiLuiRelation(luiLuiRelationId, luiLuiRelationInfo, context);
    }

    @Override
    public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {

        return getNextDecorator().deleteLuiLuiRelation(luiLuiRelationId,context);
    }

    @Override
    public LuiCapacityInfo getLuiCapacity(String luiCapacityId, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getLuiCapacity(luiCapacityId, context);
    }

    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByIds(List<String> luiCapacityIds, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getLuiCapacitiesByIds(luiCapacityIds, context);
    }

    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByLui(String luiId, ContextInfo context) 
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getLuiCapacitiesByLui(luiId, context);
    }

    
    
    @Override
    public List<String> getLuiCapacityIdsByType(String luiCapacityTypeKey, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().getLuiCapacityIdsByType(luiCapacityTypeKey, context);
    }

    @Override
    public List<String> searchForLuiCapacityIds(QueryByCriteria criteria, ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForLuiCapacityIds(criteria, context);
    }

    @Override
    public List<LuiCapacityInfo> searchForLuiCapacities(QueryByCriteria criteria, ContextInfo context) 
        throws InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForLuiCapacities(criteria, context);
    }

    @Override
    public List<ValidationResultInfo> validateLuiCapacity(String validationType, LuiCapacityInfo luiCapacityInfo, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException {

        return getNextDecorator().validateLuiCapacity(validationType, luiCapacityInfo, context);
    }

    @Override
    public LuiCapacityInfo createLuiCapacity(LuiCapacityInfo luiCapacityInfo, ContextInfo context) 
        throws AlreadyExistsException, DataValidationErrorException, 
               DoesNotExistException, InvalidParameterException, 
               MissingParameterException, OperationFailedException, 
               PermissionDeniedException {

        return getNextDecorator().createLuiCapacity(luiCapacityInfo, context);
    }

    @Override
    public LuiCapacityInfo updateLuiCapacity(String luiCapacityId, LuiCapacityInfo luiCapacityInfo, ContextInfo context) 
        throws DataValidationErrorException, DoesNotExistException, 
               InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException, 
               VersionMismatchException {

        return getNextDecorator().updateLuiCapacity(luiCapacityId, luiCapacityInfo, context);
    }

    @Override
    public StatusInfo deleteLuiCapacity(String luiCapacityId, ContextInfo context) 
        throws DependentObjectsExistException, DoesNotExistException, 
               InvalidParameterException, MissingParameterException, 
               OperationFailedException, PermissionDeniedException {

        return getNextDecorator().deleteLuiCapacity(luiCapacityId, context);
    }
}
