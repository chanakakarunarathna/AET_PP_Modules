package com.placepass.booking.domain.booking;

/**
 * The Enum FeeCategory.
 */
public enum FeeCategory {

    /** The flat. */
    FLAT("Service Fee");

    /** The value. */
    private String value;

    /**
     * Instantiates a new fee category.
     *
     * @param value the value
     */
    private FeeCategory(String value) {

    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

}
