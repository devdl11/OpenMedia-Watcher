package fr.dl11.openmedia.common.types;

import fr.dl11.openmedia.common.utils.WithDefault;

/**
 * Enumeration representing different types of equality.
 *
 * <p>This enum provides several equality types, each associated with a keyword
 * that describes its meaning. The enum also implements the {@link WithDefault}
 * interface, allowing a default equality type to be specified.
 */
public enum EqualityType implements WithDefault<EqualityType> {
    /**
     * Represents equality type "equal to".
     */
    kEqual("égal à"),

    /**
     * Represents equality type "control".
     */
    kControl("contrôle"),

    /**
     * Represents equality type "participate".
     */
    kParticipate("participe"),

    /**
     * Represents equality type "greater than".
     */
    kGreaterThan("supérieur à"),

    /**
     * Represents equality type "less than".
     */
    kLessThan("inférieur à");

    private final String keyword;

    /**
     * Constructs an {@code EqualityType} with the specified keyword.
     *
     * @param keyword the keyword associated with the equality type.
     */
    EqualityType(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Returns the keyword associated with this equality type.
     *
     * @return the keyword as a {@code String}.
     */
    @Override
    public String toString() {
        return keyword;
    }

    /**
     * Returns the default equality type.
     *
     * <p>The default equality type is {@code kControl}.
     *
     * @return the default {@code EqualityType}.
     */
    @Override
    public EqualityType getDefault() {
        return kControl;
    }
}