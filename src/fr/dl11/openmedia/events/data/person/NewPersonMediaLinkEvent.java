package fr.dl11.openmedia.events.data.person;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.person.PersonMediaLinkData;
import fr.dl11.openmedia.events.BaseEvent;

/**
 * Event representing the creation of a new person media link.
 *
 * <p>The {@code NewPersonMediaLinkEvent} class extends the {@link BaseEvent} class
 * and encapsulates information about a newly created media link for a person,
 * including the media link data and the associated {@link DataGraphManager}.
 */
public class NewPersonMediaLinkEvent extends BaseEvent {

    /**
     * The media link data of the newly created person media link.
     */
    private final PersonMediaLinkData personMediaLinkData;

    /**
     * The data graph manager associated with the person media link.
     */
    private final DataGraphManager dataGraphManager;

    /**
     * Constructs a new {@code NewPersonMediaLinkEvent}.
     *
     * @param personMediaLinkData The media link data of the newly created person media link. Must not be null.
     * @param dataGraphManager    The data graph manager associated with the person media link. Must not be null.
     */
    public NewPersonMediaLinkEvent(PersonMediaLinkData personMediaLinkData, DataGraphManager dataGraphManager) {
        this.personMediaLinkData = personMediaLinkData;
        this.dataGraphManager = dataGraphManager;
    }

    /**
     * Returns the media link data of the newly created person media link.
     *
     * @return The {@link PersonMediaLinkData} of the person media link.
     */
    public PersonMediaLinkData getPersonMediaLinkData() {
        return personMediaLinkData;
    }

    /**
     * Returns the data graph manager associated with the person media link.
     *
     * @return The {@link DataGraphManager} associated with the person media link.
     */
    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}