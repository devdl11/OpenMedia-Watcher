package fr.dl11.openmedia.common.events;

import fr.dl11.openmedia.datasource.data.RawData;
import fr.dl11.openmedia.events.BaseEvent;

public class NewRawDataEvent extends BaseEvent {
    private final RawData rawData;

    public NewRawDataEvent(RawData rawData) {
        this.rawData = rawData;
    }

    public RawData getRawData() {
        return rawData;
    }
}
