
package org.kuali.student.rules.rulemanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Tue Feb 10 16:55:43 PST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "fetchBriefBusinessRuleInfo", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchBriefBusinessRuleInfo", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement")

public class FetchBriefBusinessRuleInfo {

    @XmlElement(name = "businessRuleId")
    private java.lang.String businessRuleId;

    public java.lang.String getBusinessRuleId() {
        return this.businessRuleId;
    }

    public void setBusinessRuleId(java.lang.String newBusinessRuleId)  {
        this.businessRuleId = newBusinessRuleId;
    }

}

