package fr.dl11.openmedia.data.organization;

import fr.dl11.openmedia.common.types.EqualityType;
import fr.dl11.openmedia.datasource.binding.BindToKey;
import fr.dl11.openmedia.datasource.parsers.elements.EnumParser;
import fr.dl11.openmedia.datasource.parsers.elements.StringParser;

/**
 * Represents a link between two organizations.
 *
 * <p>The {@code OrganizationOrganizationLink} class is used to store and retrieve information
 * about the relationship between two organizations, including the origin, equality type,
 * value, target, and an optional comment. The fields are bound to specific keys in a data
 * source using the {@link BindToKey} annotation.
 */
public class OrganizationOrganizationLink {

    /**
     * The origin of the link.
     *
     * <p>This field is bound to the key "origine" in the data source and is parsed
     * using the {@link StringParser} class.
     */
    @BindToKey(key = "origine", parser = StringParser.class)
    public final String origin;

    /**
     * The equality type of the link.
     *
     * <p>This field is bound to the key "qualificatif" in the data source and is parsed
     * using the {@link EnumParser} class.
     */
    @BindToKey(key = "qualificatif", parser = EnumParser.class)
    public final EqualityType equalityType;

    /**
     * The value associated with the link.
     *
     * <p>This field is bound to the key "valeur" in the data source and is parsed
     * using the {@link StringParser} class.
     */
    @BindToKey(key = "valeur", parser = StringParser.class)
    public final String value;

    /**
     * The target of the link.
     *
     * <p>This field is bound to the key "cible" in the data source and is parsed
     * using the {@link StringParser} class.
     */
    @BindToKey(key = "cible", parser = StringParser.class)
    public final String target;

    /**
     * A comment associated with the link.
     *
     * <p>This field is bound to the key "commentaire" in the data source and is parsed
     * using the {@link StringParser} class.
     */
    @BindToKey(key = "commentaire", parser = StringParser.class)
    public final String comment;

    /**
     * Constructs a new {@code OrganizationOrganizationLink} object with default values.
     *
     * <p>The {@code origin}, {@code equalityType}, {@code value}, {@code target}, and
     * {@code comment} fields are initialized to {@code null}.
     */
    public OrganizationOrganizationLink() {
        this.origin = null;
        this.equalityType = null;
        this.value = null;
        this.target = null;
        this.comment = null;
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
     * Retrieves the comment associated with the link.
     *
     * @return The comment, or {@code null} if not set.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Returns a string representation of the {@code OrganizationOrganizationLink} object.
     *
     * <p>The string includes the values of the {@code origin}, {@code equalityType},
     * {@code value}, {@code target}, and {@code comment} fields.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "OrganizationOrganizationLink{" +
                "origin='" + origin + '\'' +
                ", equalityType=" + equalityType +
                ", value='" + value + '\'' +
                ", target='" + target + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}