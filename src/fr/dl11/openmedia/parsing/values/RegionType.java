package fr.dl11.openmedia.parsing.values;

public enum RegionType {
    kNational("National"),
    kEurope("Europe"),
    kRegion("Régional"),
    kInternational("International"),
    kCountry("Country");

    private final String keyword;

    RegionType(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return keyword;
    }
}
