package fr.dl11.openmedia.console.commands;

import fr.dl11.openmedia.common.types.*;
import fr.dl11.openmedia.console.Console;
import fr.dl11.openmedia.console.components.ICommand;
import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.data.MediaData;
import fr.dl11.openmedia.data.PublicationData;
import fr.dl11.openmedia.data.organization.OrganizationData;
import fr.dl11.openmedia.data.organization.OrganizationMediaLink;
import fr.dl11.openmedia.data.organization.OrganizationOrganizationLink;
import fr.dl11.openmedia.data.person.PersonData;
import fr.dl11.openmedia.data.person.PersonMediaLinkData;
import fr.dl11.openmedia.data.person.PersonOrganizationLink;
import fr.dl11.openmedia.events.data.NewMediaEvent;
import fr.dl11.openmedia.events.data.NewPublicationEvent;
import fr.dl11.openmedia.events.data.organization.*;
import fr.dl11.openmedia.events.data.person.*;
import fr.dl11.openmedia.models.MediaModel;
import fr.dl11.openmedia.models.OrganizationModel;
import fr.dl11.openmedia.models.PersonModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Implements the {@link ICommand} interface to provide a console command
 * for simulating the creation and publication of various data events within the system.
 * This command allows users to interactively generate new data entities (like publications,
 * media, persons, organizations) and links between them, which are then published
 * to the {@link EventBus}.
 * <p>
 * The command guides the user through a menu-driven interface to select the type
 * of event to simulate and then prompts for the necessary information to construct
 * the event data. It utilizes reflection via the {@code setFinalField} method to
 * populate data objects that may have private final fields and no direct setters.
 * </p>
 */
public class SimulateEventCommand implements ICommand {

    /**
     * Executes the simulate event command.
     * Presents a menu to the user for selecting which type of event to simulate.
     * Based on the user's choice, it delegates to a specific simulation method.
     *
     * @param args    Command line arguments (not used by this command).
     * @param graph   The {@link DataGraphManager} instance, used for context (e.g., selecting existing entities).
     * @param console The {@link Console} instance, providing access to the scanner for user input.
     */
    @Override
    public void execute(String[] args, DataGraphManager graph, Console console) {
        System.out.println("Select an event type to simulate:");
        System.out.println(" 1: New Publication (Article, Interview, etc.)");
        System.out.println(" 2: New Media");
        System.out.println(" 3: New Person");
        System.out.println(" 4: New Organization");
        System.out.println(" 5: New/Update Person-Media Link");
        System.out.println(" 6: New/Update Person-Organization Link");
        System.out.println(" 7: New/Update Organization-Media Link");
        System.out.println(" 8: New/Update Organization-Organization Link");
        System.out.println(" 0: Back to main menu");
        System.out.print("> ");

        String choice = console.scanner.nextLine();
        switch (choice) {
            case "1" -> simulateNewPublication(graph, console);
            case "2" -> simulateNewMedia(graph, console);
            case "3" -> simulateNewPerson(graph, console);
            case "4" -> simulateNewOrganization(graph, console);
            case "5" -> simulatePersonMediaLink(graph, console);
            case "6" -> simulatePersonOrganizationLink(graph, console);
            case "7" -> simulateOrganizationMediaLink(graph, console);
            case "8" -> simulateOrganizationOrganizationLink(graph, console);
            case "0" -> {
                return;
            } // Return to the main console loop
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    /**
     * Prompts the user to select a value from a given Enum type.
     * Displays the available enum constants and re-prompts on invalid input.
     *
     * @param scanner   The {@link Scanner} instance for reading user input.
     * @param enumClass The {@link Class} object of the Enum from which to select.
     * @param prompt    The message to display to the user before listing enum options.
     * @param <T>       The Enum type.
     * @return The Enum constant selected by the user.
     */
    private <T extends Enum<T>> T selectEnum(Scanner scanner, Class<T> enumClass, String prompt) {
        System.out.println(prompt);
        T[] enumConstants = enumClass.getEnumConstants();
        for (int i = 0; i < enumConstants.length; i++) {
            System.out.println(" " + (i + 1) + ": " + enumConstants[i].toString());
        }
        System.out.print("> ");
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {}
        if (choice >= 0 && choice < enumConstants.length) {
            return enumConstants[choice];
        } else {
            System.out.println("Invalid selection. Please try again.");
            return selectEnum(scanner, enumClass, prompt); // Recursive call on invalid input
        }
    }

    /**
     * Prompts the user to select an existing {@link MediaModel} from the {@link DataGraphManager}.
     * Displays a list of available media and re-prompts on invalid input.
     *
     * @param graph   The {@link DataGraphManager} to fetch media from.
     * @param scanner The {@link Scanner} for user input.
     * @param prompt  The message to display to the user.
     * @return The selected {@link MediaModel}, or {@code null} if no media are available.
     */
    private MediaModel selectMedia(DataGraphManager graph, Scanner scanner, String prompt) {
        List<MediaModel> medias = new ArrayList<>(graph.getAllMedias());
        if (medias.isEmpty()) {
            System.out.println("No media available to select.");
            return null;
        }
        System.out.println(prompt);
        for (int i = 0; i < medias.size(); i++) {
            System.out.println(" " + (i + 1) + ": " + medias.get(i).getMediaData().getName());
        }
        System.out.print("> ");
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {}
        if (choice >= 0 && choice < medias.size()) {
            return medias.get(choice);
        } else {
            System.out.println("Invalid selection. Please try again.");
            return selectMedia(graph, scanner, prompt); // Recursive call
        }
    }

    /**
     * Prompts the user to select an existing {@link PersonModel} from the {@link DataGraphManager}.
     * Displays a list of available persons and re-prompts on invalid input.
     *
     * @param graph   The {@link DataGraphManager} to fetch persons from.
     * @param scanner The {@link Scanner} for user input.
     * @param prompt  The message to display to the user.
     * @return The selected {@link PersonModel}, or {@code null} if no persons are available.
     */
    private PersonModel selectPerson(DataGraphManager graph, Scanner scanner, String prompt) {
        List<PersonModel> persons = new ArrayList<>(graph.getAllPersons());
        if (persons.isEmpty()) {
            System.out.println("No persons available to select.");
            return null;
        }
        System.out.println(prompt);
        for (int i = 0; i < persons.size(); i++) {
            System.out.println(" " + (i + 1) + ": " + persons.get(i).getPerson().getName());
        }
        System.out.print("> ");
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {}
        if (choice >= 0 && choice < persons.size()) {
            return persons.get(choice);
        } else {
            System.out.println("Invalid selection. Please try again.");
            return selectPerson(graph, scanner, prompt); // Recursive call
        }
    }

    /**
     * Prompts the user to select an existing {@link OrganizationModel} from the {@link DataGraphManager}.
     * Displays a list of available organizations and re-prompts on invalid input.
     *
     * @param graph   The {@link DataGraphManager} to fetch organizations from.
     * @param scanner The {@link Scanner} for user input.
     * @param prompt  The message to display to the user.
     * @return The selected {@link OrganizationModel}, or {@code null} if no organizations are available.
     */
    private OrganizationModel selectOrganization(DataGraphManager graph, Scanner scanner, String prompt) {
        List<OrganizationModel> organizations = new ArrayList<>(graph.getAllOrganizations());
        if (organizations.isEmpty()) {
            System.out.println("No organizations available to select.");
            return null;
        }
        System.out.println(prompt);
        for (int i = 0; i < organizations.size(); i++) {
            System.out.println(" " + (i + 1) + ": " + organizations.get(i).getOrganization().getName());
        }
        System.out.print("> ");
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {}
        if (choice >= 0 && choice < organizations.size()) {
            return organizations.get(choice);
        } else {
            System.out.println("Invalid selection. Please try again.");
            return selectOrganization(graph, scanner, prompt); // Recursive call
        }
    }

    /**
     * Reads multi-line text input from the user until they type "stop" on a new line.
     *
     * @param scanner The {@link Scanner} instance for reading user input.
     * @return A single string containing all lines of input, trimmed, with newline characters preserved between lines.
     */
    private String readMultiLineContent(Scanner scanner) {
        System.out.println("Enter publication content" + " (type 'stop' on a new line to finish):");
        StringBuilder contentBuilder = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equalsIgnoreCase("stop")) {
            contentBuilder.append(line).append("\n");
        }
        return contentBuilder.toString().trim();
    }

    /**
     * Simulates the creation of a new publication (e.g., article, interview).
     * Prompts the user for title, type, source media, author, and content.
     * Creates a {@link PublicationData} object, populates it (using reflection for final fields),
     * and publishes a {@link NewPublicationEvent}.
     *
     * @param graph   The {@link DataGraphManager} instance.
     * @param console The {@link Console} instance.
     */
    private void simulateNewPublication(DataGraphManager graph, Console console) {
        System.out.println("--- Simulating New Publication ---");
        Scanner scanner = console.scanner;

        System.out.print("Enter publication title: ");
        String title = scanner.nextLine();

        PublicationType publicationType = selectEnum(scanner, PublicationType.class, "Select publication type:");

        MediaModel selectedMedia = selectMedia(graph, scanner, "Select the source media:");
        if (selectedMedia == null) {
            System.out.println("Cannot create publication without a source media. Aborting.");
            return;
        }
        String mediaName = selectedMedia.getMediaData().getName();

        System.out.print("Enter author name: ");
        String author = scanner.nextLine();

        String content = readMultiLineContent(scanner);

        try {
            PublicationData publicationData = new PublicationData();
            setFinalField(publicationData, "title", title);
            setFinalField(publicationData, "type", publicationType);
            setFinalField(publicationData, "mediaName", mediaName);
            setFinalField(publicationData, "author", author);
            setFinalField(publicationData, "content", content);

            NewPublicationEvent event = new NewPublicationEvent(publicationData, graph);
            EventBus.getInstance().publish(event);
            System.out.println("NewPublicationEvent published successfully!");

        } catch (Exception e) {
            System.err.println("Error creating or publishing NewPublicationEvent: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Simulates the creation of a new media entity.
     * Prompts the user for name, news type, periodicity, region, billing type, and disappeared status.
     * Creates a {@link MediaData} object, populates it (using reflection for final fields),
     * and publishes a {@link NewMediaEvent}.
     *
     * @param graph   The {@link DataGraphManager} instance.
     * @param console The {@link Console} instance.
     */
    private void simulateNewMedia(DataGraphManager graph, Console console) {
        System.out.println("--- Simulating New Media ---");
        Scanner scanner = console.scanner;

        System.out.print("Enter media name: ");
        String name = scanner.nextLine();

        NewsType newsType = selectEnum(scanner, NewsType.class, "Select news type:");
        PeriodicityType periodicity = selectEnum(scanner, PeriodicityType.class, "Select periodicity:");
        RegionType region = selectEnum(scanner, RegionType.class, "Select region:");
        BillingType billing = selectEnum(scanner, BillingType.class, "Select billing type:");

        System.out.print("Is the media disappeared? (true/false): ");
        boolean disappeared = Boolean.parseBoolean(scanner.nextLine());

        try {
            MediaData mediaData = new MediaData();
            setFinalField(mediaData, "name", name);
            setFinalField(mediaData, "newsType", newsType);
            setFinalField(mediaData, "periodicity", periodicity);
            setFinalField(mediaData, "region", region);
            setFinalField(mediaData, "billing", billing);
            setFinalField(mediaData, "disappeared", disappeared);

            NewMediaEvent event = new NewMediaEvent(mediaData, graph);
            EventBus.getInstance().publish(event);
            System.out.println("NewMediaEvent published successfully!");

        } catch (Exception e) {
            System.err.println("Error creating or publishing NewMediaEvent: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Simulates the creation of a new person entity.
     * Prompts the user for the person's name.
     * Creates a {@link PersonData} object and publishes a {@link NewPersonEvent}.
     * Note: Collection of detailed {@code PersonNewsData} is not implemented in this simplified version.
     *
     * @param graph   The {@link DataGraphManager} instance.
     * @param console The {@link Console} instance.
     */
    private void simulateNewPerson(DataGraphManager graph, Console console) {
        System.out.println("--- Simulating New Person ---");
        Scanner scanner = console.scanner;

        System.out.print("Enter person's name: ");
        String name = scanner.nextLine();

        try {
            PersonData personData = new PersonData(name); // Assumes a constructor that accepts the name

            NewPersonEvent event = new NewPersonEvent(personData, graph);
            EventBus.getInstance().publish(event);
            System.out.println("NewPersonEvent published successfully for " + name + "!");

        } catch (Exception e) {
            System.err.println("Error creating or publishing NewPersonEvent: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Simulates the creation of a new organization entity.
     * Prompts the user for the organization's name and an optional comment.
     * Creates an {@link OrganizationData} object, populates it (using reflection for final fields),
     * and publishes a {@link NewOrganizationEvent}.
     *
     * @param graph   The {@link DataGraphManager} instance.
     * @param console The {@link Console} instance.
     */
    private void simulateNewOrganization(DataGraphManager graph, Console console) {
        System.out.println("--- Simulating New Organization ---");
        Scanner scanner = console.scanner;

        System.out.print("Enter organization name: ");
        String name = scanner.nextLine();
        System.out.print("Enter comment for organization (optional): ");
        String comment = scanner.nextLine();

        try {
            OrganizationData orgData = new OrganizationData();
            setFinalField(orgData, "name", name);
            setFinalField(orgData, "comment", comment);

            NewOrganizationEvent event = new NewOrganizationEvent(orgData, graph);
            EventBus.getInstance().publish(event);
            System.out.println("NewOrganizationEvent published successfully for " + name + "!");

        } catch (Exception e) {
            System.err.println("Error creating or publishing NewOrganizationEvent: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Simulates the creation or update of a link between a Person and a Media entity.
     * Prompts the user to select the source person, target media, equality type, and link value.
     * Creates a {@link PersonMediaLinkData} object.
     * Checks if the link already exists in the {@link DataGraphManager} to determine whether
     * to publish a {@link NewPersonMediaLinkEvent} or an {@link UpdatePersonMediaLinkEvent}.
     *
     * @param graph   The {@link DataGraphManager} instance.
     * @param console The {@link Console} instance.
     */
    private void simulatePersonMediaLink(DataGraphManager graph, Console console) {
        System.out.println("--- Simulating Person-Media Link (New/Update) ---");
        Scanner scanner = console.scanner;

        PersonModel person = selectPerson(graph, scanner, "Select the person (origin):");
        if (person == null) return;
        MediaModel media = selectMedia(graph, scanner, "Select the media (target):");
        if (media == null) return;

        EqualityType equalityType = selectEnum(scanner, EqualityType.class, "Select equality type for the link:");
        System.out.print("Enter value/weight for the link (e.g., percentage, role): ");
        String value = scanner.nextLine();

        try {
            PersonMediaLinkData linkData = new PersonMediaLinkData();
            setFinalField(linkData, "origin", person.getPerson().getName());
            setFinalField(linkData, "target", media.getMediaData().getName());
            setFinalField(linkData, "equalityType", equalityType);
            setFinalField(linkData, "value", value);

            boolean linkExists = graph.hasPersonMediaLink(person, media);

            if (linkExists) {
                UpdatePersonMediaLinkEvent event = new UpdatePersonMediaLinkEvent(linkData, graph);
                EventBus.getInstance().publish(event);
                System.out.println("UpdatePersonMediaLinkEvent published successfully!");
            } else {
                NewPersonMediaLinkEvent event = new NewPersonMediaLinkEvent(linkData, graph);
                EventBus.getInstance().publish(event);
                System.out.println("NewPersonMediaLinkEvent published successfully!");
            }
        } catch (Exception e) {
            System.err.println("Error creating or publishing PersonMediaLink event: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Simulates the creation or update of a link between a Person and an Organization entity.
     * Prompts for source person, target organization, equality type, link value, and an optional comment.
     * Creates a {@link PersonOrganizationLink} object.
     * Checks for link existence to publish either a {@link NewPersonOrganizationLinkEvent}
     * or an {@link UpdatePersonOrganizationLinkEvent}.
     *
     * @param graph   The {@link DataGraphManager} instance.
     * @param console The {@link Console} instance.
     */
    private void simulatePersonOrganizationLink(DataGraphManager graph, Console console) {
        System.out.println("--- Simulating Person-Organization Link (New/Update) ---");
        Scanner scanner = console.scanner;

        PersonModel person = selectPerson(graph, scanner, "Select the person (origin):");
        if (person == null) return;
        OrganizationModel organization = selectOrganization(graph, scanner, "Select the organization (target):");
        if (organization == null) return;

        EqualityType equalityType = selectEnum(scanner, EqualityType.class, "Select equality type for the link:");
        System.out.print("Enter value/weight for the link: ");
        String value = scanner.nextLine();
        System.out.print("Enter comment for the link (optional): ");
        String comment = scanner.nextLine();

        try {
            PersonOrganizationLink linkData = new PersonOrganizationLink();
            setFinalField(linkData, "origin", person.getPerson().getName());
            setFinalField(linkData, "target", organization.getOrganization().getName());
            setFinalField(linkData, "equalityType", equalityType);
            setFinalField(linkData, "value", value);
            setFinalField(linkData, "comment", comment);

            boolean linkExists = graph.hasPersonOrganizationLink(person, organization);

            if (linkExists) {
                UpdatePersonOrganizationLinkEvent event = new UpdatePersonOrganizationLinkEvent(linkData, graph);
                EventBus.getInstance().publish(event);
                System.out.println("UpdatePersonOrganizationLinkEvent published successfully!");
            } else {
                NewPersonOrganizationLinkEvent event = new NewPersonOrganizationLinkEvent(linkData, graph);
                EventBus.getInstance().publish(event);
                System.out.println("NewPersonOrganizationLinkEvent published successfully!");
            }
        } catch (Exception e) {
            System.err.println("Error creating or publishing PersonOrganizationLink event: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Simulates the creation or update of a link between an Organization and a Media entity.
     * Prompts for source organization, target media, equality type, and link value.
     * Creates an {@link OrganizationMediaLink} object.
     * Checks for link existence to publish either a {@link NewOrganizationMediaLinkEvent}
     * or an {@link UpdateOrganizationMediaLinkEvent}.
     *
     * @param graph   The {@link DataGraphManager} instance.
     * @param console The {@link Console} instance.
     */
    private void simulateOrganizationMediaLink(DataGraphManager graph, Console console) {
        System.out.println("--- Simulating Organization-Media Link (New/Update) ---");
        Scanner scanner = console.scanner;

        OrganizationModel organization = selectOrganization(graph, scanner, "Select the organization (origin):");
        if (organization == null) return;
        MediaModel media = selectMedia(graph, scanner, "Select the media (target):");
        if (media == null) return;

        EqualityType equalityType = selectEnum(scanner, EqualityType.class, "Select equality type for the link:");
        System.out.print("Enter value/weight for the link: ");
        String value = scanner.nextLine();

        try {
            OrganizationMediaLink linkData = new OrganizationMediaLink();
            setFinalField(linkData, "origin", organization.getOrganization().getName());
            setFinalField(linkData, "target", media.getMediaData().getName());
            setFinalField(linkData, "equalityType", equalityType);
            setFinalField(linkData, "value", value);

            boolean linkExists = graph.hasOrganizationMediaLink(organization, media);

            if (linkExists) {
                UpdateOrganizationMediaLinkEvent event = new UpdateOrganizationMediaLinkEvent(linkData, graph);
                EventBus.getInstance().publish(event);
                System.out.println("UpdateOrganizationMediaLinkEvent published successfully!");
            } else {
                NewOrganizationMediaLinkEvent event = new NewOrganizationMediaLinkEvent(linkData, graph);
                EventBus.getInstance().publish(event);
                System.out.println("NewOrganizationMediaLinkEvent published successfully!");
            }
        } catch (Exception e) {
            System.err.println("Error creating or publishing OrganizationMediaLink event: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Simulates the creation or update of a link between two Organization entities.
     * Prompts for source organization, target organization, equality type, link value, and an optional comment.
     * Ensures source and target organizations are not the same.
     * Creates an {@link OrganizationOrganizationLink} object.
     * Checks for link existence to publish either a {@link NewOrganizationOrganizationLinkEvent}
     * or an {@link UpdateOrganizationOrganizationLinkEvent}.
     *
     * @param graph   The {@link DataGraphManager} instance.
     * @param console The {@link Console} instance.
     */
    private void simulateOrganizationOrganizationLink(DataGraphManager graph, Console console) {
        System.out.println("--- Simulating Organization-Organization Link (New/Update) ---");
        Scanner scanner = console.scanner;

        OrganizationModel sourceOrg = selectOrganization(graph, scanner, "Select the source organization (origin):");
        if (sourceOrg == null) return;
        OrganizationModel targetOrg = selectOrganization(graph, scanner, "Select the target organization (target):");
        if (targetOrg == null) return;

        if (sourceOrg.equals(targetOrg)) {
            System.out.println("Source and target organization cannot be the same. Aborting.");
            return;
        }

        EqualityType equalityType = selectEnum(scanner, EqualityType.class, "Select equality type for the link:");
        System.out.print("Enter value/weight for the link: ");
        String value = scanner.nextLine();
        System.out.print("Enter comment for the link (optional): ");
        String comment = scanner.nextLine();

        try {
            OrganizationOrganizationLink linkData = new OrganizationOrganizationLink();
            setFinalField(linkData, "origin", sourceOrg.getOrganization().getName());
            setFinalField(linkData, "target", targetOrg.getOrganization().getName());
            setFinalField(linkData, "equalityType", equalityType);
            setFinalField(linkData, "value", value);
            setFinalField(linkData, "comment", comment);

            boolean linkExists = graph.hasOrganizationOrganizationLink(sourceOrg, targetOrg);

            if (linkExists) {
                UpdateOrganizationOrganizationLinkEvent event = new UpdateOrganizationOrganizationLinkEvent(linkData, graph);
                EventBus.getInstance().publish(event);
                System.out.println("UpdateOrganizationOrganizationLinkEvent published successfully!");
            } else {
                NewOrganizationOrganizationLinkEvent event = new NewOrganizationOrganizationLinkEvent(linkData, graph);
                EventBus.getInstance().publish(event);
                System.out.println("NewOrganizationOrganizationLinkEvent published successfully!");
            }
        } catch (Exception e) {
            System.err.println("Error creating or publishing OrganizationOrganizationLink event: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Utility method to set a private final field using reflection.
     *
     * @param object    The object on which to set the field.
     * @param fieldName The name of the field to set.
     * @param value     The value to set the field to.
     * @throws NoSuchFieldException   If a field with the specified name is not found.
     * @throws IllegalAccessException If the specified field is inaccessible.
     */
    private static void setFinalField(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true); // Allows modification of private (and final) fields
        field.set(object, value);
    }

    /**
     * Prints usage instructions for the 'simulate' command.
     */
    public void printUsage() {
        System.out.println("Usage: simulate <eventType>");
        System.out.println("Follow the on-screen prompts to select an event type and provide necessary data.");
        System.out.println("Example event types include: newPublication, newMedia, newPerson, newOrganization, personMediaLink, etc.");
    }
}
