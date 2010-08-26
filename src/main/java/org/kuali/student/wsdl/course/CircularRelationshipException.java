
package org.kuali.student.wsdl.course;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.3
 * Thu Aug 26 12:54:12 EDT 2010
 * Generated source version: 2.2.3
 * 
 */

@WebFault(name = "CircularRelationshipException", targetNamespace = "http://student.kuali.org/wsdl/course")
public class CircularRelationshipException extends Exception {
    public static final long serialVersionUID = 20100826125412L;
    
    private org.kuali.student.wsdl.exceptions.CircularRelationshipException circularRelationshipException;

    public CircularRelationshipException() {
        super();
    }
    
    public CircularRelationshipException(String message) {
        super(message);
    }
    
    public CircularRelationshipException(String message, Throwable cause) {
        super(message, cause);
    }

    public CircularRelationshipException(String message, org.kuali.student.wsdl.exceptions.CircularRelationshipException circularRelationshipException) {
        super(message);
        this.circularRelationshipException = circularRelationshipException;
    }

    public CircularRelationshipException(String message, org.kuali.student.wsdl.exceptions.CircularRelationshipException circularRelationshipException, Throwable cause) {
        super(message, cause);
        this.circularRelationshipException = circularRelationshipException;
    }

    public org.kuali.student.wsdl.exceptions.CircularRelationshipException getFaultInfo() {
        return this.circularRelationshipException;
    }
}
