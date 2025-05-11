package fr.dl11.openmedia.events.data;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.MediaData;
import fr.dl11.openmedia.events.BaseEvent;

public class NewMediaEvent extends BaseEvent {
    private final MediaData media;
    private final DataGraphManager dataGraphManager;

    public NewMediaEvent(MediaData media, DataGraphManager dataGraphManager) {
        this.media = media;
        this.dataGraphManager = dataGraphManager;
    }

    public MediaData getMedia() {
        return media;
    }

    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}
