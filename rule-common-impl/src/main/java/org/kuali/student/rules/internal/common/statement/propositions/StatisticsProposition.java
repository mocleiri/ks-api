package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.Collection;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

public class StatisticsProposition<T extends Number> extends AbstractProposition<Double> {
    private Collection<T> fact;
    private StatFunction function;
    private Double computedValue;
    
    public enum StatFunction { MAX, MEAN, MIN, STANDARD_DEVIATION, SUM, SUM_OF_SQUARES, VARIANCE };
    
    public StatisticsProposition(String id, String propositionName, 
    		ComparisonOperator operator, StatFunction function, Double expectedValue, Collection<T> fact,
    		RulePropositionDTO ruleProposition) {
        super(id, propositionName, null, operator, expectedValue, ruleProposition);
        this.fact = fact;
        this.function = function;
    }

	@Override
	public Boolean apply() {
		this.computedValue = calculate();
		
		result = checkTruthValue(computedValue, super.expectedValue);

        return result;
	}

	@Override
    public PropositionReport buildReport() {
        if (result) {
            report.setSuccessMessage("Statistics " + function + " constraint fulfilled");
            return report;
        }

        // TODO: Use the operator to compute exact message
        String advice = String.format("Statistics %1$s not met, calculate value is %2$s expected:  %3$s", function, computedValue, super.expectedValue);
        report.setFailureMessage(advice);
        return report;
	}
	
	public Double getComputedValue() {
		return this.computedValue;
	}

	private Double calculate() {
		// This is not thread safe. If concurrency is required then use 
		// SynchronizedDescriptiveStatistics
		DescriptiveStatistics stat = new DescriptiveStatistics();
		for(T v : this.fact) {
			double d = Double.valueOf(v.toString());
			stat.addValue(d);
		}
		double value = 0.0;
		
		switch(this.function) {
			case MAX: 
				value = stat.getMax();
				break;
			case MEAN: 
				value = stat.getMean();
				break;
			case MIN: 
				value = stat.getMin();
				break;
			case STANDARD_DEVIATION: 
				value = stat.getStandardDeviation();
				break;
			case SUM: 
				value = stat.getSum();
				break;
			case SUM_OF_SQUARES: 
				value = stat.getSumsq();
				break;
			case VARIANCE: 
				value = stat.getVariance();
				break;
		}
		
		return Double.valueOf(value);
	}

}
