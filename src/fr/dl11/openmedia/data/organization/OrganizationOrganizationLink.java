package fr.dl11.openmedia.data.organization;

import fr.dl11.openmedia.common.types.EqualityType;
import fr.dl11.openmedia.datasource.binding.BindToKey;
import fr.dl11.openmedia.datasource.parsers.elements.EnumParser;
import fr.dl11.openmedia.datasource.parsers.elements.StringParser;

public class OrganizationOrganizationLink {
    @BindToKey(key = "origine", parser = StringParser.class)
    public final String origin;

    @BindToKey(key = "qualificatif", parser = EnumParser.class)
    public final EqualityType equalityType;

    @BindToKey(key = "valeur", parser = StringParser.class)
    public final String value;

    @BindToKey(key = "cible", parser = StringParser.class)
    public final String target;

    @BindToKey(key = "commentaire", parser = StringParser.class)
    public final String comment;

    public OrganizationOrganizationLink() {
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
        return "OrganizationOrganizationLink{" +
                "origin='" + origin + '\'' +
                ", equalityType=" + equalityType +
                ", value='" + value + '\'' +
                ", target='" + target + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
