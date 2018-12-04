package com.gobe.connector.infrastructure;

import com.gobe.connector.application.util.GobeUtil;
import com.gobe.connector.domain.gobe.Inventory.GobeInventoryCheckRS;
import com.gobe.connector.domain.gobe.availability.GobeScheduleRS;
import com.gobe.connector.domain.gobe.book.*;
import com.gobe.connector.domain.gobe.image.GobeImageRSRepository;
import com.gobe.connector.domain.gobe.image.GobeImagesRS;
import com.gobe.connector.domain.gobe.price.GobePricesRS;
import com.gobe.connector.domain.gobe.price.GobePricesRSRepository;
import com.gobe.connector.domain.gobe.product.GobeProduct;
import com.gobe.connector.domain.gobe.product.GobeProductsRS;
import com.gobe.connector.domain.gobe.product.GobeProductsRSRepository;
import com.gobe.connector.domain.gobe.review.GobeReviewsRS;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created on 8/3/2017.
 */
@Component public class OAuth2RestClient {

    private Logger logger = LoggerFactory.getLogger(OAuth2RestClient.class);

    @Autowired private Oauth2RestConfig restServiceConfig;

    @Autowired @Qualifier("oAuth2RestTemplate") private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired private GobePricesRSRepository gobePricesRSRepository;

    @Autowired private GobeProductsRSRepository gobeProductsRSRepository;

    @Autowired private GobeImageRSRepository gobeImageRSRepository;

    @Autowired private GobeCancelOrderRSRepository gobeCancelOrderRSRepository;

    public GobeScheduleRS getSchedule(Map<String, Object> urlVariables) {

        Map<String, Object> logRequet = new HashMap<String, Object>();
        logRequet.putAll(urlVariables);
        logger.info("GET Gobe Schedule Request : {}", logRequet);

        GobeScheduleRS gobeScheduleRS = oAuth2RestTemplate
                .getForObject(restServiceConfig.getGobeScheduleUrl(), GobeScheduleRS.class, urlVariables);

        logger.info("GET Gobe Schedule Response : {}", new Gson().toJson(gobeScheduleRS));

        return gobeScheduleRS;
    }

    public GobeProductsRS getProducts() {
        int count = 1;
        GobeProductsRS gobeProductsRS = null;
        //gobeProductsRSRepository.deleteAll();

        do {
            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("count", count);

            gobeProductsRS = oAuth2RestTemplate
                    .getForObject(restServiceConfig.getGobeProductsUrl(), GobeProductsRS.class, urlVariables);

            logger.info("GET Gobe Product Response : {}", new Gson().toJson(gobeProductsRS));
            if (gobeProductsRS.getProducts() != null) {
                Iterator<GobeProduct> gobeProductIterator = gobeProductsRS.getProducts().iterator();
                while (gobeProductIterator.hasNext()) {
                    GobeProduct currentProduct = gobeProductIterator.next();
                    //if (currentProduct.getTtBookingStyle().equals("API") && GobeUtil.detectGobeAgeBandModel(currentProduct)) {
                    currentProduct.setId(currentProduct.getCode());
                    //} else {
                    //    gobeProductIterator.remove();
                    //}
                }
                //gobeProductsRSRepository.save(gobeProductsRS.getProducts());
            }
            count += gobeProductsRS.getTotalProductCount();
        } while (gobeProductsRS.getTotalProductCount() == 100);
        return gobeProductsRS;
    }

    public GobeImagesRS getImages() {
        int count = 1;
        GobeImagesRS gobeImagesRS = null;
        //gobeImageRSRepository.deleteAll();

        do {
            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("count", count);
            gobeImagesRS = oAuth2RestTemplate
                    .getForObject(restServiceConfig.getGobeImagesUrl(), GobeImagesRS.class, urlVariables);

            logger.info("GET Gobe Images Response : {}", new Gson().toJson(gobeImagesRS));

            if (gobeImagesRS.getImages() != null) {
                //gobeImageRSRepository.save(gobeImagesRS.getImages());
            }
            count += 100;

        } while (gobeImagesRS.getImages() != null && gobeImagesRS.getImages().size() > 0);
        return gobeImagesRS;
    }

    public GobePricesRS getPrices() {
        int count = 1;
        GobePricesRS gobePricesRS = null;
        //gobePricesRSRepository.deleteAll();

        do {
            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("count", count);
            gobePricesRS = oAuth2RestTemplate
                    .getForObject(restServiceConfig.getGobePriciesUrl(), GobePricesRS.class, urlVariables);

            logger.info("GET Gobe Prices Response : {}", new Gson().toJson(gobePricesRS));

            if (gobePricesRS.getPrices() != null) {
                //gobePricesRSRepository.save(gobePricesRS.getPrices());
            }
            count += 100;
        } while (gobePricesRS.getPrices() != null && gobePricesRS.getPrices().size() > 0);
        return gobePricesRS;
    }

    public GobeInventoryCheckRS checkInventory(Map<String, Object> urlVariables) {

        Map<String, Object> logRequet = new HashMap<String, Object>();
        logRequet.putAll(urlVariables);

        GobeInventoryCheckRS gobeInventoryCheckRS = oAuth2RestTemplate
                .getForObject(restServiceConfig.getgobeInventoryCheckUrl(), GobeInventoryCheckRS.class, urlVariables);

        logger.info("GET Gobe Inventory Check Response : {}", new Gson().toJson(gobeInventoryCheckRS));

        return gobeInventoryCheckRS;
    }

    public GobeReviewsRS getReviews(Map<String, Object> urlVariables) {

        Map<String, Object> logRequet = new HashMap<String, Object>();
        logRequet.putAll(urlVariables);

        GobeReviewsRS gobeReviewsRS = oAuth2RestTemplate
                .getForObject(restServiceConfig.getGobeProductReviewsUrl(), GobeReviewsRS.class, urlVariables);

        logger.info("GET Gobe Reviews Response : {}", new Gson().toJson(gobeReviewsRS));

        return gobeReviewsRS;
    }

    public GobeOrderRS makeBooking(GobeOrderRQ gobeOrderRQ) {

        logger.info("POST Gobe Order Request: {}", new Gson().toJson(gobeOrderRQ));

        GobeOrderRS gobeOrderRS = oAuth2RestTemplate
                .postForObject(restServiceConfig.getGobeBookingOrderUrl(), gobeOrderRQ, GobeOrderRS.class);

        logger.info("POST Gobe Order Response : {}", new Gson().toJson(gobeOrderRS));
        return gobeOrderRS;
    }

    public GobeCancelOrderRS cancelBooking(GobeCancelOrderRQ gobeCancelOrderRQ) {

        HttpEntity<GobeCancelOrderRQ> requestEntity = new HttpEntity<GobeCancelOrderRQ>(gobeCancelOrderRQ);
        HttpEntity<GobeCancelOrderRS> response = oAuth2RestTemplate
                .exchange(restServiceConfig.getGobeCancelBookingUrl(), HttpMethod.PUT, requestEntity,
                        GobeCancelOrderRS.class);

        GobeCancelOrderRS gobeCancelOrderRS = response.getBody();
        gobeCancelOrderRS.setVendorOrderId(gobeCancelOrderRQ.getOrderId());
        //Saving the cancellations to Mongo
        gobeCancelOrderRSRepository.save(gobeCancelOrderRS);

        logger.info("GET Gobe Cancel Order Response : {}", new Gson().toJson(gobeCancelOrderRS));

        return gobeCancelOrderRS;
    }

    public GobeOrderStatusRS getBookingStatus(Map<String, Object> urlVariables) {
        Map<String, Object> logRequet = new HashMap<String, Object>();
        logRequet.putAll(urlVariables);

        GobeOrderStatusRS gobeOrderStatusRS = oAuth2RestTemplate
                .getForObject(restServiceConfig.getGobeBookingStatusUrl(), GobeOrderStatusRS.class, urlVariables);

        logger.info("GET Gobe Order Status Response : {}", new Gson().toJson(gobeOrderStatusRS));

        return gobeOrderStatusRS;
    }
}
