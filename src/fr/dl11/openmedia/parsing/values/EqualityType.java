package fr.dl11.openmedia.parsing.values;

import fr.dl11.openmedia.parsing.AbsParsable;

public class EqualityType extends AbsParsable {
    public static final EqualityType kEqual = new EqualityType("égal à");
    public static final EqualityType kControl = new EqualityType("contrôle");
    public static final EqualityType kParticipate = new EqualityType("participe");
    public static final EqualityType kGreaterThan = new EqualityType("supérieur à");
    public static final EqualityType kLessThan = new EqualityType("inférieur à");

    EqualityType(String keyword) {
        super(keyword);
    }
}
