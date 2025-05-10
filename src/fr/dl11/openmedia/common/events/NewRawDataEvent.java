package fr.dl11.openmedia.common.events;

import fr.dl11.openmedia.datasource.data.RawData;
import fr.dl11.openmedia.events.Event;

public class NewRawDataEvent implements Event {
    private final long timestamp;
    private final RawData rawData;

    public NewRawDataEvent(RawData rawData) {
        this.timestamp = System.currentTimeMillis();
        this.rawData = rawData;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public RawData getRawData() {
        return rawData;
    }
}
