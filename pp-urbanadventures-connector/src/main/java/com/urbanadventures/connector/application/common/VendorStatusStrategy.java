package com.urbanadventures.connector.application.common;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.placepass.connector.common.booking.VendorErrorCode;
import com.placepass.connector.common.common.ResultType;
import com.urbanadventures.connector.domain.urbanadventures.ClsBookRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetPriceRS;

/**
 * @author naveen.w
 * 
 *         This Strategy handles ViatorBookResponse to ResultType mapping
 * 
 *         1 OR_ERROR 
 *         2 OR_ERR_PERMISSION 
 *         3 OR_ERR_PARAMS 
 *         4 OR_ERR_CURRENCY 
 *         5 OR_ERR_NOTFOUND 
 *         6 OR_TOUR_NOT_FOUND 
 *         7 OR_ERROR_TRIP 
 *         8 OR_TRIP_NOT_ALLOW 
 *         9 OR_NOT_LOCAL_BOOKING 
 *         10 OR_ERR_INCONSISTANT_TRAVELLER_NUM 
 *         11 OR_ERR_SALE_CLOSE 
 *         12 OR_ERR_NOT_ALLOW_CHILDREN 
 *         13 OR_ERR_DEPARTURE_DATE_INVALID 
 *         14 OR_ERR_NO_RATE 
 *         15 OR_ERR_STOP_SALE 
 *         16 OR_ERR_NO_ALLOTMENT 
 *         17 OR_ERR_PROMO_CODE_INVALID 
 *         18 OR_ERR_PROMO_WRONG_DEST 
 *         19 OR_ERR_PROMO_LIMIT_BOOKING 
 *         20 OR_ERR_PROMO_SALE_PERIOD_INVALID 
 *         21 OR_ERR_PROMO_DEPARTURE_DATE_REQUIREMENT 
 *         22 OR_ERR_PROMO_MINIMUM_TRIPS 
 *         23 OR_ERR_PROMO_MINIMUM_PAX 
 *         24 OR_ERROR_SERVER 
 *         25 OR_NOT_ALLOTMENT 
 *         26 OR_BOOKING_FAILS 
 *         27 OR_ERROR_SPECICAL_OFFER 
 *         28 OR_ERROR_GIFT
 * 
 */

@Component
public class VendorStatusStrategy {

    private static final int VENDOR_ERROR_ID = 1;
    
    private static final int BOOKING_FAILED_ID = 1;
    
    private static final String BOOKING_FAILED_MSG = "Booking fails";

    private static final int[] PRODUCT_NOT_FOUND_KEYS = {5, 6, 7, 8, 11, 14, 15};

    private static final int[] PRODUCT_UNAVAILABLE_KEYS = {16, 25};

    private static final int[] BOOKING_PARAMS_INVALID_KEYS = {2, 3, 4, 9, 10, 12, 13, 17, 18, 19, 20, 21, 22, 23};

    public ResultType getBookingResultType(ClsBookRS response) {

        ResultType resultType = null;
        int resultTypeCode = response.getOpResult() != null ? response.getOpResult().getCode() : BOOKING_FAILED_ID;

        if (response.getOpResult().getCode() == VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId()) {

            resultType = new ResultType(VendorErrorCode.CONFIRMED.getId(), VendorErrorCode.CONFIRMED.getMsg());

        } else {
            String errorMessage = response.getOpResult() != null && StringUtils.hasText(response.getOpResult().getMessage())
                    ? response.getOpResult().getMessage() : "";

            if (resultTypeCode == BOOKING_FAILED_ID
                    && response.getOpResult().getMessage().contains(BOOKING_FAILED_MSG)) {

                resultType = new ResultType(VendorErrorCode.FAILED.getId(), VendorErrorCode.FAILED.getMsg());

            } else {

                resultType = new ResultType(VendorErrorCode.REJECTED.getId(), VendorErrorCode.REJECTED.getMsg());

                if (ArrayUtils.contains(PRODUCT_NOT_FOUND_KEYS, resultTypeCode)) {

                    resultType.getExtendedAttributes().put("code",
                            String.valueOf(VendorErrorCode.PRODUCT_NOT_FOUND.getId()));
                    resultType.getExtendedAttributes().put("message",
                            VendorErrorCode.PRODUCT_NOT_FOUND.getMsg() + " - " + errorMessage);

                } else if (ArrayUtils.contains(PRODUCT_UNAVAILABLE_KEYS, resultTypeCode)) {

                    resultType.getExtendedAttributes().put("code",
                            String.valueOf(VendorErrorCode.PRODUCT_UNAVAILABLE.getId()));
                    resultType.getExtendedAttributes().put("message",
                            VendorErrorCode.PRODUCT_UNAVAILABLE.getMsg() + " - " + errorMessage);

                } else if (ArrayUtils.contains(BOOKING_PARAMS_INVALID_KEYS, resultTypeCode)) {

                    resultType.getExtendedAttributes().put("code",
                            String.valueOf(VendorErrorCode.BOOKING_PARAMS_INVALID.getId()));
                    resultType.getExtendedAttributes().put("message",
                            VendorErrorCode.BOOKING_PARAMS_INVALID.getMsg() + " - " + errorMessage);

                } else {

                    resultType.getExtendedAttributes().put("code",
                            String.valueOf(VendorErrorCode.UNKNOWN_VENDOR_ERROR.getId()));
                    resultType.getExtendedAttributes().put("message",
                            VendorErrorCode.UNKNOWN_VENDOR_ERROR.getMsg() + " - " + errorMessage);

                }
            }

        }

        return resultType;
    }

    public ResultType getPriceResultType(ClsGetPriceRS response) {

        ResultType resultType = null;

        int resultTypeCode = response.getOpResult() != null ? response.getOpResult().getCode() : VENDOR_ERROR_ID;

        if (resultTypeCode == VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId()) {

            resultType = new ResultType(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId(),
                    VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());

        } else if (ArrayUtils.contains(PRODUCT_NOT_FOUND_KEYS, resultTypeCode)) {

            resultType = new ResultType(VendorErrorCode.PRODUCT_NOT_FOUND.getId(),
                    VendorErrorCode.PRODUCT_NOT_FOUND.getMsg());

        } else if (ArrayUtils.contains(PRODUCT_UNAVAILABLE_KEYS, resultTypeCode)) {

            resultType = new ResultType(VendorErrorCode.PRODUCT_UNAVAILABLE.getId(),
                    VendorErrorCode.PRODUCT_UNAVAILABLE.getMsg());

        } else {

            resultType = new ResultType(VendorErrorCode.UNKNOWN_VENDOR_ERROR.getId(),
                    VendorErrorCode.UNKNOWN_VENDOR_ERROR.getMsg());
        }

        return resultType;

    }
}
