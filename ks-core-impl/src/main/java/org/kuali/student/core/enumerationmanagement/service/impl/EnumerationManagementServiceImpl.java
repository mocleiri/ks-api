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
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.core.enumerationmanagement.service.EnumerationManagementService", serviceName = "EnumerationManagementService", portName = "EnumerationManagementService", targetNamespace = "http://student.kuali.org/wsdl/EnumerationManagementService")
@Transactional(rollbackFor={Throwable.class})
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


    
    private ValidationResultContainer validateEnumeratedValue(EnumerationMetaInfo meta, EnumeratedValueInfo value){
//    	
//    	DictValidationResultContainer result = new DictValidationResultContainer(meta.g);
//    	List<EnumeratedValueFieldInfo> fields = meta.getEnumeratedValueFields();
//        for(EnumeratedValueFieldInfo field: fields){
//        	if(field.getId().equalsIgnoreCase("code")){
//        		result = DictValidationResultContainer.validate(value.getCode(), field.getFieldDescriptor().toMap());
//        	}
//        	else if(field.getId().equalsIgnoreCase("abbrevValue")){
//        		result = DictValidationResultContainer.validate(value.getAbbrevValue(), field.getFieldDescriptor().toMap());
//        	}
//        	else if(field.getId().equalsIgnoreCase("value")){
//        		result = DictValidationResultContainer.validate(value.getValue(), field.getFieldDescriptor().toMap());
//        	}
//        	else if(field.getId().equalsIgnoreCase("effectiveDate")){
//        		result = DictValidationResultContainer.validate(value.getEffectiveDate(), field.getFieldDescriptor().toMap());
//        	}
//        	else if(field.getId().equalsIgnoreCase("expirationDate")){
//        		result = DictValidationResultContainer.validate(value.getExpirationDate(), field.getFieldDescriptor().toMap());
//        	}
//        	else if(field.getId().equalsIgnoreCase("sortKey")){
//        		result = DictValidationResultContainer.validate(value.getSortKey(), field.getFieldDescriptor().toMap());
//        	}
//        	
//        	if(result.getErrorLevel() == DictValidationResultInfo.ErrorLevel.ERROR){
//        		break;
//        	}
//        }
//        return result;
    	return null; //FIXME need real validation
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
		ValidationResultContainer result = new ValidationResultContainer(enumerationKey);
    	EnumerationMetaInfo meta;
		try {
			meta = this.getEnumerationMeta(enumerationKey);
		} catch (DoesNotExistException e) {
			throw new InvalidParameterException("Enumeration Meta does not exist for key:"+enumerationKey);
		}
		
    	if(meta != null){
	        result = this.validateEnumeratedValue(meta, enumeratedValue);
    	}
    	
    	if(result !=null && result.getErrorLevel() != ValidationResultInfo.ErrorLevel.ERROR){
    		EnumeratedValueEntity valueEntity = new EnumeratedValueEntity();
    		EnumerationAssembler.toEnumeratedValueEntity(enumeratedValue, valueEntity);
        	enumDAO.addEnumeratedValue(enumerationKey, valueEntity);
    	}
    	else{
    		throw new EnumerationException("addEnumeratedValue failed because the EnumeratdValue failed to pass validation against its EnumerationMeta - With Messages: " + result.toString());//FIXME need to get messages here
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
        
		ValidationResultContainer result = new ValidationResultContainer(enumerationKey);
    	EnumerationMetaInfo meta = this.getEnumerationMeta(enumerationKey);
    	if(meta != null){
	        result = this.validateEnumeratedValue(meta, enumeratedValue);
    	}

    	if(result.getErrorLevel() != ValidationResultInfo.ErrorLevel.ERROR){
		    EnumeratedValueEntity enumeratedValueEntity = new EnumeratedValueEntity();    
		    EnumerationAssembler.toEnumeratedValueEntity(enumeratedValue, enumeratedValueEntity);
		    enumeratedValueEntity =  enumDAO.updateEnumeratedValue(enumerationKey, code, enumeratedValueEntity);
		    EnumerationAssembler.toEnumeratedValueInfo(enumeratedValueEntity, enumeratedValue);
    	}
    	else{
    		throw new EnumerationException("updateEnumeratedValue failed because the EnumeratdValue failed to pass validation against its EnumerationMeta - With Messages: " + result.toString());//FIXME need to output messages here
    	}
        
        return enumeratedValue;
	}
	
	@Override
    public StatusInfo removeEnumeratedValue(String enumerationKey, String code) {
        enumDAO.removeEnumeratedValue(enumerationKey, code);
        return new StatusInfo();
    }
}
