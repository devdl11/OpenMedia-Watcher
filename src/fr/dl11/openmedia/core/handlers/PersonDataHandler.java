package fr.dl11.openmedia.core.handlers;

import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.events.Event;
import fr.dl11.openmedia.events.EventHandler;
import fr.dl11.openmedia.events.data.person.*;
import fr.dl11.openmedia.exceptions.FailedEventHandling;

public class PersonDataHandler implements EventHandler<Event> {
    public PersonDataHandler() {
        EventBus bus = EventBus.getInstance();

        bus.subscribe(NewPersonEvent.class, this);
        bus.subscribe(NewPersonMediaLinkEvent.class, this);
        bus.subscribe(NewPersonOrganizationLinkEvent.class, this);
        bus.subscribe(UpdatePersonMediaLinkEvent.class, this);
        bus.subscribe(UpdatePersonOrganizationLinkEvent.class, this);
    }

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
