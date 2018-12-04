package com.placepass.booking.domain.common;

import org.apache.commons.lang3.StringUtils;

public class CreditCardUtil {

    private static final String MASK_CHAR = "X";

    /**
     * This will return all numbers masked except last 4 digits.
     *
     * @param creditCardNumber the credit card number
     * @return the masked number
     */
    public static String getMaskedNumber(final String creditCardNumber) {

        final int start = 0;
        final int end = creditCardNumber.length() - 4;
        final String overlay = StringUtils.repeat(MASK_CHAR, end - start);

        return StringUtils.overlay(creditCardNumber, overlay, start, end);
    }

}
