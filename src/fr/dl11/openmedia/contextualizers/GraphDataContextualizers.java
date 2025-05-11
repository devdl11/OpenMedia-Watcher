package fr.dl11.openmedia.contextualizers;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.datasource.events.NewIncomingDataEvent;
import fr.dl11.openmedia.events.EventHandler;

/**
 * Abstract base class for graph data contextualizers.
 *
 * <p>This class provides a foundation for handling {@link NewIncomingDataEvent} events
 * and interacting with the {@link DataGraphManager}. Subclasses should implement
 * specific logic for contextualizing data within the graph.
 */
public abstract class GraphDataContextualizers implements EventHandler<NewIncomingDataEvent> {
    /**
     * The {@link DataGraphManager} instance used for managing the data graph.
     */
    protected DataGraphManager graphManager;

    /**
     * Constructs a new GraphDataContextualizers instance.
     *
     * @param graphManager The {@link DataGraphManager} instance to be used for managing the data graph.
     */
    public GraphDataContextualizers(DataGraphManager graphManager) {
        this.graphManager = graphManager;
    }
}
