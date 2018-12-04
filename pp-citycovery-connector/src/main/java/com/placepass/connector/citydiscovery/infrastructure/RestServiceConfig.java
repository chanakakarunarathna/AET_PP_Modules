package com.placepass.connector.citydiscovery.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestServiceConfig {

    @Value("${citydiscovery.baseurl}")
    private String discoveryBaseUrl;

    @Value("${citydiscovery.agentsine}")
    private String discoveryAgentSine;

    @Value("${citydiscovery.agentdutycode}")
    private String discoveryAgentDutyCode;

    @Value("${citydiscovery.azure.headervalue}")
    private String azureHeaderValue;
    
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

    public String getDiscoveryBaseUrl() {
        return discoveryBaseUrl;
    }

    public String getAzureHeaderValue() {
        return azureHeaderValue;
    }

    public String getDiscoveryAgentSine() {
        return discoveryAgentSine;
    }

    public String getDiscoveryAgentDutyCode() {
        return discoveryAgentDutyCode;
    }

}