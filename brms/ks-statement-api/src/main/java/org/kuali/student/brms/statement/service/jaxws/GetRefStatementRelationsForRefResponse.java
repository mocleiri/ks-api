
package org.kuali.student.brms.statement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Tue Jan 26 22:57:16 PST 2010
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getRefStatementRelationsForRefResponse", namespace = "http://student.kuali.org/wsdl/statement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRefStatementRelationsForRefResponse", namespace = "http://student.kuali.org/wsdl/statement")

public class GetRefStatementRelationsForRefResponse {

    @XmlElement(name = "return")
    private java.util.List _return;

    public java.util.List getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List new_return)  {
        this._return = new_return;
    }

}

