package fr.dl11.openmedia.events.data;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.PublicationData;
import fr.dl11.openmedia.events.BaseEvent;

public class NewPublicationEvent extends BaseEvent {
    private final PublicationData publication;
    private final DataGraphManager dataGraphManager;

    public NewPublicationEvent(PublicationData publication, DataGraphManager dataGraphManager) {
        this.publication = publication;
        this.dataGraphManager = dataGraphManager;
    }

    public PublicationData getPublication() {
        return publication;
    }

    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}
