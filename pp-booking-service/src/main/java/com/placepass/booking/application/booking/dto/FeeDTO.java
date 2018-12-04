package com.placepass.booking.application.booking.dto;

/**
 * The Class FeeDTO.
 */
public class FeeDTO {

    /** The fee type. */
    private String feeType;

    /** The fee category. */
    private String feeCategory;

    /** The description. */
    private String description;

    /** The currency. */
    private String currency;

    /** The amount. */
    private float amount;
    
    /**
     * Instantiates a new fee dto.
     */
    public FeeDTO() {
        
    }

    /**
     * Instantiates a new fee.
     *
     * @param feeType the fee type
     * @param feeCategory the fee category
     * @param description the description
     * @param currency the currency
     * @param amount the amount
     */
    public FeeDTO(String feeType, String feeCategory, String description, String currency, float amount) {
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
    public String getFeeType() {
        return feeType;
    }

    /**
     * Gets the fee category.
     *
     * @return the fee category
     */
    public String getFeeCategory() {
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
