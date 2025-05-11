package fr.dl11.openmedia.core.handlers;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.data.organization.OrganizationMediaLink;
import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.EventHandler;
import fr.dl11.openmedia.events.data.organization.*;
import fr.dl11.openmedia.exceptions.FailedEventHandling;

public class OrganizationDataHandler implements EventHandler<Event> {
    public OrganizationDataHandler() {
        EventBus bus = EventBus.getInstance();

        bus.subscribe(NewOrganizationEvent.class, this);
        bus.subscribe(NewOrganizationMediaLinkEvent.class, this);
        bus.subscribe(NewOrganizationOrganizationLinkEvent.class, this);
        bus.subscribe(UpdateOrganizationMediaLinkEvent.class, this);
        bus.subscribe(UpdateOrganizationOrganizationLinkEvent.class, this);
    }

    @Override
    public void handleEvent(Event event) throws FailedEventHandling {
        assert event != null;

        switch (event) {
            case NewOrganizationEvent newOrganizationEvent -> {
                event.consume();

                var graphManager = newOrganizationEvent.getDataGraphManager();
                graphManager.onAddOrganization(newOrganizationEvent.getOrganization());
            }
            case NewOrganizationMediaLinkEvent newOrganizationMediaLinkEvent -> {
                event.consume();

                var graphManager = newOrganizationMediaLinkEvent.getDataGraphManager();
                var organizationMediaLinkData = newOrganizationMediaLinkEvent.getMediaLink();

                var organizationModel = graphManager.getOrganization(organizationMediaLinkData.getOrigin());
                var mediaModel = graphManager.getMedia(organizationMediaLinkData.getTarget());

                if (organizationModel == null || mediaModel == null) {
                    return;
                }

                graphManager.onAddOrganizationMediaLink(organizationModel, mediaModel, organizationMediaLinkData.getValue(),
                        organizationMediaLinkData.getEqualityType());
            }
            case NewOrganizationOrganizationLinkEvent newOrganizationOrganizationLinkEvent -> {
                event.consume();

                var graphManager = newOrganizationOrganizationLinkEvent.getDataGraphManager();
                var organizationOrganizationLinkData = newOrganizationOrganizationLinkEvent.getOrganizationOrganizationLink();

                var organizationModel = graphManager.getOrganization(organizationOrganizationLinkData.getOrigin());
                var targetOrganizationModel = graphManager.getOrganization(organizationOrganizationLinkData.getTarget());

                if (organizationModel == null || targetOrganizationModel == null) {
                    return;
                }

                graphManager.onAddOrganizationOrganizationLink(organizationModel, targetOrganizationModel, organizationOrganizationLinkData.getValue(),
                        organizationOrganizationLinkData.getEqualityType());
            }
            case UpdateOrganizationMediaLinkEvent updateOrganizationMediaLinkEvent -> {
                event.consume();

                var graphManager = updateOrganizationMediaLinkEvent.getDataGraphManager();
                var organizationMediaLinkData = updateOrganizationMediaLinkEvent.getOrganizationMediaLink();

                var organizationModel = graphManager.getOrganization(organizationMediaLinkData.getOrigin());
                var mediaModel = graphManager.getMedia(organizationMediaLinkData.getTarget());

                if (organizationModel == null || mediaModel == null) {
                    return;
                }

                graphManager.onUpdateOrganizationMediaLink(organizationModel, mediaModel, organizationMediaLinkData.getValue(),
                        organizationMediaLinkData.getEqualityType());
            }
            case UpdateOrganizationOrganizationLinkEvent updateOrganizationOrganizationLinkEvent -> {
                event.consume();

                var graphManager = updateOrganizationOrganizationLinkEvent.getDataGraphManager();
                var organizationOrganizationLinkData = updateOrganizationOrganizationLinkEvent.getOrganizationOrganizationLink();

                var organizationModel = graphManager.getOrganization(organizationOrganizationLinkData.getOrigin());
                var targetOrganizationModel = graphManager.getOrganization(organizationOrganizationLinkData.getTarget());

                if (organizationModel == null || targetOrganizationModel == null) {
                    return;
                }

                graphManager.onUpdateOrganizationOrganizationLink(organizationModel, targetOrganizationModel, organizationOrganizationLinkData.getValue(),
                        organizationOrganizationLinkData.getEqualityType());
            }
            default -> {}
        }
    }
}
