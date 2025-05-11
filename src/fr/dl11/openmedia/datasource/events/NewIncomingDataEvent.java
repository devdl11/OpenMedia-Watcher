package fr.dl11.openmedia.datasource.events;

import fr.dl11.openmedia.events.BaseEvent;

public class NewIncomingDataEvent extends BaseEvent {
    public Object data;

    public NewIncomingDataEvent(Object data) {
        super();
        this.data = data;
    }
}
