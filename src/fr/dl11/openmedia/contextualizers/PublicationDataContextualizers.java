package fr.dl11.openmedia.contextualizers;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.data.PublicationData;
import fr.dl11.openmedia.datasource.events.NewIncomingDataEvent;
import fr.dl11.openmedia.events.data.NewPublicationEvent;
import fr.dl11.openmedia.exceptions.FailedEventHandling;

/**
 * Contextualizer for handling publication-related data events.
 *
 * <p>This class extends {@link GraphDataContextualizers} and subscribes to
 * {@link NewIncomingDataEvent} events. It processes incoming data of type
 * {@link PublicationData} and publishes corresponding {@link NewPublicationEvent}
 * to the {@link EventBus}.
 */
public class PublicationDataContextualizers extends GraphDataContextualizers {

    /**
     * Constructs a new PublicationDataContextualizers.
     *
     * <p>Subscribes to {@link NewIncomingDataEvent} events using the {@link EventBus}.
     *
     * @param graphManager The {@link DataGraphManager} instance used for managing the data graph.
     */
    public PublicationDataContextualizers(DataGraphManager graphManager) {
        super(graphManager);
        EventBus.getInstance().subscribe(NewIncomingDataEvent.class, this);
    }

    /**
     * Handles a {@link NewIncomingDataEvent}.
     *
     * <p>If the event contains data of type {@link PublicationData}, it marks the event as consumed
     * and publishes a {@link NewPublicationEvent} to the {@link EventBus}.
     *
     * @param event The {@link NewIncomingDataEvent} to handle.
     * @throws FailedEventHandling If an error occurs while handling the event.
     */
    @Override
    public void handleEvent(NewIncomingDataEvent event) throws FailedEventHandling {
        assert event != null && event.data != null;

        Object data = event.data;
        EventBus bus = EventBus.getInstance();

        if (data instanceof PublicationData publicationData) {
            event.consume();

            bus.publish(new NewPublicationEvent(publicationData, graphManager));
            return;
        }
    }
}