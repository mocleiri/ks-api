
package org.kuali.student.rules.rulemanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.2
 * Tue Jan 06 16:57:14 EST 2009
 * Generated source version: 2.1.2
 */

@XmlRootElement(name = "AlreadyExistsException", namespace = "http://exceptions.ws.common.poc.student.kuali.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlreadyExistsException", namespace = "http://exceptions.ws.common.poc.student.kuali.org/")

public class AlreadyExistsExceptionBean {

    private java.lang.String message;

    public java.lang.String getMessage() {
        return this.message;
    }

    public void setMessage(java.lang.String newMessage)  {
        this.message = newMessage;
    }

}

