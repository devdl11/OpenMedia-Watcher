package fr.dl11.openmedia.common.types;

import fr.dl11.openmedia.common.utils.WithDefault;

public enum EqualityType implements WithDefault<EqualityType> {
    kEqual("égal à"),
    kControl("contrôle"),
    kParticipate("participe"),
    kGreaterThan("supérieur à"),
    kLessThan("inférieur à");

    private final String keyword;

    EqualityType(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return keyword;
    }

    @Override
    public EqualityType getDefault() {
        return kControl;
    }
}
