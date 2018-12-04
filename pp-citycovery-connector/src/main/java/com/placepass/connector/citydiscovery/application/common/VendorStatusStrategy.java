package com.placepass.connector.citydiscovery.application.common;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsBookInfoRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsBookRS;
import com.placepass.connector.common.booking.VendorErrorCode;
import com.placepass.connector.common.common.ResultType;

/*
*
*   1   IP not allowed  
*   2   ProcessType is invalid  
*   3   Error code not recognized   
*   4   ActivityLanguage not recognized 
*   168 AgentSine not recognized    
*   169 AgentDutyCode invalid   
*   201 Tour ID or Tour Date is missing 
*   202 This Activity is not allowed to be distributed. 
*   203 Tour is not available on the selected date  
*   204 ActivityCity and ActivityCountry are required   
*   205 ActivityCity does not exist in our database 
*   206 ActivityCountry does not exist in our database  
*   207 ActivityCity and ActivityCountry does not exist in our database 
*   208 ActivityCategory does not exist in our database 
*   209 ActivitySubCategory does not exist in our database  
*   210 ActivityRegion does not exist in our database   
*   211 ActivityDate is not a valid date    
*   212 ActivityCurrency does not exist in our database 
*   213 ActivityDate is required    
*   214 ActivityID is required  
*   215 ActivityCountry does not exist in our database  
*   301 BookingReferenceCityDiscovery is required   
*   302 BookingReferenceCityDiscovery does not exist in our database    
*   303 ActivityCountry is required 
*   304 ActivityCategory is does not exist  
*   305 ActivityCategory is not found in ActivityCity   
*   306 ActivityID is required  
*   307 ActivityPriceID is required 
*   308 ActivityPriceOptionDepartureTime is required    
*   309 BookingDate is required 
*   310 BookingNumberAdults is required 
*   311 BookingLastName is required 
*   312 returnErrorMessage is required  
*   313 BookingReferenceNumber is required  
*   314 CreditCardName is required  
*   315 CreditCardBillingAddress is required    
*   316 CreditCardBillingZip is required    
*   317 CreditCardBillingCity is required   
*   318 CreditCardBillingCountry is required    
*   319 CreditCardType is required  
*   320 CreditCardNum is required   
*   321 CreditCardExpiryDate is required    
*   322 CreditCardSecCode is required   
*   323 BookingPrice is not equal to City Discovery charge price    
*   324 NameOfHotel is required. Name of hotel or residence 
*   325 HotelAddress is required. Hotel or residence address    
*   326 ActivityPriceOptionDepartureTime does not exist from the list - 
*   327 Booking is already cancelled -  
*   328 BookingEmailAddress is required 
*   329 BookingDate is invalid  
*   330 BookingPriceCurrency is required    
*   331 BookingPrice is required    
*   332 BookingPriceCurrency is invalid 
*
*/

@Component
public class VendorStatusStrategy {

    private static final int[] PRODUCT_NOT_FOUND_KEYS = {201, 202, 326};

    private static final int[] PRODUCT_UNAVAILABLE_KEYS = {203};

    private static final int[] BOOKING_PARAMS_INVALID_KEYS = {2, 3, 4, 168, 169, 205, 206, 207, 208, 209, 210, 211, 212,
        215, 302, 304, 305, 323, 329, 332};

    private static final int[] BOOKING_DETAILS_INCOMPLETE_KEYS = {204, 213, 214, 301, 303, 306, 307, 308, 309, 310, 311,
        312, 313, 324, 325, 328, 330, 331};

    private static final int[] PAYMENT_DECLINED_KEYS = {314, 315, 316, 317, 318, 319, 320, 321, 322};

    public ResultType getResultType(ClsBookRS response) {

        ResultType resultType = null;

        ClsBookInfoRS bookingInfoRS = response.getBookInfoRS();

        String bookingStatusText = (bookingInfoRS != null && bookingInfoRS.getBookingStatus() != null)
                ? bookingInfoRS.getBookingStatus() : "";

        if (VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg().equals(response.getResultType().getErrorMessage())) {

            if (com.placepass.connector.citydiscovery.application.util.VendorErrorCode.VENDOR_BOOKING_CONFIRMED.getMsg()
                    .equals(bookingStatusText)) {
                resultType = new ResultType(VendorErrorCode.CONFIRMED.getId(),
                        VendorErrorCode.CONFIRMED.getMsg() + " - " + bookingStatusText);

            } else if (com.placepass.connector.citydiscovery.application.util.VendorErrorCode.VENDOR_BOOKING_ON_REQUEST
                    .getMsg().equals(bookingStatusText)) {
                resultType = new ResultType(VendorErrorCode.PENDING.getId(),
                        VendorErrorCode.PENDING.getMsg() + " - " + bookingStatusText);

            } else if (com.placepass.connector.citydiscovery.application.util.VendorErrorCode.VENDOR_BOOKING_NOT_AVAILABLE
                    .getMsg().equals(bookingStatusText)) {
                resultType = new ResultType(VendorErrorCode.REJECTED.getId(),
                        VendorErrorCode.REJECTED.getMsg() + " - " + bookingStatusText);
            } else {

                resultType = new ResultType(VendorErrorCode.REJECTED.getId(),
                        VendorErrorCode.REJECTED.getMsg() + " - " + bookingStatusText);
                resultType.getExtendedAttributes().put(
                        String.valueOf(VendorErrorCode.VENDOR_RETURN_UNMONITERED_STATUS.getId()),
                        VendorErrorCode.VENDOR_RETURN_UNMONITERED_STATUS.getMsg());
            }

        } else {

            int errorCode = response.getResultType().getErrorCode();
            String errorMessage = (response != null && response.getResultType() != null)
                    ? response.getResultType().getErrorMessage() : "";

            resultType = new ResultType(VendorErrorCode.REJECTED.getId(), VendorErrorCode.REJECTED.getMsg());

            if (ArrayUtils.contains(PRODUCT_NOT_FOUND_KEYS, errorCode)) {

                resultType.getExtendedAttributes().put("code",
                        String.valueOf(VendorErrorCode.PRODUCT_NOT_FOUND.getId()));
                resultType.getExtendedAttributes().put("message",
                        VendorErrorCode.PRODUCT_NOT_FOUND.getMsg() + " - " + errorMessage);

            } else if (ArrayUtils.contains(PRODUCT_UNAVAILABLE_KEYS, errorCode)) {

                resultType.getExtendedAttributes().put("code",
                        String.valueOf(VendorErrorCode.PRODUCT_UNAVAILABLE.getId()));
                resultType.getExtendedAttributes().put("message",
                        VendorErrorCode.PRODUCT_UNAVAILABLE.getMsg() + " - " + errorMessage);

            } else if (ArrayUtils.contains(BOOKING_PARAMS_INVALID_KEYS, errorCode)) {

                resultType.getExtendedAttributes().put("code",
                        String.valueOf(VendorErrorCode.BOOKING_PARAMS_INVALID.getId()));
                resultType.getExtendedAttributes().put("message",
                        VendorErrorCode.BOOKING_PARAMS_INVALID.getMsg() + " - " + errorMessage);

            } else if (ArrayUtils.contains(BOOKING_DETAILS_INCOMPLETE_KEYS, errorCode)) {

                resultType.getExtendedAttributes().put("code",
                        String.valueOf(VendorErrorCode.BOOKING_DETAILS_INCOMPLETE.getId()));
                resultType.getExtendedAttributes().put("message",
                        VendorErrorCode.BOOKING_DETAILS_INCOMPLETE.getMsg() + " - " + errorMessage);

            } else if (ArrayUtils.contains(PAYMENT_DECLINED_KEYS, errorCode)) {

                resultType.getExtendedAttributes().put("code",
                        String.valueOf(VendorErrorCode.PAYMENT_DECLINED.getId()));
                resultType.getExtendedAttributes().put("message",
                        VendorErrorCode.PAYMENT_DECLINED.getMsg() + " - " + errorMessage);

            } else {

                resultType.getExtendedAttributes().put("code",
                        String.valueOf(VendorErrorCode.UNKNOWN_VENDOR_ERROR.getId()));
                resultType.getExtendedAttributes().put("message",
                        VendorErrorCode.UNKNOWN_VENDOR_ERROR.getMsg() + " - " + errorMessage);

            }
        }

        return resultType;
    }
}
