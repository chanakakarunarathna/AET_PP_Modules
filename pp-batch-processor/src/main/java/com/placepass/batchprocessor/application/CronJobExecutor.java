package com.placepass.batchprocessor.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.placepass.batchprocessor.application.booking.PendingBookingProcessor;
import com.placepass.batchprocessor.domain.CronJobDetails;
import com.placepass.batchprocessor.infrastructure.repository.CronJobDetailsRepository;

@Component
public class CronJobExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CronJobExecutor.class);

    @Autowired
    @Qualifier("threadPoolTaskScheduler")
    private ThreadPoolTaskScheduler poolTaskScheduler;

    @Autowired
    private CronJobDetailsRepository cronJobDetailsRepository;

    @Autowired
    private PendingBookingProcessor pendingBookingProcessor;

    @Autowired
    private BookingPendingCron bookingPendingCron;

    @PostConstruct
    public void executeJob() {

        Map<String, Object> logData = new HashMap<>();
        LOGGER.info("Booking Pending Status Check Cron Job Execution initiated. {}", logData);

        List<CronJobDetails> cronJobDetailsList = cronJobDetailsRepository.findAll();
        if (cronJobDetailsList != null && !cronJobDetailsList.isEmpty()) {
            for (CronJobDetails jobDetails : cronJobDetailsList) {
                bookingPendingCron.executeBookingPendingCron(jobDetails.getVendor(),
                        new CronTrigger(jobDetails.getCronjob()), poolTaskScheduler, pendingBookingProcessor,
                        cronJobDetailsRepository);
            }
        }
    }
}
