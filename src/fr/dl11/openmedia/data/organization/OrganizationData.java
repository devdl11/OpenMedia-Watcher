package fr.dl11.openmedia.data.organization;

import fr.dl11.openmedia.datasource.binding.BindToKey;
import fr.dl11.openmedia.datasource.parsers.elements.StringParser;

/**
 * Represents data related to an organization.
 *
 * <p>The {@code OrganizationData} class is used to store and retrieve information
 * about an organization, including its name and an optional comment. The fields
 * are bound to specific keys in a data source using the {@link BindToKey} annotation.
 */
public class OrganizationData {

    /**
     * The name of the organization.
     *
     * <p>This field is bound to the key "nom" in the data source and is parsed
     * using the {@link StringParser} class.
     */
    @BindToKey(key = "nom", parser = StringParser.class)
    private final String name;

    /**
     * A comment associated with the organization.
     *
     * <p>This field is bound to the key "commentaire" in the data source and is parsed
     * using the {@link StringParser} class.
     */
    @BindToKey(key = "commentaire", parser = StringParser.class)
    private final String comment;

    /**
     * Constructs a new {@code OrganizationData} object with default values.
     *
     * <p>The {@code name} and {@code comment} fields are initialized to {@code null}.
     */
    public OrganizationData() {
        name = null;
        comment = null;
    }

    /**
     * Retrieves the name of the organization.
     *
     * @return The name of the organization, or {@code null} if not set.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the comment associated with the organization.
     *
     * @return The comment, or {@code null} if not set.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Returns a string representation of the {@code OrganizationData} object.
     *
     * <p>The string includes the values of the {@code name} and {@code comment} fields.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "OrganizationData{" +
                "name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}