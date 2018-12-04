package com.placepass.booking.domain.common;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.BookingRepository;

@Component
public class BookingReferenceGenerator {

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Generate.
     *
     * @param customerId the customer id
     * @param partnerPrefix the partner prefix
     * @return the string
     */
    public String generate(String customerId, String partnerPrefix) {

        String newBookingReference = UUID.randomUUID().toString();
        newBookingReference = partnerPrefix + newBookingReference.substring(0, 6).toUpperCase();
        Booking booking = bookingRepository.findByCustomerIdAndBookingReference(customerId, newBookingReference);
        if (booking != null) {
            // if the generated booking reference already exists for the customer, generate again.
            newBookingReference = this.generate(customerId, partnerPrefix);
        }

        return newBookingReference;

    }

}
