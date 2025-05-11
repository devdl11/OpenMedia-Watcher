package fr.dl11.openmedia.events.data;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.PublicationData;
import fr.dl11.openmedia.events.BaseEvent;

/**
 * Event representing the creation of a new publication.
 *
 * <p>The {@code NewPublicationEvent} class extends the {@link BaseEvent} class
 * and encapsulates information about a newly created publication,
 * including the publication data and the associated {@link DataGraphManager}.
 */
public class NewPublicationEvent extends BaseEvent {

    /**
     * The data of the newly created publication.
     */
    private final PublicationData publication;

    /**
     * The data graph manager associated with the publication.
     */
    private final DataGraphManager dataGraphManager;

    /**
     * Constructs a new {@code NewPublicationEvent}.
     *
     * @param publication      The data of the newly created publication. Must not be null.
     * @param dataGraphManager The data graph manager associated with the publication. Must not be null.
     */
    public NewPublicationEvent(PublicationData publication, DataGraphManager dataGraphManager) {
        this.publication = publication;
        this.dataGraphManager = dataGraphManager;
    }

    /**
     * Returns the data of the newly created publication.
     *
     * @return The {@link PublicationData} of the publication.
     */
    public PublicationData getPublication() {
        return publication;
    }

    /**
     * Returns the data graph manager associated with the publication.
     *
     * @return The {@link DataGraphManager} associated with the publication.
     */
    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}