package fr.dl11.openmedia.core;

import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.EventHandler;
import fr.dl11.openmedia.events.EventObserver;
import fr.dl11.openmedia.exceptions.FailedEventHandling;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventBus {
    private static final EventBus instance = new EventBus();

    private final Map<Class<? extends Event>, List<EventHandler<? extends Event>>> handlers;
    private final Map<Class<? extends Event>, List<EventObserver<? extends Event>>> observers;

    private EventBus() {
        this.handlers = new HashMap<>();
        this.observers = new HashMap<>();
    }

    public static EventBus getInstance() {
        return instance;
    }

    public <T extends Event> void subscribe(Class<T> event, EventHandler<? super T> handler) {
        assert event != null;
        assert handler != null;

        handlers.computeIfAbsent(event, k -> new LinkedList<>()).add(handler);
    }

    public <T extends Event> void subscribe(Class<T> event, EventObserver<? super T> observer) {
        assert event != null;
        assert observer != null;

        observers.computeIfAbsent(event, k -> new LinkedList<>()).add(observer);
    }

    public <T extends Event> void unsubscribe(Class<T> event, EventHandler<? super T> handler) {
        assert event != null;
        assert handler != null;

        List<EventHandler<? extends Event>> handlersList = handlers.get(event);
        if (handlersList != null) {
            handlersList.remove(handler);
        }
    }

    public <T extends Event> void unsubscribe(Class<T> event, EventObserver<? super T> observer) {
        assert event != null;
        assert observer != null;

        List<EventObserver<? extends Event>> observersList = observers.get(event);
        if (observersList != null) {
            observersList.remove(observer);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> void publish(T event) {
        assert event != null;

        List<EventObserver<? extends Event>> observersList = observers.get(event.getClass());
        if (observersList != null) {
            for (EventObserver<? extends Event> observer : observersList) {
                EventObserver<T> castedObserver = (EventObserver<T>) observer;
                castedObserver.onEvent(event);
                if (event.isConsumed()) {
                    throw new RuntimeException("Event was consumed by observer ! " + event.getClass().getName());
                }
            }
        }

        List<EventHandler<? extends Event>> handlerList = handlers.get(event.getClass());
        if (handlerList != null) {
            for (EventHandler<? extends Event> handler : handlerList) {
                try {
                    EventHandler<T> castedHandler = (EventHandler<T>) handler;
                    castedHandler.handleEvent(event);
                } catch (FailedEventHandling e) {
                    System.err.println("FailedEventHandling: " + e.getMessage());
                }

                if (event.isConsumed()) {
                    break;
                }
            }
        }

        if (!event.isConsumed()) {
            System.err.println("Event was not consumed ! " + event.getClass().getName());
        }
    }
}
