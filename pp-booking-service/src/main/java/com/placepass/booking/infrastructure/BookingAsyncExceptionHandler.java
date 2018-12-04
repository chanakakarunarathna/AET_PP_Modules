package com.placepass.booking.infrastructure;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import com.placepass.booking.domain.booking.Booking;
import com.placepass.utils.logging.PlatformLoggingKey;

/**
 * 
 * FIXME: This can be made common and moved to platform common library.
 * 
 * @author wathsala.w
 *
 */
public class BookingAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(BookingAsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {

        Map<String, Object> logData = new HashMap<>();

        for (Object param : obj) {

            logger.debug("async param: ", param.getClass().getName());

            if (param instanceof Booking) {
                Booking booking = (Booking) param;
                logData.put(PlatformLoggingKey.PARTNER_ID.name(), booking.getPartnerId());
                logData.put(PlatformLoggingKey.CUSTOMER_ID.name(), booking.getCustomerId());
                logData.put(PlatformLoggingKey.CART_ID.name(), booking.getCartId());
                logData.put(PlatformLoggingKey.BOOKING_ID.name(), booking.getId());
                logData.put(PlatformLoggingKey.BOOKING_REFERENCE.name(), booking.getBookingReference());
                // FIXME: move to PlatformLoggingKey
                logData.put("CUSTOMER_EMAIL", booking.getBookerDetails().getEmail());
                logData.put(PlatformLoggingKey.LOG_EVENT_PROCESSING_STAGE.name(), "BOOKING_CONFIRMATION_EVENT_FAILED");
            }
        }

        logger.error("Booking confirmation event setup failed. {}", logData);
        logger.error(String.format("Unexpected error occurred invoking async " + "method '%s'.", method), throwable);

    }

}
