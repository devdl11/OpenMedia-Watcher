package fr.dl11.openmedia.events.data.organization;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.organization.OrganizationMediaLink;
import fr.dl11.openmedia.events.BaseEvent;

/**
 * Event representing the creation of a new organization media link.
 *
 * <p>The {@code NewOrganizationMediaLinkEvent} class extends the {@link BaseEvent} class
 * and encapsulates information about a newly created media link for an organization,
 * including the media link data and the associated {@link DataGraphManager}.
 */
public class NewOrganizationMediaLinkEvent extends BaseEvent {

    /**
     * The media link data of the newly created organization media link.
     */
    private final OrganizationMediaLink mediaLink;

    /**
     * The data graph manager associated with the organization media link.
     */
    private final DataGraphManager dataGraphManager;

    /**
     * Constructs a new {@code NewOrganizationMediaLinkEvent}.
     *
     * @param mediaLink        The media link data of the newly created organization media link. Must not be null.
     * @param dataGraphManager The data graph manager associated with the organization media link. Must not be null.
     */
    public NewOrganizationMediaLinkEvent(OrganizationMediaLink mediaLink, DataGraphManager dataGraphManager) {
        this.mediaLink = mediaLink;
        this.dataGraphManager = dataGraphManager;
    }

    /**
     * Returns the media link data of the newly created organization media link.
     *
     * @return The {@link OrganizationMediaLink} of the organization media link.
     */
    public OrganizationMediaLink getMediaLink() {
        return mediaLink;
    }

    /**
     * Returns the data graph manager associated with the organization media link.
     *
     * @return The {@link DataGraphManager} associated with the organization media link.
     */
    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}