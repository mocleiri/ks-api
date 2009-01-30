package org.kuali.student.rules.internal.common.statement.yvf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.internal.common.utils.CommonTestUtil;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class YVFIntersectionPropositionTest {

    public Map<String, Object> getFactMap(FactStructureDTO fs1, FactStructureDTO fs2, String column) {
    	String criteriaKeyIntersection = FactUtil.createCriteriaKey(fs1);
    	String factKeyIntersection = FactUtil.createFactKey(fs2);

    	FactResultTypeInfoDTO columnMetaData1 = CommonTestUtil.createColumnMetaData(String.class.getName(), column);

    	FactResultDTO factResultCriteria = CommonTestUtil.createFactResult(new String[] {"CPR101","CHEM101"}, column);
    	factResultCriteria.setFactResultTypeInfo(columnMetaData1);

        FactResultDTO factResult = CommonTestUtil.createFactResult(new String[] {"CPR101","MATH101","CHEM101"}, column);
        factResult.setFactResultTypeInfo(columnMetaData1);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKeyIntersection, factResultCriteria);
        factMap.put(factKeyIntersection, factResult);
        
        return factMap;
    }

	@Test
	public void testIntersectionProposition_StaticFact() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();

		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.intersection.criteria");
		fs1.setStaticFact(true);
		fs1.setStaticValueDataType(String.class.getName());
		fs1.setStaticValue("CPR101,CHEM101");

		FactStructureDTO fs2 = CommonTestUtil.createFactStructure("fact.id.2", "course.intersection.fact");
		fs2.setStaticFact(true);
		fs2.setStaticValueDataType(String.class.getName());
		fs2.setStaticValue("CPR101,MATH101,CHEM101");

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));

		YVFIntersectionProposition<String> proposition = new YVFIntersectionProposition<String>(
				"1", "YVFIntersectionProposition", 
				ComparisonOperator.EQUAL_TO, 2, yvf, null);

		PropositionReport report = proposition.getReport();
		
		Assert.assertTrue(proposition.apply());
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNull(report.getFailureMessage());
		Assert.assertNotNull(report.getSuccessMessage());

		FactResultDTO criteriaResult = report.getCriteriaResult();
		Assert.assertEquals(2, criteriaResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(criteriaResult.getResultList(), YVFIntersectionProposition.STATIC_FACT_COLUMN, "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(criteriaResult.getResultList(), YVFIntersectionProposition.STATIC_FACT_COLUMN, "CHEM101"));

		FactResultDTO factResult = report.getFactResult();
		Assert.assertEquals(3, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), YVFIntersectionProposition.STATIC_FACT_COLUMN, "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), YVFIntersectionProposition.STATIC_FACT_COLUMN, "MATH101"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), YVFIntersectionProposition.STATIC_FACT_COLUMN, "CHEM101"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(2, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), YVFIntersectionProposition.STATIC_FACT_COLUMN, "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), YVFIntersectionProposition.STATIC_FACT_COLUMN, "CHEM101"));
	}

	@Test
	public void testIntersectionProposition() throws Exception {
		YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();

		FactStructureDTO fs1 = CommonTestUtil.createFactStructure("fact.id.1", "course.intersection.criteria");
		Map<String,String> resultColumnKeyMap = new HashMap<String, String>();
		resultColumnKeyMap.put(YVFIntersectionProposition.INTERSECTION_COLUMN_KEY, "resultColumn.cluId");
		fs1.setResultColumnKeyTranslations(resultColumnKeyMap);

		FactStructureDTO fs2 = CommonTestUtil.createFactStructure("fact.id.2", "course.intersection.fact");
		fs2.setResultColumnKeyTranslations(resultColumnKeyMap);

		yvf.setFactStructureList(Arrays.asList(fs1, fs2));

		Map<String, Object> factMap = getFactMap(fs1, fs2, "resultColumn.cluId");
		
		YVFIntersectionProposition<String> proposition = new YVFIntersectionProposition<String>(
				"1", "YVFIntersectionProposition", 
				ComparisonOperator.EQUAL_TO, 2, yvf, factMap);

		PropositionReport report = proposition.getReport();
		
		Assert.assertTrue(proposition.apply());
		Assert.assertTrue(proposition.getResult());
		Assert.assertNotNull(report);
		Assert.assertNull(report.getFailureMessage());
		Assert.assertNotNull(report.getSuccessMessage());

		FactResultDTO criteriaResult = report.getCriteriaResult();
		Assert.assertEquals(2, criteriaResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(criteriaResult.getResultList(), "resultColumn.cluId", "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(criteriaResult.getResultList(), "resultColumn.cluId", "CHEM101"));

		FactResultDTO factResult = report.getFactResult();
		Assert.assertEquals(3, factResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.cluId", "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.cluId", "MATH101"));
		Assert.assertTrue(CommonTestUtil.containsResult(factResult.getResultList(), "resultColumn.cluId", "CHEM101"));

		FactResultDTO propositionResult = report.getPropositionResult();
        Assert.assertEquals(2, propositionResult.getResultList().size());
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.cluId", "CPR101"));
		Assert.assertTrue(CommonTestUtil.containsResult(propositionResult.getResultList(), "resultColumn.cluId", "CHEM101"));
	}
}
