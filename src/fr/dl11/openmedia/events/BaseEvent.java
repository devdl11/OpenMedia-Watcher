package fr.dl11.openmedia.events;

public abstract class BaseEvent implements Event {
    private final long timestamp;
    private boolean consumed = false;

    public BaseEvent(long timestamp) {
        this.timestamp = timestamp;
    }

    public BaseEvent() {
        this(System.currentTimeMillis());
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean isConsumed() {
        return consumed;
    }

    @Override
    public void consume() {
        consumed = true;
    }
}
