package fr.dl11.openmedia.observers;

import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.data.NewPublicationEvent;

import java.util.Collection;

/**
 * Abstract observer for publication-related events.
 *
 * <p>The {@code PublicationObserver} class extends {@link BaseObserver} and provides
 * a mechanism to handle publication-related events. It listens for events and delegates
 * the handling of new publication events to a specialized method.</p>
 */
public abstract class PublicationObserver extends BaseObserver {

    /**
     * Constructs a new {@code PublicationObserver}.
     *
     * <p>This constructor initializes the observer with a name and a collection
     * of event types to listen for. It automatically subscribes the observer
     * to the specified events using the {@link BaseObserver} constructor.</p>
     *
     * @param observerName The unique name of the observer. Must not be null or empty.
     * @param events       A collection of event types to subscribe to. Must not be null.
     */
    public PublicationObserver(String observerName, Collection<Class<? extends Event>> events) {
        super(observerName, events);
    }

    /**
     * Handles an incoming event.
     *
     * <p>This method checks if the event is an instance of {@link NewPublicationEvent}.
     * If it is, the {@link #onNewPublication(NewPublicationEvent)} method is called to handle
     * the event. This ensures that only relevant events are processed.</p>
     *
     * @param event The event to handle. Must not be null.
     */
    @Override
    public void onEvent(Event event) {
        assert event != null;

        if (event instanceof NewPublicationEvent newPublicationEvent) {
            onNewPublication(newPublicationEvent);
        }
    }

    /**
     * Handles a new publication event.
     *
     * <p>This method is intended to be overridden by subclasses to provide
     * specific behavior for handling {@link NewPublicationEvent} instances.</p>
     *
     * @param event The {@link NewPublicationEvent} to handle. Must not be null.
     */
    protected void onNewPublication(NewPublicationEvent event) {
    }
}