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

public class OrganizationDataContextualizer extends GraphDataContextualizers {
    public OrganizationDataContextualizer(DataGraphManager graphManager) {
        super(graphManager);
        EventBus.getInstance().subscribe(NewIncomingDataEvent.class, this);
    }

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
