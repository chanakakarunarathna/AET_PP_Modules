package com.placepass.booking.infrastructure.discount;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ApplyDiscountRS.
 */
public class ReverseDiscountRS {

    /** The discounts. */
    private List<DiscountDTO> discounts = new ArrayList<DiscountDTO>();

    /**
     * Gets the discounts.
     *
     * @return the discounts
     */
    public List<DiscountDTO> getDiscounts() {
        return discounts;
    }

}
