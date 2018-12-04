package com.placepass.batchprocessor.application;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.placepass.batchprocessor.application.booking.PendingBookingProcessor;
import com.placepass.batchprocessor.application.booking.domain.PendingBookingRQ;
import com.placepass.batchprocessor.domain.CronJobDetails;
import com.placepass.batchprocessor.infrastructure.repository.CronJobDetailsRepository;
import com.placepass.utils.vendorproduct.Vendor;

public class BookingPendingCronJobRunner implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingPendingCronJobRunner.class);

    private String vendor;

    @Autowired
    private PendingBookingProcessor pendingBookingProcessor;

    @Autowired
    private CronJobDetailsRepository cronJobDetailsRepository;

    public BookingPendingCronJobRunner(String vendor, PendingBookingProcessor pendingBookingProcessor,
            CronJobDetailsRepository cronJobDetailsRepository) {
        this.vendor = Vendor.getVendor(vendor).name();
        this.pendingBookingProcessor = pendingBookingProcessor;
        this.cronJobDetailsRepository = cronJobDetailsRepository;
    }

    @Override
    public void run() {

        Map<String, Object> logData = new HashMap<>();
        LOGGER.info("Booking Pending Status Check Runnable Task initiated. {}", logData);

        /* In here, we need to connect to call to a service method to connect to the booking service rest endpoints */
        PendingBookingRQ pendingBookingRQ = new PendingBookingRQ();
        pendingBookingRQ.setVendor(vendor);
        pendingBookingRQ.setHitsPerPage(-1);
        pendingBookingRQ.setPageNumber(-1);

        CronJobDetails jobDetails = cronJobDetailsRepository.findByVendor(vendor);

        if (jobDetails != null) {
            if (jobDetails.isRunnable()) {
                LOGGER.info("Vendor : " + jobDetails.getVendor() + " : process started.....");
                pendingBookingProcessor.getPendingBookings(pendingBookingRQ);
                LOGGER.info("Vendor : " + jobDetails.getVendor() + " : process finished.....");
            } else {
                LOGGER.info(jobDetails.getVendor() + " : Cron Job is Paused");
            }
        }
    }
}
