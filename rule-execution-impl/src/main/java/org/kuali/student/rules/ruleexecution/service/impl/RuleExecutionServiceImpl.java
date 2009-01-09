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
package org.kuali.student.rules.ruleexecution.service.impl;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jws.WebService;

import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.factfinder.dto.FactParamDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.factfinder.service.FactFinderService;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.FactParamDefTimeKey;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.service.RuleRepositoryService;
import org.kuali.student.rules.ruleexecution.dto.AgendaExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.dto.PropositionReportDTO;
import org.kuali.student.rules.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.rules.ruleexecution.runtime.AgendaExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.RuleSetExecutor;
import org.kuali.student.rules.ruleexecution.service.RuleExecutionService;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.RuntimeAgendaDTO;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.rules.ruleexecution.service.RuleExecutionService", 
		serviceName = "RuleExecutionService", 
		portName = "RuleExecutionService", 
		targetNamespace = "http://student.kuali.org/wsdl/brms/RuleExecution")
@Transactional
public class RuleExecutionServiceImpl implements RuleExecutionService {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleExecutionServiceImpl.class);
    
    private RuleSetExecutor ruleSetExecutor;

    private RuleSetExecutor ruleSetExecutorTest;
    
    private RuleRepositoryService ruleRespositoryService;

    private RuleManagementService ruleManagementService;

    private FactFinderService factFinderService;
    
    private boolean ruleSetCachingEnabled = true;

    public void setEnableRuleSetCaching(final boolean enableCaching) {
    	this.ruleSetCachingEnabled = enableCaching;
    }
    
    /**
     * Gets the rule execution engine.
     * 
     * @return Rule execution engine
     */
	public RuleSetExecutor getRuleSetExecutor() {
		return ruleSetExecutor;
	}

	/**
     * Sets the rule execution engine.
	 * 
	 * @param ruleSetExecutor Rule execution engine
	 */
	public void setRuleSetExecutor(final RuleSetExecutor ruleSetExecutor) {
		this.ruleSetExecutor = ruleSetExecutor;
	}

	/**
     * Sets the rule execution engine used for testing.
	 * 
	 * @param ruleSetExecutor Rule execution engine
	 */
	public void setRuleSetExecutorTest(final RuleSetExecutor ruleSetExecutor) {
		this.ruleSetExecutorTest = ruleSetExecutor;
	}

	/**
	 * Sets the rule repository service.
	 * 
	 * @param ruleRespositoryService Rule repository service
	 */
	public void setRuleRespositoryService(final RuleRepositoryService ruleRespositoryService) {
		this.ruleRespositoryService = ruleRespositoryService;
	}

	/**
	 * Gets the rule management service.
	 * 
	 * @param ruleManagementService Rule management service
	 */
	public void setRuleManagementService(final RuleManagementService ruleManagementService) {
		this.ruleManagementService = ruleManagementService;
	}

	/**
	 * Sets the fact finder service.
	 * 
	 * @param factFinderService Fact finder service
	 */
	public void setFactFinderService(final FactFinderService factFinderService) {
		this.factFinderService = factFinderService;
	}

	/**
	 * Gets a rule set from the repository from rule set UUID.
	 * 
	 * @param businessRule Business rule
	 * @param ruleSetUUID Rule set UUID
	 * @return A rule set
	 * @throws OperationFailedException
	 * @throws InvalidParameterException
	 */
	private RuleSetDTO getRuleSet(BusinessRuleInfoDTO businessRule, String ruleSetUUID) 
		throws OperationFailedException, InvalidParameterException {
		RuleSetDTO ruleSet = null;
		if (businessRule.getStatus().equals(BusinessRuleStatus.ACTIVE.toString())) {
			String SnapshotName = businessRule.getRepositorySnapshotName();
    		ruleSet = this.ruleRespositoryService.fetchRuleSetSnapshot(ruleSetUUID, SnapshotName);
    	} else {
    		ruleSet = this.ruleRespositoryService.fetchRuleSet(ruleSetUUID);
    	}
		return ruleSet;
	}
	
	private void addRuleSet(BusinessRuleInfoDTO businessRule) throws OperationFailedException, InvalidParameterException {
		if (this.ruleSetCachingEnabled) {
    		if (!this.ruleSetExecutor.containsRuleSet(businessRule)) {
				RuleSetDTO ruleSet = getRuleSet(businessRule, businessRule.getCompiledId());
				this.ruleSetExecutor.addRuleSet(businessRule, ruleSet);
    		}
		} else {
			RuleSetDTO ruleSet = getRuleSet(businessRule, businessRule.getCompiledId());
			this.ruleSetExecutor.removeRuleSet(businessRule, ruleSet);
			this.ruleSetExecutor.addRuleSet(businessRule, ruleSet);
		}
	}

    private ExecutionResultDTO createExecutionResultDTO(ExecutionResult executionResult) {
    	ExecutionResultDTO dto = new ExecutionResultDTO();
    	dto.setExecutionLog(executionResult.getExecutionLog());
    	dto.setErrorMessage(executionResult.getErrorMessage());
    	dto.setExecutionResult(executionResult.getExecutionResult());
    	
    	PropositionReportDTO reportDTO = new PropositionReportDTO();
    	PropositionReport report = executionResult.getReport();
    	if (report != null) {
	    	reportDTO.setSuccessful(report.isSuccessful());
	    	reportDTO.setFailureMessage(report.getFailureMessage());
	    	reportDTO.setSuccessMessage(report.getSuccessMessage());
    	}
    	dto.setReport(reportDTO);
    	
    	return dto;
    }

    private AgendaExecutionResultDTO createAgendaExecutionResultDTO(AgendaExecutionResult executionResult) {
    	AgendaExecutionResultDTO executionResultDTO = new AgendaExecutionResultDTO();
    	for(ExecutionResult result : executionResult.getExecutionResultList()) {
    		executionResultDTO.addExecutionResult(createExecutionResultDTO(result));
    	}
    	return executionResultDTO;
    }

    private Map<String, Object> createFactMap(BusinessRuleInfoDTO businessRule, FactStructureDTO executionFact) 
    	throws OperationFailedException, DoesNotExistException {
    	Map<String, Object> factMap = new HashMap<String, Object>();

    	if (businessRule.getRuleElementList() == null) {
    		return factMap;
    	}
    	
    	for(RuleElementDTO ruleElement : businessRule.getRuleElementList()) {
    		RulePropositionDTO ruleProposition = ruleElement.getRuleProposition();
    		if (ruleProposition != null) {
	    		List<FactStructureDTO> list = ruleProposition.getLeftHandSide().getYieldValueFunction().getFactStructureList();
	    		if (list != null) {
		    		for(FactStructureDTO factStructure : list) {
		    			if (factStructure == null || factStructure.isStaticFact()) {
		    				continue;
		    			}
		    			
		    			if (executionFact == null) {
		    				throw new OperationFailedException("Execution fact is null");
		    			}
		    			
		    			String factTypeKey = factStructure.getFactTypeKey();
		    			if (factTypeKey == null || factTypeKey.trim().isEmpty()) {
		    				throw new OperationFailedException("Fact type key is null or empty");
		    			}

		    			if (logger.isInfoEnabled()) {
							logger.info("\n***** Create Fact Map *****" +
									"\nfactStructureId=" + factStructure.getFactStructureId() +
									"\nfactTypeKey=" + factTypeKey);
						}

		    			FactTypeInfoDTO factType = this.factFinderService.fetchFactType(factTypeKey);
		    			Map<String,FactParamDTO> factParamMap = factType.getFactCriteriaTypeInfo().getFactParamMap();

		    			FactStructureDTO executionFactStructure = new FactStructureDTO();
    					executionFactStructure.setFactTypeKey(factTypeKey);
		    			FactStructureDTO definitionFactStructure = new FactStructureDTO();
		    			definitionFactStructure.setFactTypeKey(factTypeKey);

    					//if(executionFact.getFactTypeKey().equals(factTypeKey)) {
				        Map<String, String> definitionParamMap = new HashMap<String, String>();
				        Map<String, String> executionParamMap = new HashMap<String, String>();
		    			for(Entry<String, FactParamDTO> entry : factParamMap.entrySet()) {
		    				String key = entry.getValue().getKey();

			    			if (logger.isInfoEnabled()) {
			    				logger.info("Fact param defTime="+entry.getValue().getDefTime() + ", key"+key);
			    			}

		    				switch(FactParamDefTimeKey.valueOf(entry.getValue().getDefTime())) {
    		    				case KUALI_FACT_EXECUTION_TIME_KEY: {
    				    			if (logger.isInfoEnabled()) {
										logger.info("EXECUTION: paramValueMap="+executionFact.getParamValueMap());
    				    			}

    				    			if (executionFact.getParamValueMap() == null ) {
    		    						throw new OperationFailedException(
    		    								"EXECUTION: Parameter value map is null");    		    					
    		    					}
    		    					if (!executionFact.getParamValueMap().containsKey(key)) {
    		    						throw new OperationFailedException(
    		    								"EXECUTION: Key '" + key +
    		    								"' not found in parameter value map: " + key);    		    					
    		    					}

    		    					String value = executionFact.getParamValueMap().get(key);
    				    			if (logger.isInfoEnabled()) {
										logger.info("EXECUTION: value="+value);
    				    			}
	    		    				executionParamMap.put(key, value);
    		    					break;
    		    				}
    		    				case KUALI_FACT_DEFINITION_TIME_KEY: {
    				    			if (logger.isInfoEnabled()) {
										logger.info("DEFINITION: paramValueMap="+factStructure.getParamValueMap());
    				    			}

    		    					if (factStructure.getParamValueMap() == null ) {
    		    						throw new OperationFailedException("DEFINITION: Parameter value map is null");    		    					
    		    					}
    		    					if (!factStructure.getParamValueMap().containsKey(key)) {
    		    						throw new OperationFailedException(
    		    								"DEFINITION: Key '" + key +
    		    								"' not found in parameter value map");    		    					
    		    					}

    		    					String value = factStructure.getParamValueMap().get(key);
    				    			if (logger.isInfoEnabled()) {
										logger.info("DEFINITION: value="+value);
    				    			}
    	    						definitionParamMap.put(key, value);
    		    					break;
    		    				}
    		    				default: throw new OperationFailedException("Invalid definition time constant: " + entry.getValue().getDefTime());
    		    			}
		    			}
	    				if (executionParamMap.size() > 0) {
	    					executionFactStructure.setFactStructureId(factStructure.getFactStructureId());
	    					executionFactStructure.setParamValueMap(executionParamMap);
    						FactResultDTO factResult = this.factFinderService.fetchFact(factTypeKey, executionFactStructure);
    		    	    	String factKey = FactUtil.createCriteriaKey(executionFactStructure);
    		    			factMap.put(factKey, factResult);
	    				}
	    				if (definitionParamMap.size() > 0) {
	    					definitionFactStructure.setFactStructureId(factStructure.getFactStructureId());
    						definitionFactStructure.setParamValueMap(definitionParamMap);
    						FactResultDTO factResult = this.factFinderService.fetchFact(factTypeKey, definitionFactStructure);
    		    	    	String factKey = FactUtil.createFactKey(definitionFactStructure);
    		    			factMap.put(factKey, factResult);
	    				}
		    		}
	    		}
    		}
    	}
    	
		if (logger.isInfoEnabled()) {
	    	logger.info("\n ***** factMap *****\n"+factMap+"\n");
		}

    	return factMap;
    }
    
	/**
     * Executes an <code>agenda</code> with <code>factStructure</code>.
	 * 
     * @param agenda Agenda to execute
	 * @return Result of executing the agenda
     * @throws DoesNotExistException Thrown if agenda does not exist
     * @throws InvalidParameterException Thrown if agenda is invalid
     * @throws MissingParameterException Thrown if agenda is null or has missing parameters
     * @throws OperationFailedException Thrown if execution fails
	 */
    public AgendaExecutionResultDTO executeAgenda(final String agendaId, final FactStructureDTO factStructure) 
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException 
    {
    	if (agendaId == null) {
    		throw new MissingParameterException("Agenda is null");
    	}

    	// Retrieve runtime agenda from rule management
    	RuntimeAgendaDTO agenda = null;
		Map<String, Object> factMap = new HashMap<String, Object>();
    	
    	try {
    		for(BusinessRuleInfoDTO businessRule : agenda.getBusinessRules()) {
    			addRuleSet(businessRule);
    			Map<String, Object> map = createFactMap(businessRule, factStructure);
    			factMap.putAll(map);
    		}
    		AgendaExecutionResult executionResult = this.ruleSetExecutor.execute(agenda, factMap);
    		return createAgendaExecutionResultDTO(executionResult);
    	} catch(RuleSetExecutionException e) {
    		logger.error(e.getMessage(), e);
    		throw new OperationFailedException(e.getMessage());
    	}
    }

    /**
     * Executes a business rule by <code>businessRuleId</code> with a 
     * <code>factStructure</code>.
     * 
     * @param businessRule A Business rule
     * @param factStructure Fact structure for the business rule
	 * @return Result of executing the business rule
     * @throws DoesNotExistException Thrown if business rule id does not exist
     * @throws InvalidParameterException Thrown if business rule id is invalid
     * @throws MissingParameterException Thrown if business rule id is null or empty
     * @throws OperationFailedException Thrown if execution fails
     */
    public ExecutionResultDTO executeBusinessRule(final String businessRuleId, final FactStructureDTO factStructure)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException 
	{
    	if (businessRuleId == null || businessRuleId.trim().isEmpty()) {
    		throw new MissingParameterException("Business rule id is null or empty");
    	}

    	// Retrieve business rule
    	BusinessRuleInfoDTO businessRule = this.ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
		addRuleSet(businessRule);
    	
		Map<String, Object> factMap = createFactMap(businessRule, factStructure);
		
        try {
        	ExecutionResult result = this.ruleSetExecutor.execute(businessRule, factMap);
    		return createExecutionResultDTO(result);
    	} catch(RuleSetExecutionException e) {
    		logger.error(e.getMessage(), e);
    		throw new OperationFailedException(e.getMessage());
    	}
    }

    /**
     * Builds a Drools package from <code>source</code>
     * 
     * @param source Drools source code
     * @return A Drools Package
     * @throws Exception
     */
    private Package buildPackage(Reader source) throws Exception {
        PackageBuilder builder = new PackageBuilder();
        builder.addPackageFromDrl(source);
        Package pkg = builder.getPackage();
        return pkg;
    }

    /**
     * <p>Executes a business rule with a <code>factStructure</code> to test that 
     * it executes properly.<br/> 
     * <code>factStructure</code> can be null for 
     * static facts. </p>
     * <p><b>Note:</b> The business rule is <b>not</b> stored in the rule repository 
     * <b>nor</b> cached in rule execution memory.</p>
     * 
     *  
     * @param businessRule Functional business rule
     * @param factStructure Fact structure for the business rule
	 * @return Result of executing the business rule
     * @throws DoesNotExistException Thrown if business rule id does not exist
	 * @throws MissingParameterException Thrown if parameter is missing
     * @throws InvalidParameterException Thrown if method parameters are invalid
     * @throws OperationFailedException Thrown if business rule translation or execution fails
     */
    public ExecutionResultDTO executeBusinessRuleTest(final BusinessRuleInfoDTO businessRule, final FactStructureDTO factStructure)
		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		if (businessRule == null) {
			throw new MissingParameterException("businessRule is null");
		}
		
		RuleSetDTO ruleSet = this.ruleRespositoryService.translateBusinessRule(businessRule);

		Map<String, Object> factMap = createFactMap(businessRule, factStructure);
		
		try {
        	this.ruleSetExecutorTest.addRuleSet(businessRule, ruleSet);
        	ExecutionResult result = this.ruleSetExecutorTest.execute(businessRule, factMap);
    		return createExecutionResultDTO(result);
    	} catch(RuleSetExecutionException e) {
    		logger.error(e.getMessage(), e);
    		throw new OperationFailedException(e.getMessage());
    	}
    	finally {
    		this.ruleSetExecutorTest.removeRuleSet(businessRule, ruleSet);
    	}
    }

}
