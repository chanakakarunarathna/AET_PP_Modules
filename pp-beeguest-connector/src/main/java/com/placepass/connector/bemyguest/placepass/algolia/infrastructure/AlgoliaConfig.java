package com.placepass.connector.bemyguest.placepass.algolia.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlgoliaConfig {

    @Value("${algolia.applicationId}")
    private String applicationId;

    @Value("${algolia.adminApiKey}")
    private String adminApiKey;

    public String getApplicationId() {
        return applicationId;
    }

    public String getAdminApiKey() {
        return adminApiKey;
    }
}
