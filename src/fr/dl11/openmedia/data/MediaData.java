package fr.dl11.openmedia.data;

import fr.dl11.openmedia.common.types.BillingType;
import fr.dl11.openmedia.common.types.NewsType;
import fr.dl11.openmedia.common.types.PeriodicityType;
import fr.dl11.openmedia.common.types.RegionType;
import fr.dl11.openmedia.datasource.binding.BindToKey;
import fr.dl11.openmedia.datasource.parsers.elements.BooleanParser;
import fr.dl11.openmedia.datasource.parsers.elements.EnumParser;
import fr.dl11.openmedia.datasource.parsers.elements.StringParser;

public class MediaData {
    @BindToKey(key = "nom", parser = StringParser.class)
    private final String name;

    @BindToKey(key = "type", parser = EnumParser.class)
    public final NewsType newsType;

    @BindToKey(key = "periodicite", parser = EnumParser.class)
    public final PeriodicityType periodicity;

    @BindToKey(key = "echelle", parser = EnumParser.class)
    public final RegionType region;

    @BindToKey(key = "prix", parser = EnumParser.class)
    public final BillingType billing;

    @BindToKey(key = "disparu", parser = BooleanParser.class)
    public final boolean disappeared;

    MediaData() {
        newsType = null;
        periodicity = null;
        region = null;
        billing = null;
        disappeared = false;
        name = null;
    }

    @Override
    public String toString() {
        return "MediaData{" +
                "newsType=" + newsType +
                ", periodicity=" + periodicity +
                ", region=" + region +
                ", billing=" + billing +
                ", disappeared=" + disappeared +
                ", name='" + name + '\'' +
                '}'  + super.toString();
    }
}
