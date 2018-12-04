package com.placepass.connector.bemyguest.placepass.algolia.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.placepass.connector.bemyguest.domain.placepass.algolia.product.AlgoliaProductCatalog;
import com.placepass.connector.bemyguest.domain.placepass.algolia.product.AlgoliaProductDetail;
import com.placepass.connector.bemyguest.domain.placepass.algolia.product.AlgoliaProductWebLocation;
import com.placepass.connector.bemyguest.domain.placepass.algolia.product.GetAlgoliaProductDetailsRS;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;

@Component
public class AlgoliaSearchClient {

    private AlgoliaExtensions algoliaExtensions;

    private PlacePassIndices placePassIndices;

    @Autowired
    public AlgoliaSearchClient(AlgoliaExtensions algoliaExtensions, PlacePassIndices placePassIndices) {
        this.algoliaExtensions = algoliaExtensions;
        this.placePassIndices = placePassIndices;
    }

    public GetAlgoliaProductDetailsRS getProductDetails(String productId) {

        // Get info from ProductCatalog
        Index index = algoliaExtensions.initializeAlgolia(placePassIndices.getPpProductCatalog());
        JSONObject productCatalogJson = algoliaExtensions.getObjectById(index, productId);
        GetAlgoliaProductDetailsRS getProductDetailsRS = new GetAlgoliaProductDetailsRS();
        if (productCatalogJson == null) {
            throw new NotFoundException(PlacePassExceptionCodes.PRODUCT_ID_NOT_FOUND.toString(),
                    PlacePassExceptionCodes.PRODUCT_ID_NOT_FOUND.getDescription());
        } else {
            AlgoliaProductCatalog productCatalog = AlgoliaSearchHelper.getProductCatalog(productCatalogJson.toString());
            AlgoliaProductDetail detail = productCatalog.getDetails();
            if (detail == null) {
                throw new NotFoundException(PlacePassExceptionCodes.PRODUCT_ID_NOT_FOUND.toString(),
                        PlacePassExceptionCodes.PRODUCT_ID_NOT_FOUND.getDescription());
            } else {
                getProductDetailsRS = AlgoliaSearchHelper.merge(detail, productCatalog, getProductDetailsRS);
                index = algoliaExtensions.initializeAlgolia(placePassIndices.getPpLocations());
                JSONObject locationsJson = algoliaExtensions.getObjectsByIds(index, productCatalog.getGeoLocationIds());
                List<AlgoliaProductWebLocation> webLocations = AlgoliaSearchHelper
                        .getWebLocations(locationsJson.toString());
                AlgoliaProductWebLocation finalWebLocation = AlgoliaSearchHelper.getUniqueWebLocation(webLocations);
                getProductDetailsRS.setWebLocation(finalWebLocation);

            }
        }
        return getProductDetailsRS;
    }

    private Query queryFacetsAndFilters(String vendorProductId) {
        Query q = new Query();
        List<String> vendorIdFacet = new ArrayList<String>();
        String facetFilterString = "productCodes:" + vendorProductId;
        vendorIdFacet.add(facetFilterString);
        q.setFacetFilters(vendorIdFacet);
        return q;
    }
}
