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
package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

/**
 * A constraint that specifies that a fact set must be a subset of a given size of a given set of criteria.
 * 
 * @param <E>
 *            the type of elements being constrained
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 */
public class SubsetProposition<E> extends AbstractProposition<Integer> {
    // ~ Instance fields --------------------------------------------------------
	private Set<E> met;
	
    Set<E> criteriaSet;
    Set<E> factSet;
    Collection<?> resultValues;

    public final static String DEFAULT_SUCCESS_MESSAGE = "Subset constraint fulfilled";
    public final static String DEFAULT_FAILURE_MESSAGE = "#needed# of #unmetSet# is still required";

    public final static String NEEDED_REPORT_TEMPLATE_TOKEN = "needed";
    public final static String UNMET_REPORT_TEMPLATE_TOKEN = "unmetSet";

    // ~ Constructors -----------------------------------------------------------

    public SubsetProposition() {
        super();
    }

    public SubsetProposition(String id, String propositionName, 
    		Set<E> criteriaSet, Set<E> factSet, RulePropositionDTO ruleProposition) {
        super(id, propositionName, PropositionType.SUBSET, ComparisonOperator.EQUAL_TO, new Integer(criteriaSet.size()), ruleProposition);
        this.criteriaSet = criteriaSet;
        this.factSet = (factSet == null ? new HashSet<E>() : factSet);
    }

    // ~ Methods ----------------------------------------------------------------

    @Override
    public Boolean apply() {
        this.met = and();

        result = checkTruthValue(met.size(), super.expectedValue);

        this.resultValues = met;

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.rules.constraint.AbstractConstraint#cacheAdvice(java.lang.String, java.lang.Object[])
     */
    @Override
    public PropositionReport buildReport() {
        // TODO: Use the operator to compute exact message
        Integer count = met.size();
        Set<E> unMet = andNot();
        Integer needed = super.expectedValue - count;
        addMessageToken(NEEDED_REPORT_TEMPLATE_TOKEN, needed.toString());
        addMessageToken(UNMET_REPORT_TEMPLATE_TOKEN, unMet.toString());
        buildDefaultReport(DEFAULT_SUCCESS_MESSAGE, DEFAULT_FAILURE_MESSAGE);
		reportBuilt = Boolean.TRUE;
        return report;
    }

    /**
     * Returns the intersection of the fact set with the criteria set.
     * 
     * @return the intersection
     */
    public Set<E> and() {
        Set<E> rval = new HashSet<E>(factSet);
        rval.retainAll(criteriaSet);

        return rval;
    }

    /**
     * Returns the disjunction of the criteria set from the fact set.
     * 
     * @return
     */
    public Set<E> andNot() {
        HashSet<E> rval = new HashSet<E>(criteriaSet);
        rval.removeAll(factSet);

        return rval;
    }

    /**
     * @return the criteriaSet
     */
    public Set<E> getCriteriaSet() {
        return criteriaSet;
    }

    /**
     * @param criteriaSet
     *            the criteriaSet to set
     */
    public void setCriteriaSet(Set<E> criteriaSet) {
        this.criteriaSet = new HashSet<E>(criteriaSet);
    }

    /**
     * @return the factSet
     */
    public Set<E> getFactSet() {
        return factSet;
    }

    /**
     * @param factSet
     *            the factSet to set
     */
    public void setFactSet(Set<E> factSet) {
        this.factSet = new HashSet<E>(factSet);
    }

    public Collection<?> getResultValues() {
    	return this.resultValues;
    }
}
