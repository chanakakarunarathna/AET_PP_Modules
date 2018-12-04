package com.placepass.utils.email;

import org.junit.Assert;
import org.junit.Test;

import com.placepass.utills.email.ValidateEmail;

public class EmailValidationTest {

    @Test
    public void testInvalidEmailScenarioOne() {

        String email = "abc@gmail";

        Assert.assertFalse(ValidateEmail.isValidEmail(email));

    }
    
    @Test
    public void testInvalidEmailScenarioTwo() {

        String email = "abcgmailCOM";

        Assert.assertFalse(ValidateEmail.isValidEmail(email));

    }
    
    @Test
    public void testInvalidEmailScenarioThree() {

        String email = "abc123.com";

        Assert.assertFalse(ValidateEmail.isValidEmail(email));

    }
    
    @Test
    public void testValidEmailScenarioOne() {

        String email = "abc@gmail.com";

        Assert.assertTrue(ValidateEmail.isValidEmail(email));

    }
    
    @Test
    public void testValidEmailScenarioTwo() {

        String email = "Abc123@gmail.com";

        Assert.assertTrue(ValidateEmail.isValidEmail(email));

    }
    
    @Test
    public void testValidEmailScenarioThree() {

        String email = "ABC@gmail.com";

        Assert.assertTrue(ValidateEmail.isValidEmail(email));

    }

}
