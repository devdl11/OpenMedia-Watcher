package fr.dl11.openmedia.events;

import fr.dl11.openmedia.exceptions.FailedEventHandling;

public interface EventHandler<T extends Event> {
    void handleEvent(T event) throws FailedEventHandling;
}
