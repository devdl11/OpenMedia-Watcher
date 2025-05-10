package fr.dl11.openmedia.common.types;

import fr.dl11.openmedia.common.utils.WithDefault;

public enum PeriodicityType implements WithDefault<PeriodicityType> {
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

    @Override
    public PeriodicityType getDefault() {
        return kDaily;
    }
}
