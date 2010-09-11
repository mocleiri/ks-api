
package org.kuali.student.wsdl.document;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.10
 * Wed Sep 08 11:26:25 EDT 2010
 * Generated source version: 2.2.10
 * 
 */

@WebFault(name = "VersionMismatchException", targetNamespace = "http://student.kuali.org/wsdl/document")
public class VersionMismatchException extends Exception {
    public static final long serialVersionUID = 20100908112625L;
    
    private org.kuali.student.wsdl.exceptions.VersionMismatchException versionMismatchException;

    public VersionMismatchException() {
        super();
    }
    
    public VersionMismatchException(String message) {
        super(message);
    }
    
    public VersionMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public VersionMismatchException(String message, org.kuali.student.wsdl.exceptions.VersionMismatchException versionMismatchException) {
        super(message);
        this.versionMismatchException = versionMismatchException;
    }

    public VersionMismatchException(String message, org.kuali.student.wsdl.exceptions.VersionMismatchException versionMismatchException, Throwable cause) {
        super(message, cause);
        this.versionMismatchException = versionMismatchException;
    }

    public org.kuali.student.wsdl.exceptions.VersionMismatchException getFaultInfo() {
        return this.versionMismatchException;
    }
}
