
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.4
 * Tue Feb 17 15:12:59 EST 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "AlreadyExistsException", namespace = "http://exceptions.core.student.kuali.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlreadyExistsException", namespace = "http://exceptions.core.student.kuali.org/")

public class AlreadyExistsExceptionBean {

    private java.lang.String message;

    public java.lang.String getMessage() {
        return this.message;
    }

    public void setMessage(java.lang.String newMessage)  {
        this.message = newMessage;
    }

}

