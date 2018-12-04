package com.placepass.cart;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.placepass.booking.application.cart.CartValidator;
import com.placepass.booking.application.cart.dto.TravelerDTO;

public class CheckingLeadTravelerHeadCountTest {

    /**
     * 
     * Only one lead traveler can define in any traveler list
     * 
     */
    @Test
    public void leadTravelerHeadCountGraterThaneOne() {

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
        travelerTwo.setLeadTraveler(true);
        
        travelerThree.setAgeBandId(1);
        travelerThree.setFirstName("Nuwan");
        travelerThree.setLastName("Kulaseklara");
        travelerThree.setLeadTraveler(true);
        
        travelers.add(travelerOne);
        travelers.add(travelerTwo);
        travelers.add(travelerThree);
        
        Assert.assertNotEquals(1, CartValidator.getLeadTravelerHeadCount(travelers));

    }

    @Test
    public void leadTravelerHeadCountZero() {
        
        List<TravelerDTO> travelers = new ArrayList<>();
        TravelerDTO travelerOne = new TravelerDTO();
        TravelerDTO travelerTwo = new TravelerDTO();
        TravelerDTO travelerThree = new TravelerDTO();
        
        travelerOne.setAgeBandId(1);
        travelerOne.setFirstName("saman");
        travelerOne.setLastName("Perera");
        travelerOne.setLeadTraveler(false);
        
        travelerTwo.setAgeBandId(1);
        travelerTwo.setFirstName("Kasun");
        travelerTwo.setLastName("Perise");
        travelerTwo.setLeadTraveler(false);
        
        travelerThree.setAgeBandId(1);
        travelerThree.setFirstName("Nuwan");
        travelerThree.setLastName("Kulaseklara");
        travelerThree.setLeadTraveler(false);
        
        travelers.add(travelerOne);
        travelers.add(travelerTwo);
        travelers.add(travelerThree);
        
        Assert.assertNotEquals(1, CartValidator.getLeadTravelerHeadCount(travelers));

    }

    @Test
    public void leadTravelerHeadCountOne() {

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
        
        travelerThree.setAgeBandId(1);
        travelerThree.setFirstName("Nuwan");
        travelerThree.setLastName("Kulaseklara");
        travelerThree.setLeadTraveler(false);
        
        travelers.add(travelerOne);
        travelers.add(travelerTwo);
        travelers.add(travelerThree);
        
        Assert.assertEquals(1, CartValidator.getLeadTravelerHeadCount(travelers));
        
    }
}
