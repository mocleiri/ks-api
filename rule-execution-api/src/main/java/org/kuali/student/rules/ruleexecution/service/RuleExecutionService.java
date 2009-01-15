/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.ruleexecution.service;

import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.poc.common.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.ruleexecution.dto.AgendaExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;

/**
 * This is the rule execution runtime service interface.
 * 
 * @author lcarlsen
 *
 */
@WebService(name = "RuleExecutionService",
		targetNamespace = "http://student.kuali.org/wsdl/brms/RuleExecution")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, 
		 use = SOAPBinding.Use.LITERAL, 
		 parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface RuleExecutionService {

	/**
     * Executes an <code>agenda</code> with <code>exectionParamMap</code>.
     * <code>exectionParamMap</code> must match the fact criteria type meta data.
	 * 
     * @param agenda Agenda to execute
     * @param exectionParamMap Execution fact parameter map
	 * @return Result of executing the agenda
     * @throws DoesNotExistException Thrown if agenda does not exist
     * @throws InvalidParameterException Thrown if agenda is invalid
     * @throws MissingParameterException Thrown if agenda is null or has missing parameters
     * @throws OperationFailedException Thrown if execution fails
	 */
    @WebMethod
    public AgendaExecutionResultDTO executeAgenda(
    		@WebParam(name="agenda")String agendaId, 
    		@WebParam(name="exectionParamMap") @XmlJavaTypeAdapter(value=JaxbAttributeMapListAdapter.class, type=Map.class) Map<String,String> exectionParamMap)
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Executes a business rule by <code>businessRuleId</code> with a 
     * <code>exectionParamMap</code>.
     * <code>exectionParamMap</code> must match the fact criteria type meta data.
     * <code>exectionParamMap</code> can be null for static facts. </p>
     * 
     * @param businessRule A Business rule
     * @param exectionParamMap Execution fact parameter map
	 * @return Result of executing the business rule
     * @throws DoesNotExistException Thrown if business rule id does not exist
     * @throws InvalidParameterException Thrown if business rule id is invalid
     * @throws MissingParameterException Thrown if business rule id is null or empty
     * @throws OperationFailedException Thrown if execution fails
     */
    @WebMethod
    public ExecutionResultDTO executeBusinessRule(
    		@WebParam(name="businessRuleId")String businessRuleId, 
    		@WebParam(name="exectionParamMap") @XmlJavaTypeAdapter(value=JaxbAttributeMapListAdapter.class, type=Map.class) Map<String,String> exectionParamMap)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * <p>Executes a business rule with a <code>factStructure</code> to test 
     * that it executes properly.<br/> 
     * <code>exectionParamMap</code> must match the fact criteria type meta data.
     * <code>exectionParamMap</code> can be null for static facts. </p>
     * <p><b>Note:</b> The business rule is <b>not</b> stored in the 
     * rule repository <b>nor</b> cached in rule execution memory.</p>
     * 
     *  
     * @param businessRule Functional business rule
     * @param exectionParamMap Execution fact parameter map
	 * @return Result of executing the business rule
     * @throws DoesNotExistException Thrown if business rule id does not exist
	 * @throws MissingParameterException Thrown if parameter is missing
     * @throws InvalidParameterException Thrown if method parameters are invalid
     * @throws OperationFailedException Thrown if business rule translation or execution fails
     */
    @WebMethod
    public ExecutionResultDTO executeBusinessRuleTest(
    		@WebParam(name="businessRule")BusinessRuleInfoDTO businessRule, 
    		@WebParam(name="exectionParamMap") @XmlJavaTypeAdapter(value=JaxbAttributeMapListAdapter.class, type=Map.class) Map<String,String> exectionParamMap)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
}
