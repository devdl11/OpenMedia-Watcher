package fr.dl11.openmedia.contextualizers;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.data.PublicationData;
import fr.dl11.openmedia.datasource.events.NewIncomingDataEvent;
import fr.dl11.openmedia.events.data.NewPublicationEvent;
import fr.dl11.openmedia.exceptions.FailedEventHandling;

public class PublicationDataContextualizers extends GraphDataContextualizers{
    public PublicationDataContextualizers(DataGraphManager graphManager) {
        super(graphManager);
        EventBus.getInstance().subscribe(NewIncomingDataEvent.class, this);
    }

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
