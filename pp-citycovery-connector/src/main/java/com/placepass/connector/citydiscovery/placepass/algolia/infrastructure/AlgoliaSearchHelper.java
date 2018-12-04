package com.placepass.connector.citydiscovery.placepass.algolia.infrastructure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.placepass.connector.citydiscovery.domain.placepass.algolia.product.AlgoliaProductCatalog;
import com.placepass.connector.citydiscovery.domain.placepass.algolia.product.AlgoliaProductDetail;
import com.placepass.connector.citydiscovery.domain.placepass.algolia.product.AlgoliaProductLocationIndex;
import com.placepass.connector.citydiscovery.domain.placepass.algolia.product.AlgoliaProductWebLocation;
import com.placepass.connector.citydiscovery.domain.placepass.algolia.product.GetAlgoliaProductDetailsRS;

public final class AlgoliaSearchHelper {

    public static GetAlgoliaProductDetailsRS getProductDetails(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        GetAlgoliaProductDetailsRS getProductDetailsRS = null;
        try {
            getProductDetailsRS = mapper.readValue(jsonString, GetAlgoliaProductDetailsRS.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getProductDetailsRS;
    }

    public static AlgoliaProductCatalog getProductCatalog(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        AlgoliaProductCatalog productCatalog = null;
        try {
            productCatalog = mapper.readValue(jsonString, AlgoliaProductCatalog.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productCatalog;
    }

    public static GetAlgoliaProductDetailsRS merge(AlgoliaProductDetail detail, AlgoliaProductCatalog productCatalog,
            GetAlgoliaProductDetailsRS getProductDetailsRS) {
        ModelMapper modelMapper = new ModelMapper();
        getProductDetailsRS = modelMapper.map(detail, GetAlgoliaProductDetailsRS.class);
        getProductDetailsRS.setCategory(productCatalog.getCategory());
        getProductDetailsRS.setTriedAndTrueGuarantee(productCatalog.getTriedAndTrueGuarantee());
        getProductDetailsRS.setRewardPoints(productCatalog.getRewardPoints());

        return getProductDetailsRS;
    }

    public static GetAlgoliaProductDetailsRS mergeDetailCatalog(String catalog, String detail) {

        GetAlgoliaProductDetailsRS getProductDetailsRS = getProductDetails(detail);
        ObjectMapper mapper = new ObjectMapper();
        AlgoliaProductCatalog productCatalog = null;
        try {
            productCatalog = mapper.readValue(catalog, AlgoliaProductCatalog.class);
            getProductDetailsRS.setCategory(productCatalog.getCategory());
            getProductDetailsRS.setTriedAndTrueGuarantee(productCatalog.getTriedAndTrueGuarantee());
            getProductDetailsRS.setRewardPoints(productCatalog.getRewardPoints());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getProductDetailsRS;

    }

    public static GetAlgoliaProductDetailsRS merge(AlgoliaProductCatalog productCatalog,
            GetAlgoliaProductDetailsRS getProductDetailsRS) {
        getProductDetailsRS.setCategory(productCatalog.getCategory());
        getProductDetailsRS.setTriedAndTrueGuarantee(productCatalog.getTriedAndTrueGuarantee());
        getProductDetailsRS.setRewardPoints(productCatalog.getRewardPoints());
        return getProductDetailsRS;
    }

    public static List<AlgoliaProductWebLocation> getWebLocations(String locationString) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(new TypeReference<List<AlgoliaProductLocationIndex>>() {
        });
        JsonNode rootNode;
        List<AlgoliaProductLocationIndex> locations = null;
        List<AlgoliaProductWebLocation> webLocations = new ArrayList<>();
        try {
            rootNode = mapper.readTree(locationString);
            JsonNode resultsNode = rootNode.get("results");
            locations = reader.readValue(resultsNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (AlgoliaProductLocationIndex locationIndex : locations) {
            AlgoliaProductWebLocation webLocation = null;
            if (locationIndex != null) {
                webLocation = new AlgoliaProductWebLocation();
                webLocation.setAddressType(locationIndex.getAddressType());
                webLocation.setCity(locationIndex.getCity());
                webLocation.setCountry(locationIndex.getCountry());
                webLocation.setCountryWebId(locationIndex.getCountryWebId());
                webLocation.setLocation(locationIndex.getLocation());
                webLocation.setPlaceId(locationIndex.getPlaceId());
                webLocation.setPlacePassRegion(locationIndex.getPlacePassRegion());
                webLocation.setPlacePassRegionWebId(locationIndex.getPlacePassRegionWebId());
                webLocation.setRegion(locationIndex.getRegion());
                webLocation.setRegionWebId(locationIndex.getRegionWebId());
                webLocation.setWebId(locationIndex.getWebId());
                if (locationIndex.get_geoloc() != null) {
                    webLocation.setLat(locationIndex.get_geoloc().getLat());
                    webLocation.setLng(locationIndex.get_geoloc().getLng());
                }
            }

            webLocations.add(webLocation);
        }
        return webLocations;
    }

    public static AlgoliaProductWebLocation getUniqueWebLocation(List<AlgoliaProductWebLocation> webLocations) {
        for (AlgoliaProductWebLocation webLocation : webLocations)
            if (webLocation != null)
                return webLocation;
        return null;

    }

    /*
     * public static List<GetHotelDetailsRS> getHotelDetails(String response) { ObjectMapper mapper = new
     * ObjectMapper(); ObjectReader reader = mapper.readerFor(new TypeReference<List<GetHotelDetailsRS>>() { });
     * JsonNode rootNode; List<GetHotelDetailsRS> hotels = null; try { rootNode = mapper.readTree(response); JsonNode
     * resultsNode = rootNode.get("hits"); hotels = reader.readValue(resultsNode); } catch (IOException e) {
     * e.printStackTrace(); } return hotels; }
     */

}