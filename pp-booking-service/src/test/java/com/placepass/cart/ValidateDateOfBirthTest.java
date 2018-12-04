package com.placepass.cart;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.placepass.booking.application.cart.CartValidator;
import com.placepass.exutil.BadRequestException;

public class ValidateDateOfBirthTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * 
     * Checking whether it is a validate date format or not. Date format should be "yyyy-MM-dd"
     * 
     */

    @Test
    public void toDateFormatInvalidScenarioOne() {

        Assert.assertFalse(CartValidator.isValidDateFormat("2018/01/01"));
    }

    @Test
    public void toDateFormatInvalidScenarioTwo() {

        Assert.assertFalse(CartValidator.isValidDateFormat("01-01-2018"));
    }

    @Test
    public void toDateFormatInvalidScenarioThree() {

        Assert.assertFalse(CartValidator.isValidDateFormat("01/01/2018"));
    }

    @Test
    public void toDateFormatInvalidScenarioFour() {

        Assert.assertFalse(CartValidator.isValidDateFormat("01/01/18"));
    }

    @Test
    public void toDateFormatInvalidScenarioFive() {

        Assert.assertFalse(CartValidator.isValidDateFormat("01-01-18"));
    }

    @Test
    public void toDateFormatInvalidScenarioSix() {

        Assert.assertFalse(CartValidator.isValidDateFormat("01-Jan-18"));
    }

    @Test
    public void toDateFormatValidScenario() {

        Assert.assertTrue(CartValidator.isValidDateFormat("2018-01-01"));
    }

    /**
     * 
     * Date of birth can not be a future date.It should be a before than CURRENT_DATE.So here checking whether DOB is
     * future date or not.
     * 
     * Inside of this checking date format(yyyy-MM-dd) as well like above test cases
     * 
     */

    /**
     * After current date - INVALID
     ***/
    @Test
    public void toDateOfBirthInvalidScenarioOne() {

        exception.expect(BadRequestException.class);
        exception.expectMessage("INVALID_DATE_OF_BIRTH");
        CartValidator.validateDateOfBirth("2030-01-01");
    }

    /**
     * Before current date - VALID
     ***/
    @Test
    public void toDateOfBirthValidScenarioOne() {

        CartValidator.validateDateOfBirth("1989-01-20");
    }

}
