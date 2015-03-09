package com.event.flasher.system;

import com.event.flasher.annotation.Subscribe;
import com.event.flasher.exception.NoEventHandlerException;
import com.event.flasher.exception.NoSubscriberException;
import com.event.flasher.exception.ParameterMismatchException;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PusherController implements IBus {
    private static PusherController mBus;

    // manage subscribers
    private Map<Event, Set<EventHandler>> mSubscribers;

    // manage handlers
    private List<Object> allHandlers = new ArrayList<>();

    // constructor
    private PusherController() {
        mSubscribers = new HashMap<>();
    }

    public static PusherController getInstance() {
        if (mBus == null) {
            synchronized (PusherController.class) {
                if (mBus == null) {
                    mBus = new PusherController();
                }
            }
        }
        return mBus;
    }

    @Override
    public void subscribe(Object subscriber) {
        allHandlers.add(subscriber);

        // find all method of this object that has @Subscribe annotation
        for (Method method : subscriber.getClass().getDeclaredMethods()) {
            final Subscribe subscribeAnnotation = method.getAnnotation(Subscribe.class);

            // this method has @Subscribe annotation
            if (subscribeAnnotation != null) {
                // get all parameter types of this method
                Type[] gpType = method.getGenericParameterTypes();

                // method has @Subscribe annotation shouldn't have more than one parameter
                if (gpType.length != 1) {
                    throw new ParameterMismatchException();
                }

                // get first (and only) DataType
                Type datatype = gpType[0];

                // make event object for searching or adding to map purpose
                Event eventKey = new Event(datatype);

                // find Event that this method belongs to
                Set<EventHandler> eventHandlers = mSubscribers.get(eventKey);

                // create EventHandler from this subscriber information
                EventHandler handler = new EventHandler(subscriber, method, eventKey);

                // if this Event has already contained in map
                if (eventHandlers != null) {
                    eventHandlers.add(handler);
                } else {
                    // create new HashSet to store EventHandlers
                    eventHandlers = new HashSet<>();
                    eventHandlers.add(handler);

                    // put into map
                    mSubscribers.put(eventKey, eventHandlers);
                }
            }
        }
    }


    @Override
    public void unsubscribe(Object subscriber) {
        if (!checkHasSubscribed(subscriber)) {
            throw new NoSubscriberException();
        }
        allHandlers.remove(subscriber);
        removeAllSubscriberMethod(subscriber);
    }

    // remove all exist methods
    private void removeAllSubscriberMethod(Object subscriber) {
        Set<Map.Entry<Event, Set<EventHandler>>> set = mSubscribers.entrySet();
        Iterator<Map.Entry<Event, Set<EventHandler>>> iterator = set.iterator();

        while (iterator.hasNext()) {
            Map.Entry<Event, Set<EventHandler>> entry = iterator.next();
            Set<EventHandler> handlerSet = entry.getValue();
            Iterator<EventHandler> iterSet = handlerSet.iterator();

            while (iterSet.hasNext()) {
                EventHandler handler = iterSet.next();
                if (handler.getHandler().equals(subscriber)) {
                    iterSet.remove();
                }
            }

            if (handlerSet.size() == 0) {
                iterator.remove();
            }
        }
    }

    // check have subscribed or not
    private boolean checkHasSubscribed(Object subscriber) {
        return allHandlers.contains(subscriber);
    }

    @Override
    public void post(Object data) {
        // 1. find all EventHandler that register this event
        // make dummy object for searching purpose
        Event search = new Event();
        search.setmEventName(data.getClass().toString());
        Set<EventHandler> eventHandlers = mSubscribers.get(search);

        if (eventHandlers == null) {
            throw new NoEventHandlerException();
        }

        for (EventHandler handler : eventHandlers) {
            // 2. send to each event handler signals
            handler.invoke(data);
        }
    }

}
