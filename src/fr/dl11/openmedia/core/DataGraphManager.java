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

/**
 * Manages the in-memory graph of media-related entities and their relationships.
 * This class stores and provides access to {@link MediaModel}, {@link PersonModel},
 * and {@link OrganizationModel} objects, as well as the links between them.
 * It also initializes data handlers for processing various types of data.
 * <p>
 * The DataGraphManager is responsible for:
 * - Storing media, persons, and organizations.
 * - Managing different types of links:
 * - Person to Media
 * - Person to Organization
 * - Organization to Media
 * - Organization to Organization
 * - Providing methods to add, remove, retrieve, and check the existence of entities and links.
 * - Adding publications to media entities.
 * <p>
 * Note: There is a pending task to split this class for improved organization and readability.
 */
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

    /**
     * Constructs a new DataGraphManager.
     * Initializes the collections for storing entities and links,
     * and instantiates the data handlers.
     */
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

    // --- Entity Existence Checks ---

    /**
     * Checks if a media entity with the given name exists.
     *
     * @param name The name of the media entity.
     * @return {@code true} if the media entity exists, {@code false} otherwise.
     */
    public boolean doesMediaExist(String name) {
        return medias.containsKey(name);
    }

    /**
     * Checks if a person entity with the given name exists.
     *
     * @param name The name of the person entity.
     * @return {@code true} if the person entity exists, {@code false} otherwise.
     */
    public boolean doesPersonExist(String name) {
        return persons.containsKey(name);
    }

    /**
     * Checks if an organization entity with the given name exists.
     *
     * @param name The name of the organization entity.
     * @return {@code true} if the organization entity exists, {@code false} otherwise.
     */
    public boolean doesOrganizationExist(String name) {
        return organizations.containsKey(name);
    }

    // --- Link Existence Checks ---

    /**
     * Checks if a link exists between the given person and media.
     *
     * @param person The person model.
     * @param media  The media model.
     * @return {@code true} if a link exists, {@code false} otherwise.
     */
    public boolean hasPersonMediaLink(PersonModel person, MediaModel media) {
        return personMediaLinks.stream().anyMatch(link -> link.isLinkBetween(person, media));
    }

    /**
     * Checks if a link exists between the given person and organization.
     *
     * @param person       The person model.
     * @param organization The organization model.
     * @return {@code true} if a link exists, {@code false} otherwise.
     */
    public boolean hasPersonOrganizationLink(PersonModel person, OrganizationModel organization) {
        return personOrganizationLinks.stream().anyMatch(link -> link.isLinkBetween(person, organization));
    }

    /**
     * Checks if a link exists between the given organization and media.
     *
     * @param organization The organization model.
     * @param media        The media model.
     * @return {@code true} if a link exists, {@code false} otherwise.
     */
    public boolean hasOrganizationMediaLink(OrganizationModel organization, MediaModel media) {
        return organizationMediaLinks.stream().anyMatch(link -> link.isLinkBetween(organization, media));
    }

    /**
     * Checks if a link exists between the given source and target organizations.
     *
     * @param source The source organization model.
     * @param target The target organization model.
     * @return {@code true} if a link exists, {@code false} otherwise.
     */
    public boolean hasOrganizationOrganizationLink(OrganizationModel source, OrganizationModel target) {
        return organizationOrganizationLinks.stream().anyMatch(link -> link.isLinkBetween(source, target));
    }

    // --- Entity Getters ---

    /**
     * Retrieves a person entity by its name.
     * Asserts that the person exists.
     *
     * @param name The name of the person.
     * @return The {@link PersonModel} associated with the name.
     */
    public PersonModel getPerson(String name) {
        assert doesPersonExist(name);
        return persons.get(name);
    }

    /**
     * Retrieves a media entity by its name.
     * Asserts that the media exists.
     *
     * @param name The name of the media.
     * @return The {@link MediaModel} associated with the name.
     */
    public MediaModel getMedia(String name) {
        assert doesMediaExist(name);
        return medias.get(name);
    }

    /**
     * Retrieves an organization entity by its name.
     * Asserts that the organization exists.
     *
     * @param name The name of the organization.
     * @return The {@link OrganizationModel} associated with the name.
     */
    public OrganizationModel getOrganization(String name) {
        assert doesOrganizationExist(name);
        return organizations.get(name);
    }

    /**
     * Retrieves an unmodifiable collection of all media entities.
     *
     * @return A collection of {@link MediaModel}.
     */
    public Collection<MediaModel> getAllMedias() {
        return Collections.unmodifiableCollection(medias.values());
    }

    /**
     * Retrieves an unmodifiable collection of all person entities.
     *
     * @return A collection of {@link PersonModel}.
     */
    public Collection<PersonModel> getAllPersons() {
        return Collections.unmodifiableCollection(persons.values());
    }

    /**
     * Retrieves an unmodifiable collection of all organization entities.
     *
     * @return A collection of {@link OrganizationModel}.
     */
    public Collection<OrganizationModel> getAllOrganizations() {
        return Collections.unmodifiableCollection(organizations.values());
    }

    //  ----- Entity Modification Callbacks (triggered by handlers) -----

    /**
     * Adds a new media entity from {@link MediaData}.
     * If a media with the same name already exists, the operation is skipped.
     *
     * @param data The {@link MediaData} to create the media entity from.
     */
    public void onAddMedia(MediaData data) {
        if (medias.containsKey(data.getName())) return;

        MediaModel media = new MediaModel(data);
        medias.put(data.getName(), media);
    }

    /**
     * Adds a new person entity from {@link PersonData}.
     * If a person with the same name already exists, the operation is skipped.
     *
     * @param person The {@link PersonData} to create the person entity from.
     */
    public void onAddPerson(PersonData person) {
        if (persons.containsKey(person.getName())) return;

        PersonModel personModel = new PersonModel(person);
        persons.put(person.getName(), personModel);
    }

    /**
     * Adds a new organization entity from {@link OrganizationData}.
     * If an organization with the same name already exists, the operation is skipped.
     *
     * @param data The {@link OrganizationData} to create the organization entity from.
     */
    public void onAddOrganization(OrganizationData data) {
        if (organizations.containsKey(data.getName())) return;

        OrganizationModel organizationModel = new OrganizationModel(data);
        organizations.put(data.getName(), organizationModel);
    }

    /**
     * Removes a media entity by its name.
     * Also removes all links associated with this media.
     * If the media does not exist, the operation is skipped.
     *
     * @param name The name of the media to remove.
     */
    public void onRemoveMedia(String name) {
        if (!medias.containsKey(name)) return;

        MediaModel media = medias.remove(name);
        personMediaLinks.removeIf(link -> link.target().equals(media));
        organizationMediaLinks.removeIf(link -> link.target().equals(media));
    }

    /**
     * Removes a person entity by its name.
     * Also removes all links associated with this person.
     * If the person does not exist, the operation is skipped.
     *
     * @param name The name of the person to remove.
     */
    public void onRemovePerson(String name) {
        if (!persons.containsKey(name)) return;

        PersonModel person = persons.remove(name);
        personMediaLinks.removeIf(link -> link.source().equals(person));
        personOrganizationLinks.removeIf(link -> link.source().equals(person));
    }

    /**
     * Removes an organization entity by its name.
     * Also removes all links where this organization is a source or a target.
     * If the organization does not exist, the operation is skipped.
     *
     * @param name The name of the organization to remove.
     */
    public void onRemoveOrganization(String name) {
        if (!organizations.containsKey(name)) return;

        OrganizationModel organization = organizations.remove(name);
        organizationMediaLinks.removeIf(link -> link.source().equals(organization));
        organizationOrganizationLinks.removeIf(link -> link.target().equals(organization) || link.source().equals(organization)); // Corrected to remove links where it's a source too
    }

    /**
     * Adds a link between a person and a media.
     * If the link already exists, the operation is skipped.
     *
     * @param person The source {@link PersonModel}.
     * @param media  The target {@link MediaModel}.
     * @param value  The value or description of the link.
     * @param type   The {@link EqualityType} of the link.
     */
    public void onAddPersonMediaLink(PersonModel person, MediaModel media, String value, EqualityType type) {
        if (hasPersonMediaLink(person, media)) return; // Changed to use existing checker

        EntityLink<PersonModel, MediaModel> link = new EntityLink<>(person, media, value, type);
        personMediaLinks.add(link);
    }

    /**
     * Updates an existing link between a person and a media.
     * This is achieved by removing the old link (if any) and adding a new one.
     *
     * @param person The source {@link PersonModel}.
     * @param media  The target {@link MediaModel}.
     * @param value  The new value or description of the link.
     * @param type   The new {@link EqualityType} of the link.
     */
    public void onUpdatePersonMediaLink(PersonModel person, MediaModel media, String value, EqualityType type) {
        personMediaLinks.removeIf(link -> link.isLinkBetween(person, media));

        EntityLink<PersonModel, MediaModel> link = new EntityLink<>(person, media, value, type);
        personMediaLinks.add(link);
    }

    /**
     * Removes a link between a person and a media.
     *
     * @param person The source {@link PersonModel}.
     * @param media  The target {@link MediaModel}.
     */
    public void onRemovePersonMediaLink(PersonModel person, MediaModel media) {
        personMediaLinks.removeIf(link -> link.isLinkBetween(person, media));
    }

    /**
     * Adds a link between a person and an organization.
     * If the link already exists, the operation is skipped.
     *
     * @param person       The source {@link PersonModel}.
     * @param organization The target {@link OrganizationModel}.
     * @param value        The value or description of the link.
     * @param type         The {@link EqualityType} of the link.
     */
    public void onAddPersonOrganizationLink(PersonModel person, OrganizationModel organization, String value, EqualityType type) {
        if (hasPersonOrganizationLink(person, organization)) return; // Changed to use existing checker

        EntityLink<PersonModel, OrganizationModel> link = new EntityLink<>(person, organization, value, type);
        personOrganizationLinks.add(link);
    }

    /**
     * Updates an existing link between a person and an organization.
     * This is achieved by removing the old link (if any) and adding a new one.
     *
     * @param person       The source {@link PersonModel}.
     * @param organization The target {@link OrganizationModel}.
     * @param value        The new value or description of the link.
     * @param type         The new {@link EqualityType} of the link.
     */
    public void onUpdatePersonOrganizationLink(PersonModel person, OrganizationModel organization, String value, EqualityType type) {
        personOrganizationLinks.removeIf(link -> link.isLinkBetween(person, organization));

        EntityLink<PersonModel, OrganizationModel> link = new EntityLink<>(person, organization, value, type);
        personOrganizationLinks.add(link);
    }

    /**
     * Removes a link between a person and an organization.
     *
     * @param person       The source {@link PersonModel}.
     * @param organization The target {@link OrganizationModel}.
     */
    public void onRemovePersonOrganizationLink(PersonModel person, OrganizationModel organization) {
        personOrganizationLinks.removeIf(link -> link.isLinkBetween(person, organization));
    }

    /**
     * Adds a link between an organization and a media.
     * If the link already exists, the operation is skipped.
     *
     * @param organization The source {@link OrganizationModel}.
     * @param media        The target {@link MediaModel}.
     * @param value        The value or description of the link.
     * @param type         The {@link EqualityType} of the link.
     */
    public void onAddOrganizationMediaLink(OrganizationModel organization, MediaModel media, String value, EqualityType type) {
        if (hasOrganizationMediaLink(organization, media)) return; // Changed to use existing checker

        EntityLink<OrganizationModel, MediaModel> link = new EntityLink<>(organization, media, value, type);
        organizationMediaLinks.add(link);
    }

    /**
     * Updates an existing link between an organization and a media.
     * This is achieved by removing the old link (if any) and adding a new one.
     *
     * @param organization The source {@link OrganizationModel}.
     * @param media        The target {@link MediaModel}.
     * @param value        The new value or description of the link.
     * @param type         The new {@link EqualityType} of the link.
     */
    public void onUpdateOrganizationMediaLink(OrganizationModel organization, MediaModel media, String value, EqualityType type) {
        organizationMediaLinks.removeIf(link -> link.isLinkBetween(organization, media));

        EntityLink<OrganizationModel, MediaModel> link = new EntityLink<>(organization, media, value, type);
        organizationMediaLinks.add(link);
    }

    /**
     * Removes a link between an organization and a media.
     *
     * @param organization The source {@link OrganizationModel}.
     * @param media        The target {@link MediaModel}.
     */
    public void onRemoveOrganizationMediaLink(OrganizationModel organization, MediaModel media) {
        organizationMediaLinks.removeIf(link -> link.isLinkBetween(organization, media));
    }

    /**
     * Adds a link between a source organization and a target organization.
     * If the link already exists, the operation is skipped.
     *
     * @param source The source {@link OrganizationModel}.
     * @param target The target {@link OrganizationModel}.
     * @param value  The value or description of the link.
     * @param type   The {@link EqualityType} of the link.
     */
    public void onAddOrganizationOrganizationLink(OrganizationModel source, OrganizationModel target, String value, EqualityType type) {
        if (hasOrganizationOrganizationLink(source, target)) return; // Changed to use existing checker

        EntityLink<OrganizationModel, OrganizationModel> link = new EntityLink<>(source, target, value, type);
        organizationOrganizationLinks.add(link);
    }

    /**
     * Updates an existing link between a source organization and a target organization.
     * This is achieved by removing the old link (if any) and adding a new one.
     *
     * @param source The source {@link OrganizationModel}.
     * @param target The target {@link OrganizationModel}.
     * @param value  The new value or description of the link.
     * @param type   The new {@link EqualityType} of the link.
     */
    public void onUpdateOrganizationOrganizationLink(OrganizationModel source, OrganizationModel target, String value, EqualityType type) {
        organizationOrganizationLinks.removeIf(link -> link.isLinkBetween(source, target));

        EntityLink<OrganizationModel, OrganizationModel> link = new EntityLink<>(source, target, value, type);
        organizationOrganizationLinks.add(link);
    }

    /**
     * Removes a link between a source organization and a target organization.
     *
     * @param source The source {@link OrganizationModel}.
     * @param target The target {@link OrganizationModel}.
     */
    public void onRemoveOrganizationOrganizationLink(OrganizationModel source, OrganizationModel target) {
        organizationOrganizationLinks.removeIf(link -> link.isLinkBetween(source, target));
    }

    /**
     * Adds a publication to a media entity.
     * Asserts that both mediaModel and publication are not null.
     *
     * @param mediaModel  The {@link MediaModel} to add the publication to.
     * @param publication The {@link PublicationData} to add.
     */
    public void onAddPublication(MediaModel mediaModel, PublicationData publication) {
        assert mediaModel != null && publication != null;
        mediaModel.addPublication(publication);
    }

    // --- Link Getters ---

    /**
     * Retrieves all media links for a given person.
     *
     * @param person The {@link PersonModel}.
     * @return A collection of {@link EntityLink} between the person and media.
     */
    public Collection<EntityLink<PersonModel, MediaModel>> getMediaLinksForPerson(PersonModel person) {
        return personMediaLinks.stream()
                .filter(link -> link.source().equals(person))
                .toList();
    }

    /**
     * Retrieves all person links for a given media.
     *
     * @param media The {@link MediaModel}.
     * @return A collection of {@link EntityLink} between persons and the media.
     */
    public Collection<EntityLink<PersonModel, MediaModel>> getPersonLinksForMedia(MediaModel media) {
        return personMediaLinks.stream()
                .filter(link -> link.target().equals(media))
                .toList();
    }

    /**
     * Retrieves all organization links for a given person.
     *
     * @param person The {@link PersonModel}.
     * @return A collection of {@link EntityLink} between the person and organizations.
     */
    public Collection<EntityLink<PersonModel, OrganizationModel>> getOrganizationLinksForPerson(PersonModel person) {
        return personOrganizationLinks.stream()
                .filter(link -> link.source().equals(person))
                .toList();
    }

    /**
     * Retrieves all person links for a given organization.
     *
     * @param organization The {@link OrganizationModel}.
     * @return A collection of {@link EntityLink} between persons and the organization.
     */
    public Collection<EntityLink<PersonModel, OrganizationModel>> getPersonLinksForOrganization(OrganizationModel organization) {
        return personOrganizationLinks.stream()
                .filter(link -> link.target().equals(organization))
                .toList();
    }

    /**
     * Retrieves all media links for a given organization.
     *
     * @param organization The {@link OrganizationModel}.
     * @return A collection of {@link EntityLink} between the organization and media.
     */
    public Collection<EntityLink<OrganizationModel, MediaModel>> getMediaLinksForOrganization(OrganizationModel organization) {
        return organizationMediaLinks.stream()
                .filter(link -> link.source().equals(organization))
                .toList();
    }

    /**
     * Retrieves all organization links for a given media.
     *
     * @param media The {@link MediaModel}.
     * @return A collection of {@link EntityLink} between organizations and the media.
     */
    public Collection<EntityLink<OrganizationModel, MediaModel>> getOrganizationLinksForMedia(MediaModel media) {
        return organizationMediaLinks.stream()
                .filter(link -> link.target().equals(media))
                .toList();
    }

    /**
     * Retrieves all organization links where the given organization is the source.
     *
     * @param organization The source {@link OrganizationModel}.
     * @return A collection of {@link EntityLink} between the source organization and other organizations.
     */
    public Collection<EntityLink<OrganizationModel, OrganizationModel>> getOrganizationLinksForOrganization(OrganizationModel organization) {
        return organizationOrganizationLinks.stream()
                .filter(link -> link.source().equals(organization))
                .toList();
    }
}