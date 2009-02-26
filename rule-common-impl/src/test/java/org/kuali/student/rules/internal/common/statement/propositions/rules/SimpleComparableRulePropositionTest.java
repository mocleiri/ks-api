package org.kuali.student.rules.internal.common.statement.propositions.rules;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.statement.propositions.rules.SimpleComparableRuleProposition;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class SimpleComparableRulePropositionTest {

    public Map<String, Object> getFactMap(FactStructureDTO fs1, String dataType, String value, String column) {
    	String factKey = FactUtil.createFactKey(fs1);

    	FactResultTypeInfoDTO columnMetaData1 = CommonTestUtil.createColumnMetaData(dataType, column);
        FactResultDTO factResult = CommonTestUtil.createFactResult(new String[] {value}, column);
        factResult.setFactResultTypeInfo(columnMetaData1);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey, factResult);
        
        return factMap;
    }

	@Test
	public void testSimpleComparableProposition_BigDecimal_StaticFact_False() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.comparable.fact");
		fs1.setStaticFact(true);
		fs1.setStaticValueDataType(BigDecimal.class.getName());
		fs1.setStaticValue("80");

		yvf.setFactStructureList(Arrays.asList(fs1));
		BigDecimal expectedValue = new BigDecimal(90);
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, expectedValue.toString(), ComparisonOperator.EQUAL_TO.toString(), BigDecimal.class.getName());

		SimpleComparableRuleProposition<BigDecimal> proposition = new SimpleComparableRuleProposition<BigDecimal>(
				"1", "SimpleComparableRuleProposition", ruleProposition, null);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getFailureMessage());
		Assert.assertNull(report.getSuccessMessage());

		FactResultDTO factResult = report.getFactResult();
		Assert.assertEquals(1, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "80"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, "false"));
	}

	@Test
	public void testSimpleComparableProposition_BigDecimal_False() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.comparable.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SIMPLE_COMPARABLE_COLUMN_KEY, "resultColumn.grade");
		fs1.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs1));
		BigDecimal expectedValue = new BigDecimal(90);
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, expectedValue.toString(), ComparisonOperator.EQUAL_TO.toString(), BigDecimal.class.getName());

		Map<String, Object> factMap = getFactMap(fs1, BigDecimal.class.getName(), "80", "resultColumn.grade");
		
		SimpleComparableRuleProposition<BigDecimal> proposition = new SimpleComparableRuleProposition<BigDecimal>(
				"1", "SimpleComparableRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getFailureMessage());
		Assert.assertNull(report.getSuccessMessage());

		FactResultDTO factResult = report.getFactResult();
		Assert.assertEquals(1, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.grade", "80"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.grade", "false"));
	}

	@Test
	public void testSimpleComparableProposition_String() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.comparable.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SIMPLE_COMPARABLE_COLUMN_KEY, "resultColumn.grade");
		fs1.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs1));
		String expectedValue = "80";
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, expectedValue, ComparisonOperator.EQUAL_TO.toString(), String.class.getName());

		Map<String, Object> factMap = getFactMap(fs1, String.class.getName(), "80", "resultColumn.grade");
		
		SimpleComparableRuleProposition<String> proposition = new SimpleComparableRuleProposition<String>(
				"1", "SimpleComparableRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNull(report.getFailureMessage());
		Assert.assertNotNull(report.getSuccessMessage());

		FactResultDTO factResult = report.getFactResult();
		Assert.assertEquals(1, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.grade", "80"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.grade", "true"));
	}

	@Test
	public void testSimpleComparableProposition_Calendar() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.comparable.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SIMPLE_COMPARABLE_COLUMN_KEY, "resultColumn.date");
		fs1.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs1));

		Calendar cal = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
    	String calStr = BusinessRuleUtil.formatIsoDate(cal.getTime());

    	Map<String, Object> factMap = getFactMap(fs1, java.util.Calendar.class.getName(), calStr, "resultColumn.date");

		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, calStr, ComparisonOperator.EQUAL_TO.toString(), Calendar.class.getName());
		
		SimpleComparableRuleProposition<Calendar> proposition = new SimpleComparableRuleProposition<Calendar>(
				"1", "SimpleComparableRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNull(report.getFailureMessage());
		Assert.assertNotNull(report.getSuccessMessage());

		FactResultDTO factResult = report.getFactResult();
		Assert.assertEquals(1, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.date", calStr));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.date", "true"));
	}

	@Test
	public void testSimpleComparableProposition_Calendar_LessThan() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.comparable.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SIMPLE_COMPARABLE_COLUMN_KEY, "resultColumn.date");
		fs1.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs1));

		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2100, 1, 1, 1, 0);
    	String calStr1 = BusinessRuleUtil.formatIsoDate(cal1.getTime());
    	String calStr2 = BusinessRuleUtil.formatIsoDate(cal2.getTime());

    	Map<String, Object> factMap = getFactMap(fs1, java.util.Calendar.class.getName(), calStr1, "resultColumn.date");
		
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, calStr2, ComparisonOperator.LESS_THAN.toString(), Calendar.class.getName());
		
		SimpleComparableRuleProposition<Calendar> proposition = new SimpleComparableRuleProposition<Calendar>(
				"1", "SimpleComparableRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNull(report.getFailureMessage());
		Assert.assertNotNull(report.getSuccessMessage());

		FactResultDTO factResult = report.getFactResult();
		Assert.assertEquals(1, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.date", calStr1));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.date", "true"));
	}

	@Test
	public void testSimpleComparableProposition_Calendar_GreaterThan() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.comparable.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SIMPLE_COMPARABLE_COLUMN_KEY, "resultColumn.date");
		fs1.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs1));

		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2100, 1, 1, 1, 0);
    	String calStr1 = BusinessRuleUtil.formatIsoDate(cal1.getTime());
    	String calStr2 = BusinessRuleUtil.formatIsoDate(cal2.getTime());

    	Map<String, Object> factMap = getFactMap(fs1, java.util.Calendar.class.getName(), calStr2, "resultColumn.date");
		
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, calStr1, ComparisonOperator.GREATER_THAN.toString(), Calendar.class.getName());
		
		SimpleComparableRuleProposition<Calendar> proposition = new SimpleComparableRuleProposition<Calendar>(
				"1", "SimpleComparableRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNull(report.getFailureMessage());
		Assert.assertNotNull(report.getSuccessMessage());

		FactResultDTO factResult = report.getFactResult();
		Assert.assertEquals(1, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.date", calStr2));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(1, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.date", "true"));
	}

	@Test
	public void testSimpleComparableProposition_DefaultSuccessMessage() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.comparable.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SIMPLE_COMPARABLE_COLUMN_KEY, "resultColumn.grade");
		fs1.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs1));
		String expectedValue = "80";
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, expectedValue, ComparisonOperator.EQUAL_TO.toString(), String.class.getName());

		Map<String, Object> factMap = getFactMap(fs1, String.class.getName(), "80", "resultColumn.grade");
		
		SimpleComparableRuleProposition<String> proposition = new SimpleComparableRuleProposition<String>(
				"1", "SimpleComparableRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNull(report.getFailureMessage());
		Assert.assertNotNull(report.getSuccessMessage());
        Assert.assertEquals(MessageContextConstants.PROPOSITION_SIMPLE_COMPARABLE_SUCCESS_MESSAGE, report.getSuccessMessage());
	}

	@Test
	public void testSimpleComparableProposition_Calendar_DefaultFailureMessage() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.comparable.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SIMPLE_COMPARABLE_COLUMN_KEY, "resultColumn.date");
		fs1.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs1));

		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2100, 1, 1, 1, 0);
    	String calStr1 = BusinessRuleUtil.formatIsoDate(cal1.getTime());
    	String calStr2 = BusinessRuleUtil.formatIsoDate(cal2.getTime());

    	Map<String, Object> factMap = getFactMap(fs1, java.util.Calendar.class.getName(), calStr2, "resultColumn.date");
		
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, calStr1, ComparisonOperator.LESS_THAN.toString(), Calendar.class.getName());
		
		SimpleComparableRuleProposition<Calendar> proposition = new SimpleComparableRuleProposition<Calendar>(
				"1", "SimpleComparableRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getFailureMessage());
		Assert.assertNull(report.getSuccessMessage());
        Assert.assertEquals("2100-01-01T01:00:00.000-0800 not LESS_THAN 2000-01-01T01:00:00.000-0800", report.getFailureMessage());
	}

	@Test
	public void testSimpleComparableProposition_Date_DefaultFailureMessage() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.comparable.fact");

		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(MessageContextConstants.PROPOSITION_SIMPLE_COMPARABLE_COLUMN_KEY, "resultColumn.date");
		fs1.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs1));

		Calendar cal1 = CommonTestUtil.createDate(2000, 1, 1, 1, 0);
		Calendar cal2 = CommonTestUtil.createDate(2100, 1, 1, 1, 0);
    	String calStr1 = BusinessRuleUtil.formatIsoDate(cal1.getTime());
    	String calStr2 = BusinessRuleUtil.formatIsoDate(cal2.getTime());
    	
		Map<String, Object> factMap = getFactMap(fs1, Date.class.getName(), calStr2, "resultColumn.date");
		
		RulePropositionDTO ruleProposition = CommonTestUtil.createRuleProposition(
				yvf, calStr1, ComparisonOperator.LESS_THAN.toString(), Date.class.getName());
		
		SimpleComparableRuleProposition<Date> proposition = new SimpleComparableRuleProposition<Date>(
				"1", "SimpleComparableRuleProposition", ruleProposition, factMap);

		proposition.apply();
		PropositionReport report = proposition.buildReport();
		
		Assert.assertFalse(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getFailureMessage());
		Assert.assertNull(report.getSuccessMessage());
        Assert.assertEquals("2100-01-01T01:00:00.000-0800 not LESS_THAN 2000-01-01T01:00:00.000-0800", report.getFailureMessage());
	}

}
