package fr.dl11.openmedia.observers.specialized;

import fr.dl11.openmedia.data.person.PersonMediaLinkData;
import fr.dl11.openmedia.events.data.person.NewPersonMediaLinkEvent;
import fr.dl11.openmedia.events.data.person.UpdatePersonMediaLinkEvent;
import fr.dl11.openmedia.observers.PersonObserver;

import java.util.Arrays;

public class PersonMediaLinkTargetObserver extends PersonObserver {
    private final String targetMediaName;

    /**
     * Observer that triggers when a person is linked (new or update) to a specific media.
     * @param observerName A unique name for this observer instance.
     * @param targetMediaName The name of the target media to watch for in links.
     */
    public PersonMediaLinkTargetObserver(String observerName, String targetMediaName) {
        super(observerName, Arrays.asList(NewPersonMediaLinkEvent.class, UpdatePersonMediaLinkEvent.class));
        if (targetMediaName == null || targetMediaName.isEmpty()) {
            throw new IllegalArgumentException("Target media name cannot be null or empty.");
        }
        this.targetMediaName = targetMediaName;
    }

    private void processLink(String personName, String linkedMediaName, String action) {
        if (linkedMediaName.equalsIgnoreCase(targetMediaName)) {
            System.out.println("\nObserver '" + getObserverName() + "' triggered! \n");
            System.out.println("   Person '" + personName + "' " + action + " link with media: '" + targetMediaName + "'\n");
            System.out.print("> ");
        }
    }

    @Override
    protected void onNewPersonMediaLink(NewPersonMediaLinkEvent event) {
        PersonMediaLinkData linkData = event.getPersonMediaLinkData();
        if (linkData != null && linkData.getTarget() != null && linkData.getOrigin() != null) {
            processLink(linkData.getOrigin(), linkData.getTarget(), "created new");
        }
    }

    @Override
    protected void onUpdatePersonMediaLink(UpdatePersonMediaLinkEvent event) {
        PersonMediaLinkData linkData = event.getPersonMediaLinkData();
        if (linkData != null && linkData.getTarget() != null && linkData.getOrigin() != null) {
            processLink(linkData.getOrigin(), linkData.getTarget(), "updated");
        }
    }
}
