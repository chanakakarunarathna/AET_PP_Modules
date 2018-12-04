package com.placepass.booking.domain.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.placepass.booking.domain.booking.BookingOption;
import com.placepass.booking.domain.booking.Total;
import com.placepass.utils.currency.AmountFormatter;

@Component
public class TotalCalculator {

    private static final Logger logger = LoggerFactory.getLogger(TotalCalculator.class);

    /**
     * Calculates the cart total by summing booking options total.
     *
     * @param bookingOptions the booking options
     * @return the total
     */
    public static Total calculate(List<BookingOption> bookingOptions) {

        logger.info("Start to calculate cart total process");

        float finalTotal = 0;
        float merchantTotal = 0;
        float retailTotal = 0;

        Total cartTotal = new Total();
        if (bookingOptions != null) {
            for (BookingOption option : bookingOptions) {
                if (option.getTotal() != null) {

                    cartTotal.setCurrency(option.getTotal().getCurrency());
                    cartTotal.setCurrencyCode(option.getTotal().getCurrencyCode());

                    finalTotal = finalTotal + option.getTotal().getFinalTotal();
                    cartTotal.setFinalTotal(finalTotal);

                    cartTotal.setRoundedFinalTotal(AmountFormatter.floatToFloatRoundingFinalTotal(finalTotal));

                    merchantTotal = +merchantTotal + option.getTotal().getMerchantTotal();
                    cartTotal.setMerchantTotal(merchantTotal);

                    retailTotal = retailTotal + option.getTotal().getRetailTotal();
                    cartTotal.setRetailTotal(retailTotal);

                }

            }
        }

        logger.info(" End calculate cart total process");
        return cartTotal;
    }

}
