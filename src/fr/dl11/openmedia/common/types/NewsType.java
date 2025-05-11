package fr.dl11.openmedia.common.types;

import fr.dl11.openmedia.common.utils.WithDefault;

/**
 * Enumeration representing different types of news sources.
 *
 * <p>This enum provides several news types, each associated with a keyword
 * that describes its meaning. The enum also implements the {@link WithDefault}
 * interface, allowing a default news type to be specified.
 */
public enum NewsType implements WithDefault<NewsType> {
    /**
     * Represents news type "Television".
     */
    kTV("Télévision"),

    /**
     * Represents news type "Radio".
     */
    kRadio("Radio"),

    /**
     * Represents news type "Website".
     */
    kWeb("Site"),

    /**
     * Represents news type "General press (political, economic)".
     */
    kGen("Presse (généraliste  politique  économique)"),

    /**
     * Represents no specific news type.
     */
    kNone("Aucun");

    private final String keyword;

    /**
     * Constructs a {@code NewsType} with the specified keyword.
     *
     * @param keyword the keyword associated with the news type.
     */
    NewsType(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Returns the keyword associated with this news type.
     *
     * @return the keyword as a {@code String}.
     */
    @Override
    public String toString() {
        return keyword;
    }

    /**
     * Returns the default news type.
     *
     * <p>The default news type is {@code kNone}.
     *
     * @return the default {@code NewsType}.
     */
    @Override
    public NewsType getDefault() {
        return kNone;
    }
}