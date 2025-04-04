package fr.dl11.openmedia.parsing.values;

import fr.dl11.openmedia.parsing.AbsParsable;

public class PeriodicityType extends AbsParsable {
    public static final PeriodicityType kDaily = new PeriodicityType("Quotidien");
    public static final PeriodicityType kWeekly = new PeriodicityType("Hebdomadaire");
    public static final PeriodicityType kMonthly = new PeriodicityType("Mensuel");

    PeriodicityType(String keyword) {
        super(keyword);
    }
}
