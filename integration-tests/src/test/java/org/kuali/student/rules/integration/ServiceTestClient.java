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
package org.kuali.student.rules.integration;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.factfinder.service.FactFinderService;
import org.kuali.student.rules.internal.common.entity.AnchorTypeKey;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.utils.ServiceFactory;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.service.RuleRepositoryService;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.dto.PropositionReportDTO;
import org.kuali.student.rules.ruleexecution.service.RuleExecutionService;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.MetaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;

public class ServiceTestClient {

    private final static String HOST = "http://localhost:8080/brms-ws-0.1.0-SNAPSHOT";
	private static String ruleManagementServiceURL = HOST+"/services/RuleManagementService";
    private static String ruleManagementNamespace = "http://student.kuali.org/wsdl/brms/RuleManagement";
    private static String ruleManagementServiceName = "RuleManagementService";
    private static String ruleManagementServiceInterface = RuleManagementService.class.getName();

    private static String ruleRepositoryServiceURL = HOST+"/services/RuleRepositoryService";
    private static String ruleRepositoryNamespace = "http://student.kuali.org/wsdl/brms/RuleRepository";
    private static String ruleRepositoryServiceName = "RuleRepositoryService";
    private static String ruleRepositoryServiceInterface = RuleRepositoryService.class.getName();

    private static String ruleExecutionServiceURL = HOST+"/services/RuleExecutionService";
    private static String ruleExecutionNamespace = "http://student.kuali.org/wsdl/brms/RuleExecution";
    private static String ruleExecutionServiceName = "RuleExecutionService";
    private static String ruleExecutionServiceInterface = RuleExecutionService.class.getName();

    private static String factServiceURL = HOST+"/services/FactFinderService";
    private static String factNamespace = "http://student.kuali.org/wsdl/brms/FactFinder";
    private static String factServiceName = "FactFinderService";
    private static String factServiceInterface = FactFinderService.class.getName();

    private static RuleManagementService ruleManagementService;
    private static RuleRepositoryService ruleRepositoryService;
    private static RuleExecutionService ruleExecutionService;
    private static FactFinderService factFinderService;
    
	@BeforeClass
    public static void setUpOnce() throws Exception {
    	ruleManagementService = (RuleManagementService) ServiceFactory.getPort(
    			ruleManagementServiceURL + "?wsdl", 
    			ruleManagementNamespace, 
    			ruleManagementServiceName, 
    			ruleManagementServiceInterface, 
    			ruleManagementServiceURL);

    	ruleRepositoryService = (RuleRepositoryService) ServiceFactory.getPort(
    			ruleRepositoryServiceURL + "?wsdl", 
    			ruleRepositoryNamespace, 
    			ruleRepositoryServiceName, 
    			ruleRepositoryServiceInterface, 
    			ruleRepositoryServiceURL);

    	ruleExecutionService = (RuleExecutionService) ServiceFactory.getPort(
    			ruleExecutionServiceURL + "?wsdl", 
    			ruleExecutionNamespace, 
    			ruleExecutionServiceName, 
    			ruleExecutionServiceInterface, 
    			ruleExecutionServiceURL);

    	factFinderService = (FactFinderService) ServiceFactory.getPort(
    			factServiceURL + "?wsdl", 
    			factNamespace, 
    			factServiceName, 
    			factServiceInterface, 
    			factServiceURL);
	}

    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() throws Exception {
    }

    private boolean containsResult(List<Map<String,String>> set, String column, String value) {
    	for(Map<String,String> map : set) {
    		if (map.get(column).equals(value)) {
    			return true;
    		}
    	}
    	return false;
    }

    private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute, 0);
    	return cal.getTime();
    }

    /*
     * Builds the fact structure to be used as a criteria
     */
    private FactStructureDTO buildFactStructureForRuleCriteria(boolean staticFact) {
        FactStructureDTO fs = new FactStructureDTO();
        fs.setFactStructureId("1");
        fs.setFactTypeKey("fact.clusetId");
        fs.setAnchorFlag(false);

        Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
        resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY, "resultColumn.cluId");
        fs.setResultColumnKeyTranslations(resultColumnKeyMap);
        
        fs.setStaticFact(staticFact);
        if (staticFact) {
	        fs.setStaticValue("CPR101");
	        fs.setStaticValueDataType(String.class.getName());
        } else {
		    Map<String, String> paramMap = new HashMap<String, String>();
		    paramMap.put("factParam.clusetId", "PSYC 200");
		    fs.setParamValueMap(paramMap);
        }        

        return fs;
    }

    /*
     * Builds the fact structure to be used as a 'rule fact' in an intersection
     */
    private FactStructureDTO buildFactStructureForIntersection(boolean staticFact) {
        FactStructureDTO fs = new FactStructureDTO();
        fs.setFactStructureId("2");
        fs.setFactTypeKey("fact.completed_course_list");
        fs.setAnchorFlag(false);

        Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
        resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY, "resultColumn.cluId");
        fs.setResultColumnKeyTranslations(resultColumnKeyMap);
        
        fs.setStaticFact(staticFact);
        if (staticFact) {
        	fs.setStaticValue("CPR101, CPR201, CPR301");
            fs.setStaticValueDataType(String.class.getName());
        } else {
		    Map<String, String> paramMap = new HashMap<String, String>();
		    paramMap.put("factParam.studentId", "student1");
		    fs.setParamValueMap(paramMap);
        }        
        return fs;                
    }

    private BusinessRuleInfoDTO generateNewBusinessRuleInfo(String ruleName, String anchor) {
    	return generateNewBusinessRuleInfo(ruleName, anchor, true);
    }

    private BusinessRuleInfoDTO generateNewBusinessRuleInfo(String ruleName, String anchor, boolean staticFact) {
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateID("Zdenek");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateID("Len");
     
        FactStructureDTO fs1 = buildFactStructureForRuleCriteria(staticFact);
        FactStructureDTO fs2 = buildFactStructureForIntersection(staticFact);
        
        List<FactStructureDTO> factStructureList = new ArrayList<FactStructureDTO>();
        factStructureList.add(fs1);
        factStructureList.add(fs2);
        
        YieldValueFunctionDTO yieldValueFunctionDTO = new YieldValueFunctionDTO();
        yieldValueFunctionDTO.setYieldValueFunctionType(YieldValueFunctionType.INTERSECTION.toString());
        yieldValueFunctionDTO.setFactStructureList(factStructureList);

        LeftHandSideDTO leftHandSideDTO = new LeftHandSideDTO();
        leftHandSideDTO.setYieldValueFunction(yieldValueFunctionDTO);

        RightHandSideDTO rightHandSideDTO = new RightHandSideDTO();
        rightHandSideDTO.setExpectedValue("12");

        RulePropositionDTO rulePropositionDTO = new RulePropositionDTO();
        rulePropositionDTO.setName("Credit Check");
        rulePropositionDTO.setDescription("Credit Intersection Change");
        rulePropositionDTO.setLeftHandSide(leftHandSideDTO);
        rulePropositionDTO.setRightHandSide(rightHandSideDTO);
        rulePropositionDTO.setComparisonDataTypeKey(Double.class.getName());
        rulePropositionDTO.setComparisonOperatorTypeKey(ComparisonOperator.LESS_THAN.toString());

        RuleElementDTO reDTO = new RuleElementDTO();
        reDTO.setName("Pre-req 1");
        reDTO.setDescription("Pre req check for Math 101");
        reDTO.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString());
        reDTO.setBusinessRuleProposition(rulePropositionDTO);

        BusinessRuleInfoDTO brInfoDTO = new BusinessRuleInfoDTO();
        //brInfoDTO.setId("123");
        brInfoDTO.setName(ruleName);
        brInfoDTO.setDesc("Prerequsite courses required in order to enroll in CHEM 100");
        brInfoDTO.setSuccessMessage("Test success message");
        brInfoDTO.setFailureMessage("Test failure message");
        brInfoDTO.setType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchorValue(anchor);
        brInfoDTO.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
        brInfoDTO.setMetaInfo(metaInfo);

        //brInfoDTO.setEffectiveStartTime(new Date());
        //brInfoDTO.setEffectiveEndTime(new Date());
        Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2010, 1, 1, 12, 00);
        brInfoDTO.setEffectiveDate(effectiveStartTime);
        brInfoDTO.setExpirationDate(effectiveEndTime);

        List<RuleElementDTO> elementList = new ArrayList<RuleElementDTO>();
        elementList.add(reDTO);

        brInfoDTO.setBusinessRuleElementList(elementList);
        
        return brInfoDTO;
    }
    
    private BusinessRuleInfoDTO generateNewEmptyBusinessRuleInfo(String businessRuleId, String ruleName, String anchor) {
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateTime(new Date());
        metaInfo.setCreateID("");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setUpdateID("");
     
        BusinessRuleInfoDTO brInfoDTO = new BusinessRuleInfoDTO();
        //brInfoDTO.setId(businessRuleId);
        brInfoDTO.setName(ruleName);
        brInfoDTO.setDesc("Prerequsite courses required in order to enroll in CHEM 100");
        brInfoDTO.setType(BusinessRuleTypeKey.KUALI_PRE_REQ.toString());
        brInfoDTO.setAnchorTypeKey(AnchorTypeKey.KUALI_COURSE.toString());
        brInfoDTO.setAnchorValue(anchor);
        brInfoDTO.setState(BusinessRuleStatus.DRAFT_IN_PROGRESS.toString());
        brInfoDTO.setMetaInfo(metaInfo);

		Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
		Date effectiveEndTime = createDate(2010, 1, 1, 12, 00);
		brInfoDTO.setEffectiveDate(effectiveStartTime);
		brInfoDTO.setExpirationDate(effectiveEndTime);
        
        return brInfoDTO;
    }
    
    @Test
    public void testFindBusinessRuleTypesFromTestBeans() throws Exception {
    	System.out.println("*****  testFindBusinessRuleTypesFromTestBeans  *****");

		List<String> businessRuleIdList1 = ruleManagementService.findBusinessRuleIdsByBusinessRuleType("KUALI_PRE_REQ");
		Assert.assertNotNull(businessRuleIdList1);
		System.out.println("Business Rule ID1: "+businessRuleIdList1);
		List<String> businessRuleIdList2 = ruleManagementService.findBusinessRuleIdsByBusinessRuleType("KUALI_CO_REQ");
		Assert.assertNotNull(businessRuleIdList2);
		System.out.println("Business Rule ID2: "+businessRuleIdList2);
    }
    
    @Test
    public void testFindBusinessRuleTypesFromTestBeansAndExecute_StaticFact() throws Exception {
    	System.out.println("*****  testFindBusinessRuleTypesFromTestBeansAndExecute_StaticFact  *****");

		List<String> businessRuleIdList1 = ruleManagementService.findBusinessRuleIdsByBusinessRuleType("KUALI_PRE_REQ");
		List<String> businessRuleIdList2 = ruleManagementService.findBusinessRuleIdsByBusinessRuleType("KUALI_CO_REQ");

		businessRuleIdList1.addAll(businessRuleIdList2);
		System.out.println("Business Rule ID1: "+businessRuleIdList1);
		
        for(String businessRuleId : businessRuleIdList1) {
    		// Ignore since it has dynamic facts
    		if (businessRuleId.equals("11223344-1122-1122-1112-100000000032") ||
    		    businessRuleId.equals("11223344-1122-1122-1112-100000000041")) {
        			continue;
        		}
    		
    		System.out.println("Executing Business Rule ID: "+businessRuleId);
    		
    		// Update business rule to translate/compile and create a new rule set in the rule repository
    		BusinessRuleInfoDTO businessRuleInfo = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
//    		RuleSetDTO ruleSet = ruleRepositoryService.fetchRuleSet(businessRuleInfo.getCompiledId());
//    		if (ruleSet != null) {
	    		ruleManagementService.updateBusinessRule(businessRuleId, businessRuleInfo);
//    		}
    		
    		ExecutionResultDTO executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, null);
    		Assert.assertNotNull(executionResult);
    		System.out.println("Execution result:        "+executionResult.isExecutionSuccessful());
	        System.out.println("Execution error message: "+executionResult.getErrorMessage());
	        System.out.println("Report success:          "+executionResult.getReport().isSuccessful());
	        System.out.println("Report failure message:  "+executionResult.getReport().getFailureMessage());
	        System.out.println("Report success message:  "+executionResult.getReport().getSuccessMessage());
        }
    }
    
    @Test
    public void testCreateBusinessRule() throws Exception {
    	System.out.println("*****  testCreateBusinessRule  *****");
    	BusinessRuleInfoDTO businessRule1 = generateNewBusinessRuleInfo("CHEM200PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1).getId();
    		BusinessRuleInfoDTO businessRule2 = ruleManagementService.fetchBusinessRuleInfo(businessRuleId);
    		Assert.assertEquals(businessRule1.getName(), businessRule2.getName());
    		
    		System.out.println("Business Rule ID:   "+businessRule2.getId());
	        System.out.println("Business Rule Name: "+businessRule2.getName());
    	} finally {
    		//ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }
    
    @Test
    public void testCreateEmptyBusinessRule() throws Exception {
    	System.out.println("\n\n*****  testCreateEmptyBusinessRule  *****");
    	BusinessRuleInfoDTO businessRule1 = generateNewEmptyBusinessRuleInfo("1000", "CHEM200PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1).getId();
	        System.out.println("Business Rule ID:   "+businessRuleId);
    		BusinessRuleInfoDTO businessRule2 = ruleManagementService.fetchBusinessRuleInfo(businessRuleId);
	        System.out.println("Business Rule Name: "+businessRule2.getName());
	        Assert.assertEquals(businessRule1.getName(), businessRule2.getName());
    	} finally {
    		//ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }
    
    @Test
    public void testCreateAndFetchBusinessRule() throws Exception {
    	System.out.println("\n\n*****  testCreateAndFetchBusinessRule  *****");
    	BusinessRuleInfoDTO businessRule = generateNewBusinessRuleInfo("CHEM100PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule).getId();
	        Assert.assertNotNull(businessRuleId);
	        System.out.println("businessRuleId:         "+businessRuleId);
	        
	        // fetchDetailedBusinessRuleInfo fails 
	        businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
	        System.out.println("Business Rule ID:       "+businessRule.getId());
	        System.out.println("Business Rule Name:     "+businessRule.getName());
	        System.out.println("Business Compiled ID:   "+businessRule.getCompiledId());
	        
	        RuleSetDTO ruleSet = ruleRepositoryService.fetchRuleSet(businessRule.getCompiledId());
	        Assert.assertNotNull(ruleSet);
	        System.out.println("RuleSet Name:           "+ruleSet.getName());
	        System.out.println("RuleSet UUID:           "+ruleSet.getUUID());
	        System.out.println("RuleSet Version Number: "+ruleSet.getVersionNumber());
	        System.out.println("RuleSet Source:\n"+ruleSet.getContent());
    	} finally {
    		//ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }

    @Test
    public void testUpdateBusinessRule() throws Exception {
    	System.out.println("\n\n*****  testUpdateBusinessRule  *****");
    	BusinessRuleInfoDTO businessRule = generateNewBusinessRuleInfo("CHEM200PRE_REQ", "CHEM100");
    	
    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule).getId();
	        Assert.assertNotNull(businessRuleId);
	        System.out.println("businessRuleId:         "+businessRuleId);
	        businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
	        String status = ruleManagementService.updateBusinessRule(businessRuleId, businessRule).getState();
	        Assert.assertNotNull(status);
	        System.out.println("status:                 "+status);
	
	        // fetchDetailedBusinessRuleInfo fails 
	        businessRule = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
	        Assert.assertNotNull(businessRule);
	        System.out.println("Business Rule ID:       "+businessRule.getId());
	        System.out.println("Business Rule Name:     "+businessRule.getName());
	        System.out.println("Business Compiled ID:   "+businessRule.getCompiledId());
	        
	        RuleSetDTO ruleSet = ruleRepositoryService.fetchRuleSet(businessRule.getCompiledId());
	        Assert.assertNotNull(ruleSet);
	        System.out.println("RuleSet Name:           "+ruleSet.getName());
	        System.out.println("RuleSet UUID:           "+ruleSet.getUUID());
	        System.out.println("RuleSet Version Number: "+ruleSet.getVersionNumber());
	        System.out.println("RuleSet Source:\n"+ruleSet.getContent());
    	} finally {
    		//ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }

    @Test
    public void testCreateBusinessRuleAndExecute_StaticFact() throws Exception {
    	System.out.println("\n\n*****  testCreateBusinessRuleAndExecute  *****");
    	BusinessRuleInfoDTO businessRule1 = generateNewBusinessRuleInfo("CHEM100PRE_REQ", "CHEM100");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1).getId();
    		BusinessRuleInfoDTO businessRule = ruleManagementService.fetchBusinessRuleInfo(businessRuleId);
	        Assert.assertNotNull(businessRule);
	        System.out.println("Business Rule ID:        "+businessRule.getId());
	        System.out.println("Business Rule Name:      "+businessRule.getName());

	        ExecutionResultDTO executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, null);
	        Assert.assertNotNull(executionResult);
	        System.out.println("Execution result:        "+executionResult.isExecutionSuccessful());
	        System.out.println("Execution error message: "+executionResult.getErrorMessage());
	        System.out.println("Report success:          "+executionResult.getReport().isSuccessful());
	        System.out.println("Report failure message:  "+executionResult.getReport().getFailureMessage());
	        System.out.println("Report success message:  "+executionResult.getReport().getSuccessMessage());
	        //System.out.println("Execution log:\n"+executionResult.getExecutionLog());
	        Assert.assertTrue(executionResult.getReport().isSuccessful());
    	} finally {
    		//ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }
    
    @Test
    public void testFindFactTypes() throws Exception {
    	System.out.println("\n\n*****  testFindFactTypes  *****");
    	List<FactTypeInfoDTO> factTypes = factFinderService.findFactTypes();
        Assert.assertNotNull(factTypes);
    	for(FactTypeInfoDTO factTypeInfo : factTypes) {
    		System.out.println("Fact type name:        "+factTypeInfo.getName());
    		System.out.println("Fact type description: "+factTypeInfo.getDescription());
    		System.out.println("Fact type key:         "+factTypeInfo.getFactTypeKey());
    	}
    }

    @Test
    public void testFetchDynamicFact() throws Exception {
    	System.out.println("\n\n*****  testFetchDynamicFact  *****");
        String factTypeKey = "fact.completed_course_list";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("factParam.studentId", "student1");
        
        FactStructureDTO factStructureDTO = new FactStructureDTO();
        factStructureDTO.setFactTypeKey(factTypeKey);
        factStructureDTO.setParamValueMap(paramMap);
        
        FactResultDTO result = factFinderService.fetchFact(factTypeKey, factStructureDTO);
        Assert.assertNotNull(result);
        
        Assert.assertEquals(result.getFactResultTypeInfo().getKey(), "result.completedCourseInfo");
        Assert.assertEquals(2, result.getFactResultTypeInfo().getResultColumnsMap().size());
        
        Assert.assertEquals(4, result.getResultList().size());
        Assert.assertTrue(containsResult(result.getResultList(), "resultColumn.cluId", "PSYC 200"));
        Assert.assertTrue(containsResult(result.getResultList(), "resultColumn.cluId", "PSYC 201"));
        Assert.assertTrue(containsResult(result.getResultList(), "resultColumn.cluId", "PSYC 202"));
    }

    @Test
    public void testCreateAndExecuteBusinessRule_DynamicFact() throws Exception {
    	System.out.println("\n\n*****  testCreateAndExecuteBusinessRule_DynamicFact  *****");
    	BusinessRuleInfoDTO businessRule1 = generateNewBusinessRuleInfo("CHEM200PRE_REQ", "CHEM200", false);

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("factParam.studentId", "student1");

    	String businessRuleId = null;
    	try {
    		businessRuleId = ruleManagementService.createBusinessRule(businessRule1).getId();
            Assert.assertNotNull(businessRuleId);
    		BusinessRuleInfoDTO businessRule2 = ruleManagementService.fetchDetailedBusinessRuleInfo(businessRuleId);
	        System.out.println("Business Rule ID:        "+businessRule2.getId());
	        System.out.println("Business Rule Name :     "+businessRule2.getName());

	        ExecutionResultDTO executionResult = ruleExecutionService.executeBusinessRule(businessRuleId, paramMap);
            Assert.assertNotNull(executionResult);
	        System.out.println("Execution result:        "+executionResult.isExecutionSuccessful());
	        System.out.println("Execution error message: "+executionResult.getErrorMessage());
	        System.out.println("Report success:          "+executionResult.getReport().isSuccessful());
	        System.out.println("Report failure message:  "+executionResult.getReport().getFailureMessage());
	        System.out.println("Report success message:  "+executionResult.getReport().getSuccessMessage());
	        //System.out.println("Execution log:\n"+executionResult.getExecutionLog());
	        Assert.assertTrue(executionResult.getReport().isSuccessful());

	        // Test proposition reports
	        Assert.assertEquals(1, executionResult.getReport().getPropositionReports().size());
	        
	        PropositionReportDTO prDTO = executionResult.getReport().getPropositionReports().get(0);
	        
	        Assert.assertEquals("P1", prDTO.getPropositionName());
	        Assert.assertTrue(prDTO.isSuccessful());
	        
	        // Test criteria facts
	        Map<String,String> criteriaRowMap = prDTO.getCriteriaResult().getResultList().get(0);
			Assert.assertEquals(1, criteriaRowMap.size());
	        Assert.assertEquals("PSYC 200", criteriaRowMap.get("resultColumn.cluId"));

	        // Test facts
	        Map<String,String> factRowMap1 = prDTO.getFactResult().getResultList().get(0);
	        Map<String,String> factRowMap2 = prDTO.getFactResult().getResultList().get(1);
	        Map<String,String> factRowMap3 = prDTO.getFactResult().getResultList().get(2);
	        Assert.assertEquals(2, factRowMap1.size());
	        Assert.assertEquals(2, factRowMap2.size());
	        Assert.assertEquals(2, factRowMap3.size());
	        Assert.assertEquals("PSYC 200", factRowMap1.get("resultColumn.cluId"));
	        Assert.assertEquals("PSYC 201", factRowMap2.get("resultColumn.cluId"));
	        Assert.assertEquals("PSYC 202", factRowMap3.get("resultColumn.cluId"));

			FactResultDTO propositionResult1 = prDTO.getPropositionResult();
	        Assert.assertEquals(1, propositionResult1.getResultList().size());
			Assert.assertTrue(containsResult(propositionResult1.getResultList(), "resultColumn.cluId", "PSYC 200"));
    	} finally {
    		//ruleManagementService.deleteBusinessRule(businessRuleId);
    	}
    }
    
    @Test
    public void testCreateAndExecuteBusinessRuleTest_StaticFact() throws Exception {
    	System.out.println("\n\n*****  testCreateAndExecuteBusinessRuleTest_StaticFact  *****");
    	BusinessRuleInfoDTO businessRule1 = generateNewBusinessRuleInfo("CHEM100PRE_REQ_TEST", "CHEM100");
    	businessRule1.setId("xxx");

        ExecutionResultDTO executionResult = ruleExecutionService.executeBusinessRuleTest(businessRule1, null);
        Assert.assertNotNull(executionResult);

        System.out.println("Execution result:        "+executionResult.isExecutionSuccessful());
        System.out.println("Execution error message: "+executionResult.getErrorMessage());
        System.out.println("Report success:          "+executionResult.getReport().isSuccessful());
        System.out.println("Report failure message:  "+executionResult.getReport().getFailureMessage());
        System.out.println("Report success message:  "+executionResult.getReport().getSuccessMessage());
        //System.out.println("Execution log:\n"+executionResult.getExecutionLog());
        Assert.assertTrue(executionResult.getReport().isSuccessful());

        // Test proposition reports
        Assert.assertEquals(1, executionResult.getReport().getPropositionReports().size());

        PropositionReportDTO prDTO = executionResult.getReport().getPropositionReports().get(0);
        
        Assert.assertEquals("P1", prDTO.getPropositionName());
        Assert.assertTrue(prDTO.isSuccessful());
        
        // Test criteria facts
        Map<String,String> criteriaRowMap = prDTO.getCriteriaResult().getResultList().get(0);
        Assert.assertEquals(1, criteriaRowMap.size());
        Assert.assertEquals("CPR101", criteriaRowMap.get(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN));

        // Test facts
        Map<String,String> factRowMap1 = prDTO.getFactResult().getResultList().get(0);
        Map<String,String> factRowMap2 = prDTO.getFactResult().getResultList().get(1);
        Map<String,String> factRowMap3 = prDTO.getFactResult().getResultList().get(2);
        Assert.assertEquals(1, factRowMap1.size());
        Assert.assertEquals(1, factRowMap2.size());
        Assert.assertEquals(1, factRowMap3.size());
        Assert.assertEquals("CPR101", factRowMap1.get(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN));
        Assert.assertEquals("CPR201", factRowMap2.get(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN));
        Assert.assertEquals("CPR301", factRowMap3.get(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN));

		FactResultDTO propositionResult1 = prDTO.getPropositionResult();
        Assert.assertEquals(1, propositionResult1.getResultList().size());
		Assert.assertTrue(containsResult(propositionResult1.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "CPR101"));
    }

    @Test
    public void testCreateAndExecuteBusinessRuleTest_DynamicFact() throws Exception {
    	System.out.println("\n\n*****  testCreateBusinessRuleAndExecute_DynamicFact  *****");
    	BusinessRuleInfoDTO businessRule1 = generateNewBusinessRuleInfo("CHEM100PRE_REQ", "CHEM100", false);
    	businessRule1.setId("xxx");

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("factParam.studentId", "student1");
    	
        ExecutionResultDTO executionResult = ruleExecutionService.executeBusinessRuleTest(businessRule1, paramMap);
        Assert.assertNotNull(executionResult);

        System.out.println("Execution result:        "+executionResult.isExecutionSuccessful());
        System.out.println("Execution error message: "+executionResult.getErrorMessage());
        System.out.println("Report success:          "+executionResult.getReport().isSuccessful());
        System.out.println("Report failure message:  "+executionResult.getReport().getFailureMessage());
        System.out.println("Report success message:  "+executionResult.getReport().getSuccessMessage());
        //System.out.println("Execution log:\n"+executionResult.getExecutionLog());
        Assert.assertTrue(executionResult.getReport().isSuccessful());

        // Test proposition reports
        Assert.assertEquals(1, executionResult.getReport().getPropositionReports().size());
        
        PropositionReportDTO prDTO = executionResult.getReport().getPropositionReports().get(0);
        
        Assert.assertEquals("P1", prDTO.getPropositionName());
        Assert.assertTrue(prDTO.isSuccessful());
        
        // Test criteria facts
        Map<String,String> criteriaRowMap = prDTO.getCriteriaResult().getResultList().get(0);
		Assert.assertEquals(1, criteriaRowMap.size());
        Assert.assertEquals("PSYC 200", criteriaRowMap.get("resultColumn.cluId"));

        // Test facts
        Map<String,String> factRowMap1 = prDTO.getFactResult().getResultList().get(0);
        Map<String,String> factRowMap2 = prDTO.getFactResult().getResultList().get(1);
        Map<String,String> factRowMap3 = prDTO.getFactResult().getResultList().get(2);
        Assert.assertEquals(2, factRowMap1.size());
        Assert.assertEquals(2, factRowMap2.size());
        Assert.assertEquals(2, factRowMap3.size());
        Assert.assertEquals("PSYC 200", factRowMap1.get("resultColumn.cluId"));
        Assert.assertEquals("PSYC 201", factRowMap2.get("resultColumn.cluId"));
        Assert.assertEquals("PSYC 202", factRowMap3.get("resultColumn.cluId"));

		FactResultDTO propositionResult1 = prDTO.getPropositionResult();
        Assert.assertEquals(1, propositionResult1.getResultList().size());
		Assert.assertTrue(containsResult(propositionResult1.getResultList(), "resultColumn.cluId", "PSYC 200"));
    }
    
}
