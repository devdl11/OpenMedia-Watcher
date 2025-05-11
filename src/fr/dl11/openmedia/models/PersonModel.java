package fr.dl11.openmedia.models;

import fr.dl11.openmedia.data.person.PersonData;

public class PersonModel {
    private final PersonData person;

    public PersonModel(PersonData person) {
        this.person = person;
    }

    public PersonData getPerson() {
        return person;
    }
}
