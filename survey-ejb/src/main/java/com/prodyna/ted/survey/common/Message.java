/**
 * 
 */
package com.prodyna.ted.survey.common;

/**
 * @author Iryna Feuerstein, PRODYNA AG
 * @verion 04.04.2014
 */
public class Message {
    private final String code;
    private final String defaultMessage;
    private final Severity severity;

    /**
     * @param code
     * @param defaultMessage
     */
    public Message(String code, String defaultMessage, Severity severity) {
        super();
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.severity = severity;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the defaultMessage
     */
    public String getDefaultMessage() {
        return defaultMessage;
    }

    public enum Severity {
        INFO, WARN, ERROR
    }

    /**
     * @return the severity
     */
    public Severity getSeverity() {
        return severity;
    }

}
