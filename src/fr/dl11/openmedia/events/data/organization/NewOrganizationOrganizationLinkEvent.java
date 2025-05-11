package fr.dl11.openmedia.events.data.organization;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.organization.OrganizationOrganizationLink;
import fr.dl11.openmedia.events.BaseEvent;

/**
 * Event representing the creation of a new organization-to-organization link.
 *
 * <p>The {@code NewOrganizationOrganizationLinkEvent} class extends the {@link BaseEvent} class
 * and encapsulates information about a newly created link between two organizations,
 * including the link data and the associated {@link DataGraphManager}.
 */
public class NewOrganizationOrganizationLinkEvent extends BaseEvent {

    /**
     * The link data of the newly created organization-to-organization link.
     */
    private final OrganizationOrganizationLink organizationOrganizationLink;

    /**
     * The data graph manager associated with the organization-to-organization link.
     */
    private final DataGraphManager dataGraphManager;

    /**
     * Constructs a new {@code NewOrganizationOrganizationLinkEvent}.
     *
     * @param organizationOrganizationLink The link data of the newly created organization-to-organization link. Must not be null.
     * @param dataGraphManager             The data graph manager associated with the organization-to-organization link. Must not be null.
     */
    public NewOrganizationOrganizationLinkEvent(OrganizationOrganizationLink organizationOrganizationLink, DataGraphManager dataGraphManager) {
        this.organizationOrganizationLink = organizationOrganizationLink;
        this.dataGraphManager = dataGraphManager;
    }

    /**
     * Returns the link data of the newly created organization-to-organization link.
     *
     * @return The {@link OrganizationOrganizationLink} of the organization-to-organization link.
     */
    public OrganizationOrganizationLink getOrganizationOrganizationLink() {
        return organizationOrganizationLink;
    }

    /**
     * Returns the data graph manager associated with the organization-to-organization link.
     *
     * @return The {@link DataGraphManager} associated with the organization-to-organization link.
     */
    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}