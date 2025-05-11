package fr.dl11.openmedia.contextualizers;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.data.organization.OrganizationData;
import fr.dl11.openmedia.data.organization.OrganizationMediaLink;
import fr.dl11.openmedia.data.organization.OrganizationOrganizationLink;
import fr.dl11.openmedia.datasource.events.NewIncomingDataEvent;
import fr.dl11.openmedia.events.data.organization.*;
import fr.dl11.openmedia.exceptions.FailedEventHandling;
import fr.dl11.openmedia.models.MediaModel;
import fr.dl11.openmedia.models.OrganizationModel;

/**
 * Contextualizer for handling organization-related data events.
 *
 * <p>This class extends {@link GraphDataContextualizers} and subscribes to
 * {@link NewIncomingDataEvent} events. It processes incoming data related to
 * organizations, such as {@link OrganizationData}, {@link OrganizationMediaLink},
 * and {@link OrganizationOrganizationLink}, and publishes corresponding events
 * to the {@link EventBus}.
 */
public class OrganizationDataContextualizer extends GraphDataContextualizers {

    /**
     * Constructs a new OrganizationDataContextualizer.
     *
     * <p>Subscribes to {@link NewIncomingDataEvent} events using the {@link EventBus}.
     *
     * @param graphManager The {@link DataGraphManager} instance used for managing the data graph.
     */
    public OrganizationDataContextualizer(DataGraphManager graphManager) {
        super(graphManager);
        EventBus.getInstance().subscribe(NewIncomingDataEvent.class, this);
    }

    /**
     * Handles a {@link NewIncomingDataEvent}.
     *
     * <p>Processes the event based on the type of data it contains:
     * <ul>
     *     <li>If the data is of type {@link OrganizationData}, it publishes a {@link NewOrganizationEvent}.</li>
     *     <li>If the data is of type {@link OrganizationMediaLink}, it determines whether to publish
     *     a {@link NewOrganizationMediaLinkEvent} or an {@link UpdateOrganizationMediaLinkEvent}.</li>
     *     <li>If the data is of type {@link OrganizationOrganizationLink}, it determines whether to publish
     *     a {@link NewOrganizationOrganizationLinkEvent} or an {@link UpdateOrganizationOrganizationLinkEvent}.</li>
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
            case OrganizationData organizationData -> {
                event.consume();
                bus.publish(new NewOrganizationEvent(organizationData, graphManager));
            }
            case OrganizationMediaLink organizationMediaLink -> {
                event.consume();

                OrganizationModel organizationModel = graphManager.getOrganization(organizationMediaLink.getOrigin());
                MediaModel mediaModel = graphManager.getMedia(organizationMediaLink.getTarget());

                if (organizationModel == null || mediaModel == null) {
                    return;
                }

                if (graphManager.hasOrganizationMediaLink(organizationModel, mediaModel)) {
                    bus.publish(new UpdateOrganizationMediaLinkEvent(organizationMediaLink, graphManager));
                } else {
                    bus.publish(new NewOrganizationMediaLinkEvent(organizationMediaLink, graphManager));
                }
            }
            case OrganizationOrganizationLink organizationOrganizationLink -> {
                event.consume();

                OrganizationModel organizationModel = graphManager.getOrganization(organizationOrganizationLink.getOrigin());
                OrganizationModel targetOrganizationModel = graphManager.getOrganization(organizationOrganizationLink.getTarget());

                if (organizationModel == null || targetOrganizationModel == null) {
                    return;
                }

                if (graphManager.hasOrganizationOrganizationLink(organizationModel, targetOrganizationModel)) {
                    bus.publish(new UpdateOrganizationOrganizationLinkEvent(organizationOrganizationLink, graphManager));
                } else {
                    bus.publish(new NewOrganizationOrganizationLinkEvent(organizationOrganizationLink, graphManager));
                }
            }
            default -> {
            }
        }
    }
}