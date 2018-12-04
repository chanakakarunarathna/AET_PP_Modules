package com.placepass.batchprocessor.infrastructure.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.placepass.batchprocessor.BookingServiceURLConfiguration;
import com.placepass.batchprocessor.RestConfiguration;
import com.placepass.batchprocessor.application.BookingTransformer;
import com.placepass.batchprocessor.application.booking.domain.PendingBookingRQ;
import com.placepass.batchprocessor.application.booking.domain.PendingBookingsRS;
import com.placepass.batchprocessor.application.common.BatchProcessorLogger;
import com.placepass.batchprocessor.domain.PendingBooking;

@MessageEndpoint
public class GetPendingBookingsServiceActivator {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetPendingBookingsServiceActivator.class);

    private static final String HITS_PER_PAGE = "hitsperpage";

    private static final String VENDOR = "vendor";

    private static final String PAGE_NUMBER = "pagenumber";

    @Autowired
    private BookingServiceURLConfiguration bookingServiceURLConfiguration;

    @Autowired
    private RestConfiguration restConfiguration;

    @Autowired
    private PendingBookingService pendingBookingService;

    @ServiceActivator(inputChannel = "pendingbookings.request.channel")
    public List<PendingBooking> getPendingBookings(PendingBookingRQ pendingBookingRQ) {

        Map<String, Object> logData = new HashMap<>();
        logData.put(BatchProcessorLogger.VENDOR.name(), pendingBookingRQ.getVendor());
        logData.put(BatchProcessorLogger.HITS_PER_PAGE.name(), pendingBookingRQ.getHitsPerPage());
        logData.put(BatchProcessorLogger.PAGE_NUMBER.name(), pendingBookingRQ.getPageNumber());
        
        LOGGER.info("Get Pending Bookings Service Activator Request initiated. {}", logData);
        
        PendingBookingsRS pendingBookingRSFromService = null;

        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put(HITS_PER_PAGE, pendingBookingRQ.getHitsPerPage());
        urlVariables.put(PAGE_NUMBER, pendingBookingRQ.getPageNumber());
        urlVariables.put(VENDOR, pendingBookingRQ.getVendor());

        List<PendingBooking> pendingBookingsListToSave = new ArrayList<>();
        try {
            pendingBookingRSFromService = new RestTemplate(restConfiguration.getRestTemplateClientFactory()).getForObject(
                    bookingServiceURLConfiguration.getSchedularPendingBookingsUrl(),
                    com.placepass.batchprocessor.application.booking.domain.PendingBookingsRS.class, urlVariables);
            List<PendingBooking> transformedPendingBookingsFromService = BookingTransformer.pendingBookingsRSToPendingBooking(
                    pendingBookingRSFromService);

            if (!transformedPendingBookingsFromService.isEmpty()) {
                for (PendingBooking pendingBooking : transformedPendingBookingsFromService) {
                    PendingBooking pendingBookingtoSave = new PendingBooking();
                    /*pendingBookingtoSave = pendingBookingService.getPendingBookingById(pendingBooking.getBookingId());*/
                    pendingBookingtoSave = pendingBookingService.getPendingBookingByBookingId(pendingBooking.getBookingId());
                    if (pendingBookingtoSave == null) {
                        pendingBookingsListToSave.add(pendingBooking);
                    }
                }
            }
        } catch (HttpServerErrorException hse) {
            LOGGER.error("HttpServerErrorException occurred while connecting to the booking service pending bookings endpoint", hse);
        
        } catch (HttpClientErrorException hce) {
            LOGGER.error("HttpClientErrorException occurred while connecting to the booking service pending bookings endpoint", hce);
        
        } catch (ResourceAccessException rae) {
            LOGGER.error("ResourceAccessException occurred while connecting to the booking service pending bookings endpoint", rae);
        
        } catch (Exception e) {
            LOGGER.error("Exception occurred while connecting to the booking service pending bookings endpoint", e);
        }
        
        LOGGER.info("Get Pending Bookings Service Activator Request completed. {}", logData);
        return pendingBookingsListToSave;
    }

}
