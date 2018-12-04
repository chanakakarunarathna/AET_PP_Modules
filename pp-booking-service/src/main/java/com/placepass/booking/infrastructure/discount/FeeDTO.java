package com.placepass.booking.infrastructure.discount;

/**
 * The Class FeeDTO.
 */
public class FeeDTO {

    /** The fee type. */
    private String type;

    /** The fee category. */
    private String category;

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
     * @param type the type
     * @param category the category
     * @param description the description
     * @param currency the currency
     * @param amount the amount
     */
    public FeeDTO(String type, String category, String description, String currency, float amount) {
        super();
        this.type = type;
        this.category = category;
        this.description = description;
        this.currency = currency;
        this.amount = amount;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
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
