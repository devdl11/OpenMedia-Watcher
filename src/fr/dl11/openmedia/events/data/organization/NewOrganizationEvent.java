package fr.dl11.openmedia.events.data.organization;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.organization.OrganizationData;
import fr.dl11.openmedia.events.BaseEvent;

/**
 * Event representing the creation of a new organization.
 *
 * <p>The {@code NewOrganizationEvent} class extends the {@link BaseEvent} class
 * and encapsulates information about a newly created organization, including
 * its data and the associated {@link DataGraphManager}.
 */
public class NewOrganizationEvent extends BaseEvent {

    /**
     * The data of the newly created organization.
     */
    private final OrganizationData organization;

    /**
     * The data graph manager associated with the organization.
     */
    private final DataGraphManager dataGraphManager;

    /**
     * Constructs a new {@code NewOrganizationEvent}.
     *
     * @param organization     The data of the newly created organization. Must not be null.
     * @param dataGraphManager The data graph manager associated with the organization. Must not be null.
     */
    public NewOrganizationEvent(OrganizationData organization, DataGraphManager dataGraphManager) {
        this.organization = organization;
        this.dataGraphManager = dataGraphManager;
    }

    /**
     * Returns the data of the newly created organization.
     *
     * @return The {@link OrganizationData} of the organization.
     */
    public OrganizationData getOrganization() {
        return organization;
    }

    /**
     * Returns the data graph manager associated with the organization.
     *
     * @return The {@link DataGraphManager} associated with the organization.
     */
    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}