package fr.dl11.openmedia.events;

public interface EventObserver<T extends Event> {
    void onEvent(T event);
}
