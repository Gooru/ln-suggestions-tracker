package org.gooru.suggestions.exceptions;

import org.gooru.suggestions.responses.MessageResponse;

/**
 * @author ashish on 24/2/2017.
 */
public class MessageResponseWrapperException extends RuntimeException {
    private final MessageResponse response;

    public MessageResponseWrapperException(MessageResponse response) {
        this.response = response;
    }

    public MessageResponse getMessageResponse() {
        return this.response;
    }
}
