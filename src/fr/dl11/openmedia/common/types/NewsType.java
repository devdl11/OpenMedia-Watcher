package fr.dl11.openmedia.common.types;

import fr.dl11.openmedia.common.utils.WithDefault;

public enum NewsType implements WithDefault<NewsType> {
    kTV("Télévision"),
    kRadio("Radio"),
    kWeb("Site"),
    kGen("Presse (généraliste politique économique)"),
    kNone("Aucun");

    private final String keyword;

    NewsType(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return keyword;
    }

    @Override
    public NewsType getDefault() {
        return kNone;
    }
}
