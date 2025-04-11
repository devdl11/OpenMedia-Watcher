package fr.dl11.openmedia.parsing.values;

public enum EqualityType {
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
}
