package fr.dl11.openmedia.common.types;

import fr.dl11.openmedia.common.utils.WithDefault;

/**
 * Enumeration representing different types of periodicity.
 *
 * <p>This enum provides several periodicity types, each associated with a keyword
 * that describes its meaning. The enum also implements the {@link WithDefault}
 * interface, allowing a default periodicity type to be specified.
 */
public enum PeriodicityType implements WithDefault<PeriodicityType> {
    /**
     * Represents a daily periodicity type.
     */
    kDaily("Quotidien"),

    /**
     * Represents a weekly periodicity type.
     */
    kWeekly("Hebdomadaire"),

    /**
     * Represents a monthly periodicity type.
     */
    kMonthly("Mensuel");

    private final String keyword;

    /**
     * Constructs a {@code PeriodicityType} with the specified keyword.
     *
     * @param keyword the keyword associated with the periodicity type.
     */
    PeriodicityType(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Returns the keyword associated with this periodicity type.
     *
     * @return the keyword as a {@code String}.
     */
    @Override
    public String toString() {
        return keyword;
    }

    /**
     * Returns the default periodicity type.
     *
     * <p>The default periodicity type is {@code kDaily}.
     *
     * @return the default {@code PeriodicityType}.
     */
    @Override
    public PeriodicityType getDefault() {
        return kDaily;
    }
}