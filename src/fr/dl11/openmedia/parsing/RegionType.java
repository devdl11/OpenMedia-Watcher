package fr.dl11.openmedia.parsing;

public class RegionType extends AbsParsable{
    public static final RegionType kNational = new RegionType("National");
    public static final RegionType kEurope = new RegionType("Europe");
    public static final RegionType kRegion = new RegionType("Régional");
    public static final RegionType kInternational = new RegionType("International");
    public static final RegionType kCountry = new RegionType("Country");

    RegionType(String keyword) {
        super(keyword);
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
