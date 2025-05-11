package fr.dl11.openmedia.events.data.organization;

import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.organization.OrganizationMediaLink;
import fr.dl11.openmedia.events.BaseEvent;

public class NewOrganizationMediaLinkEvent extends BaseEvent {
   private final OrganizationMediaLink mediaLink;
   private final DataGraphManager dataGraphManager;

   public NewOrganizationMediaLinkEvent(OrganizationMediaLink mediaLink, DataGraphManager dataGraphManager) {
         this.mediaLink = mediaLink;
         this.dataGraphManager = dataGraphManager;
    }

    public OrganizationMediaLink getMediaLink() {
            return mediaLink;
     }

    public DataGraphManager getDataGraphManager() {
            return dataGraphManager;
     }
}
