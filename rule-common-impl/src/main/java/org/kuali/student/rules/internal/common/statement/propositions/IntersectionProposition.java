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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.statement.propositions.functions.Function;
import org.kuali.student.rules.internal.common.statement.propositions.functions.Intersection;

/**
 * A constraint that specifies that a fact set must be a subset of a given size of a given set of criteria.
 * 
 * @param <E>
 *            the type of elements being constrained
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 */
public class IntersectionProposition<E> extends AbstractProposition<Integer> {

    // ~ Instance fields --------------------------------------------------------
	private Set<E> met;
    private Function intersection;
    private Collection<E> resultValues;
    
    // ~ Constructors -----------------------------------------------------------

    public IntersectionProposition() {
        super();
    }

    public IntersectionProposition(String id, String propositionName, 
    		ComparisonOperator operator, Integer expectedValue,
            Set<E> criteriaSet, Set<E> factSet) {
        super(id, propositionName, PropositionType.INTERSECTION, operator, expectedValue);
        factSet = (factSet == null ? new HashSet<E>() : factSet);

		List<Collection<E>> list = new ArrayList<Collection<E>>();
		list.add(criteriaSet);
		list.add(factSet);
    	intersection = new Intersection<E>(list);
    }

    // ~ Methods ----------------------------------------------------------------

    @Override
    public Boolean apply() {
    	intersection.setOperation(Intersection.Operation.INTERSECTION.toString());
    	intersection.compute();
    	this.met = new HashSet<E>((Collection<E>) intersection.getOutput());
    	
        Integer count = Integer.valueOf(met.size());

        result = checkTruthValue(count, super.expectedValue);

        this.resultValues = met;
        
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.rules.constraint.AbstractConstraint#cacheAdvice(java.lang.String, java.lang.Object[])
     */
    @Override
    public void buildMessageContextMap() {
        Integer count = met.size();
        Integer expectedValue = (Integer) super.expectedValue;
        addMessageContext(MessageContextConstants.PROPOSITION_INTERSECT_MESSAGE_CTX_KEY_MET, met);

    	intersection.setOperation(Intersection.Operation.DIFFERENCE.toString());
    	intersection.compute();
    	Set<E> unMet = new HashSet<E>((Collection<E>) intersection.getOutput());

    	Integer needed = expectedValue - count;
        addMessageContext(MessageContextConstants.PROPOSITION_INTERSECT_MESSAGE_CTX_KEY_DIFF, needed);
        addMessageContext(MessageContextConstants.PROPOSITION_INTERSECT_MESSAGE_CTX_KEY_UNMET, unMet);
    }

    public Collection<?> getResultValues() {
    	return this.resultValues;
    }
}
