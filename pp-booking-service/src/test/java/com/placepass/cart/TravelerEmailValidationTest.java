package com.placepass.cart;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.placepass.booking.application.cart.CartValidator;
import com.placepass.booking.application.cart.dto.TravelerDTO;
import com.placepass.exutil.BadRequestException;

public class TravelerEmailValidationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * 
     * Checking whether it is a validate Email format or not.
     * 
     */

    @Test
    public void toEmailFormatInvalidScenarioOne() {

        Assert.assertFalse(CartValidator.isValidEmailAddress("abc@gmail"));
    }

    @Test
    public void toEmailFormatInvalidScenarioTwo() {

        Assert.assertFalse(CartValidator.isValidEmailAddress("abc@gmailcom"));
    }

    @Test
    public void toEmailFormatInvalidScenarioThree() {

        Assert.assertFalse(CartValidator.isValidEmailAddress("abc@gmailcom"));
    }

    @Test
    public void toEmailFormatInvalidScenarioFour() {

        Assert.assertFalse(CartValidator.isValidEmailAddress("abc#gmailcom"));
    }

    @Test
    public void toEmailFormatValidScenarioFour() {

        Assert.assertTrue(CartValidator.isValidEmailAddress("abc1989@gmail.com"));
    }

    /**
     * 
     * Email address is required for lead traveler and not required for non-lead traveler(normal traveler). But if we
     * entered email address for non-lead traveler(normal traveler), it should validate.
     * 
     * 
     */

    /**
     * 
     * Lead traveler can not be NULL
     * 
     */
    @Test
    public void toLeadTravelerEmailInValidScenarioOne() {

        TravelerDTO traveler = new TravelerDTO();
        traveler.setLeadTraveler(true);
        traveler.setAgeBandId(1);

        exception.expect(BadRequestException.class);
        exception.expectMessage("EMAIL_ADDRESS_REQUIRED_FOR_LEAD_TRAVELER");
        CartValidator.validateTravelerEmail(traveler);
    }

    /**
     * 
     * Lead traveler can not be EMPTY
     * 
     */
    @Test
    public void toLeadTravelerEmailInValidScenarioTwo() {

        TravelerDTO traveler = new TravelerDTO();
        traveler.setLeadTraveler(true);
        traveler.setAgeBandId(1);
        traveler.setEmail("");

        exception.expect(BadRequestException.class);
        exception.expectMessage("EMAIL_ADDRESS_CAN_NOT_BE_EMPTY_FOR_LEAD_TRAVELER");
        CartValidator.validateTravelerEmail(traveler);
    }

    /**
     * 
     * Valid scenario
     * 
     */

    @Test
    public void toLeadTravelerEmailValidScenario() {

        TravelerDTO traveler = new TravelerDTO();
        traveler.setLeadTraveler(true);
        traveler.setAgeBandId(1);
        traveler.setEmail("abc@gmail.com");

        CartValidator.validateTravelerEmail(traveler);
    }

    /**
     * 
     * Email address not required for normal traveler but if it is exist it should validate
     * 
     */
    @Test
    public void toNormalTravelerEmailInValidScenario() {

        TravelerDTO traveler = new TravelerDTO();
        traveler.setLeadTraveler(false);
        traveler.setAgeBandId(1);
        traveler.setEmail("abc#gmailcom");

        exception.expect(BadRequestException.class);
        exception.expectMessage("INVALID_EMAIL_FOR_TRAVELER");
        CartValidator.validateTravelerEmail(traveler);
    }

    /**
     * 
     * Valid scenario - For normal traveler can provide empty value or null value for email address
     * 
     */
    @Test
    public void toNormalTravelerEmailValidScenarioOne() {

        TravelerDTO traveler = new TravelerDTO();
        traveler.setLeadTraveler(false);
        traveler.setAgeBandId(1);
        traveler.setEmail("");

        CartValidator.validateTravelerEmail(traveler);
    }

    /**
     * 
     * Valid scenario
     * 
     */

    @Test
    public void toNormalTravelerEmailValidScenarioTwo() {

        TravelerDTO traveler = new TravelerDTO();
        traveler.setLeadTraveler(false);
        traveler.setAgeBandId(1);
        traveler.setEmail("abc@gmail.com");

        CartValidator.validateTravelerEmail(traveler);
    }

}
