package fr.dl11.openmedia.observers;

import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.EventObserver;

import java.util.Collection;

public abstract class BaseObserver implements EventObserver<Event> {
    private final String observerName;
    private final Collection<Class<? extends Event>> events;

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

    public String getObserverName() {
        return observerName;
    }

    public void unsubscribe() {
        EventBus eventBus = EventBus.getInstance();
        for (Class<? extends Event> event : events) {
            assert event != null;
            eventBus.unsubscribe(event, this);
        }
    }
}
