package fr.dl11.openmedia.parsing;

public class BillingType extends AbsParsable {
    public static final BillingType kFree = new BillingType("Gratuit");
    public static final BillingType kPaid = new BillingType("Payant");

    BillingType(String keyword) {
        super(keyword);
    }
}
