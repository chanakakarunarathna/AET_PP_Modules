package com.placepass.cart;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.placepass.booking.application.cart.CartValidator;
import com.placepass.booking.application.cart.dto.TravelerDTO;
import com.placepass.booking.application.cart.dto.UpdateTravelerDetailsRQ;
import com.placepass.booking.domain.booking.Quantity;
import com.placepass.exutil.BadRequestException;

public class ValidateTravelerHeadCountTest {

    /**
     * Traveler head count should be equal with cart quantity total
     * 
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    /**
     * Invalid Scenario
     * 
     * Traveler head count grater than cart total quantity 
     * 
     * ADULT = 1 , CHILD = 3 , TOTAL_QUANTITY_COUNT = 4
     * 
     * ADULT = 2 , CHILD = 3 , TOTAL_TRAVELER_COUNT = 5
     * 
     */
    @Test
    public void toInValidScenarioOne() {
        
        // Adding quantities
        List<Quantity> quantities = new ArrayList<>();

        Quantity adultQty = new Quantity();
        Quantity childQty = new Quantity();

        adultQty.setAgeBandId(1);
        adultQty.setAgeBandLabel("ADULT");
        adultQty.setQuantity(1);

        childQty.setAgeBandId(2);
        childQty.setAgeBandLabel("CHILD");
        childQty.setQuantity(3);

        quantities.add(adultQty);
        quantities.add(childQty);
        
        //Adding traveler details
        UpdateTravelerDetailsRQ updateTravelerDetailsRQ = new UpdateTravelerDetailsRQ();
        
        List<TravelerDTO> travelers = new ArrayList<>();
        TravelerDTO travelerOne = new TravelerDTO();
        TravelerDTO travelerTwo = new TravelerDTO();
        TravelerDTO travelerThree = new TravelerDTO();
        TravelerDTO travelerFour = new TravelerDTO();
        TravelerDTO travelerFive = new TravelerDTO();

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
        
        travelerFour.setAgeBandId(2);
        travelerFour.setFirstName("Kavinda");
        travelerFour.setLastName("Silva");
        travelerFour.setLeadTraveler(false);
        
        travelerFive.setAgeBandId(2);
        travelerFive.setFirstName("Kusal");
        travelerFive.setLastName("Mendis");
        travelerFive.setLeadTraveler(false);
        

        travelers.add(travelerOne);
        travelers.add(travelerTwo);
        travelers.add(travelerThree);
        travelers.add(travelerFour);
        travelers.add(travelerFive);
        
        updateTravelerDetailsRQ.setTravelers(travelers);
        
        exception.expect(BadRequestException.class);
        exception.expectMessage("TRAVELER_DETAILS_DO_NOT_MATCH_TRAVELER_COUNT");
        CartValidator.validateTravelerHeadCount(quantities, updateTravelerDetailsRQ);
    }
    
    /**
     * Invalid Scenario
     * 
     * Traveler head count less than cart total quantity 
     * 
     * ADULT = 1 , CHILD = 3 , TOTAL_QUANTITY_COUNT = 4
     * 
     * ADULT = 1 , CHILD = 2 , TOTAL_TRAVELER_COUNT = 3
     * 
     */
    @Test
    public void toInValidScenarioTwo() {
     // Adding quantities
        List<Quantity> quantities = new ArrayList<>();

        Quantity adultQty = new Quantity();
        Quantity childQty = new Quantity();

        adultQty.setAgeBandId(1);
        adultQty.setAgeBandLabel("ADULT");
        adultQty.setQuantity(1);

        childQty.setAgeBandId(2);
        childQty.setAgeBandLabel("CHILD");
        childQty.setQuantity(3);

        quantities.add(adultQty);
        quantities.add(childQty);
        
        //Adding traveler details
        UpdateTravelerDetailsRQ updateTravelerDetailsRQ = new UpdateTravelerDetailsRQ();
        
        List<TravelerDTO> travelers = new ArrayList<>();
        TravelerDTO travelerOne = new TravelerDTO();
        TravelerDTO travelerThree = new TravelerDTO();
        TravelerDTO travelerFour = new TravelerDTO();

        travelerOne.setAgeBandId(1);
        travelerOne.setFirstName("saman");
        travelerOne.setLastName("Perera");
        travelerOne.setLeadTraveler(true);

        travelerThree.setAgeBandId(2);
        travelerThree.setFirstName("Nuwan");
        travelerThree.setLastName("Kulaseklara");
        travelerThree.setLeadTraveler(false);
        
        travelerFour.setAgeBandId(2);
        travelerFour.setFirstName("Kavinda");
        travelerFour.setLastName("Silva");
        travelerFour.setLeadTraveler(false);
        
        travelers.add(travelerOne);
        travelers.add(travelerThree);
        travelers.add(travelerFour);
        
        updateTravelerDetailsRQ.setTravelers(travelers);
        
        exception.expect(BadRequestException.class);
        exception.expectMessage("TRAVELER_DETAILS_DO_NOT_MATCH_TRAVELER_COUNT");
        CartValidator.validateTravelerHeadCount(quantities, updateTravelerDetailsRQ);
    }
    
    /**
     * Valid Scenario
     * 
     * Traveler head count equals with cart total quantity 
     * 
     * ADULT = 1 , CHILD = 3 , TOTAL_QUANTITY_COUNT = 4
     * 
     * ADULT = 1 , CHILD = 3 , TOTAL_TRAVELER_COUNT = 4
     * 
     */
    @Test
    public void toValidScenarioOne() {
        
        // Adding quantities
        List<Quantity> quantities = new ArrayList<>();

        Quantity adultQty = new Quantity();
        Quantity childQty = new Quantity();

        adultQty.setAgeBandId(1);
        adultQty.setAgeBandLabel("ADULT");
        adultQty.setQuantity(1);

        childQty.setAgeBandId(2);
        childQty.setAgeBandLabel("CHILD");
        childQty.setQuantity(3);

        quantities.add(adultQty);
        quantities.add(childQty);
        
        //Adding traveler details
        UpdateTravelerDetailsRQ updateTravelerDetailsRQ = new UpdateTravelerDetailsRQ();
        
        List<TravelerDTO> travelers = new ArrayList<>();
        TravelerDTO travelerOne = new TravelerDTO();
        TravelerDTO travelerThree = new TravelerDTO();
        TravelerDTO travelerFour = new TravelerDTO();
        TravelerDTO travelerFive = new TravelerDTO();

        travelerOne.setAgeBandId(1);
        travelerOne.setFirstName("saman");
        travelerOne.setLastName("Perera");
        travelerOne.setLeadTraveler(true);

        travelerThree.setAgeBandId(2);
        travelerThree.setFirstName("Nuwan");
        travelerThree.setLastName("Kulaseklara");
        travelerThree.setLeadTraveler(false);
        
        travelerFour.setAgeBandId(2);
        travelerFour.setFirstName("Kavinda");
        travelerFour.setLastName("Silva");
        travelerFour.setLeadTraveler(false);
        
        travelerFive.setAgeBandId(2);
        travelerFive.setFirstName("Kusal");
        travelerFive.setLastName("Mendis");
        travelerFive.setLeadTraveler(false);
        

        travelers.add(travelerOne);
        travelers.add(travelerThree);
        travelers.add(travelerFour);
        travelers.add(travelerFive);
        
        updateTravelerDetailsRQ.setTravelers(travelers);
        
        CartValidator.validateTravelerHeadCount(quantities, updateTravelerDetailsRQ);
    }
    
}
