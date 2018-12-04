package com.urbanadventures.connector.application.booking;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.WebServiceIOException;

import com.urbanadventures.connector.application.common.UrbanAdventuresLogger;
import com.urbanadventures.connector.application.common.VendorStatusStrategy;
import com.urbanadventures.connector.application.soapclient.SoapClientService;
import com.placepass.connector.common.booking.BookingVoucherRQ;
import com.placepass.connector.common.booking.BookingVoucherRS;
import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.CancelBookingRS;
import com.placepass.connector.common.booking.GetBookingQuestionsRS;
import com.placepass.connector.common.booking.GetProductPriceRQ;
import com.placepass.connector.common.booking.GetProductPriceRS;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRS;
import com.placepass.connector.common.booking.VendorErrorCode;
import com.placepass.connector.common.common.ResultType;
import com.urbanadventures.connector.domain.urbanadventures.ClsBookRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsBookRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsCancelBookingRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsCancelBookingRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetBookingVoucherRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetBookingVoucherRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetPriceRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetPriceRS;

@Service
public class BookingServicesImpl implements BookingServices {

    private static final Logger logger = LoggerFactory.getLogger(BookingServicesImpl.class);

    @Autowired
    private Environment environment;

    @Autowired
    private SoapClientService soapClientService;

    @Autowired
    private EmailSpecification emailSpecification;

    @Autowired
    private VendorStatusStrategy vendorStatusStrategy;

    @Autowired
    private MakeBookingRequestSpecification makeBookingRequestSpecification;

    @Override
    public GetProductPriceRS getProductPrice(GetProductPriceRQ getProductPriceRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(UrbanAdventuresLogger.PRODUCT_ID.name(), getProductPriceRQ.getVendorProductId());
        logData.put(UrbanAdventuresLogger.PRODUCT_OPTION_ID.name(), getProductPriceRQ.getVendorProductOptionId());
        logData.put(UrbanAdventuresLogger.BOOKING_DATE.name(), getProductPriceRQ.getBookingDate());
        logger.info("Product Price Request Initiated {} ", logData);

        ClsGetPriceRS clsGetPriceRS = null;
        ClsGetPriceRQ clsGetPriceRQ = null;
        ResultType resultType = new ResultType();

        clsGetPriceRQ = BookingTransformer.toClsGetPriceRQ(getProductPriceRQ);

        logData.put(UrbanAdventuresLogger.NUMBER_OF_ADULTS.name(), clsGetPriceRQ.getNumAdult());
        logData.put(UrbanAdventuresLogger.NUMBER_OF_CHILDS.name(), clsGetPriceRQ.getNumChild());
        try {
            logger.info("Price Request Initiated {} ", logData);
            clsGetPriceRS = soapClientService.getProductPrice(clsGetPriceRQ);
            resultType = vendorStatusStrategy.getPriceResultType(clsGetPriceRS);

            logger.info("Price Request Completed");

        } catch (WebServiceIOException wsioe) {
            logger.error("Urban Adventures Product Price Request Error", wsioe);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());

        } catch (Exception e) {
            logger.error("Urban Adventures Product Price Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
        }
        logger.info("Returning Product Price Response");
        return BookingTransformer.toGetProductPriceRS(clsGetPriceRS, resultType);
    }

    @Override
    public MakeBookingRS makeBooking(MakeBookingRQ makeBookingRQ) {

        String[] activeProfiles = environment.getActiveProfiles();

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(UrbanAdventuresLogger.PRODUCT_ID.name(),
                makeBookingRQ.getBookingOptions().get(0).getVendorProductId());
        logData.put(UrbanAdventuresLogger.PRODUCT_OPTION_ID.name(),
                makeBookingRQ.getBookingOptions().get(0).getVendorProductOptionId());
        logData.put(UrbanAdventuresLogger.BOOKING_DATE.name(),
                makeBookingRQ.getBookingOptions().get(0).getBookingDate());
        logData.put(UrbanAdventuresLogger.ACTIVE_ENVIRONMENT.name(), activeProfiles[0]);
        logger.info("Make Booking Request Initiated {} ", logData);

        ResultType resultType = null;
        ClsBookRS clsBookRS = null;
        ClsGetBookingVoucherRS clsGetBookingVoucherRS = null;

        if (makeBookingRequestSpecification.isSatisfiedBy(makeBookingRQ)) {
            // Start, Binding values urbanadventures >><< placepass
            ClsBookRQ uaClsBookRQ = BookingTransformer.toClsBookRQ(makeBookingRQ);

            logData.put(UrbanAdventuresLogger.NUMBER_OF_ADULTS.name(), uaClsBookRQ.getNumAdult());
            logData.put(UrbanAdventuresLogger.NUMBER_OF_CHILDS.name(), uaClsBookRQ.getNumChild());

            if (emailSpecification.isSatisfiedBy(makeBookingRQ)) {
                // Calling Soap Client Services
                try {

                    logData.put(UrbanAdventuresLogger.VENDOR_METHOD.name(), "makeBooking()");
                    logger.info("Booking Request Initiated {} ", logData);

                    clsBookRS = soapClientService.makeBooking(uaClsBookRQ);
                    resultType = vendorStatusStrategy.getBookingResultType(clsBookRS);

                    logger.info("Booking Request Completed");

                } catch (WebServiceIOException wsioe) {
                    logger.error("Urban Adventures Booking Request Error", wsioe);
                    resultType = new ResultType(VendorErrorCode.FAILED.getId(), VendorErrorCode.FAILED.getMsg());
                    resultType.getExtendedAttributes().put("code",
                            String.valueOf(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId()));
                    resultType.getExtendedAttributes().put("message",
                            VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());

                } catch (Exception e) {
                    logger.error("Urban Adventures Booking Request Error", e);
                    resultType = new ResultType(VendorErrorCode.FAILED.getId(), VendorErrorCode.FAILED.getMsg());
                    resultType.getExtendedAttributes().put("code",
                            String.valueOf(VendorErrorCode.VENDOR_API_ERROR.getId()));
                    resultType.getExtendedAttributes().put("message", VendorErrorCode.VENDOR_API_ERROR.getMsg());
                }

                // for success booking
                if (resultType.getCode() == VendorErrorCode.CONFIRMED.getId()) {
                    clsGetBookingVoucherRS = this.retrieveVoucherDetails(logData, resultType, clsBookRS);
                }
            } else {
                logger.info("LIVE BOOKING : " + "ONLY PERMITTED EMAILS ARE ALLOWED");
                resultType = new ResultType(VendorErrorCode.REQUEST_FAILED.getId(),
                        "Only permitted Emails are allowed");
            }
        } else {
            logger.error("LIVE BOOKING PLACE PASS REQUEST BINDING ERROR");
            resultType = new ResultType(VendorErrorCode.BOOKING_REQUEST_BINDING_ERROR.getId(),
                    VendorErrorCode.BOOKING_REQUEST_BINDING_ERROR.getMsg());
        }

        logger.info("Returning Booking Response");
        return BookingTransformer.toMakeBookingRS(clsBookRS, clsGetBookingVoucherRS, makeBookingRQ, resultType);
    }

    private ClsGetBookingVoucherRS retrieveVoucherDetails(Map<String, Object> logData, ResultType resultType, ClsBookRS clsBookRS) {
        ClsGetBookingVoucherRS clsGetBookingVoucherRS = null;
        try {
            ClsGetBookingVoucherRQ clsGetBookingVoucherRQ = BookingTransformer.toClsGetBookingVoucherRQ(clsBookRS);

            logData.put(UrbanAdventuresLogger.VENDOR_METHOD.name(), "getBookingVoucher()");
            logger.info("BookingVoucher Request Initiated {} ", logData);
            clsGetBookingVoucherRS = soapClientService.getBookingVoucher(clsGetBookingVoucherRQ);
            logger.info("BookingVoucher Request Completed");

        } catch (WebServiceIOException wsioe) {
            logger.error("Urban Adventures BookingVoucher Request Error", wsioe);
            resultType.setMessage(
                    "BOOKING: CONFIRMED. VOUCHER: " + VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());

        } catch (Exception e) {
            logger.error("Urban Adventures BookingVoucher Request Error", e);
            resultType.setMessage("BOOKING: CONFIRMED. VOUCHER: " + VendorErrorCode.VENDOR_API_ERROR.getMsg());
        }
        return clsGetBookingVoucherRS;
    }

    @Override
    public BookingVoucherRS getVoucherDetails(BookingVoucherRQ bookingVoucherRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(UrbanAdventuresLogger.BOOKING_REFERENCE.name(), bookingVoucherRQ.getReferenceNumber());
        logger.info("Get Voucher Details Request Initiated {} ", logData);

        ClsGetBookingVoucherRS clsGetBookingVoucherRS = null;
        ResultType resultType = new ResultType();
        BookingVoucherRS bookingVoucherRS = new BookingVoucherRS();

        ClsGetBookingVoucherRQ clsGetBookingVoucherRQ = BookingTransformer.toClsGetBookingVoucherRQ(bookingVoucherRQ);
        try {
            logger.info("Voucher Request Initiated {} ", logData);
            clsGetBookingVoucherRS = soapClientService.getBookingVoucher(clsGetBookingVoucherRQ);
            logger.info("Voucher Request Completed");

            bookingVoucherRS = BookingTransformer.toBookingVoucherRS(clsGetBookingVoucherRS);

        } catch (WebServiceIOException wsioe) {
            logger.error("Urban Adventures Voucher Request Error", wsioe);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("Urban Adventures Voucher Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);
        }
        logger.info("Returning Voucher Response");
        return bookingVoucherRS;
    }

    @Override
    public GetBookingQuestionsRS getBookingQuestions() {
        ResultType resultType = new ResultType();
        // Since ua doesn't support booking questions
        resultType.setCode(1);
        resultType.setMessage(VendorErrorCode.QUESTION_NOT_FOUND_ERROR.getMsg());

        GetBookingQuestionsRS getBookingQuestionsRS = new GetBookingQuestionsRS();
        getBookingQuestionsRS.setResultType(resultType);

        logger.info("Returning Booking Questions Response");
        return getBookingQuestionsRS;
    }

    @Override
    public CancelBookingRS cancelBooking(CancelBookingRQ cancelBookingRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(UrbanAdventuresLogger.BOOKING_REFERENCE.name(), cancelBookingRQ.getBookingReferenceId());
        logData.put(UrbanAdventuresLogger.BOOKING_ID.name(), cancelBookingRQ.getBookingId());
        logData.put(UrbanAdventuresLogger.BOOKING_STATUS.name(), cancelBookingRQ.getBookingStatus());
        logData.put(UrbanAdventuresLogger.CANCELATION_TYPE.name(), cancelBookingRQ.getCancelationType());
        logger.info("Cancel Booking Request Initiated {} ", logData);

        ClsCancelBookingRS clsCancelBookingRS = null;
        ResultType resultType = new ResultType();
        CancelBookingRS cancelBookingRS = new CancelBookingRS();

        ClsCancelBookingRQ clsCancelBookingRQ = BookingTransformer.toClsCancelBookingRQ(cancelBookingRQ);
        try {
            logger.info("Booking Cancel Request Initiated {} ", logData);
            clsCancelBookingRS = soapClientService.cancelBooking(clsCancelBookingRQ);
            logger.info("Booking Cancel Request Completed");

            cancelBookingRS = BookingTransformer.toCancelBookingRS(clsCancelBookingRS);

        } catch (WebServiceIOException wsioe) {
            logger.error("Urban Adventures Cancel Booking Request Error", wsioe);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            cancelBookingRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("Urban Adventures Cancel Booking Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            cancelBookingRS.setResultType(resultType);
        }
        logger.info("Returning Cancel Booking Response");
        return cancelBookingRS;
    }
}
