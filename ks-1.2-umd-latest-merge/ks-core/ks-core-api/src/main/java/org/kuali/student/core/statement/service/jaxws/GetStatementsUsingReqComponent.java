
package org.kuali.student.core.statement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Wed May 12 12:54:47 PDT 2010
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getStatementsUsingReqComponent", namespace = "http://student.kuali.org/wsdl/statement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getStatementsUsingReqComponent", namespace = "http://student.kuali.org/wsdl/statement")

public class GetStatementsUsingReqComponent {

    @XmlElement(name = "reqComponentId")
    private java.lang.String reqComponentId;

    public java.lang.String getReqComponentId() {
        return this.reqComponentId;
    }

    public void setReqComponentId(java.lang.String newReqComponentId)  {
        this.reqComponentId = newReqComponentId;
    }

}

