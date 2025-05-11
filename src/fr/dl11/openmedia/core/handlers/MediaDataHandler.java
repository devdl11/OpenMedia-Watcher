package fr.dl11.openmedia.core.handlers;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.EventHandler;
import fr.dl11.openmedia.events.data.NewMediaEvent;
import fr.dl11.openmedia.exceptions.FailedEventHandling;

/**
 * Handler for processing media-related events.
 *
 * <p>This class implements {@link EventHandler} to handle {@link NewMediaEvent} events.
 * It subscribes to the {@link EventBus} and processes events by updating the
 * {@link DataGraphManager} with new media data.
 */
public class MediaDataHandler implements EventHandler<Event> {

    /**
     * Constructs a new MediaDataHandler.
     *
     * <p>Registers this handler to the {@link EventBus} to listen for {@link NewMediaEvent} events.
     */
    public MediaDataHandler() {
        // Register this handler to the event bus
        EventBus.getInstance().subscribe(NewMediaEvent.class, this);
    }

    /**
     * Handles an incoming event.
     *
     * <p>If the event is of type {@link NewMediaEvent}, it marks the event as consumed
     * and updates the {@link DataGraphManager} with the new media data.
     *
     * @param event The {@link Event} to handle.
     * @throws FailedEventHandling If an error occurs while handling the event.
     */
    @Override
    public void handleEvent(Event event) throws FailedEventHandling {
        assert event != null;

        if (event instanceof NewMediaEvent newMediaEvent) {
            event.consume();

            var graphManager = newMediaEvent.getDataGraphManager();

            graphManager.onAddMedia(newMediaEvent.getMedia());
        }
    }
}