package fr.dl11.openmedia.events.data.person;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.person.PersonMediaLinkData;
import fr.dl11.openmedia.events.BaseEvent;

/**
 * Event representing the update of a person media link.
 *
 * <p>The {@code UpdatePersonMediaLinkEvent} class extends the {@link BaseEvent} class
 * and encapsulates information about an updated media link for a person,
 * including the updated media link data and the associated {@link DataGraphManager}.
 */
public class UpdatePersonMediaLinkEvent extends BaseEvent {

    /**
     * The updated media link data of the person.
     */
    private final PersonMediaLinkData personMediaLinkData;

    /**
     * The data graph manager associated with the updated person media link.
     */
    private final DataGraphManager dataGraphManager;

    /**
     * Constructs a new {@code UpdatePersonMediaLinkEvent}.
     *
     * @param personMediaLinkData The updated media link data of the person. Must not be null.
     * @param dataGraphManager    The data graph manager associated with the updated person media link. Must not be null.
     */
    public UpdatePersonMediaLinkEvent(PersonMediaLinkData personMediaLinkData, DataGraphManager dataGraphManager) {
        this.personMediaLinkData = personMediaLinkData;
        this.dataGraphManager = dataGraphManager;
    }

    /**
     * Returns the updated media link data of the person.
     *
     * @return The {@link PersonMediaLinkData} of the updated person media link.
     */
    public PersonMediaLinkData getPersonMediaLinkData() {
        return personMediaLinkData;
    }

    /**
     * Returns the data graph manager associated with the updated person media link.
     *
     * @return The {@link DataGraphManager} associated with the updated person media link.
     */
    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}