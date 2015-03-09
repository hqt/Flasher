package com.event.flasher.exception;

public class ParameterMismatchException extends RuntimeException {

    public ParameterMismatchException() {
        super("Should exactly one parameter for this method");
    }

    //Constructor that accepts a message
    public ParameterMismatchException(String message) {
        super(message);
    }

    public ParameterMismatchException(Throwable cause) {
        super(cause);
    }

    public ParameterMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
