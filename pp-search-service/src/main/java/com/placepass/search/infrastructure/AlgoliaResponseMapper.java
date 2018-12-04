package com.placepass.search.infrastructure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.placepass.search.application.search.response.SearchResponse;
import com.placepass.search.application.search.response.SearchResponseCatalog;
import com.placepass.search.application.search.response.ShelvesResponse;
import com.placepass.search.application.search.response.FacetItem;
import com.placepass.search.application.search.response.Facets;
import com.placepass.search.application.search.response.Product;
import com.placepass.search.application.search.response.ProductCatalog;

public final class AlgoliaResponseMapper {

    public static SearchResponseCatalog getPaginatedResponse(String productsJsonString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode;
        SearchResponseCatalog res = null;
        try {
            rootNode = mapper.readTree(productsJsonString);
            res = mapper.readValue(productsJsonString, SearchResponseCatalog.class);
            JsonNode facetNodes = rootNode.path("facets");
            JsonNode groupNode = facetNodes.path("Group");
            JsonNode subGroupNode = facetNodes.path("SubGroup");
            JsonNode formattedAddressNode = facetNodes.path("FormattedAddress");
            JsonNode vendorNode = facetNodes.path("Vendor");
            JsonNode avgRatingNode = facetNodes.path("AvgRating");
            JsonNode reviewsNode = facetNodes.path("Reviews");
            JsonNode classificationNode = facetNodes.path("Classifications");
            JsonNode availableDatesNode = facetNodes.path("AvailableDays");

            Facets facets = new Facets();
            facets.setGroup(getFromJsonNode(groupNode));
            facets.setSubGroup(getFromJsonNode(subGroupNode));
            facets.setFormattedAddress(getFromJsonNode(formattedAddressNode));
            facets.setVendor(getFromJsonNode(vendorNode));
            facets.setAvgRating(getFromJsonNode(avgRatingNode));
            facets.setReviews(getFromJsonNode(reviewsNode));
            facets.setClassifications(getFromJsonNode(classificationNode));
            facets.setAvailableDays(getFromJsonNode(availableDatesNode));
            res.setFacets(facets);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static List<ProductCatalog> getProductsResponse(String productsJsonString) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(new TypeReference<List<ProductCatalog>>() {
        });
        JsonNode rootNode;
        List<ProductCatalog> products = null;
        try {
            rootNode = mapper.readTree(productsJsonString);
            JsonNode resultsNode = rootNode.get("results");
            products = reader.readValue(resultsNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Deprecated
    public static ShelvesResponse toShelvesResponse(String jsonString, String partnerId) {
        ShelvesResponse res = null;
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(new TypeReference<ShelvesResponse>() {
        });
        JsonNode rootNode;
        try {
            rootNode = mapper.readTree(jsonString);
            res = reader.readValue(rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (res.getPartnerId().equals(partnerId))
            return res;
        return null;
    }

    public static List<FacetItem> getFromJsonNode(JsonNode node) {
        List<FacetItem> facetItemList = new ArrayList<>();
        Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()) {
            FacetItem fItem = new FacetItem();
            String fieldName = fieldNames.next();
            JsonNode fieldValue = node.get(fieldName);
            String value = fieldValue.asText();
            fItem.setKey(fieldName);
            fItem.setValue(value);
            facetItemList.add(fItem);
        }
        return facetItemList;
    }

    public static void removeSearchedProduct(String productId, SearchResponse res) {

        List<Product> productList = res.getHits();
        Iterator<Product> it = productList.iterator();
        while (it.hasNext()) {
            Product product = it.next();
            if (product.getObjectID().equals(productId)) {
                it.remove();
                Integer hitsCount = res.getNbHits();
                Integer changedCount = hitsCount - 1;
                res.setNbHits(changedCount);
            }
        }
    }

    public static void cleanProducts(List<Product> products) {
        products.removeAll(Collections.singleton(null));
    }

    public static SearchResponse filterResponse(SearchResponseCatalog resCatalog, String partnerId) {

        ModelMapper modelMapper = new ModelMapper();
        SearchResponse res = modelMapper.map(resCatalog, SearchResponse.class);
        int count = -1;
        for (ProductCatalog hit : resCatalog.getHits()) {
            count++;
            if (hit.getPartnerProperties() != null) {
                if (hit.getPartnerProperties().get(partnerId) != null) {
                    if (hit.getPartnerProperties().get(partnerId).size() > 0) {
                        res.getHits().get(count).setLoyaltyDetails(hit.getPartnerProperties().get(partnerId));
                    }
                }
            }
        }
        return res;
    }

    public static List<Product> filterLists(List<ProductCatalog> resCatalog, List<Product> res, String partnerId) {
        ModelMapper modelMapper = new ModelMapper();
        res = new ArrayList<>();
        int count = -1;
        for (ProductCatalog hit : resCatalog) {
            if (hit != null) {
                Product toMap = modelMapper.map(hit, Product.class);
                res.add(toMap);
                count++;
                if (hit.getPartnerProperties() != null) {
                    if (hit.getPartnerProperties().get(partnerId) != null) {
                        if (hit.getPartnerProperties().get(partnerId).size() > 0) {
                            res.get(count).setLoyaltyDetails(hit.getPartnerProperties().get(partnerId));
                        }
                    }
                }
            }
        }
        return res;
    }
}
