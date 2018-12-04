package com.placepass.booking.domain.booking;

/**
 * The Class Discount.
 */
public class Discount {

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

    public Discount() {
    }

    public Discount(String discountCode) {
        super();
        this.discountCode = discountCode;
    }

    public Discount(String discountCode, String discountId, String title, String description, float discountValue, float discountAmount,
            String discountType, String discountStatus, String expiryDate) {
        super();
        this.discountCode = discountCode;
        this.discountId = discountId;
        this.title = title;
        this.description = description;
        this.discountValue = discountValue;
        this.discountAmount = discountAmount;
        this.discountType = discountType;
        this.discountStatus = discountStatus;
        this.expiryDate = expiryDate;
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

    
}
