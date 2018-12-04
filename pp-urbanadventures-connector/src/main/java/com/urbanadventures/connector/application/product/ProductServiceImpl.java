package com.urbanadventures.connector.application.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.urbanadventures.connector.application.common.UrbanAdventuresLogger;
import com.urbanadventures.connector.application.common.UrbanAdventuresUtil;
import com.urbanadventures.connector.application.common.VendorErrorCode;
import com.urbanadventures.connector.application.soapclient.SoapClientService;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductDetailsRS;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRS;
import com.placepass.connector.common.product.ProductOption;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetPriceRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetPriceRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetTripInfoRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetTripInfoRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripAlmRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripAlmRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripAvailableDateRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripAvailableDateRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripDeparture;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripFullInfo;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripPrice;

@Service
public class ProductServiceImpl extends WebServiceGatewaySupport implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private SoapClientService soapClientService;

    @Override
    public GetProductDetailsRS getProductDetails(String productId) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(UrbanAdventuresLogger.PRODUCT_ID.name(), productId);
        logger.info("Get Product Details Request Initiated {} ", logData);

        ClsGetTripInfoRS clsGetTripInfoRS = null;
        GetProductDetailsRS getProductDetailsRS = new GetProductDetailsRS();
        ResultType resultType = new ResultType();
        try {
            ClsGetTripInfoRQ clsGetTripInfoRQ = ProductTransformer.toClsGetTripInfoRQ(productId);
            
            logger.info("TripInfo Request Initiated {} ", logData);
            clsGetTripInfoRS = soapClientService.getProductDetails(clsGetTripInfoRQ);
            logger.info("TripInfo Request Completed");
            
            getProductDetailsRS = ProductTransformer.toGetProductDetailsRS(clsGetTripInfoRS);

        } catch (WebServiceIOException wsioe) {
            logger.error("Urban Adventures Product Details Request Error", wsioe);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            getProductDetailsRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("Urban Adventures Product Details Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getProductDetailsRS.setResultType(resultType);
        }
        logger.info("Returning Product Details Response");
        return getProductDetailsRS;
    }

    @Override
    public GetAvailabilityRS getAvailability(String productId, String month, String year, String fromDate,
            String toDate) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(UrbanAdventuresLogger.PRODUCT_ID.name(), productId);
        logData.put(UrbanAdventuresLogger.MONTH.name(), month);
        logData.put(UrbanAdventuresLogger.YEAR.name(), year);
        logData.put(UrbanAdventuresLogger.FROM_DATE.name(), fromDate);
        logData.put(UrbanAdventuresLogger.TO_DATE.name(), toDate);
        logger.info("Get Availability Request Initiated {} ", logData);

        ClsTripAvailableDateRS clsTripAvailableDateRS = null;
        GetAvailabilityRS getAvailabilityRS = new GetAvailabilityRS();
        ResultType resultType = new ResultType();
        try {
            String startDate = year + "-" + month + "-01";
            String endDate = year + "-" + month + "-" + UrbanAdventuresUtil.getLastDayOfMonth(startDate);

            ClsTripAvailableDateRQ uaClsTripAvailableDateRQ = ProductTransformer.toClsTripAvailableDateRQ(productId,
                    startDate, endDate);
            
            logger.info("TripAvailability Request Initiated {} ", logData);
            clsTripAvailableDateRS = soapClientService.getProductAvailability(uaClsTripAvailableDateRQ);
            logger.info("TripAvailability Request Completed");

            getAvailabilityRS = ProductTransformer.toGetAvailabilityRS(clsTripAvailableDateRS);

        } catch (WebServiceIOException wsioe) {
            logger.error("Urban Adventures Availability Request Error", wsioe);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            getAvailabilityRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("Urban Adventures Availability Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getAvailabilityRS.setResultType(resultType);
        }
        logger.info("Returning Availability Response");
        return getAvailabilityRS;
    }

    @Override
    public GetProductOptionsRS getProductOptions(String productId, String date) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(UrbanAdventuresLogger.PRODUCT_ID.name(), productId);
        logData.put(UrbanAdventuresLogger.PRODUCT_OPTION_DATE.name(), date);
        logger.info("Get Product Options Request Initiated {} ", logData);

        GetProductOptionsRS getProductOptionsRS = new GetProductOptionsRS();
        ResultType resultType = new ResultType();
        ClsGetTripInfoRS uaClsGetTripInfoRS = null;
        try {
            ClsGetTripInfoRQ uaClsGetTripInfoRQ = ProductTransformer.toClsGetTripInfoRQ(productId);
            
            logData.put(UrbanAdventuresLogger.VENDOR_METHOD.name(), "getProductDetails()");
            logger.info("TripInfo Request Initiated {} ", logData);
            uaClsGetTripInfoRS = soapClientService.getProductDetails(uaClsGetTripInfoRQ);
            logger.info("TripInfo Request Completed");

            // Start, binding values urbanadventures >><< placepass
            if (uaClsGetTripInfoRS.getOpResult().getCode() != 0) {

                resultType.setCode(uaClsGetTripInfoRS.getOpResult().getCode());
                resultType.setMessage(uaClsGetTripInfoRS.getOpResult().getMessage());
                getProductOptionsRS.setResultType(resultType);
                return getProductOptionsRS;
            }

            ClsTripFullInfo uaClsTripFullInfo = uaClsGetTripInfoRS.getTripInfo();
            List<ClsTripDeparture> uaTripDepartures = uaClsTripFullInfo.getDeparture();
            List<ProductOption> ppproductOptions = new ArrayList<>();

            for (ClsTripDeparture uaTripDeparture : uaTripDepartures) {

                // Getting allotment|availability count for departure id
                ClsTripAlmRQ uaClsTripAlmRQ = ProductTransformer.toClsTripAlmRQ(productId, date,
                        uaTripDeparture.getId());
                ClsTripAlmRS uaClsTripAlmRS = null;

                logData.put(UrbanAdventuresLogger.VENDOR_METHOD.name(), "getProductAllotment()");
                logger.info("TripAllotment Request Initiated {} ", logData);
                uaClsTripAlmRS = soapClientService.getProductAllotment(uaClsTripAlmRQ);
                logger.info("TripAllotment Request Completed");
                
                if (uaClsTripAlmRS.getOpResult().getCode() != 0) {

                    resultType.setCode(uaClsTripAlmRS.getOpResult().getCode());
                    resultType.setMessage(uaClsTripAlmRS.getOpResult().getMessage());
                    getProductOptionsRS.setResultType(resultType);
                    return getProductOptionsRS;
                }

                // Getting accurate adult|child prices
                ClsGetPriceRQ clsGetPriceRQ = ProductTransformer.toClsGetPriceRQ(productId, date,
                        uaTripDeparture.getId());
                ClsGetPriceRS clsGetPriceRS = null;

                logData.put(UrbanAdventuresLogger.VENDOR_METHOD.name(), "getProductPrice()");
                logger.info("Price Request Initiated {} ", logData);
                clsGetPriceRS = soapClientService.getProductPrice(clsGetPriceRQ);
                logger.info("Price Request Completed");
                
                ClsTripPrice clsTripPrice = clsGetPriceRS.getPriceInfo();

                if (clsGetPriceRS.getOpResult().getCode() != 0) {

                    resultType.setCode(clsGetPriceRS.getOpResult().getCode());
                    resultType.setMessage(clsGetPriceRS.getOpResult().getMessage());
                    getProductOptionsRS.setResultType(resultType);
                    return getProductOptionsRS;
                }

                ppproductOptions.add(ProductTransformer.toProductOption(uaClsTripFullInfo, uaTripDeparture,
                        uaClsTripAlmRS, clsTripPrice));
            }
            getProductOptionsRS = ProductTransformer.toGetProductOptionsRS(ppproductOptions);

        } catch (WebServiceIOException wsioe) {
            logger.error("Urban Adventures Product Options Request Error", wsioe);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);

        } catch (Exception e) {
            logger.error("Urban Adventures Product Options Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);
        }
        logger.info("Returning Product Options Response");
        return getProductOptionsRS;
    }

    @Override
    public GetProductReviewsRS getProductReviews() {
        ResultType resultType = new ResultType();
        // Since ua doesn't support reviews
        resultType.setCode(1);
        resultType.setMessage("Reviews are not available for this product.");

        GetProductReviewsRS getProductReviewsRS = new GetProductReviewsRS();
        getProductReviewsRS.setResultType(resultType);

        logger.info("Returning Product Reviews Response");
        return getProductReviewsRS;
    }

}
