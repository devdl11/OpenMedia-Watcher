package fr.dl11.openmedia.common.types;

import fr.dl11.openmedia.common.utils.WithDefault;

/**
 * Enumeration representing different types of publications.
 *
 * <p>This enum provides several publication types, each associated with a name
 * that describes its meaning. The enum also implements the {@link WithDefault}
 * interface, allowing a default publication type to be specified.
 */
public enum PublicationType implements WithDefault<PublicationType> {
    /**
     * Represents a publication type "Article".
     */
    kArticle("Article"),

    /**
     * Represents a publication type "Interview".
     */
    kInterview("Interview"),

    /**
     * Represents a publication type "Reporting".
     */
    kReporting("Reporting");

    private final String name;

    /**
     * Constructs a {@code PublicationType} with the specified name.
     *
     * @param name the name associated with the publication type.
     */
    PublicationType(String name) {
        this.name = name;
    }

    /**
     * Returns the name associated with this publication type.
     *
     * @return the name as a {@code String}.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the default publication type.
     *
     * <p>The default publication type is {@code kArticle}.
     *
     * @return the default {@code PublicationType}.
     */
    @Override
    public PublicationType getDefault() {
        return kArticle;
    }
}