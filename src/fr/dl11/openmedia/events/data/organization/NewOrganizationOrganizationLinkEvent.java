package fr.dl11.openmedia.events.data.organization;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.organization.OrganizationOrganizationLink;
import fr.dl11.openmedia.events.BaseEvent;

public class NewOrganizationOrganizationLinkEvent extends BaseEvent {
    private final OrganizationOrganizationLink organizationOrganizationLink;
    private final DataGraphManager dataGraphManager;

    public NewOrganizationOrganizationLinkEvent(OrganizationOrganizationLink organizationOrganizationLink, DataGraphManager dataGraphManager) {
        this.organizationOrganizationLink = organizationOrganizationLink;
        this.dataGraphManager = dataGraphManager;
    }

    public OrganizationOrganizationLink getOrganizationOrganizationLink() {
        return organizationOrganizationLink;
    }

    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}
