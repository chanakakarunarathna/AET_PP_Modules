package com.viator.connector.application.common;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.placepass.connector.common.booking.VendorErrorCode;
import com.placepass.connector.common.common.ResultType;
import com.viator.connector.domain.viator.book.ViatorBookResDetails;
import com.viator.connector.domain.viator.book.ViatorBookResponse;

/**
 * @author naveen.w
 * 
 *         This Strategy handles ViatorBookResponse to ResultType mapping
 */
@Component
public class VendorStatusStrategy {

    private final static String TOUR_NOT_AVAILABLE = "TOUR_NOT_AVAILABLE";

    private final static String PRODUCT_UNAVAILABLE = "PRODUCT_UNAVAILABLE";

    private final static String TOUR_NOT_FOUND = "TOUR_NOT_FOUND";

    private final static String TRAVELLER_COUNT_EXCEEDED_MAX_LIMIT = "TRAVELLER_COUNT_EXCEEDED_MAX_LIMIT";

    private final static String TRAVELLER_FIRST_NAME_REQUIRED = "TRAVELLER_FIRST_NAME_REQUIRED";
    
    private final static String TRAVELLER_LAST_NAME_REQUIRED = "TRAVELLER_LAST_NAME_REQUIRED";
    
    private final static String EMAIL_REQUIRED = "EMAIL_REQUIRED";
    
    private final static String TRAVELLER_FIRST_NAME_INVALID = "TRAVELLER_FIRST_NAME_INVALID";

    private final static String TRAVELLER_LAST_NAME_INVALID = "TRAVELLER_LAST_NAME_INVALID";
    
    private final static String EMAIL_ADDRESS_INVALID = "EMAIL_ADDRESS_INVALID";
    
    private final static String ADDRESS_SIZE_EXCEEDED = "ADDRESS_SIZE_EXCEEDED";

    private final static String TRAVELLER_MISMATCH = "TRAVELLER_MISMATCH";
    
    private final static String PAYMENT_AMOUNTS_CHANGED = "PAYMENT_AMOUNTS_CHANGED";

    private final static String PAYMENT_CURRENCY_MISMATCH = "PAYMENT_CURRENCY_MISMATCH";

    private final static String PAYMENT_ENCRYPTION_ERROR = "PAYMENT_ENCRYPTION_ERROR";

    private final static String PAYMENT_INTERNAL_ERROR = "PAYMENT_INTERNAL_ERROR";

    private final static String PAYMENT_LIMIT_REACHED = "PAYMENT_LIMIT_REACHED";

    private final static String PAYMENT_REJECTED = "PAYMENT_REJECTED";

    private final static String REFUND_REJECTED = "REFUND_REJECTED";

    private final static String UNKNOWN_PAYMENT_METHOD = "UNKNOWN_PAYMENT_METHOD";

    private final static String UNKNOWN_ERROR = "UNKNOWN_ERROR";

    public ResultType getResultType(ViatorBookResponse response) {
        ResultType resultType = null;
                
        String bookingStatusText = (response.getData() != null
                && response.getData().getBookingStatus() != null)
                        ? response.getData().getBookingStatus().getText() : "";

        if (response.getSuccess()) {

            ViatorBookResDetails bookResDetails = response.getData();

            if (bookResDetails != null && bookResDetails.getBookingStatus().getConfirmed()
                    && !bookResDetails.getBookingStatus().getFailed()) {
                resultType = new ResultType(VendorErrorCode.CONFIRMED.getId(),
                        VendorErrorCode.CONFIRMED.getMsg() + " - " + bookingStatusText);
            } else if (bookResDetails != null && bookResDetails.getBookingStatus().getPending()) {
                // Special case, where we treat it as a success-with-pending.
                resultType = new ResultType(VendorErrorCode.PENDING.getId(),
                        VendorErrorCode.PENDING.getMsg() + " - " + bookingStatusText);
            } else if (bookResDetails != null && bookResDetails.getBookingStatus().getCancelled()) {
                resultType = new ResultType(VendorErrorCode.CANCELLED.getId(),
                        VendorErrorCode.CANCELLED.getMsg() + " - " + bookingStatusText);
            } else if (bookResDetails != null && bookResDetails.getBookingStatus().getFailed()) {
                resultType = new ResultType(VendorErrorCode.FAILED.getId(),
                        VendorErrorCode.FAILED.getMsg() + " - " + bookingStatusText);
            } else if (bookResDetails != null && bookResDetails.getBookingStatus().getAmended()) {
                resultType = new ResultType(VendorErrorCode.AMENDED.getId(),
                        VendorErrorCode.AMENDED.getMsg() + " - " + bookingStatusText);
            } else {
                resultType = new ResultType(VendorErrorCode.REJECTED.getId(),
                        VendorErrorCode.REJECTED.getMsg() + " - " + bookingStatusText);
                resultType.getExtendedAttributes().put("code", String.valueOf(VendorErrorCode.REQUEST_FAILED.getId()));
                resultType.getExtendedAttributes().put("message",
                        VendorErrorCode.REQUEST_FAILED.getMsg() + " - " + bookingStatusText);
            }
        } else {
            String message = !CollectionUtils.isEmpty(response.getErrorMessageText())
                    ? response.getErrorMessageText().get(0) : "";

            resultType = new ResultType(VendorErrorCode.REJECTED.getId(),
                    VendorErrorCode.REJECTED.getMsg());

            if (!response.getErrorCodes().isEmpty()) {
                String errorCode = response.getErrorCodes().get(0);
                if (errorCode.equals(TOUR_NOT_FOUND)) {
                    
                    resultType.getExtendedAttributes().put("code",
                            String.valueOf(VendorErrorCode.PRODUCT_NOT_FOUND.getId()));
                    resultType.getExtendedAttributes().put("message",
                            VendorErrorCode.PRODUCT_NOT_FOUND.getMsg() + " - " + message);
                    
                } else if (errorCode.equals(TOUR_NOT_AVAILABLE) || errorCode.equals(PRODUCT_UNAVAILABLE)) {
                    
                    resultType.getExtendedAttributes().put("code",
                            String.valueOf(VendorErrorCode.PRODUCT_UNAVAILABLE.getId()));
                    resultType.getExtendedAttributes().put("message",
                            VendorErrorCode.PRODUCT_UNAVAILABLE.getMsg() + " - " + message);
                    
                } else if (errorCode.equals(TRAVELLER_FIRST_NAME_REQUIRED) || errorCode.equals(TRAVELLER_LAST_NAME_REQUIRED)
                        || errorCode.equals(EMAIL_REQUIRED)) {
                    
                    resultType.getExtendedAttributes().put("code",
                            String.valueOf(VendorErrorCode.BOOKING_DETAILS_INCOMPLETE.getId()));
                    resultType.getExtendedAttributes().put("message",
                            VendorErrorCode.BOOKING_DETAILS_INCOMPLETE.getMsg() + " - " + message);
                    
                } else if (errorCode.equals(TRAVELLER_COUNT_EXCEEDED_MAX_LIMIT) || errorCode.equals(TRAVELLER_FIRST_NAME_INVALID)
                        || errorCode.equals(TRAVELLER_LAST_NAME_INVALID) || errorCode.equals(EMAIL_ADDRESS_INVALID) 
                        || errorCode.equals(ADDRESS_SIZE_EXCEEDED) || errorCode.equals(TRAVELLER_MISMATCH)) {
                    
                    resultType.getExtendedAttributes().put("code",
                            String.valueOf(VendorErrorCode.BOOKING_PARAMS_INVALID.getId()));
                    resultType.getExtendedAttributes().put("message",
                            VendorErrorCode.BOOKING_PARAMS_INVALID.getMsg() + " - " + message);
                    
                } else if (errorCode.equals(PAYMENT_AMOUNTS_CHANGED) || errorCode.equals(PAYMENT_CURRENCY_MISMATCH)
                        || errorCode.equals(PAYMENT_ENCRYPTION_ERROR) || errorCode.equals(PAYMENT_INTERNAL_ERROR)
                        || errorCode.equals(PAYMENT_LIMIT_REACHED) || errorCode.equals(PAYMENT_REJECTED)
                        || errorCode.equals(REFUND_REJECTED) || errorCode.equals(UNKNOWN_PAYMENT_METHOD)) {
                    
                    resultType.getExtendedAttributes().put("code",
                            String.valueOf(VendorErrorCode.PAYMENT_DECLINED.getId()));
                    resultType.getExtendedAttributes().put("message",
                            VendorErrorCode.PAYMENT_DECLINED.getMsg() + " - " + message);
                    
                } else if (errorCode.equals(UNKNOWN_ERROR)) {
                    resultType.getExtendedAttributes().put("code",
                            String.valueOf(VendorErrorCode.UNKNOWN_VENDOR_ERROR.getId()));
                    resultType.getExtendedAttributes().put("message",
                            VendorErrorCode.UNKNOWN_VENDOR_ERROR.getMsg() + " - " + message);
                }
            }
        }
        return resultType;
    }
}
