package com.prodyna.ted.survey.exception;

/**
 * Functional Exception for the Client.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class FunctionalException extends Exception {

	private static final long serialVersionUID = 1L;

	public FunctionalException(String message) {
		super(message);
	}
	
	public FunctionalException() {
	}

}
