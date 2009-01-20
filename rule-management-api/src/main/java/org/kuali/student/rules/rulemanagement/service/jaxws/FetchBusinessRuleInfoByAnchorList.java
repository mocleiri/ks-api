
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

@XmlRootElement(name = "fetchBusinessRuleInfoByAnchorList", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchBusinessRuleInfoByAnchorList", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement")

public class FetchBusinessRuleInfoByAnchorList {

    @XmlElement(name = "businessRuleAnchorInfoList")
    private org.kuali.student.rules.rulemanagement.dto.BusinessRuleAnchorInfoDTO businessRuleAnchorInfoList;

    public org.kuali.student.rules.rulemanagement.dto.BusinessRuleAnchorInfoDTO getBusinessRuleAnchorInfoList() {
        return this.businessRuleAnchorInfoList;
    }

    public void setBusinessRuleAnchorInfoList(org.kuali.student.rules.rulemanagement.dto.BusinessRuleAnchorInfoDTO newBusinessRuleAnchorInfoList)  {
        this.businessRuleAnchorInfoList = newBusinessRuleAnchorInfoList;
    }

}

