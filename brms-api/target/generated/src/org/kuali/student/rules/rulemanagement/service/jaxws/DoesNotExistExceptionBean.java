
package org.kuali.student.rules.rulemanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.4
 * Wed Mar 11 16:48:58 EDT 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "DoesNotExistException", namespace = "http://exceptions.ws.common.poc.student.kuali.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoesNotExistException", namespace = "http://exceptions.ws.common.poc.student.kuali.org/")

public class DoesNotExistExceptionBean {

    private java.lang.String message;

    public java.lang.String getMessage() {
        return this.message;
    }

    public void setMessage(java.lang.String newMessage)  {
        this.message = newMessage;
    }

}

