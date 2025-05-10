package fr.dl11.openmedia.common.types;

import fr.dl11.openmedia.common.utils.WithDefault;

public enum RegionType implements WithDefault<RegionType> {
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

    @Override
    public RegionType getDefault() {
        return kNational;
    }
}
