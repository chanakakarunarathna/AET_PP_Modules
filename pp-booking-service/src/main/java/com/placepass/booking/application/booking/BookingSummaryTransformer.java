package com.placepass.booking.application.booking;

import org.springframework.util.StringUtils;

import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.BookingSummary;
import com.placepass.booking.domain.booking.cancel.CancelBooking;
import com.placepass.booking.domain.booking.cancel.CancelBookingTransaction;
import com.placepass.booking.domain.platform.BookingStatus;
import com.placepass.booking.domain.platform.PlatformStatus;
import com.placepass.booking.domain.platform.Status;

public class BookingSummaryTransformer {

    private static final String OK = "OK";

    public static void updateBookingSummary(Booking booking) {
        BookingSummary bookingSummary = new BookingSummary();
        String bookingGS = null;

        bookingSummary.setCancelSuccessful(false);
        bookingSummary.setCancelTriggerred(false);
        bookingSummary.setOverallGoodStanding(booking.isInGoodStanding());

        if (StringUtils.isEmpty(booking.getIsInGoodStandingDescription())) {
            bookingGS = OK;
        } else {
            bookingGS = booking.getIsInGoodStandingDescription();
        }

        bookingSummary.setOverallGoodStandingDescription("BookingGoodStanding - [bookingId: " + booking.getId()
                + ",GoodStanding: " + bookingGS + "]");
        bookingSummary.setOverallStatus(booking.getBookingStatus());
        
        BookingStatus bookingStatus = resolveBookingStatus(booking.getBookingStatus().getStatus());
        bookingSummary.setBookingStatus(bookingStatus);

        booking.setBookingSummary(bookingSummary);

    }

    public static void updateBookingSummary(Booking booking, CancelBooking cancelBooking,
            CancelBookingTransaction cancelBookingTransaction) {

        StringBuilder overallGSDescriptionBuilder = new StringBuilder();
        String bookingGS = null;
        String cancelGS = null;
        BookingStatus bookingStatus = booking.getBookingSummary().getBookingStatus();
        BookingSummary bookingSummary = new BookingSummary();
        Status status = new Status();

        status.setStatus(cancelBookingTransaction.getCancelStatus().getStatus());
        status.setConnectorStatus(cancelBookingTransaction.getCancelStatus().getConnectorStatus());
        status.setExternalStatus(cancelBookingTransaction.getCancelStatus().getExternalStatus());

        bookingSummary.setCancelBookingId(cancelBooking.getId());
        if (PlatformStatus.SUCCESS == cancelBookingTransaction.getCancelStatus().getStatus()) {
            bookingSummary.setCancelSuccessful(true);
            status.setStatus(PlatformStatus.CANCELLED);
            bookingStatus = BookingStatus.CANCELLED;
        }
        //Conditional check will be removed after the data migration
        if (bookingStatus != null){
        	bookingSummary.setBookingStatus(bookingStatus);
        }
        bookingSummary.setOverallStatus(status);
        bookingSummary.setCancelTriggerred(true);
        // bookingSummary.setExtendedAttributes(null);

        // if atleast one CancelBookingTransaction GoodStanding is false, this returns false
        boolean overallCancelGS = cancelBooking.getCancelBookingTransactions().stream()
                .allMatch(cbt -> cbt.isInGoodStanding());
        if (booking.isInGoodStanding() && overallCancelGS) {
            bookingSummary.setOverallGoodStanding(true);
        } else {
            bookingSummary.setOverallGoodStanding(false);
        }

        if (StringUtils.isEmpty(booking.getIsInGoodStandingDescription())) {
            bookingGS = OK;
        } else {
            bookingGS = booking.getIsInGoodStandingDescription();
        }
        overallGSDescriptionBuilder.append("BookingGoodStanding - [bookingId: " + booking.getId() + ",GoodStanding: "
                + bookingGS + "], CancelGoodStanding - ");

        for (CancelBookingTransaction cancelBookingTx : cancelBooking.getCancelBookingTransactions()) {

            if (StringUtils.isEmpty(cancelBookingTx.getIsInGoodStandingDescription())) {
                cancelGS = OK;
            } else {
                cancelGS = cancelBookingTx.getIsInGoodStandingDescription();
            }

            overallGSDescriptionBuilder.append("[cancelTxId: " + cancelBookingTx.getTransactionId() + ",GoodStanding: "
                    + cancelGS + "] ");
        }
        bookingSummary.setOverallGoodStandingDescription(overallGSDescriptionBuilder.toString());

        if (cancelBookingTransaction.getCancellationFee() != null) {
            bookingSummary.setCancellationFee(cancelBookingTransaction.getCancellationFee());
        }
        if (cancelBookingTransaction.getRefunds() != null && !cancelBookingTransaction.getRefunds().isEmpty()) {
            bookingSummary.setRefundAmount(cancelBookingTransaction.getRefunds().get(0).getRefundAmount());
        }
        bookingSummary.setRefundMode(cancelBookingTransaction.getRefundMode());
        bookingSummary.setCancellationReasonCode(cancelBookingTransaction.getCancellationReasonCode());
        
        booking.setBookingSummary(bookingSummary);
    }
    
    public static BookingStatus resolveBookingStatus(PlatformStatus status){
    	
    	switch (status){
    		case SUCCESS:
    			return BookingStatus.CONFIRMED;
    		case PENDING:
    			return BookingStatus.PENDING;
    		case CANCELLED:
    			return BookingStatus.CANCELLED;
    		case BOOKING_REJECTED:
    		case PAYMENT_FAILED:
    		case PAYMENT_TIMEOUT:
    		case INSUFFICIENT_FUNDS:
    		case INVALID_PAYMENT_DETAILS:
    		case CARD_EXPIRED:
    		case CARD_ERROR:
    		case UNKNOWN_PAYMENT_GATEWAY_ERROR:
    		case CARD_CVV_FAILURE:
    		case CARD_AVS_FAILURE:
    		case CARD_DECLINED:
    			return BookingStatus.REJECTED;
    		case BOOKING_FAILED:
    		case BOOKING_TIMEOUT:
    			return BookingStatus.FAILED;
    		default: 
				return BookingStatus.FAILED;
    	}
    	
    }
}
