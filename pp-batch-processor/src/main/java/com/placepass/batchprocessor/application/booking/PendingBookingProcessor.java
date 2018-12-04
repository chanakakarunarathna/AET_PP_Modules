package com.placepass.batchprocessor.application.booking;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.placepass.batchprocessor.application.BookingTransformer;
import com.placepass.batchprocessor.application.booking.domain.PendingBookingRQ;
import com.placepass.batchprocessor.application.common.BatchProcessorLogger;
import com.placepass.batchprocessor.domain.PendingBooking;
import com.placepass.batchprocessor.domain.ProcessStatus;
import com.placepass.batchprocessor.infrastructure.service.GetBookingStatusGatewayService;
import com.placepass.batchprocessor.infrastructure.service.GetPendingBookingsGatewayService;
import com.placepass.batchprocessor.infrastructure.service.PendingBookingService;
import com.placepass.batchprocessor.application.booking.domain.BookingStatusRQ;
import com.placepass.batchprocessor.application.booking.domain.BookingStatusRS;

@Service
public class PendingBookingProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PendingBookingProcessor.class);

    @Autowired
    private GetPendingBookingsGatewayService pendingBookingsGatewayService;

    @Autowired
    private GetBookingStatusGatewayService bookingStatusGatewayService;

    @Autowired
    private PendingBookingService pendingBookingService;
	
    public void getPendingBookings(PendingBookingRQ pendingBookingRQ) {

        Map<String, Object> logData = new HashMap<>();
        logData.put(BatchProcessorLogger.VENDOR.name(), pendingBookingRQ.getVendor());
        logData.put(BatchProcessorLogger.HITS_PER_PAGE.name(), pendingBookingRQ.getHitsPerPage());
        logData.put(BatchProcessorLogger.PAGE_NUMBER.name(), pendingBookingRQ.getPageNumber());

        LOGGER.info("Get Pending Bookings Request initiated. {}", logData);

        List<PendingBooking> pendingBookings = pendingBookingsGatewayService.getPendingBookings(pendingBookingRQ);

        if (pendingBookings.isEmpty()) {
            LOGGER.error("Pending Bookings List is Empty");
        } else {
            for (PendingBooking pendingBooking : pendingBookings) {
                pendingBooking.setLatestBookingStatus(pendingBooking.getBookingStatus());
                pendingBooking.setUpdatedTime(Instant.now());
                pendingBooking.setProcessStatus(ProcessStatus.NOT_STARTED);
                pendingBookingService.savePendingBooking(pendingBooking);
            }
        }
        LOGGER.info("Get Pending Bookings Request completed. {}", logData);

        getBookingStatusForPendingBooking(pendingBookingRQ.getVendor());
    }
	
    public void getBookingStatusForPendingBooking(String vendor) {

        Map<String, Object> logData = new HashMap<>();
        logData.put(BatchProcessorLogger.VENDOR.name(), vendor);

        LOGGER.info("Get Booking Status For Pending Bookings Request initiated. {}", logData);

        List<PendingBooking> pendingBookingByVendor = pendingBookingService.getPendingBookingByVendor(vendor);

        if (pendingBookingByVendor != null && !pendingBookingByVendor.isEmpty()) {
            for (PendingBooking pendingBooking : pendingBookingByVendor) {
                BookingStatusRQ bookingStatusRQ = BookingTransformer.pendingBookingToBookingStatusRQ(pendingBooking);
                BookingStatusRS bookingStatusResponse = bookingStatusGatewayService.getBookingStatus(bookingStatusRQ);

                updateBookingStatusForPendingBookings(pendingBooking, bookingStatusResponse);
            }
        }
        LOGGER.info("Get Booking Status For Pending Bookings Request completed. {}", logData);
    }

    private void updateBookingStatusForPendingBookings(PendingBooking pendingBooking,
            BookingStatusRS bookingStatusResponse) {

    	BookingTransformer.bookingStatusRStoPendingBooking(pendingBooking, bookingStatusResponse);
        pendingBookingService.savePendingBooking(pendingBooking);
    }
	
}
