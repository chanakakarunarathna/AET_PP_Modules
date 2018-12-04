package com.placepass.booking.infrastructure.discount;

/**
 * The Class Discount.
 */
public class DiscountDTO {

    /** The discount code. */
    private String discountCode;

    /** The discount id. */
    private String discountId;

    /** The title. */
    private String title;

    /** The description. */
    private String description;

    /** The discount value. */
    private float discountValue;

    /** The discount amount. */
    private float discountAmount;

    /** The discount type. */
    private String discountType;

    /** The discount status. */
    private String discountStatus;

    /** The expiry date. */
    private String expiryDate;

    /** The message. */
    private String message;

    /**
     * Instantiates a new discount dto.
     */
    public DiscountDTO() {

    }

    public DiscountDTO(String discountCode) {
        super();
        this.discountCode = discountCode;
    }

    public DiscountDTO(String discountId, String title, String description, float discountValue, float discountAmount,
            String discountType, String discountStatus, String expiryDate, String message) {
        super();
        this.discountId = discountId;
        this.title = title;
        this.description = description;
        this.discountValue = discountValue;
        this.discountAmount = discountAmount;
        this.discountType = discountType;
        this.discountStatus = discountStatus;
        this.expiryDate = expiryDate;
        this.message = message;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public String getDiscountId() {
        return discountId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getDiscountValue() {
        return discountValue;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public String getDiscountStatus() {
        return discountStatus;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
