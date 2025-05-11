package fr.dl11.openmedia.events.data.person;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.person.PersonData;
import fr.dl11.openmedia.events.BaseEvent;

/**
 * Event representing the creation of a new person.
 *
 * <p>The {@code NewPersonEvent} class extends the {@link BaseEvent} class
 * and encapsulates information about a newly created person, including
 * their data and the associated {@link DataGraphManager}.
 */
public class NewPersonEvent extends BaseEvent {

    /**
     * The data of the newly created person.
     */
    private final PersonData person;

    /**
     * The data graph manager associated with the person.
     */
    private final DataGraphManager dataGraphManager;

    /**
     * Constructs a new {@code NewPersonEvent}.
     *
     * @param person           The data of the newly created person. Must not be null.
     * @param dataGraphManager The data graph manager associated with the person. Must not be null.
     */
    public NewPersonEvent(PersonData person, DataGraphManager dataGraphManager) {
        this.person = person;
        this.dataGraphManager = dataGraphManager;
    }

    /**
     * Returns the data of the newly created person.
     *
     * @return The {@link PersonData} of the person.
     */
    public PersonData getPerson() {
        return person;
    }

    /**
     * Returns the data graph manager associated with the person.
     *
     * @return The {@link DataGraphManager} associated with the person.
     */
    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}