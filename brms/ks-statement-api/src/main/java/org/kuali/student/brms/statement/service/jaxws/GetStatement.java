
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

@XmlRootElement(name = "getStatement", namespace = "http://student.kuali.org/wsdl/statement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getStatement", namespace = "http://student.kuali.org/wsdl/statement")

public class GetStatement {

    @XmlElement(name = "statementId")
    private java.lang.String statementId;

    public java.lang.String getStatementId() {
        return this.statementId;
    }

    public void setStatementId(java.lang.String newStatementId)  {
        this.statementId = newStatementId;
    }

}

