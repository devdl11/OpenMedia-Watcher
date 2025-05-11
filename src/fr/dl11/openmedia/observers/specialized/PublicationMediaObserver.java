package fr.dl11.openmedia.observers.specialized;

import fr.dl11.openmedia.data.PublicationData;
import fr.dl11.openmedia.events.data.NewPublicationEvent;
import fr.dl11.openmedia.observers.PublicationObserver;

import java.util.List;

/**
 * Observer that monitors new publication events for a specific media.
 *
 * <p>The {@code PublicationMediaObserver} class extends {@link PublicationObserver}
 * and listens for events related to new publications. It checks if the media name
 * of the publication matches a specified name and triggers actions accordingly.</p>
 */
public class PublicationMediaObserver extends PublicationObserver {
    private final String mediaName;

    /**
     * Constructs a new {@code PublicationMediaObserver}.
     *
     * @param name      The unique name of this observer instance. Must not be null or empty.
     * @param mediaName The name of the media to watch for in publications. Must not be null or empty.
     * @throws IllegalArgumentException If {@code mediaName} is null or empty.
     */
    public PublicationMediaObserver(String name, String mediaName) {
        super(name, List.of(NewPublicationEvent.class));
        if (mediaName == null || mediaName.isEmpty()) {
            throw new IllegalArgumentException("Media name cannot be null or empty.");
        }
        this.mediaName = mediaName;
    }

    /**
     * Handles the event of a new publication being created.
     *
     * <p>This method checks if the media name of the new publication matches the
     * specified media name. If it does, it prints a message indicating that the
     * observer has been triggered.</p>
     *
     * @param event The {@link NewPublicationEvent} containing the details of the new publication. Must not be null.
     */
    @Override
    protected void onNewPublication(NewPublicationEvent event) {
        PublicationData publication = event.getPublication();
        if (publication == null || publication.getMediaName() == null) {
            return;
        }

        if (publication.getMediaName().equalsIgnoreCase(mediaName)) {
            System.out.println("\nObserver '" + getObserverName() + "' triggered! \n");
            System.out.println("   New publication for media '" + mediaName + "': '" + publication.getTitle() + "'\n");
            System.out.print("> "); // Reprint console prompt
        }
    }
}