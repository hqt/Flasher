package com.event.flasher.exception;

public class NoEventHandlerException extends RuntimeException {

    public NoEventHandlerException() {
        super("No EventHandler handlers current event");
    }

    //Constructor that accepts a message
    public NoEventHandlerException(String message) {
        super(message);
    }

    public NoEventHandlerException(Throwable cause) {
        super(cause);
    }

    public NoEventHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
