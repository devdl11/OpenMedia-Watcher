package fr.dl11.openmedia.contextualizers;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.datasource.events.NewIncomingDataEvent;
import fr.dl11.openmedia.events.EventHandler;

public abstract class GraphDataContextualizers implements EventHandler<NewIncomingDataEvent> {
    protected DataGraphManager graphManager;

    public GraphDataContextualizers(DataGraphManager graphManager) {
        this.graphManager = graphManager;
    }
}
