package com.prodyna.ted.survey.exception;

/**
 * Exception for Persistence Errors.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class PersistenceException extends Exception {

	private static final long serialVersionUID = 1L;

	public PersistenceException(String message) {
		super(message);
	}
	
	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public PersistenceException(Throwable cause) {
		super(cause);
	}
}
