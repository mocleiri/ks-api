package org.kuali.student.core.exceptions;

public class MissingParameterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public MissingParameterException() {
        super();
    }

    public MissingParameterException(String message) {
        super(message);
    }

}
