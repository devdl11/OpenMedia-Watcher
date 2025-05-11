package fr.dl11.openmedia.models;

import fr.dl11.openmedia.data.MediaData;
import fr.dl11.openmedia.data.PublicationData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MediaModel {
    private final MediaData mediaData;
    private final List<PublicationData> publications;

    public MediaModel(MediaData mediaData) {
        this.mediaData = mediaData;
        publications = new ArrayList<>();
    }

    public MediaData getMediaData() {
        return mediaData;
    }

    public void addPublication(PublicationData publication) {
        publications.add(publication);
    }

    public Collection<PublicationData> getPublications() {
        return Collections.unmodifiableCollection(publications);
    }
}
