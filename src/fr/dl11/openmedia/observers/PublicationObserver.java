package fr.dl11.openmedia.observers;

import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.data.NewPublicationEvent;

import java.util.Collection;

public abstract class PublicationObserver extends BaseObserver {
    public PublicationObserver(String observerName, Collection<Class<? extends Event>> events) {
        super(observerName, events);
    }

    @Override
    public void onEvent(Event event) {
        assert event != null;

        if (event instanceof NewPublicationEvent newPublicationEvent) {
            onNewPublication(newPublicationEvent);
        }
    }

    protected void onNewPublication(NewPublicationEvent event) {}
}
