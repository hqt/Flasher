package com.event.flasher.system;

import java.lang.reflect.Type;

public class Event {
    private Type mEventType;
    private String mEventName;
    private int mHashCode = 0;

    // fucking constructor
    public Event() {

    }

    public Event(Type eventType) {
        this.mEventType = eventType;
        this.mEventName = mEventType.toString();
    }

    public String getEventName() {
        return mEventName;
    }

    public void setmEventName(String mEventName) {
        this.mEventName = mEventName;
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Event))
            return false;
        if (that == this)
            return true;

        Event thatEvent = (Event) that;
        return this.getEventName().equals(thatEvent.getEventName());
    }

    @Override
    public int hashCode() {
        if (mHashCode == 0) {
            mHashCode = this.getEventName().hashCode();
        }
        return mHashCode;
    }
}
