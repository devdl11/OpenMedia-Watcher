package fr.dl11.openmedia.events;

/**
 * Interface representing an observer for events.
 *
 * <p>The {@code EventObserver} interface defines a contract for observing
 * and reacting to events of a specific type. Implementations of this interface
 * should provide the logic for handling the observed events.</p>
 *
 * @param <T> The type of event that this observer listens to. Must extend {@link Event}.
 */
public interface EventObserver<T extends Event> {

    /**
     * Called when an event of the specified type occurs.
     *
     * @param event The event to be handled. Must not be null.
     */
    void onEvent(T event);
}