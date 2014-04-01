/**
 * 
 */
package com.prodyna.ted.survey.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Iryna Feuerstein, PRODYNA AG
 * @verion 01.04.2014
 */
public class FunctionalRuntimeException extends RuntimeException {

    private static final Logger LOG = LoggerFactory.getLogger(FunctionalRuntimeException.class);

    /**
     * Declared as private to force the usage of exception messages
     */
    private FunctionalRuntimeException() {
        LOG.error("FunctionalRuntimeException");
    }

    /**
     * @param message
     */
    public FunctionalRuntimeException(String message) {
        super(message);
        LOG.error(message);
    }

    /**
     * @param cause
     */
    public FunctionalRuntimeException(Throwable cause) {
        super(cause);
        LOG.error(cause.getMessage());
    }

    /**
     * @param message
     * @param cause
     */
    public FunctionalRuntimeException(String message, Throwable cause) {
        super(message, cause);
        LOG.error(cause.getMessage());
    }

}
