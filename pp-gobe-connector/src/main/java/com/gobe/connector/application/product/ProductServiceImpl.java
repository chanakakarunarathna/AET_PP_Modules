package com.gobe.connector.application.product;

import com.gobe.connector.application.util.GobeLogger;
import com.gobe.connector.application.util.VendorErrorCode;
import com.gobe.connector.domain.gobe.Inventory.GobeInventoryCheckRS;
import com.gobe.connector.domain.gobe.availability.GobeScheduleRS;
import com.gobe.connector.domain.gobe.availability.TimeSlot;
import com.gobe.connector.domain.gobe.price.GobePrice;
import com.gobe.connector.domain.gobe.price.GobePricesRSRepository;
import com.gobe.connector.domain.gobe.product.GobeProduct;
import com.gobe.connector.domain.gobe.product.GobeProductsRSRepository;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductDetailsRS;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRS;
import com.gobe.connector.infrastructure.OAuth2RestClient;
import com.placepass.exutil.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created on 8/8/2017.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    GobePricesRSRepository gobePricesRSRepository;

    @Autowired
    GobeProductsRSRepository gobeProductsRSRepository;

    @Autowired
    OAuth2RestClient restClient;

    @Override
    public GetProductDetailsRS getProductDetails(String partnerId, String productId, String currencyCode,
            boolean excludeTourGrade, boolean showUnavailable) {
        return null;
    }

    @Override
    public GetAvailabilityRS getAvailability(String productid, String month, String year) {
        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(GobeLogger.PRODUCT_ID.name(), productid);
        logData.put(GobeLogger.MONTH.name(), month);
        logData.put(GobeLogger.YEAR.name(), year);
        logger.info("Get Availability Request Initiated {} ", logData);

        GetAvailabilityRS getAvailabilityRS = new GetAvailabilityRS();
        ResultType resultType = new ResultType();
        GobeScheduleRS gobeScheduleRS = null;

        if (month == null || month.isEmpty() || year == null || year.isEmpty()) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.name(), "month and year cannot be null");
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String startDate = null;
            String endDate = null;
            Calendar calendar = Calendar.getInstance();

            // Note month is in [1, 12] and Java Date has month in [0, 11]
            if (calendar.get(Calendar.MONTH) == Integer.parseInt(month) - 1
                    && calendar.get(Calendar.YEAR) == Integer.parseInt(year)) {
                startDate = dateFormat.format(calendar.getTime());
            } else {
                calendar.set(Calendar.DATE, 1);
                calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
                calendar.set(Calendar.YEAR, Integer.parseInt(year));
                startDate = dateFormat.format(calendar.getTime());
            }
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            endDate = dateFormat.format(calendar.getTime());

            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("tourId", productid);
            urlVariables.put("startDate", startDate);
            urlVariables.put("endDate", endDate);

            logger.info("Gobe Schedule Request Initiated {} ", logData);
            gobeScheduleRS = restClient.getSchedule(urlVariables);
            logger.info("Gobe Schedule Request Completed");

            try {
                logger.info("Request product details from Mongo");
                GobeProduct gobeProduct = gobeProductsRSRepository.findOne(productid);
                logger.info("Finished getting product details from Mongo");
                if (gobeProduct == null) {
                    logger.error("Product not found on Mongo");
                    // Should create a new error and message for this
                    resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
                    resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
                    getAvailabilityRS.setResultType(resultType);
                    return getAvailabilityRS;
                }
                getAvailabilityRS = ProductTransformer.toProductAvailability(gobeScheduleRS, gobeProduct);
            } catch (DataAccessException ex) {
                logger.error("Error Requesting info from Mongo");
                // Should create a new error and message for this
                resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
                resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
                getAvailabilityRS.setResultType(resultType);
                return getAvailabilityRS;
            }

        } catch (HttpServerErrorException hse) {
            logger.error("Gobe Schedule Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getAvailabilityRS.setResultType(resultType);
            return getAvailabilityRS;

        } catch (HttpClientErrorException hce) {
            logger.error("Gobe Schedule Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getAvailabilityRS.setResultType(resultType);
            return getAvailabilityRS;

        } catch (ResourceAccessException rae) {
            logger.error("Gobe Schedule Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            getAvailabilityRS.setResultType(resultType);
            return getAvailabilityRS;

        } catch (Exception e) {
            logger.error("Gobe Schedule Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getAvailabilityRS.setResultType(resultType);
            return getAvailabilityRS;
        }
        logger.info("Returning Availability Response");
        return getAvailabilityRS;
    }

    @Override
    public GetProductOptionsRS getProductOptions(String productid, String date) {

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(GobeLogger.PRODUCT_ID.name(), productid);
        logData.put(GobeLogger.PRODUCT_OPTION_DATE.name(), date);
        logger.info("Get Product Options Request Initiated {} ", logData);

        GetProductOptionsRS getProductOptionsRS = new GetProductOptionsRS();
        ResultType resultType = new ResultType();
        GobeScheduleRS gobeScheduleRS = null;

        try {

            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("tourId", productid);
            urlVariables.put("startDate", date);
            urlVariables.put("endDate", date);

            logger.info("Gobe Schedule Request Initiated {} ", logData);
            gobeScheduleRS = restClient.getSchedule(urlVariables);
            logger.info("Gobe Schedule Request Completed");


            try {
                logger.info("Request product and prices details from Mongo");
                List<GobePrice> gobePrices = gobePricesRSRepository.findByTourIdRegex(productid);
                GobeProduct gobeProduct = gobeProductsRSRepository.findOne(productid);
                logger.info("Finished getting product and prices details from Mongo");
                if (gobeProduct == null || gobePrices == null) {
                    logger.error("Product or price not found on Mongo");
                    // Should create a new error and message for this
                    resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
                    resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
                    getProductOptionsRS.setResultType(resultType);
                    return getProductOptionsRS;
                }
                // checking inventory on each scheduled timeslot
                ArrayList<TimeSlot> availableTimeSlots = new ArrayList<>();
                if (gobeScheduleRS.getTimeSlots() != null) {
                    if (gobeScheduleRS.getTimeSlots().size() <= 2) {
                        for (TimeSlot timeSlot : gobeScheduleRS.getTimeSlots()) {
                            try {
                                String startTime = timeSlot.getStartTime().substring(timeSlot.getStartTime().indexOf("T") + 1,
                                        timeSlot.getStartTime().length() - 4);

                                Map<String, Object> inventoryCheckUrlVariables = new HashMap<String, Object>();
                                inventoryCheckUrlVariables.put("productId", productid);
                                inventoryCheckUrlVariables.put("startTime", startTime);
                                inventoryCheckUrlVariables.put("tourDate", date);
                                //making sure to check inventory for the minimum capacity
                                String minimumCapacity = "1";
                                if (gobeProduct.getMinimumCapacity() != null && !gobeProduct.getMinimumCapacity().equals("")) {
                                    minimumCapacity = gobeProduct.getMinimumCapacity();
                                }
                                inventoryCheckUrlVariables.put("quantity", minimumCapacity);

                                GobeInventoryCheckRS gobeInventoryCheckRS = restClient
                                        .checkInventory(inventoryCheckUrlVariables);

                                if (gobeInventoryCheckRS.getStatus() != null
                                        && gobeInventoryCheckRS.getStatus().toLowerCase().equals("available")) {
                                    availableTimeSlots.add(timeSlot);
                                }

                            } catch (HttpServerErrorException hse) {
                                logger.error("Gobe Inventory Check Request Error", hse);
                                continue;
                            } catch (HttpClientErrorException hce) {
                                logger.error("Gobe Inventory Check Request Error", hce);
                                continue;
                            } catch (ResourceAccessException rae) {
                                logger.error("Gobe Inventory Check Request Error", rae);
                                continue;
                            } catch (Exception e) {
                                logger.error("Gobe Inventory Check Request Error", e);
                                continue;
                            }
                        }
                    } else {
                        availableTimeSlots.addAll(gobeScheduleRS.getTimeSlots());
                    }
                } else {
                    logger.error("Gobe Schedule Request Error With No Time Slots Available");
                    resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
                    resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
                    getProductOptionsRS.setResultType(resultType);
                    return getProductOptionsRS;
                }

                getProductOptionsRS = ProductTransformer.toProductOptions(availableTimeSlots, gobePrices, gobeProduct,
                        date);
            } catch (DataAccessException ex) {
                logger.error("Error Requesting info from Mongo");
                // Should create a new error and message for this
                resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
                resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
                getProductOptionsRS.setResultType(resultType);
                return getProductOptionsRS;
            }

        } catch (HttpServerErrorException hse) {
            logger.error("Gobe Schedule Request Error", hse);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);
            return getProductOptionsRS;

        } catch (HttpClientErrorException hce) {
            logger.error("Gobe Schedule Request Error", hce);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);
            return getProductOptionsRS;

        } catch (ResourceAccessException rae) {
            logger.error("Gobe Schedule Request Error", rae);
            resultType.setCode(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);
            return getProductOptionsRS;

        } catch (Exception e) {
            logger.error("Gobe Schedule Request Error", e);
            resultType.setCode(VendorErrorCode.VENDOR_API_ERROR.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_API_ERROR.getMsg());
            getProductOptionsRS.setResultType(resultType);
            return getProductOptionsRS;
        }

        logger.info("Returning Product Options Response");
        return getProductOptionsRS;
    }

    @Override
    public GetProductReviewsRS getProductReviews(String productid, int hitsPerPage, int pageNumber) {
        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put(GobeLogger.PRODUCT_ID.name(), productid);
        logData.put(GobeLogger.PAGE_NUMBER.name(), pageNumber);
        logData.put(GobeLogger.HITS_PER_PAGE.name(), hitsPerPage);
        logger.info("Get Product Reviews Request Initiated {} ", logData);
        GetProductReviewsRS getProductReviewsRS = new GetProductReviewsRS();
        // set ok here since there is no review
        ResultType resultType = new ResultType();
        resultType.setCode(0);
        resultType.setMessage("");
        getProductReviewsRS.setResultType(resultType);
        getProductReviewsRS.setReviews(new ArrayList<>());
        getProductReviewsRS.setTotalReviewCount(0);
        logger.info("Get Product Reviews Request Response");
        return getProductReviewsRS;
    }
}
