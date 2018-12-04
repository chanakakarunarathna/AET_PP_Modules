package com.viator.connector.application.booking;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.viator.connector.domain.viator.availability.ViatorAvailabilityRequest;
import com.viator.connector.domain.viator.availability.ViatorAvailabilityResInfo;
import com.viator.connector.domain.viator.availability.ViatorAvailabilityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.placepass.connector.common.booking.BookingStatusRQ;
import com.placepass.connector.common.booking.BookingStatusRS;
import com.placepass.connector.common.booking.BookingVoucherRQ;
import com.placepass.connector.common.booking.BookingVoucherRS;
import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.CancelBookingRS;
import com.placepass.connector.common.booking.GetBookingQuestionsRQ;
import com.placepass.connector.common.booking.GetBookingQuestionsRS;
import com.placepass.connector.common.booking.GetProductPriceRQ;
import com.placepass.connector.common.booking.GetProductPriceRS;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRS;
import com.placepass.connector.common.booking.VendorErrorCode;
import com.placepass.connector.common.common.ResultType;
import com.placepass.utils.pricematch.PriceMatch;
import com.placepass.utils.pricematch.PriceMatchPriceBreakDown;
import com.placepass.utils.pricematch.PriceMatchPricePerAgeBand;
import com.placepass.utils.pricematch.PriceMatchQuantity;
import com.placepass.utils.pricematch.PriceMatchTotalPrice;
import com.placepass.utils.vendorproduct.ProductHashGenerator;
import com.viator.connector.application.common.VendorStatusStrategy;
import com.viator.connector.application.util.ViatorLogger;
import com.viator.connector.domain.viator.book.ViatorBookRequest;
import com.viator.connector.domain.viator.book.ViatorBookResponse;
import com.viator.connector.domain.viator.calculateprice.ViatorCalculatepriceRequest;
import com.viator.connector.domain.viator.calculateprice.ViatorCalculatepriceResponse;
import com.viator.connector.domain.viator.cancel.ViatorCancelBookingRequest;
import com.viator.connector.domain.viator.cancel.ViatorCancelBookingResponse;
import com.viator.connector.domain.viator.mybookings.ViatorMybookingsResponse;
import com.viator.connector.domain.viator.product.ViatorProductResponse;
import com.viator.connector.infrastructure.RestClient;
import com.viator.connector.placepass.algolia.infrastructure.AlgoliaSearchClient;

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

    @Value("${viator.setDemo}")
    private boolean setDemo;

    @Autowired
    private VendorStatusStrategy statusStrategy;

    @Override
    public GetProductPriceRS getProductPrice(GetProductPriceRQ getProductPriceRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(ViatorLogger.PRODUCT_ID.name(), getProductPriceRQ.getVendorProductId());
        logData.put(ViatorLogger.PRODUCT_OPTION_ID.name(), getProductPriceRQ.getVendorProductOptionId());
        logData.put(ViatorLogger.BOOKING_DATE.name(), getProductPriceRQ.getBookingDate());
        logData.put(ViatorLogger.PRICE_VALIDATION.name(), getProductPriceRQ.isVendorValidation());
        logger.info("Product Price Request Initiated {} ", logData);


        GetProductPriceRS getProductPriceRS = new GetProductPriceRS();
        ResultType resultType = new ResultType();
        try {

            logger.info("ViatorCalculatepriceRequest Initiated {} ", logData);

            List<PriceMatchPricePerAgeBand> inputPriceMatchList = BookingTransformer
                    .toPriceMatchPricePerAgeBandToPriceMatch(getProductPriceRQ.getPrices());

            List<PriceMatchQuantity> priceMatchQuantities = BookingTransformer
                    .toQuantitiesToPriceMatch(getProductPriceRQ.getQuantities());

            List<PriceMatchPriceBreakDown> priceMatchPriceBreakDowns = PriceMatch
                    .getFilteredPriceList(inputPriceMatchList, priceMatchQuantities);

            PriceMatchTotalPrice priceMatchTotalPrice = PriceMatch.getTotalForQuantities(priceMatchPriceBreakDowns);

            // if VendorValidation is true, then we call the vendor connector price endpoint call
            if (getProductPriceRQ.isVendorValidation()) {
                ResultType productPriceResultType = validateProductAvailability(getProductPriceRQ, priceMatchTotalPrice.getFinalTotal());
                if (productPriceResultType.getCode() != VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId()) {
                    getProductPriceRS.setResultType(productPriceResultType);
                    return getProductPriceRS;
                }
            }

            getProductPriceRS = BookingTransformer.toPricingResponse(priceMatchTotalPrice);

            logger.info("ViatorCalculatepriceRequest Completed");

        } catch (Exception e) {
            logger.error("Viator Product Price Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getProductPriceRS.setResultType(resultType);
        }
        logger.info("Returning Product Price Response");
        return getProductPriceRS;
    }

    @Override
    public MakeBookingRS makeBooking(MakeBookingRQ makeBookingRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(ViatorLogger.BOOKING_ID.name(), makeBookingRQ.getBookingId());
        logData.put(ViatorLogger.PRODUCT_ID.name(), makeBookingRQ.getBookingOptions().get(0).getVendorProductId());
        logData.put(ViatorLogger.PRODUCT_OPTION_ID.name(),
                makeBookingRQ.getBookingOptions().get(0).getVendorProductOptionId());
        logData.put(ViatorLogger.BOOKING_DATE.name(), makeBookingRQ.getBookingOptions().get(0).getBookingDate());
        logData.put(ViatorLogger.BOOKER_EMAIL.name(), makeBookingRQ.getBookerDetails().getEmail());
        logger.info("Make Booking Request Initiated {} ", logData);

        // MakeBookingRS makeBookingRS = new MakeBookingRS();
        ResultType resultType = null;
        ViatorBookResponse viatorBookResponse = null;

        try {
            ViatorBookRequest viatorBookingRequest = BookingTransformer.toBookingRequest(makeBookingRQ, setDemo);
            logData.put(ViatorLogger.VENDOR_METHOD.name(), "createBooking()");
            logger.info("ViatorBookRequest Initiated {} ", logData);

            viatorBookResponse = restClient.createBooking(viatorBookingRequest);
            resultType = statusStrategy.getResultType(viatorBookResponse);

            logger.info("ViatorBookRequest Completed");

        } catch (HttpServerErrorException hse) {
            logger.error("Viator Booking Request Error", hse);

            resultType = new ResultType(VendorErrorCode.FAILED.getId(), VendorErrorCode.FAILED.getMsg());
            resultType.getExtendedAttributes().put("code",
                    String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
            resultType.getExtendedAttributes().put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());

        } catch (HttpClientErrorException hce) {
            logger.error("Viator Booking Request Error", hce);

            resultType = new ResultType(VendorErrorCode.FAILED.getId(), VendorErrorCode.FAILED.getMsg());
            resultType.getExtendedAttributes().put("code",
                    String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
            resultType.getExtendedAttributes().put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());

        } catch (ResourceAccessException rae) {
            logger.error("Viator Booking Request Error", rae);

            resultType = new ResultType(VendorErrorCode.FAILED.getId(), VendorErrorCode.FAILED.getMsg());
            resultType.getExtendedAttributes().put("code",
                    String.valueOf(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId()));
            resultType.getExtendedAttributes().put("message", VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());

        } catch (Exception e) {
            logger.error("Viator Booking Request Error", e);

            resultType = new ResultType(VendorErrorCode.FAILED.getId(), VendorErrorCode.FAILED.getMsg());
            resultType.getExtendedAttributes().put("code", String.valueOf(VendorErrorCode.VENDOR_API_ERROR.getId()));
            resultType.getExtendedAttributes().put("message", VendorErrorCode.VENDOR_API_ERROR.getMsg());
        }

        ViatorMybookingsResponse viatorMybookingsResponse = null;
        if (resultType.getCode() == VendorErrorCode.CONFIRMED.getId() && viatorBookResponse.getData().getHasVoucher()) {
            viatorMybookingsResponse = this.retrieveVoucherDetails(logData, resultType, viatorBookResponse);
        }

        logger.info("Returning Booking Response");

        return BookingTransformer.toBookingResponse(viatorBookResponse, viatorMybookingsResponse, resultType);
    }

    /**
     * @param logData
     * @param resultType
     * @param viatorBookResponse
     * @return
     */
    private ViatorMybookingsResponse retrieveVoucherDetails(Map<String, Object> logData, ResultType resultType,
            ViatorBookResponse viatorBookResponse) {
        ViatorMybookingsResponse viatorMybookingsResponse = null;
        try {
            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("voucherKey", viatorBookResponse.getData().getVoucherKey());
            logData.put(ViatorLogger.VENDOR_METHOD.name(), "getVoucherDetails()");
            logger.info("Viator Booking Voucher Request Initiated {} ", logData);

            viatorMybookingsResponse = restClient.getBookingStatus(urlVariables);

        } catch (HttpServerErrorException hse) {
            logger.error("Viator Voucher Request Error", hse);
            resultType.setMessage("BOOKING: CONFIRMED. VOUCHER: " + VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());

        } catch (HttpClientErrorException hce) {
            logger.error("Viator Voucher Request Error", hce);
            resultType.setMessage("BOOKING: CONFIRMED. VOUCHER: " + VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());

        } catch (ResourceAccessException rae) {
            logger.error("Viator Voucher Request Error", rae);
            resultType.setMessage(
                    "BOOKING: CONFIRMED. VOUCHER: " + VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());

        } catch (Exception e) {
            logger.error("Viator Voucher Request Error", e);
            resultType.setMessage("BOOKING: CONFIRMED. VOUCHER: " + VendorErrorCode.VENDOR_API_ERROR.getMsg());
        }
        logger.info("Viator Booking Voucher Request Completed");
        return viatorMybookingsResponse;
    }

    @Override
    public GetBookingQuestionsRS getBookingQuestions(GetBookingQuestionsRQ bookingQuestionsRQ) {
        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(ViatorLogger.PRODUCT_ID.name(), bookingQuestionsRQ.getProductId());
        logData.put(ViatorLogger.CUR_CODE.name(), bookingQuestionsRQ.getCurrencyCode());
        logger.info("Get Booking Questions Request Initiated {} ", logData);

        Boolean excludeTourGrade = false;
        Boolean showUnavailable = false;

        GetBookingQuestionsRS getBookingQuestionsRS = new GetBookingQuestionsRS();
        ResultType resultType = new ResultType();
        ViatorProductResponse viatorProductResponse = null;

        try {
            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("code", bookingQuestionsRQ.getProductId());
            urlVariables.put("currencyCode", bookingQuestionsRQ.getCurrencyCode());
            urlVariables.put("excludeTourGradeAvailability", excludeTourGrade);
            urlVariables.put("showUnavailable", showUnavailable);

            logger.info("Viator Product Details Request Initiated {} ", logData);
            viatorProductResponse = restClient.getProductDetails(urlVariables);
            logger.info("Viator Product Details Request Completed");

            getBookingQuestionsRS = BookingTransformer.toBookingQuestions(viatorProductResponse);

        } catch (HttpServerErrorException hse) {
            logger.error("Viator Booking Questions Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getBookingQuestionsRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("Viator Booking Questions Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getBookingQuestionsRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("Viator Booking Questions Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            getBookingQuestionsRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("Viator Booking Questions Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getBookingQuestionsRS.setResultType(resultType);
        }
        logger.info("Returning Booking Questions Response");
        return getBookingQuestionsRS;
    }

    @Override
    public BookingVoucherRS getVoucherDetails(BookingVoucherRQ bookingVoucherRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(ViatorLogger.BOOKING_REFERENCE.name(), bookingVoucherRQ.getReferenceNumber());
        logger.info("Get Voucher Details Request Initiated {} ", logData);

        BookingVoucherRS bookingVoucherRS = new BookingVoucherRS();
        ResultType resultType = new ResultType();
        ViatorMybookingsResponse viatorBookingVoucherRS = null;
        try {

            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("itineraryOrItemId", bookingVoucherRQ.getReferenceNumber());
            urlVariables.put("email", bookingVoucherRQ.getBookerEmail());

            logger.info("Viator Voucher Details Request Initiated {} ", logData);
            viatorBookingVoucherRS = restClient.getBookingStatus(urlVariables);
            logger.info("Viator Voucher Details Request Completed");
            bookingVoucherRS = BookingTransformer.toVoucherResponse(viatorBookingVoucherRS);

        } catch (HttpServerErrorException hse) {
            logger.error("Viator Voucher Details Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("Viator Voucher Details Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("Viator Voucher Details Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("Viator Voucher Details Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);
        }
        logger.info("Returning Voucher Details Response");
        return bookingVoucherRS;
    }

    @Override
    public CancelBookingRS cancelBooking(CancelBookingRQ cancelBookingRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(ViatorLogger.BOOKING_REFERENCE.name(), cancelBookingRQ.getBookingReferenceId());
        logData.put(ViatorLogger.BOOKING_ID.name(), cancelBookingRQ.getBookingId());
        logData.put(ViatorLogger.BOOKING_STATUS.name(), cancelBookingRQ.getBookingStatus());
        logData.put(ViatorLogger.CANCELATION_TYPE.name(), cancelBookingRQ.getCancelationType());
        logger.info("Cancel Booking Request Initiated {} ", logData);

        CancelBookingRS cancelBookingRS = new CancelBookingRS();
        ResultType resultType = new ResultType();
        ViatorCancelBookingResponse viatorCancelBookingResponse = new ViatorCancelBookingResponse();
        try {
            ViatorCancelBookingRequest cancelBookingRequest = BookingTransformer
                    .toCancelBookingRequest(cancelBookingRQ);

            logger.info("Viator Cancel Booking Request Initiated {} ", logData);
            viatorCancelBookingResponse = restClient.getCancelBookingDetails(cancelBookingRequest);
            logger.info("Viator Cancel Booking Request Completed");

            cancelBookingRS = BookingTransformer.toViatorCancelBookingResponse(viatorCancelBookingResponse);

        } catch (HttpServerErrorException hse) {
            logger.error("Viator Cancel Booking Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            cancelBookingRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("Viator Cancel Booking Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            cancelBookingRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("Viator Cancel Booking Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            cancelBookingRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("Viator Cancel Booking Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            cancelBookingRS.setResultType(resultType);
        }
        return cancelBookingRS;
    }

	@Override
	public BookingStatusRS getBookingStatus(BookingStatusRQ bookingStatusRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(ViatorLogger.BOOKING_REFERENCE.name(), bookingStatusRQ.getReferenceNumber());
        logger.info("Booking Status Request Initiated {} ", logData);

        BookingStatusRS bookingStatusRS = new BookingStatusRS();

        ResultType resultType = new ResultType();
        ViatorMybookingsResponse viatorMybookingsRS = null;
        try {

            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("itineraryOrItemId", bookingStatusRQ.getReferenceNumber());
            urlVariables.put("email", bookingStatusRQ.getBookerEmail());

            viatorMybookingsRS = restClient.getBookingStatus(urlVariables);
            logger.info("Viator Booking Status Request Completed");
            bookingStatusRS = BookingTransformer.toBookingStatusResponse(viatorMybookingsRS);

        } catch (HttpServerErrorException hse) {
            logger.error("Viator Booking Status Request Error", hse);
            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            bookingStatusRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("Viator Booking Status Request Error", hce);
            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            bookingStatusRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("Viator Booking Status Request Error", rae);
            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            bookingStatusRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("Viator Booking Status Request Error", e);
            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_API_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_API_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            bookingStatusRS.setResultType(resultType);
        }
        bookingStatusRS.setReferenceNumber(bookingStatusRQ.getReferenceNumber());
		bookingStatusRS.setOldStatus(bookingStatusRQ.getStatus());
        logger.info("Returning Booking Status Response");
        return bookingStatusRS;

	}

    private ResultType validateProductAvailability(GetProductPriceRQ getProductPriceRQ, Float calculatedTotal) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(ViatorLogger.PRODUCT_ID.name(), getProductPriceRQ.getVendorProductId());
        logData.put(ViatorLogger.PRODUCT_OPTION_ID.name(), getProductPriceRQ.getVendorProductOptionId());
        logData.put(ViatorLogger.BOOKING_DATE.name(), getProductPriceRQ.getBookingDate());
        logger.info("Product Price Availability Request Initiated {} ", logData);

        ResultType resultType = new ResultType();
        ViatorCalculatepriceResponse viatorPriceDetails = null;
        ViatorAvailabilityResponse viatorAvailabilityResponse = null;

        try {
            ViatorCalculatepriceRequest viatorPricingRequest = BookingTransformer.toPricingRequest(getProductPriceRQ);
            logger.info("ViatorCalculatepriceRequest Initiated {} ", logData);
            viatorPriceDetails = restClient.createCalculatePricing(viatorPricingRequest);
            logger.info("ViatorCalculatepriceRequest Completed");
            resultType = BookingTransformer.toValidateProductResponse(viatorPriceDetails);

            if (resultType.getCode() == VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId()) {
                ViatorAvailabilityRequest viatorAvailabilityRequest = BookingTransformer.toAvailabilityRequest(getProductPriceRQ);
                logger.info("ViatorAvailableScheduleRequest Initiated {} ", logData);
                viatorAvailabilityResponse = restClient.getAvailableSchedule(viatorAvailabilityRequest);
                logger.info("ViatorAvailableScheduleRequest Completed");
                String bookingDate = getProductPriceRQ.getBookingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                resultType = BookingTransformer.toValidateAvailabilityResponse(viatorAvailabilityResponse, bookingDate);
            }

            //validating Viator Flat Rate issue
            if (viatorPriceDetails.getData().getItineraryNewPrice() > calculatedTotal) {
                logger.error("Viator product has unexpected total pricing calculation rules: {}", getProductPriceRQ.getVendorProductId());
                resultType.setCode(VendorErrorCode.PRODUCT_NOT_FOUND.getId());
                resultType.setMessage(VendorErrorCode.PRODUCT_NOT_FOUND.getMsg());
            }



        } catch (HttpServerErrorException hse) {
            logger.error("Viator Product Price Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());

        } catch (HttpClientErrorException hce) {
            logger.error("Viator Product Price Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());

        } catch (ResourceAccessException rae) {
            logger.error("Viator Product Price Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());

        } catch (Exception e) {
            logger.error("Viator Product Price Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
        }
        logger.info("Returning Product Price Availability Response");
        return resultType;
    }

}
