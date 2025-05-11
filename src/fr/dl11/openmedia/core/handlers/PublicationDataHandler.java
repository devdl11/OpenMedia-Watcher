package fr.dl11.openmedia.core.handlers;

import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.EventHandler;
import fr.dl11.openmedia.events.data.NewPublicationEvent;
import fr.dl11.openmedia.exceptions.FailedEventHandling;
import fr.dl11.openmedia.models.MediaModel;

public class PublicationDataHandler implements EventHandler<Event> {
    public PublicationDataHandler() {
        EventBus.getInstance().subscribe(NewPublicationEvent.class, this);
    }

    @Override
    public void handleEvent(Event event) throws FailedEventHandling {
        assert event != null;

        if (event instanceof NewPublicationEvent newPublicationEvent) {
            event.consume();
            var graphManager = newPublicationEvent.getDataGraphManager();
            var publication = newPublicationEvent.getPublication();

            var mediaModel = graphManager.getMedia(publication.getMediaName());
            if (mediaModel == null) {
                return;
            }

            graphManager.onAddPublication(mediaModel, newPublicationEvent.getPublication());
        }
    }
}
