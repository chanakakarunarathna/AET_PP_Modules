package com.placepass.product.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.algolia.search.saas.APIClient;

@Component
public class AlgoliaConfig {

    @Value("${algolia.applicationId}")
    private String applicationId;

    @Value("${algolia.adminApiKey}")
    private String adminApiKey;
    
    @Value("${algolia.client.http.max.connections}")
    private String algoliaMaxConnections;

    @Bean("algoliaAPIClient")
	public APIClient getAPIClient() {
        System.setProperty("http.maxConnections", algoliaMaxConnections);
    	
		APIClient client = new APIClient(applicationId, adminApiKey);
		return client;
	}
    
    public String getApplicationId() {
        return applicationId;
    }

    public String getAdminApiKey() {
        return adminApiKey;
    }
    
    
}
