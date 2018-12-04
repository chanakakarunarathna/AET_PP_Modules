package com.placepass.booking.infrastructure;

import com.placepass.booking.infrastructure.discount.DiscountFeeRQ;
import com.placepass.booking.infrastructure.discount.DiscountFeeRS;
import com.placepass.booking.infrastructure.discount.RedeemDiscountRQ;
import com.placepass.booking.infrastructure.discount.RedeemDiscountRS;
import com.placepass.booking.infrastructure.discount.ReverseDiscountRQ;
import com.placepass.booking.infrastructure.discount.ReverseDiscountRS;

/**
 * The Interface DiscountService.
 */
public interface DiscountService {

    
    /**
     * Discount fee.
     *
     * @param partnerId the partner id
     * @param discountFeeRQ the discount fee rq
     * @return the discount fee rs
     */
    public DiscountFeeRS discountFee(String partnerId, DiscountFeeRQ discountFeeRQ);
    
    /**
     * Redeem discount.
     *
     * @param partnerId the partner id
     * @param redeemDiscountRQ the redeem discount rq
     * @return the apply discount rs
     */

    public RedeemDiscountRS redeemDiscount(String partnerId, RedeemDiscountRQ redeemDiscountRQ);

    /**
     * Reverse discount.
     *
     * @param partnerId the partner id
     * @param reverseDiscountRQ the reverse discount rq
     * @return the reverse discount rs
     */
    public ReverseDiscountRS reverseDiscount(String partnerId, ReverseDiscountRQ reverseDiscountRQ);

}
