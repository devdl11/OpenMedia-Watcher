package fr.dl11.openmedia.events.data.organization;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.organization.OrganizationMediaLink;
import fr.dl11.openmedia.events.BaseEvent;

public class UpdateOrganizationMediaLinkEvent extends BaseEvent {
    private final OrganizationMediaLink organizationMediaLink;
    private final DataGraphManager dataGraphManager;

    public UpdateOrganizationMediaLinkEvent(OrganizationMediaLink organizationMediaLink, DataGraphManager dataGraphManager) {
        this.organizationMediaLink = organizationMediaLink;
        this.dataGraphManager = dataGraphManager;
    }

    public OrganizationMediaLink getOrganizationMediaLink() {
        return organizationMediaLink;
    }

    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}
