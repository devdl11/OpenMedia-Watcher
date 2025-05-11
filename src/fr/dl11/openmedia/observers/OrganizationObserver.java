package fr.dl11.openmedia.observers;

import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.data.organization.*;

import java.util.Collection;

/**
 * Abstract observer for organization-related events.
 *
 * <p>The {@code OrganizationObserver} class extends {@link BaseObserver} and provides
 * a mechanism to handle various organization-related events. It listens for events
 * and delegates the handling of specific event types to specialized methods.</p>
 */
public abstract class OrganizationObserver extends BaseObserver {

    /**
     * Constructs a new {@code OrganizationObserver}.
     *
     * <p>This constructor initializes the observer with a name and a collection
     * of event types to listen for. It automatically subscribes the observer
     * to the specified events using the {@link BaseObserver} constructor.</p>
     *
     * @param observerName The unique name of the observer. Must not be null or empty.
     * @param events       A collection of event types to subscribe to. Must not be null.
     */
    public OrganizationObserver(String observerName, Collection<Class<? extends Event>> events) {
        super(observerName, events);
    }

    /**
     * Handles an incoming event.
     *
     * <p>This method checks the type of the event and delegates its handling
     * to the appropriate specialized method based on the event type.</p>
     *
     * @param event The event to handle. Must not be null.
     */
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
            default -> {
            }
        }
    }

    /**
     * Handles a new organization event.
     *
     * <p>This method is intended to be overridden by subclasses to provide
     * specific behavior for handling {@link NewOrganizationEvent} instances.</p>
     *
     * @param event The {@link NewOrganizationEvent} to handle. Must not be null.
     */
    protected void onNewOrganization(NewOrganizationEvent event) {
    }

    /**
     * Handles a new organization-media link event.
     *
     * <p>This method is intended to be overridden by subclasses to provide
     * specific behavior for handling {@link NewOrganizationMediaLinkEvent} instances.</p>
     *
     * @param event The {@link NewOrganizationMediaLinkEvent} to handle. Must not be null.
     */
    protected void onNewOrganizationMediaLink(NewOrganizationMediaLinkEvent event) {
    }

    /**
     * Handles a new organization-organization link event.
     *
     * <p>This method is intended to be overridden by subclasses to provide
     * specific behavior for handling {@link NewOrganizationOrganizationLinkEvent} instances.</p>
     *
     * @param event The {@link NewOrganizationOrganizationLinkEvent} to handle. Must not be null.
     */
    protected void onNewOrganizationOrganizationLink(NewOrganizationOrganizationLinkEvent event) {
    }

    /**
     * Handles an update to an organization-media link event.
     *
     * <p>This method is intended to be overridden by subclasses to provide
     * specific behavior for handling {@link UpdateOrganizationMediaLinkEvent} instances.</p>
     *
     * @param event The {@link UpdateOrganizationMediaLinkEvent} to handle. Must not be null.
     */
    protected void onUpdateOrganizationMediaLink(UpdateOrganizationMediaLinkEvent event) {
    }

    /**
     * Handles an update to an organization-organization link event.
     *
     * <p>This method is intended to be overridden by subclasses to provide
     * specific behavior for handling {@link UpdateOrganizationOrganizationLinkEvent} instances.</p>
     *
     * @param event The {@link UpdateOrganizationOrganizationLinkEvent} to handle. Must not be null.
     */
    protected void onUpdateOrganizationOrganizationLink(UpdateOrganizationOrganizationLinkEvent event) {
    }
}