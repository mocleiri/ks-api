
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

@XmlRootElement(name = "fetchBusinessRuleInfoByAnchorResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/rulemanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchBusinessRuleInfoByAnchorResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/rulemanagement")

public class FetchBusinessRuleInfoByAnchorResponse {

    @XmlElement(name = "return")
    private java.util.List _return;

    public java.util.List getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List new_return)  {
        this._return = new_return;
    }

}

