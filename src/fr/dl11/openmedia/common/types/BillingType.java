package fr.dl11.openmedia.common.types;

import fr.dl11.openmedia.common.utils.WithDefault;

/**
 * Enumeration representing the billing type.
 *
 * <p>This enum provides two billing types: free and paid. Each type is associated
 * with a keyword that describes it. The enum also implements the {@link WithDefault}
 * interface, allowing a default billing type to be specified.
 */
public enum BillingType implements WithDefault<BillingType> {
    /**
     * Represents a free billing type.
     */
    kFree("Gratuit"),

    /**
     * Represents a paid billing type.
     */
    kPaid("Payant");

    private final String keyword;

    /**
     * Constructs a {@code BillingType} with the specified keyword.
     *
     * @param keyword the keyword associated with the billing type.
     */
    BillingType(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Returns the keyword associated with this billing type.
     *
     * @return the keyword as a {@code String}.
     */
    @Override
    public String toString() {
        return keyword;
    }

    /**
     * Returns the default billing type.
     *
     * <p>The default billing type is {@code kFree}.
     *
     * @return the default {@code BillingType}.
     */
    @Override
    public BillingType getDefault() {
        return kFree;
    }
}