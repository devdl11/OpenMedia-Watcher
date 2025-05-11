package fr.dl11.openmedia.observers;

import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.data.person.*;

import java.util.Collection;

public abstract class PersonObserver extends BaseObserver {
    public PersonObserver(String observerName, Collection<Class<? extends Event>> events) {
        super(observerName, events);
    }

    @Override
    public void onEvent(Event event) {
        assert event != null;

        switch (event) {
            case NewPersonEvent newPersonEvent -> {
                onNewPerson(newPersonEvent);
            }
            case NewPersonMediaLinkEvent newPersonMediaLinkEvent -> {
                onNewPersonMediaLink(newPersonMediaLinkEvent);
            }
            case NewPersonOrganizationLinkEvent newPersonOrganizationLinkEvent -> {
                onNewPersonOrganizationLink(newPersonOrganizationLinkEvent);
            }
            case UpdatePersonMediaLinkEvent updatePersonMediaLinkEvent -> {
                onUpdatePersonMediaLink(updatePersonMediaLinkEvent);
            }
            case UpdatePersonOrganizationLinkEvent updatePersonOrganizationLinkEvent -> {
                onUpdatePersonOrganizationLink(updatePersonOrganizationLinkEvent);
            }
            default -> {
            }
        }
    }

    protected void onNewPerson(NewPersonEvent event) {}

    protected void onNewPersonMediaLink(NewPersonMediaLinkEvent event) {}

    protected void onNewPersonOrganizationLink(NewPersonOrganizationLinkEvent event) {}

    protected void onUpdatePersonMediaLink(UpdatePersonMediaLinkEvent event) {}

    protected void onUpdatePersonOrganizationLink(UpdatePersonOrganizationLinkEvent event) {}
}
