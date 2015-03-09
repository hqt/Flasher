package com.event.flasher.system;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventHandler {

    /**
     * object that has subscribe this event
     */
    private Object handler;

    /**
     * method that this subscriber will be called when received event intent
     */
    private Method mMethod;

    /**
     * event
     */
    private Event event;

    public EventHandler(Object object, Method method, Event event) {
        this.handler = object;
        this.mMethod = method;
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public Object getHandler() {
        return handler;
    }

    public Method getmMethod() {
        return mMethod;
    }

    public void invoke(Object data) {
        try {
            mMethod.invoke(handler, data);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
