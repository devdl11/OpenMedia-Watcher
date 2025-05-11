package fr.dl11.openmedia.data.organization;

import fr.dl11.openmedia.datasource.binding.BindToKey;
import fr.dl11.openmedia.datasource.parsers.elements.StringParser;

public class OrganizationData {
    @BindToKey(key = "nom", parser = StringParser.class)
    public final String name;

    @BindToKey(key = "commentaire", parser = StringParser.class)
    public final String comment;

    OrganizationData() {
        name = null;
        comment = null;
    }
}
