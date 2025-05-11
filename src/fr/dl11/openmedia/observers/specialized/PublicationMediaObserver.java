package fr.dl11.openmedia.observers.specialized;

import fr.dl11.openmedia.data.PublicationData;
import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.data.NewPublicationEvent;
import fr.dl11.openmedia.observers.PublicationObserver;

import java.util.Collection;
import java.util.List;

public class PublicationMediaObserver extends PublicationObserver {
    private final String mediaName;

    public PublicationMediaObserver(String name, String mediaName) {
        super(name, List.of(NewPublicationEvent.class));
        if (mediaName == null || mediaName.isEmpty()) {
            throw new IllegalArgumentException("Media name cannot be null or empty.");
        }
        this.mediaName = mediaName;
    }

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
