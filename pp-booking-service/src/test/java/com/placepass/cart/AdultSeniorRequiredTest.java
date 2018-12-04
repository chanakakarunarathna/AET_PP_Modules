package com.placepass.cart;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.placepass.booking.application.cart.CartRequestTransformer;
import com.placepass.booking.application.cart.dto.BookingOptionDTO;
import com.placepass.booking.application.cart.dto.QuantityDTO;
import com.placepass.utils.pricematch.PriceMatch;

public class AdultSeniorRequiredTest {

    /**
     * 
     * ADULT or SENEIOR must include in any traveler list
     * 
     */

    @Test
    public void validateAdultandChildOnly() {

        BookingOptionDTO bookingOptionDTO = new BookingOptionDTO();
        List<QuantityDTO> quantities = new ArrayList<>();

        QuantityDTO adultQty = new QuantityDTO();
        QuantityDTO childQty = new QuantityDTO();

        adultQty.setAgeBandId(1);
        adultQty.setAgeBandLabel("ADULT");
        adultQty.setQuantity(1);

        childQty.setAgeBandId(2);
        childQty.setAgeBandLabel("CHILD");
        childQty.setQuantity(1);

        quantities.add(adultQty);
        quantities.add(childQty);

        bookingOptionDTO.setQuantities(quantities);

        Assert.assertTrue(PriceMatch.isAdultOrSeniorExist(CartRequestTransformer.toQuantitiesDtoToPriceMatch(bookingOptionDTO.getQuantities())));

    }

    @Test
    public void validateSeniorandChildOnly() {

        BookingOptionDTO bookingOptionDTO = new BookingOptionDTO();
        List<QuantityDTO> quantities = new ArrayList<>();

        QuantityDTO childQty = new QuantityDTO();
        QuantityDTO seniorQty = new QuantityDTO();

        seniorQty.setAgeBandId(5);
        seniorQty.setAgeBandLabel("SENIOR");
        seniorQty.setQuantity(1);

        childQty.setAgeBandId(2);
        childQty.setAgeBandLabel("CHILD");
        childQty.setQuantity(1);

        quantities.add(seniorQty);
        quantities.add(childQty);

        bookingOptionDTO.setQuantities(quantities);

        Assert.assertTrue(PriceMatch.isAdultOrSeniorExist(CartRequestTransformer.toQuantitiesDtoToPriceMatch(bookingOptionDTO.getQuantities())));

    }

    @Test
    public void validateAdultandChildZero() {

        BookingOptionDTO bookingOptionDTO = new BookingOptionDTO();
        List<QuantityDTO> quantities = new ArrayList<>();

        QuantityDTO adultQty = new QuantityDTO();
        QuantityDTO childQty = new QuantityDTO();

        adultQty.setAgeBandId(1);
        adultQty.setAgeBandLabel("ADULT");
        adultQty.setQuantity(1);

        childQty.setAgeBandId(2);
        childQty.setAgeBandLabel("CHILD");
        childQty.setQuantity(0);

        quantities.add(adultQty);
        quantities.add(childQty);

        bookingOptionDTO.setQuantities(quantities);

        Assert.assertTrue(PriceMatch.isAdultOrSeniorExist(CartRequestTransformer.toQuantitiesDtoToPriceMatch(bookingOptionDTO.getQuantities())));

    }

    @Test
    public void validateChildOnly() {

        BookingOptionDTO bookingOptionDTO = new BookingOptionDTO();
        List<QuantityDTO> quantities = new ArrayList<>();

        QuantityDTO childQty = new QuantityDTO();

        childQty.setAgeBandId(2);
        childQty.setAgeBandLabel("CHILD");
        childQty.setQuantity(0);

        quantities.add(childQty);

        bookingOptionDTO.setQuantities(quantities);

        Assert.assertFalse(PriceMatch.isAdultOrSeniorExist(CartRequestTransformer.toQuantitiesDtoToPriceMatch(bookingOptionDTO.getQuantities())));

    }

}
