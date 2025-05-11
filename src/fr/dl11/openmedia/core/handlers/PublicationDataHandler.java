package fr.dl11.openmedia.core.handlers;

import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.EventHandler;
import fr.dl11.openmedia.events.data.NewPublicationEvent;
import fr.dl11.openmedia.exceptions.FailedEventHandling;
import fr.dl11.openmedia.models.MediaModel;

/**
 * Handler for processing publication-related events.
 *
 * <p>This class implements {@link EventHandler} to handle {@link NewPublicationEvent} events.
 * It subscribes to the {@link EventBus} and processes events by updating the
 * {@link DataGraphManager} with new publication data.
 */
public class PublicationDataHandler implements EventHandler<Event> {

    /**
     * Constructs a new PublicationDataHandler.
     *
     * <p>Registers this handler to the {@link EventBus} to listen for {@link NewPublicationEvent} events.
     */
    public PublicationDataHandler() {
        EventBus.getInstance().subscribe(NewPublicationEvent.class, this);
    }

    /**
     * Handles an incoming event.
     *
     * <p>If the event is of type {@link NewPublicationEvent}, it marks the event as consumed,
     * retrieves the associated {@link MediaModel} from the {@link DataGraphManager}, and
     * adds the publication to the data graph. If the media model is not found, the event is ignored.
     *
     * @param event The {@link Event} to handle.
     * @throws FailedEventHandling If an error occurs while handling the event.
     */
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