package fr.dl11.openmedia.parsing.values;

public enum NewsType {
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
}
