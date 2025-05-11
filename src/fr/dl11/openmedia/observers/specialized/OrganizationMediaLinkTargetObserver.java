package fr.dl11.openmedia.observers.specialized;

import fr.dl11.openmedia.data.organization.OrganizationMediaLink;
import fr.dl11.openmedia.events.data.organization.NewOrganizationMediaLinkEvent;
import fr.dl11.openmedia.events.data.organization.UpdateOrganizationMediaLinkEvent;
import fr.dl11.openmedia.observers.OrganizationObserver;

import java.util.Arrays;

public class OrganizationMediaLinkTargetObserver extends OrganizationObserver {
    private final String targetMediaName;

    /**
     * Observer that triggers when an organization is linked (new or update) to a specific media.
     * @param observerName A unique name for this observer instance.
     * @param targetMediaName The name of the target media to watch for in links.
     */
    public OrganizationMediaLinkTargetObserver(String observerName, String targetMediaName) {
        super(observerName, Arrays.asList(NewOrganizationMediaLinkEvent.class, UpdateOrganizationMediaLinkEvent.class));
        if (targetMediaName == null || targetMediaName.isEmpty()) {
            throw new IllegalArgumentException("Target media name cannot be null or empty.");
        }
        this.targetMediaName = targetMediaName;
    }

    private void processLink(String orgName, String linkedMediaName, String action) {
        if (linkedMediaName.equalsIgnoreCase(targetMediaName)) {
            System.out.println("\n Observer '" + getObserverName() + "' triggered! \n");
            System.out.println("   Organization '" + orgName + "' " + action + " link with media: '" + targetMediaName + "'");
            System.out.print("> ");
        }
    }

    @Override
    protected void onNewOrganizationMediaLink(NewOrganizationMediaLinkEvent event) {
        OrganizationMediaLink linkData = event.getMediaLink();
        if (linkData != null && linkData.getTarget() != null && linkData.getOrigin() != null) {
            processLink(linkData.getOrigin(), linkData.getTarget(), "created new");
        }
    }

    @Override
    protected void onUpdateOrganizationMediaLink(UpdateOrganizationMediaLinkEvent event) {
        OrganizationMediaLink linkData = event.getOrganizationMediaLink();
        if (linkData != null && linkData.getTarget() != null && linkData.getOrigin() != null) {
            processLink(linkData.getOrigin(), linkData.getTarget(), "updated");
        }
    }
}
