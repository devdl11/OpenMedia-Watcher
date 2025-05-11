package fr.dl11.openmedia.observers;

import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.data.person.*;

import java.util.Collection;

/**
 * Abstract observer for person-related events.
 *
 * <p>The {@code PersonObserver} class extends {@link BaseObserver} and provides
 * a mechanism to handle various person-related events. It listens for events
 * and delegates the handling of specific event types to specialized methods.</p>
 */
public abstract class PersonObserver extends BaseObserver {

    /**
     * Constructs a new {@code PersonObserver}.
     *
     * <p>This constructor initializes the observer with a name and a collection
     * of event types to listen for. It automatically subscribes the observer
     * to the specified events using the {@link BaseObserver} constructor.</p>
     *
     * @param observerName The unique name of the observer. Must not be null or empty.
     * @param events       A collection of event types to subscribe to. Must not be null.
     */
    public PersonObserver(String observerName, Collection<Class<? extends Event>> events) {
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

    /**
     * Handles a new person event.
     *
     * <p>This method is intended to be overridden by subclasses to provide
     * specific behavior for handling {@link NewPersonEvent} instances.</p>
     *
     * @param event The {@link NewPersonEvent} to handle. Must not be null.
     */
    protected void onNewPerson(NewPersonEvent event) {
    }

    /**
     * Handles a new person-media link event.
     *
     * <p>This method is intended to be overridden by subclasses to provide
     * specific behavior for handling {@link NewPersonMediaLinkEvent} instances.</p>
     *
     * @param event The {@link NewPersonMediaLinkEvent} to handle. Must not be null.
     */
    protected void onNewPersonMediaLink(NewPersonMediaLinkEvent event) {
    }

    /**
     * Handles a new person-organization link event.
     *
     * <p>This method is intended to be overridden by subclasses to provide
     * specific behavior for handling {@link NewPersonOrganizationLinkEvent} instances.</p>
     *
     * @param event The {@link NewPersonOrganizationLinkEvent} to handle. Must not be null.
     */
    protected void onNewPersonOrganizationLink(NewPersonOrganizationLinkEvent event) {
    }

    /**
     * Handles an update to a person-media link event.
     *
     * <p>This method is intended to be overridden by subclasses to provide
     * specific behavior for handling {@link UpdatePersonMediaLinkEvent} instances.</p>
     *
     * @param event The {@link UpdatePersonMediaLinkEvent} to handle. Must not be null.
     */
    protected void onUpdatePersonMediaLink(UpdatePersonMediaLinkEvent event) {
    }

    /**
     * Handles an update to a person-organization link event.
     *
     * <p>This method is intended to be overridden by subclasses to provide
     * specific behavior for handling {@link UpdatePersonOrganizationLinkEvent} instances.</p>
     *
     * @param event The {@link UpdatePersonOrganizationLinkEvent} to handle. Must not be null.
     */
    protected void onUpdatePersonOrganizationLink(UpdatePersonOrganizationLinkEvent event) {
    }
}