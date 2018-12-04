package com.urbanadventures.connector.application.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.placepass.connector.common.booking.MakeBookingRQ;

/**
 * The Class FullRefundSpecification checks if the contract fulfills to do a full refund.
 * 
 * @author naveen.w
 */
@Component
public class EmailSpecification {

    @Autowired
    private Environment environment;

    @Value("#{'${permitted.emails}'.split(',')}")
    private List<String> permittedEmails;

    /**
     * Checks if is satisfied by.
     * 
     * @return true, if is satisfied by
     */

    public boolean isSatisfiedBy(MakeBookingRQ makeBookingRQ) {

        String[] activeProfiles = environment.getActiveProfiles();

        // PROD environment support any email address
        return (activeProfiles[0].equals("PROD"))
                || (permittedEmails.stream().anyMatch(q -> makeBookingRQ.getBookerDetails().getEmail().equals(q)));
    }

}
