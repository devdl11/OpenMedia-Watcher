package fr.dl11.openmedia.observers;

import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.data.organization.*;

import java.util.Collection;

public abstract class OrganizationObserver extends BaseObserver{
    public OrganizationObserver(String observerName, Collection<Class<? extends Event>> events) {
        super(observerName, events);
    }

    @Override
    public void onEvent(Event event) {
        assert event != null;

        switch (event) {
            case NewOrganizationEvent newOrganizationEvent -> {
                onNewOrganization(newOrganizationEvent);
            }
            case NewOrganizationMediaLinkEvent newOrganizationMediaLinkEvent -> {
                onNewOrganizationMediaLink(newOrganizationMediaLinkEvent);
            }
            case NewOrganizationOrganizationLinkEvent newOrganizationOrganizationLinkEvent -> {
                onNewOrganizationOrganizationLink(newOrganizationOrganizationLinkEvent);
            }
            case UpdateOrganizationMediaLinkEvent updateOrganizationMediaLinkEvent -> {
                onUpdateOrganizationMediaLink(updateOrganizationMediaLinkEvent);
            }
            case UpdateOrganizationOrganizationLinkEvent updateOrganizationOrganizationLinkEvent -> {
                onUpdateOrganizationOrganizationLink(updateOrganizationOrganizationLinkEvent);
            }
            default -> {}
        }
    }

    protected void onNewOrganization(NewOrganizationEvent event) {}

    protected void onNewOrganizationMediaLink(NewOrganizationMediaLinkEvent event) {}

    protected void onNewOrganizationOrganizationLink(NewOrganizationOrganizationLinkEvent event) {}

    protected void onUpdateOrganizationMediaLink(UpdateOrganizationMediaLinkEvent event) {}

    protected void onUpdateOrganizationOrganizationLink(UpdateOrganizationOrganizationLinkEvent event) {}
}
