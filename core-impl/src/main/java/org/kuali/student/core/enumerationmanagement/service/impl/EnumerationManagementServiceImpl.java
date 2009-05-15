package org.kuali.student.core.enumerationmanagement.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.enumerationmanagement.EnumerationException;
import org.kuali.student.core.enumerationmanagement.dao.EnumerationManagementDAO;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueFieldInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumerationMetaInfo;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValueEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumerationMetaEntity;
import org.kuali.student.core.enumerationmanagement.service.EnumerationManagementService;
import org.kuali.student.core.enumerationmanagement.service.impl.util.EnumerationAssembler;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.validation.Validator;
import org.kuali.student.core.validation.dto.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.core.enumerationmanagement.service.EnumerationManagementService", serviceName = "EnumerationManagementService", portName = "EnumerationManagementService", targetNamespace = "http://student.kuali.org/wsdl/EnumerationManagementService")
@Transactional
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class EnumerationManagementServiceImpl implements EnumerationManagementService{
    
	final static Logger logger = LoggerFactory.getLogger(EnumerationManagementServiceImpl.class);
	
	public EnumerationManagementServiceImpl() {}
	
	private EnumerationManagementDAO enumDAO;
    
    public EnumerationManagementDAO getEnumDAO() {
        return enumDAO;
    }

    public void setEnumDAO(EnumerationManagementDAO enumDAO) {
        this.enumDAO = enumDAO;
    }


    
    private ValidationResult validateEnumeratedValue(EnumerationMetaInfo meta, EnumeratedValueInfo value){
    	
    	ValidationResult result = new ValidationResult();
    	List<EnumeratedValueFieldInfo> fields = meta.getEnumeratedValueFields();
        for(EnumeratedValueFieldInfo field: fields){
        	if(field.getId().equalsIgnoreCase("code")){
        		result = Validator.validate(value.getCode(), field.getFieldDescriptor().toMap());
        	}
        	else if(field.getId().equalsIgnoreCase("abbrevValue")){
        		result = Validator.validate(value.getAbbrevValue(), field.getFieldDescriptor().toMap());
        	}
        	else if(field.getId().equalsIgnoreCase("value")){
        		result = Validator.validate(value.getValue(), field.getFieldDescriptor().toMap());
        	}
        	else if(field.getId().equalsIgnoreCase("effectiveDate")){
        		result = Validator.validate(value.getEffectiveDate(), field.getFieldDescriptor().toMap());
        	}
        	else if(field.getId().equalsIgnoreCase("expirationDate")){
        		result = Validator.validate(value.getExpirationDate(), field.getFieldDescriptor().toMap());
        	}
        	else if(field.getId().equalsIgnoreCase("sortKey")){
        		result = Validator.validate(value.getSortKey(), field.getFieldDescriptor().toMap());
        	}
        	
        	if(result.getErrorLevel() == ValidationResult.ErrorLevel.ERROR){
        		break;
        	}
        }
        return result;
    }
     
	@Override
	public EnumerationMetaInfo getEnumerationMeta(String enumerationKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        
        EnumerationMetaEntity enumerationMetaEntity = enumDAO.fetchEnumerationMeta(enumerationKey);
        EnumerationMetaInfo enumerationMeta = null;
        if(enumerationMetaEntity != null){
        	enumerationMeta = new EnumerationMetaInfo();
	        EnumerationAssembler.toEnumeratedMetaInfo(enumerationMetaEntity, enumerationMeta);
        }
        return enumerationMeta;
	}

	@Override
	public List<EnumerationMetaInfo> getEnumerationMetas()
			throws OperationFailedException {
        List<EnumerationMetaEntity> entities =  this.enumDAO.findEnumerationMetas();
        
        List<EnumerationMetaInfo> dtos = EnumerationAssembler.toEnumerationMetaList(entities);
       
        return dtos;
	}
	
	@Override
	public EnumeratedValueInfo addEnumeratedValue(String enumerationKey,
			EnumeratedValueInfo enumeratedValue) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		ValidationResult result = new ValidationResult();
    	EnumerationMetaInfo meta;
		try {
			meta = this.getEnumerationMeta(enumerationKey);
		} catch (DoesNotExistException e) {
			throw new InvalidParameterException("Enumeration Meta does not exist for key:"+enumerationKey);
		}
		
    	if(meta != null){
	        result = this.validateEnumeratedValue(meta, enumeratedValue);
    	}
    	
    	if(result.getErrorLevel() != ValidationResult.ErrorLevel.ERROR){
    		EnumeratedValueEntity valueEntity = new EnumeratedValueEntity();
    		EnumerationAssembler.toEnumeratedValueEntity(enumeratedValue, valueEntity);
        	enumDAO.addEnumeratedValue(enumerationKey, valueEntity);
    	}
    	else{
    		throw new EnumerationException("addEnumeratedValue failed because the EnumeratdValue failed to pass validation against its EnumerationMeta - With Messages: " + result.getMessages());
    	}

        return enumeratedValue;
	}

	@Override
	public List<EnumeratedValueInfo> getEnumeration(String enumerationKey,
			String contextType, String contextValue, Date contextDate)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

        List<EnumeratedValueEntity> enumeratedValueEntityList = new ArrayList<EnumeratedValueEntity>();
        if(enumerationKey != null && contextType != null && contextValue != null && contextDate != null){
        	enumeratedValueEntityList = enumDAO.fetchEnumerationWithContextAndDate(enumerationKey, contextType, contextValue, contextDate);
        }
        else if(enumerationKey != null && contextType != null && contextValue != null){
        	enumeratedValueEntityList = enumDAO.fetchEnumerationWithContext(enumerationKey, contextType, contextValue);
        }
        else if(enumerationKey != null && contextDate != null){
        	enumeratedValueEntityList = enumDAO.fetchEnumerationWithDate(enumerationKey, contextDate);
        }
        else if(enumerationKey != null){
        	enumeratedValueEntityList = enumDAO.fetchEnumeration(enumerationKey);
        }
        
        List<EnumeratedValueInfo> enumeratedValueList = EnumerationAssembler.toEnumeratedValueList(enumeratedValueEntityList);
        return enumeratedValueList;
	}


	@Override
	public EnumeratedValueInfo updateEnumeratedValue(String enumerationKey,
			String code, EnumeratedValueInfo enumeratedValue)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        
		ValidationResult result = new ValidationResult();
    	EnumerationMetaInfo meta = this.getEnumerationMeta(enumerationKey);
    	if(meta != null){
	        result = this.validateEnumeratedValue(meta, enumeratedValue);
    	}

    	if(result.getErrorLevel() != ValidationResult.ErrorLevel.ERROR){
		    EnumeratedValueEntity enumeratedValueEntity = new EnumeratedValueEntity();    
		    EnumerationAssembler.toEnumeratedValueEntity(enumeratedValue, enumeratedValueEntity);
		    enumeratedValueEntity =  enumDAO.updateEnumeratedValue(enumerationKey, code, enumeratedValueEntity);
		    EnumerationAssembler.toEnumeratedValueInfo(enumeratedValueEntity, enumeratedValue);
    	}
    	else{
    		throw new EnumerationException("updateEnumeratedValue failed because the EnumeratdValue failed to pass validation against its EnumerationMeta - With Messages: " + result.getMessages());
    	}
        
        return enumeratedValue;
	}
	
	@Override
    public StatusInfo removeEnumeratedValue(String enumerationKey, String code) {
        enumDAO.removeEnumeratedValue(enumerationKey, code);
        return new StatusInfo();
    }
}
