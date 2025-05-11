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

public class SimulateEventCommand implements ICommand {

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
            case "0" -> {return;}
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

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
        } catch (NumberFormatException e) {
            // Handled below
        }
        if (choice >= 0 && choice < enumConstants.length) {
            return enumConstants[choice];
        } else {
            System.out.println("Invalid selection. Please try again.");
            return selectEnum(scanner, enumClass, prompt); // Recursive call on invalid input
        }
    }

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
        } catch (NumberFormatException e) {
            //
        }
        if (choice >= 0 && choice < medias.size()) {
            return medias.get(choice);
        } else {
            System.out.println("Invalid selection. Please try again.");
            return selectMedia(graph, scanner, prompt);
        }
    }

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
        } catch (NumberFormatException e) {
            // Handled below
        }
        if (choice >= 0 && choice < persons.size()) {
            return persons.get(choice);
        } else {
            System.out.println("Invalid selection. Please try again.");
            return selectPerson(graph, scanner, prompt);
        }
    }

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
        } catch (NumberFormatException e) {
            // Handled below
        }
        if (choice >= 0 && choice < organizations.size()) {
            return organizations.get(choice);
        } else {
            System.out.println("Invalid selection. Please try again.");
            return selectOrganization(graph, scanner, prompt);
        }
    }

    private String readMultiLineContent(Scanner scanner) {
        System.out.println("Enter publication content" + " (type 'stop' on a new line to finish):");
        StringBuilder contentBuilder = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equalsIgnoreCase("stop")) {
            contentBuilder.append(line).append("\n");
        }
        return contentBuilder.toString().trim();
    }

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
            // Using reflection to set private final fields, common in such data classes if no setters/constructors provided for all fields
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

    private void simulateNewPerson(DataGraphManager graph, Console console) {
        System.out.println("--- Simulating New Person ---");
        Scanner scanner = console.scanner;

        System.out.print("Enter person's name: ");
        String name = scanner.nextLine();

        try {
            PersonData personData = new PersonData(name); // Assuming constructor that takes name

            NewPersonEvent event = new NewPersonEvent(personData, graph);
            EventBus.getInstance().publish(event);
            System.out.println("NewPersonEvent published successfully for " + name + "!");

        } catch (Exception e) {
            System.err.println("Error creating or publishing NewPersonEvent: " + e.getMessage());
            e.printStackTrace();
        }
    }

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

            // Check if link exists to decide between New and Update event
            boolean linkExists = graph.hasPersonMediaLink(person, media); // You need to implement this in DataGraphManager

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

            boolean linkExists = graph.hasPersonOrganizationLink(person, organization); // Implement in DataGraphManager

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

            boolean linkExists = graph.hasOrganizationMediaLink(organization, media); // Implement in DataGraphManager

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

            boolean linkExists = graph.hasOrganizationOrganizationLink(sourceOrg, targetOrg); // Implement in DataGraphManager

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
     * This is generally not recommended but can be necessary for data classes
     * that don't provide setters or appropriate constructors.
     */
    private static void setFinalField(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    public void printUsage() { // Kept from original template, can be adapted or removed
        System.out.println("Usage: simulate <eventType>");
        System.out.println("Possible event types: newPublication, newMedia, newPerson, newOrganization, personMediaLink, etc.");
    }
}