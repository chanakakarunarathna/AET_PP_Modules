package com.placepass.cart;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.placepass.booking.application.cart.CartValidator;
import com.placepass.booking.application.cart.dto.TravelerDTO;
import com.placepass.booking.domain.booking.Quantity;
import com.placepass.exutil.BadRequestException;

public class ValidateTravelerAgbandsAndCartQuantityAgeBandsTest {

    /**
     * 
     * Traveler count with specific age bands should match with the quantity age bands in create cart and add booking
     * option
     * 
     */

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * 
     * In traveler list request 2 ADULT and 1 CHILD
     * 
     * In cart Quantity has 1 ADULT and 2 CHILD
     * 
     */
    @Test
    public void toInvalidScenario() {

        // Adding traveler details
        List<TravelerDTO> travelers = new ArrayList<>();
        TravelerDTO travelerOne = new TravelerDTO();
        TravelerDTO travelerTwo = new TravelerDTO();
        TravelerDTO travelerThree = new TravelerDTO();

        travelerOne.setAgeBandId(1);
        travelerOne.setFirstName("saman");
        travelerOne.setLastName("Perera");
        travelerOne.setLeadTraveler(true);

        travelerTwo.setAgeBandId(1);
        travelerTwo.setFirstName("Kasun");
        travelerTwo.setLastName("Perise");
        travelerTwo.setLeadTraveler(false);

        travelerThree.setAgeBandId(2);
        travelerThree.setFirstName("Nuwan");
        travelerThree.setLastName("Kulaseklara");
        travelerThree.setLeadTraveler(false);

        travelers.add(travelerOne);
        travelers.add(travelerTwo);
        travelers.add(travelerThree);

        // Adding quantities
        List<Quantity> quantities = new ArrayList<>();

        Quantity adultQty = new Quantity();
        Quantity childQty = new Quantity();

        adultQty.setAgeBandId(1);
        adultQty.setAgeBandLabel("ADULT");
        adultQty.setQuantity(1);

        childQty.setAgeBandId(2);
        childQty.setAgeBandLabel("CHILD");
        childQty.setQuantity(2);

        quantities.add(adultQty);
        quantities.add(childQty);

        exception.expect(BadRequestException.class);
        exception.expectMessage("TRAVELER_AGE_BAND_COUNT_NOT_MATCH_WITH_CART_QUANTITY_AGE_BAND_COUNT");
        CartValidator.validatingTravelerAgeBandsWithCartAgeBands(quantities, travelers);
    }

    /**
     * 
     * In traveler list request 2 ADULT and 1 CHILD
     * 
     * In cart Quantity has 2 ADULT and 1 CHILD
     * 
     */
    @Test
    public void toValidScenario() {

        // Adding traveler details
        List<TravelerDTO> travelers = new ArrayList<>();
        TravelerDTO travelerOne = new TravelerDTO();
        TravelerDTO travelerTwo = new TravelerDTO();
        TravelerDTO travelerThree = new TravelerDTO();

        travelerOne.setAgeBandId(1);
        travelerOne.setFirstName("saman");
        travelerOne.setLastName("Perera");
        travelerOne.setLeadTraveler(true);

        travelerTwo.setAgeBandId(1);
        travelerTwo.setFirstName("Kasun");
        travelerTwo.setLastName("Perise");
        travelerTwo.setLeadTraveler(false);

        travelerThree.setAgeBandId(2);
        travelerThree.setFirstName("Nuwan");
        travelerThree.setLastName("Kulaseklara");
        travelerThree.setLeadTraveler(false);

        travelers.add(travelerOne);
        travelers.add(travelerTwo);
        travelers.add(travelerThree);

        // Adding quantities
        List<Quantity> quantities = new ArrayList<>();

        Quantity adultQty = new Quantity();
        Quantity childQty = new Quantity();

        adultQty.setAgeBandId(1);
        adultQty.setAgeBandLabel("ADULT");
        adultQty.setQuantity(2);

        childQty.setAgeBandId(2);
        childQty.setAgeBandLabel("CHILD");
        childQty.setQuantity(1);

        quantities.add(adultQty);
        quantities.add(childQty);

        CartValidator.validatingTravelerAgeBandsWithCartAgeBands(quantities, travelers);
    }

}
