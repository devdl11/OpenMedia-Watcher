package fr.dl11.openmedia.events.data.person;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.person.PersonOrganizationLink;
import fr.dl11.openmedia.events.BaseEvent;

/**
 * Event representing the update of a person-to-organization link.
 *
 * <p>The {@code UpdatePersonOrganizationLinkEvent} class extends the {@link BaseEvent} class
 * and encapsulates information about an updated link between a person and an organization,
 * including the updated link data and the associated {@link DataGraphManager}.
 */
public class UpdatePersonOrganizationLinkEvent extends BaseEvent {

    /**
     * The updated link data of the person-to-organization link.
     */
    private final PersonOrganizationLink personOrganizationLink;

    /**
     * The data graph manager associated with the updated person-to-organization link.
     */
    private final DataGraphManager dataGraphManager;

    /**
     * Constructs a new {@code UpdatePersonOrganizationLinkEvent}.
     *
     * @param personOrganizationLink The updated link data of the person-to-organization link. Must not be null.
     * @param dataGraphManager       The data graph manager associated with the updated person-to-organization link. Must not be null.
     */
    public UpdatePersonOrganizationLinkEvent(PersonOrganizationLink personOrganizationLink, DataGraphManager dataGraphManager) {
        this.personOrganizationLink = personOrganizationLink;
        this.dataGraphManager = dataGraphManager;
    }

    /**
     * Returns the updated link data of the person-to-organization link.
     *
     * @return The {@link PersonOrganizationLink} of the updated person-to-organization link.
     */
    public PersonOrganizationLink getPersonOrganizationLink() {
        return personOrganizationLink;
    }

    /**
     * Returns the data graph manager associated with the updated person-to-organization link.
     *
     * @return The {@link DataGraphManager} associated with the updated person-to-organization link.
     */
    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}