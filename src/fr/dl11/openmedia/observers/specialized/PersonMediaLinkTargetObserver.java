package fr.dl11.openmedia.observers.specialized;

import fr.dl11.openmedia.data.person.PersonMediaLinkData;
import fr.dl11.openmedia.events.data.person.NewPersonMediaLinkEvent;
import fr.dl11.openmedia.events.data.person.UpdatePersonMediaLinkEvent;
import fr.dl11.openmedia.observers.PersonObserver;

import java.util.Arrays;

/**
 * Observer that monitors person-media link events for a specific target media.
 *
 * <p>The {@code PersonMediaLinkTargetObserver} class extends {@link PersonObserver}
 * and listens for events related to the creation or update of links between persons
 * and a specific target media. It processes these events and triggers actions when the
 * target media matches the specified name.</p>
 */
public class PersonMediaLinkTargetObserver extends PersonObserver {
    private final String targetMediaName;

    /**
     * Constructs a new {@code PersonMediaLinkTargetObserver}.
     *
     * @param observerName    A unique name for this observer instance. Must not be null or empty.
     * @param targetMediaName The name of the target media to watch for in links. Must not be null or empty.
     * @throws IllegalArgumentException If {@code targetMediaName} is null or empty.
     */
    public PersonMediaLinkTargetObserver(String observerName, String targetMediaName) {
        super(observerName, Arrays.asList(NewPersonMediaLinkEvent.class, UpdatePersonMediaLinkEvent.class));
        if (targetMediaName == null || targetMediaName.isEmpty()) {
            throw new IllegalArgumentException("Target media name cannot be null or empty.");
        }
        this.targetMediaName = targetMediaName;
    }

    /**
     * Processes a link event for the specified person and media.
     *
     * @param personName      The name of the person involved in the link. Must not be null.
     * @param linkedMediaName The name of the media involved in the link. Must not be null.
     * @param action          A description of the action performed (e.g., "created new", "updated"). Must not be null.
     */
    private void processLink(String personName, String linkedMediaName, String action) {
        if (linkedMediaName.equalsIgnoreCase(targetMediaName)) {
            System.out.println("\nObserver '" + getObserverName() + "' triggered! \n");
            System.out.println("   Person '" + personName + "' " + action + " link with media: '" + targetMediaName + "'\n");
            System.out.print("> ");
        }
    }

    /**
     * Handles the event of a new person-media link being created.
     *
     * @param event The {@link NewPersonMediaLinkEvent} containing the details of the new link. Must not be null.
     */
    @Override
    protected void onNewPersonMediaLink(NewPersonMediaLinkEvent event) {
        PersonMediaLinkData linkData = event.getPersonMediaLinkData();
        if (linkData != null && linkData.getTarget() != null && linkData.getOrigin() != null) {
            processLink(linkData.getOrigin(), linkData.getTarget(), "created new");
        }
    }

    /**
     * Handles the event of a person-media link being updated.
     *
     * @param event The {@link UpdatePersonMediaLinkEvent} containing the details of the updated link. Must not be null.
     */
    @Override
    protected void onUpdatePersonMediaLink(UpdatePersonMediaLinkEvent event) {
        PersonMediaLinkData linkData = event.getPersonMediaLinkData();
        if (linkData != null && linkData.getTarget() != null && linkData.getOrigin() != null) {
            processLink(linkData.getOrigin(), linkData.getTarget(), "updated");
        }
    }
}