
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

@XmlRootElement(name = "updateStatementResponse", namespace = "http://student.kuali.org/wsdl/statement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateStatementResponse", namespace = "http://student.kuali.org/wsdl/statement")

public class UpdateStatementResponse {

    @XmlElement(name = "return")
    private org.kuali.student.core.statement.dto.StatementInfo _return;

    public org.kuali.student.core.statement.dto.StatementInfo getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.core.statement.dto.StatementInfo new_return)  {
        this._return = new_return;
    }

}

