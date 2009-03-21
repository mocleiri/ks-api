package org.kuali.student.rules.internal.common.statement.propositions.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.kuali.student.rules.factfinder.dto.FactResultColumnInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.rules.internal.common.statement.propositions.AbstractProposition;
import org.kuali.student.rules.internal.common.statement.propositions.Proposition;
import org.kuali.student.rules.internal.common.statement.propositions.PropositionType;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.internal.common.utils.VelocityTemplateEngine;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRuleProposition<T> implements RuleProposition {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(AbstractRuleProposition.class);

	protected Proposition proposition;

	protected FactResultDTO criteriaDTO = null;
	protected FactResultDTO factDTO = null;
	protected String factColumn = MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN;

    protected String id;
    protected String propositionName;
    protected PropositionType propositionType;
    protected PropositionReport report;
    protected RulePropositionDTO ruleProposition;
    protected String expectedValue;
    protected String comparisonOperator;
    protected String comparisonOperatorDataType;
    protected FactResultDTO factResultDTO;

    protected Boolean reportBuilt = Boolean.FALSE;
    
    private final VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
    
    private final Map<String,Object> contextMap = new HashMap<String, Object>();

    public AbstractRuleProposition() {}
    
    public AbstractRuleProposition(String id, String propositionName, PropositionType type, RulePropositionDTO ruleProposition) {
		if (id == null || id.isEmpty()) {
			throw new PropositionException("Proposition id cannot be null");
		} else if (propositionName == null || propositionName.isEmpty()) {
			throw new PropositionException("Proposition name cannot be null");
		} else if (ruleProposition == null) {
			throw new PropositionException("Rule proposition cannot be null");
//		} else if (ruleProposition.getComparisonOperatorTypeKey() == null) {
//			throw new PropositionException("Rule proposition comparison operator cannot be null");
//		} else if (ruleProposition.getRightHandSide().getExpectedValue() == null) {
//			throw new PropositionException("Rule proposition expected value cannot be null");
		}

    	this.id = id;
        this.propositionName = propositionName;
        this.ruleProposition = ruleProposition;
        this.report = new PropositionReport(propositionName, type);
        this.expectedValue = ruleProposition.getRightHandSide().getExpectedValue();
        this.comparisonOperator = ruleProposition.getComparisonOperatorTypeKey();
        this.comparisonOperatorDataType = ruleProposition.getComparisonOperatorTypeKey();
    }

    public FactResultColumnInfoDTO getFactResultColumnInfo(FactResultDTO factResult, String columnKey) {
		if (factResult == null) {
			throw new PropositionException("FactResultDTO cannot be null");
		}
		Map<String, FactResultColumnInfoDTO> columnMetaData = factResult.getFactResultTypeInfo().getResultColumnsMap();
		return columnMetaData.get(columnKey);
    }

    public FactResultDTO createFactResult(FactResultColumnInfoDTO columnInfo, Collection<?> factList) {
    	FactResultDTO factResult = new FactResultDTO();
    	FactResultTypeInfoDTO factResultTypeInfo = new FactResultTypeInfoDTO();
    	Map<String, FactResultColumnInfoDTO> columnMap = new HashMap<String, FactResultColumnInfoDTO>();

    	FactResultColumnInfoDTO newColumnInfo = new FactResultColumnInfoDTO();
    	newColumnInfo.setKey(columnInfo.getKey());
    	newColumnInfo.setDescription(columnInfo.getDescription());
    	newColumnInfo.setDataType(columnInfo.getDataType());
    	newColumnInfo.setName(columnInfo.getName());
    	columnMap.put(newColumnInfo.getKey(), newColumnInfo);
    	factResultTypeInfo.setResultColumnsMap(columnMap);
    	factResult.setFactResultTypeInfo(factResultTypeInfo);

    	List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		for(Object value : factList) {
    		Map<String,String> row = new HashMap<String, String>();
    		row.put(columnInfo.getKey(), value.toString());
    		resultList.add(row);
    	}
    	
    	factResult.setResultList(resultList);
    	return factResult;
    }

    /**
     * Creates a fact result. This method is usually called from static facts.
     * 
     * @param columnDataType Column data type
     * @param factList Comma separated list of values
     * @return Fact result
     */
    public FactResultDTO createStaticFactResult(String columnDataType, String factList) {
    	FactResultDTO factResult = new FactResultDTO();
    	
    	FactResultTypeInfoDTO factResultTypeInfo = new FactResultTypeInfoDTO();
    	Map<String, FactResultColumnInfoDTO> columnMap = new HashMap<String, FactResultColumnInfoDTO>();
    	FactResultColumnInfoDTO columnInfo = new FactResultColumnInfoDTO();
    	columnInfo.setKey(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN);
    	columnInfo.setDescription("Static Fact Column");
    	columnInfo.setDataType(columnDataType);
    	columnInfo.setName(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN);
    	columnMap.put(columnInfo.getKey(), columnInfo);
    	factResultTypeInfo.setResultColumnsMap(columnMap);
    	factResult.setFactResultTypeInfo(factResultTypeInfo);

    	List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		for(String value : factList.split("\\s*,\\s*")) {
    		Map<String,String> row = new HashMap<String, String>();
    		row.put(MessageContextConstants.PROPOSITION_STATIC_FACT_COLUMN, value);
    		resultList.add(row);
    	}
    	
    	factResult.setResultList(resultList);
    	return factResult;
    }

	@SuppressWarnings("unchecked")
	public Set<T> getSet(String dataType, String stringList) {
		Set<T> set = new HashSet<T>();
		for(String value : stringList.split("\\s*,\\s*")) {
			try {
				T obj = (T) BusinessRuleUtil.convertToDataType(dataType, value);
				set.add(obj);
			} catch(NumberFormatException e) {
				logger.error("DataType conversion failed. dataType=" + dataType + ", value=", e);
				throw new NumberFormatException(e.getMessage() + ": dataType=" + ", value=");
			}
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	public Set<T> getSet(FactResultDTO factResult, String column) {
		if (factResult == null) {
			throw new PropositionException("FactResultDTO cannot be null");
		}
		Map<String, FactResultColumnInfoDTO> columnMetaData = factResult.getFactResultTypeInfo().getResultColumnsMap();
		Set<T> set = new HashSet<T>();
		for( Map<String,String> map : factResult.getResultList()) {
			for(Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().equals(column)) {
					String value = entry.getValue();
					FactResultColumnInfoDTO info = columnMetaData.get(entry.getKey());
					String dataType = info.getDataType();
					try {
						T obj = (T) BusinessRuleUtil.convertToDataType(dataType, value);
						set.add(obj);
					} catch(NumberFormatException e) {
						logger.error("DataType conversion failed. dataType=" + dataType + ", value=", e);
						throw new NumberFormatException(e.getMessage() + ": dataType=" + ", value=");
					}
				}
			}
		}
		return set;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getList(String dataType, String stringList) {
		List<T> list = new ArrayList<T>();
		for(String value : stringList.split("\\s*,\\s*")) {
			try {
				T obj = (T) BusinessRuleUtil.convertToDataType(dataType, value);
				list.add(obj);
			} catch(NumberFormatException e) {
				logger.error("DataType conversion failed. dataType=" + dataType + ", value=", e);
				throw new NumberFormatException(e.getMessage() + ": dataType=" + ", value=");
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> getList(FactResultDTO factResult, String column) {
		Map<String, FactResultColumnInfoDTO> columnMetaData = factResult.getFactResultTypeInfo().getResultColumnsMap();
		List<T> list = new ArrayList<T>();
		for(Map<String,String> map : factResult.getResultList()) {
			for(Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().equals(column)) {
					String value = (String) entry.getValue();
					FactResultColumnInfoDTO info = columnMetaData.get(entry.getKey());
					String dataType = info.getDataType();
					try {
						T obj = (T) BusinessRuleUtil.convertToDataType(dataType, value);
						list.add(obj);
					} catch(NumberFormatException e) {
						logger.error("DataType conversion failed. dataType=" + dataType + ", value=", e);
						throw new NumberFormatException(e.getMessage() + ": dataType=" + ", value=");
					}
				}
			}
		}
		return list;
	}

	/**
	 * Gets the proposition.
	 * 
	 * @return Proposition
	 */
	public Proposition getProposition() {
		return this.proposition;
	}

	@Override
	public Boolean apply() {
		Boolean b = proposition.apply();
		if(logger.isDebugEnabled()) {
			logger.debug("apply()"
					+ "\nProposition id="+this.getId()
					+ "\nProposition name="+this.getPropositionName()
					+ "\nProposition result="+this.getResult());
		}
		FactResultColumnInfoDTO columnInfo = getFactResultColumnInfo(factDTO, factColumn);
		this.factResultDTO = createFactResult(columnInfo, this.getResultValues());
		this.report.setPropositionResult(this.factResultDTO);
		this.report.setCriteriaResult(criteriaDTO);
		this.report.setFactResult(factDTO);
		((AbstractProposition<?>) this.proposition).buildMessageContextMap(); 

		Map<String, Object> propContextMap = this.proposition.getMessageContextMap();
		addMessageContext(propContextMap);
		buildMessageContextMap();
		
		return b;
	}
	
	/**
	 * Builds a message from the <code>messageTemplate</code>.
	 * 
	 * @param messageTemplate Velocity template
	 * @return Message
	 */
    protected String buildMessage(String messageTemplate) {
    	return templateEngine.evaluate(this.contextMap, messageTemplate);
    }

    /**
     * Builds the message context map.
     */
    private void buildMessageContextMap() {
        if (this.comparisonOperator != null) {
	        addMessageContext(MessageContextConstants.PROPOSITION_MESSAGE_CTX_KEY_OPERATOR, this.comparisonOperator.toString());
        }
        if (this.comparisonOperatorDataType != null) {
	        addMessageContext(MessageContextConstants.PROPOSITION_MESSAGE_CTX_KEY_OPERATOR_DATATYPE, this.comparisonOperatorDataType);
        }
        if (this.expectedValue != null) {
	        addMessageContext(MessageContextConstants.PROPOSITION_MESSAGE_CTX_KEY_EXPECTEDVALUE, this.expectedValue);
        }
    	addMessageContext(MessageContextConstants.PROPOSITION_MESSAGE_CTX_KEY_FACT, this.factDTO);
        addMessageContext(MessageContextConstants.PROPOSITION_MESSAGE_CTX_KEY_RESULT, this.factResultDTO);
        addMessageContext("resultValues", this.proposition.getResultValues());
    }

    /**
     * Adds a key and value to the message context map.
     * 
     * @param key Key
     * @param value Value for key
     */
    private void addMessageContext(String key, Object value) {
    	this.contextMap.put(key, value);
    }

    /**
     * Adds a message context map to the current message context map.
     * 
     * @param contextMap Message context map
     */
    private void addMessageContext(Map<String, Object> contextMap) {
    	this.contextMap.putAll(contextMap);
    }

    /**
     * Generates a proposition report.
     * 
     * Success/failure message is optionally set by the rule proposition.
     * If success/failure message is not set then a generic success/failure 
     * message is generated.
     */
    public abstract PropositionReport buildReport();

    /**
     * Builds a default proposition report.
     * 
     * @param successMessage Proposition success message
     * @param failureMessage Proposition failure message
     * @return Proposition report
     */
    protected PropositionReport buildDefaultReport(String successMessage, String failureMessage) {
    	// Build success message
        if (this.proposition.getResult()) {
    		if(this.ruleProposition.getSuccessMessage() == null || this.ruleProposition.getSuccessMessage().trim().isEmpty()) {
    			report.setMessage(successMessage);
    		} else {
	    		String msg = buildMessage(this.ruleProposition.getSuccessMessage());
	    		report.setMessage(msg);
    		}
            return report;
        }
        // Build failure message
		if(this.ruleProposition.getFailureMessage() == null || this.ruleProposition.getFailureMessage().trim().isEmpty()) {
	        String msg = buildMessage(failureMessage);
	        report.setMessage(msg);
		} else {
			String msg = buildMessage(this.ruleProposition.getFailureMessage());
	        report.setMessage(msg);
		}
		this.reportBuilt = Boolean.TRUE;
        return report;
    }

    /**
     * Gets the success/failure message id.
     * 
     * @return Message id
     */
	public String getMessageId() {
		return this.propositionName;
	}

	/**
	 * Returns whether the proposition is a success or failure.
	 * 
	 * @return True if successful; otherwise false
	 */
	public Boolean isSuccesful() {
		return this.getResult();
	}

	/**
	 * Returns either the success or failure message.
	 * 
	 *  @return Success or failure message
	 */
	public String getMessage() {
		if(!this.reportBuilt) {
			buildReport();
		}
    	return this.report.getMessage();
	}

	/**
	 * Gets the proposition's unique id.
	 * 
	 * @return Proposition's unique id
	 */
	@Override
	public String getId() {
		return this.proposition.getId();
	}

	/**
	 * Gets the proposition name.
	 *
	 * @return Proposition name
	 */
	@Override
	public String getPropositionName() {
		return this.proposition.getPropositionName();
	}

	/**
	 * Gets a proposition report. 
	 * An explanation of the results of the constraint.
	 * 
	 * @return Proposition report
	 */
	@Override
	public PropositionReport getReport() {
    	if(!this.reportBuilt) {
    		buildReport();
    	}
    	return this.report;
	}

	/**
	 * Gets the proposition result.
	 * 
	 * @return Proposition result
	 */
	@Override
	public Boolean getResult() {
		return this.proposition.getResult();
	}

	/**
	 * Gets the proposition type.
	 * 
	 * @return Proposition type
	 */
	@Override
	public PropositionType getType() {
		return this.proposition.getType();
	}

	/**
	 * Gets the proposition result values.
	 * 
	 * @return Proposition result values
	 */
	@Override
	public Collection<?> getResultValues() {
		return this.proposition.getResultValues();
	}

    public String toString() {
    	return "YVFProposition[id=" + this.proposition.getId() 
    		+ ", propositionName=" + this.proposition.getPropositionName() 
    		+ ", type=" + this.proposition.getType() 
    		+ ", result="+this.proposition.getResult() + "]";
    }
}
