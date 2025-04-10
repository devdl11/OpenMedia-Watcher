package fr.dl11.openmedia.parsing.values;

import fr.dl11.openmedia.parsing.AbstractToken;

public class BillingType extends AbstractToken {
    public static final BillingType kFree = new BillingType("Gratuit");
    public static final BillingType kPaid = new BillingType("Payant");

    BillingType(String keyword) {
        super(keyword);
    }
}
