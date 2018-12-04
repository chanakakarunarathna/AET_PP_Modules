package com.placepass.connector.bemyguest.infrastructure;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.placepass.connector.bemyguest.domain.bemyguest.booking.BmgBookRequest;
import com.placepass.connector.bemyguest.domain.bemyguest.booking.BmgBookResponse;
import com.placepass.connector.bemyguest.domain.bemyguest.booking.BmgGetBookingStatusResponse;
import com.placepass.connector.bemyguest.domain.bemyguest.bookingcheck.BeMyGuestCheckBookingRQ;
import com.placepass.connector.bemyguest.domain.bemyguest.bookingcheck.BeMyGuestCheckBookingRS;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgProductDetailsRS;

@Component
public class RestClient {

    private Logger logger = LoggerFactory.getLogger(RestClient.class);

    @Autowired
    @Qualifier("RestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private RestServiceConfig restServiceConfig;

    public BmgProductDetailsRS getProductDetails(Map<String, Object> urlVariables) {

        logger.info("GET BmgProductDetails Request : {}", urlVariables);

        HttpHeaders headers = getHttpHeaders(HttpMethod.GET);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<BmgProductDetailsRS> model = restTemplate.exchange(restServiceConfig.getBeMyGuestProductUrl(),
                HttpMethod.GET, entity, BmgProductDetailsRS.class, urlVariables);
        BmgProductDetailsRS productDetailResponse = (BmgProductDetailsRS) model.getBody();

        logger.info("GET BeMyGuest Product Response : {}", new Gson().toJson(productDetailResponse));
        logger.info(model.toString());

        return productDetailResponse;
    }

    public BmgBookResponse createBooking(BmgBookRequest bookingRequest) {

        logger.info("POST Bemyguest Book Request : {}", new Gson().toJson(bookingRequest));

        HttpHeaders headers = getHttpHeaders(HttpMethod.POST);
        HttpEntity<BmgBookRequest> request = new HttpEntity<BmgBookRequest>(bookingRequest, headers);
        ResponseEntity<BmgBookResponse> bmgBookResponseEntity = restTemplate
                .postForEntity(restServiceConfig.getBeMyGuestBookingBookUrl(), request, BmgBookResponse.class);
        BmgBookResponse bmgBookResponse = bmgBookResponseEntity.getBody();

        logger.info("POST Bemyguest Book Response : {}", new Gson().toJson(bmgBookResponse));
        return bmgBookResponse;

    }

    public BmgBookResponse updateBookingStatus(Map<String, Object> urlVariables) {

        logger.info("PUT Bmg updateBookingStatus Request : {}", urlVariables);

        HttpHeaders headers = getHttpHeaders(HttpMethod.PUT);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<BmgBookResponse> model = restTemplate.exchange(
                restServiceConfig.getBeMyGuestBookingUpdateStatusUrl(), HttpMethod.PUT, entity, BmgBookResponse.class,
                urlVariables);
        BmgBookResponse bmgBookResponse = (BmgBookResponse) model.getBody();

        logger.info("PUT Bmg updateBookingStatus Response : {}", new Gson().toJson(bmgBookResponse));
        logger.info(model.toString());

        return bmgBookResponse;
    }

    public BeMyGuestCheckBookingRS getProductPrice(BeMyGuestCheckBookingRQ tobeMyGuestCheckBookingRQ) {

        logger.info("POST BeMyGuest Booking Check Price Request {}", new Gson().toJson(tobeMyGuestCheckBookingRQ));
        
        BeMyGuestCheckBookingRS beMyGuestCheckBookingRS = null;
        ResponseEntity<BeMyGuestCheckBookingRS> beMyGuestCheckBookingResponse = null;
        
        HttpHeaders headers = getHttpHeaders(HttpMethod.POST);
        HttpEntity<BeMyGuestCheckBookingRQ> request = new HttpEntity<BeMyGuestCheckBookingRQ>(tobeMyGuestCheckBookingRQ,
                headers);        
        beMyGuestCheckBookingResponse = restTemplate.postForEntity(restServiceConfig.getBeMyGuestPriceUrl(),
                request, BeMyGuestCheckBookingRS.class);
        if (beMyGuestCheckBookingResponse != null) {
            beMyGuestCheckBookingRS = beMyGuestCheckBookingResponse.getBody();
        }

        logger.info("POST BeMyGuest Booking Check Price Response : {}", new Gson().toJson(beMyGuestCheckBookingRS));
        return beMyGuestCheckBookingRS;
    }

    public BmgGetBookingStatusResponse getVoucherDetails(Map<String, Object> urlVariables) {

        logger.info("GET BmgGetBookingStatus Request : {}", urlVariables);

        HttpHeaders headers = getHttpHeaders(HttpMethod.GET);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<BmgGetBookingStatusResponse> model = restTemplate.exchange(
                restServiceConfig.getBeMyGuestGetBookingStatusUrl(), HttpMethod.GET, entity,
                BmgGetBookingStatusResponse.class, urlVariables);
        BmgGetBookingStatusResponse getBookingStatusResponse = (BmgGetBookingStatusResponse) model.getBody();

        logger.info("GET BmgGetBookingStatus Response : {}", new Gson().toJson(getBookingStatusResponse));
        logger.info(model.toString());

        return getBookingStatusResponse;

    }
    
    private HttpHeaders getHttpHeaders(HttpMethod httpMethod) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authorization", restServiceConfig.getBeMyGuestApiKey());
        if (!HttpMethod.GET.equals(httpMethod)) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        return headers;
    }

}