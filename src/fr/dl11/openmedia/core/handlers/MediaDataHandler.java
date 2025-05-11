package fr.dl11.openmedia.core.handlers;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.EventHandler;
import fr.dl11.openmedia.events.data.NewMediaEvent;
import fr.dl11.openmedia.exceptions.FailedEventHandling;

public class MediaDataHandler implements EventHandler<Event> {
    public MediaDataHandler() {
        // Register this handler to the event bus
        EventBus.getInstance().subscribe(NewMediaEvent.class, this);
    }

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
