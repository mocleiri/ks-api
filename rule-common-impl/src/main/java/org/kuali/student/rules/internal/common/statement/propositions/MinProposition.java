package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.ArrayList;

import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.statement.propositions.functions.Min;

public class MinProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private Min minFunction;
    private T min;
//	private Collection<T> fact;

    public MinProposition() {
    }
    
    public MinProposition(String id, String propositionName, 
    		ComparisonOperator operator, T expectedValue, 
    		FactResultDTO factDTO, String factColumn) {
        super(id, propositionName, PropositionType.MIN, operator, expectedValue, 
        		null, null, factDTO, factColumn);
//        this.fact = fact;
        this.minFunction = new Min(factDTO, factColumn);
	}

    @Override
    public Boolean apply() {
    	this.min = (T) this.minFunction.compute();

        result = checkTruthValue(this.min, super.expectedValue);

        resultValues = new ArrayList<T>();
        resultValues.add(this.min);

        return result;
    }

    @Override
    public void buildMessageContextMap() {
        String minStr = getTypeAsString(this.min);
        addMessageContext(MessageContextConstants.PROPOSITION_MIN_MESSAGE_CTX_KEY_MIN, minStr);
    }
}
