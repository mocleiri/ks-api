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
package org.kuali.student.rules.internal.common.statement.yvf;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.rules.internal.common.statement.propositions.AverageProposition;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class YVFAverageProposition<E extends Number> extends AbstractYVFProposition<E> {

	public final static String AVERAGE_COLUMN_KEY = "key.proposition.column.average";

	public YVFAverageProposition(String id, String propositionName, 
			ComparisonOperator comparisonOperator, BigDecimal expectedValue, 
			YieldValueFunctionDTO yvf, Map<String, ?> factMap) {
		if (propositionName == null || propositionName.isEmpty()) {
			throw new PropositionException("Proposition name cannot be null");
		} else if (comparisonOperator == null) {
			throw new PropositionException("Comparison operator name cannot be null");
		} else if (expectedValue == null) {
			throw new PropositionException("Expected value cannot be null");
		} else if (yvf == null) {
			throw new PropositionException("Yield value function cannot be null");
		}

		List<FactStructureDTO> factStructureList = yvf.getFactStructureList();
		FactStructureDTO fact = factStructureList.get(0);

		if (fact == null) {
			throw new PropositionException("Fact structure cannot be null");
		}

		List<E> factList = null;

		if (fact.isStaticFact()) {
			String value = fact.getStaticValue();
			String dataType = fact.getStaticValueDataType();
			if (value == null || value.isEmpty() || dataType == null || dataType.isEmpty()) {
				throw new PropositionException("Static value and data type cannot be null or empty");
			}
			factList = getList(dataType, value);
		} else {
			if (factMap == null || factMap.isEmpty()) {
				throw new PropositionException("Fact map cannot be null or empty");
			}
	    	String factKey = FactUtil.createFactKey(fact);
			FactResultDTO factDTO = (FactResultDTO) factMap.get(factKey);

			String column = fact.getResultColumnKeyTranslations().get(AVERAGE_COLUMN_KEY);
			if (column == null || column.trim().isEmpty()) {
				throw new PropositionException("Average column not found for key '"+
						AVERAGE_COLUMN_KEY+"'. Fact structure id: " + fact.getFactStructureId());
			}

			factList = getList(factDTO, column);
			if (factList == null || factList.isEmpty()) {
				throw new PropositionException("Facts not found for column '"+
						column+"'. Fact structure id: " + fact.getFactStructureId());
			}
		}

		if(logger.isDebugEnabled()) {
			logger.debug("\n---------- YVFAverageProposition ----------"
					+ "\nFact static="+fact.isStaticFact()
					+ "\nFact key="+FactUtil.createFactKey(fact)
					+ "\nYield value function type="+yvf.getYieldValueFunctionType()
					+ "\nComparison operator="+comparisonOperator
					+ "\nExpected value="+expectedValue
					+ "\nFact list="+factList
					+ "\n--------------------------------------------------");
		}

        super.proposition = new AverageProposition<E>(id, propositionName, 
        		comparisonOperator, expectedValue, factList); 
	}
}
