/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.nlt.naturallanguage.translators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.kuali.student.brms.internal.common.runtime.BooleanMessage;
import org.kuali.student.brms.internal.common.runtime.MessageContainer;
import org.kuali.student.brms.internal.common.runtime.ast.BooleanMessageImpl;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.LuStatementTypeHeaderTemplateInfo;
import org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo;
import org.kuali.student.lum.nlt.naturallanguage.ContextRegistry;
import org.kuali.student.lum.nlt.naturallanguage.context.Context;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomLuStatementInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomReqComponentInfo;
import org.kuali.student.lum.nlt.naturallanguage.util.ReqComponentReference;
import org.kuali.student.lum.nlt.naturallanguage.util.LuStatementAnchor;
import org.kuali.student.lum.nlt.naturallanguage.util.TemplateTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class translates a LU (learning unit) statement into a specific 
 * natural language.
 */
public class StatementTranslator {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(StatementTranslator.class);

    private String language;
	private StatementParser statementParser = new StatementParser("*", "+");
	private ReqComponentTranslator reqComponentTranslator;
	private NaturalLanguageMessageBuilder messageBuilder;
    private ContextRegistry<Context<LuStatementAnchor>> contextRegistry;
    private TemplateTranslator templateTranslator = new TemplateTranslator();
    
	/**
	 * Constructs a new natural language translator in the 
	 * default language locale.
	 */
	public StatementTranslator() {
		this.language = Locale.getDefault().getLanguage();
    }

	/**
	 * Sets the translation language.
	 * 
	 * @param language Translation language
	 */
	public void setLanguage(final String language) {
		this.language = language;
		setLanguage();
	}

	/**
	 * Sets the translation language for the message builder and 
	 * requirement component translator.
	 */
	private void setLanguage() {
		if(this.language != null) {
			if(this.messageBuilder != null) {
				this.messageBuilder.setLanguage(this.language);
			}
			if(this.reqComponentTranslator != null) {
				this.reqComponentTranslator.setLanguage(this.language);
			}
		}
	}
	
    /**
     * Sets the template context registry.
     * 
     * @param contextRegistry Template context registry
     */
    public void setContextRegistry(final ContextRegistry<Context<LuStatementAnchor>> contextRegistry) {
    	this.contextRegistry = contextRegistry;
    }

	/**
	 * Sets the requirement component translator.
	 * 
	 * @param reqComponentTranslator Requirement component translator
	 */
	public void setReqComponentTranslator(final ReqComponentTranslator reqComponentTranslator) {
		this.reqComponentTranslator = reqComponentTranslator;
		setLanguage();
	}

	/**
	 * Sets the message builder.
	 * 
	 * @param messageBuilder Message builder
	 */
    public void setMessageBuilder(final NaturalLanguageMessageBuilder messageBuilder) {
		this.messageBuilder = messageBuilder;
		setLanguage();
    }

	/**
	 * Translates a statement directly attached to a CLU (anchor) for a 
	 * specific natural language usuage type (context) into natural language.
	 * 
	 * @param cluId CLU anchor
	 * @param luStatement LU Statement
	 * @param nlUsageTypeKey Usuage type key (context)
	 * @return Natural language statement translation
	 * @throws DoesNotExistException CLU or statement id does not exists
	 * @throws OperationFailedException Translation fails
	 */
	public String translate(final String cluId, final CustomLuStatementInfo luStatement, final String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		if(luStatement == null) {
    		throw new DoesNotExistException("LuStatement cannot be null");
		}

		String booleanExpression = this.statementParser.getBooleanExpressionAsReqComponents(luStatement);
		List<ReqComponentReference> reqComponentList = this.statementParser.getLeafReqComponents(luStatement);
		String message = buildMessage(nlUsageTypeKey, booleanExpression, reqComponentList);
		String header = "";
		if(cluId != null && !cluId.isEmpty()) {
			header = getHeader(luStatement, nlUsageTypeKey, cluId);
		}
		
		return header + message;
	}

	/**
	 * Translates a statement directly attached to a CLU for a specific natural 
	 * language usuage type (context) into natural language tree structure.
	 * 
	 * @param cluId Clu anchor
	 * @param luStatement LU statement
	 * @param nlUsageTypeKey Natural language usage type key (context)
	 * @return Natural language root tree node
	 * @throws DoesNotExistException CLU or statement does not exist
	 * @throws OperationFailedException Translation fails
	 */
	public NLTranslationNodeInfo translateToTree(final String cluId, final CustomLuStatementInfo luStatement, final String nlUsageTypeKey) throws DoesNotExistException, OperationFailedException {
		if(luStatement == null) {
//			return null;
    		throw new DoesNotExistException("LuStatement cannot be null");
		}

		String booleanExpression = statementParser.getBooleanExpressionAsReqComponents(luStatement);
		String operator = (luStatement.getOperator() == null ? null : luStatement.getOperator().toString());
		String booleanId = statementParser.getIdMap().get(luStatement.getId());
		NLTranslationNodeInfo rootNode = new NLTranslationNodeInfo(luStatement.getId(), booleanId, operator);
		rootNode.setBooleanExpression(booleanExpression);

		createStatementTree(luStatement, rootNode, nlUsageTypeKey);

		String translation = translate(cluId, luStatement, nlUsageTypeKey);
		rootNode.setNLTranslation(translation);

		return rootNode;
	}
	
	/**
	 * Builds the full translated message.
	 * 
	 * @param nlUsageTypeKey Usuage type key
	 * @param booleanExpression Boolean expression
	 * @param reqComponentList Requirement component list
	 * @return Translated message
	 * @throws DoesNotExistException Requirement component does not exist
	 * @throws OperationFailedException Translation fails
	 */
	private String buildMessage(String nlUsageTypeKey, String booleanExpression, List<ReqComponentReference> reqComponentList) throws DoesNotExistException, OperationFailedException {
		MessageContainer messageContainer = new MessageContainer();
		for(ReqComponentReference reqComponent : reqComponentList) {
			String translation = this.reqComponentTranslator.translate(reqComponent.getReqComponent(), nlUsageTypeKey);
			BooleanMessage bm = new BooleanMessageImpl(reqComponent.getBooleanId(), true, translation);
			messageContainer.addMessage(bm);
		}
		
		String message = this.messageBuilder.buildMessage(booleanExpression, messageContainer);
		return message;
	}
	
	/**
	 * Gets header for root <code>luStatement</code>.
	 * 
	 * @param luStatement LU statement
	 * @param nlUsageTypeKey Natural language usuage type context key
	 * @param cluId Anchor CLU id
	 * @return Statement header
	 * @throws DoesNotExistException CLU or header template does not exist
	 */
	private String getHeader(CustomLuStatementInfo luStatement, String nlUsageTypeKey, String cluId) throws OperationFailedException, DoesNotExistException {
        if(cluId == null) {
        	return "";
        }
        
        String template = getHeaderTemplate(luStatement, nlUsageTypeKey);
		
        Map<String, Object> contextMap = buildContextMap(luStatement, cluId);
        String header = this.templateTranslator.translate(contextMap, template);
        
        return header;
	}

    /**
     * Builds a statement type context map.
     * 
	 * @param luStatement Lu statement
	 * @param cluId Lu statement's anchor CLU id
	 * @return Context map 
	 * @throws DoesNotExistException
	 */
	private Map<String, Object> buildContextMap(CustomLuStatementInfo luStatement, String cluId) throws OperationFailedException, DoesNotExistException {
		LuStatementAnchor lua = new LuStatementAnchor(luStatement, cluId);
		String statementTypeKey = luStatement.getLuStatementType().getId();
		Context<LuStatementAnchor> context = this.contextRegistry.get(statementTypeKey);
    	if(context == null) {
        	throw new DoesNotExistException("Header context not found in registry for statement type key: " + statementTypeKey);
    	}
		Map<String, Object> contextMap = context.createContextMap(lua);
    	
        return contextMap;
	}

	/**
	 * Gets header template for root <code>luStatement</code>.
	 * 
	 * @param luStatement LU statement
	 * @param nlUsageTypeKey Natural language usuage type context key
	 * @return Header template
	 * @throws DoesNotExistException Header template does not exist
	 */
	private String getHeaderTemplate(CustomLuStatementInfo luStatement, String nlUsageTypeKey) throws DoesNotExistException {
		for(LuStatementTypeHeaderTemplateInfo header : luStatement.getLuStatementType().getHeaders()) {
			if(header.getNlUsageTypeKey().equals(nlUsageTypeKey) && header.getLanguage().equals(this.language)) {
				return (header.getTemplate() == null ? "" : header.getTemplate());
			}
		}
        throw new DoesNotExistException("Natural language usage type key '" + nlUsageTypeKey + "'" +
        		" and language code '" + this.language + "' for statement type header not found");
	}

	/**
	 * Create a statement tree as <code>NLTranslationNodeInfo</code>.
	 * 
	 * @param luStatement LU statement
	 * @param rootNode Root node to translate to
	 * @param nlUsageTypeKey Natural language usuage type context key
	 * @throws DoesNotExistException Requirement component does not exist
	 * @throws OperationFailedException Translation fails
	 */
	private void createStatementTree(CustomLuStatementInfo luStatement, NLTranslationNodeInfo rootNode, String nlUsageTypeKey) 
		throws DoesNotExistException, OperationFailedException {
		if (luStatement.getChildren() == null || luStatement.getChildren().isEmpty()) {
			List<NLTranslationNodeInfo> children = getReqComponents(luStatement.getRequiredComponents(), nlUsageTypeKey);
			rootNode.setChildNodes(children);
			rootNode.setNLTranslation(getNLTranslation(children, rootNode.getOperator()));
			return;
		}

		for(Iterator<CustomLuStatementInfo> it = luStatement.getChildren().iterator(); it.hasNext();) {
			CustomLuStatementInfo stmt = it.next();
			String operator = (stmt.getOperator() == null ? null : stmt.getOperator().toString());
			String booleanId = statementParser.getIdMap().get(stmt.getId());
			NLTranslationNodeInfo node = new NLTranslationNodeInfo(stmt.getId(), booleanId, operator);
			node.setParentNode(rootNode);
			rootNode.addChildNode(node);
			if (stmt.getChildren() == null || stmt.getChildren().isEmpty()) {
				List<NLTranslationNodeInfo> children = getReqComponents(stmt.getRequiredComponents(), nlUsageTypeKey);
				node.setChildNodes(children);
				node.setNLTranslation(getNLTranslation(children, node.getOperator()));
			} else {
				createStatementTree(stmt, node, nlUsageTypeKey);
			}
		}
	}
	
	/**
	 * Gets the node's natural language translation.
	 * 
	 * @param children Nodes children
	 * @param operator Boolean operator
	 * @return Node's natural language translation
	 */
	private String getNLTranslation(List<NLTranslationNodeInfo> children, String operator) {
		StringBuilder sb = new StringBuilder();
		for(Iterator<NLTranslationNodeInfo> it = children.iterator(); it.hasNext();) {
			NLTranslationNodeInfo node = it.next();
			sb.append(node.getNLTranslation());
			if(it.hasNext()) {
				sb.append(" ");
				sb.append(operator.toLowerCase());
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	/**
	 * Gets the requirement components as a list of translated nodes.
	 * 
	 * @param reqComponentList Requirement component list
	 * @param nlUsageTypeKey Usuage type key (context)
	 * @return List of translated nodes
	 * @throws DoesNotExistException Requirement component does not exist
	 * @throws OperationFailedException Translation fails
	 */
	private List<NLTranslationNodeInfo> getReqComponents(List<CustomReqComponentInfo> reqComponentList, String nlUsageTypeKey) 
		throws DoesNotExistException, OperationFailedException {
		List<NLTranslationNodeInfo> list = new ArrayList<NLTranslationNodeInfo>(reqComponentList.size());
		for(CustomReqComponentInfo reqComp : reqComponentList) {
			String translation = this.reqComponentTranslator.translate(reqComp, nlUsageTypeKey);
			String booleanId = statementParser.getIdMap().get(reqComp.getId());
			NLTranslationNodeInfo node = new NLTranslationNodeInfo(reqComp.getId(), booleanId, null);
			node.setNLTranslation(translation);
			list.add(node);
		}
		return list;
	}
}
