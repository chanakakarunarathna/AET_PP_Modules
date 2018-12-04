package com.placepass.cart;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.placepass.booking.application.cart.CartRequestTransformer;
import com.placepass.booking.application.cart.dto.BookingOptionDTO;
import com.placepass.booking.application.cart.dto.QuantityDTO;
import com.placepass.exutil.BadRequestException;

public class ValidateAgeBandLabels {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testAdultAgeBandLabel() {

        BookingOptionDTO bookingOption1 = new BookingOptionDTO();
        QuantityDTO quantity1 = new QuantityDTO();
        List<QuantityDTO> quantities = new ArrayList<>();

        quantity1.setAgeBandId(1);
        quantities.add(quantity1);
        bookingOption1.setQuantities(quantities);

        CartRequestTransformer.setQuantityDTOAgeBandLabels(bookingOption1);

        assertEquals(bookingOption1.getQuantities().get(0).getAgeBandLabel(), "ADULT");

    }

    @Test
    public void testYouthAgeBandLabel() {

        BookingOptionDTO bookingOption1 = new BookingOptionDTO();
        QuantityDTO quantity1 = new QuantityDTO();
        List<QuantityDTO> quantities = new ArrayList<>();

        quantity1.setAgeBandId(4);
        quantities.add(quantity1);
        bookingOption1.setQuantities(quantities);

        CartRequestTransformer.setQuantityDTOAgeBandLabels(bookingOption1);

        assertEquals(bookingOption1.getQuantities().get(0).getAgeBandLabel(), "YOUTH");

    }

    @Test
    public void testFirstInvalidScenarioForAgeBand() {

        BookingOptionDTO bookingOption1 = new BookingOptionDTO();
        QuantityDTO quantity1 = new QuantityDTO();
        List<QuantityDTO> quantities = new ArrayList<>();

        quantity1.setAgeBandId(8);
        quantities.add(quantity1);
        bookingOption1.setQuantities(quantities);

        exception.expect(BadRequestException.class);
        exception.expectMessage("INVALID_AGE_BAND_ID");
        
        CartRequestTransformer.setQuantityDTOAgeBandLabels(bookingOption1);
    }

}
