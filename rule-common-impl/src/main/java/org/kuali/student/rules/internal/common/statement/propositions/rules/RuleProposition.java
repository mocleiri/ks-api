package org.kuali.student.rules.internal.common.statement.propositions.rules;

import java.util.Collection;

import org.kuali.student.rules.internal.common.statement.exceptions.IllegalPropositionStateException;
import org.kuali.student.rules.internal.common.statement.propositions.PropositionType;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;

public interface RuleProposition {
	/**
	 * Evaluates the proposition to a truth value
	 * @return <code>true</code> if the constraint is met.
	 */
	public Boolean apply();

	/**
	 * Returns the cached value of apply method's result
	 * @return <code>true</code> if the constraint is met.
	 * @exception IllegalPropositionStateException
	 */
	public Boolean getResult();	
	
	/**
	 * Returns the proposition name
	 * @return Proposition name
	 */
	public String getPropositionName();

	/**
	 * Return the proposition id.
	 * @return Proposition id
	 */
	public String getId();
	
	/**
	 * Returns the proposition type.
	 * @return Proposition type
	 */
	public PropositionType getType();

	/**
	 * Returns the values of the proposition result.
	 * 
	 * @return Proposition result values
	 */
	public Collection<?> getResultValues();	

	/**
     * Builds a proposition report.
     * 
     * @return Proposition report
     */
	public PropositionReport buildReport();

	/**
	 * An explanation of the results of the constraint.
	 * @return the advice
	 */
	public PropositionReport getReport();
}
