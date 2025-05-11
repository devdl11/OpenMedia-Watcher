package fr.dl11.openmedia.events;

/**
 * Abstract base class for all event types.
 *
 * <p>The {@code BaseEvent} class provides a common structure for events, including
 * a timestamp to indicate when the event was created and a mechanism to mark the event
 * as consumed. It implements the {@link Event} interface.</p>
 */
public abstract class BaseEvent implements Event {

    /**
     * The timestamp indicating when the event was created.
     */
    private final long timestamp;

    /**
     * Flag indicating whether the event has been consumed.
     */
    private boolean consumed = false;

    /**
     * Constructs a new {@code BaseEvent} with the specified timestamp.
     *
     * @param timestamp The timestamp of the event creation.
     */
    public BaseEvent(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Constructs a new {@code BaseEvent} with the current system time as the timestamp.
     */
    public BaseEvent() {
        this(System.currentTimeMillis());
    }

    /**
     * Returns the timestamp of the event creation.
     *
     * @return The timestamp of the event.
     */
    @Override
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Checks whether the event has been consumed.
     *
     * @return {@code true} if the event is consumed, {@code false} otherwise.
     */
    @Override
    public boolean isConsumed() {
        return consumed;
    }

    /**
     * Marks the event as consumed.
     */
    @Override
    public void consume() {
        consumed = true;
    }
}