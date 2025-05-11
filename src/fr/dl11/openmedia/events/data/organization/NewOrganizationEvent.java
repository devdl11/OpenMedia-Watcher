package fr.dl11.openmedia.events.data.organization;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.organization.OrganizationData;
import fr.dl11.openmedia.events.BaseEvent;

public class NewOrganizationEvent extends BaseEvent {
    private final OrganizationData organization;
    private final DataGraphManager dataGraphManager;

    public NewOrganizationEvent(OrganizationData organization, DataGraphManager dataGraphManager) {
        this.organization = organization;
        this.dataGraphManager = dataGraphManager;
    }

    public OrganizationData getOrganization() {
        return organization;
    }

    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}
