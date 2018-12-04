package com.placepass.booking.domain.booking;

/**
 * The Class Fee.
 */
public class Fee {

    /** The fee type. */
    private FeeType feeType;

    /** The fee category. */
    private FeeCategory feeCategory;

    /** The description. */
    private String description;

    /** The currency. */
    private String currency;

    /** The amount. */
    private float amount;

    /**
     * Instantiates a new fee.
     *
     * @param feeType the fee type
     * @param feeCategory the fee category
     * @param description the description
     * @param currency the currency
     * @param amount the amount
     */
    public Fee(FeeType feeType, FeeCategory feeCategory, String description, String currency, float amount) {
        super();
        this.feeType = feeType;
        this.feeCategory = feeCategory;
        this.description = description;
        this.currency = currency;
        this.amount = amount;
    }

    /**
     * Gets the fee type.
     *
     * @return the fee type
     */
    public FeeType getFeeType() {
        return feeType;
    }

    /**
     * Gets the fee category.
     *
     * @return the fee category
     */
    public FeeCategory getFeeCategory() {
        return feeCategory;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public float getAmount() {
        return amount;
    }

}
