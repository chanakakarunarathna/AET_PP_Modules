package com.viator.connector.infrastructure;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.viator.connector.domain.viator.availability.ViatorAvailabilityRequest;
import com.viator.connector.domain.viator.availability.ViatorAvailabilityResponse;
import com.viator.connector.domain.viator.book.ViatorBookRequest;
import com.viator.connector.domain.viator.book.ViatorBookResponse;
import com.viator.connector.domain.viator.calculateprice.ViatorCalculatepriceRequest;
import com.viator.connector.domain.viator.calculateprice.ViatorCalculatepriceResponse;
import com.viator.connector.domain.viator.cancel.ViatorCancelBookingRequest;
import com.viator.connector.domain.viator.cancel.ViatorCancelBookingResponse;
import com.viator.connector.domain.viator.mybookings.ViatorMybookingsResponse;
import com.viator.connector.domain.viator.pricingmatrix.ViatorPricingMatrixRequest;
import com.viator.connector.domain.viator.pricingmatrix.ViatorPricingMatrixResponse;
import com.viator.connector.domain.viator.product.ViatorProductResponse;
import com.viator.connector.domain.viator.reviews.ViatorProductReviewsResponse;
import com.viator.connector.domain.viator.tourgrades.ViatorTourgradesRequest;
import com.viator.connector.domain.viator.tourgrades.ViatorTourgradesResponse;

@Component
public class RestClient {

    private Logger logger = LoggerFactory.getLogger(RestClient.class);

    @Autowired
    @Qualifier("RestTemplate")
    private RestTemplate restTemplate;
    
    @Autowired
    private RestServiceConfig restServiceConfig;

    @Value("${viator.apiKey}")
    private String viatorApiKey;

    public ViatorProductResponse getProductDetails(Map<String, Object> urlVariables) {

        Map<String, Object> logRequet = new HashMap<String, Object>();
        logRequet.putAll(urlVariables);
        logger.info("GET ViatorProductDetails Request : {}", logRequet);

        urlVariables.put("apiKey", viatorApiKey);
        ViatorProductResponse viatorProductResponse = restTemplate.getForObject(restServiceConfig.getViatorProductUrl(),
                ViatorProductResponse.class, urlVariables);

        logger.info("GET ViatorProductDetails Response : {}", new Gson().toJson(viatorProductResponse));
        return viatorProductResponse;

    }

    public ViatorAvailabilityResponse getAvailableSchedule(ViatorAvailabilityRequest availabilityRequest) {

        logger.info("POST ViatorAvailability Request {}", new Gson().toJson(availabilityRequest));

        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("apiKey", viatorApiKey);
        ViatorAvailabilityResponse viatorAvailabilityResponse = restTemplate.postForObject(
                restServiceConfig.getViatorAvailabilityUrl(), availabilityRequest, ViatorAvailabilityResponse.class,
                urlVariables);

        logger.info("POST ViatorAvailability Response : {}", new Gson().toJson(viatorAvailabilityResponse));
        return viatorAvailabilityResponse;
    }

    public ViatorProductReviewsResponse getProductReviews(Map<String, Object> urlVariables) {

        Map<String, Object> logRequet = new HashMap<String, Object>();
        logRequet.putAll(urlVariables);
        logger.info("GET ViatorProductReviews Request : {}", logRequet);

        urlVariables.put("apiKey", viatorApiKey);
        ViatorProductReviewsResponse viatorProductReviewsResponse = restTemplate.getForObject(
                restServiceConfig.getViatorProductReviewsUrl(), ViatorProductReviewsResponse.class, urlVariables);

        logger.info("GET ViatorProductReviews Response : {}", new Gson().toJson(viatorProductReviewsResponse));
        return viatorProductReviewsResponse;
    }

    public ViatorPricingMatrixResponse getPricingMatrixAvailability(ViatorPricingMatrixRequest availabilityRequest) {

        logger.info("POST ViatorPricingMatrix Request : {}", new Gson().toJson(availabilityRequest));

        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("apiKey", viatorApiKey);
        ViatorPricingMatrixResponse viatorPricingMatrixResponse = restTemplate.postForObject(
                restServiceConfig.getViatorTourgradesPricingMatrixUrl(), availabilityRequest,
                ViatorPricingMatrixResponse.class, urlVariables);

        logger.info("POST ViatorPricingMatrix Response : {}", new Gson().toJson(viatorPricingMatrixResponse));
        return viatorPricingMatrixResponse;
    }

    public ViatorBookResponse createBooking(ViatorBookRequest bookingRequest) {

        logger.info("POST ViatorBook Request : {}", new Gson().toJson(bookingRequest));

        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("apiKey", viatorApiKey);
        ViatorBookResponse viatorBookDetails = restTemplate.postForObject(restServiceConfig.getViatorBookingBookUrl(),
                bookingRequest, ViatorBookResponse.class, urlVariables);

        logger.info("POST ViatorBook Response : {}", new Gson().toJson(viatorBookDetails));
        return viatorBookDetails;

    }

    public ViatorCalculatepriceResponse createCalculatePricing(ViatorCalculatepriceRequest viatorPricingRequest) {

        logger.info("POST ViatorCalculateprice Request : {}", new Gson().toJson(viatorPricingRequest));

        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("apiKey", viatorApiKey);
        ViatorCalculatepriceResponse viatorPricingDetails = restTemplate.postForObject(
                restServiceConfig.getViatorBookingCalculatePriceUrl(), viatorPricingRequest,
                ViatorCalculatepriceResponse.class, urlVariables);

        logger.info("POST ViatorCalculateprice Response : {}", new Gson().toJson(viatorPricingDetails));
        return viatorPricingDetails;
    }

    public ViatorTourgradesResponse getAvailabilityTourgrades(ViatorTourgradesRequest tourgradesRequest) {

        logger.info("POST ViatorTourgrades Request : {}", new Gson().toJson(tourgradesRequest));

        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("apiKey", viatorApiKey);
        ViatorTourgradesResponse viatorTourgradesResponse = restTemplate.postForObject(
                restServiceConfig.getViatorAvailabilityTourgradesUrl(), tourgradesRequest,
                ViatorTourgradesResponse.class, urlVariables);

        logger.info("POST ViatorTourgrades Response : {}", new Gson().toJson(viatorTourgradesResponse));
        return viatorTourgradesResponse;
    }
    
    public ViatorMybookingsResponse getBookingStatus(Map<String, Object> urlVariables) {

        ViatorMybookingsResponse viatorMybookingsResponse = null;
        Map<String, Object> logRequet = new HashMap<String, Object>();
        logRequet.putAll(urlVariables);
        logger.info("GET ViatorMybookings Request : {}", logRequet);

        urlVariables.put("apiKey", viatorApiKey);

        if (urlVariables.containsKey("voucherKey")) {
            viatorMybookingsResponse = restTemplate.getForObject(restServiceConfig.getViatorMybookingsUrl(),
                    ViatorMybookingsResponse.class, urlVariables);
        } else {
            viatorMybookingsResponse = restTemplate.getForObject(restServiceConfig.getViatorBookingItineraryIdUrl(),
                    ViatorMybookingsResponse.class, urlVariables);
        }

        logger.info("GET ViatorMybookings Response : {}", new Gson().toJson(viatorMybookingsResponse));
        return viatorMybookingsResponse;
    }

    public ViatorCancelBookingResponse getCancelBookingDetails(ViatorCancelBookingRequest cancelBookingRequest) {

        logger.info("POST ViatorCancelBooking Request : {}", new Gson().toJson(cancelBookingRequest));

        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("apiKey", viatorApiKey);
        ViatorCancelBookingResponse viatorCancelBookingResponse = restTemplate.postForObject(
                restServiceConfig.getViatorCancelBookingUrl(), cancelBookingRequest, ViatorCancelBookingResponse.class,
                urlVariables);

        logger.info("POST ViatorCancelBooking Response : {}", new Gson().toJson(viatorCancelBookingResponse));
        return viatorCancelBookingResponse;
    }
}