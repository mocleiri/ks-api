package org.kuali.student.poc.common.ws.exceptions;

public class UnsupportedActionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public UnsupportedActionException(){
        super();
    }
	
	public UnsupportedActionException(String message) {
        super(message);
    }

}
