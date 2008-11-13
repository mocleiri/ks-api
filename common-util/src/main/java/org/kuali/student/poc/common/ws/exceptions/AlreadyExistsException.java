package org.kuali.student.poc.common.ws.exceptions;

public class AlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public AlreadyExistsException(){
        super();
    }
	
	public AlreadyExistsException(String message) {
        super(message);
    }

}
