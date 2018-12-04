package com.gobe.connector.application.booking;

import com.gobe.connector.application.util.GobeLogger;
import com.gobe.connector.application.util.GobeUtil;
import com.gobe.connector.application.util.VendorErrorCode;
import com.gobe.connector.domain.gobe.Inventory.GobeInventoryCheckRS;
import com.gobe.connector.domain.gobe.book.*;
import com.gobe.connector.domain.gobe.price.GobePrice;
import com.gobe.connector.domain.gobe.price.GobePricesRSRepository;
import com.gobe.connector.domain.gobe.product.GobeProduct;
import com.gobe.connector.domain.gobe.product.GobeProductsRSRepository;
import com.placepass.connector.common.booking.*;
import com.placepass.connector.common.common.ResultType;
import com.gobe.connector.infrastructure.OAuth2RestClient;
import com.placepass.utils.pricematch.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created on 8/11/2017.
 */
@Service
public class BookingServiceImpl implements BookingService {

    private Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    OAuth2RestClient restClient;

    @Autowired
    GobePricesRSRepository gobePricesRSRepository;

    @Autowired
    GobeProductsRSRepository gobeProductsRSRepository;

    @Override
    public GetProductPriceRS getProductPrice(GetProductPriceRQ getProductPriceRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(GobeLogger.PRODUCT_ID.name(), getProductPriceRQ.getVendorProductId());
        logData.put(GobeLogger.PRODUCT_OPTION_ID.name(), getProductPriceRQ.getVendorProductOptionId());
        logData.put(GobeLogger.BOOKING_DATE.name(), getProductPriceRQ.getBookingDate());
        logger.info("Product Price Request Initiated {} ", logData);

        GetProductPriceRS getProductPriceRS = new GetProductPriceRS();
        ResultType resultType = new ResultType();
        try {

            // if VendorValidation is true, then we call the vendor connector price endpoint call
            if (getProductPriceRQ.isVendorValidation()) {
                ResultType productPriceResultType = validateProductAvailability(getProductPriceRQ);
                if (productPriceResultType.getCode() != VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId()) {
                    getProductPriceRS.setResultType(productPriceResultType);
                    return getProductPriceRS;
                }
            }

            List<PriceMatchPricePerAgeBand> inputPriceMatchList = BookingTransformer
                    .toPriceMatchPricePerAgeBandToPriceMatch(getProductPriceRQ.getPrices());
            List<PriceMatchQuantity> priceMatchQuantities = BookingTransformer
                    .toQuantitiesToPriceMatch(getProductPriceRQ.getQuantities());
            List<PriceMatchPriceBreakDown> priceMatchPriceBreakDowns = PriceMatch
                    .getFilteredPriceList(inputPriceMatchList, priceMatchQuantities);
            PriceMatchTotalPrice priceMatchTotalPrice = PriceMatch.getTotalForQuantities(priceMatchPriceBreakDowns);
            getProductPriceRS = BookingTransformer.toPricingResponse(priceMatchTotalPrice);

        } catch (Exception e) {
            logger.error("Gobe Product Price Request Error", e);
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

        /*
         * No Cart Id logData.put(GobeLogger.CART_ID.name(), makeBookingRQ.getCartId());
         */
        // logging out querry params
        if (makeBookingRQ != null && makeBookingRQ.getBookingOptions() != null
                && makeBookingRQ.getBookerDetails() != null) {
            for (BookingOption bookingOption : makeBookingRQ.getBookingOptions()) {
                logData.put(GobeLogger.PRODUCT_ID.name(), bookingOption.getVendorProductId());
                logData.put(GobeLogger.PRODUCT_OPTION_ID.name(), bookingOption.getVendorProductOptionId());
                logData.put(GobeLogger.BOOKING_DATE.name(), bookingOption.getBookingDate());
                logData.put(GobeLogger.BOOKING_ID.name(), makeBookingRQ.getBookingId());
            }
            logData.put(GobeLogger.BOOKER_EMAIL.name(), makeBookingRQ.getBookerDetails().getEmail());
            logger.info("Make Booking Request Initiated {} ", logData);
        }

        // Initialize the Response
        MakeBookingRS makeBookingRS = new MakeBookingRS();
        ResultType resultType = new ResultType();
        GobeOrderRS gobeOrderRS = null;
        GobeOrderStatusRS gobeOrderStatusRS = null;

        GobeProduct gobeProduct = null;
        List<GobePrice> gobePrices = null;

        // Getting product info and prices from Mongo
        if (makeBookingRQ.getBookingOptions() != null) {
            // going through the for loop however we only support 1 booking for now
            for (BookingOption bookingOption : makeBookingRQ.getBookingOptions()) {
                if (bookingOption.getVendorProductId() != null) {
                    try {
                        logger.info("Request product details from Mongo");
                        gobeProduct = gobeProductsRSRepository.findOne(bookingOption.getVendorProductId());
                        gobePrices = gobePricesRSRepository.findByTourIdRegex(bookingOption.getVendorProductId());
                        logger.info("Finished getting product details from Mongo");
                    } catch (DataAccessException ex) {
                        logger.error("Error getting product from MongoDB for Gobe Booking Request");
                        // Should create a new error for this

                        resultType.setCode(VendorErrorCode.FAILED.getId());
                        resultType.setMessage(VendorErrorCode.FAILED.getMsg());

                        Map<String, String> extendedAttributes = new HashMap<>();
                        extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_API_ERROR.getId()));
                        extendedAttributes.put("message", VendorErrorCode.VENDOR_API_ERROR.getMsg());
                        resultType.setExtendedAttributes(extendedAttributes);

                        makeBookingRS.setResultType(resultType);

                        return makeBookingRS;
                    }
                }
            }
        }

        try {
            if (gobeProduct == null || gobePrices == null) {
                logger.error("Product or price not found on Mongo");
                // Should create a new error and message for this
                resultType.setCode(VendorErrorCode.FAILED.getId());
                resultType.setMessage(VendorErrorCode.FAILED.getMsg());

                Map<String, String> extendedAttributes = new HashMap<>();
                extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_API_ERROR.getId()));
                extendedAttributes.put("message", VendorErrorCode.VENDOR_API_ERROR.getMsg());
                resultType.setExtendedAttributes(extendedAttributes);

                makeBookingRS.setResultType(resultType);

                return makeBookingRS;
            }
            GobeOrderRQ gobeOrderRQ = BookingTransformer.toBookingRequest(makeBookingRQ, gobeProduct, gobePrices);
            logData.put(GobeLogger.VENDOR_METHOD.name(), "makeBooking()");
            logger.info("Gobe Order Request Initiated {} ", logData);

            gobeOrderRS = restClient.makeBooking(gobeOrderRQ);

            logger.info("Gobe Order Request Completed");

            /*
             * if (gobeOrderRS != null && gobeOrderRS.getTtOrderStatus() != null &&
             * gobeOrderRS.getTtOrderStatus().toLowerCase().equals("processing") && gobeOrderRS.getTtOrderNumber() !=
             * null) { resultType.setCode(101); resultType.setMessage("PENDING"); //Booking seems to be good } //Every
             * other status is fail for now else { resultType.setCode(1);
             * resultType.setMessage("BOOKING: REQUEST FAILED"); makeBookingRS.setResultType(resultType); return
             * makeBookingRS; }
             */
        } catch (HttpServerErrorException hse) {
            logger.error("Gobe Booking Request Error", hse);

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            makeBookingRS.setResultType(resultType);
            return makeBookingRS;

        } catch (HttpClientErrorException hce) {
            logger.error("Gobe Booking Request Error", hce);

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            makeBookingRS.setResultType(resultType);
            return makeBookingRS;

        } catch (ResourceAccessException rae) {
            logger.error("Gobe Booking Request Error", rae);

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            makeBookingRS.setResultType(resultType);
            return makeBookingRS;

        } catch (Exception e) {
            logger.error("Gobe Booking Request Error", e);

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_API_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_API_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            makeBookingRS.setResultType(resultType);
            return makeBookingRS;
        }
        /*
         * //TODO check for what each status mean //Getting order status and voucher //Assuming if the Status fails. The
         * order will also fail if (resultType.getCode() == 0) { try { Map<String, Object> urlVariables = new
         * HashMap<String, Object>(); urlVariables.put("orderId", gobeOrderRS.getTtOrderNumber());
         * logData.put(GobeLogger.VENDOR_METHOD.name(), "getBookingStatus()");
         * 
         * logger.info("Gobe Order Status Request Initiated {} ", logData); //TODO make this faster
         * TimeUnit.SECONDS.sleep(6); gobeOrderStatusRS = restClient.getBookingStatus(urlVariables);
         * logger.info("Gobe Order Status Request Completed");
         * 
         * } catch (HttpServerErrorException hse) { logger.error("Gobe Order Status Request Error", hse);
         * resultType.setCode(1); resultType.setMessage( "BOOKING STATUS: " +
         * VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg()); makeBookingRS.setResultType(resultType); return
         * makeBookingRS; } catch (HttpClientErrorException hce) { logger.error("Gobe Order Status Request Error", hce);
         * resultType.setCode(1); resultType.setMessage( "BOOKING STATUS: " +
         * VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg()); makeBookingRS.setResultType(resultType); return
         * makeBookingRS; } catch (ResourceAccessException rae) { logger.error("Gobe Order Status Request Error", rae);
         * resultType.setCode(1); resultType.setMessage( "BOOKING STATUS: " +
         * VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg()); makeBookingRS.setResultType(resultType); return
         * makeBookingRS; } catch (Exception e) { logger.error("Gobe Order Status Request Error", e);
         * resultType.setCode(1); resultType.setMessage("BOOKING STATUS: " + VendorErrorCode.VENDOR_API_ERROR.getMsg());
         * makeBookingRS.setResultType(resultType); return makeBookingRS; } }
         */
        logger.info("Make Booking Request Completed");

        makeBookingRS = BookingTransformer.toBookingResponse(gobeOrderRS);

        logger.info("Returning Booking Response");
        return makeBookingRS;

    }

    @Override
    public GetBookingQuestionsRS getBookingQuestions(String productId, String currencyCode) {
        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(GobeLogger.PRODUCT_ID.name(), productId);
        logData.put(GobeLogger.CUR_CODE.name(), currencyCode);
        logger.info("Get Booking Questions Request Initiated {} ", logData);
        GobeProduct gobeProduct = null;
        ResultType resultType = new ResultType();
        try {
            logger.info("Request product details from Mongo");
            gobeProduct = gobeProductsRSRepository.findOne(productId);
            logger.info("Finished getting product details from Mongo");
        } catch (DataAccessException ex) {
            logger.error("Error getting product from MongoDB for Gobe Booking Question Request");
        }
        logger.info("Get Booking Questions Request Response");
        GetBookingQuestionsRS getBookingQuestionsRS = BookingTransformer.toBookingQuestions(gobeProduct);
        return getBookingQuestionsRS;
    }

    @Override
    public BookingVoucherRS getVoucherDetails(BookingVoucherRQ bookingVoucherRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(GobeLogger.BOOKING_REFERENCE.name(), bookingVoucherRQ.getReferenceNumber());
        logger.info("Get Voucher Details Request Initiated {} ", logData);

        BookingVoucherRS bookingVoucherRS = new BookingVoucherRS();
        ResultType resultType = new ResultType();
        GobeOrderStatusRS gobeOrderStatusRS = null;
        try {
            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("orderId", bookingVoucherRQ.getReferenceNumber());
            logData.put(GobeLogger.VENDOR_METHOD.name(), "getBookingStatus()");

            logger.info("Gobe Order Status Request Initiated {} ", logData);
            gobeOrderStatusRS = restClient.getBookingStatus(urlVariables);
            logger.info("Gobe Order Status Request Completed");
            bookingVoucherRS = BookingTransformer.toVoucherResponse(gobeOrderStatusRS);

        } catch (HttpServerErrorException hse) {
            logger.error("Gobe Voucher Details Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("Gobe Voucher Details Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("Gobe Voucher Details Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            bookingVoucherRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("Gobe Voucher Details Request Error", e);
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
        logData.put(GobeLogger.BOOKING_REFERENCE.name(), cancelBookingRQ.getBookingReferenceId());
        logData.put(GobeLogger.BOOKING_ID.name(), cancelBookingRQ.getBookingId());
        logData.put(GobeLogger.BOOKING_STATUS.name(), cancelBookingRQ.getBookingStatus());
        logData.put(GobeLogger.CANCELATION_TYPE.name(), cancelBookingRQ.getCancelationType());
        logger.info("Cancel Booking Request Initiated {} ", logData);

        CancelBookingRS cancelBookingRS = new CancelBookingRS();
        ResultType resultType = new ResultType();
        GobeCancelOrderRS gobeCancelOrderRS = new GobeCancelOrderRS();
        try {
            GobeCancelOrderRQ cancelBookingRequest = BookingTransformer.toCancelBookingRequest(cancelBookingRQ);

            logger.info("Gobe Cancel Booking Request Initiated {} ", logData);
            gobeCancelOrderRS = restClient.cancelBooking(cancelBookingRequest);
            logger.info("Gobe Cancel Booking Request Completed");

            cancelBookingRS = BookingTransformer.toCancelBookingResponse(gobeCancelOrderRS);

        } catch (HttpServerErrorException hse) {
            logger.error("Gobe Cancel Booking Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            cancelBookingRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("Gobe Cancel Booking Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            cancelBookingRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("Gobe Cancel Booking Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            cancelBookingRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("Gobe Cancel Booking Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            cancelBookingRS.setResultType(resultType);
        }
        return cancelBookingRS;
    }

    @Override
    public BookingStatusRS getBookingStatus(BookingStatusRQ bookingStatusRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(GobeLogger.BOOKING_REFERENCE.name(), bookingStatusRQ.getReferenceNumber());
        logData.put(GobeLogger.VENDOR_METHOD.name(), "getBookingStatus()");
        logger.info("Gobe Booking Status Request Initiated {} ", logData);

        BookingStatusRS bookingStatusRS = new BookingStatusRS();

        ResultType resultType = new ResultType();
        GobeOrderStatusRS gobeOrderStatusRS = null;

        try {

            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("orderId", bookingStatusRQ.getReferenceNumber());
            gobeOrderStatusRS = restClient.getBookingStatus(urlVariables);

            logger.info("Gobe Booking Status Request Completed");
            bookingStatusRS = BookingTransformer.toBookingStatusResponse(gobeOrderStatusRS);

        } catch (HttpServerErrorException hse) {

            logger.error("Gobe Booking Status Request Error", hse);

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            bookingStatusRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {

            logger.error("Gobe Booking Status Request Error", hce);

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            bookingStatusRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {

            logger.error("Gobe Booking Status Request Error", rae);

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            bookingStatusRS.setResultType(resultType);

        } catch (Exception e) {

            logger.error("Gobe Booking Status Request Error", e);

            resultType.setCode(VendorErrorCode.FAILED.getId());
            resultType.setMessage(VendorErrorCode.FAILED.getMsg());

            Map<String, String> extendedAttributes = new HashMap<>();
            extendedAttributes.put("code", String.valueOf(VendorErrorCode.VENDOR_API_ERROR.getId()));
            extendedAttributes.put("message", VendorErrorCode.VENDOR_API_ERROR.getMsg());
            resultType.setExtendedAttributes(extendedAttributes);

            bookingStatusRS.setResultType(resultType);

        }

        logger.info("Returning Booking Status Response");

        bookingStatusRS.setReferenceNumber(bookingStatusRQ.getReferenceNumber());
        bookingStatusRS.setOldStatus(bookingStatusRQ.getStatus());

        return bookingStatusRS;

    }

    private ResultType validateProductAvailability(GetProductPriceRQ getProductPriceRQ) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(GobeLogger.PRODUCT_ID.name(), getProductPriceRQ.getVendorProductId());
        logData.put(GobeLogger.PRODUCT_OPTION_ID.name(), getProductPriceRQ.getVendorProductOptionId());
        logData.put(GobeLogger.BOOKING_DATE.name(), getProductPriceRQ.getBookingDate());
        logger.info("Product Inventory Check Request Initiated {} ", logData);

        ResultType resultType = new ResultType();

        List<GobePrice> gobePrices = null;
        GobeProduct gobeProduct = null;

        try {
            logger.info("Request product details from Mongo");
            gobeProduct = gobeProductsRSRepository.findOne(getProductPriceRQ.getVendorProductId());
            gobePrices = gobePricesRSRepository.findByTourIdRegex(getProductPriceRQ.getVendorProductId());
            logger.info("Finished getting product details from Mongo");
        } catch (DataAccessException ex) {
            logger.error("Error getting product from MongoDB for Gobe Booking Request");
            // Should create a new error for this
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
        }

        if (gobePrices != null && !gobePrices.isEmpty() && gobeProduct != null) {
            if (GobeUtil.detectGobeAgeBandModel(gobeProduct)) {
                int totalQuantity = 0;
                for (Quantity quantity : getProductPriceRQ.getQuantities()) {
                    totalQuantity += quantity.getQuantity();
                }
                try {
                    String productOptionId = getProductPriceRQ.getVendorProductOptionId();
                    String startTime = productOptionId.substring(productOptionId.length() - 4, productOptionId.length() - 2) + ":" +
                            productOptionId.substring(productOptionId.length() - 2, productOptionId.length());
                    String tourDate = productOptionId.substring(productOptionId.length() - 12, productOptionId.length() - 8) + "-" +
                            productOptionId.substring(productOptionId.length() - 8, productOptionId.length() - 6) + "-" +
                            productOptionId.substring(productOptionId.length() - 6, productOptionId.length() - 4);

                    Map<String, Object> inventoryCheckUrlVariables = new HashMap<String, Object>();
                    inventoryCheckUrlVariables.put("productId", getProductPriceRQ.getVendorProductId());
                    inventoryCheckUrlVariables.put("startTime", startTime);
                    inventoryCheckUrlVariables.put("tourDate", tourDate);
                    inventoryCheckUrlVariables.put("quantity", totalQuantity);

                    logger.info("Gobe Inventory Check Request: {}", inventoryCheckUrlVariables);

                    GobeInventoryCheckRS gobeInventoryCheckRS = restClient
                            .checkInventory(inventoryCheckUrlVariables);

                    if (gobeInventoryCheckRS.getStatus() != null
                            && gobeInventoryCheckRS.getStatus().toLowerCase().equals("available")) {
                        resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
                        resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());
                    } else {
                        resultType.setCode(VendorErrorCode.INVENTORY_NOT_AVAILABLE.getId());
                        resultType.setMessage(VendorErrorCode.INVENTORY_NOT_AVAILABLE.getMsg());
                        return resultType;
                    }

                } catch (HttpServerErrorException hse) {
                    logger.error("Gobe Inventory Check Request Error", hse);
                    resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
                    resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
                } catch (HttpClientErrorException hce) {
                    logger.error("Gobe Inventory Check Request Error", hce);
                    resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
                    resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
                } catch (ResourceAccessException rae) {
                    logger.error("Gobe Inventory Check Request Error", rae);
                    resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
                    resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
                } catch (Exception e) {
                    logger.error("Gobe Inventory Check Request Error", e);
                    resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
                    resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
                }

            } else {
                logger.error("Product Not Follow AgeBand");
                resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
                resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            }
        }
        logger.info("Return Product Inventory Check Request");
        return resultType;
    }
}





