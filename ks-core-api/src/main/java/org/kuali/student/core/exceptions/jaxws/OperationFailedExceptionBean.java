package org.kuali.student.core.exceptions.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by the CXF 2.0.4-incubator Thu Mar 20 11:20:16 EDT 2008 Generated source version: 2.0.4-incubator
 */

@XmlRootElement(name = "OperationFailedException", namespace = "http://student.kuali.org/wsdl/exceptions")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperationFailedException", namespace = "http://student.kuali.org/wsdl/exceptions")
public class OperationFailedExceptionBean {

    private java.lang.String message;

    public java.lang.String getMessage() {
        return message;
    }

    public void setMessage(java.lang.String newMessage) {
        message = newMessage;
    }

}
