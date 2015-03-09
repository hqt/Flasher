package com.event.flasher.exception;

public class NoSubscriberException extends RuntimeException {
    public NoSubscriberException() {
        super("Calling unsubscribe, you have not called subscribe() on this object");
    }

    public NoSubscriberException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NoSubscriberException(String detailMessage) {
        super(detailMessage);
    }

    public NoSubscriberException(Throwable throwable) {
        super(throwable);
    }
}
