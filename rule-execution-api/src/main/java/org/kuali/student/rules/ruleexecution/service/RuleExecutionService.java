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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.dto.FactDTO;
import org.kuali.student.rules.ruleexecution.dto.ResultDTO;
import org.kuali.student.rules.rulemanagement.dto.RuntimeAgendaDTO;

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
     * Executes an <code>agenda</code> with <code>fact</code>.
	 * 
     * @param agenda Agenda to execute
	 * @return An execution result
     * @throws DoesNotExistException Thrown if agenda does not exist
     * @throws InvalidParameterException Thrown if agenda is invalid
     * @throws MissingParameterException Thrown if agenda is null or has missing parameters
     * @throws OperationFailedException Thrown if execution fails
	 */
    @WebMethod
    public ExecutionResultDTO executeAgenda(@WebParam(name="agenda")RuntimeAgendaDTO agenda)
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Executes a <code>ruleSet</code> with a <code>fact</code>.
     * 
     * @param ruleSet Rule set to execute
     * @param fact Facts for the <code>ruleSet</code>
	 * @return An execution result
     * @throws InvalidParameterException Thrown if ruleSet or fact is invalid
     * @throws MissingParameterException Thrown if ruleSet or fact is null or has missing parameters
     * @throws OperationFailedException Thrown if execution fails
     */
    @WebMethod
    public ExecutionResultDTO executeRuleSet(@WebParam(name="ruleSet")RuleSetDTO ruleSet, @WebParam(name="fact")FactResultDTO fact)
		throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Executes a <code>ruleSet</code> with a <code>fact</code>.
     * 
     * @param businessRuleId Functional business rule id
	 * @return An execution result
     * @throws DoesNotExistException Thrown if business rule id does not exist
     * @throws InvalidParameterException Thrown if business rule id is invalid
     * @throws MissingParameterException Thrown if business rule id is null or empty
     * @throws OperationFailedException Thrown if execution fails
     */
    public ExecutionResultDTO executeBusinessRule(@WebParam(name="businessRuleId")String businessRuleId)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
}
