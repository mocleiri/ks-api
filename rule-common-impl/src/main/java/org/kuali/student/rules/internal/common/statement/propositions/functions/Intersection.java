package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.statement.exceptions.FunctionException;
import org.kuali.student.rules.internal.common.statement.exceptions.IllegalFunctionStateException;

public class Intersection extends AbstractFunction<FactResultDTO> { 

	public enum Operation {INTERSECTION, DIFFERENCE, SUBSET_DIFFERENCE}
	
    private FactResultDTO criteriaDTO;
    private FactResultDTO factDTO;
    private String criteriaColumnKey;
    private String factColumnKey;
    
    private Collection<String> criteriaSet;
    private Collection<String> factSet;
    
    public Intersection() {
    }
    
    public Intersection(FactResultDTO criteriaDTO, String criteriaColumnKey, FactResultDTO factDTO, String factColumnKey) {
    	this.criteriaDTO = criteriaDTO;
    	this.criteriaColumnKey = criteriaColumnKey;
    	this.factDTO = factDTO;
    	this.factColumnKey = factColumnKey;
    }
    
    public void setCriteria(FactResultDTO criteriaDTO, String criteriaColumnKey) {
    	this.criteriaDTO = criteriaDTO;
    	this.criteriaColumnKey = criteriaColumnKey;
    }

    public void setFact(FactResultDTO factDTO, String factColumnKey) {
    	this.factDTO = factDTO;
    	this.factColumnKey = factColumnKey;
    }

    public FactResultDTO compute() {
    	if(this.factDTO == null) {
    		throw new IllegalFunctionStateException("Fact is null: " + this.factDTO);
    	} else if(this.factColumnKey == null) {
    		throw new IllegalFunctionStateException("Fact column key is null: " + this.factColumnKey);
    	} else if(this.criteriaDTO == null) {
    		throw new IllegalFunctionStateException("Criteria fact is null: " + this.factDTO);
    	} else if(this.criteriaColumnKey == null) {
    		throw new IllegalFunctionStateException("Criteria fact column key is null: " + this.factColumnKey);
    	} else if(!containsFactColumnKey(this.factDTO, this.factColumnKey)) {
    		throw new IllegalFunctionStateException("Fact column key not found: " + this.factColumnKey);
    	} else  if(!containsFactColumnKey(this.criteriaDTO, this.criteriaColumnKey)) {
    		throw new IllegalFunctionStateException("Criteria fact column key not found: " + this.factColumnKey);
    	}

    	// Criteria
		this.criteriaSet = getCollectionString(this.criteriaDTO, this.criteriaColumnKey);
		// Fact
		this.factSet = getCollectionString(this.factDTO, this.factColumnKey);
		
		Collection<String> result = null;
		Operation operationType = Operation.valueOf(super.operation);
		switch(operationType) {
			case INTERSECTION: {
				result = intersection();
				break;
			}
			case DIFFERENCE: { 
				result = difference();
				break;
			}
			case SUBSET_DIFFERENCE: { 
				result = subSetDifference();
				break;
			}
			default: 
				throw new FunctionException("Operation not found");
		}
		
		FactResultDTO factResult = buildFactResult(result, factColumnKey);
		
		return factResult;
	}

    /**
     * Returns the intersection of the fact set with the criteria set.
     * 
     * @return the intersection
     */
    private Collection<String> intersection() {
        Set<String> set = new HashSet<String>(this.factSet);
        set.retainAll(this.criteriaSet);
        return set;
    }

    /**
     * Returns the disjunction of the criteria set from the fact set.
     * 
     * @return
     */
    private Collection<String> difference() {
        HashSet<String> set = new HashSet<String>(this.criteriaSet);
        set.removeAll(this.factSet);
        return set;
    }

    private Collection<String> subSetDifference() {
        HashSet<String> set = new HashSet<String>(this.factSet);
        set.removeAll(this.criteriaSet);
        return set;
    }
    
    private FactResultDTO buildFactResult(Collection<String> intersections, String column) {
    	List<Map<String,String>> resultList = new ArrayList<Map<String,String>>(); 
    	Set<Map<String,String>> set = new HashSet<Map<String,String>>(this.factDTO.getResultList());
    	for(Map<String,String> row : set) {
    		String value = row.get(column);
    		if(intersections.contains(value)) {
    			resultList.add(row);
    		}
    	}
    	
    	FactResultDTO factResult = new FactResultDTO();
    	factResult.setFactResultTypeInfo(this.factDTO.getFactResultTypeInfo());
    	factResult.setResultList(resultList);
    	return factResult;
    }
}
