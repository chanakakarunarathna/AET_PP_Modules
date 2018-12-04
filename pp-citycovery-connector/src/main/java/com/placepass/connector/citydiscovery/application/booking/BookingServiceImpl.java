package com.placepass.connector.citydiscovery.application.booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.placepass.connector.citydiscovery.application.common.VendorStatusStrategy;
import com.placepass.connector.citydiscovery.application.exception.InvalidDateException;
import com.placepass.connector.citydiscovery.application.util.CityDiscoveryLogger;
import com.placepass.connector.citydiscovery.application.util.VendorErrorCode;
import com.placepass.connector.citydiscovery.application.util.VendorQuestions;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsActivityBookingRQ;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsBookRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsBookStatusRQ;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsBookStatusRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsCancelBookingRQ;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsCancelBookingRS;
import com.placepass.connector.citydiscovery.domain.placepass.algolia.product.GetAlgoliaProductDetailsRS;
import com.placepass.connector.citydiscovery.infrastructure.RestClient;
import com.placepass.connector.citydiscovery.placepass.algolia.infrastructure.AlgoliaSearchClient;
import com.placepass.connector.common.booking.BookingQuestion;
import com.placepass.connector.common.booking.BookingStatusRQ;
import com.placepass.connector.common.booking.BookingStatusRS;
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
import com.placepass.exutil.NotFoundException;
import com.placepass.utils.pricematch.PriceMatch;
import com.placepass.utils.pricematch.PriceMatchPriceBreakDown;
import com.placepass.utils.pricematch.PriceMatchPricePerAgeBand;
import com.placepass.utils.pricematch.PriceMatchQuantity;
import com.placepass.utils.pricematch.PriceMatchTotalPrice;
import com.placepass.utils.vendorproduct.ProductHashGenerator;
import com.placepass.utils.vendorproduct.Vendor;

@Service
public class BookingServiceImpl implements BookingService {

    private Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    RestClient restClient;

    @Autowired
    AlgoliaSearchClient algoliaSearchClient;

    @Autowired
    @Qualifier("ProductHashGenerator")
    private ProductHashGenerator productHashGenerator;

    @Autowired
    private EmailSpecification emailSpecification;

    @Autowired
    private MakeBookingRequestSpecification makeBookingRequestSpecification;

    @Autowired
    private VendorStatusStrategy statusStrategy;

    @Override
    public GetProductPriceRS getProductPrice(GetProductPriceRQ productPriceRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(CityDiscoveryLogger.PRODUCT_ID.name(), productPriceRQ.getVendorProductId());
        logData.put(CityDiscoveryLogger.PRODUCT_OPTION_ID.name(), productPriceRQ.getVendorProductOptionId());
        logData.put(CityDiscoveryLogger.BOOKING_DATE.name(), productPriceRQ.getBookingDate());
        logger.info("Product Price Request Initiated {} ", logData);

        GetProductPriceRS productPriceRS = new GetProductPriceRS();
        ResultType resultType = new ResultType();

        try {

            logger.info("CityDiscovery Price Details Request Initiated {} ", logData);

            List<PriceMatchPricePerAgeBand> inputPriceMatchList = BookingTransformer
                    .toPriceMatchPricePerAgeBandToPriceMatch(productPriceRQ.getPrices());

            List<PriceMatchQuantity> priceMatchQuantities = BookingTransformer
                    .toQuantitiesToPriceMatch(productPriceRQ.getQuantities());

            List<PriceMatchPriceBreakDown> priceMatchPriceBreakDowns = PriceMatch
                    .getFilteredPriceList(inputPriceMatchList, priceMatchQuantities);

            PriceMatchTotalPrice priceMatchTotalPrice = PriceMatch.getTotalForQuantities(priceMatchPriceBreakDowns);

            productPriceRS = BookingTransformer.toPricingResponse(priceMatchTotalPrice);

            logger.info("CityDiscovery Price Details Completed");

        } catch (Exception e) {
            logger.error("CityDiscovery Product Price Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            productPriceRS.setResultType(resultType);
        }
        logger.info("Returning CityDiscovery Price Details Response");

        return productPriceRS;
    }

    @Override
    public MakeBookingRS makeBooking(MakeBookingRQ makeBookingRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(CityDiscoveryLogger.BOOKING_ID.name(), makeBookingRQ.getBookingId());
        logData.put(CityDiscoveryLogger.PRODUCT_ID.name(),
                makeBookingRQ.getBookingOptions().get(0).getVendorProductId());
        logData.put(CityDiscoveryLogger.PRODUCT_OPTION_ID.name(),
                makeBookingRQ.getBookingOptions().get(0).getVendorProductOptionId());
        logData.put(CityDiscoveryLogger.BOOKING_DATE.name(), makeBookingRQ.getBookingOptions().get(0).getBookingDate());
        logData.put(CityDiscoveryLogger.BOOKER_EMAIL.name(), makeBookingRQ.getBookerDetails().getEmail());

        ClsActivityBookingRQ clsActivityBookingRQ = null;
        ClsBookRS clsBookRS = null;

        ResultType resultType = null;
        if (emailSpecification.isSatisfiedBy(makeBookingRQ)) {

            try {

                logger.info("CityDiscovery Make a Booking Request Initiated {} ", logData);
                clsActivityBookingRQ = BookingTransformer.toMakeBookingRequest(makeBookingRQ);
                clsBookRS = restClient.makeBooking(clsActivityBookingRQ);
                resultType = statusStrategy.getResultType(clsBookRS);
                logger.info("CityDiscovery Make a Booking Completed");

            } catch (HttpServerErrorException hse) {
                logger.error("CityDiscovery Make a Booking Request Error", hse);

                resultType = new ResultType(VendorErrorCode.FAILED.getId(), VendorErrorCode.FAILED.getMsg());
                resultType.getExtendedAttributes().put("code",
                        String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
                resultType.getExtendedAttributes().put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());

            } catch (InvalidDateException ide) {
                logger.error("CityDiscovery Make a Booking Request Error", ide);

                resultType = new ResultType(VendorErrorCode.FAILED.getId(), VendorErrorCode.FAILED.getMsg());
                resultType.getExtendedAttributes().put("code",
                        String.valueOf(VendorErrorCode.INVALID_DATE_FOUND.getId()));
                resultType.getExtendedAttributes().put("message", VendorErrorCode.INVALID_DATE_FOUND.getMsg());

            } catch (HttpClientErrorException hce) {
                logger.error("CityDiscovery Make a Booking Request Error", hce);

                resultType = new ResultType(VendorErrorCode.FAILED.getId(), VendorErrorCode.FAILED.getMsg());
                resultType.getExtendedAttributes().put("code",
                        String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
                resultType.getExtendedAttributes().put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());

            } catch (ResourceAccessException rae) {
                logger.error("CityDiscovery Make a Booking Request Error", rae);

                resultType = new ResultType(VendorErrorCode.FAILED.getId(), VendorErrorCode.FAILED.getMsg());
                resultType.getExtendedAttributes().put("code",
                        String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
                resultType.getExtendedAttributes().put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());

            } catch (Exception e) {
                logger.error("CityDiscovery Make a Booking Request Error", e);

                resultType = new ResultType(VendorErrorCode.FAILED.getId(), VendorErrorCode.FAILED.getMsg());
                resultType.getExtendedAttributes().put("code",
                        String.valueOf(VendorErrorCode.VENDOR_API_ERROR.getId()));
                resultType.getExtendedAttributes().put("message", VendorErrorCode.VENDOR_API_ERROR.getMsg());

            }

        } else {

            logger.info("LIVE BOOKING : " + "ONLY PERMITTED EMAILS ARE ALLOWED");
            resultType = new ResultType(VendorErrorCode.PERMITTED_EMAIL_VALIDATED.getId(),
                    VendorErrorCode.PERMITTED_EMAIL_VALIDATED.getMsg());
        }

        return BookingTransformer.toMakeBooking(clsBookRS, makeBookingRQ.getBookingId(), resultType);

    }

    @Override
    public GetBookingQuestionsRS getBookingQuestions(String productId, String currencyCode) {

        logger.info("Get Booking Questions Request Initiated {} ");

        GetBookingQuestionsRS bookingQuestionsRS = new GetBookingQuestionsRS();
        ResultType resultType = new ResultType();
        Map<String, Object> logData = new HashMap<String, Object>();

        GetAlgoliaProductDetailsRS algoliaProductDetails = null;
        boolean isHotelPickUpEligible = false;

        try {

            /*
             * In here we will get the vendor specific product id and hash it to place pass specific vendor product code
             * to get product details from Algolia
             */

            logger.info("Generating PlacePass Specific Vendor Product Code using generateHash()");

            logger.info("Before Hashing the Vendor Specific Product Code : " + productId);
            String hashedProductId = productHashGenerator.generateHash(Vendor.CTYDSY.name() + productId);
            logger.info("After Hashing the Vendor Specific Product Code : " + hashedProductId);

            logData.put(CityDiscoveryLogger.VENDOR_METHOD.name(), "getProductDetails()");
            logger.info("Get Booking Questions Algolia Request Initiated {} ", logData);
            algoliaProductDetails = algoliaSearchClient.getProductDetails(hashedProductId);
            logger.info("Get Booking Questions Algolia Request Completed");

            isHotelPickUpEligible = algoliaProductDetails.getIsHotelPickUpEligible();

        } catch (Exception e) {
            logger.error("Get Booking Questions Algolia Request Error", e);
        }

        try {

            bookingQuestionsRS = getBookingQuestionsRS(isHotelPickUpEligible);

        } catch (Exception e) {
            logger.error("CityDiscovery Booking Questions Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            bookingQuestionsRS.setResultType(resultType);
        }
        logger.info("Returning Booking Questions Response");
        return bookingQuestionsRS;
    }

    @Override
    public BookingVoucherRS getVoucherDetails(BookingVoucherRQ bookingVoucherRQ) {

        Map<String, Object> logData = new HashMap<>();
        logData.put(CityDiscoveryLogger.BOOKING_REFERENCE.name(), bookingVoucherRQ.getReferenceNumber());
        logger.info("Get CityDiscovery Details Request Initiated {} ", logData);

        ResultType resultType = new ResultType();

        BookingVoucherRS bookingVoucherRS = null;
        ClsBookStatusRQ bookStatusRequest = null;
        ClsBookStatusRS bookStatusResponse = null;

        try {

            logger.info("CityDiscovery Voucher Details Request Initiated {} ", logData);
            bookStatusRequest = BookingTransformer.toVoucherDetailsRequest(bookingVoucherRQ);
            bookStatusResponse = restClient.getBookingStatus(bookStatusRequest);
            bookingVoucherRS = BookingTransformer.toVoucherDetails(bookStatusResponse);
            logger.info("CityDiscovery Voucher Details Request Completed");

        } catch (NotFoundException ne) {
            logger.error("CityDiscovery product option id not found", ne);
            resultType.setCode(VendorErrorCode.PRODUCT_OPTION_NOT_FOUND.getId());
            resultType.setMessage(VendorErrorCode.PRODUCT_OPTION_NOT_FOUND.getMsg());
            bookingVoucherRS.setResultType(resultType);

        } catch (HttpServerErrorException hse) {
            logger.error("CityDiscovery Voucher Details Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("CityDiscovery Voucher Details Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("CityDiscovery Voucher Details Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("CityDiscovery Voucher Details Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);
        }

        return bookingVoucherRS;
    }

    @Override
    public CancelBookingRS cancelBooking(CancelBookingRQ cancelBookingRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(CityDiscoveryLogger.BOOKING_REFERENCE.name(), cancelBookingRQ.getBookingReferenceId());
        logData.put(CityDiscoveryLogger.BOOKING_ID.name(), cancelBookingRQ.getBookingId());
        logData.put(CityDiscoveryLogger.BOOKING_STATUS.name(), cancelBookingRQ.getBookingStatus());
        logData.put(CityDiscoveryLogger.CANCELATION_TYPE.name(), cancelBookingRQ.getCancelationType());
        logger.info("Cancel Booking Request Initiated {} ", logData);

        ResultType resultType = new ResultType();

        CancelBookingRS cancelBookingRS = null;
        ClsCancelBookingRQ cancelBookRequest = null;
        ClsCancelBookingRS cancelBookResponse = null;

        try {

            logger.info("CityDiscovery Voucher Details Request Initiated {} ", logData);
            cancelBookRequest = BookingTransformer.toCancelBookingRequest(cancelBookingRQ);
            cancelBookResponse = restClient.cancelBooking(cancelBookRequest);
            cancelBookingRS = BookingTransformer.toCancelBooking(cancelBookResponse);
            logger.info("CityDiscovery Voucher Details Request Completed");

        } catch (NotFoundException ne) {
            logger.error("CityDiscovery product option id not found", ne);
            resultType.setCode(VendorErrorCode.PRODUCT_OPTION_NOT_FOUND.getId());
            resultType.setMessage(VendorErrorCode.PRODUCT_OPTION_NOT_FOUND.getMsg());
            // cancelBookingRS.setResultType(resultType);

        } catch (HttpServerErrorException hse) {
            logger.error("CityDiscovery Cancel Booking Details Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            // cancelBookingRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("CityDiscovery Cancel Booking Details Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            // cancelBookingRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("CityDiscovery Cancel Booking Details Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            // cancelBookingRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("CityDiscovery Cancel Booking Details Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            // cancelBookingRS.setResultType(resultType);
        }

        return cancelBookingRS;
    }

    @Override
    public BookingStatusRS getBookingStatus(BookingStatusRQ bookingStatusRQ) {

        Map<String, Object> logData = new HashMap<>();
        logData.put(CityDiscoveryLogger.BOOKING_REFERENCE.name(), bookingStatusRQ.getReferenceNumber());
        logger.info("CityDiscovery Booking Status Request Initiated {} ", logData);

        ResultType resultType = new ResultType();

        BookingStatusRS bookingStatusRS = new BookingStatusRS();
        ClsBookStatusRQ bookStatusRequest = null;
        ClsBookStatusRS bookStatusResponse = null;

        try {

            bookStatusRequest = BookingTransformer.toBookingStatusRequest(bookingStatusRQ);
            bookStatusResponse = restClient.getBookingStatus(bookStatusRequest);
            bookingStatusRS = BookingTransformer.toBookingStatus(bookStatusResponse);
            logger.info("CityDiscovery Booking Status Request Completed");

        } catch (NotFoundException ne) {

            logger.error("CityDiscovery product option id not found", ne);

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.PRODUCT_OPTION_NOT_FOUND.getId()));
            extendedAttributes.put("message", VendorErrorCode.PRODUCT_OPTION_NOT_FOUND.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());
            bookingStatusRS.setResultType(resultType);

        } catch (HttpServerErrorException hse) {

            logger.error("CityDiscovery Booking Status Request Error", hse);

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());
            bookingStatusRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {

            logger.error("CityDiscovery Booking Status Request Error", hce);

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());
            bookingStatusRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {

            logger.error("CityDiscovery Booking Status Request Error", rae);

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());
            bookingStatusRS.setResultType(resultType);

        } catch (Exception e) {

            logger.error("CityDiscovery Booking Status Request Error", e);

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_API_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_API_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());
            bookingStatusRS.setResultType(resultType);

        }

        bookingStatusRS.setReferenceNumber(bookingStatusRQ.getReferenceNumber());
        bookingStatusRS.setOldStatus(bookingStatusRQ.getStatus());

        return bookingStatusRS;

    }

    private GetBookingQuestionsRS getBookingQuestionsRS(boolean isHotelPickUpEligible) {

        GetBookingQuestionsRS bookingQuestionsRS = new GetBookingQuestionsRS();
        List<BookingQuestion> bookingQuestions = new ArrayList<BookingQuestion>();

        if (isHotelPickUpEligible) {

            BookingQuestion bookingQuestion1 = new BookingQuestion();
            bookingQuestion1.setMessage(VendorQuestions.VENDOR_QUESTION_HOTEL_ADDRESS.getQuestion());
            bookingQuestion1.setQuestionId(VendorQuestions.VENDOR_QUESTION_HOTEL_ADDRESS.getId());
            bookingQuestion1.setRequired(true);
            bookingQuestion1.setStringQuestionId(String.valueOf(VendorQuestions.VENDOR_QUESTION_HOTEL_ADDRESS.getId()));
            bookingQuestion1.setSubTitle("");
            bookingQuestion1.setTitle(VendorQuestions.VENDOR_QUESTION_HOTEL_ADDRESS.getTitle());

            bookingQuestions.add(bookingQuestion1);

            BookingQuestion bookingQuestion2 = new BookingQuestion();
            bookingQuestion2.setMessage(VendorQuestions.VENDOR_QUESTION_NAME_OF_HOTEL.getQuestion());
            bookingQuestion2.setQuestionId(VendorQuestions.VENDOR_QUESTION_NAME_OF_HOTEL.getId());
            bookingQuestion2.setRequired(true);
            bookingQuestion2.setStringQuestionId(String.valueOf(VendorQuestions.VENDOR_QUESTION_NAME_OF_HOTEL.getId()));
            bookingQuestion2.setSubTitle("");
            bookingQuestion2.setTitle(VendorQuestions.VENDOR_QUESTION_NAME_OF_HOTEL.getTitle());

            bookingQuestions.add(bookingQuestion2);

        }

        BookingQuestion bookingQuestion3 = new BookingQuestion();
        bookingQuestion3.setMessage(VendorQuestions.VENDOR_QUESTION_BOOKING_COMMENTS.getQuestion());
        bookingQuestion3.setQuestionId(VendorQuestions.VENDOR_QUESTION_BOOKING_COMMENTS.getId());
        bookingQuestion3.setRequired(true);
        bookingQuestion3.setStringQuestionId(String.valueOf(VendorQuestions.VENDOR_QUESTION_BOOKING_COMMENTS.getId()));
        bookingQuestion3.setSubTitle("");
        bookingQuestion3.setTitle(VendorQuestions.VENDOR_QUESTION_BOOKING_COMMENTS.getTitle());

        bookingQuestions.add(bookingQuestion3);

        ResultType resultType = new ResultType();
        resultType.setCode(0);
        resultType.setMessage("");

        bookingQuestionsRS.setBookingQuestions(bookingQuestions);
        bookingQuestionsRS.setResultType(resultType);

        return bookingQuestionsRS;
    }

}
