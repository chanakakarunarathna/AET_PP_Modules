package com.placepass.connector.bemyguest.application.booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.google.gson.Gson;
import com.placepass.connector.bemyguest.application.util.BeMyGuestLogger;
import com.placepass.connector.bemyguest.application.util.VendorErrorCode;
import com.placepass.connector.bemyguest.application.util.VendorQuestions;
import com.placepass.connector.bemyguest.domain.bemyguest.booking.BmgBookRequest;
import com.placepass.connector.bemyguest.domain.bemyguest.booking.BmgBookResponse;
import com.placepass.connector.bemyguest.domain.bemyguest.booking.BmgGetBookingStatusResponse;
import com.placepass.connector.bemyguest.domain.bemyguest.bookingcheck.BeMyGuestCheckBookingRQ;
import com.placepass.connector.bemyguest.domain.bemyguest.bookingcheck.BeMyGuestCheckBookingRS;
import com.placepass.connector.bemyguest.domain.bemyguest.common.BeMyGuestError;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgProductDetail;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgProductDetailService;
import com.placepass.connector.common.booking.BookingQuestion;
import com.placepass.connector.common.booking.BookingVoucherRQ;
import com.placepass.connector.common.booking.BookingVoucherRS;
import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.CancelBookingRS;
import com.placepass.connector.common.booking.GetBookingQuestionsRS;
import com.placepass.connector.common.booking.GetProductPriceRQ;
import com.placepass.connector.common.booking.GetProductPriceRS;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRS;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.bemyguest.infrastructure.RestClient;
import com.placepass.connector.bemyguest.placepass.algolia.infrastructure.AlgoliaSearchClient;
import com.placepass.utils.vendorproduct.ProductHashGenerator;

@Service
public class BookingServiceImpl implements BookingService {

    private Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    private static final String CONFIRM = "confirm";

    private static final String APPROVE_STATUS = "approved";

    private static final String WAITING_STATUS = "waiting";

    @Autowired
    RestClient restClient;

    @Autowired
    AlgoliaSearchClient algoliaSearchClient;

    @Autowired
    @Qualifier("ProductHashGenerator")
    private ProductHashGenerator productHashGenerator;

    @Autowired
    private BmgProductDetailService bmgProductDetailService;

    @Value("${bmg.isTest}")
    private boolean isTest;

    @Override
    public GetProductPriceRS getProductPrice(GetProductPriceRQ getProductPriceRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(BeMyGuestLogger.PRODUCT_ID.name(), getProductPriceRQ.getProductId());
        logData.put(BeMyGuestLogger.PRODUCT_OPTION_ID.name(), getProductPriceRQ.getProductOptionId());
        logData.put(BeMyGuestLogger.VENDOR_PRODUCT_ID.name(), getProductPriceRQ.getVendorProductId());
        logData.put(BeMyGuestLogger.VENDOR_PRODUCT_OPTION_ID.name(), getProductPriceRQ.getVendorProductOptionId());
        logData.put(BeMyGuestLogger.BOOKING_DATE.name(), getProductPriceRQ.getBookingDate());
        logger.info("Product Price Request Initiated {} ", logData);
               
        GetProductPriceRS getProductPriceRS = new GetProductPriceRS();
        ResultType resultType = new ResultType();
        BeMyGuestCheckBookingRQ tobeMyGuestCheckBookingRQ = BookingTransformer.tobeMyGuestCheckBookingRQ(getProductPriceRQ);
        BeMyGuestCheckBookingRS beMyGuestCheckBookingRS = null;        
        
        Map<String, String> extendedAttributes = new HashMap<String, String>();
        Gson gson = new Gson();
        
        try {
            logger.info("BeMyGuest PriceRequest Initiated {} ", logData);
            beMyGuestCheckBookingRS = restClient.getProductPrice(tobeMyGuestCheckBookingRQ);
            logger.info("BeMyGuest PriceRequest Completed {} ", logData);

            getProductPriceRS = BookingTransformer.toGetProductPriceRS(beMyGuestCheckBookingRS);

        } catch (HttpClientErrorException hce) {
            logger.error("BeMyGuest Product Price Request Error {}", hce.getResponseBodyAsString());
            BeMyGuestError beMyGuestError = gson.fromJson(hce.getResponseBodyAsString(), BeMyGuestError.class);
            if (beMyGuestError.getError() != null) {
                extendedAttributes.put(beMyGuestError.getError().getCode(),
                        beMyGuestError.getError().getHttp_code() + " : " + beMyGuestError.getError().getMessage());
            }
            resultType.setCode(VendorErrorCode.VENDOR_REQUEST_FAILED_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_REQUEST_FAILED_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);
            getProductPriceRS.setResultType(resultType);
            return getProductPriceRS;

        } catch (HttpServerErrorException hse) {
            logger.error("BeMyGuest Product Price Request Error {}", hse.getResponseBodyAsString());
            BeMyGuestError beMyGuestError = gson.fromJson(hse.getResponseBodyAsString(), BeMyGuestError.class);
            if (beMyGuestError.getError() != null) {
                extendedAttributes.put(beMyGuestError.getError().getCode(),
                        beMyGuestError.getError().getHttp_code() + " : " + beMyGuestError.getError().getMessage());
            }
            resultType.setCode(VendorErrorCode.VENDOR_REQUEST_FAILED_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_REQUEST_FAILED_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);
            getProductPriceRS.setResultType(resultType);
            return getProductPriceRS;

        } catch (ResourceAccessException rae) {
            logger.error("BeMyGuest Product Price Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            getProductPriceRS.setResultType(resultType);
            return getProductPriceRS;

        } catch (Exception e) {
            logger.error("BeMyGuest Product Price Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getProductPriceRS.setResultType(resultType);
            return getProductPriceRS;
        }
        logger.info("Product PriceRequest Completed {} ");
        return getProductPriceRS;
    }

    @Override
    public MakeBookingRS makeBooking(MakeBookingRQ makeBookingRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(BeMyGuestLogger.CART_ID.name(), makeBookingRQ.getCartId());
        logData.put(BeMyGuestLogger.PRODUCT_ID.name(), makeBookingRQ.getBookingOptions().get(0).getVendorProductId());
        logData.put(BeMyGuestLogger.PRODUCT_OPTION_ID.name(),
                makeBookingRQ.getBookingOptions().get(0).getVendorProductOptionId());
        logData.put(BeMyGuestLogger.BOOKING_DATE.name(), makeBookingRQ.getBookingOptions().get(0).getBookingDate());
        logData.put(BeMyGuestLogger.BOOKER_EMAIL.name(), makeBookingRQ.getBookerDetails().getEmail());
        logger.info("Make Booking Request Initiated {} ", logData);

        MakeBookingRS makeBookingRS = new MakeBookingRS();
        ResultType resultType = new ResultType();
        BmgBookResponse bmgBookResponse = null;
        Gson gson = new Gson();
        Map<String, String> extendedAttributes = new HashMap<String, String>();

        try {
            BmgBookRequest bmgBookingRequest = BookingTransformer.toBookingRequest(makeBookingRQ, isTest);
            logData.put(BeMyGuestLogger.VENDOR_METHOD.name(), "createBooking()");
            logger.info("Bemyguest BookRequest Initiated {} ", logData);
            bmgBookResponse = restClient.createBooking(bmgBookingRequest);

            resultType.setCode(VendorErrorCode.PENDING.getId());
            resultType.setMessage(VendorErrorCode.PENDING.getMsg());
            extendedAttributes.put("Vendor Booking Status", bmgBookResponse.getData().getStatus());
            resultType.setExtendedAttributes(extendedAttributes);

            makeBookingRS = BookingTransformer.toBookingResponse(null, bmgBookResponse, resultType);
            
        } catch (HttpClientErrorException hce) {
            logger.error("BeMyGuest BookRequest Error {}", hce.getResponseBodyAsString());
            BeMyGuestError beMyGuestError = gson.fromJson(hce.getResponseBodyAsString(), BeMyGuestError.class);
            resultType.setCode(VendorErrorCode.REQUEST_FAILED.getId());
            resultType.setMessage(VendorErrorCode.REQUEST_FAILED.getMsg());
            if (beMyGuestError.getError() != null) {
                extendedAttributes.put(beMyGuestError.getError().getCode(),
                        beMyGuestError.getError().getHttp_code() + " : " + beMyGuestError.getError().getMessage());
            }
            resultType.setExtendedAttributes(extendedAttributes);
            makeBookingRS.setResultType(resultType);
            return makeBookingRS;

        } catch (HttpServerErrorException hse) {
            logger.error("BeMyGuest BookRequest Error {}", hse.getResponseBodyAsString());
            BeMyGuestError beMyGuestError = gson.fromJson(hse.getResponseBodyAsString(), BeMyGuestError.class);
            resultType.setCode(VendorErrorCode.REQUEST_FAILED.getId());
            resultType.setMessage(VendorErrorCode.REQUEST_FAILED.getMsg());
            if (beMyGuestError.getError() != null) {
                extendedAttributes.put(beMyGuestError.getError().getCode(),
                        beMyGuestError.getError().getHttp_code() + " : " + beMyGuestError.getError().getMessage());
            }
            resultType.setExtendedAttributes(extendedAttributes);

            makeBookingRS.setResultType(resultType);
            return makeBookingRS;

        } catch (ResourceAccessException rae) {
            logger.error("BeMyGuest BookRequest Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            makeBookingRS.setResultType(resultType);
            return makeBookingRS;

        } catch (Exception e) {
            logger.error("BeMyGuest BookRequest Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            makeBookingRS.setResultType(resultType);
            return makeBookingRS;
        }

        try {
            Map<String, Object> urlVariables = new HashMap<String, Object>();
            BmgBookResponse bmgUpdateBookingStatusResponse = null;
            urlVariables.put("uuid", makeBookingRS.getReferenceNumber());
            urlVariables.put("status", CONFIRM);

            logData.put(BeMyGuestLogger.VENDOR_METHOD.name(), "updateBookingStatus()");
            logger.info("Bemyguest Update Booking Status Request Initiated {} ", logData);

            bmgUpdateBookingStatusResponse = restClient.updateBookingStatus(urlVariables);

            if (APPROVE_STATUS.equalsIgnoreCase(bmgUpdateBookingStatusResponse.getData().getStatus())) {
                makeBookingRS.getResultType().setCode(VendorErrorCode.CONFIRMED.getId());
                makeBookingRS.getResultType().setMessage(VendorErrorCode.CONFIRMED.getMsg());
                makeBookingRS.getResultType().getExtendedAttributes().put("Vendor Booking Status",
                        bmgUpdateBookingStatusResponse.getData().getStatus());

            } else if (WAITING_STATUS.equalsIgnoreCase(bmgUpdateBookingStatusResponse.getData().getStatus())) {
                makeBookingRS.getResultType().getExtendedAttributes().put("Vendor Booking Status",
                        bmgUpdateBookingStatusResponse.getData().getStatus());
            }

            BookingTransformer.toBookingResponse(makeBookingRS, bmgUpdateBookingStatusResponse, null);
            logger.info("Bemyguest Update Booking Status Request Completed");

        } catch (HttpClientErrorException hce) {
            logger.error("BeMyGuest Update Booking Status Request Error {}", hce.getResponseBodyAsString());
            BeMyGuestError beMyGuestError = gson.fromJson(hce.getResponseBodyAsString(), BeMyGuestError.class);
            makeBookingRS.getResultType().setMessage(makeBookingRS.getResultType().getMessage()
                    + ". CONFIRMATION FAILED: " + VendorErrorCode.VENDOR_REQUEST_FAILED_ERROR.getMsg());
            if (beMyGuestError.getError() != null) {
                makeBookingRS.getResultType().getExtendedAttributes().put(beMyGuestError.getError().getCode(),
                        beMyGuestError.getError().getHttp_code() + " : " + beMyGuestError.getError().getMessage());
            }

        } catch (HttpServerErrorException hse) {
            logger.error("BeMyGuest Update Booking Status Request Error {}", hse.getResponseBodyAsString());
            BeMyGuestError beMyGuestError = gson.fromJson(hse.getResponseBodyAsString(), BeMyGuestError.class);
            makeBookingRS.getResultType().setMessage(makeBookingRS.getResultType().getMessage()
                    + ". CONFIRMATION FAILED: " + VendorErrorCode.VENDOR_REQUEST_FAILED_ERROR.getMsg());
            if (beMyGuestError.getError() != null) {
                makeBookingRS.getResultType().getExtendedAttributes().put(beMyGuestError.getError().getCode(),
                        beMyGuestError.getError().getHttp_code() + " : " + beMyGuestError.getError().getMessage());
            }

        } catch (ResourceAccessException rae) {
            logger.error("BeMyGuest Update Booking Status Request Error", rae);
            makeBookingRS.getResultType().setMessage(makeBookingRS.getResultType().getMessage()
                    + ". CONFIRMATION FAILED: " + VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());

        } catch (Exception e) {
            logger.error("BeMyGuest Update Booking Status Request Error", e);
            makeBookingRS.getResultType().setMessage(makeBookingRS.getResultType().getMessage()
                    + ". CONFIRMATION FAILED: " + VendorErrorCode.VENDOR_API_ERROR.getMsg());

        }

        logger.info("Returning Booking Response");

        return makeBookingRS;

    }

    @Override
    public GetBookingQuestionsRS getBookingQuestions(String productId, String currencyCode) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(BeMyGuestLogger.PRODUCT_ID.name(), productId);
        logData.put(BeMyGuestLogger.CUR_CODE.name(), currencyCode);
        logger.info("Get Booking Questions Request Initiated {} ", logData);

        GetBookingQuestionsRS getBookingQuestionsRS = new GetBookingQuestionsRS();
        ResultType resultType = new ResultType();
        List<BookingQuestion> bookingQuestions = new ArrayList<>();

        BmgProductDetail bmgProductDetail = bmgProductDetailService.getBmgDetails(productId);
        if (bmgProductDetail != null) {
            if (bmgProductDetail.isHotelPickup()) {
                BookingQuestion hotelNameBookingQuestion = new BookingQuestion();
                hotelNameBookingQuestion.setQuestionId(VendorQuestions.VENDOR_QUESTION_NAME_OF_HOTEL.getId());
                hotelNameBookingQuestion.setMessage(VendorQuestions.VENDOR_QUESTION_NAME_OF_HOTEL.getQuestionInfo());
                hotelNameBookingQuestion.setRequired(true);
                hotelNameBookingQuestion
                        .setStringQuestionId(String.valueOf(VendorQuestions.VENDOR_QUESTION_NAME_OF_HOTEL.getId()));
                hotelNameBookingQuestion.setSubTitle("");
                hotelNameBookingQuestion.setTitle(VendorQuestions.VENDOR_QUESTION_HOTEL_PICKUP_TITLE.getQuestionInfo());
                bookingQuestions.add(hotelNameBookingQuestion);

                BookingQuestion hotelAddressBookingQuestion = new BookingQuestion();
                hotelAddressBookingQuestion.setQuestionId(VendorQuestions.VENDOR_QUESTION_HOTEL_ADDRESS.getId());
                hotelAddressBookingQuestion.setMessage(VendorQuestions.VENDOR_QUESTION_HOTEL_ADDRESS.getQuestionInfo());
                hotelAddressBookingQuestion.setRequired(true);
                hotelAddressBookingQuestion
                        .setStringQuestionId(String.valueOf(VendorQuestions.VENDOR_QUESTION_HOTEL_ADDRESS.getId()));
                hotelAddressBookingQuestion.setSubTitle("");
                hotelAddressBookingQuestion.setTitle(VendorQuestions.VENDOR_QUESTION_HOTEL_PICKUP_TITLE.getQuestionInfo());
                bookingQuestions.add(hotelAddressBookingQuestion);
            }
            if (bmgProductDetail.isAirportPickup()) {
                BookingQuestion flightNumberBookingQuestion = new BookingQuestion();
                flightNumberBookingQuestion.setQuestionId(VendorQuestions.VENDOR_QUESTION_FLIGHT_NUMBER.getId());
                flightNumberBookingQuestion.setMessage(VendorQuestions.VENDOR_QUESTION_FLIGHT_NUMBER.getQuestionInfo());
                flightNumberBookingQuestion.setRequired(true);
                flightNumberBookingQuestion
                        .setStringQuestionId(String.valueOf(VendorQuestions.VENDOR_QUESTION_FLIGHT_NUMBER.getId()));
                flightNumberBookingQuestion.setSubTitle("");
                flightNumberBookingQuestion.setTitle(VendorQuestions.VENDOR_QUESTION_AIRPORT_PICKUP_TITLE.getQuestionInfo());
                bookingQuestions.add(flightNumberBookingQuestion);

                BookingQuestion flightDateTimeBookingQuestion = new BookingQuestion();
                flightDateTimeBookingQuestion
                        .setQuestionId(VendorQuestions.VENDOR_QUESTION_FLIGHT_DATE_AND_TIME.getId());
                flightDateTimeBookingQuestion
                        .setMessage(VendorQuestions.VENDOR_QUESTION_FLIGHT_DATE_AND_TIME.getQuestionInfo());
                flightDateTimeBookingQuestion.setRequired(true);
                flightDateTimeBookingQuestion.setStringQuestionId(
                        String.valueOf(VendorQuestions.VENDOR_QUESTION_FLIGHT_DATE_AND_TIME.getId()));
                flightDateTimeBookingQuestion.setSubTitle("");
                flightDateTimeBookingQuestion.setTitle(VendorQuestions.VENDOR_QUESTION_AIRPORT_PICKUP_TITLE.getQuestionInfo());
                bookingQuestions.add(flightDateTimeBookingQuestion);

                BookingQuestion flightDestinationBookingQuestion = new BookingQuestion();
                flightDestinationBookingQuestion
                        .setQuestionId(VendorQuestions.VENDOR_QUESTION_FLIGHT_DESTINATION.getId());
                flightDestinationBookingQuestion
                        .setMessage(VendorQuestions.VENDOR_QUESTION_FLIGHT_DESTINATION.getQuestionInfo());
                flightDestinationBookingQuestion.setRequired(true);
                flightDestinationBookingQuestion.setStringQuestionId(
                        String.valueOf(VendorQuestions.VENDOR_QUESTION_FLIGHT_DESTINATION.getId()));
                flightDestinationBookingQuestion.setSubTitle("");
                flightDestinationBookingQuestion.setTitle(VendorQuestions.VENDOR_QUESTION_AIRPORT_PICKUP_TITLE.getQuestionInfo());
                bookingQuestions.add(flightDestinationBookingQuestion);
            }
        }
        if (!bookingQuestions.isEmpty()) {
            resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());
            getBookingQuestionsRS.setBookingQuestions(bookingQuestions);
        } else {
            resultType.setCode(VendorErrorCode.QUESTION_NOT_FOUND_ERROR.getId());
            resultType.setMessage(VendorErrorCode.QUESTION_NOT_FOUND_ERROR.getMsg());
        }
        getBookingQuestionsRS.setResultType(resultType);
        logger.info("Get Booking Questions Request Completed {} ", logData);
        return getBookingQuestionsRS;
    }

    @Override
    public BookingVoucherRS getVoucherDetails(BookingVoucherRQ bookingVoucherRQ) {
        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(BeMyGuestLogger.BOOKING_REFERENCE.name(), bookingVoucherRQ.getReferenceNumber());
        logger.info("Get Voucher Details Request Initiated {} ", logData);

        BookingVoucherRS bookingVoucherRS = new BookingVoucherRS();
        ResultType resultType = new ResultType();
        BmgGetBookingStatusResponse bmgBookingVoucherRS = null;

        Map<String, String> extendedAttributes = new HashMap<String, String>();
        Gson gson = new Gson();
        try {

            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("uuid", bookingVoucherRQ.getReferenceNumber());

            logger.info("BeMyGuest Voucher Details Request Initiated {} ", logData);
            bmgBookingVoucherRS = restClient.getVoucherDetails(urlVariables);
            logger.info("BeMyGuest Voucher Details Request Completed");
            bookingVoucherRS = BookingTransformer.toVoucherResponse(bmgBookingVoucherRS);

        } catch (HttpServerErrorException hse) {
            logger.error("BeMyGuest Voucher Details Request Error{}", hse.getResponseBodyAsString());
            BeMyGuestError beMyGuestError = gson.fromJson(hse.getResponseBodyAsString(), BeMyGuestError.class);
            resultType.setCode(VendorErrorCode.VENDOR_REQUEST_FAILED_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_REQUEST_FAILED_ERROR.getMsg());
            if (beMyGuestError.getError() != null) {
                extendedAttributes.put(beMyGuestError.getError().getCode(),
                        beMyGuestError.getError().getHttp_code() + " : " + beMyGuestError.getError().getMessage());
            }
            resultType.setExtendedAttributes(extendedAttributes);
            bookingVoucherRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("BeMyGuest Voucher Details Request Error{}", hce.getResponseBodyAsString());
            BeMyGuestError beMyGuestError = gson.fromJson(hce.getResponseBodyAsString(), BeMyGuestError.class);
            resultType.setCode(VendorErrorCode.VENDOR_REQUEST_FAILED_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_REQUEST_FAILED_ERROR.getMsg());
            if (beMyGuestError.getError() != null) {
                extendedAttributes.put(beMyGuestError.getError().getCode(),
                        beMyGuestError.getError().getHttp_code() + " : " + beMyGuestError.getError().getMessage());
            }
            resultType.setExtendedAttributes(extendedAttributes);
            bookingVoucherRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("BeMyGuest Voucher Details Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("BeMyGuest Voucher Details Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);
        }
        logger.info("Returning Voucher Details Response");
        return bookingVoucherRS;
    }

    @Override
    public CancelBookingRS cancelBooking(CancelBookingRQ cancelBookingRQ) {
        // TODO Auto-generated method stub
        return null;
    }

}
