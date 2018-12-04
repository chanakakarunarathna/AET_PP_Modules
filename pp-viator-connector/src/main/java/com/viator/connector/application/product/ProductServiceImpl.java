package com.viator.connector.application.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.placepass.exutil.BadRequestException;
import com.placepass.utils.vendorproduct.ProductHashGenerator;
import com.placepass.utils.vendorproduct.Vendor;
import com.viator.connector.application.util.ViatorLogger;
import com.viator.connector.application.util.ViatorUtil;
import com.viator.connector.domain.placepass.algolia.product.GetAlgoliaProductDetailsRS;
import com.placepass.connector.common.booking.VendorErrorCode;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.product.GetAvailabilityRQ;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductDetailsRQ;
import com.placepass.connector.common.product.GetProductDetailsRS;
import com.placepass.connector.common.product.GetProductOptionsRQ;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRQ;
import com.placepass.connector.common.product.GetProductReviewsRS;
import com.viator.connector.domain.viator.common.AgeBand;
import com.viator.connector.domain.viator.pricingmatrix.ViatorPricingMatrixRequest;
import com.viator.connector.domain.viator.pricingmatrix.ViatorPricingMatrixResponse;
import com.viator.connector.domain.viator.product.ViatorProductResponse;
import com.viator.connector.domain.viator.reviews.ViatorProductReviewsResponse;
import com.viator.connector.domain.viator.tourgrades.ViatorTourgradesRequest;
import com.viator.connector.domain.viator.tourgrades.ViatorTourgradesResponse;
import com.viator.connector.infrastructure.RestClient;
import com.viator.connector.placepass.algolia.infrastructure.AlgoliaSearchClient;

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

    @Override
    public GetProductDetailsRS getProductDetails(GetProductDetailsRQ productDetailsRQ) {

        String partnerId = productDetailsRQ.getPartnerId();
        String productId = productDetailsRQ.getProductId();
        String currencyCode = productDetailsRQ.getCurrencyCode();
        boolean excludeTourGrade = productDetailsRQ.isExcludeTourGrade();
        boolean showUnavailable = productDetailsRQ.isShowUnavailable();

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(ViatorLogger.PRODUCT_ID.name(), productId);
        logData.put(ViatorLogger.PARTNER_ID.name(), partnerId);
        logData.put(ViatorLogger.CUR_CODE.name(), currencyCode);
        logger.info("Get Product Details Request Initiated {} ", logData);

        GetProductDetailsRS getProductDetailsRS = new GetProductDetailsRS();
        ResultType resultType = new ResultType();
        ViatorProductResponse viatorProductResponse = null;
        try {
            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("code", productId);
            urlVariables.put("currencyCode", currencyCode);
            urlVariables.put("excludeTourGradeAvailability", excludeTourGrade);
            urlVariables.put("showUnavailable", showUnavailable);

            logger.info("Viator Product Details Request Initiated {} ", logData);
            viatorProductResponse = restClient.getProductDetails(urlVariables);
            logger.info("Viator Product Details Request Completed");
            getProductDetailsRS = ProductTransformer.toProductDetails(viatorProductResponse);

        } catch (HttpServerErrorException hse) {
            logger.error("Viator Product Details Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getProductDetailsRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("Viator Product Details Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getProductDetailsRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("Viator Product Details Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            getProductDetailsRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("Viator Product Details Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getProductDetailsRS.setResultType(resultType);
        }
        logger.info("Returning Product Details Response");
        return getProductDetailsRS;
    }

    @Override
    public GetAvailabilityRS getAvailability(GetAvailabilityRQ availabilityRQ) {

        String productId = availabilityRQ.getProductId();
        String month = availabilityRQ.getMonth();
        String year = availabilityRQ.getYear();

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(ViatorLogger.PRODUCT_ID.name(), productId);
        logData.put(ViatorLogger.MONTH.name(), month);
        logData.put(ViatorLogger.YEAR.name(), year);
        logger.info("Get Availability Request Initiated {} ", logData);

        GetAvailabilityRS getAvailabilityRS = new GetAvailabilityRS();
        ResultType resultType = new ResultType();
        ViatorPricingMatrixResponse viatorPricingMatrixResponse = null;

        if (month == null || month.isEmpty() || year == null || year.isEmpty()) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.name(), "month and year cannot be null");
        }
        try {
            ViatorPricingMatrixRequest pricingMatrixRequest = ProductTransformer.toAvailabilityRequest(productId,
                    Integer.parseInt(month), Integer.parseInt(year));

            logger.info("ViatorPricingMatrixRequest Initiated {} ", logData);
            viatorPricingMatrixResponse = restClient.getPricingMatrixAvailability(pricingMatrixRequest);
            logger.info("ViatorPricingMatrixRequest Completed");
            getAvailabilityRS = ProductTransformer.toProductAvailability(viatorPricingMatrixResponse);

        } catch (HttpServerErrorException hse) {
            logger.error("Viator Pricing Matrix Availability Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getAvailabilityRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("Viator Pricing Matrix Availability Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getAvailabilityRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("Viator Pricing Matrix Availability Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            getAvailabilityRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("Viator Pricing Matrix Availability Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getAvailabilityRS.setResultType(resultType);
        }
        logger.info("Returning Availability Response");
        return getAvailabilityRS;
    }

    @Override
    public GetProductOptionsRS getProductOptions(GetProductOptionsRQ productOptionsRQ) {

        String productId = productOptionsRQ.getProductId();
        String date = productOptionsRQ.getBookingDate();

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(ViatorLogger.PRODUCT_ID.name(), productId);
        logData.put(ViatorLogger.PRODUCT_OPTION_DATE.name(), date);
        logger.info("Get Product Options Request Initiated {} ", logData);

        GetProductOptionsRS getProductOptionsRS = new GetProductOptionsRS();
        ResultType resultType = new ResultType();
        ViatorPricingMatrixResponse viatorPricingMatrixResponse = null;
        ViatorTourgradesResponse viatorTourgradesResponse = null;
        GetAlgoliaProductDetailsRS algoliaProductDetails = null;
        List<AgeBand> productRequiredAgebands = null;

        try {
            String month = ViatorUtil.getMonthFromString(date);
            String year = ViatorUtil.getYearFromString(date);

            ViatorPricingMatrixRequest pricingMatrixRequest = ProductTransformer.toAvailabilityRequest(productId,
                    Integer.parseInt(month), Integer.parseInt(year));
            logData.put(ViatorLogger.VENDOR_METHOD.name(), "getPricingMatrixAvailability()");
            logger.info("ViatorPricingMatrixRequest Initiated {} ", logData);
            viatorPricingMatrixResponse = restClient.getPricingMatrixAvailability(pricingMatrixRequest);
            logger.info("ViatorPricingMatrixRequest Completed");

        } catch (HttpServerErrorException hse) {
            logger.error("Viator Pricing Matrix Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);
            return getProductOptionsRS;

        } catch (HttpClientErrorException hce) {
            logger.error("Viator Pricing Matrix Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);
            return getProductOptionsRS;

        } catch (ResourceAccessException rae) {
            logger.error("Viator Pricing Matrix Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);
            return getProductOptionsRS;

        } catch (Exception e) {
            logger.error("Viator Pricing Matrix Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);
            return getProductOptionsRS;
        }

        try {

            ViatorTourgradesRequest viatorTourgradesRequest = ProductTransformer.toTourgradesDefaultAgebandRequest(
                    productId, date);
            logData.put(ViatorLogger.VENDOR_METHOD.name(), "getAvailabilityTourgrades()");

            logger.info("ViatorTourgradesRequest (Default Adult) Initiated {} ", logData);
            viatorTourgradesResponse = restClient.getAvailabilityTourgrades(viatorTourgradesRequest);
            logger.info("ViatorTourgradesRequest (Default Adult) Completed");

            if (viatorTourgradesResponse.getData() != null && !viatorTourgradesResponse.getData().isEmpty()
                    && "TRAVELLER_MISMATCH".equals(viatorTourgradesResponse.getData().get(0).getUnavailableReason())) {

                productRequiredAgebands = new ArrayList<>();
                productRequiredAgebands = ProductTransformer.getProductRequiredAgeBands(viatorTourgradesResponse
                        .getData().get(0).getAgeBandsRequired().get(0));

                viatorTourgradesRequest = ProductTransformer.toTourgradesRequiredAgebandsRequest(productId, date,
                        productRequiredAgebands);

                logger.info("ViatorTourgradesRequest with RequiredAgebands Initiated {} ", logData);
                viatorTourgradesResponse = restClient.getAvailabilityTourgrades(viatorTourgradesRequest);
                logger.info("ViatorTourgradesRequest with RequiredAgebands Completed");

            }

        } catch (HttpServerErrorException hse) {
            logger.error("Viator Tour Grades Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);
            return getProductOptionsRS;

        } catch (HttpClientErrorException hce) {
            logger.error("Viator Tour Grades Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);
            return getProductOptionsRS;

        } catch (ResourceAccessException rae) {
            logger.error("Viator Tour Grades Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);
            return getProductOptionsRS;

        } catch (Exception e) {
            logger.error("Viator Tour Grades Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);
            return getProductOptionsRS;
        }

        try {

            /*
             * In here we will get the vendor specific product id and hash it to place pass specific vendor product code
             * to get product details from Algolia
             */
            logger.info("Generating PlacePass Specific Vendor Product Code using generateHash()");

            logger.info("Before Hashing the Vendor Specific Product Code : " + productId);
            String hashedProductId = productHashGenerator.generateHash(Vendor.VIATOR.name() + productId);
            logger.info("After Hashing the Vendor Specific Product Code : " + hashedProductId);

            logData.put(ViatorLogger.VENDOR_METHOD.name(), "getProductDetails()");
            logger.info("Get Product Details Algolia Request Initiated {} ", logData);
            algoliaProductDetails = algoliaSearchClient.getProductDetails(hashedProductId);
            logger.info("Get Product Details Algolia Request Completed");

        } catch (Exception e) {
            logger.error("Product Details Algolia Request Error", e);
        }

        getProductOptionsRS = ProductTransformer.toProductOptions(viatorPricingMatrixResponse,
                viatorTourgradesResponse, date, algoliaProductDetails);

        logger.info("Returning Product Options Response");
        return getProductOptionsRS;
    }

    @Override
    public GetProductReviewsRS getProductReviews(GetProductReviewsRQ productReviewsRQ) {

        String productId = productReviewsRQ.getProductId();
        int hitsPerPage = productReviewsRQ.getHitsPerPage();
        int pageNumber = productReviewsRQ.getPageNumber();

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(ViatorLogger.PRODUCT_ID.name(), productId);
        logData.put(ViatorLogger.PAGE_NUMBER.name(), pageNumber);
        logData.put(ViatorLogger.HITS_PER_PAGE.name(), hitsPerPage);
        logger.info("Get Product Reviews Request Initiated {} ", logData);

        int startIndex = (hitsPerPage * pageNumber) + 1;
        int endIndex = hitsPerPage * (pageNumber + 1);

        GetProductReviewsRS getProductReviewsRS = new GetProductReviewsRS();
        ResultType resultType = new ResultType();
        ViatorProductReviewsResponse viatorProductReviewsResponse = null;

        try {
            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("code", productId);
            urlVariables.put("topX", String.valueOf(startIndex) + " - " + String.valueOf(endIndex));
            urlVariables.put("showUnavailable", "true");
            urlVariables.put("sortOrder", "REVIEW_RATING_SUBMISSION_DATE_D");

            logger.info("ViatorProductReviews Request Initiated {} ", logData);
            viatorProductReviewsResponse = restClient.getProductReviews(urlVariables);
            logger.info("ViatorProductReviews Request Completed");
            getProductReviewsRS = ProductTransformer.toReviews(viatorProductReviewsResponse);

        } catch (HttpServerErrorException hse) {
            logger.error("Viator Product Reviews Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getProductReviewsRS.setResultType(resultType);

        } catch (HttpClientErrorException hce) {
            logger.error("Viator Product Reviews Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getProductReviewsRS.setResultType(resultType);

        } catch (ResourceAccessException rae) {
            logger.error("Viator Product Reviews Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            getProductReviewsRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("Viator Product Reviews Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getProductReviewsRS.setResultType(resultType);
        }
        logger.info("Returning Product Reviews Response");
        return getProductReviewsRS;
    }

}