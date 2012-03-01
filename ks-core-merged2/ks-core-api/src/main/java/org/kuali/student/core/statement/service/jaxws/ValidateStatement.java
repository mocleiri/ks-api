
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

@Deprecated
@XmlRootElement(name = "validateStatement", namespace = "http://student.kuali.org/wsdl/statement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateStatement", namespace = "http://student.kuali.org/wsdl/statement", propOrder = {"validationType","statementInfo"})

public class ValidateStatement {

    @XmlElement(name = "validationType")
    private java.lang.String validationType;
    @XmlElement(name = "statementInfo")
    private org.kuali.student.core.statement.dto.StatementInfo statementInfo;

    public java.lang.String getValidationType() {
        return this.validationType;
    }

    public void setValidationType(java.lang.String newValidationType)  {
        this.validationType = newValidationType;
    }

    public org.kuali.student.core.statement.dto.StatementInfo getStatementInfo() {
        return this.statementInfo;
    }

    public void setStatementInfo(org.kuali.student.core.statement.dto.StatementInfo newStatementInfo)  {
        this.statementInfo = newStatementInfo;
    }

}

