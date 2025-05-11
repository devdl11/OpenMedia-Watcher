package fr.dl11.openmedia.data.organization;

import fr.dl11.openmedia.common.types.EqualityType;
import fr.dl11.openmedia.datasource.binding.BindToKey;
import fr.dl11.openmedia.datasource.parsers.elements.EnumParser;
import fr.dl11.openmedia.datasource.parsers.elements.StringParser;

/**
 * Represents a link between an organization and media.
 *
 * <p>The {@code OrganizationMediaLink} class is used to store and retrieve information
 * about the relationship between an organization and media, including the origin,
 * equality type, value, and target. The fields are bound to specific keys in a data
 * source using the {@link BindToKey} annotation.
 */
public class OrganizationMediaLink {

    /**
     * The origin of the link.
     *
     * <p>This field is bound to the key "origine" in the data source and is parsed
     * using the {@link StringParser} class.
     */
    @BindToKey(key = "origine", parser = StringParser.class)
    private final String origin;

    /**
     * The equality type of the link.
     *
     * <p>This field is bound to the key "qualificatif" in the data source and is parsed
     * using the {@link EnumParser} class.
     */
    @BindToKey(key = "qualificatif", parser = EnumParser.class)
    private final EqualityType equalityType;

    /**
     * The value associated with the link.
     *
     * <p>This field is bound to the key "valeur" in the data source and is parsed
     * using the {@link StringParser} class.
     */
    @BindToKey(key = "valeur", parser = StringParser.class)
    private final String value;

    /**
     * The target of the link.
     *
     * <p>This field is bound to the key "cible" in the data source and is parsed
     * using the {@link StringParser} class.
     */
    @BindToKey(key = "cible", parser = StringParser.class)
    private final String target;

    /**
     * Constructs a new {@code OrganizationMediaLink} object with default values.
     *
     * <p>The {@code origin}, {@code equalityType}, {@code value}, and {@code target}
     * fields are initialized to {@code null}.
     */
    public OrganizationMediaLink() {
        this.origin = null;
        this.equalityType = null;
        this.value = null;
        this.target = null;
    }

    /**
     * Retrieves the origin of the link.
     *
     * @return The origin of the link, or {@code null} if not set.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Retrieves the equality type of the link.
     *
     * @return The equality type of the link, or {@code null} if not set.
     */
    public EqualityType getEqualityType() {
        return equalityType;
    }

    /**
     * Retrieves the value associated with the link.
     *
     * @return The value of the link, or {@code null} if not set.
     */
    public String getValue() {
        return value;
    }

    /**
     * Retrieves the target of the link.
     *
     * @return The target of the link, or {@code null} if not set.
     */
    public String getTarget() {
        return target;
    }

    /**
     * Returns a string representation of the {@code OrganizationMediaLink} object.
     *
     * <p>The string includes the values of the {@code origin}, {@code equalityType},
     * {@code value}, and {@code target} fields.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "OrganizationMediaLink{" +
                "origin='" + origin + '\'' +
                ", equalityType=" + equalityType +
                ", value='" + value + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}