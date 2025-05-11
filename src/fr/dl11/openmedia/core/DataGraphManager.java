package fr.dl11.openmedia.core;

import fr.dl11.openmedia.common.types.EqualityType;
import fr.dl11.openmedia.core.handlers.MediaDataHandler;
import fr.dl11.openmedia.core.handlers.OrganizationDataHandler;
import fr.dl11.openmedia.core.handlers.PersonDataHandler;
import fr.dl11.openmedia.core.handlers.PublicationDataHandler;
import fr.dl11.openmedia.data.MediaData;
import fr.dl11.openmedia.data.PublicationData;
import fr.dl11.openmedia.data.organization.OrganizationData;
import fr.dl11.openmedia.data.person.PersonData;
import fr.dl11.openmedia.models.EntityLink;
import fr.dl11.openmedia.models.MediaModel;
import fr.dl11.openmedia.models.OrganizationModel;
import fr.dl11.openmedia.models.PersonModel;

import java.util.*;

// TODO: Split this class into multiple classes for better organization and readability
public class DataGraphManager {
    private final Map<String, MediaModel> medias;
    private final Map<String, PersonModel> persons;
    private final Map<String, OrganizationModel> organizations;

    private final List<EntityLink<PersonModel, MediaModel>> personMediaLinks;
    private final List<EntityLink<PersonModel, OrganizationModel>> personOrganizationLinks;

    private final List<EntityLink<OrganizationModel, MediaModel>> organizationMediaLinks;
    private final List<EntityLink<OrganizationModel, OrganizationModel>> organizationOrganizationLinks;

    private final MediaDataHandler mediaDataHandler;
    private final OrganizationDataHandler organizationDataHandler;
    private final PersonDataHandler personDataHandler;
    private final PublicationDataHandler publicationDataHandler;

    public DataGraphManager() {
        medias = new HashMap<>();
        persons = new HashMap<>();
        organizations = new HashMap<>();

        personMediaLinks = new ArrayList<>();
        personOrganizationLinks = new ArrayList<>();
        organizationMediaLinks = new ArrayList<>();
        organizationOrganizationLinks = new ArrayList<>();

        mediaDataHandler = new MediaDataHandler();
        organizationDataHandler = new OrganizationDataHandler();
        personDataHandler = new PersonDataHandler();
        publicationDataHandler = new PublicationDataHandler();
    }

    // ---
    public boolean doesMediaExist(String name) {
        return medias.containsKey(name);
    }

    public boolean doesPersonExist(String name) {
        return persons.containsKey(name);
    }

    public boolean doesOrganizationExist(String name) {
        return organizations.containsKey(name);
    }

    // ---

    public boolean hasPersonMediaLink(PersonModel person, MediaModel media) {
        return personMediaLinks.stream().anyMatch(link -> link.isLinkBetween(person, media));
    }

    public boolean hasPersonOrganizationLink(PersonModel person, OrganizationModel organization) {
        return personOrganizationLinks.stream().anyMatch(link -> link.isLinkBetween(person, organization));
    }

    public boolean hasOrganizationMediaLink(OrganizationModel organization, MediaModel media) {
        return organizationMediaLinks.stream().anyMatch(link -> link.isLinkBetween(organization, media));
    }

    public boolean hasOrganizationOrganizationLink(OrganizationModel source, OrganizationModel target) {
        return organizationOrganizationLinks.stream().anyMatch(link -> link.isLinkBetween(source, target));
    }

    // ---

    public PersonModel getPerson(String name) {
        assert doesPersonExist(name);
        return persons.get(name);
    }

    public MediaModel getMedia(String name) {
        assert doesMediaExist(name);
        return medias.get(name);
    }

    public OrganizationModel getOrganization(String name) {
        assert doesOrganizationExist(name);
        return organizations.get(name);
    }

    public Collection<MediaModel> getAllMedias() {
        return Collections.unmodifiableCollection(medias.values());
    }

    public Collection<PersonModel> getAllPersons() {
        return Collections.unmodifiableCollection(persons.values());
    }

    public Collection<OrganizationModel> getAllOrganizations() {
        return Collections.unmodifiableCollection(organizations.values());
    }

    //  -----

    public void onAddMedia(MediaData data) {
        if (medias.containsKey(data.getName())) return;

        MediaModel media = new MediaModel(data);
        medias.put(data.getName(), media);
    }

    public void onAddPerson(PersonData person) {
        if (persons.containsKey(person.getName())) return;

        PersonModel personModel = new PersonModel(person);
        persons.put(person.getName(), personModel);
    }

    public void onAddOrganization(OrganizationData data) {
        if (organizations.containsKey(data.getName())) return;

        OrganizationModel organizationModel = new OrganizationModel(data);
        organizations.put(data.getName(), organizationModel);
    }

    public void onRemoveMedia(String name) {
        if (!medias.containsKey(name)) return;

        MediaModel media = medias.remove(name);
        personMediaLinks.removeIf(link -> link.target().equals(media));
        organizationMediaLinks.removeIf(link -> link.target().equals(media));
    }

    public void onRemovePerson(String name) {
        if (!persons.containsKey(name)) return;

        PersonModel person = persons.remove(name);
        personMediaLinks.removeIf(link -> link.source().equals(person));
        personOrganizationLinks.removeIf(link -> link.source().equals(person));
    }

    public void onRemoveOrganization(String name) {
        if (!organizations.containsKey(name)) return;

        OrganizationModel organization = organizations.remove(name);
        organizationMediaLinks.removeIf(link -> link.source().equals(organization));
        organizationOrganizationLinks.removeIf(link -> link.target().equals(organization));
    }

    public void onAddPersonMediaLink(PersonModel person, MediaModel media, String value, EqualityType type) {
        if (personMediaLinks.stream().anyMatch(link -> link.isLinkBetween(person, media))) return;

        EntityLink<PersonModel, MediaModel> link = new EntityLink<>(person, media, value, type);
        personMediaLinks.add(link);
    }

    public void onUpdatePersonMediaLink(PersonModel person, MediaModel media, String value, EqualityType type) {
        personMediaLinks.removeIf(link -> link.isLinkBetween(person, media));

        EntityLink<PersonModel, MediaModel> link = new EntityLink<>(person, media, value, type);
        personMediaLinks.add(link);
    }

    public void onRemovePersonMediaLink(PersonModel person, MediaModel media) {
        personMediaLinks.removeIf(link -> link.isLinkBetween(person, media));
    }

    public void onAddPersonOrganizationLink(PersonModel person, OrganizationModel organization, String value, EqualityType type) {
        if (personOrganizationLinks.stream().anyMatch(link -> link.isLinkBetween(person, organization))) return;

        EntityLink<PersonModel, OrganizationModel> link = new EntityLink<>(person, organization, value, type);
        personOrganizationLinks.add(link);
    }

    public void onUpdatePersonOrganizationLink(PersonModel person, OrganizationModel organization, String value, EqualityType type) {
        personOrganizationLinks.removeIf(link -> link.isLinkBetween(person, organization));

        EntityLink<PersonModel, OrganizationModel> link = new EntityLink<>(person, organization, value, type);
        personOrganizationLinks.add(link);
    }

    public void onRemovePersonOrganizationLink(PersonModel person, OrganizationModel organization) {
        personOrganizationLinks.removeIf(link -> link.isLinkBetween(person, organization));
    }

    public void onAddOrganizationMediaLink(OrganizationModel organization, MediaModel media, String value, EqualityType type) {
        if (organizationMediaLinks.stream().anyMatch(link -> link.isLinkBetween(organization, media))) return;

        EntityLink<OrganizationModel, MediaModel> link = new EntityLink<>(organization, media, value, type);
        organizationMediaLinks.add(link);
    }

    public void onUpdateOrganizationMediaLink(OrganizationModel organization, MediaModel media, String value, EqualityType type) {
        organizationMediaLinks.removeIf(link -> link.isLinkBetween(organization, media));

        EntityLink<OrganizationModel, MediaModel> link = new EntityLink<>(organization, media, value, type);
        organizationMediaLinks.add(link);
    }

    public void onRemoveOrganizationMediaLink(OrganizationModel organization, MediaModel media) {
        organizationMediaLinks.removeIf(link -> link.isLinkBetween(organization, media));
    }

    public void onAddOrganizationOrganizationLink(OrganizationModel source, OrganizationModel target, String value, EqualityType type) {
        if (organizationOrganizationLinks.stream().anyMatch(link -> link.isLinkBetween(source, target))) return;

        EntityLink<OrganizationModel, OrganizationModel> link = new EntityLink<>(source, target, value, type);
        organizationOrganizationLinks.add(link);
    }

    public void onUpdateOrganizationOrganizationLink(OrganizationModel source, OrganizationModel target, String value, EqualityType type) {
        organizationOrganizationLinks.removeIf(link -> link.isLinkBetween(source, target));

        EntityLink<OrganizationModel, OrganizationModel> link = new EntityLink<>(source, target, value, type);
        organizationOrganizationLinks.add(link);
    }

    public void onRemoveOrganizationOrganizationLink(OrganizationModel source, OrganizationModel target) {
        organizationOrganizationLinks.removeIf(link -> link.isLinkBetween(source, target));
    }

    public void onAddPublication(MediaModel mediaModel, PublicationData publication) {
        assert mediaModel != null && publication != null;
        mediaModel.addPublication(publication);
    }

    // ---

    public Collection<EntityLink<PersonModel, MediaModel>> getMediaLinksForPerson(PersonModel person) {
        return personMediaLinks.stream()
                .filter(link -> link.source().equals(person))
                .toList();
    }

    public Collection<EntityLink<PersonModel, MediaModel>> getPersonLinksForMedia(MediaModel media) {
        return personMediaLinks.stream()
                .filter(link -> link.target().equals(media))
                .toList();
    }

    public Collection<EntityLink<PersonModel, OrganizationModel>> getOrganizationLinksForPerson(PersonModel person) {
        return personOrganizationLinks.stream()
                .filter(link -> link.source().equals(person))
                .toList();
    }

    public Collection<EntityLink<PersonModel, OrganizationModel>> getPersonLinksForOrganization(OrganizationModel organization) {
        return personOrganizationLinks.stream()
                .filter(link -> link.target().equals(organization))
                .toList();
    }

    public Collection<EntityLink<OrganizationModel, MediaModel>> getMediaLinksForOrganization(OrganizationModel organization) {
        return organizationMediaLinks.stream()
                .filter(link -> link.source().equals(organization))
                .toList();
    }

    public Collection<EntityLink<OrganizationModel, MediaModel>> getOrganizationLinksForMedia(MediaModel media) {
        return organizationMediaLinks.stream()
                .filter(link -> link.target().equals(media))
                .toList();
    }

    public Collection<EntityLink<OrganizationModel, OrganizationModel>> getOrganizationLinksForOrganization(OrganizationModel organization) {
        return organizationOrganizationLinks.stream()
                .filter(link -> link.source().equals(organization))
                .toList();
    }
}
