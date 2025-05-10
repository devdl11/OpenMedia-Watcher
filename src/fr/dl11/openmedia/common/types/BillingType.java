package fr.dl11.openmedia.common.types;

import fr.dl11.openmedia.common.utils.WithDefault;

public enum BillingType implements WithDefault<BillingType> {
    kFree("Gratuit"),
    kPaid("Payant");

    private final String keyword;

    BillingType(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return keyword;
    }

    @Override
    public BillingType getDefault() {
        return kFree;
    }
}
