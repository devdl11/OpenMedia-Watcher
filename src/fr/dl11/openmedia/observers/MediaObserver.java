package fr.dl11.openmedia.observers;

import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.data.NewMediaEvent;

import java.util.Collection;

public abstract class MediaObserver extends BaseObserver {
    public MediaObserver(String name, Collection<Class<? extends Event>> events) {
        super(name, events);
    }

    @Override
    public void onEvent(Event event) {
        assert event != null;

        if (event instanceof NewMediaEvent newMediaEvent) {
            onNewMedia(newMediaEvent);
        }
    }

    protected void onNewMedia(NewMediaEvent event) {}
}
