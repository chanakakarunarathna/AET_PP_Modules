package com.placepass.batchprocessor.infrastructure.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.placepass.batchprocessor.BookingServiceURLConfiguration;
import com.placepass.batchprocessor.RestConfiguration;
import com.placepass.batchprocessor.application.BookingTransformer;
import com.placepass.batchprocessor.application.booking.domain.BookingStatusRQ;
import com.placepass.batchprocessor.application.booking.domain.BookingStatusRS;
import com.placepass.batchprocessor.application.booking.domain.GetBookingStatusRS;
import com.placepass.batchprocessor.application.common.BatchProcessorLogger;

@MessageEndpoint
public class GetBookingStatusServiceActivator {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetBookingStatusServiceActivator.class);

    private static final String PARTNER_ID = "partner-id";

    private static final String CONTENT_TYPE = "Content-Type";

    private static final String APPLICATION_CONTENT = "application/json";

    private static final String BOOKING_ID = "bookingid";
	
    @Autowired
    private RestConfiguration restConfiguration;

    @Autowired
    private BookingServiceURLConfiguration bookingServiceURLConfiguration;
	
    @ServiceActivator(inputChannel = "bookingstatus.request.channel")
    public BookingStatusRS getBookingStatus(BookingStatusRQ bookingStatusRQ) {

        Map<String, Object> logData = new HashMap<>();
        logData.put(BatchProcessorLogger.VENDOR.name(), bookingStatusRQ.getVendor());
        logData.put(BatchProcessorLogger.REFERENCE_NUMBER.name(), bookingStatusRQ.getReferenceNumber());
        logData.put(BatchProcessorLogger.BOOKER_EMAIL.name(), bookingStatusRQ.getBookerEmail());
        logData.put(BatchProcessorLogger.CONNECTOR_BOOKING_STATUS.name(), bookingStatusRQ.getStatus());
        logData.put(BatchProcessorLogger.BOOKING_ID.name(), bookingStatusRQ.getBookingId());
        logData.put(BatchProcessorLogger.PARTNER_ID.name(), bookingStatusRQ.getPartnerId());
        logData.put(BatchProcessorLogger.VENDOR_BOOKING_REFERENCE_ID.name(), bookingStatusRQ.getVendorBookingRefId());

        LOGGER.info("Get Booking Status For Pending Bookings Service Activator Request initiated. {}", logData);

        ResponseEntity<GetBookingStatusRS> bookingStatusResponse = null;
        BookingStatusRS bookingStatusRS = new BookingStatusRS();

        HttpHeaders headers = new HttpHeaders();
        headers.set(PARTNER_ID, bookingStatusRQ.getPartnerId());
        headers.set(CONTENT_TYPE, APPLICATION_CONTENT);

        Map<String, String> uriVariables = new HashMap<String, String>();
        uriVariables.put(BOOKING_ID, bookingStatusRQ.getBookingId());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(bookingServiceURLConfiguration.getSchedularBookingStatusUrl());

        String url = builder.buildAndExpand(uriVariables).toUriString();
        HttpEntity<?> requestEntity = new HttpEntity<>(bookingStatusRQ, headers);

        try {
            bookingStatusResponse = new RestTemplate(restConfiguration.getRestTemplateClientFactory()).exchange(url,
                    HttpMethod.POST, requestEntity, GetBookingStatusRS.class, uriVariables);
            if (bookingStatusResponse != null && bookingStatusResponse.getBody() != null) {
            	bookingStatusRS = BookingTransformer.toBookingStatusRS(bookingStatusRS, bookingStatusResponse.getBody());
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
        
        LOGGER.info("Get Booking Status For Pending Bookings Service Activator Request completed. {}", logData);
        return bookingStatusRS;
    }
}
