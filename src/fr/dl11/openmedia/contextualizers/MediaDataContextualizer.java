package fr.dl11.openmedia.contextualizers;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.data.MediaData;
import fr.dl11.openmedia.datasource.events.NewIncomingDataEvent;
import fr.dl11.openmedia.events.data.NewMediaEvent;
import fr.dl11.openmedia.exceptions.FailedEventHandling;

/**
 * Contextualizer for handling media data events.
 *
 * <p>This class extends {@link GraphDataContextualizers} and subscribes to
 * {@link NewIncomingDataEvent} events. It processes incoming data and publishes
 * a {@link NewMediaEvent} if the data is of type {@link MediaData}.
 */
public class MediaDataContextualizer extends GraphDataContextualizers {

    /**
     * Constructs a new MediaDataContextualizer.
     *
     * <p>Subscribes to {@link NewIncomingDataEvent} events using the {@link EventBus}.
     *
     * @param graphManager The {@link DataGraphManager} instance used for managing the data graph.
     */
    public MediaDataContextualizer(DataGraphManager graphManager) {
        super(graphManager);
        EventBus.getInstance().subscribe(NewIncomingDataEvent.class, this);
    }

    /**
     * Handles a {@link NewIncomingDataEvent}.
     *
     * <p>If the event contains data of type {@link MediaData}, it marks the event as consumed
     * and publishes a {@link NewMediaEvent} to the {@link EventBus}. If the data is null or
     * not of type {@link MediaData}, the event is ignored.
     *
     * @param event The {@link NewIncomingDataEvent} to handle.
     * @throws FailedEventHandling If an error occurs while handling the event.
     */
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