package com.placepass.cart;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.placepass.booking.application.cart.CartValidator;
import com.placepass.booking.application.cart.dto.TravelerDTO;
import com.placepass.exutil.BadRequestException;

public class ValidateTravelerISOCountryCodeTest {

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

        exception.expect(BadRequestException.class);
        exception.expectMessage("INVALID_ISO_COUNTRY_CODE_FOR_LEAD_TRAVELER");
        CartValidator.validateTravelerISOCountryCode(travelerOne);
    }

    @Test
    public void testFirstValidScenarioForLeadTraveler() {

        TravelerDTO travelerOne = new TravelerDTO();
        travelerOne.setAgeBandId(1);
        travelerOne.setFirstName("Saman");
        travelerOne.setLastName("Perera");
        travelerOne.setLeadTraveler(true);
        travelerOne.setCountryISOCode("LK");

        CartValidator.validateTravelerISOCountryCode(travelerOne);
    }

    @Test
    public void testFirstInvalidScenarioForNormalTraveler() {

        TravelerDTO travelerOne = new TravelerDTO();
        travelerOne.setAgeBandId(1);
        travelerOne.setFirstName("Saman");
        travelerOne.setLastName("Perera");
        travelerOne.setLeadTraveler(false);
        travelerOne.setCountryISOCode("AAA");

        exception.expect(BadRequestException.class);
        exception.expectMessage("INVALID_ISO_COUNTRY_CODE_FOR_TRAVELER");
        CartValidator.validateTravelerISOCountryCode(travelerOne);
    }

    @Test
    public void testFirstValidScenarioForNormalTraveler() {

        TravelerDTO travelerOne = new TravelerDTO();
        travelerOne.setAgeBandId(1);
        travelerOne.setFirstName("Saman");
        travelerOne.setLastName("Perera");
        travelerOne.setLeadTraveler(false);
        travelerOne.setCountryISOCode("");

        CartValidator.validateTravelerISOCountryCode(travelerOne);
    }

}
