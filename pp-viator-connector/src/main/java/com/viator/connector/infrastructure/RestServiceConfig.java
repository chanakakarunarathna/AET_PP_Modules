package com.viator.connector.infrastructure;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestServiceConfig {

    @Value("${viator.baseurl}")
    private String viatorBaseUrl;

    @Value("${viator.product.url}")
    private String viatorProductUrl;

    @Value("${viator.availability.url}")
    private String viatorAvailabilityUrl;

    @Value("${viator.pricingmatrix.url}")
    private String viatorPricingMatrixUrl;

    @Value("${viator.availability.tourgrades.url}")
    private String viatorAvailabilityTourgradesUrl;

    @Value("${viator.booking.book.url}")
    private String viatorBookingBookUrl;

    @Value("${viator.booking.status.url}")
    private String viatorBookingStatusUrl;
    
    @Value("${viator.booking.calculateprice.url}")
    private String viatorBookingCalculatePriceUrl;

    @Value("${viator.product.reviews.url}")
    private String viatorProductReviewsUrl;

    @Value("${viator.bookingquestions.url}")
    private String viatorBookingQuestionsUrl;

    @Value("${viator.tourgrades.pricingmatrix.url}")
    private String viatorTourgradesPricingMatrixUrl;

    @Value("${viator.booking.mybookings.url}")
    private String viatorMybookingsUrl;

    @Value("${viator.booking.cancel.url}")
    private String viatorCancelBookingUrl;

    @Value("${viator.booking.mybookings.itineraryId.url}")
    private String viatorBookingItineraryIdUrl;

    /**
     * Rest template timeout configurations
     */
    @Value("${resttemplate.connection.timeout}")
    private int connectionTimeOut;

    @Value("${resttemplate.read.timeout}")
    private int readTimeOut;

    @Value("${resttemplate.connection.maxperroute}")
    private int maxPerRoute;

    @Value("${resttemplate.con.maxtotal}")
    private int maxTotal;

    @Bean(name = "RestTemplate")
    public RestTemplate restTemplate() {

        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);
        poolingHttpClientConnectionManager.setMaxTotal(maxTotal);

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager).build());
        requestFactory.setConnectTimeout(connectionTimeOut);
        requestFactory.setReadTimeout(readTimeOut);
        return new RestTemplate(requestFactory);
    }

    public String getviatorBaseUrl() {
        return viatorBaseUrl;
    }

    public String getViatorProductUrl() {
        return viatorBaseUrl + viatorProductUrl;
    }

    public String getViatorAvailabilityUrl() {
        return viatorBaseUrl + viatorAvailabilityUrl;
    }

    public String getViatorPricingmatrixUrl() {
        return viatorPricingMatrixUrl;
    }

    public String getViatorTourgradesPricingMatrixUrl() {
        return viatorBaseUrl + viatorTourgradesPricingMatrixUrl;
    }

    public String getViatorAvailabilityTourgradesUrl() {
        return viatorBaseUrl + viatorAvailabilityTourgradesUrl;
    }

    public String getViatorBookingBookUrl() {
        return viatorBaseUrl + viatorBookingBookUrl;
    }

    public String getViatorBookingStatusUrl(){
        return viatorBaseUrl + viatorBookingStatusUrl;
    }
    
    public String getViatorBookingCalculatePriceUrl() {
        return viatorBaseUrl + viatorBookingCalculatePriceUrl;
    }

    public String getViatorProductReviewsUrl() {
        return viatorBaseUrl + viatorProductReviewsUrl;
    }

    public String getViatorBookingQuestionsUrl() {
        return viatorBaseUrl + viatorBookingQuestionsUrl;
    }

    public String getViatorMybookingsUrl() {
        return viatorBaseUrl + viatorMybookingsUrl;
    }

    public String getViatorCancelBookingUrl() {
        return viatorBaseUrl + viatorCancelBookingUrl;
    }

    public String getViatorBookingItineraryIdUrl() {
        return viatorBaseUrl + viatorBookingItineraryIdUrl;
    }

}