package fr.dl11.openmedia.common.events;

import fr.dl11.openmedia.datasource.data.RawData;
import fr.dl11.openmedia.events.BaseEvent;

/**
 * Event representing the arrival of new raw data.
 *
 * <p>This class extends the {@link BaseEvent} and encapsulates
 * a {@link RawData} object, which contains the raw data associated
 * with the event. It provides a method to retrieve the raw data.
 */
public class NewRawDataEvent extends BaseEvent {
    private final RawData rawData;

    /**
     * Constructs a new {@code NewRawDataEvent} with the specified raw data.
     *
     * @param rawData the raw data associated with this event, must not be null.
     */
    public NewRawDataEvent(RawData rawData) {
        this.rawData = rawData;
    }

    /**
     * Returns the raw data associated with this event.
     *
     * @return the {@link RawData} object.
     */
    public RawData getRawData() {
        return rawData;
    }
}