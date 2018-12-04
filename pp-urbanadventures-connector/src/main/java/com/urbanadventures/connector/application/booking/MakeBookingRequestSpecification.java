package com.urbanadventures.connector.application.booking;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.placepass.connector.common.booking.MakeBookingRQ;

/**
 * The Class MakeBookingRequestSpecification checks if the request consists of required information to carry out a make
 * booking.
 * 
 * @author naveen.w
 */
@Component
public class MakeBookingRequestSpecification {

    /**
     * Checks if is satisfied by.
     * 
     * @return true, if is satisfied by
     */

    public boolean isSatisfiedBy(MakeBookingRQ makeBookingRQ) {

        Boolean isSatisfied = true;
        if (makeBookingRQ.getBookingOptions() == null || makeBookingRQ.getBookingOptions().isEmpty()) {
            isSatisfied = false;
        } else if (makeBookingRQ.getBookingOptions().get(0) == null) {
            isSatisfied = false;
        } else if (makeBookingRQ.getBookingOptions().get(0).getBookingDate() == null
                || makeBookingRQ.getBookingOptions().get(0).getVendorProductOptionId() == null
                || !StringUtils.isNumeric(makeBookingRQ.getBookingOptions().get(0).getVendorProductOptionId())) {
            isSatisfied = false;
        } else if (makeBookingRQ.getBookingOptions().get(0).getQuantities() == null) {
            isSatisfied = false;
        } else if (makeBookingRQ.getBookingOptions().get(0).getQuantities().stream()
                .anyMatch(q -> q.getAgeBandLabel() == null)) {
            isSatisfied = false;
        } else if (makeBookingRQ.getBookerDetails() == null) {
            isSatisfied = false;
        } else if (makeBookingRQ.getBookerDetails().getCountryCode() == null
                || !StringUtils.isNumeric(makeBookingRQ.getBookerDetails().getCountryCode())) {
            isSatisfied = false;
        } else if (makeBookingRQ.getBookingOptions().get(0).getTraverlerDetails() == null) {
            isSatisfied = false;
        }
        return isSatisfied;
    }

}
