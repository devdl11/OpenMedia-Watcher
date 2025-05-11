package fr.dl11.openmedia.events.data.person;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.person.PersonOrganizationLink;
import fr.dl11.openmedia.events.BaseEvent;

public class NewPersonOrganizationLinkEvent extends BaseEvent {
    private final PersonOrganizationLink personOrganizationLink;
    private final DataGraphManager dataGraphManager;

    public NewPersonOrganizationLinkEvent(PersonOrganizationLink personOrganizationLink, DataGraphManager dataGraphManager) {
        this.personOrganizationLink = personOrganizationLink;
        this.dataGraphManager = dataGraphManager;
    }

    public PersonOrganizationLink getPersonOrganizationLink() {
        return personOrganizationLink;
    }

    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}
