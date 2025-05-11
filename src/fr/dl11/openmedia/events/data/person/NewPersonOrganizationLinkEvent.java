package fr.dl11.openmedia.events.data.person;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.person.PersonOrganizationLink;
import fr.dl11.openmedia.events.BaseEvent;

/**
 * Event representing the creation of a new person-to-organization link.
 *
 * <p>The {@code NewPersonOrganizationLinkEvent} class extends the {@link BaseEvent} class
 * and encapsulates information about a newly created link between a person and an organization,
 * including the link data and the associated {@link DataGraphManager}.
 */
public class NewPersonOrganizationLinkEvent extends BaseEvent {

    /**
     * The link data of the newly created person-to-organization link.
     */
    private final PersonOrganizationLink personOrganizationLink;

    /**
     * The data graph manager associated with the person-to-organization link.
     */
    private final DataGraphManager dataGraphManager;

    /**
     * Constructs a new {@code NewPersonOrganizationLinkEvent}.
     *
     * @param personOrganizationLink The link data of the newly created person-to-organization link. Must not be null.
     * @param dataGraphManager       The data graph manager associated with the person-to-organization link. Must not be null.
     */
    public NewPersonOrganizationLinkEvent(PersonOrganizationLink personOrganizationLink, DataGraphManager dataGraphManager) {
        this.personOrganizationLink = personOrganizationLink;
        this.dataGraphManager = dataGraphManager;
    }

    /**
     * Returns the link data of the newly created person-to-organization link.
     *
     * @return The {@link PersonOrganizationLink} of the person-to-organization link.
     */
    public PersonOrganizationLink getPersonOrganizationLink() {
        return personOrganizationLink;
    }

    /**
     * Returns the data graph manager associated with the person-to-organization link.
     *
     * @return The {@link DataGraphManager} associated with the person-to-organization link.
     */
    public DataGraphManager getDataGraphManager() {
        return dataGraphManager;
    }
}