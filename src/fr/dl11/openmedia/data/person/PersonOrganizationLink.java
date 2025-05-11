package fr.dl11.openmedia.data.person;

import fr.dl11.openmedia.common.types.EqualityType;
import fr.dl11.openmedia.datasource.binding.BindToKey;
import fr.dl11.openmedia.datasource.parsers.elements.EnumParser;
import fr.dl11.openmedia.datasource.parsers.elements.StringParser;

public class PersonOrganizationLink {
    @BindToKey(key = "origine", parser = StringParser.class)
    private final String origin;

    @BindToKey(key = "qualificatif", parser = EnumParser.class)
    private final EqualityType equalityType;

    @BindToKey(key = "valeur", parser = StringParser.class)
    private final String value;

    @BindToKey(key = "cible", parser = StringParser.class)
    private final String target;

    @BindToKey(key = "commentaire", parser = StringParser.class)
    private final String comment;

    public PersonOrganizationLink() {
        this.origin = null;
        this.equalityType = null;
        this.value = null;
        this.target = null;
        this.comment = null;
    }

    public String getOrigin() {
        return origin;
    }

    public EqualityType getEqualityType() {
        return equalityType;
    }

    public String getValue() {
        return value;
    }

    public String getTarget() {
        return target;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "PersonOrganizationLink{" +
                "origin='" + origin + '\'' +
                ", equalityType=" + equalityType +
                ", value='" + value + '\'' +
                ", target='" + target + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
