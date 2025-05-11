package fr.dl11.openmedia.observers.specialized;

import fr.dl11.openmedia.data.PublicationData;
import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.data.NewPublicationEvent;
import fr.dl11.openmedia.observers.PublicationObserver;

import java.util.Collection;
import java.util.List;

public class PublicationContentObserver extends PublicationObserver {
    private final String content;

    public PublicationContentObserver(String name, String content) {
        super(name, List.of(NewPublicationEvent.class));
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content to find cannot be null or empty.");
        }
        this.content = content;
    }

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
