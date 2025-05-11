package fr.dl11.openmedia.datasource.events;

import fr.dl11.openmedia.events.BaseEvent;

/**
 * Represents an event triggered when new data is received.
 *
 * <p>The {@code NewIncomingDataEvent} class extends {@link BaseEvent} and
 * encapsulates the data associated with the event. It provides a constructor
 * to initialize the event with the incoming data.
 */
public class NewIncomingDataEvent extends BaseEvent {

    /**
     * The data associated with the event.
     */
    public Object data;

    /**
     * Constructs a new {@code NewIncomingDataEvent} with the specified data.
     *
     * @param data The data associated with the event.
     */
    public NewIncomingDataEvent(Object data) {
        super();
        this.data = data;
    }
}