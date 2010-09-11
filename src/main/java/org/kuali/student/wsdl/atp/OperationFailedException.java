
package org.kuali.student.wsdl.atp;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.10
 * Wed Sep 08 11:26:22 EDT 2010
 * Generated source version: 2.2.10
 * 
 */

@WebFault(name = "OperationFailedException", targetNamespace = "http://student.kuali.org/wsdl/atp")
public class OperationFailedException extends Exception {
    public static final long serialVersionUID = 20100908112622L;
    
    private org.kuali.student.wsdl.exceptions.OperationFailedException operationFailedException;

    public OperationFailedException() {
        super();
    }
    
    public OperationFailedException(String message) {
        super(message);
    }
    
    public OperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationFailedException(String message, org.kuali.student.wsdl.exceptions.OperationFailedException operationFailedException) {
        super(message);
        this.operationFailedException = operationFailedException;
    }

    public OperationFailedException(String message, org.kuali.student.wsdl.exceptions.OperationFailedException operationFailedException, Throwable cause) {
        super(message, cause);
        this.operationFailedException = operationFailedException;
    }

    public org.kuali.student.wsdl.exceptions.OperationFailedException getFaultInfo() {
        return this.operationFailedException;
    }
}
