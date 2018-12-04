package com.placepass.booking.domain.booking.strategy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.Cart;
import com.placepass.booking.domain.booking.Discount;
import com.placepass.booking.domain.booking.Fee;
import com.placepass.booking.infrastructure.DiscountService;
import com.placepass.booking.infrastructure.discount.DiscountDTO;
import com.placepass.booking.infrastructure.discount.DiscountFeeRQ;
import com.placepass.booking.infrastructure.discount.DiscountFeeRS;
import com.placepass.booking.infrastructure.discount.DiscountTransformer;
import com.placepass.booking.infrastructure.discount.FeeDTO;
import com.placepass.booking.infrastructure.discount.RedeemDiscountRQ;
import com.placepass.booking.infrastructure.discount.RedeemDiscountRS;
import com.placepass.utils.currency.AmountFormatter;

@Service
public class DiscountStrategyImpl implements DiscountStrategy {

    @Autowired
    private DiscountService discountService;

    @Override
    public Cart applyFees(String partnerId, Cart cart) {

        DiscountFeeRQ discountFeeRQ = DiscountTransformer.transformDiscountFeeRQ(cart);

        DiscountFeeRS discountFeeRS = discountService.discountFee(partnerId, discountFeeRQ);

        if (discountFeeRS != null) {
            if (discountFeeRS.getFees() != null) {
                for (FeeDTO feeDTO : discountFeeRS.getFees()) {
                    Fee fee = DiscountTransformer.constructFee(feeDTO);
                    cart.getFees().add(fee);
                }

            }

            if (cart.getTotal() != null) {
                cart.getTotal().setOriginalTotal(cart.getTotal().getFinalTotal());
                cart.getTotal().setFinalTotal(discountFeeRS.getFinalTotalPrice());
                cart.getTotal().setRoundedFinalTotal(
                        AmountFormatter.floatToFloatRoundingFinalTotal(discountFeeRS.getFinalTotalPrice()));
            }
        }

        return cart;
    }

    public Booking redeemDiscount(String partnerId, Booking booking, Cart cart, List<Discount> discounts) {

        RedeemDiscountRQ redeemDiscountRQ = DiscountTransformer.transformRedeemDiscountRQ(cart, discounts, booking);

        RedeemDiscountRS redeemDiscountRS = discountService.redeemDiscount(partnerId, redeemDiscountRQ);

        if (booking != null && booking.getTotal() != null) {
            booking.getTotal().setOriginalTotal(booking.getTotal().getFinalTotal());

            if (redeemDiscountRS != null && redeemDiscountRS.getPriceSummary() != null) {
                booking.getTotal().setFinalTotal(redeemDiscountRS.getPriceSummary().getFinalTotalPrice());
                booking.getTotal().setRoundedFinalTotal(
                        AmountFormatter.floatToFloatRoundingFinalTotal(redeemDiscountRS.getPriceSummary()
                                .getFinalTotalPrice()));
                booking.getTotal().setDiscountAmount(redeemDiscountRS.getPriceSummary().getDiscountAmount());
                booking.getTotal().setTotalAfterDiscount(
                        redeemDiscountRS.getPriceSummary().getTotalPriceAfterDiscount());
                booking.getTotal().setCurrencyCode(redeemDiscountRS.getPriceSummary().getCurrencyCode());

                if (redeemDiscountRS.getPriceSummary().getFees() != null
                        && !redeemDiscountRS.getPriceSummary().getFees().isEmpty()) {

                    List<Fee> fees = new ArrayList<>();
                    for (FeeDTO feeDTO : redeemDiscountRS.getPriceSummary().getFees()) {
                        Fee fee = DiscountTransformer.constructFee(feeDTO);
                        fees.add(fee);
                    }
                    booking.setFees(fees);
                }

                if (redeemDiscountRS.getDiscounts() != null && !redeemDiscountRS.getDiscounts().isEmpty()) {
                    for (DiscountDTO discountDTO : redeemDiscountRS.getDiscounts()) {
                        Discount discount = new Discount(discountDTO.getDiscountCode(), discountDTO.getDiscountId(),
                                discountDTO.getTitle(), discountDTO.getDescription(), discountDTO.getDiscountValue(),
                                discountDTO.getDiscountAmount(), discountDTO.getDiscountType(),
                                discountDTO.getDiscountStatus(), discountDTO.getExpiryDate());
                        booking.getDiscounts().add(discount);
                    }
                }

            }

        }

        return booking;
    }

}
