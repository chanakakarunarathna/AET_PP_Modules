package com.placepass.connector.citydiscovery.application.booking;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.placepass.connector.citydiscovery.application.exception.InvalidDateException;
import com.placepass.connector.citydiscovery.application.util.CityDiscoveryUtil;
import com.placepass.connector.citydiscovery.application.util.VendorErrorCode;
import com.placepass.connector.citydiscovery.application.util.VendorQuestions;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsActivityBookingRQ;
import com.placepass.connector.common.booking.Booker;
import com.placepass.connector.common.booking.BookingAnswer;
import com.placepass.connector.common.booking.BookingOption;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.Quantity;
import com.placepass.utils.ageband.PlacePassAgeBandType;

/**
 * The Class MakeBookingRequestSpecification checks if the request consists of required information to carry out a make
 * booking.
 * 
 * TODO: This needs to be fleshed out more
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
        } else if (makeBookingRQ.getBookerDetails() == null) {
            isSatisfied = false;
        } else if (!StringUtils.isNumeric(makeBookingRQ.getBookingOptions().get(0).getVendorProductId())) {
            isSatisfied = false;
        } else if (makeBookingRQ.getBookingOptions().get(0).getTotal() == null) {
            isSatisfied = false;
        } else if (makeBookingRQ.getBookingOptions().get(0).getQuantities() == null) {
            isSatisfied = false;
        } else if (makeBookingRQ.getBookingOptions().get(0).getVendorProductOptionId() == null) {
            isSatisfied = false;
        } else if (makeBookingRQ.getBookingOptions().get(0).getBookingAnswers() != null) {

        }

        /*
         * String[] dateArray = bookingOption.getVendorProductOptionId().split("&"); String
         * activityPriceOptionDepartureTime = dateArray[1];
         * activityBookingRQ.setActivityPriceID(Integer.parseInt(dateArray[0]));
         * activityBookingRQ.setActivityPriceOptionDepartureTime(activityPriceOptionDepartureTime);
         * 
         * // Here system depends on BookingAnswer List, it helps to avoid an extra algolia call if (answers != null) {
         * 
         * for (BookingAnswer bookingAnswer : answers) {
         * 
         * if (Integer.parseInt(bookingAnswer.getQuestionId()) == VendorQuestions.VENDOR_QUESTION_NAME_OF_HOTEL
         * .getId()) activityBookingRQ.setNameOfHotel(bookingAnswer.getAnswer()); if
         * (Integer.parseInt(bookingAnswer.getQuestionId()) == VendorQuestions.VENDOR_QUESTION_HOTEL_ADDRESS .getId())
         * activityBookingRQ.setHotelAddress(bookingAnswer.getAnswer()); if
         * (Integer.parseInt(bookingAnswer.getQuestionId()) == VendorQuestions.VENDOR_QUESTION_BOOKING_COMMENTS
         * .getId()) activityBookingRQ.setBookingNotes(bookingAnswer.getAnswer()); }
         * 
         * try { activityBookingRQ
         * .setBookingDate(CityDiscoveryUtil.convertLocalDateToString(bookingOption.getBookingDate())); } catch
         * (Exception ex) { throw new InvalidDateException(VendorErrorCode.INVALID_DATE_FOUND.toString()); }
         * 
         * if (TITLE_MR.equals(booker.getTitle())) { activityBookingRQ.setBookingGender(MALE); } else if
         * (TITLE_MRS.equals(booker.getTitle()) || TITLE_MS.equals(booker.getTitle())) {
         * activityBookingRQ.setBookingGender(FEMALE); }
         * 
         * activityBookingRQ.setPostPayment(POST_PAYMENT); activityBookingRQ.setProcessType(PROCESS_TYPE_BOOKING);
         * 
         * return activityBookingRQ; }
         */
        return isSatisfied;
    }

}
