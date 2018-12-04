package com.placepass.connector.citydiscovery.application.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.placepass.connector.citydiscovery.application.exception.InvalidDateException;
import com.placepass.connector.citydiscovery.application.exception.ProductNotAvailableException;
import com.placepass.connector.citydiscovery.application.exception.ProductNotFoundException;
import com.placepass.connector.citydiscovery.application.util.CityDiscoveryLogger;
import com.placepass.connector.citydiscovery.application.util.CityDiscoveryUtil;
import com.placepass.connector.citydiscovery.application.util.VendorErrorCode;
import com.placepass.connector.citydiscovery.domain.citydiscovery.activity.ActivityAvailability;
import com.placepass.connector.citydiscovery.domain.citydiscovery.activity.ActivityAvailabilityService;
import com.placepass.connector.citydiscovery.domain.citydiscovery.activity.ActivityDisplay;
import com.placepass.connector.citydiscovery.domain.citydiscovery.activity.ActivityService;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsActivityCancellationPolicy;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsProductDetailsInfoRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsProductDetailsRQ;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsProductDetailsRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsReviewRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsReviewsRQ;
import com.placepass.connector.citydiscovery.infrastructure.RestClient;
import com.placepass.connector.citydiscovery.placepass.algolia.infrastructure.AlgoliaSearchClient;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.product.CancellationRules;
import com.placepass.connector.common.product.GetAvailabilityRQ;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetCancellationRulesRS;
import com.placepass.connector.common.product.GetProductDetailsRS;
import com.placepass.connector.common.product.GetProductOptionsRQ;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRQ;
import com.placepass.connector.common.product.GetProductReviewsRS;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.NotFoundException;
import com.placepass.utils.vendorproduct.ProductHashGenerator;
import com.placepass.utils.vendorproduct.Vendor;

@Service
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    RestClient restClient;

    @Autowired
    AlgoliaSearchClient algoliaSearchClient;

    @Autowired
    ActivityService activityService;

    @Autowired
    ActivityAvailabilityService activityAvailabilityService;

    @Autowired
    @Qualifier("ProductHashGenerator")
    private ProductHashGenerator productHashGenerator;

    @Override
    // TODO Need to fix this correctly after client verification
    public ClsProductDetailsInfoRS getProductDetails(String productId) {

        GetProductDetailsRS getProductDetailsRS = new GetProductDetailsRS();
        ResultType resultType = new ResultType();
        Map<String, Object> logData = new HashMap<String, Object>();

        ClsProductDetailsRS cityDiscoveryResponse = null;
        ClsProductDetailsRQ cityDiscoveryRequest = null;

        logData.put(CityDiscoveryLogger.PRODUCT_ID.name(), productId);
        logger.info("Get Product Details Request Initiated {} ", logData);

        try {
            logger.info("CityDiscovery Product Details Request Initiated {} ", logData);
            cityDiscoveryRequest = ProductTransformer.toProductDetailsRequest(productId);
            cityDiscoveryResponse = restClient.getProductDetails(cityDiscoveryRequest);
            logger.info("CityDiscovery Product Details Request Completed");
            getProductDetailsRS = ProductTransformer.toProductDetails(cityDiscoveryResponse);
        } catch (Exception e) {
            logger.error("CityDiscovery Product Details Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getProductDetailsRS.setResultType(resultType);
        }
        logger.info("Returning Product Details Response");
        return cityDiscoveryResponse.getProductDetailsInfoRS();
    }

    // OLD VERSION
    /*
     * @Override public GetAvailabilityRS getAvailability(GetAvailabilityRQ availabilityRQ) {
     * 
     * Map<String, Object> logData = new HashMap<>(); logData.put(CityDiscoveryLogger.PRODUCT_ID.name(),
     * availabilityRQ.getProductId()); logData.put(CityDiscoveryLogger.MONTH.name(), availabilityRQ.getMonth());
     * logData.put(CityDiscoveryLogger.YEAR.name(), availabilityRQ.getYear());
     * logger.info("Get Availability Request Initiated {} ", logData);
     * 
     * if (StringUtils.isEmpty(availabilityRQ.getMonth()) || StringUtils.isEmpty(availabilityRQ.getYear())) { throw new
     * BadRequestException(HttpStatus.BAD_REQUEST.name(), "Month and Year cannot be null"); }
     * 
     * String productId = availabilityRQ.getProductId(); String month = availabilityRQ.getMonth(); String year =
     * availabilityRQ.getYear();
     * 
     * GetAvailabilityRS availabilityRS = new GetAvailabilityRS(); ResultType resultType = new ResultType();
     * 
     * ActivityDisplay activityDisplay = null; List<Date> availableDates = null;
     * 
     * try {
     * 
     * logger.info("CityDiscovery availability Request Initiated {} ", logData); activityDisplay =
     * activityService.getActivityDisplay(Integer.parseInt(productId)); availableDates =
     * getProductAvailabilities(activityDisplay, productId, month, year); availabilityRS =
     * ProductTransformer.toAvailabilityDetails(availableDates); logger.info("CityDiscovery availability Completed");
     * 
     * } catch (NotFoundException ne) { logger.error("CityDiscovery product id not found", ne);
     * resultType.setCode(VendorErrorCode.PRODUCT_NOT_FOUND.getId());
     * resultType.setMessage(VendorErrorCode.PRODUCT_NOT_FOUND.getMsg()); availabilityRS.setResultType(resultType);
     * 
     * } catch (Exception e) { logger.error("CityDiscovery availability Request Error", e);
     * resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
     * resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg()); availabilityRS.setResultType(resultType);
     * 
     * }
     * 
     * logger.info("Returning Availability Response"); return availabilityRS; }
     */

    @Override
    public GetAvailabilityRS getAvailability(GetAvailabilityRQ availabilityRQ) {

        Map<String, Object> logData = new HashMap<>();
        logData.put(CityDiscoveryLogger.PRODUCT_ID.name(), availabilityRQ.getProductId());
        logData.put(CityDiscoveryLogger.MONTH.name(), availabilityRQ.getMonth());
        logData.put(CityDiscoveryLogger.YEAR.name(), availabilityRQ.getYear());
        logger.info("Get Availability Request Initiated {} ", logData);

        if (StringUtils.isEmpty(availabilityRQ.getMonth()) || StringUtils.isEmpty(availabilityRQ.getYear())) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.name(), "Month and Year cannot be null");
        }

        String dehashProductId = availabilityRQ.getProductId();
        String month = availabilityRQ.getMonth();
        String year = availabilityRQ.getYear();

        GetAvailabilityRS availabilityRS = new GetAvailabilityRS();
        ResultType resultType = new ResultType();

        ActivityAvailability activityAvailability = null;
        String productId = null;

        try {

            logger.info("CityDiscovery availability Request Initiated {} ", logData);
            productId = productHashGenerator.generateHash(Vendor.CTYDSY.name() + dehashProductId);
            activityAvailability = activityAvailabilityService.getActivityAvailability(productId);
            availabilityRS = ProductTransformer.toAvailabilityDetails(activityAvailability, month, year);
            logger.info("CityDiscovery availability Completed");

        } catch (NotFoundException ne) {
            logger.error("CityDiscovery product id not found", ne);
            resultType.setCode(VendorErrorCode.PRODUCT_NOT_FOUND.getId());
            resultType.setMessage(VendorErrorCode.PRODUCT_NOT_FOUND.getMsg());
            availabilityRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("CityDiscovery availability Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            availabilityRS.setResultType(resultType);

        }

        logger.info("Returning Availability Response");
        return availabilityRS;
    }

    @Override
    public GetProductOptionsRS getProductOptions(GetProductOptionsRQ productOptionsRQ) {

        String productId = productOptionsRQ.getProductId();
        String date = productOptionsRQ.getBookingDate();

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(CityDiscoveryLogger.PRODUCT_ID.name(), productId);
        logData.put(CityDiscoveryLogger.PRODUCT_OPTION_DATE.name(), date);
        logger.info("Get Product Options Request Initiated {} ", logData);

        GetProductOptionsRS productOptionsRS = new GetProductOptionsRS();
        ClsProductDetailsRQ cityDiscoveryRequest = null;
        ClsProductDetailsRS cityDiscoveryResponse = null;
        ResultType resultType = new ResultType();

        ActivityDisplay activityDisplay = null;


        try {

            logger.info("CityDiscovery product options Request Initiated {} ", logData);
            activityDisplay = activityService.getActivityDisplay(Integer.parseInt(productId));

            logger.info("CityDiscovery Product Details Request Initiated {} ", logData);
            cityDiscoveryRequest = ProductTransformer.toProductDetailsRequest(productId);

            logger.info("CityDiscovery Product Details Request Initiated");
            cityDiscoveryResponse = restClient.getProductDetails(cityDiscoveryRequest);
            logger.info("CityDiscovery Product Details Request Completed: {}", new Gson().toJson(cityDiscoveryResponse));

            productOptionsRS = ProductTransformer.toProductOptions(ProductTransformer.updateAcitivityWithLivePricing(activityDisplay, cityDiscoveryResponse), date);
            logger.info("CityDiscovery product options Completed");

        } catch (ProductNotAvailableException pnae) {
            logger.error("CityDiscovery product id not available", pnae);
            resultType.setCode(VendorErrorCode.PRODUCT_OPTIONS_NOT_FOUND_FOR_GIVEN_DATE.getId());
            resultType.setMessage(VendorErrorCode.PRODUCT_OPTIONS_NOT_FOUND_FOR_GIVEN_DATE.getMsg());
            productOptionsRS.setResultType(resultType);
            return productOptionsRS;
        }  catch (InvalidDateException ide) {
            logger.error("CityDiscovery Make a Booking Request Error", ide);
            resultType.setCode(VendorErrorCode.INVALID_MILITARY_TIME_FOUND.getId());
            resultType.setMessage(VendorErrorCode.INVALID_MILITARY_TIME_FOUND.getMsg());
            productOptionsRS.setResultType(resultType);
         } catch (ProductNotFoundException pnfe) {
            logger.error("CityDiscovery product id not found", pnfe);
            resultType.setCode(VendorErrorCode.PRODUCT_NOT_FOUND.getId());
            resultType.setMessage(VendorErrorCode.PRODUCT_NOT_FOUND.getMsg());
            productOptionsRS.setResultType(resultType);
            return productOptionsRS;
        } catch (Exception e) {
            logger.error("CityDiscovery Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            productOptionsRS.setResultType(resultType);
            return productOptionsRS;
        }

        logger.info("Returning Product Options Response");
        return productOptionsRS;
    }

    @Override
    public GetProductReviewsRS getProductReviews(GetProductReviewsRQ productReviewRQ) {

        String productId = productReviewRQ.getProductId();
        int hitsPerPage = productReviewRQ.getHitsPerPage();
        int pageNumber = productReviewRQ.getPageNumber();

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(CityDiscoveryLogger.PRODUCT_ID.name(), productId);
        logData.put(CityDiscoveryLogger.HITS_PER_PAGE.name(), hitsPerPage);
        logData.put(CityDiscoveryLogger.PAGE_NUMBER.name(), pageNumber);
        logger.info("Get Product Reviews Request Initiated {} ", logData);

        ClsReviewsRQ reviewsRequest = null;
        ClsReviewRS reviewsResponse = null;
        GetProductReviewsRS productReviewsRS = null;
        List<com.placepass.connector.common.product.Review> reviews = null;
        ResultType resultType = new ResultType();

        try {
            logger.info("CityDiscovery Product Reviews Request Initiated {} ", logData);
            reviewsRequest = ProductTransformer.toProductReviewsRequest(productId);
            reviewsResponse = restClient.getProductReviews(reviewsRequest);
            productReviewsRS = ProductTransformer.toReviews(reviewsResponse, productId);
            logger.info("CityDiscovery Product Reviews Completed");

            reviews = productReviewsRS.getReviews();
            reviews = CityDiscoveryUtil.paginate(pageNumber, hitsPerPage, reviews);
            productReviewsRS.setReviews(reviews);

        } catch (HttpServerErrorException hse) {
            logger.error("CityDiscovery Review Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            productReviewsRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("CityDiscovery Review Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            productReviewsRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("CityDiscovery Review Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            productReviewsRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("CityDiscovery Review Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            productReviewsRS.setResultType(resultType);
        }

        logger.info("Returning Product Reviews Response");
        return productReviewsRS;
    }

    // OLD Version
    /*
     * private List<Date> getProductAvailabilities(ActivityDisplay activityDisplay, String productid, String month,
     * String year) {
     * 
     * Date priceDateBegins = null; Date priceDateEnds = null; List<String> blockoutDates = null; List<Integer>
     * activityPriceDays = null; List<Date> availableDates = null;
     * 
     * // We are getting first price object for showing availability ActivityPrice activityPrice =
     * activityDisplay.getActivityPrices().get(0);
     * 
     * priceDateBegins = activityPrice.getActivityPriceDateBegins(); priceDateEnds =
     * activityPrice.getActivityPriceDateEnds(); blockoutDates =
     * DateUtil.getBlackOutDates(activityDisplay.getActivityBlockOutdates()); activityPriceDays =
     * DateUtil.getAvailableWeekdays(activityPrice.getActivityPriceDays()); availableDates =
     * DateUtil.getAvailableDates((Integer.parseInt(month) - 1), Integer.parseInt(year), priceDateBegins, priceDateEnds,
     * activityPriceDays, blockoutDates);
     * 
     * return availableDates; }
     */

    @Override
    // TODO Need to fix this correctly after client verification
    public GetCancellationRulesRS getProductCancellationRules(String productId) {

        GetCancellationRulesRS getCancellationRulesRS = new GetCancellationRulesRS();
        ResultType resultType = new ResultType();
        Map<String, Object> logData = new HashMap<String, Object>();

        ClsProductDetailsRS cityDiscoveryResponse = null;
        ClsProductDetailsRQ cityDiscoveryRequest = null;

        logData.put(CityDiscoveryLogger.PRODUCT_ID.name(), productId);
        logger.info("Get Cancellation Rules Request Initiated {} ", logData);

        try {
            logger.info("CityDiscovery Cancellation Rules Request Initiated {} ", logData);
            cityDiscoveryRequest = ProductTransformer.toProductDetailsRequest(productId);
            cityDiscoveryResponse = restClient.getProductDetails(cityDiscoveryRequest);
            logger.info("CityDiscovery Cancellation Rules Request Completed");
            List<ClsActivityCancellationPolicy> activityCancellationPolicies = cityDiscoveryResponse
                    .getProductDetailsInfoRS().getTourInformationItems().getTour().getActivityCancellation()
                    .getActivityCancellationPolicy();
            CancellationRules cancellationRules = CityDiscoveryUtil
                    .createCancellationRules(activityCancellationPolicies);
            getCancellationRulesRS.setCancellationRules(cancellationRules);

        } catch (Exception e) {
            logger.error("CityDiscovery Cancellation Rules Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getCancellationRulesRS.setResultType(resultType);
        }

        logger.info("Returning Cancellation Rules Response");
        return getCancellationRulesRS;
    }

}