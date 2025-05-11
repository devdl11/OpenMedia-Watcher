package fr.dl11.openmedia.console.commands;

import fr.dl11.openmedia.console.Console;
import fr.dl11.openmedia.console.components.ICommand;
import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.models.MediaModel;
import fr.dl11.openmedia.models.OrganizationModel;
import fr.dl11.openmedia.models.PersonModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Implements the {@link ICommand} interface to provide a console command
 * for displaying detailed information about various data entities within the system.
 * This command allows users to view data for media, persons, and organizations,
 * offering options to display all entities of a type or select a specific one.
 * For persons, it also includes sorting capabilities.
 */
public class ShowDataCommand implements ICommand {

    /**
     * Executes the "show" command based on the provided arguments.
     * Expects an argument specifying the type of data to show (e.g., "media", "person", "organization").
     * If the argument is missing or invalid, it displays usage instructions.
     *
     * @param args    Command line arguments. {@code args[1]} is expected to be the type of data to show.
     * @param graph   The {@link DataGraphManager} instance, used to retrieve data.
     * @param console The {@link Console} instance, providing access to the scanner for user input.
     */
    @Override
    public void execute(String[] args, DataGraphManager graph, Console console) {
        if (args.length < 2) {
            showUsage();
            return;
        }

        String type = args[1];
        switch (type) {
            case "media" -> showMedia(graph, console);
            case "person" -> showPerson(graph, console);
            case "organization" -> showOrganization(graph, console);
            default -> {
                System.out.println("Unknown type: " + type);
                showUsage();
            }
        }
    }

    /**
     * Displays the correct usage of the "show" command to the console.
     */
    public void showUsage() {
        System.out.println("Usage: show [media/person/organization]");
    }

    /**
     * Displays detailed information for a specific {@link MediaModel}.
     * Information includes media attributes (region, type, periodicity),
     * its publications, and any linked persons or organizations.
     *
     * @param model The {@link MediaModel} to display information for.
     * @param graph The {@link DataGraphManager} to retrieve linked entities.
     */
    private void showInfoForMedia(MediaModel model, DataGraphManager graph) {
        System.out.println("Showing info for media: " + model.getMediaData().getName());
        System.out.println(" - Region: " + model.getMediaData().getRegion());
        System.out.println(" - Type: " + model.getMediaData().getNewsType());
        System.out.println(" - Periodicity: " + model.getMediaData().getPeriodicity());
        System.out.println(" - Publications: ");

        if (model.getPublications().isEmpty()) {
            System.out.println("   No publications found for this media.");
        } else {
            for (var publication : model.getPublications()) {
                System.out.println("   - Title: " + publication.getTitle());
                System.out.println("   - Author: " + publication.getAuthor());
                System.out.println("   - Type of news: " + publication.getType());
                System.out.println("   - Content: " + (publication.getContent() != null && publication.getContent().length() > 100 ? publication.getContent().substring(0, 100) + "..." : publication.getContent()));
            }
        }
        System.out.println("--- End of publications");

        var personLinked = graph.getPersonLinksForMedia(model);
        if (!personLinked.isEmpty()) {
            System.out.println(" - People linked to this media: ");
            for (var person : personLinked) {
                System.out.println("   - " + person.source().getPerson().getName());
                System.out.println("   - Type of link: " + person.equalityType());
                System.out.println("   - Weight of the link: " + person.value());
                System.out.println();
            }
            System.out.println(" - End of people linked to this media");
        }

        var organizationLinked = graph.getOrganizationLinksForMedia(model);
        if (!organizationLinked.isEmpty()) {
            System.out.println(" - Organizations linked to this media: ");
            for (var organization : organizationLinked) {
                System.out.println("   - " + organization.source().getOrganization().getName());
                System.out.println("   - Type of link: " + organization.equalityType());
                System.out.println("   - Weight of the link: " + organization.value());
                System.out.println();
            }
            System.out.println(" - End of organizations linked to this media");
        }
        System.out.println(" - End of media info");
    }

    /**
     * Prompts the user to select a specific media from a list of all available media.
     * After selection, it calls {@link #showInfoForMedia(MediaModel, DataGraphManager)} to display its details.
     *
     * @param graph   The {@link DataGraphManager} to retrieve media from.
     * @param console The {@link Console} instance for user input.
     */
    private void selectMedia(DataGraphManager graph, Console console) {
        var medias = new ArrayList<>(graph.getAllMedias());
        if (medias.isEmpty()) {
            System.out.println("No media entities found.");
            return;
        }
        System.out.println("Select a media: ");
        for (int i = 0; i < medias.size(); i++) {
            System.out.println(" " + i + ": " + medias.get(i).getMediaData().getName());
        }
        System.out.println("Enter the number of the media: ");
        String input = console.scanner.nextLine();
        int index;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + input + ". Please enter a number.");
            return;
        }
        if (index < 0 || index >= medias.size()) {
            System.out.println("Invalid index: " + index);
            return;
        }

        var media = medias.get(index);
        showInfoForMedia(media, graph);
    }

    /**
     * Handles the display of media information.
     * Prompts the user to choose between showing all media or a specific media.
     *
     * @param graph   The {@link DataGraphManager} instance.
     * @param console The {@link Console} instance.
     */
    private void showMedia(DataGraphManager graph, Console console) {
        System.out.println("Show infos for: \n" + " 1. all medias\n" + " 2. specific media\n");
        System.out.print("> ");

        String input = console.scanner.nextLine();
        switch (input) {
            case "1" -> {
                if (graph.getAllMedias().isEmpty()) {
                    System.out.println("No media entities found.");
                } else {
                    for (var media : graph.getAllMedias()) {
                        showInfoForMedia(media, graph);
                        System.out.println();
                    }
                }
            }
            case "2" -> selectMedia(graph, console);
            default -> System.out.println("Unknown option: " + input);
        }
        System.out.println("   Returning to main menu...");
    }

    /**
     * Displays detailed information for a specific {@link PersonModel}.
     * Information includes the person's name, news ranking data (Forbes, Challenges),
     * and any linked media or organizations.
     *
     * @param graph   The {@link DataGraphManager} to retrieve linked entities.
     * @param console The {@link Console} instance (currently not used in this specific method but passed for consistency).
     * @param person  The {@link PersonModel} to display information for.
     */
    private void showPerson(DataGraphManager graph, Console console, PersonModel person) {
        System.out.println("Showing info for person: " + person.getPerson().getName());
        System.out.println(" - News Ranking:");
        if (person.getPerson().getNewsData().isEmpty()) {
            System.out.println("   No news ranking data available.");
        } else {
            for (var news : person.getPerson().getNewsData()) {
                System.out.println("   - Year: " + news.getYear());
                System.out.println("     - Forbes Ranking: " + (news.getForbesRank().isPresent() ? news.getForbesRank().get().toString() : "N/A"));
                System.out.println("     - Top Challenges: " + (news.getChallengeRank().isPresent() ? news.getChallengeRank().get().toString() : "N/A"));
                System.out.println();
            }
        }
        System.out.println(" - End of news ranking");

        var mediaLinked = graph.getMediaLinksForPerson(person);
        if (!mediaLinked.isEmpty()) {
            System.out.println(" - Medias linked to this person: ");
            for (var media : mediaLinked) {
                System.out.println("   - " + media.target().getMediaData().getName());
                System.out.println("   - Type of link: " + media.equalityType());
                System.out.println("   - Weight of the link: " + media.value());
                System.out.println();
            }
            System.out.println(" - End of medias linked to this person");
        }

        var organizationLinked = graph.getOrganizationLinksForPerson(person);
        if (!organizationLinked.isEmpty()) {
            System.out.println(" - Organizations linked to this person: ");
            for (var organization : organizationLinked) {
                System.out.println("   - " + organization.target().getOrganization().getName());
                System.out.println("   - Type of link: " + organization.equalityType());
                System.out.println("   - Weight of the link: " + organization.value());
                System.out.println();
            }
            System.out.println(" - End of organizations linked to this person");
        }
        System.out.println(" - End of person info");
    }

    /**
     * Displays information for all persons, with options for sorting.
     * Users can sort by name (ascending/descending) or by the number of media possessions (ascending/descending).
     *
     * @param graph   The {@link DataGraphManager} instance.
     * @param console The {@link Console} instance for user input.
     */
    private void showAllPersons(DataGraphManager graph, Console console) {
        var persons = new ArrayList<>(graph.getAllPersons());
        if (persons.isEmpty()) {
            System.out.println("No person entities found.");
            return;
        }

        Map<String, Integer> personsMediaCount = new HashMap<>();
        for (var person : persons) {
            var mediaCount = graph.getMediaLinksForPerson(person).size();
            personsMediaCount.put(person.getPerson().getName(), mediaCount);
        }

        System.out.println("Show all persons by: ");
        System.out.println("""
                 1. Name ASCENDING
                 2. Name DESCENDING
                 3. Media Possessions ASCENDING
                 4. Media Possessions DESCENDING
                """);
        System.out.print("> ");
        String input = console.scanner.nextLine();

        switch (input) {
            case "1" -> persons.sort(Comparator.comparing(o -> o.getPerson().getName()));
            case "2" -> persons.sort((o1, o2) -> o2.getPerson().getName().compareTo(o1.getPerson().getName()));
            case "3" -> persons.sort(Comparator.comparingInt(o -> personsMediaCount.get(o.getPerson().getName())));
            case "4" ->
                    persons.sort((o1, o2) -> Integer.compare(personsMediaCount.get(o2.getPerson().getName()), personsMediaCount.get(o1.getPerson().getName())));
            default -> {
                System.out.println("Unknown option: " + input);
                return;
            }
        }

        for (var person : persons) {
            showPerson(graph, console, person);
            System.out.println();
        }
    }

    /**
     * Prompts the user to select a specific person from a list of all available persons.
     * After selection, it calls {@link #showPerson(DataGraphManager, Console, PersonModel)} to display their details.
     *
     * @param graph   The {@link DataGraphManager} to retrieve persons from.
     * @param console The {@link Console} instance for user input.
     */
    private void selectPerson(DataGraphManager graph, Console console) {
        var persons = new ArrayList<>(graph.getAllPersons());
        if (persons.isEmpty()) {
            System.out.println("No person entities found.");
            return;
        }
        System.out.println("Select a person: ");
        for (int i = 0; i < persons.size(); i++) {
            System.out.println(" " + i + ": " + persons.get(i).getPerson().getName());
        }
        System.out.println("Enter the number of the person: ");
        String input = console.scanner.nextLine();
        int index;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + input + ". Please enter a number.");
            return;
        }
        if (index < 0 || index >= persons.size()) {
            System.out.println("Invalid index: " + index);
            return;
        }

        var person = persons.get(index);
        showPerson(graph, console, person); // Calls the overloaded showPerson method
    }

    /**
     * Handles the display of person information.
     * Prompts the user to choose between showing all persons (with sorting) or a specific person.
     *
     * @param graph   The {@link DataGraphManager} instance.
     * @param console The {@link Console} instance.
     */
    private void showPerson(DataGraphManager graph, Console console) {
        System.out.println("""
                Show infos for:\s
                 1. all persons
                 2. specific person
                """);
        System.out.print("> ");
        String input = console.scanner.nextLine();

        switch (input) {
            case "1" -> showAllPersons(graph, console);
            case "2" -> selectPerson(graph, console);
            default -> System.out.println("Unknown option: " + input);
        }
        System.out.println("   Returning to main menu...");
    }

    /**
     * Displays detailed information for a specific {@link OrganizationModel}.
     * Information includes the organization's name, comment, and any linked media or persons.
     *
     * @param graph        The {@link DataGraphManager} to retrieve linked entities.
     * @param console      The {@link Console} instance (currently not used in this specific method).
     * @param organization The {@link OrganizationModel} to display information for.
     */
    private void showOrganizationInfo(DataGraphManager graph, Console console, OrganizationModel organization) {
        System.out.println("Showing info for organization: " + organization.getOrganization().getName());
        System.out.println(" - Comment: " + (organization.getOrganization().getComment() != null && !organization.getOrganization().getComment().isEmpty() ? organization.getOrganization().getComment() : "N/A"));

        var mediaLinked = graph.getMediaLinksForOrganization(organization);
        if (!mediaLinked.isEmpty()) {
            System.out.println(" - Medias linked to this organization: ");
            for (var media : mediaLinked) {
                System.out.println("   - " + media.target().getMediaData().getName());
                System.out.println("   - Type of link: " + media.equalityType());
                System.out.println("   - Weight of the link: " + media.value());
                System.out.println();
            }
            System.out.println(" - End of medias linked to this organization");
        }

        var personLinked = graph.getPersonLinksForOrganization(organization);
        if (!personLinked.isEmpty()) {
            System.out.println(" - People linked to this organization: ");
            for (var person : personLinked) {
                System.out.println("   - " + person.source().getPerson().getName());
                System.out.println("   - Type of link: " + person.equalityType());
                System.out.println("   - Weight of the link: " + person.value());
                System.out.println();
            }
            System.out.println(" - End of people linked to this organization");
        }
        System.out.println(" - End of organization info");
    }

    /**
     * Prompts the user to select a specific organization from a list of all available organizations.
     * After selection, it calls {@link #showOrganizationInfo(DataGraphManager, Console, OrganizationModel)} to display its details.
     *
     * @param graph   The {@link DataGraphManager} to retrieve organizations from.
     * @param console The {@link Console} instance for user input.
     */
    private void selectOrganization(DataGraphManager graph, Console console) {
        var organizations = new ArrayList<>(graph.getAllOrganizations());
        if (organizations.isEmpty()) {
            System.out.println("No organization entities found.");
            return;
        }
        System.out.println("Select an organization: ");
        for (int i = 0; i < organizations.size(); i++) {
            System.out.println(" " + i + ": " + organizations.get(i).getOrganization().getName());
        }
        System.out.println("Enter the number of the organization: ");
        String input = console.scanner.nextLine();
        int index;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + input + ". Please enter a number.");
            return;
        }
        if (index < 0 || index >= organizations.size()) {
            System.out.println("Invalid index: " + index);
            return;
        }

        var organization = organizations.get(index);
        showOrganizationInfo(graph, console, organization);
    }

    /**
     * Handles the display of organization information.
     * Prompts the user to choose between showing all organizations or a specific organization.
     *
     * @param graph   The {@link DataGraphManager} instance.
     * @param console The {@link Console} instance.
     */
    private void showOrganization(DataGraphManager graph, Console console) {
        System.out.println("""
                Show infos for:\s
                 1. all organizations
                 2. specific organization
                """);
        System.out.print("> ");

        String input = console.scanner.nextLine();
        switch (input) {
            case "1" -> {
                if (graph.getAllOrganizations().isEmpty()) {
                    System.out.println("No organization entities found.");
                } else {
                    for (var organization : graph.getAllOrganizations()) {
                        showOrganizationInfo(graph, console, organization);
                        System.out.println();
                    }
                }
            }
            case "2" -> selectOrganization(graph, console);
            default -> System.out.println("Unknown option: " + input);
        }
        System.out.println("   Returning to main menu...");
    }
}
