/**
 * 
 */
package com.prodyna.ted.survey.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iryna Feuerstein, PRODYNA AG
 * @version 04.04.2014
 */
public class ServiceResult<T> {
    private final T result;
    private final List<Message> messages;

    /**
     * @param result
     * @param messages
     */
    private ServiceResult(T result, List<Message> messages) {
        super();
        this.result = result;
        this.messages = messages;
    }

    public static <T> ServiceResult<T> createServiceResult(T result, List<Message> messages) {
        return new ServiceResult<T>(result, messages);
    }

    public static <T> ServiceResult<T> createServiceResultNoMessage(T result) {
        List<Message> messages = new ArrayList<Message>();
        return new ServiceResult<T>(result, messages);
    }

    /**
     * @return the result
     */
    public T getResult() {
        return result;
    }

    /**
     * @return the messages
     */
    public List<Message> getMessages() {
        return messages;
    }

}
