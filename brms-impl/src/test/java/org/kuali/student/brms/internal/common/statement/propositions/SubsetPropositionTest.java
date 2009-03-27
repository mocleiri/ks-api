package org.kuali.student.brms.internal.common.statement.propositions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.internal.common.statement.propositions.SubsetProposition;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionDTO;

public class SubsetPropositionTest {
    private Set<String> set1 = new HashSet<String>(Arrays.asList("CHEM101".split(",")));
    private Set<String> set2 = new HashSet<String>(Arrays.asList("MATH101,MATH103,CHEM101".split(",")));
    private Set<String> set3 = new HashSet<String>(Arrays.asList("BIOL101".split(",")));

	private RulePropositionDTO ruleProposition = new RulePropositionDTO();

	@Test
    public void testSubsetTrue() throws Exception {
        SubsetProposition<String> prop = new SubsetProposition<String>("A-1", "A", set1, set2, ruleProposition);

        Boolean result = prop.apply();

        Assert.assertTrue(result);
        Assert.assertTrue(prop.getResultValues().contains("CHEM101"));
    }

    @Test
    public void testSubsetFalse() throws Exception {
        SubsetProposition<String> prop = new SubsetProposition<String>("A-1", "A", set3, set2, ruleProposition);

        Boolean result = prop.apply();

        Assert.assertFalse(result);
        Assert.assertEquals(0, prop.getResultValues().size());
    }
}
