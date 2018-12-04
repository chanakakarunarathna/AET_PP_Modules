package com.placepass.booking;

import org.junit.Assert;
import org.junit.Test;

import com.placepass.booking.domain.common.CreditCardUtil;

public class CreditCardUtilTest {

    @Test
    public void testGetMaskedNumber1() {

        String creditCardNumber = "1234567890123456";
        String maskedNumber = CreditCardUtil.getMaskedNumber(creditCardNumber);

        Assert.assertTrue("masked number should match", "XXXXXXXXXXXX3456".equals(maskedNumber));
    }

    @Test
    public void testGetMaskedNumber2() {

        String creditCardNumber = "1234";
        String maskedNumber = CreditCardUtil.getMaskedNumber(creditCardNumber);

        Assert.assertTrue("masked number should match", "1234".equals(maskedNumber));
    }

}
