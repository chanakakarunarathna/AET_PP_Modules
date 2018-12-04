package com.placepass.booking.domain.booking.strategy;

import java.util.List;

import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.Cart;
import com.placepass.booking.domain.booking.Discount;

/**
 * The Interface DiscountStrategy.
 */
public interface DiscountStrategy {

    /**
     * Apply fees.
     *
     * @param partnerId the partner id
     * @param cart the cart
     * @return the cart
     */
    public Cart applyFees(String partnerId, Cart cart);
    
    /**
     * Redeem discount.
     *
     * @param partnerId the partner id
     * @param booking the booking
     * @param cart the cart
     * @param discounts the discounts
     * @return the booking
     */
    public Booking redeemDiscount(String partnerId, Booking booking, Cart cart, List<Discount> discounts);

}
