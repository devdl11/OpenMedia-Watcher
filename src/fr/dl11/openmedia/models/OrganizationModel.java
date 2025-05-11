package fr.dl11.openmedia.models;

import fr.dl11.openmedia.data.organization.OrganizationData;

/**
 * Represents a model for an organization.
 *
 * <p>The {@code OrganizationModel} class provides a structure to manage
 * organization-related data. It encapsulates an {@link OrganizationData}
 * object and provides access to it.</p>
 */
public class OrganizationModel {
    private final OrganizationData organization;

    /**
     * Constructs a new {@code OrganizationModel} with the specified organization data.
     *
     * @param organization The {@link OrganizationData} associated with this model. Must not be null.
     */
    public OrganizationModel(OrganizationData organization) {
        this.organization = organization;
    }

    /**
     * Retrieves the organization data associated with this model.
     *
     * @return The {@link OrganizationData} object representing the organization data.
     */
    public OrganizationData getOrganization() {
        return organization;
    }
}
