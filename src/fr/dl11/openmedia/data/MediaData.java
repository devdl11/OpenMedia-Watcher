package fr.dl11.openmedia.data;

import fr.dl11.openmedia.common.types.BillingType;
import fr.dl11.openmedia.common.types.NewsType;
import fr.dl11.openmedia.common.types.PeriodicityType;
import fr.dl11.openmedia.common.types.RegionType;
import fr.dl11.openmedia.datasource.binding.BindToKey;
import fr.dl11.openmedia.datasource.parsers.elements.BooleanParser;
import fr.dl11.openmedia.datasource.parsers.elements.EnumParser;
import fr.dl11.openmedia.datasource.parsers.elements.StringParser;

/**
 * Represents data related to a media entity.
 *
 * <p>The {@code MediaData} class is used to store and retrieve information about a media entity,
 * including its name, type, periodicity, region, billing type, and whether it has disappeared.
 * The fields are bound to specific keys in a data source using the {@link BindToKey} annotation.
 */
public class MediaData {

    /**
     * The name of the media.
     *
     * <p>This field is bound to the key "nom" in the data source and is parsed
     * using the {@link StringParser} class.
     */
    @BindToKey(key = "nom", parser = StringParser.class)
    private final String name;

    /**
     * The type of news associated with the media.
     *
     * <p>This field is bound to the key "type" in the data source and is parsed
     * using the {@link EnumParser} class.
     */
    @BindToKey(key = "type", parser = EnumParser.class)
    private final NewsType newsType;

    /**
     * The periodicity of the media.
     *
     * <p>This field is bound to the key "periodicite" in the data source and is parsed
     * using the {@link EnumParser} class.
     */
    @BindToKey(key = "periodicite", parser = EnumParser.class)
    private final PeriodicityType periodicity;

    /**
     * The region type of the media.
     *
     * <p>This field is bound to the key "echelle" in the data source and is parsed
     * using the {@link EnumParser} class.
     */
    @BindToKey(key = "echelle", parser = EnumParser.class)
    private final RegionType region;

    /**
     * The billing type of the media.
     *
     * <p>This field is bound to the key "prix" in the data source and is parsed
     * using the {@link EnumParser} class.
     */
    @BindToKey(key = "prix", parser = EnumParser.class)
    private final BillingType billing;

    /**
     * Indicates whether the media has disappeared.
     *
     * <p>This field is bound to the key "disparu" in the data source and is parsed
     * using the {@link BooleanParser} class.
     */
    @BindToKey(key = "disparu", parser = BooleanParser.class)
    private final boolean disappeared;

    /**
     * Constructs a new {@code MediaData} object with default values.
     *
     * <p>The {@code name}, {@code newsType}, {@code periodicity}, {@code region}, and
     * {@code billing} fields are initialized to {@code null}, and {@code disappeared}
     * is initialized to {@code false}.
     */
    public MediaData() {
        newsType = null;
        periodicity = null;
        region = null;
        billing = null;
        disappeared = false;
        name = null;
    }

    /**
     * Retrieves the name of the media.
     *
     * @return The name of the media, or {@code null} if not set.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the type of news associated with the media.
     *
     * @return The news type, or {@code null} if not set.
     */
    public NewsType getNewsType() {
        return newsType;
    }

    /**
     * Retrieves the periodicity of the media.
     *
     * @return The periodicity, or {@code null} if not set.
     */
    public PeriodicityType getPeriodicity() {
        return periodicity;
    }

    /**
     * Retrieves the region type of the media.
     *
     * @return The region type, or {@code null} if not set.
     */
    public RegionType getRegion() {
        return region;
    }

    /**
     * Retrieves the billing type of the media.
     *
     * @return The billing type, or {@code null} if not set.
     */
    public BillingType getBilling() {
        return billing;
    }

    /**
     * Checks whether the media has disappeared.
     *
     * @return {@code true} if the media has disappeared, {@code false} otherwise.
     */
    public boolean isDisappeared() {
        return disappeared;
    }

    /**
     * Returns a string representation of the {@code MediaData} object.
     *
     * <p>The string includes the values of the {@code name}, {@code newsType},
     * {@code periodicity}, {@code region}, {@code billing}, and {@code disappeared} fields.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "MediaData{" +
                "newsType=" + newsType +
                ", periodicity=" + periodicity +
                ", region=" + region +
                ", billing=" + billing +
                ", disappeared=" + disappeared +
                ", name='" + name + '\'' +
                '}' + super.toString();
    }
}