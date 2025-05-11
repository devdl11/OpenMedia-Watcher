package fr.dl11.openmedia.events.data;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.MediaData;
import fr.dl11.openmedia.events.BaseEvent;

/**
 * Event representing the creation of a new media item.
 *
 * <p>The {@code NewMediaEvent} class extends the {@link BaseEvent} class
 * and encapsulates information about a newly created media item,
 * including the media data and the associated {@link DataGraphManager}.
 */
public class NewMediaEvent extends BaseEvent {

    /**
     * The data of the newly created media item.
     */
    private final MediaData media;

    /**
     * The data graph manager associated with the media item.
     */
    private final DataGraphManager dataGraphManager;

    /**
     * Constructs a new {@code NewMediaEvent}.
     *
     * @param media            The data of the newly created media item. Must not be null.
     * @param dataGraphManager The data graph manager associated with the media item. Must not be null.
     */
    public NewMediaEvent(MediaData media, DataGraphManager dataGraphManager) {
        this.media = media;
        this.dataGraphManager = dataGraphManager;
    }

    /**
     * Returns the data of the newly created media item.
     *
     * @return The {@link MediaData} of the media item.
     */
    public MediaData getMedia() {
        return media;
    }

    /**
     * Returns the data graph manager associated with the media item.
     *
     * @return The {@link DataGraphManager} associated with the media item.
     */
    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}