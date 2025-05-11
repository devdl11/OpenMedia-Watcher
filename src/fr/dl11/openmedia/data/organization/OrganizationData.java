package fr.dl11.openmedia.data.organization;

import fr.dl11.openmedia.datasource.binding.BindToKey;
import fr.dl11.openmedia.datasource.parsers.elements.StringParser;

public class OrganizationData {
    @BindToKey(key = "nom", parser = StringParser.class)
    private final String name;

    @BindToKey(key = "commentaire", parser = StringParser.class)
    private final String comment;

    public OrganizationData() {
        name = null;
        comment = null;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "OrganizationData{" +
                "name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
