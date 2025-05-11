package fr.dl11.openmedia.common.types;

import fr.dl11.openmedia.common.utils.WithDefault;

public enum PublicationType implements WithDefault<PublicationType> {
    kArticle("Article"),
    kInterview("Interview"),
    kReporting("Reporting");

    private final String name;

    PublicationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public PublicationType getDefault() {
        return kArticle;
    }
}
