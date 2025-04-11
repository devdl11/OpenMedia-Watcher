package fr.dl11.openmedia.parsing.values;

public enum PeriodicityType {
    kDaily("Quotidien"),
    kWeekly("Hebdomadaire"),
    kMonthly("Mensuel");

    private final String keyword;

    PeriodicityType(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return keyword;
    }
}
