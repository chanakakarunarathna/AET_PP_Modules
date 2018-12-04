package com.placepass.batchprocessor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingServiceURLConfiguration {

    /**
     * Booking Service RestTemplate configurations
     */
    @Value("${booking.service.baseurl}")
    private String baseUrl;

    @Value("${booking.service.schedular.pending.bookings}")
    private String schedularPendingBookingsUrl;

    public String getSchedularPendingBookingsUrl() {
        return baseUrl + schedularPendingBookingsUrl;
    }

    @Value("${booking.service.schedular.booking.status}")
    private String schedularBookingStatusUrl;

    public String getSchedularBookingStatusUrl() {
        return baseUrl + schedularBookingStatusUrl;
    }

}
