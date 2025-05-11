package fr.dl11.openmedia.events.data.organization;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.organization.OrganizationOrganizationLink;
import fr.dl11.openmedia.events.BaseEvent;

/**
 * Event representing the update of an organization-to-organization link.
 *
 * <p>The {@code UpdateOrganizationOrganizationLinkEvent} class extends the {@link BaseEvent} class
 * and encapsulates information about an updated link between two organizations,
 * including the updated link data and the associated {@link DataGraphManager}.
 */
public class UpdateOrganizationOrganizationLinkEvent extends BaseEvent {

    /**
     * The updated link data of the organization-to-organization link.
     */
    private final OrganizationOrganizationLink organizationOrganizationLink;

    /**
     * The data graph manager associated with the updated organization-to-organization link.
     */
    private final DataGraphManager dataGraphManager;

    /**
     * Constructs a new {@code UpdateOrganizationOrganizationLinkEvent}.
     *
     * @param organizationOrganizationLink The updated link data of the organization-to-organization link. Must not be null.
     * @param dataGraphManager             The data graph manager associated with the updated organization-to-organization link. Must not be null.
     */
    public UpdateOrganizationOrganizationLinkEvent(OrganizationOrganizationLink organizationOrganizationLink, DataGraphManager dataGraphManager) {
        this.organizationOrganizationLink = organizationOrganizationLink;
        this.dataGraphManager = dataGraphManager;
    }

    /**
     * Returns the updated link data of the organization-to-organization link.
     *
     * @return The {@link OrganizationOrganizationLink} of the updated organization-to-organization link.
     */
    public OrganizationOrganizationLink getOrganizationOrganizationLink() {
        return organizationOrganizationLink;
    }

    /**
     * Returns the data graph manager associated with the updated organization-to-organization link.
     *
     * @return The {@link DataGraphManager} associated with the updated organization-to-organization link.
     */
    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}