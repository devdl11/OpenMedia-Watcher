package fr.dl11.openmedia.observers.specialized;

import fr.dl11.openmedia.data.PublicationData;
import fr.dl11.openmedia.events.data.NewPublicationEvent;
import fr.dl11.openmedia.observers.PublicationObserver;

import java.util.List;

/**
 * Observer that monitors new publication events for specific content.
 *
 * <p>The {@code PublicationContentObserver} class extends {@link PublicationObserver}
 * and listens for events related to new publications. It checks if the content
 * of the publication contains a specified string and triggers actions accordingly.</p>
 */
public class PublicationContentObserver extends PublicationObserver {
    private final String content;

    /**
     * Constructs a new {@code PublicationContentObserver}.
     *
     * @param name    The unique name of this observer instance. Must not be null or empty.
     * @param content The content to search for in publications. Must not be null or empty.
     * @throws IllegalArgumentException If {@code content} is null or empty.
     */
    public PublicationContentObserver(String name, String content) {
        super(name, List.of(NewPublicationEvent.class));
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content to find cannot be null or empty.");
        }
        this.content = content;
    }

    /**
     * Handles the event of a new publication being created.
     *
     * <p>This method checks if the content of the new publication contains the
     * specified string. If it does, it prints a message indicating that the
     * observer has been triggered.</p>
     *
     * @param event The {@link NewPublicationEvent} containing the details of the new publication. Must not be null.
     */
    @Override
    protected void onNewPublication(NewPublicationEvent event) {
        PublicationData publication = event.getPublication();
        if (publication == null || publication.getContent() == null) {
            return;
        }

        if (publication.getContent().toLowerCase().contains(content.toLowerCase())) {
            System.out.println("\nObserver '" + getObserverName() + "' triggered !\n");
            System.out.println("   Publication '" + publication.getTitle() + "' contains content: '" + content + "'\n");
            System.out.print("> ");
        }
    }
}