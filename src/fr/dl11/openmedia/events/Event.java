package fr.dl11.openmedia.events;

/**
 * Interface representing a generic event.
 *
 * <p>The {@code Event} interface defines the basic structure and behavior
 * of an event, including methods to retrieve the event's timestamp,
 * check if the event has been consumed, and mark the event as consumed.</p>
 */
public interface Event {

    /**
     * Returns the timestamp of the event creation.
     *
     * @return The timestamp of the event as a {@code long}.
     */
    long getTimestamp();

    /**
     * Checks whether the event has been consumed.
     *
     * @return {@code true} if the event is consumed, {@code false} otherwise.
     */
    boolean isConsumed();

    /**
     * Marks the event as consumed.
     */
    void consume();
}