/*
 * Copyright 2009 The Kuali Foundation
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

package org.kuali.student.lum.workflow.qualifier;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.role.XPathQualifierResolver;
import org.kuali.rice.kew.rule.bo.RuleAttribute;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.service.OrganizationService;

/**
 * @author Kuali Student Team
 *
 */
public abstract class AbstractOrgQualifierResolver extends XPathQualifierResolver {

	private static final String KUALI_ORG_DEPARTMENT = "kuali.org.Department";
	private static final String KUALI_ORG_COLLEGE = "kuali.org.College";
	protected static final String DEPARTMENT = "department";
	protected static final String COLLEGE = "college";
	protected static final String ORG_ID = "orgId";
	protected OrganizationService orgService;
	
	protected static final String DEPT_RESOLVER_CONFIG =
									"<resolverConfig>" +
										"<baseXPathExpression>/documentContent/applicationContent/cluProposal</baseXPathExpression>" +
										"<qualifier name=\"" + ORG_ID +  "\">" +
											"<xPathExpression>./orgId</xPathExpression>" + 
										"</qualifier>" +
									"</resolverConfig>";
	
	
	protected static RuleAttribute ruleAttribute = new RuleAttribute();
	
	static {
		ruleAttribute.setXmlConfigData(DEPT_RESOLVER_CONFIG);
	}

	/**
	 * 
	 */
	public AbstractOrgQualifierResolver() {
		setRuleAttribute(ruleAttribute);
	}

	protected OrganizationService getOrganizationService() {
		if (null == orgService) {
		   	orgService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://org.kuali.student/core/organization","OrganizationService"));
		}
		return orgService;
	}

	protected void setOrganizationService(OrganizationService orgSvc) {
		orgService = orgSvc;
	}

	protected boolean isDepartment(OrgInfo orgInfo) {
		return orgInfo.getType().equals(KUALI_ORG_DEPARTMENT);
	}
	
	protected boolean isCollege(OrgInfo orgInfo) {
		return orgInfo.getType().equals(KUALI_ORG_COLLEGE);
	}
}