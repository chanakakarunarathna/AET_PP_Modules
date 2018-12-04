package com.gobe.connector.infrastructure;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableOAuth2Client
public class Oauth2RestConfig {

    @Value("${gobe.oAuth2ClientId}")
    private String oAuth2ClientId;

    @Value("${gobe.oAuth2ClientSecret}")
    private String oAuth2ClientSecret;

    @Value("${gobe.accessTokenUri}")
    private String accessTokenUri;

    @Value("${gobe.oAuth2ClientUsername}")
    private String oAuth2ClientUsername;

    @Value("${gobe.oAuth2ClientPassword}")
    private String oAuth2ClientPassword;

    @Value("${gobe.azure.headervalue}")
    private String gobeAzureHeaderValue;
    
    @Value("${resttemplate.connection.timeout}")
    private int connectionTimeOut;

    @Value("${resttemplate.read.timeout}")
    private int readTimeOut;

    @Value("${resttemplate.connection.maxperroute}")
    private int maxPerRoute;
    
    @Value("${resttemplate.connection.maxtotal}")
    private int maxTotal;
    
    @Bean(name = "oAuth2RestTemplate")
    @Primary
    public OAuth2RestTemplate oAuth2RestTemplate() {
        //Password grant type strategy
        ResourceOwnerPasswordResourceDetails resourceDetailsPassword = new ResourceOwnerPasswordResourceDetails();
        resourceDetailsPassword.setClientId(oAuth2ClientId);
        resourceDetailsPassword.setClientSecret(oAuth2ClientSecret);
        resourceDetailsPassword.setAccessTokenUri(accessTokenUri);
        resourceDetailsPassword.setUsername(oAuth2ClientUsername);
        resourceDetailsPassword.setPassword(oAuth2ClientPassword);

        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetailsPassword, new DefaultOAuth2ClientContext());
        //Use HttpClient instead of RestTemplate SimpleHttp for making https calls
        
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);
        poolingHttpClientConnectionManager.setMaxTotal(maxTotal);
        
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager).build());
        requestFactory.setConnectTimeout(connectionTimeOut);
        requestFactory.setReadTimeout(readTimeOut);

        //Adding header for all calls to azure
        //Note the access token call will not be intercepted here. therefore we pass the Subscription Key in the Access Token URL query param
        restTemplate.setInterceptors(Collections.singletonList(new AzureRequestInterceptor("Ocp-Apim-Subscription-Key", gobeAzureHeaderValue)));

        restTemplate.setRequestFactory(requestFactory);

        return restTemplate;
    }

    @Value("${gobe.baseurl}")
    private String gobeBaseUrl;

    @Value("${gobe.schedule.url}")
    private String gobeScheduleUrl;

    @Value("${gobe.products.url}")
    private String gobeProductsUrl;

    @Value("${gobe.images.url}")
    private String gobeImagesUrl;

    @Value("${gobe.inventory.check.url}")
    private String gobeInventoryCheckUrl;

    @Value("${gobe.prices.url}")
    private String gobePriciesUrl;

    @Value("${gobe.booking.order.url}")
    private String gobeBookingOrderUrl;

    @Value("${gobe.product.reviews.url}")
    private String gobeProductReviewsUrl;

    @Value("${gobe.booking.cancel.url}")
    private String gobeCancelBookingUrl;

    @Value("${gobe.booking.status.url}")
    private String gobeBookingStatusUrl;


    public String getGobeBaseUrl() {
        return gobeBaseUrl;
    }

    public String getGobeScheduleUrl() {
        return gobeBaseUrl + gobeScheduleUrl;
    }

    public String getGobeProductsUrl() {
        return gobeBaseUrl + gobeProductsUrl;
    }

    public String getgobeInventoryCheckUrl() {
        return gobeBaseUrl + gobeInventoryCheckUrl;
    }

    public String getGobePriciesUrl() {
        return gobeBaseUrl + gobePriciesUrl;
    }

    public String getGobeBookingOrderUrl() {
        return gobeBaseUrl + gobeBookingOrderUrl;
    }

    public String getGobeProductReviewsUrl() {
        return gobeBaseUrl + gobeProductReviewsUrl;
    }

    public String getGobeCancelBookingUrl() {
        return gobeBaseUrl + gobeCancelBookingUrl;
    }

    public String getGobeBookingStatusUrl() {
        return gobeBaseUrl + gobeBookingStatusUrl;
    }

    public String getGobeImagesUrl() {
        return gobeBaseUrl + gobeImagesUrl;
    }

}
