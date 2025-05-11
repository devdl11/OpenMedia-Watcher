package fr.dl11.openmedia.contextualizers;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.data.person.PersonData;
import fr.dl11.openmedia.data.person.PersonMediaLinkData;
import fr.dl11.openmedia.data.person.PersonOrganizationLink;
import fr.dl11.openmedia.datasource.events.NewIncomingDataEvent;
import fr.dl11.openmedia.events.data.person.*;
import fr.dl11.openmedia.exceptions.FailedEventHandling;
import fr.dl11.openmedia.models.MediaModel;
import fr.dl11.openmedia.models.OrganizationModel;
import fr.dl11.openmedia.models.PersonModel;

/**
 * Contextualizer for handling person-related data events.
 *
 * <p>This class extends {@link GraphDataContextualizers} and subscribes to
 * {@link NewIncomingDataEvent} events. It processes incoming data related to
 * persons, such as {@link PersonData}, {@link PersonMediaLinkData}, and
 * {@link PersonOrganizationLink}, and publishes corresponding events to the
 * {@link EventBus}.
 */
public class PersonDataContextualizer extends GraphDataContextualizers {

    /**
     * Constructs a new PersonDataContextualizer.
     *
     * <p>Subscribes to {@link NewIncomingDataEvent} events using the {@link EventBus}.
     *
     * @param graphManager The {@link DataGraphManager} instance used for managing the data graph.
     */
    public PersonDataContextualizer(DataGraphManager graphManager) {
        super(graphManager);
        EventBus.getInstance().subscribe(NewIncomingDataEvent.class, this);
    }

    /**
     * Handles a {@link NewIncomingDataEvent}.
     *
     * <p>Processes the event based on the type of data it contains:
     * <ul>
     *     <li>If the data is of type {@link PersonData}, it publishes a {@link NewPersonEvent}.</li>
     *     <li>If the data is of type {@link PersonMediaLinkData}, it determines whether to publish
     *     a {@link NewPersonMediaLinkEvent} or an {@link UpdatePersonMediaLinkEvent}.</li>
     *     <li>If the data is of type {@link PersonOrganizationLink}, it determines whether to publish
     *     a {@link NewPersonOrganizationLinkEvent} or an {@link UpdatePersonOrganizationLinkEvent}.</li>
     *     <li>Ignores the event if the data type is unrecognized.</li>
     * </ul>
     *
     * @param event The {@link NewIncomingDataEvent} to handle.
     * @throws FailedEventHandling If an error occurs while handling the event.
     */
    @Override
    public void handleEvent(NewIncomingDataEvent event) throws FailedEventHandling {
        assert event != null && event.data != null;

        Object data = event.data;
        EventBus bus = EventBus.getInstance();

        switch (data) {
            case PersonData personData -> {
                event.consume();
                bus.publish(new NewPersonEvent(personData, graphManager));
            }
            case PersonMediaLinkData personMediaLinkData -> {
                event.consume();

                PersonModel personModel = graphManager.getPerson(personMediaLinkData.getOrigin());
                MediaModel mediaModel = graphManager.getMedia(personMediaLinkData.getTarget());
                if (personModel == null || mediaModel == null) {
                    return;
                }

                if (graphManager.hasPersonMediaLink(personModel, mediaModel)) {
                    bus.publish(new UpdatePersonMediaLinkEvent(personMediaLinkData, graphManager));
                } else {
                    bus.publish(new NewPersonMediaLinkEvent(personMediaLinkData, graphManager));
                }
            }
            case PersonOrganizationLink personOrganizationLink -> {
                event.consume();

                PersonModel personModel = graphManager.getPerson(personOrganizationLink.getOrigin());
                OrganizationModel organizationModel = graphManager.getOrganization(personOrganizationLink.getTarget());

                if (personModel == null || organizationModel == null) {
                    return;
                }

                if (graphManager.hasPersonOrganizationLink(personModel, organizationModel)) {
                    bus.publish(new UpdatePersonOrganizationLinkEvent(personOrganizationLink, graphManager));
                } else {
                    bus.publish(new NewPersonOrganizationLinkEvent(personOrganizationLink, graphManager));
                }
            }
            default -> {
            }
        }
    }
}