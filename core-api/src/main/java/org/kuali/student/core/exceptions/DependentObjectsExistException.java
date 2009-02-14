package org.kuali.student.core.exceptions;

//@WebFault(faultBean="org.kuali.student.core.exceptions.jaxws.DependentObjectsExistExceptionBean")
public class DependentObjectsExistException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    public DependentObjectsExistException(){
        super();
    }

	public DependentObjectsExistException(String message) {
        super(message);
    }
}
