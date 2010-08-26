
package org.kuali.student.wsdl.proposal;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.3
 * Thu Aug 26 16:25:56 EDT 2010
 * Generated source version: 2.2.3
 * 
 */

@WebFault(name = "InvalidParameterException", targetNamespace = "http://student.kuali.org/wsdl/proposal")
public class InvalidParameterException extends Exception {
    public static final long serialVersionUID = 20100826162556L;
    
    private org.kuali.student.wsdl.exceptions.InvalidParameterException invalidParameterException;

    public InvalidParameterException() {
        super();
    }
    
    public InvalidParameterException(String message) {
        super(message);
    }
    
    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParameterException(String message, org.kuali.student.wsdl.exceptions.InvalidParameterException invalidParameterException) {
        super(message);
        this.invalidParameterException = invalidParameterException;
    }

    public InvalidParameterException(String message, org.kuali.student.wsdl.exceptions.InvalidParameterException invalidParameterException, Throwable cause) {
        super(message, cause);
        this.invalidParameterException = invalidParameterException;
    }

    public org.kuali.student.wsdl.exceptions.InvalidParameterException getFaultInfo() {
        return this.invalidParameterException;
    }
}
