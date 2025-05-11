package fr.dl11.openmedia.console.commands;

import fr.dl11.openmedia.console.Console;
import fr.dl11.openmedia.console.components.ICommand;
import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.models.MediaModel;
import fr.dl11.openmedia.observers.BaseObserver;

import fr.dl11.openmedia.observers.specialized.PublicationContentObserver;
import fr.dl11.openmedia.observers.specialized.PublicationMediaObserver;
import fr.dl11.openmedia.observers.specialized.OrganizationMediaLinkTargetObserver;
import fr.dl11.openmedia.observers.specialized.PersonMediaLinkTargetObserver;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ObserverManagerCommand implements ICommand {

    @Override
    public void execute(String[] args, DataGraphManager graph, Console console) {
        while (true) {
            System.out.println("\n--- Observer Manager ---");
            System.out.println("1. List Active Observers");
            System.out.println("2. Create New Specialized Observer");
            System.out.println("3. Remove Observer");
            System.out.println("0. Back to Main Menu");
            System.out.print("> ");
            String choice = console.scanner.nextLine().trim();

            switch (choice) {
                case "1" -> listActiveObservers(console);
                case "2" -> createNewSpecializedObserver(graph, console);
                case "3" -> removeObserver(console);
                case "0" -> {
                    return; // Exit this command's loop
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Lists all currently active observers.
     * Assumes BaseObserver has getName(), getId(), getDescription(), and isSubscribed() methods.
     * @param console The console instance containing the list of active observers.
     */
    private void listActiveObservers(Console console) {
        System.out.println("\n--- Active Observers ---");
        if (console.activeObservers == null || console.activeObservers.isEmpty()) {
            System.out.println("No active observers.");
            return;
        }
        AtomicInteger index = new AtomicInteger(1);
        console.activeObservers.forEach(observer -> {
            System.out.println(index.getAndIncrement() + ". Name: " + observer.getObserverName());
        });
    }

    /**
     * Allows the user to remove an observer from the active list.
     * @param console The console instance.
     */
    private void removeObserver(Console console) {
        System.out.println("\n--- Remove Observer ---");
        if (console.activeObservers == null || console.activeObservers.isEmpty()) {
            System.out.println("No observers to remove.");
            return;
        }
        listActiveObservers(console);
        System.out.print("Enter the number of the observer to remove (or 0 to cancel): ");
        int choice;
        try {
            choice = Integer.parseInt(console.scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        if (choice == 0) {
            System.out.println("Removal cancelled.");
            return;
        }
        if (choice > 0 && choice <= console.activeObservers.size()) {
            // Remove from list and call unsubscribe
            BaseObserver observerToRemove = console.activeObservers.remove(choice - 1);
            observerToRemove.unsubscribe();
            System.out.println("Observer '" + observerToRemove.getObserverName() + "' removed and unsubscribed.");
        } else {
            System.out.println("Invalid observer number.");
        }
    }

    /**
     * Guides the user through creating a new specialized observer.
     * @param graph The DataGraphManager for context (e.g., selecting media).
     * @param console The console instance.
     */
    private void createNewSpecializedObserver(DataGraphManager graph, Console console) {
        System.out.println("\n--- Create New Specialized Observer ---");
        System.out.print("Enter a unique name for this observer: ");
        String observerName = console.scanner.nextLine().trim();
        if (observerName.isEmpty()) {
            System.out.println("Observer name cannot be empty.");
            return;
        }
        // Check for name uniqueness
        if (console.activeObservers != null && console.activeObservers.stream().anyMatch(o -> o.getObserverName().equalsIgnoreCase(observerName))) {
            System.out.println("An observer with this name already exists. Please choose a different name.");
            return;
        }

        System.out.println("Select type of specialized observer to create:");
        System.out.println("1. Publication Content Observer (Watches for content in new publications)");
        System.out.println("2. Publication Media Observer (Watches for new publications from a specific media)");
        System.out.println("3. Organization-Media Link Target Observer (Watches for org links to a specific media)");
        System.out.println("4. Person-Media Link Target Observer (Watches for person links to a specific media)");
        System.out.println("0. Cancel");
        System.out.print("> ");
        String typeChoice = console.scanner.nextLine().trim();

        BaseObserver newObserver = null;

        switch (typeChoice) {
            case "1" -> newObserver = configurePublicationContentObserver(observerName, console);
            case "2" -> newObserver = configurePublicationMediaObserver(observerName, graph, console);
            case "3" -> newObserver = configureOrgMediaLinkTargetObserver(observerName, graph, console);
            case "4" -> newObserver = configurePersonMediaLinkTargetObserver(observerName, graph, console);
            case "0" -> {
                System.out.println("Observer creation cancelled.");
                return;
            }
            default -> {
                System.out.println("Invalid observer type selected.");
                return;
            }
        }

        if (newObserver != null) {
            if (console.activeObservers == null) {
                console.activeObservers = new ArrayList<>();
            }
            console.activeObservers.add(newObserver);
            System.out.println("Observer '" + newObserver.getObserverName() + "' created and activated successfully.");
        } else {
            System.out.println("Failed to configure and create observer.");
        }
    }

    // --- Configuration methods for each specialized observer ---

    private PublicationContentObserver configurePublicationContentObserver(String observerName, Console console) {
        System.out.print("Enter the content string to watch for in new publications: ");
        String contentToWatch = console.scanner.nextLine().trim();
        if (contentToWatch.isEmpty()) {
            System.out.println("Content to watch cannot be empty.");
            return null;
        }

        return new PublicationContentObserver(observerName, contentToWatch);
    }

    private PublicationMediaObserver configurePublicationMediaObserver(String observerName, DataGraphManager graph, Console console) {
        MediaModel selectedMedia = selectMedia(graph, console.scanner, "Select the Media to watch for publications from:");
        if (selectedMedia == null) {
            System.out.println("Media selection is required. Observer not created.");
            return null;
        }

        return new PublicationMediaObserver(observerName, selectedMedia.getMediaData().getName());
    }

    private OrganizationMediaLinkTargetObserver configureOrgMediaLinkTargetObserver(String observerName, DataGraphManager graph, Console console) {
        MediaModel targetMedia = selectMedia(graph, console.scanner, "Select the Target Media for Organization-Media links:");
        if (targetMedia == null) {
            System.out.println("Target Media selection is required. Observer not created.");
            return null;
        }

        return new OrganizationMediaLinkTargetObserver(observerName, targetMedia.getMediaData().getName());
    }

    private PersonMediaLinkTargetObserver configurePersonMediaLinkTargetObserver(String observerName, DataGraphManager graph, Console console) {
        MediaModel targetMedia = selectMedia(graph, console.scanner, "Select the Target Media for Person-Media links:");
        if (targetMedia == null) {
            System.out.println("Target Media selection is required. Observer not created.");
            return null;
        }

        return new PersonMediaLinkTargetObserver(observerName, targetMedia.getMediaData().getName());
    }


    /**
     * Helper method to allow the user to select a Media entity.
     * @param graph DataGraphManager to fetch media from.
     * @param scanner Scanner for user input.
     * @param prompt The message to display to the user.
     * @return The selected MediaModel, or null if selection is cancelled or invalid.
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
        System.out.println(" 0: Cancel Selection");
        System.out.print("> ");

        int choice = -1;
        String input = scanner.nextLine().trim();
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return null;
        }

        if (choice == 0) {
            System.out.println("Selection cancelled.");
            return null;
        }
        if (choice > 0 && choice <= medias.size()) {
            return medias.get(choice - 1);
        } else {
            System.out.println("Invalid media number selected.");
            return null;
        }
    }
}
