package com.placepass.booking.infrastructure.discount;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class RedeemDiscountRS.
 */
public class RedeemDiscountRS {

    /** The discounts. */
    private List<DiscountDTO> discounts = new ArrayList<DiscountDTO>();

    /** The price summary. */
    private PriceSummaryDTO priceSummary;

    /** The loyalty details.*/
    private List<LoyaltyDetailDTO> loyalty;

    /**
     * Gets the discounts.
     *
     * @return the discounts
     */
    @JsonProperty("discount")
    public List<DiscountDTO> getDiscounts() {
        return discounts;
    }

    /**
     * Gets the price summary.
     *
     * @return the price summary
     */
    public PriceSummaryDTO getPriceSummary() {
        return priceSummary;
    }

    /**
     * Sets the price summary.
     *
     * @param priceSummary the new price summary
     */
    public void setPriceSummary(PriceSummaryDTO priceSummary) {
        this.priceSummary = priceSummary;
    }

    /**
     * Gets the loyalty details.
     *
     * @return the loyalty details
     */
    @JsonProperty("loyalty")
    public List<LoyaltyDetailDTO> getLoyalty() {
        return loyalty;
    }

    /**
     * Sets the loyalty details.
     *
     * @param loyalty the new loyalty details
     */
    public void setLoyalty(List<LoyaltyDetailDTO> loyalty) {
        this.loyalty = loyalty;
    }

}
