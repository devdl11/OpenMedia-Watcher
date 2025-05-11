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

import java.util.Collection;
import java.util.List;

public class PersonDataContextualizer extends GraphDataContextualizers {
    public PersonDataContextualizer(DataGraphManager graphManager) {
        super(graphManager);
        EventBus.getInstance().subscribe(NewIncomingDataEvent.class, this);
    }

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