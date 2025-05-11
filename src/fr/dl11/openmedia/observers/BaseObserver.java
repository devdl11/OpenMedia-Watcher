package fr.dl11.openmedia.observers;

import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.EventObserver;

import java.util.Collection;

/**
 * Base class for observers that listen to specific events.
 *
 * <p>The {@code BaseObserver} class provides a foundation for creating
 * event observers. It automatically subscribes to a collection of event
 * types upon instantiation and allows unsubscribing from them when needed.</p>
 */
public abstract class BaseObserver implements EventObserver<Event> {
    private final String observerName;
    private final Collection<Class<? extends Event>> events;

    /**
     * Constructs a new {@code BaseObserver}.
     *
     * <p>This constructor initializes the observer with a name and a collection
     * of event types to listen for. It automatically subscribes the observer
     * to the specified events using the {@link EventBus}.</p>
     *
     * @param observerName The unique name of the observer. Must not be null.
     * @param events       A collection of event types to subscribe to. Must not be null.
     * @throws AssertionError If {@code events} or any event type within it is null.
     */
    public BaseObserver(String observerName, Collection<Class<? extends Event>> events) {
        assert events != null;

        EventBus eventBus = EventBus.getInstance();
        for (Class<? extends Event> event : events) {
            assert event != null;
            eventBus.subscribe(event, this);
        }

        this.observerName = observerName;
        this.events = events;
    }

    /**
     * Retrieves the name of the observer.
     *
     * @return The name of the observer.
     */
    public String getObserverName() {
        return observerName;
    }

    /**
     * Unsubscribes the observer from all events it was subscribed to.
     *
     * <p>This method removes the observer's subscriptions from the {@link EventBus}
     * for all event types specified during its construction.</p>
     */
    public void unsubscribe() {
        EventBus eventBus = EventBus.getInstance();
        for (Class<? extends Event> event : events) {
            assert event != null;
            eventBus.unsubscribe(event, this);
        }
    }
}