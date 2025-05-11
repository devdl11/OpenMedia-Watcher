package fr.dl11.openmedia.models;

import fr.dl11.openmedia.data.person.PersonData;

/**
 * Represents a model for a person.
 *
 * <p>The {@code PersonModel} class provides a structure to manage
 * person-related data. It encapsulates a {@link PersonData} object
 * and provides access to it.</p>
 */
public class PersonModel {
    private final PersonData person;

    /**
     * Constructs a new {@code PersonModel} with the specified person data.
     *
     * @param person The {@link PersonData} associated with this model. Must not be null.
     */
    public PersonModel(PersonData person) {
        this.person = person;
    }

    /**
     * Retrieves the person data associated with this model.
     *
     * @return The {@link PersonData} object representing the person data.
     */
    public PersonData getPerson() {
        return person;
    }
}
