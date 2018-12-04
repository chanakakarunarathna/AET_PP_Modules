package com.viator.connector.placepass.algolia.infrastructure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.viator.connector.domain.placepass.algolia.product.AlgoliaProductCatalog;
import com.viator.connector.domain.placepass.algolia.product.AlgoliaProductDetail;
import com.viator.connector.domain.placepass.algolia.product.AlgoliaProductLocationIndex;
import com.viator.connector.domain.placepass.algolia.product.AlgoliaProductWebLocation;
import com.viator.connector.domain.placepass.algolia.product.GetAlgoliaProductDetailsRS;

public final class AlgoliaSearchHelper {

    private static Logger logger = LoggerFactory.getLogger(AlgoliaSearchHelper.class);

    private static ObjectMapper objMapper;

    static {
        objMapper = getObjectMapper();
    }

    public static GetAlgoliaProductDetailsRS getProductDetails(String jsonString) {
        GetAlgoliaProductDetailsRS getProductDetailsRS = null;
        try {
            getProductDetailsRS = objMapper.readValue(jsonString, GetAlgoliaProductDetailsRS.class);
        } catch (IOException e) {
            logger.error("Product Details Mapping ERROR : " + e);
        }
        return getProductDetailsRS;
    }

    public static AlgoliaProductCatalog getProductCatalog(String jsonString) {
        AlgoliaProductCatalog productCatalog = null;
        try {
            productCatalog = objMapper.readValue(jsonString, AlgoliaProductCatalog.class);
        } catch (IOException e) {
            logger.error("Product Catalog Mapping ERROR : " + e);
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
        AlgoliaProductCatalog productCatalog = null;
        try {
            productCatalog = objMapper.readValue(catalog, AlgoliaProductCatalog.class);
            getProductDetailsRS.setCategory(productCatalog.getCategory());
            getProductDetailsRS.setTriedAndTrueGuarantee(productCatalog.getTriedAndTrueGuarantee());
            getProductDetailsRS.setRewardPoints(productCatalog.getRewardPoints());
        } catch (IOException e) {
            logger.error("Product Details Catalog Merging Mapping ERROR : " + e);
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
        ObjectReader reader = objMapper.readerFor(new TypeReference<List<AlgoliaProductLocationIndex>>() {
        });
        JsonNode rootNode;
        List<AlgoliaProductLocationIndex> locations = null;
        List<AlgoliaProductWebLocation> webLocations = new ArrayList<>();
        try {
            rootNode = objMapper.readTree(locationString);
            JsonNode resultsNode = rootNode.get("results");
            locations = reader.readValue(resultsNode);
        } catch (IOException e) {
            logger.error("Web Locations Mapping ERROR : " + e);
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

    public static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}