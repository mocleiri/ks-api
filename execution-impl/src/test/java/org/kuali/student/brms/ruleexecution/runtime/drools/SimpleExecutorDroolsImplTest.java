package org.kuali.student.brms.ruleexecution.runtime.drools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.brms.repository.dto.RuleSetDTO;
import org.kuali.student.brms.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.brms.ruleexecution.runtime.SimpleExecutor;
import org.kuali.student.brms.ruleexecution.runtime.drools.DroolsRuleBase;
import org.kuali.student.brms.ruleexecution.runtime.drools.SimpleExecutorDroolsImpl;
import org.kuali.student.brms.ruleexecution.runtime.drools.util.DroolsTestUtil;
import org.kuali.student.brms.ruleexecution.runtime.drools.util.DroolsUtil;

public class SimpleExecutorDroolsImplTest {

	private final DroolsUtil droolsUtil = new DroolsUtil();
    private final static DroolsRuleBase ruleBase = new DroolsRuleBase();
	private static SimpleExecutor executor = new SimpleExecutorDroolsImpl(true);;
	
    @BeforeClass
    public static void setUpOnce() throws Exception {
		((SimpleExecutorDroolsImpl)executor).setEnableStatisticsLogging(true);
    	((SimpleExecutorDroolsImpl)executor).setRuleBaseCache(ruleBase);
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
    	executor = null;
    }

    @Before
    public void setUp() throws Exception {
    	executor.clearRuleSetCache();
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testExecuteSimpleRuleSet_Drools() throws Exception {
        RuleSetDTO ruleSet = DroolsTestUtil.createRuleSet();
        byte[] bytes = DroolsTestUtil.createPackage(ruleSet);
        ruleSet.setCompiledRuleSet(bytes);

        // Add facts
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2008);
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.MINUTE, 0);
        List<Object> facts = new ArrayList<Object>();
        facts.add(cal);

        // Execute ruleset and fact
    	String ruleBaseType = SimpleExecutor.DEFAULT_RULE_CACHE_KEY;
    	executor.addRuleSet(ruleBaseType, ruleSet);
        ExecutionResult result = executor.execute(ruleSet, facts);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getResults());

        // Iterate through returned rule engine objects
        String time = null;
        for(Object obj : result.getResults()) {
            if (obj instanceof String) {
                time = (String) obj;
                break;
            }
        }

        Assert.assertNotNull(time);
        Assert.assertTrue(time.startsWith("Minute is even: 0"));
	    System.out.println(droolsUtil.getStatisticsSummary(((SimpleExecutorDroolsImpl)executor).getStatistics()));
    }

}
