package com.placepass.connector.bemyguest.infrastructure;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestServiceConfig {

    @Value("${bemyguest.baseurl}")
    private String beMyGuestBaseUrl;
    
    @Value("${bemyguest.apiKey}")
    private String beMyGuestApiKey;

    @Value("${bemyguest.product.url}")
    private String beMyGuestProductUrl;

    @Value("${bemyguest.booking.book.url}")
    private String beMyGuestBookingBookUrl;

    @Value("${bemyguest.booking.updateStatus}")
    private String beMyGuestBookingUpdateStatusUrl;

    @Value("${bemyguest.booking.checkbooking.url}")
    private String beMyGuestPriceUrl;

    @Value("${bemyguest.booking.getBookingStatus.url}")
    private String beMyGuestGetBookingStatusUrl;

    /**
     * Rest template timeout configurations
     */
    @Value("${resttemplate.connection.timeout}")
    private int connectionTimeOut;

    @Value("${resttemplate.read.timeout}")
    private int readTimeOut;

    @Bean(name = "RestTemplate")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(readTimeOut);
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(connectionTimeOut);
        return restTemplate;
    }

    public String getBeMyGuestBaseUrl() {
        return beMyGuestBaseUrl;
    }

    public String getBeMyGuestApiKey() {
        return beMyGuestApiKey;
    }

    public String getBeMyGuestProductUrl() {
        return beMyGuestBaseUrl + beMyGuestProductUrl;
    }

    public String getBeMyGuestBookingBookUrl() {
        return beMyGuestBaseUrl + beMyGuestBookingBookUrl;
    }

    public String getBeMyGuestBookingUpdateStatusUrl() {
        return beMyGuestBaseUrl + beMyGuestBookingUpdateStatusUrl;
    }

    public String getBeMyGuestPriceUrl() {
        return beMyGuestBaseUrl + beMyGuestPriceUrl;
    }

    public String getBeMyGuestGetBookingStatusUrl() {
        return beMyGuestBaseUrl + beMyGuestGetBookingStatusUrl;
    }

}