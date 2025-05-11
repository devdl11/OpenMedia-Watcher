package fr.dl11.openmedia.core;

import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.EventHandler;
import fr.dl11.openmedia.events.EventObserver;
import fr.dl11.openmedia.exceptions.FailedEventHandling;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * EventBus is a singleton class that facilitates the publish-subscribe pattern.
 *
 * <p>It allows event handlers and observers to subscribe to specific event types
 * and ensures that events are dispatched to the appropriate subscribers.
 */
public class EventBus {
    private static final EventBus instance = new EventBus();

    private final Map<Class<? extends Event>, List<EventHandler<? extends Event>>> handlers;
    private final Map<Class<? extends Event>, List<EventObserver<? extends Event>>> observers;

    /**
     * Private constructor to enforce the singleton pattern.
     */
    private EventBus() {
        this.handlers = new HashMap<>();
        this.observers = new HashMap<>();
    }

    /**
     * Retrieves the singleton instance of the EventBus.
     *
     * @return The singleton instance of the EventBus.
     */
    public static EventBus getInstance() {
        return instance;
    }

    /**
     * Subscribes an event handler to a specific event type.
     *
     * @param event   The class of the event to subscribe to.
     * @param handler The event handler to register.
     * @param <T>     The type of the event.
     */
    public <T extends Event> void subscribe(Class<T> event, EventHandler<? super T> handler) {
        assert event != null;
        assert handler != null;

        handlers.computeIfAbsent(event, k -> new LinkedList<>()).add(handler);
    }

    /**
     * Subscribes an event observer to a specific event type.
     *
     * @param event    The class of the event to subscribe to.
     * @param observer The event observer to register.
     * @param <T>      The type of the event.
     */
    public <T extends Event> void subscribe(Class<T> event, EventObserver<? super T> observer) {
        assert event != null;
        assert observer != null;

        observers.computeIfAbsent(event, k -> new LinkedList<>()).add(observer);
    }

    /**
     * Unsubscribes an event handler from a specific event type.
     *
     * @param event   The class of the event to unsubscribe from.
     * @param handler The event handler to remove.
     * @param <T>     The type of the event.
     */
    public <T extends Event> void unsubscribe(Class<T> event, EventHandler<? super T> handler) {
        assert event != null;
        assert handler != null;

        List<EventHandler<? extends Event>> handlersList = handlers.get(event);
        if (handlersList != null) {
            handlersList.remove(handler);
        }
    }

    /**
     * Unsubscribes an event observer from a specific event type.
     *
     * @param event    The class of the event to unsubscribe from.
     * @param observer The event observer to remove.
     * @param <T>      The type of the event.
     */
    public <T extends Event> void unsubscribe(Class<T> event, EventObserver<? super T> observer) {
        assert event != null;
        assert observer != null;

        List<EventObserver<? extends Event>> observersList = observers.get(event);
        if (observersList != null) {
            observersList.remove(observer);
        }
    }

    /**
     * Publishes an event to all subscribed handlers and observers.
     *
     * <p>Observers are notified first, followed by handlers. If an observer
     * consumes the event, it will not be passed to handlers. If a handler
     * consumes the event, subsequent handlers will not be notified.
     *
     * @param event The event to publish.
     * @param <T>   The type of the event.
     * @throws RuntimeException If an observer consumes the event.
     */
    @SuppressWarnings("unchecked")
    public <T extends Event> void publish(T event) {
        assert event != null;

        // Notify observers
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

        // Notify handlers
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

        // Log if the event was not consumed
        if (!event.isConsumed()) {
            System.err.println("Event was not consumed ! " + event.getClass().getName());
        }
    }
}