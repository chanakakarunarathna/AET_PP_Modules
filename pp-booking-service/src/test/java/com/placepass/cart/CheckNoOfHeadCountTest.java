package com.placepass.cart;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.placepass.booking.application.cart.CartApplicationService;
import com.placepass.booking.application.cart.dto.BookingOptionDTO;
import com.placepass.booking.application.cart.dto.QuantityDTO;

public class CheckNoOfHeadCountTest {

    /**
     * 
     * Quantity head count should be nine.
     * 
     */
   
    @Test
    public void HeadCountGreaterThanNine() {

        BookingOptionDTO bookingOptionDTO = new BookingOptionDTO();
        List<QuantityDTO> quantities = new ArrayList<>();

        QuantityDTO adultQty = new QuantityDTO();
        QuantityDTO childQty = new QuantityDTO();

        adultQty.setAgeBandId(1);
        adultQty.setAgeBandLabel("ADULT");
        adultQty.setQuantity(7);

        childQty.setAgeBandId(2);
        childQty.setAgeBandLabel("CHILD");
        childQty.setQuantity(3);

        quantities.add(adultQty);
        quantities.add(childQty);

        bookingOptionDTO.setQuantities(quantities);

        Assert.assertTrue(CartApplicationService.isHeadCountExceeded(bookingOptionDTO));

    }
    
   
    @Test
    public void HeadCountLessThanNine() {

        BookingOptionDTO bookingOptionDTO = new BookingOptionDTO();
        List<QuantityDTO> quantities = new ArrayList<>();

        QuantityDTO adultQty = new QuantityDTO();
        QuantityDTO childQty = new QuantityDTO();

        adultQty.setAgeBandId(1);
        adultQty.setAgeBandLabel("ADULT");
        adultQty.setQuantity(3);

        childQty.setAgeBandId(2);
        childQty.setAgeBandLabel("CHILD");
        childQty.setQuantity(3);

        quantities.add(adultQty);
        quantities.add(childQty);

        bookingOptionDTO.setQuantities(quantities);

        Assert.assertFalse(CartApplicationService.isHeadCountExceeded(bookingOptionDTO));

    }
    
}
