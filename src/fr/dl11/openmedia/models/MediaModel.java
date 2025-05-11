package fr.dl11.openmedia.models;

import fr.dl11.openmedia.data.MediaData;
import fr.dl11.openmedia.data.PublicationData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represents a media model containing media data and associated publications.
 *
 * <p>The {@code MediaModel} class provides a structure to manage media data
 * and its related publications. It allows adding publications and retrieving
 * both the media data and the list of publications.</p>
 */
public class MediaModel {
    private final MediaData mediaData;
    private final List<PublicationData> publications;

    /**
     * Constructs a new {@code MediaModel} with the specified media data.
     *
     * @param mediaData The media data associated with this model. Must not be null.
     */
    public MediaModel(MediaData mediaData) {
        this.mediaData = mediaData;
        publications = new ArrayList<>();
    }

    /**
     * Retrieves the media data associated with this model.
     *
     * @return The {@link MediaData} object representing the media data.
     */
    public MediaData getMediaData() {
        return mediaData;
    }

    /**
     * Adds a publication to the list of publications associated with this model.
     *
     * @param publication The {@link PublicationData} object to be added. Must not be null.
     */
    public void addPublication(PublicationData publication) {
        publications.add(publication);
    }

    /**
     * Retrieves an unmodifiable collection of publications associated with this model.
     *
     * @return A {@link Collection} of {@link PublicationData} objects representing the publications.
     */
    public Collection<PublicationData> getPublications() {
        return Collections.unmodifiableCollection(publications);
    }
}
