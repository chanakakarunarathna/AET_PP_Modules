package com.placepass.cart;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.placepass.booking.application.cart.CartValidator;
import com.placepass.booking.application.cart.dto.TravelerDTO;
import com.placepass.exutil.BadRequestException;

public class ValidateTravelerPhoneNumber {
    
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Test
    public void testFirstInvalidScenarioForLeadTraveler() {

        TravelerDTO travelerOne = new TravelerDTO();
        travelerOne.setAgeBandId(1);
        travelerOne.setFirstName("Saman");
        travelerOne.setLastName("Perera");
        travelerOne.setLeadTraveler(true);
        travelerOne.setCountryISOCode("ABC");
        travelerOne.setPhoneNumber("0771234524");

        exception.expect(BadRequestException.class);
        exception.expectMessage("LEAD_TRAVELER_PHONE_NUMBER_INVALID_FOR_COUNTRY_ISO_CODE");
        CartValidator.validateTravelerPhoneNumber(travelerOne);
    }

    @Test
    public void testFirstValidScenarioForLeadTraveler() {

        TravelerDTO travelerOne = new TravelerDTO();
        travelerOne.setAgeBandId(1);
        travelerOne.setFirstName("Saman");
        travelerOne.setLastName("Perera");
        travelerOne.setLeadTraveler(true);
        travelerOne.setCountryISOCode("LK");
        travelerOne.setPhoneNumber("0771234524");

        CartValidator.validateTravelerPhoneNumber(travelerOne);
    }

    @Test
    public void testFirstInvalidScenarioForNormalTraveler() {

        TravelerDTO travelerOne = new TravelerDTO();
        travelerOne.setAgeBandId(1);
        travelerOne.setFirstName("Saman");
        travelerOne.setLastName("Perera");
        travelerOne.setLeadTraveler(false);
        travelerOne.setCountryISOCode("LK");
        travelerOne.setPhoneNumber("0551234524");

        exception.expect(BadRequestException.class);
        exception.expectMessage("TRAVELER_PHONE_NUMBER_INVALID_FOR_COUNTRY_ISO_CODE");
        CartValidator.validateTravelerPhoneNumber(travelerOne);
    }

    @Test
    public void testFirstValidScenarioForNormalTraveler() {

        TravelerDTO travelerOne = new TravelerDTO();
        travelerOne.setAgeBandId(1);
        travelerOne.setFirstName("Saman");
        travelerOne.setLastName("Perera");
        travelerOne.setLeadTraveler(false);
        travelerOne.setCountryISOCode("");
        travelerOne.setPhoneNumber("");

        CartValidator.validateTravelerPhoneNumber(travelerOne);
    }
    
    

}
