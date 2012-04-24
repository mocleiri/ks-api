/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.common.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.kuali.student.common.dictionary.dto.CaseConstraint;
import org.kuali.student.common.dictionary.dto.CommonLookupParam;
import org.kuali.student.common.dictionary.dto.Constraint;
import org.kuali.student.common.dictionary.dto.DataType;
import org.kuali.student.common.dictionary.dto.FieldDefinition;
import org.kuali.student.common.dictionary.dto.LookupConstraint;
import org.kuali.student.common.dictionary.dto.MustOccurConstraint;
import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.dictionary.dto.RequiredConstraint;
import org.kuali.student.common.dictionary.dto.ValidCharsConstraint;
import org.kuali.student.common.dictionary.dto.WhenConstraint;
import org.kuali.student.common.messages.dto.Message;
import org.kuali.student.common.messages.service.MessageService;
import org.kuali.student.common.search.dto.SearchParam;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.service.SearchDispatcher;
import org.kuali.student.common.util.MessageUtils;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validation.dto.ValidationResultInfo.ErrorLevel;
import org.springframework.beans.BeanUtils;

public class DefaultValidatorImpl extends BaseAbstractValidator {
    final static Logger LOG = Logger.getLogger(DefaultValidatorImpl.class);

    private MessageService messageService = null;

    private SearchDispatcher searchDispatcher;

    private String messageLocaleKey = "en";

    private String messageGroupKey = "validation";

    private DateParser dateParser = new ServerDateParser();

    private boolean serverSide = true;

    public MessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public String getMessageLocaleKey() {
        return messageLocaleKey;
    }

    public void setMessageLocaleKey(String messageLocaleKey) {
        this.messageLocaleKey = messageLocaleKey;
    }

    public String getMessageGroupKey() {
        return messageGroupKey;
    }

    public void setMessageGroupKey(String messageGroupKey) {
        this.messageGroupKey = messageGroupKey;
    }

    public void setDateParser(DateParser dateParser) {
        this.dateParser = dateParser;
    }

	/**
     * @return the serverSide
     */
    public boolean isServerSide() {
        return serverSide;
    }

    /**
     * @param serverSide
     *            the serverSide to set
     */
    public void setServerSide(boolean serverSide) {
        this.serverSide = serverSide;
    }

    /**
     * @return the dateParser
     */
    public DateParser getDateParser() {
        return dateParser;
    }

    /**
     * Validate Object and all its nested child objects for given type and state
     *
     * @param data
     * @param objStructure
     * @return
     */
    public List<ValidationResultInfo> validateObject(Object data, ObjectStructureDefinition objStructure) {

        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();
    	Stack<String> elementStack = new Stack<String>();

       validateObject(results, data, objStructure, elementStack, data, objStructure, true);
       
       return results;
    }

    private void validateObject(List<ValidationResultInfo> results, Object data, ObjectStructureDefinition objStructure, Stack<String> elementStack,  Object rootData, ObjectStructureDefinition rootObjStructure, boolean isRoot) {

        ConstraintDataProvider dataProvider = new BeanConstraintDataProvider();
        dataProvider.initialize(data);

        // Push object structure to the top of the stack
        StringBuilder objXPathElement = new StringBuilder(dataProvider.getPath());

        if(!isRoot && !objXPathElement.toString().isEmpty()){
        	elementStack.push(objXPathElement.toString());
        }

        /*
         * Do nothing if the object to be validated is not type/state or if the objectstructure with constraints is not
         * provided
         */
        if (null == objStructure) {
            return;
        }

        for (FieldDefinition f : objStructure.getAttributes()) {
            validateField(results, f, objStructure, dataProvider, elementStack, rootData, rootObjStructure);

            // Use Custom Validators
            if (f.getCustomValidatorClass() != null || f.isServerSide() && serverSide) {
            	Validator customValidator = validatorFactory.getValidator(f.getCustomValidatorClass());
            	if(customValidator==null){
            		throw new RuntimeException("Custom Validator "+f.getCustomValidatorClass()+" was not configured in this context");
            	}
            	List<ValidationResultInfo> l = customValidator.validateObject(f,data, objStructure,elementStack);
            	results.addAll(l);
            }
        }
        if(!isRoot && !objXPathElement.toString().isEmpty()){
        	elementStack.pop();
        }

        /* All Field validations are returned right now */
        // List<ValidationResultInfo> resultsBuffer = new
        // ArrayList<ValidationResultInfo>();
        // for (ValidationResultContainer vc : results) {
        // if (skipFields.contains(vc.getElement()) == false) {
        // resultsBuffer.add(vc);
        // }
        // }
        // results = resultsBuffer;
    }

    public void validateField(List<ValidationResultInfo> results, FieldDefinition field, ObjectStructureDefinition objStruct, ConstraintDataProvider dataProvider, Stack<String> elementStack,  Object rootData, ObjectStructureDefinition rootObjectStructure) {

        Object value = dataProvider.getValue(field.getName());

        // Handle null values in field
        if (value == null || "".equals(value.toString().trim())) {
            processConstraint(results, field, objStruct, value, dataProvider, elementStack, rootData, rootObjectStructure);
            return; //no need to do further processing
        }

        /*
         * For complex object structures only the following constraints apply 1. TypeStateCase 2. MinOccurs 3. MaxOccurs
         */
        if (DataType.COMPLEX.equals(field.getDataType())) {
            ObjectStructureDefinition nestedObjStruct = null;

            if (null != field.getDataObjectStructure()) {
                nestedObjStruct = field.getDataObjectStructure();
            }

            elementStack.push(field.getName());
//           	beanPathStack.push(field.isDynamic()?"attributes("+field.getName()+")":field.getName());

            if (value instanceof Collection) {

                String xPathForCollection = getElementXpath(elementStack) + "/*";

                int i=0;
                for (Object o : (Collection<?>) value) {
                	elementStack.push(Integer.toString(i));
//                	beanPathStack.push(!beanPathStack.isEmpty()?beanPathStack.pop():""+"["+i+"]");
                    processNestedObjectStructure(results, o, nestedObjStruct, field, elementStack, rootData, rootObjectStructure);
//                    beanPathStack.pop();
//                    beanPathStack.push(field.isDynamic()?"attributes("+field.getName()+")":field.getName());
                    elementStack.pop();
                    i++;
                }
                if (field.getMinOccurs() != null && field.getMinOccurs() > ((Collection<?>) value).size()) {
                    ValidationResultInfo valRes = new ValidationResultInfo(xPathForCollection, value);
                    valRes.setError(MessageUtils.interpolate(getMessage("validation.minOccurs"), toMap(field)));
                    results.add(valRes);
                }

                Integer maxOccurs = tryParse(field.getMaxOccurs());
                if (maxOccurs != null && maxOccurs < ((Collection<?>) value).size()) {
                    ValidationResultInfo valRes = new ValidationResultInfo(xPathForCollection, value);
                    valRes.setError(MessageUtils.interpolate(getMessage("validation.maxOccurs"), toMap(field)));
                    results.add(valRes);
                }
            } else {
                if (null != value) {
                    processNestedObjectStructure(results, value, nestedObjStruct, field, elementStack, rootData, rootObjectStructure);
                } else {
                    if (field.getMinOccurs() != null && field.getMinOccurs() > 0) {
                        ValidationResultInfo val = new ValidationResultInfo(getElementXpath(elementStack), value);
                        if(field.getLabelKey()!=null){
                        	val.setError(getMessage(field.getLabelKey()));
                        } else {
                        	val.setError(getMessage("validation.required"));
                        }
                        results.add(val);
                    }
                }
            }
            
//            beanPathStack.pop();
            elementStack.pop();

        } else { // If non complex data type

            if (value instanceof Collection) {

                if(((Collection<?>)value).isEmpty()){
                    processConstraint(results, field, objStruct, "", dataProvider, elementStack, rootData, rootObjectStructure);
                }

            	int i = 0;
                for (Object o : (Collection<?>) value) {
                	//This is tricky, change the field name to the index in the elementStack(this is for lists of non complex types)
                	elementStack.push(field.getName());
                	FieldDefinition tempField = new FieldDefinition();
                	BeanUtils.copyProperties(field, tempField);
                	tempField.setName(Integer.toBinaryString(i));
                	processConstraint(results, tempField, objStruct, o, dataProvider, elementStack, rootData, rootObjectStructure);
                	elementStack.pop();
                    i++;
                }

                String xPath = getElementXpath(elementStack) + "/" + field.getName() + "/*";
                if (field.getMinOccurs() != null && field.getMinOccurs() > ((Collection<?>) value).size()) {
                    ValidationResultInfo valRes = new ValidationResultInfo(xPath, value);
                    valRes.setError(MessageUtils.interpolate(getMessage("validation.minOccurs"), toMap(field)));
                    results.add(valRes);
                }

                Integer maxOccurs = tryParse(field.getMaxOccurs());
                if (maxOccurs != null && maxOccurs < ((Collection<?>) value).size()) {
                    ValidationResultInfo valRes = new ValidationResultInfo(xPath, value);
                    valRes.setError(MessageUtils.interpolate(getMessage("validation.maxOccurs"), toMap(field)));
                    results.add(valRes);
                }
            } else {
                processConstraint(results, field, objStruct, value, dataProvider, elementStack, rootData, rootObjectStructure);
            }

        }
    }

    protected Integer tryParse(String s) {
        Integer result = null;
        if (s != null) {
            try {
                result = Integer.valueOf(s);
            } catch (NumberFormatException e) {
                // do nothing
            }
        }
        return result;
    }

    protected void processNestedObjectStructure(List<ValidationResultInfo> results, Object value, ObjectStructureDefinition nestedObjStruct, FieldDefinition field, Stack<String> elementStack,  Object rootData, ObjectStructureDefinition rootObjStructure) {
        validateObject(results, value, nestedObjStruct, elementStack, rootData, rootObjStructure, false);
    }

    protected void processConstraint(List<ValidationResultInfo> valResults, FieldDefinition field, ObjectStructureDefinition objStructure, Object value, ConstraintDataProvider dataProvider, Stack<String> elementStack,  Object rootData, ObjectStructureDefinition rootObjStructure) {

        // Process Case Constraint
        // Case Constraint are only evaluated on the field. Nested case constraints are currently ignored
        Constraint caseConstraint = processCaseConstraint(valResults, field.getCaseConstraint(), objStructure, value, dataProvider, elementStack, rootData, rootObjStructure);

        Constraint constraint = (null != caseConstraint) ? caseConstraint : field;

        processBaseConstraints(valResults, constraint, field, value, elementStack);
        
        // Stop other checks if value is null
        if (value == null || "".equals(value.toString().trim())) {
            return;
        }

        String elementPath = getElementXpath(elementStack) + "/" + field.getName();

        // Process Valid Chars
        if (null != constraint.getValidChars()) {
            ValidationResultInfo val = processValidCharConstraint(elementPath, constraint.getValidChars(), dataProvider, value);
            if (null != val) {
                valResults.add(val);
            }
        }

        // Process Require Constraints (only if this field has value)
        if (value != null && !"".equals(value.toString().trim())) {
            if (null != constraint.getRequireConstraint() && constraint.getRequireConstraint().size() > 0) {
                for (RequiredConstraint rc : constraint.getRequireConstraint()) {
                    ValidationResultInfo val = processRequireConstraint(elementPath, rc, field, objStructure, dataProvider);
                    if (null != val) {
                        valResults.add(val);
                        //FIXME: For clarity, might be better to handle this in the processRequireConstraint method instead.
                        processCrossFieldWarning(valResults, rc, val.getErrorLevel(), field.getName());
                    }
                }
            }
        }

        // Process Occurs Constraint
        if (null != constraint.getOccursConstraint() && constraint.getOccursConstraint().size() > 0) {
            for (MustOccurConstraint oc : constraint.getOccursConstraint()) {
                ValidationResultInfo val = processOccursConstraint(elementPath, oc, field, objStructure, dataProvider);
                if (null != val) {
                    valResults.add(val); 
                }
            }
        }

        // Process lookup Constraint
        if (null != constraint.getLookupDefinition()) {
            processLookupConstraint(valResults, constraint.getLookupDefinition(), field, elementStack, dataProvider,objStructure, rootData, rootObjStructure, value);
        }
    }

    protected ValidationResultInfo processRequireConstraint(String element, RequiredConstraint constraint, FieldDefinition field, ObjectStructureDefinition objStructure, ConstraintDataProvider dataProvider) {

        ValidationResultInfo val = null;

        String fieldName = constraint.getFieldPath();// TODO parse fieldname from here
        Object fieldValue = dataProvider.getValue(fieldName);

        boolean result = true;

        if (fieldValue instanceof java.lang.String) {
            result = hasText((String) fieldValue);
        } else if (fieldValue instanceof Collection) {
            result = (((Collection<?>) fieldValue).size() > 0);
        } else {
            result = (null != fieldValue) ? true : false;
        }

        if (!result) {
            Map<String, Object> rMap = new HashMap<String, Object>();
            rMap.put("field1", field.getName());
            rMap.put("field2", fieldName);
            val = new ValidationResultInfo(element, fieldValue);
            val.setMessage(MessageUtils.interpolate(getMessage("validation.requiresField"), rMap));
            val.setLevel(constraint.getErrorLevel());                       
        }

        return val;
    }

    /**
     * Process caseConstraint tag and sets any of the base constraint items if any of the when condition matches
     *
     * @param caseConstraint
     * @param caseConstraint
     * @param field
     */
    protected Constraint processCaseConstraint(List<ValidationResultInfo> valResults, CaseConstraint caseConstraint, ObjectStructureDefinition objStructure, Object value, ConstraintDataProvider dataProvider, Stack<String> elementStack,  Object rootData, ObjectStructureDefinition rootObjStructure) {

        if (null == caseConstraint) {
            return null;
        }

        String operator = (hasText(caseConstraint.getOperator())) ? caseConstraint.getOperator() : "EQUALS";
        FieldDefinition caseField = null;
        boolean absolutePath = false;
        if(hasText(caseConstraint.getFieldPath())){
        	if(caseConstraint.getFieldPath().startsWith("/")){
        		absolutePath = true;
        		caseField = ValidatorUtils.getField(caseConstraint.getFieldPath().substring(1), rootObjStructure);
        	}else{
        		caseField = ValidatorUtils.getField(caseConstraint.getFieldPath(), objStructure); 
        	}
        }

        // TODO: What happens when the field is not in the dataProvider?
        Object fieldValue = value;
        if(caseField!=null){
        	if(absolutePath){
        		try {
        			if(caseField.isDynamic()){
        				//Pull the value from the dynamic attribute map
        				//TODO There needs to be some mapping from PropertyUtils to the KS path
        				//Until then, this will only work for root level properties
        				Map<String,String> attributes = (Map<String,String>) PropertyUtils.getNestedProperty(rootData, "attributes");
        				if(attributes!=null){
        					fieldValue = attributes.get(caseConstraint.getFieldPath().substring(1));
        				}
        			}else{
        				fieldValue = PropertyUtils.getNestedProperty(rootData, caseConstraint.getFieldPath().substring(1));
        			}
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				} catch (NoSuchMethodException e) {
				}
        	}else{
            	fieldValue = dataProvider.getValue(caseField.getName());
        	}
        }
        DataType fieldDataType = (null != caseField ? caseField.getDataType():null);

        // If fieldValue is null then skip Case check
        if(null == fieldValue) {
            return null;
        }

        // Extract value for field Key
        for (WhenConstraint wc : caseConstraint.getWhenConstraint()) {

        	if(hasText(wc.getValuePath())){
        		Object whenValue = null;
        		if(wc.getValuePath().startsWith("/")){
        			try {
        				whenValue = PropertyUtils.getNestedProperty(rootData, wc.getValuePath().substring(1));
					} catch (IllegalAccessException e) {
					} catch (InvocationTargetException e) {
					} catch (NoSuchMethodException e) {
					}
        		}else{
        			whenValue = dataProvider.getValue(wc.getValuePath());
        		}
        		if (ValidatorUtils.compareValues(fieldValue, whenValue, fieldDataType, operator, caseConstraint.isCaseSensitive(), dateParser) && null != wc.getConstraint()) {                    
        			Constraint constraint = wc.getConstraint();
        			if (constraint.getCaseConstraint() != null){
        				return processCaseConstraint(valResults, constraint.getCaseConstraint(), objStructure, value, dataProvider, elementStack, rootData, rootObjStructure);
        			} else {
        				processCrossFieldWarning(valResults, caseConstraint, constraint, value, constraint.getErrorLevel());
        				return constraint;
        			}
                }
        	}else{
        		List<Object> whenValueList = wc.getValues();
            
	            for (Object whenValue : whenValueList) {
	                if (ValidatorUtils.compareValues(fieldValue, whenValue, fieldDataType, operator, caseConstraint.isCaseSensitive(), dateParser) && null != wc.getConstraint()) {
	        			Constraint constraint = wc.getConstraint();
	        			if (constraint.getCaseConstraint() != null){
	        				return processCaseConstraint(valResults, constraint.getCaseConstraint(), objStructure, value, dataProvider, elementStack, rootData, rootObjStructure);
	        			} else {
	        				processCrossFieldWarning(valResults, caseConstraint, constraint, value, constraint.getErrorLevel());	        				
	        				return constraint;
	        			}
	                }
	            }
        	}
        }

        return null;
    }

    public ValidationResultInfo processValidCharConstraint(String element, ValidCharsConstraint vcConstraint, ConstraintDataProvider dataProvider, Object value) {

        ValidationResultInfo val = null;

        StringBuilder fieldValue = new StringBuilder();
        String validChars = vcConstraint.getValue();

        fieldValue.append(ValidatorUtils.getString(value));

        int typIdx = validChars.indexOf(":");
        String processorType = "regex";
        if (-1 == typIdx) {
            validChars = "[" + validChars + "]*";
        } else {
            processorType = validChars.substring(0, typIdx);
            validChars = validChars.substring(typIdx + 1);
        }

        if ("regex".equalsIgnoreCase(processorType)) {
            if (fieldValue == null || !fieldValue.toString().matches(validChars)) {
            	val = new ValidationResultInfo(element, fieldValue);
                if(vcConstraint.getLabelKey()!=null){
                	val.setError(getMessage(vcConstraint.getLabelKey()));
                }else{
                	val.setError(getMessage("validation.validCharsFailed"));
                }
            }
        }

        return val;
    }

    /**
     * Computes if all the filed required in the occurs clause are between the min and max
     *
     * @param valResults
     * @param constraint
     * @param field
     * @param type
     * @param state
     * @param objStructure
     * @param dataProvider
     * @return
     */
    protected ValidationResultInfo processOccursConstraint(String element, MustOccurConstraint constraint, FieldDefinition field, ObjectStructureDefinition objStructure, ConstraintDataProvider dataProvider) {

        boolean result = false;
        int trueCount = 0;

        ValidationResultInfo val = null;

        for (RequiredConstraint rc : constraint.getRequiredFields()) {
            trueCount += (processRequireConstraint("", rc, field, objStructure, dataProvider) != null) ? 1 : 0;
        }

        for (MustOccurConstraint oc : constraint.getOccurs()) {
            trueCount += (processOccursConstraint("", oc, field, objStructure, dataProvider) != null) ? 1 : 0;
        }

        result = (trueCount >= constraint.getMin() && trueCount <= constraint.getMax()) ? true : false;

        if (!result) {
         // TODO: figure out what data should go here instead of null
            val = new ValidationResultInfo(element, null);
            val.setMessage(getMessage("validation.occurs"));
            val.setLevel(constraint.getErrorLevel());
        }

        return val;
    }

    // TODO: Implement lookup constraint
    protected void processLookupConstraint(List<ValidationResultInfo> valResults, LookupConstraint lookupConstraint, FieldDefinition field, Stack<String> elementStack, ConstraintDataProvider dataProvider, ObjectStructureDefinition objStructure, Object rootData, ObjectStructureDefinition rootObjStructure, Object value) {
        if (lookupConstraint == null) {
            return;
        }

        // Create search params based on the param mapping
        List<SearchParam> params = new ArrayList<SearchParam>();

        for (CommonLookupParam paramMapping : lookupConstraint.getParams()) {
        	//Skip params that are the search param id key
            if(lookupConstraint.getSearchParamIdKey()!=null&&lookupConstraint.getSearchParamIdKey().equals(paramMapping.getKey())){
            	continue;
            }
        	
            SearchParam param = new SearchParam();

            param.setKey(paramMapping.getKey());

            // If the value of the search param comes form another field then get it
            if (paramMapping.getFieldPath() != null && !paramMapping.getFieldPath().isEmpty()) {
                FieldDefinition lookupField = null;
            	boolean absolutePath = false;
                if(hasText(paramMapping.getFieldPath())){
                	if(paramMapping.getFieldPath().startsWith("/")){
                		absolutePath = true;
                		lookupField = ValidatorUtils.getField(paramMapping.getFieldPath().substring(1), rootObjStructure);
                	}else{
                		lookupField = ValidatorUtils.getField(paramMapping.getFieldPath(), objStructure); 
                	}
                }
                Object fieldValue = null;
                if(lookupField!=null){
                	if(absolutePath){
                		try {
                			if(lookupField.isDynamic()){
                				//Pull the value from the dynamic attribute map
                				//Until then, this will only work for root level properties
                				Map<String,String> attributes = (Map<String,String>) PropertyUtils.getNestedProperty(rootData, "attributes");
                				if(attributes!=null){
                					fieldValue = attributes.get(paramMapping.getFieldPath().substring(1));
                				}
                			}else{
                				fieldValue = PropertyUtils.getNestedProperty(rootData, paramMapping.getFieldPath().substring(1));
                			}
        				} catch (IllegalAccessException e) {
        				} catch (InvocationTargetException e) {
        				} catch (NoSuchMethodException e) {
        				}
                	}else{
                    	fieldValue = dataProvider.getValue(lookupField.getName());
                	}
                }else{
                	fieldValue = dataProvider.getValue(paramMapping.getFieldPath());
                }
                
                if (fieldValue instanceof String) {
                    param.setValue((String) fieldValue);
                } else if (fieldValue instanceof List<?>) {
                    param.setValue((List<String>) fieldValue);
                }
            } else if (paramMapping.getDefaultValueString() != null) {
                param.setValue(paramMapping.getDefaultValueString());
            } else {
                param.setValue(paramMapping.getDefaultValueList());
            }
            params.add(param);
        }

        if(lookupConstraint.getSearchParamIdKey()!=null){
        	SearchParam param = new SearchParam();
        	param.setKey(lookupConstraint.getSearchParamIdKey());
            if (value instanceof String) {
                param.setValue((String) value);
            } else if (value instanceof List<?>) {
                param.setValue((List<String>) value);
            }
        	params.add(param);
        }
        
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setMaxResults(1);
        searchRequest.setStartAt(0);
        searchRequest.setNeededTotalResults(false);
        searchRequest.setSearchKey(lookupConstraint.getSearchTypeId());
        searchRequest.setParams(params);

        SearchResult searchResult = null;
        try {
            searchResult = searchDispatcher.dispatchSearch(searchRequest);
        } catch (Exception e) {
            LOG.info("Error calling Search", e);
        }
        //If there are no search results then make a validation result
        if (searchResult == null || searchResult.getRows() == null || searchResult.getRows().isEmpty()) {
            ValidationResultInfo val = new ValidationResultInfo(getElementXpath(elementStack) + "/" + field.getName(), value);
            val.setLevel(lookupConstraint.getErrorLevel());
            val.setMessage(getMessage("validation.lookup"));
            valResults.add(val);
        	processCrossFieldWarning(valResults, lookupConstraint, lookupConstraint.getErrorLevel());
        }
    }

    protected void processBaseConstraints(List<ValidationResultInfo> valResults, Constraint constraint, FieldDefinition field, Object value, Stack<String> elementStack) {
    	DataType dataType = field.getDataType();
    	String name = field.getName();

    	if (value == null || "".equals(value.toString().trim())) {
            if (constraint.getMinOccurs() != null && constraint.getMinOccurs() > 0) {
                ValidationResultInfo val = new ValidationResultInfo(getElementXpath(elementStack) + "/" + name, value);
                if(constraint.getLabelKey()!=null){
                	val.setError(getMessage(constraint.getLabelKey()));
                } else {
                	val.setMessage(getMessage("validation.required"));
                }
                val.setLevel(constraint.getErrorLevel());
                valResults.add(val);
            }
            return;
        }

        String elementPath = getElementXpath(elementStack) + "/" + name;

        if (DataType.STRING.equals(dataType)) {
            validateString(value, constraint, elementPath, valResults);
        } else if (DataType.INTEGER.equals(dataType)) {
            validateInteger(value, constraint, elementPath, valResults);
        } else if (DataType.LONG.equals(dataType)) {
            validateLong(value, constraint, elementPath, valResults);
        } else if (DataType.DOUBLE.equals(dataType)) {
            validateDouble(value, constraint, elementPath, valResults);
        } else if (DataType.FLOAT.equals(dataType)) {
            validateFloat(value, constraint, elementPath, valResults);
        } else if (DataType.BOOLEAN.equals(dataType)) {
            validateBoolean(value, constraint, elementPath, valResults);
        } else if (DataType.DATE.equals(dataType)) {
            validateDate(value, constraint, elementPath, valResults, dateParser);
        }
    }
    
    /**
     * This adds a warning on the related field when a processed case-constraint results in a warning
     * 
     * @param valResults
     * @param crossConstraint
     * @param field
     */
    protected void processCrossFieldWarning(List<ValidationResultInfo> valResults, CaseConstraint crossConstraint, Constraint constraint, Object value, ErrorLevel errorLevel){
    	if ((ErrorLevel.WARN == errorLevel || ErrorLevel.ERROR == errorLevel) && (value == null || "".equals(value.toString().trim()))) {
            if (constraint.getMinOccurs() != null && constraint.getMinOccurs() > 0) {

	            String crossFieldPath = crossConstraint.getFieldPath();
	            String crossFieldMessageId = crossConstraint.getFieldPathMessageId() == null ? 
	            		"validation.required":crossConstraint.getFieldPathMessageId();            
	            addCrossFieldWarning(valResults, crossFieldPath, getMessage(crossFieldMessageId), errorLevel);
            }
    	}
    }

    /**
     * This adds a warning on the related field when a processed case-constraint results in a warning
     * 
     * @param valResults
     * @param requiredConstraint
     * @param field
     */
    protected void processCrossFieldWarning(List<ValidationResultInfo> valResults, RequiredConstraint requiredConstraint, ErrorLevel errorLevel, String field){
    	if ((ErrorLevel.WARN == errorLevel || ErrorLevel.ERROR == errorLevel) && requiredConstraint != null){
            String crossFieldPath = requiredConstraint.getFieldPath();
            String crossFieldMessageId = requiredConstraint.getFieldPathMessageId() == null ? 
            		"validation.required":requiredConstraint.getFieldPathMessageId();
            addCrossFieldWarning(valResults, crossFieldPath, getMessage(crossFieldMessageId), errorLevel);
    	}
    }

    /**
     * This adds a warning on the related field when a processed lookup-constraint results in a warning
     * 
     * @param valResults
     * @param lookupConstraint
     */
    protected void processCrossFieldWarning(List<ValidationResultInfo> valResults, LookupConstraint lookupConstraint, ErrorLevel errorLevel){
    	if ((ErrorLevel.WARN == errorLevel || ErrorLevel.ERROR == errorLevel) && lookupConstraint != null){
    		for(CommonLookupParam param:lookupConstraint.getParams()){
    			if(param.getFieldPath()!=null && !param.getFieldPath().isEmpty()){
		            String crossFieldPath = param.getFieldPath();
		            String crossFieldMessageId = param.getFieldPathMessageId() == null ? 
		            		"validation.lookup.cause":param.getFieldPathMessageId();
		            addCrossFieldWarning(valResults, crossFieldPath, getMessage(crossFieldMessageId), errorLevel);
    			}
    		}
    	}
    }
    
    protected void addCrossFieldWarning(List<ValidationResultInfo> valResults, String crossFieldPath, String message, ErrorLevel errorLevel){
    	//Check to see if the exact same validation message already exists on referenced field
    	boolean warnAlreadyExists = false;
    	for (ValidationResultInfo vr:valResults){
    		if (vr.getElement().equals(crossFieldPath) && vr.getMessage().equals(message)){
    			warnAlreadyExists = true;
    		}
    	}
    	
    	//Only add this warning, if it hasn't been already added
    	if (!warnAlreadyExists){
	    	ValidationResultInfo val = new ValidationResultInfo(crossFieldPath, null);
			val.setMessage(message);
	        val.setLevel(errorLevel);
	        valResults.add(val);
    	}
    }
    
    protected void validateBoolean(Object value, Constraint constraint, String element, List<ValidationResultInfo> results) {
        if (!(value instanceof Boolean)) {
            try {
                Boolean.valueOf(value.toString());
            } catch (Exception e) {
                ValidationResultInfo val = new ValidationResultInfo(element, value);
                val.setError(getMessage("validation.mustBeBoolean"));
                results.add(val);
            }
        }
    }

    protected void validateDouble(Object value, Constraint constraint, String element, List<ValidationResultInfo> results) {
        Double v = null;

        ValidationResultInfo val = new ValidationResultInfo(element, value);

        if (value instanceof Number) {
            v = ((Number) value).doubleValue();
        } else {
            try {
                v = Double.valueOf(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeDouble"));
            }
        }

        if (val.isOk()) {
            Double maxValue = ValidatorUtils.getDouble(constraint.getInclusiveMax());
            Double minValue = ValidatorUtils.getDouble(constraint.getExclusiveMin());

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange"), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    protected void validateFloat(Object value, Constraint constraint, String element, List<ValidationResultInfo> results) {
        Float v = null;

        ValidationResultInfo val = new ValidationResultInfo(element, value);
        if (value instanceof Number) {
            v = ((Number) value).floatValue();
        } else {
            try {
                v = Float.valueOf(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeFloat"));
            }
        }

        if (val.isOk()) {
            Float maxValue = ValidatorUtils.getFloat(constraint.getInclusiveMax());
            Float minValue = ValidatorUtils.getFloat(constraint.getExclusiveMin());

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange"), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    protected void validateLong(Object value, Constraint constraint, String element, List<ValidationResultInfo> results) {
        Long v = null;

        ValidationResultInfo val = new ValidationResultInfo(element, value);
        if (value instanceof Number) {
            v = ((Number) value).longValue();
        } else {
            try {
                v = Long.valueOf(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeLong"));
            }
        }

        if (val.isOk()) {
            Long maxValue = ValidatorUtils.getLong(constraint.getInclusiveMax());
            Long minValue = ValidatorUtils.getLong(constraint.getExclusiveMin());

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange"), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }

    }

    protected void validateInteger(Object value, Constraint constraint, String element, List<ValidationResultInfo> results) {
        Integer v = null;

        ValidationResultInfo val = new ValidationResultInfo(element, value);

        if (value instanceof Number) {
            v = ((Number) value).intValue();
        } else {
            try {
                v = Integer.valueOf(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeInteger"));
            }
        }

        if (val.isOk()) {
            Integer maxValue = ValidatorUtils.getInteger(constraint.getInclusiveMax());
            Integer minValue = ValidatorUtils.getInteger(constraint.getExclusiveMin());

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange"), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    protected void validateDate(Object value, Constraint constraint, String element, List<ValidationResultInfo> results, DateParser dateParser) {
        ValidationResultInfo val = new ValidationResultInfo(element, value);

        Date v = null;

        if (value instanceof Date) {
            v = (Date) value;
        } else {
            try {
                v = dateParser.parseDate(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeDate"));
            }
        }

        if (val.isOk()) {
            Date maxValue = ValidatorUtils.getDate(constraint.getInclusiveMax(), dateParser);
            Date minValue = ValidatorUtils.getDate(constraint.getExclusiveMin(), dateParser);

            if (maxValue != null && minValue != null) {
                // validate range
                if (v.getTime() > maxValue.getTime() || v.getTime() < minValue.getTime()) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange"), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v.getTime() > maxValue.getTime()) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v.getTime() < minValue.getTime()) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    protected void validateString(Object value, Constraint constraint, String element, List<ValidationResultInfo> results) {

        if (value == null) {
            value = "";
        }
        String s = value.toString().trim();

        ValidationResultInfo val = new ValidationResultInfo(element, value);

        Integer maxLength = tryParse(constraint.getMaxLength());
        if (maxLength != null && constraint.getMinLength() != null && constraint.getMinLength() > 0) {
            if (s.length() > maxLength || s.length() < constraint.getMinLength()) {
                val.setError(MessageUtils.interpolate(getMessage("validation.lengthOutOfRange"), toMap(constraint)));
            }
        } else if (maxLength != null) {
            if (s.length() > Integer.parseInt(constraint.getMaxLength())) {
                val.setError(MessageUtils.interpolate(getMessage("validation.maxLengthFailed"), toMap(constraint)));
            }
        } else if (constraint.getMinLength() != null && constraint.getMinLength() > 0) {
            if (s.length() < constraint.getMinLength()) {
                val.setError(MessageUtils.interpolate(getMessage("validation.minLengthFailed"), toMap(constraint)));
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    protected String getMessage(String messageId) {
        if (null == messageService) {
            return messageId;
        }

        Message msg = messageService.getMessage(messageLocaleKey, messageGroupKey, messageId);

        return msg.getValue();
    }

    protected String getElementXpath(Stack<String> elementStack) {
        StringBuilder xPath = new StringBuilder();
        Iterator<String> itr = elementStack.iterator();
        while (itr.hasNext()) {
            xPath.append(itr.next());
            if(itr.hasNext()){
            	xPath.append("/");
            }
        }

        return xPath.toString();
    }

    /*
     * Homemade has text so we dont need outside libs.
     */
    protected boolean hasText(String string) {

        if (string == null || string.length() < 1) {
            return false;
        }
        int stringLength = string.length();

        for (int i = 0; i < stringLength; i++) {
            char currentChar = string.charAt(i);
            if (' ' != currentChar || '\t' != currentChar || '\n' != currentChar) {
                return true;
            }
        }

        return false;
    }

    protected Map<String, Object> toMap(Constraint c) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("minOccurs", c.getMinOccurs());
        result.put("maxOccurs", c.getMaxOccurs());
        result.put("minLength", c.getMinLength());
        result.put("maxLength", c.getMaxLength());
        result.put("minValue", c.getExclusiveMin());
        result.put("maxValue", c.getInclusiveMax());
        // result.put("dataType", c.getDataType());

        return result;
    }

    public SearchDispatcher getSearchDispatcher() {
        return searchDispatcher;
    }

    public void setSearchDispatcher(SearchDispatcher searchDispatcher) {
        this.searchDispatcher = searchDispatcher;
    }

	@Override
	public List<ValidationResultInfo> validateObject(FieldDefinition field,
			Object o, ObjectStructureDefinition objStructure,Stack<String> elementStack) {
		return null;
	}
}
