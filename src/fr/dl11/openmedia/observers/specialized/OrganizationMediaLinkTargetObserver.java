package fr.dl11.openmedia.observers.specialized;

import fr.dl11.openmedia.data.organization.OrganizationMediaLink;
import fr.dl11.openmedia.events.data.organization.NewOrganizationMediaLinkEvent;
import fr.dl11.openmedia.events.data.organization.UpdateOrganizationMediaLinkEvent;
import fr.dl11.openmedia.observers.OrganizationObserver;

import java.util.Arrays;

/**
 * Observer that monitors organization-media link events for a specific target media.
 *
 * <p>The {@code OrganizationMediaLinkTargetObserver} class extends {@link OrganizationObserver}
 * and listens for events related to the creation or update of links between organizations
 * and a specific target media. It processes these events and triggers actions when the
 * target media matches the specified name.</p>
 */
public class OrganizationMediaLinkTargetObserver extends OrganizationObserver {
    private final String targetMediaName;

    /**
     * Constructs a new {@code OrganizationMediaLinkTargetObserver}.
     *
     * @param observerName    A unique name for this observer instance. Must not be null or empty.
     * @param targetMediaName The name of the target media to watch for in links. Must not be null or empty.
     * @throws IllegalArgumentException If {@code targetMediaName} is null or empty.
     */
    public OrganizationMediaLinkTargetObserver(String observerName, String targetMediaName) {
        super(observerName, Arrays.asList(NewOrganizationMediaLinkEvent.class, UpdateOrganizationMediaLinkEvent.class));
        if (targetMediaName == null || targetMediaName.isEmpty()) {
            throw new IllegalArgumentException("Target media name cannot be null or empty.");
        }
        this.targetMediaName = targetMediaName;
    }

    /**
     * Processes a link event for the specified organization and media.
     *
     * @param orgName         The name of the organization involved in the link. Must not be null.
     * @param linkedMediaName The name of the media involved in the link. Must not be null.
     * @param action          A description of the action performed (e.g., "created new", "updated"). Must not be null.
     */
    private void processLink(String orgName, String linkedMediaName, String action) {
        if (linkedMediaName.equalsIgnoreCase(targetMediaName)) {
            System.out.println("\n Observer '" + getObserverName() + "' triggered! \n");
            System.out.println("   Organization '" + orgName + "' " + action + " link with media: '" + targetMediaName + "'");
            System.out.print("> ");
        }
    }

    /**
     * Handles the event of a new organization-media link being created.
     *
     * @param event The {@link NewOrganizationMediaLinkEvent} containing the details of the new link. Must not be null.
     */
    @Override
    protected void onNewOrganizationMediaLink(NewOrganizationMediaLinkEvent event) {
        OrganizationMediaLink linkData = event.getMediaLink();
        if (linkData != null && linkData.getTarget() != null && linkData.getOrigin() != null) {
            processLink(linkData.getOrigin(), linkData.getTarget(), "created new");
        }
    }

    /**
     * Handles the event of an organization-media link being updated.
     *
     * @param event The {@link UpdateOrganizationMediaLinkEvent} containing the details of the updated link. Must not be null.
     */
    @Override
    protected void onUpdateOrganizationMediaLink(UpdateOrganizationMediaLinkEvent event) {
        OrganizationMediaLink linkData = event.getOrganizationMediaLink();
        if (linkData != null && linkData.getTarget() != null && linkData.getOrigin() != null) {
            processLink(linkData.getOrigin(), linkData.getTarget(), "updated");
        }
    }
}