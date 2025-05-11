package fr.dl11.openmedia.core.handlers;

import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.EventHandler;
import fr.dl11.openmedia.events.data.person.*;
import fr.dl11.openmedia.exceptions.FailedEventHandling;

/**
 * Handler for processing person-related events.
 *
 * <p>This class implements {@link EventHandler} to handle various person-related events,
 * such as {@link NewPersonEvent}, {@link NewPersonMediaLinkEvent}, and others.
 * It subscribes to the {@link EventBus} and processes events by updating the
 * {@link DataGraphManager} with person-related data.
 */
public class PersonDataHandler implements EventHandler<Event> {

    /**
     * Constructs a new PersonDataHandler.
     *
     * <p>Registers this handler to the {@link EventBus} to listen for person-related events,
     * including {@link NewPersonEvent}, {@link NewPersonMediaLinkEvent}, and others.
     */
    public PersonDataHandler() {
        EventBus bus = EventBus.getInstance();

        bus.subscribe(NewPersonEvent.class, this);
        bus.subscribe(NewPersonMediaLinkEvent.class, this);
        bus.subscribe(NewPersonOrganizationLinkEvent.class, this);
        bus.subscribe(UpdatePersonMediaLinkEvent.class, this);
        bus.subscribe(UpdatePersonOrganizationLinkEvent.class, this);
    }

    /**
     * Handles an incoming event.
     *
     * <p>Processes the event based on its type:
     * <ul>
     *     <li>{@link NewPersonEvent}: Adds a new person to the {@link DataGraphManager}.</li>
     *     <li>{@link NewPersonMediaLinkEvent}: Adds a new link between a person and media.</li>
     *     <li>{@link NewPersonOrganizationLinkEvent}: Adds a new link between a person and an organization.</li>
     *     <li>{@link UpdatePersonMediaLinkEvent}: Updates an existing link between a person and media.</li>
     *     <li>{@link UpdatePersonOrganizationLinkEvent}: Updates an existing link between a person and an organization.</li>
     * </ul>
     * If the event type is unrecognized, it is ignored.
     *
     * @param event The {@link Event} to handle.
     * @throws FailedEventHandling If an error occurs while handling the event.
     */
    @Override
    public void handleEvent(Event event) throws FailedEventHandling {
        assert event != null;

        switch (event) {
            case NewPersonEvent newPersonEvent -> {
                event.consume();
                var graphManager = newPersonEvent.getDataGraphManager();

                graphManager.onAddPerson(newPersonEvent.getPerson());
            }
            case NewPersonMediaLinkEvent newPersonMediaLinkEvent -> {
                event.consume();
                var graphManager = newPersonMediaLinkEvent.getDataGraphManager();
                var personMediaLinkData = newPersonMediaLinkEvent.getPersonMediaLinkData();

                var personModel = graphManager.getPerson(personMediaLinkData.getOrigin());
                var mediaModel = graphManager.getMedia(personMediaLinkData.getTarget());

                if (personModel == null || mediaModel == null) {
                    return;
                }

                graphManager.onAddPersonMediaLink(personModel, mediaModel, personMediaLinkData.getValue(),
                        personMediaLinkData.getEqualityType());
            }
            case NewPersonOrganizationLinkEvent newPersonOrganizationLinkEvent -> {
                event.consume();
                var graphManager = newPersonOrganizationLinkEvent.getDataGraphManager();
                var personOrganizationLinkData = newPersonOrganizationLinkEvent.getPersonOrganizationLink();

                var personModel = graphManager.getPerson(personOrganizationLinkData.getOrigin());
                var organizationModel = graphManager.getOrganization(personOrganizationLinkData.getTarget());

                if (personModel == null || organizationModel == null) {
                    return;
                }

                graphManager.onAddPersonOrganizationLink(personModel, organizationModel, personOrganizationLinkData.getValue(),
                        personOrganizationLinkData.getEqualityType());
            }
            case UpdatePersonMediaLinkEvent updatePersonMediaLinkEvent -> {
                event.consume();
                var graphManager = updatePersonMediaLinkEvent.getDataGraphManager();
                var personMediaLinkData = updatePersonMediaLinkEvent.getPersonMediaLinkData();

                var personModel = graphManager.getPerson(personMediaLinkData.getOrigin());
                var mediaModel = graphManager.getMedia(personMediaLinkData.getTarget());

                if (personModel == null || mediaModel == null) {
                    return;
                }

                graphManager.onUpdatePersonMediaLink(personModel, mediaModel, personMediaLinkData.getValue(),
                        personMediaLinkData.getEqualityType());
            }
            case UpdatePersonOrganizationLinkEvent updatePersonOrganizationLinkEvent -> {
                event.consume();
                var graphManager = updatePersonOrganizationLinkEvent.getDataGraphManager();
                var personOrganizationLinkData = updatePersonOrganizationLinkEvent.getPersonOrganizationLink();

                var personModel = graphManager.getPerson(personOrganizationLinkData.getOrigin());
                var organizationModel = graphManager.getOrganization(personOrganizationLinkData.getTarget());

                if (personModel == null || organizationModel == null) {
                    return;
                }

                graphManager.onUpdatePersonOrganizationLink(personModel, organizationModel, personOrganizationLinkData.getValue(),
                        personOrganizationLinkData.getEqualityType());
            }
            default -> {
            }
        }
    }
}