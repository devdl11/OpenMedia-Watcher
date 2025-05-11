package fr.dl11.openmedia.contextualizers;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.data.MediaData;
import fr.dl11.openmedia.datasource.events.NewIncomingDataEvent;
import fr.dl11.openmedia.events.data.NewMediaEvent;
import fr.dl11.openmedia.exceptions.FailedEventHandling;

import javax.print.attribute.standard.Media;

public class MediaDataContextualizer extends GraphDataContextualizers {
    public MediaDataContextualizer(DataGraphManager graphManager) {
        super(graphManager);
        EventBus.getInstance().subscribe(NewIncomingDataEvent.class, this);
    }

    @Override
    public void handleEvent(NewIncomingDataEvent event) throws FailedEventHandling {
        assert event != null && event.data != null;

        Object data = event.data;
        EventBus bus = EventBus.getInstance();

        if (data instanceof MediaData mediaData) {
            event.consume();

            bus.publish(new NewMediaEvent(mediaData, graphManager));
            return;
        }
    }
}
