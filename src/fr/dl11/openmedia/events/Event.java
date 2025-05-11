package fr.dl11.openmedia.events;

public interface Event {
    long getTimestamp();

    boolean isConsumed();
    void consume();
}
