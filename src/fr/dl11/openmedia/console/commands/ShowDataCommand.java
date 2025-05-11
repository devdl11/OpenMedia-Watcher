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

public class ShowDataCommand implements ICommand {
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

    public void showUsage() {
        System.out.println("Usage: show [media/person/organization]");
    }

    private void showInfoForMedia(MediaModel model, DataGraphManager graph) {
        System.out.println("Showing info for media: " + model.getMediaData().getName());
        System.out.println(" - Region: " + model.getMediaData().getRegion());
        System.out.println(" - Type: " + model.getMediaData().getNewsType());
        System.out.println(" - Periodicity: " + model.getMediaData().getPeriodicity());
        System.out.println(" - Publications: ");

        for (var publication : model.getPublications()) {
            System.out.println("   - Title: " + publication.getTitle());
            System.out.println("   - Author: " + publication.getAuthor());
            System.out.println("   - Type of news: " + publication.getType());
            System.out.println("   - Content: " + publication.getContent());
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

    private void selectMedia(DataGraphManager graph, Console console) {
        var medias = new ArrayList<>(graph.getAllMedias());
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
            System.out.println("Invalid input: " + input);
            return;
        }
        if (index < 0 || index >= medias.size()) {
            System.out.println("Invalid index: " + index);
            return;
        }

        var media = medias.get(index);
        showInfoForMedia(media, graph);
    }

    private void showMedia(DataGraphManager graph, Console console) {
        System.out.println("Show infos for: \n" +
                " 1. all medias\n" +
                " 2. specific media\n");
        System.out.print("> ");

        String input = console.scanner.nextLine();
        switch (input) {
            case "1" -> {
                for (var media : graph.getAllMedias()) {
                    showInfoForMedia(media, graph);
                }
            }
            case "2" -> {
                selectMedia(graph, console);
            }
            default -> {
                System.out.println("Unknown option: " + input);
            }
        }

        System.out.println("   Returning to main menu...");
    }

    private void showPerson(DataGraphManager graph, Console console, PersonModel person) {
        System.out.println("Showing info for person: " + person.getPerson().getName());
        System.out.println(" - News Raking:");
        for (var news : person.getPerson().getNewsData()) {
            System.out.println("   - Forbes Ranking: " + (news.getForbesRank().isPresent()
                    ? news.getForbesRank().get() : "N/A"));
            System.out.println("   - Top Challenges: " + (news.getChallengeRank().isPresent()
                    ? news.getChallengeRank().get() : "N/A"));
            System.out.println();
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

    private void showAllPersons(DataGraphManager graph, Console console) {
        var persons = new ArrayList<>(graph.getAllPersons());
        Map<String, Integer> personsMediaCount = new HashMap<>();

        for (var person : persons) {
            var mediaCount = graph.getMediaLinksForPerson(person).size();
            personsMediaCount.put(person.getPerson().getName(), mediaCount);
        }

        System.out.println("Show all persons by: ");
        System.out.println(
                """
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
            case "4" -> persons.sort((o1, o2) -> Integer.compare(
                    personsMediaCount.get(o2.getPerson().getName()),
                    personsMediaCount.get(o1.getPerson().getName())
            ));
            default -> {
                System.out.println("Unknown option: " + input);
                return;
            }
        }

        for (var person : persons) {
            showPerson(graph, console, person);
        }
    }

    private void selectPerson(DataGraphManager graph, Console console) {
        var persons = new ArrayList<>(graph.getAllPersons());
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
            System.out.println("Invalid input: " + input);
            return;
        }
        if (index < 0 || index >= persons.size()) {
            System.out.println("Invalid index: " + index);
            return;
        }

        var person = persons.get(index);
        showPerson(graph, console, person);
    }

    private void showPerson(DataGraphManager graph, Console console) {
        System.out.println("""
                Show infos for:\s
                 1. all persons
                 2. specific person
                """);
        System.out.print("> ");
        String input = console.scanner.nextLine();

        switch (input) {
            case "1" -> {
                showAllPersons(graph, console);
            }
            case "2" -> {
                selectPerson(graph, console);
            }
            default -> {
                System.out.println("Unknown option: " + input);
            }
        }

        System.out.println("   Returning to main menu...");
    }

    private void showOrganizationInfo(DataGraphManager graph, Console console, OrganizationModel organization) {
        System.out.println("Showing info for organization: " + organization.getOrganization().getName());
        System.out.println(" - Comment: " + organization.getOrganization().getComment());

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

    private void selectOrganization(DataGraphManager graph, Console console) {
        var organizations = new ArrayList<>(graph.getAllOrganizations());
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
            System.out.println("Invalid input: " + input);
            return;
        }
        if (index < 0 || index >= organizations.size()) {
            System.out.println("Invalid index: " + index);
            return;
        }

        var organization = organizations.get(index);
        showOrganizationInfo(graph, console, organization);
    }

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
                for (var organization : graph.getAllOrganizations()) {
                    showOrganizationInfo(graph, console, organization);
                }
            }
            case "2" -> {
                selectOrganization(graph, console);
            }
            default -> {
                System.out.println("Unknown option: " + input);
            }
        }

        System.out.println("   Returning to main menu...");
    }
}
