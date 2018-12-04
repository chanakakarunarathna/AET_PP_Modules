package com.placepass.batchprocessor.application;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.placepass.batchprocessor.application.booking.PendingBookingProcessor;
import com.placepass.batchprocessor.infrastructure.repository.CronJobDetailsRepository;

@Service
public class BookingPendingCron {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingPendingCron.class);

    public void executeBookingPendingCron(String vendor, CronTrigger cronTrigger,
            ThreadPoolTaskScheduler poolTaskScheduler, PendingBookingProcessor pendingBookingProcessor,
            CronJobDetailsRepository cronJobDetailsRepository) {

        Map<String, Object> logData = new HashMap<>();
        LOGGER.info("Booking Pending Status Check Task Schedular initiated. {}", logData);
        poolTaskScheduler.schedule(
                new BookingPendingCronJobRunner(vendor, pendingBookingProcessor, cronJobDetailsRepository),
                cronTrigger);
    }
}
