package fr.dl11.openmedia.parsing.values;

import fr.dl11.openmedia.parsing.AbstractToken;

public class RegionType extends AbstractToken {
    public static final RegionType kNational = new RegionType("National");
    public static final RegionType kEurope = new RegionType("Europe");
    public static final RegionType kRegion = new RegionType("Régional");
    public static final RegionType kInternational = new RegionType("International");
    public static final RegionType kCountry = new RegionType("Country");

    RegionType(String keyword) {
        super(keyword);
    }

    public RegionType countryRegion(String country) {
        return new RegionType(country);
    }

    @Override
    public RegionType fromString(String keyword) throws IllegalArgumentException {
        try {
            return (RegionType)super.fromString(keyword);
        } catch (IllegalArgumentException e) {
            return kCountry;
        }
    }
}
