
package org.kuali.student.rules.rulemanagement.service.jaxws;

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

@XmlRootElement(name = "updateBusinessRuleStateResponse", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateBusinessRuleStateResponse", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement")

public class UpdateBusinessRuleStateResponse {

    @XmlElement(name = "return")
    private org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO _return;

    public org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO new_return)  {
        this._return = new_return;
    }

}

