
package org.kuali.student.poc.wsdl.learningunit.luipersonrelation.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by the CXF 2.0.4-incubator
 * Tue Apr 29 15:25:19 EDT 2008
 * Generated source version: 2.0.4-incubator
 * 
 */

@XmlRootElement(name = "PermissionDeniedException", namespace = "http://exceptions.ws.common.poc.student.kuali.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PermissionDeniedException", namespace = "http://exceptions.ws.common.poc.student.kuali.org/")

public class PermissionDeniedExceptionBean {

    private java.lang.String message;

    public java.lang.String getMessage() {
        return this.message;
    }
    
    public void setMessage( java.lang.String newMessage ) {
        this.message = newMessage;
    }
    
}

