
package org.kuali.student.brms.rulemanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.2
 * Thu Jan 15 15:29:33 EST 2009
 * Generated source version: 2.1.2
 */

@XmlRootElement(name = "createNewVersion", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createNewVersion", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement")

public class CreateNewVersion {

    @XmlElement(name = "businessRuleInfo")
    private org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfoDTO businessRuleInfo;

    public org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfoDTO getBusinessRuleInfo() {
        return this.businessRuleInfo;
    }

    public void setBusinessRuleInfo(org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfoDTO newBusinessRuleInfo)  {
        this.businessRuleInfo = newBusinessRuleInfo;
    }

}

