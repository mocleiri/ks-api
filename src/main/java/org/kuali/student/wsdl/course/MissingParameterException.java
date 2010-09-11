
package org.kuali.student.wsdl.course;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.10
 * Wed Sep 08 11:26:43 EDT 2010
 * Generated source version: 2.2.10
 * 
 */

@WebFault(name = "MissingParameterException", targetNamespace = "http://student.kuali.org/wsdl/course")
public class MissingParameterException extends Exception {
    public static final long serialVersionUID = 20100908112643L;
    
    private org.kuali.student.wsdl.exceptions.MissingParameterException missingParameterException;

    public MissingParameterException() {
        super();
    }
    
    public MissingParameterException(String message) {
        super(message);
    }
    
    public MissingParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingParameterException(String message, org.kuali.student.wsdl.exceptions.MissingParameterException missingParameterException) {
        super(message);
        this.missingParameterException = missingParameterException;
    }

    public MissingParameterException(String message, org.kuali.student.wsdl.exceptions.MissingParameterException missingParameterException, Throwable cause) {
        super(message, cause);
        this.missingParameterException = missingParameterException;
    }

    public org.kuali.student.wsdl.exceptions.MissingParameterException getFaultInfo() {
        return this.missingParameterException;
    }
}
