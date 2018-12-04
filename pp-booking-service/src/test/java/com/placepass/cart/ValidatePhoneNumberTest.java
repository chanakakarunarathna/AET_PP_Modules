package com.placepass.cart;

import org.junit.Assert;
import org.junit.Test;

import com.placepass.booking.application.cart.CartValidator;

public class ValidatePhoneNumberTest {

    /**
     * 
     * Here validating phone number test cases.It can not contain any characters
     * 
     * */

    @Test
    public void testInvalidScenarioOne() {
        
        Assert.assertFalse(CartValidator.isValidPhoneNumber("SK771111111"));
    }
    
    @Test
    public void testValidScenarioOne() {
        
        Assert.assertTrue(CartValidator.isValidPhoneNumber("0771465243"));
    }
    
}
