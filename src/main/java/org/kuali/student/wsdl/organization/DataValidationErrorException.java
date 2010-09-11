
package org.kuali.student.wsdl.organization;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.10
 * Wed Sep 08 11:26:35 EDT 2010
 * Generated source version: 2.2.10
 * 
 */

@WebFault(name = "DataValidationErrorException", targetNamespace = "http://student.kuali.org/wsdl/organization")
public class DataValidationErrorException extends Exception {
    public static final long serialVersionUID = 20100908112635L;
    
    private org.kuali.student.wsdl.exceptions.DataValidationErrorException dataValidationErrorException;

    public DataValidationErrorException() {
        super();
    }
    
    public DataValidationErrorException(String message) {
        super(message);
    }
    
    public DataValidationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataValidationErrorException(String message, org.kuali.student.wsdl.exceptions.DataValidationErrorException dataValidationErrorException) {
        super(message);
        this.dataValidationErrorException = dataValidationErrorException;
    }

    public DataValidationErrorException(String message, org.kuali.student.wsdl.exceptions.DataValidationErrorException dataValidationErrorException, Throwable cause) {
        super(message, cause);
        this.dataValidationErrorException = dataValidationErrorException;
    }

    public org.kuali.student.wsdl.exceptions.DataValidationErrorException getFaultInfo() {
        return this.dataValidationErrorException;
    }
}
