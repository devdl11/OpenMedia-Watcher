package fr.dl11.openmedia.events;

import fr.dl11.openmedia.exceptions.FailedEventHandling;

/**
 * Interface representing an event handler.
 *
 * <p>The {@code EventHandler} interface defines a contract for handling events
 * of a specific type. Implementations of this interface are responsible for
 * processing events and may throw a {@link FailedEventHandling} exception
 * if the event handling fails.</p>
 *
 * @param <T> The type of event that this handler processes. Must extend {@link Event}.
 */
public interface EventHandler<T extends Event> {

    /**
     * Handles the specified event.
     *
     * @param event The event to be handled. Must not be null.
     * @throws FailedEventHandling If the event handling fails.
     */
    void handleEvent(T event) throws FailedEventHandling;
}