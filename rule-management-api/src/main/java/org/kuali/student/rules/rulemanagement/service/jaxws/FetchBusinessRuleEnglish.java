
package org.kuali.student.rules.rulemanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1
 * Thu Oct 23 16:22:41 EDT 2008
 * Generated source version: 2.1
 */

@XmlRootElement(name = "fetchBusinessRuleEnglish", namespace = "http://student.kuali.org/poc/wsdl/brms/rulemanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchBusinessRuleEnglish", namespace = "http://student.kuali.org/poc/wsdl/brms/rulemanagement")

public class FetchBusinessRuleEnglish {

    @XmlElement(name = "businessRuleId")
    private java.lang.String businessRuleId;

    public java.lang.String getBusinessRuleId() {
        return this.businessRuleId;
    }

    public void setBusinessRuleId(java.lang.String newBusinessRuleId)  {
        this.businessRuleId = newBusinessRuleId;
    }

}

