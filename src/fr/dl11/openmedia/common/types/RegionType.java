package fr.dl11.openmedia.common.types;

import fr.dl11.openmedia.common.utils.WithDefault;

/**
 * Enumeration representing different types of regions.
 *
 * <p>This enum provides several region types, each associated with a keyword
 * that describes its meaning. The enum also implements the {@link WithDefault}
 * interface, allowing a default region type to be specified.
 */
public enum RegionType implements WithDefault<RegionType> {
    /**
     * Represents the national region type.
     */
    kNational("National"),

    /**
     * Represents the European region type.
     */
    kEurope("Europe"),

    /**
     * Represents the regional region type.
     */
    kRegion("Régional"),

    /**
     * Represents the international region type.
     */
    kInternational("International"),

    /**
     * Represents a specific country region type.
     */
    kCountry("Country");

    private final String keyword;

    /**
     * Constructs a {@code RegionType} with the specified keyword.
     *
     * @param keyword the keyword associated with the region type.
     */
    RegionType(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Returns the keyword associated with this region type.
     *
     * @return the keyword as a {@code String}.
     */
    @Override
    public String toString() {
        return keyword;
    }

    /**
     * Returns the default region type.
     *
     * <p>The default region type is {@code kNational}.
     *
     * @return the default {@code RegionType}.
     */
    @Override
    public RegionType getDefault() {
        return kNational;
    }
}