package com.event.flasher.system;

public interface IBus {
    public void subscribe(Object object);
    public void unsubscribe(Object object);
    public void post(Object data);
}
