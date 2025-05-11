package fr.dl11.openmedia.observers;

import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.data.NewMediaEvent;

import java.util.Collection;

/**
 * Abstract observer for media-related events.
 *
 * <p>The {@code MediaObserver} class extends {@link BaseObserver} and provides
 * a mechanism to handle media-related events. It listens for events and delegates
 * the handling of new media events to a specialized method.</p>
 */
public abstract class MediaObserver extends BaseObserver {

    /**
     * Constructs a new {@code MediaObserver}.
     *
     * <p>This constructor initializes the observer with a name and a collection
     * of event types to listen for. It automatically subscribes the observer
     * to the specified events using the {@link BaseObserver} constructor.</p>
     *
     * @param name   The unique name of the observer. Must not be null or empty.
     * @param events A collection of event types to subscribe to. Must not be null.
     */
    public MediaObserver(String name, Collection<Class<? extends Event>> events) {
        super(name, events);
    }

    /**
     * Handles an incoming event.
     *
     * <p>This method checks if the event is an instance of {@link NewMediaEvent}.
     * If it is, the {@link #onNewMedia(NewMediaEvent)} method is called to handle
     * the event. This ensures that only relevant events are processed.</p>
     *
     * @param event The event to handle. Must not be null.
     */
    @Override
    public void onEvent(Event event) {
        assert event != null;

        if (event instanceof NewMediaEvent newMediaEvent) {
            onNewMedia(newMediaEvent);
        }
    }

    /**
     * Handles a new media event.
     *
     * <p>This method is intended to be overridden by subclasses to provide
     * specific behavior for handling {@link NewMediaEvent} instances.</p>
     *
     * @param event The {@link NewMediaEvent} to handle. Must not be null.
     */
    protected void onNewMedia(NewMediaEvent event) {
    }
}