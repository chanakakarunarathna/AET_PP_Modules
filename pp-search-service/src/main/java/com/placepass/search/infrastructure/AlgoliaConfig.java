package com.placepass.search.infrastructure;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.algolia.search.saas.APIClient;
import com.placepass.exutil.BadRequestException;
import com.placepass.search.application.search.request.ProductSearchRequest;

@Component
public class AlgoliaConfig {

    @Value("${algolia.applicationId}")
    private String applicationId;

    @Value("${algolia.adminApiKey}")
    private String adminApiKey;
    
    @Value("#{'${placepass.partnerids}'.split(',')}")
    private List<String> partnerIds;
    
    @Value("${algolia.client.http.max.connections}")
    private String algoliaMaxConnections;
    
    @Bean("algoliaAPIClient")
    public APIClient getAPIClient() {
        System.setProperty("http.maxConnections", algoliaMaxConnections);
        
        APIClient client = new APIClient(applicationId, adminApiKey);
        return client;
    }

    public List<String> getPartnerIds() {
        return partnerIds;
    }

    public void setPartnerIds(List<String> partnerIds) {
        this.partnerIds = partnerIds;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getAdminApiKey() {
        return adminApiKey;
    }

    public static <T> String getIndexName(T object, ProductSearchRequest psr) {
        if (object instanceof PlacePassIndices) {
            return getPlacepassIndex((PlacePassIndices) object, psr);
        }
        return null;
    }

    private static String getPlacepassIndex(PlacePassIndices pObject, ProductSearchRequest psr) {
        String indexName = null;
        if (psr.getSortBy() != null) {
            switch (psr.getSortBy()) {
                case recommended:
                    indexName = pObject.getPpSearchIndexRecommended();
                    break;
                case topRated:
                    indexName = pObject.getPpSearchIndexTopRated();
                    break;
                case popularity:
                    indexName = pObject.getPpSearchIndexPopularity();
                    break;
                case priceLowHigh:
                    indexName = pObject.getPpSearchIndexPriceLowHigh();
                    break;
                case priceHighLow:
                    indexName = pObject.getPpSearchIndexPriceHighLow();
                    break;
                default:
                    throw new BadRequestException(HttpStatus.BAD_REQUEST.name(), "Invalid SortBy field");
            }
        } else {
            indexName = pObject.getPpSearchIndex();
        }
        return indexName;
    }

 
}
