
package org.kuali.student.rules.rulemanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.2
 * Mon Jan 05 10:43:34 EST 2009
 * Generated source version: 2.1.2
 */

@XmlRootElement(name = "findAgendaTypesResponse", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findAgendaTypesResponse", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement")

public class FindAgendaTypesResponse {

    @XmlElement(name = "return")
    private java.util.List _return;

    public java.util.List getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List new_return)  {
        this._return = new_return;
    }

}

