package com.placepass.product.infrastructure;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    /**
     * Rest template timeout configurations
     */
    @Value("${resttemplate.connection.timeout}")
    private int connectionTimeOut;

    @Value("${resttemplate.read.timeout}")
    private int readTimeOut;
    
    @Value("${resttemplate.connection.maxperroute}")
    private int maxPerRoute;

    @Value("${resttemplate.connection.maxtotal}")
    private int maxTotal;

    @Value("${location.data.baseurl}")
    private String locationBaseUrl;

    @Value("${discount.service.baseurl}")
    private String discountServiceBaseUrl;

    @Value("${discount.service.fee.url}")
    private String discountServiceFeeUrl;

    @Bean(name = "restTemplate")
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

    public String getLocationBaseUrl() {
        return locationBaseUrl;
    }

    public void setLocationBaseUrl(String locationBaseUrl) {
        this.locationBaseUrl = locationBaseUrl;
    }

    public String getDiscountServiceFeeUrl() {
        return discountServiceBaseUrl + discountServiceFeeUrl;
    }
}
