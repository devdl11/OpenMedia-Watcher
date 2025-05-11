package fr.dl11.openmedia.events.data.person;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.person.PersonData;
import fr.dl11.openmedia.events.BaseEvent;

public class NewPersonEvent extends BaseEvent {
    private final PersonData person;
    private final DataGraphManager dataGraphManager;

    public NewPersonEvent(PersonData person, DataGraphManager dataGraphManager) {
        this.person = person;
        this.dataGraphManager = dataGraphManager;
    }

    public PersonData getPerson() {
        return person;
    }

    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}
