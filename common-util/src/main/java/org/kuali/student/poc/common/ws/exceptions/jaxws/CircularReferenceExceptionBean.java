
package org.kuali.student.poc.common.ws.exceptions.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by the CXF 2.0.4-incubator
 * Thu Mar 27 11:42:26 EDT 2008
 * Generated source version: 2.0.4-incubator
 * 
 */

@XmlRootElement(name = "CircularReferenceException", namespace = "http://exceptions.personidentity.wsdl.poc.student.kuali.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CircularReferenceException", namespace = "http://exceptions.personidentity.wsdl.poc.student.kuali.org/")

public class CircularReferenceExceptionBean {

    private java.lang.String message;

    public java.lang.String getMessage() {
        return this.message;
    }
    
    public void setMessage( java.lang.String newMessage ) {
        this.message = newMessage;
    }
    
}

