package fr.dl11.openmedia.events.data.person;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.person.PersonMediaLinkData;
import fr.dl11.openmedia.events.BaseEvent;

public class UpdatePersonMediaLinkEvent extends BaseEvent {
    private final PersonMediaLinkData personMediaLinkData;
    private final DataGraphManager dataGraphManager;

    public UpdatePersonMediaLinkEvent(PersonMediaLinkData personMediaLinkData, DataGraphManager dataGraphManager) {
        this.personMediaLinkData = personMediaLinkData;
        this.dataGraphManager = dataGraphManager;
    }

    public PersonMediaLinkData getPersonMediaLinkData() {
        return personMediaLinkData;
    }

    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}
