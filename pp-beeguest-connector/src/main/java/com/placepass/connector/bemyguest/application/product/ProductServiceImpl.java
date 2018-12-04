package com.placepass.connector.bemyguest.application.product;

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

import com.placepass.connector.bemyguest.application.util.BeMyGuestLogger;
import com.placepass.connector.bemyguest.application.util.VendorErrorCode;
import com.placepass.connector.bemyguest.domain.bemyguest.availability.BmgProductAvailability;
import com.placepass.connector.bemyguest.domain.bemyguest.availability.BmgProductAvailabilityService;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgCancellationPolicies;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgProductDetail;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgProductDetailService;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgProductDetailsRS;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.product.CancellationRules;
import com.placepass.connector.common.product.GetAvailabilityRQ;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetCancellationRulesRS;
import com.placepass.connector.common.product.GetProductDetailsRS;
import com.placepass.connector.common.product.GetProductOptionsRQ;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRS;
import com.placepass.connector.bemyguest.infrastructure.RestClient;
import com.placepass.connector.bemyguest.placepass.algolia.infrastructure.AlgoliaSearchClient;
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
    @Qualifier("ProductHashGenerator")
    private ProductHashGenerator productHashGenerator;

    @Autowired
    private BmgProductDetailService bmgProductDetailService;

    @Autowired
    private BmgProductAvailabilityService bmgProductAvailabilityService;

    @Override
    public GetProductDetailsRS getProductDetails(String productId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logger.info("Get Product Details Request Initiated {} ", logData);
        Map<String,Object> urlVariables;
        GetProductDetailsRS getProductDetailsRS = new GetProductDetailsRS();
        ResultType resultType = new ResultType();
        BmgProductDetailsRS beMyGuestResponse = null;
        try {
            logger.info("BeMyGuest Product Details Request Initiated {} ", logData);
            urlVariables = ProductTransformer.toProductDetailsRequestParamMap(productId);
            beMyGuestResponse = restClient.getProductDetails(urlVariables);
            logger.info("BeMyGuest Product Details Request Completed");
            getProductDetailsRS = ProductTransformer.toProductDetails(beMyGuestResponse);
        } catch (HttpServerErrorException hse) {
            logger.error("BeMyGuest Product Details Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getProductDetailsRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("BeMyGuest Product Details Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getProductDetailsRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("BeMyGuest Product Details Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            getProductDetailsRS.setResultType(resultType);
        } catch (Exception e) {
            logger.error("BeMyGuest Product Details Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getProductDetailsRS.setResultType(resultType);
        }
        logger.info("Returning Product Details Response");
        return getProductDetailsRS;
    }

    /*
     * @Override public GetAvailabilityRS getAvailability(String productId, String month, String year) { Map<String,
     * Object> logData = new HashMap<String, Object>();
     * //logger.info(algoliaSearchClient.getProductDetails(productId).toString(), logData);
     * logger.info("Get Product Details Request Initiated {} ", logData); BeMyGuestProductDetailsRQ
     * beMyGuestProductDetailsRequest = null; GetAvailabilityRS getAvailabilityRS = new GetAvailabilityRS(); ResultType
     * resultType = new ResultType(); BeMyGuestProductDetailsRS beMyGuestResponse = null;
     *
     * try { logger.info("BeMyGuest Product Details Request Initiated {} ", logData); beMyGuestProductDetailsRequest =
     * ProductTransformer.toProductDetailsRequest(productId); beMyGuestResponse =
     * restClient.getProductDetails(beMyGuestProductDetailsRequest);
     * logger.info("BeMyGuest Product Details Request Completed"); getAvailabilityRS =
     * ProductTransformer.toAvailability(beMyGuestResponse); } catch (Exception e) {
     * logger.error("BeMyGuest Product Details Request Error", e);
     * resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
     * resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg()); getAvailabilityRS.setResultType(resultType); }
     *
     * logger.info("Returning Product Details Response"); return getAvailabilityRS;
     *
     * }
     */

    @Override
    public GetAvailabilityRS getAvailability(GetAvailabilityRQ rq) {

        String encodedProductId = null;
        String productId = rq.getProductId();
        String month = rq.getMonth();
        String year = rq.getYear();

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(BeMyGuestLogger.PRODUCT_ID.name(), productId);
        logData.put(BeMyGuestLogger.MONTH.name(), month);
        logData.put(BeMyGuestLogger.YEAR.name(), year);
        logger.info("Get Availability Request Initiated {} ", logData);

        GetAvailabilityRS getAvailabilityRS = new GetAvailabilityRS();
        ResultType resultType = new ResultType();

        try {
            logger.info("BeMyGuest Availability Request Initiated {} ", logData);

            encodedProductId = productHashGenerator.generateHash(Vendor.BEMYGT + productId);
            BmgProductAvailability bmgProductAvailability = bmgProductAvailabilityService
                    .getBmgProductAvailability(encodedProductId);

            logger.info("BeMyGuest Availability Completed");
            getAvailabilityRS = ProductTransformer.toGetAvailabilityRS(bmgProductAvailability, month, year);
        } catch (Exception e) {
            logger.error("BeMyGuest Availability Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getAvailabilityRS.setResultType(resultType);
        }

        logger.info("Returning Availabilitys Response");

        return getAvailabilityRS;
    }

    /*
     * @Override public GetAvailabilityRS getAvailability(GetAvailabilityRQ rq) {
     *
     * Map<String, Object> logData = new HashMap<String, Object>(); logData.put(BeMyGuestLogger.PRODUCT_ID.name(),
     * rq.getProductId()); logData.put(BeMyGuestLogger.MONTH.name(), rq.getMonth());
     * logData.put(BeMyGuestLogger.YEAR.name(), rq.getYear()); logger.info("Get Availability Request Initiated {} ",
     * logData);
     *
     * if (StringUtils.isEmpty(rq.getMonth()) || StringUtils.isEmpty(rq.getYear())) { throw new
     * BadRequestException(HttpStatus.BAD_REQUEST.name(), "month and year cannot be null"); }
     *
     * ResultType resultType = new ResultType(); GetAvailabilityRS getAvailabilityRS = new GetAvailabilityRS();
     *
     * try { logger.info("BeMyGuest availability Request Initiated {} ", logData); BmgProductDetail bmgProductDetail =
     * bmgProductDetailService.getBmgDetails(rq.getProductId()); logger.info("BeMyGuest availability Completed");
     * getAvailabilityRS = ProductTransformer.toGetAvailabilityRS(bmgProductDetail, rq.getMonth(), rq.getYear()); }
     * catch (Exception e) { logger.error("BeMyGuest availability Request Error", e);
     * resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
     * resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg()); getAvailabilityRS.setResultType(resultType); }
     *
     * logger.info("Returning Availability Response"); return getAvailabilityRS; }
     */

    @Override
    public GetProductOptionsRS getProductOptions(GetProductOptionsRQ productOptionsRQ) {
        String productId = productOptionsRQ.getProductId();
        String date = productOptionsRQ.getBookingDate();

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(BeMyGuestLogger.PRODUCT_ID.name(), productId);
        logData.put(BeMyGuestLogger.PRODUCT_OPTION_DATE.name(), date);
        logger.info("Get Product Options Request Initiated {} ", logData);

        GetProductOptionsRS getProductOptionsRS = new GetProductOptionsRS();
        ResultType resultType = new ResultType();

        try {
            logger.info("BeMyGuest product options Request Initiated {} ", logData);
            BmgProductDetail bmgProductDetail = bmgProductDetailService.getBmgDetails(productId);
            logger.info("BeMyGuest product options Completed");
            getProductOptionsRS = ProductTransformer.toProductOptions(bmgProductDetail, date);
        } catch (Exception e) {
            logger.error("BeMyGuest product options Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);
        }

        logger.info("Returning Product Options Response");

        return getProductOptionsRS;
    }

    @Override
    public GetProductReviewsRS getProductReviews(String productid, int hitsPerPage, int pageNumber) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(BeMyGuestLogger.PRODUCT_ID.name(), productid);
        logger.info("Get Product Reviews Request Initiated {} ", logData);
        // BeMyGuest connector doesn't support for product reviews
        ResultType resultType = new ResultType();
        GetProductReviewsRS getProductReviewsRS = new GetProductReviewsRS();
        resultType.setCode(VendorErrorCode.REVIEWS_NOT_FOUND_ERROR.getId());
        resultType.setMessage(VendorErrorCode.REVIEWS_NOT_FOUND_ERROR.getMsg());
        getProductReviewsRS.setResultType(resultType);

        logger.info("Returning Product Reviews Response");
        return getProductReviewsRS;
    }

    @Override
    public GetCancellationRulesRS getProductCancellationRules(String productId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logger.info("Get Product Cancellation Rules Request Initiated {} ", logData);
        Map<String,Object> urlVariables;
        GetCancellationRulesRS getCancellationRulesRS = new GetCancellationRulesRS();
        CancellationRules cancellationRules = new CancellationRules();
        ResultType resultType = new ResultType();
        BmgProductDetailsRS beMyGuestResponse = null;
        try {
            logger.info("BeMyGuest Cancellation Rules Request Initiated {} ", logData);
            urlVariables = ProductTransformer.toProductDetailsRequestParamMap(productId);
            beMyGuestResponse = restClient.getProductDetails(urlVariables);
            List<BmgCancellationPolicies> bmgCancellationPolicies = beMyGuestResponse.getData().getProductTypes().get(0).getCancellationPolicies();
            logger.info("BeMyGuest Cancellation Rules Request Completed");
            cancellationRules = ProductTransformer.toCancellationRules(bmgCancellationPolicies);
            getCancellationRulesRS.setCancellationRules(cancellationRules);

        } catch (HttpServerErrorException hse) {
            logger.error("BeMyGuest Cancellation Rules Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getCancellationRulesRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("BeMyGuest Cancellation Rules Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getCancellationRulesRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("BeMyGuest Cancellation Rules Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            getCancellationRulesRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("BeMyGuest Cancellation Rules Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getCancellationRulesRS.setResultType(resultType);
        }

        logger.info("Returning Cancellation Rules Response");
        return getCancellationRulesRS;
    }
}