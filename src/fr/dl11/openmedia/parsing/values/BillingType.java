package fr.dl11.openmedia.parsing.values;

public enum BillingType {
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
}
