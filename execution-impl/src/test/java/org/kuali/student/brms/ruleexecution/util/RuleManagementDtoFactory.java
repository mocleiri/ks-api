package org.kuali.student.brms.ruleexecution.util;

import java.util.List;

import org.kuali.student.brms.factfinder.dto.FactStructureDTO;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.brms.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.brms.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.brms.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.brms.rulemanagement.dto.YieldValueFunctionDTO;

public class RuleManagementDtoFactory {

	public RuleManagementDtoFactory() {}
	
	public static RuleManagementDtoFactory getInstance() {
		return new RuleManagementDtoFactory();
	}
	
	public YieldValueFunctionDTO createYieldValueFunctionDTO(List<FactStructureDTO> factStructureList,
															 String yieldValueFunctionType) {
		YieldValueFunctionDTO dto = new YieldValueFunctionDTO();
		dto.setFactStructureList(factStructureList);
		dto.setYieldValueFunctionType(yieldValueFunctionType);
		return dto;
	}

	public LeftHandSideDTO createLeftHandSideDTO(YieldValueFunctionDTO yieldValueFunction) {
		LeftHandSideDTO dto = new LeftHandSideDTO();
		dto.setYieldValueFunction(yieldValueFunction);
		return dto;
	}

	public RightHandSideDTO createRightHandSideDTO(String expectedValue) {
		RightHandSideDTO dto = new RightHandSideDTO();
		dto.setExpectedValue(expectedValue);
		return dto;
	}

	public RulePropositionDTO createRulePropositionDTO(String name,
													   String comparisonDataType,
													   String comparisonOperatorType,
													   LeftHandSideDTO leftHandSide,
													   RightHandSideDTO rightHandSide) {
		RulePropositionDTO dto = new RulePropositionDTO();
		dto.setComparisonDataTypeKey(comparisonDataType);
		dto.setComparisonOperatorTypeKey(comparisonOperatorType);
		dto.setDescription("Rule proposition DTO");
		dto.setFailureMessage("Rule proposition failure");
		dto.setLeftHandSide(leftHandSide);
		dto.setName(name);
		dto.setRightHandSide(rightHandSide);
		return dto;
	}

	public RuleElementDTO createRuleElementDTO(String name, 
											   String operation, 
											   Integer ordinalPosition,
											   RulePropositionDTO ruleProposition) {
		RuleElementDTO dto = new RuleElementDTO();
		dto.setDescription("Rule element DTO");
		dto.setName(name);
		dto.setBusinessRuleElemnetTypeKey(operation);
		dto.setOrdinalPosition(ordinalPosition);
		dto.setBusinessRuleProposition(ruleProposition);
		return dto;
	}

	public BusinessRuleInfoDTO createBusinessRuleInfoDTO(String name, 
														 String businessRuleId,
														 String successMessage,
														 String failureMessage,
														 String businessRuleTypeKey,
														 List<RuleElementDTO> ruleElementList,
														 String anchorTypeKey,
														 String anchorValue) {
		BusinessRuleInfoDTO dto = new BusinessRuleInfoDTO();
		dto.setName(name);
		dto.setDesc("Business rule info DTO");
		dto.setSuccessMessage(successMessage);
		dto.setFailureMessage(failureMessage);
		dto.setId(businessRuleId);
		//dto.setbusinessRuleInfo = businessRuleInfo;
		dto.setType(businessRuleTypeKey);
		dto.setAnchorTypeKey(anchorTypeKey);
		dto.setAnchorValue(anchorValue);
		dto.setBusinessRuleElementList(ruleElementList);
		return dto;
	}
}
