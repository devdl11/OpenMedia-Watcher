package fr.dl11.openmedia.events.data.organization;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.organization.OrganizationMediaLink;
import fr.dl11.openmedia.events.BaseEvent;

/**
 * Event representing the update of an organization media link.
 *
 * <p>The {@code UpdateOrganizationMediaLinkEvent} class extends the {@link BaseEvent} class
 * and encapsulates information about an updated media link for an organization,
 * including the updated media link data and the associated {@link DataGraphManager}.
 */
public class UpdateOrganizationMediaLinkEvent extends BaseEvent {

    /**
     * The updated media link data of the organization.
     */
    private final OrganizationMediaLink organizationMediaLink;

    /**
     * The data graph manager associated with the updated organization media link.
     */
    private final DataGraphManager dataGraphManager;

    /**
     * Constructs a new {@code UpdateOrganizationMediaLinkEvent}.
     *
     * @param organizationMediaLink The updated media link data of the organization. Must not be null.
     * @param dataGraphManager      The data graph manager associated with the updated organization media link. Must not be null.
     */
    public UpdateOrganizationMediaLinkEvent(OrganizationMediaLink organizationMediaLink, DataGraphManager dataGraphManager) {
        this.organizationMediaLink = organizationMediaLink;
        this.dataGraphManager = dataGraphManager;
    }

    /**
     * Returns the updated media link data of the organization.
     *
     * @return The {@link OrganizationMediaLink} of the updated organization media link.
     */
    public OrganizationMediaLink getOrganizationMediaLink() {
        return organizationMediaLink;
    }

    /**
     * Returns the data graph manager associated with the updated organization media link.
     *
     * @return The {@link DataGraphManager} associated with the updated organization media link.
     */
    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}