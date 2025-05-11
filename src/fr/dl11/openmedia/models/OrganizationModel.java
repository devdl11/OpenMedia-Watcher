package fr.dl11.openmedia.models;

import fr.dl11.openmedia.data.organization.OrganizationData;

public class OrganizationModel {
    private final OrganizationData organization;

    public OrganizationModel(OrganizationData organization) {
        this.organization = organization;
    }

    public OrganizationData getOrganization() {
        return organization;
    }
}
