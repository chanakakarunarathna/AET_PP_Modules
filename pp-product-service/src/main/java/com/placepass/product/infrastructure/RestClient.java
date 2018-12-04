package com.placepass.product.infrastructure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.placepass.product.application.pricematch.dto.GetPriceMatchRS;
import com.placepass.product.application.pricematch.dto.PriceSummary;
import com.placepass.product.application.productdetails.dto.Geolocation;
import com.placepass.product.application.productdetails.dto.WebLocation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRS;
import com.placepass.product.domain.product.ProductDetails;
import com.placepass.utils.vendorproduct.Vendor;

/**
 * 
 * NOTE: To be phased out once all vendor connector calls are made through RMQ. @see VendorProductGateway.
 */
@Deprecated
@Component
public class RestClient {

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private RestConfig restConfig;

    @Autowired
    private SimpleVendorConnectorRouter vendorRouter;

    public GetAvailabilityRS getAvailability(Map<String, Object> urlVariables, Vendor vendor) {
        GetAvailabilityRS availability = restTemplate.getForObject(vendorRouter.getAvailabilityUrl(vendor),
                GetAvailabilityRS.class, urlVariables);
        return availability;
    }

    public GetProductOptionsRS getProductOptions(Map<String, Object> urlVariables, Vendor vendor) {
        GetProductOptionsRS productOptions = restTemplate.getForObject(vendorRouter.getBookingoptionsUrl(vendor),
                GetProductOptionsRS.class, urlVariables);
        return productOptions;
    }

    public ProductDetails getProductDetails(Map<String, Object> urlVariables, Vendor vendor) {
        ProductDetails productDetails = restTemplate.getForObject(vendorRouter.getProductDetailsUrl(vendor),
                ProductDetails.class, urlVariables);

        return productDetails;
    }

    public GetProductReviewsRS getProductReviews(Map<String, Object> urlVariables, Vendor vendor) {
        GetProductReviewsRS productReviews = restTemplate.getForObject(vendorRouter.getProductReviewsUrl(vendor),
                GetProductReviewsRS.class, urlVariables);

        return productReviews;
    }

    public WebLocation getWebLocations(String locationId) {
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("id", locationId);

        String locationString = restTemplate.getForObject(restConfig.getLocationBaseUrl(),
                String.class, urlVariables);
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader mapLocationReader = mapper.readerFor(new TypeReference<WebLocation>() {});
        ObjectReader geolocationReader = mapper.readerFor(new TypeReference<Geolocation>() {});
        ObjectReader stringReader = mapper.readerFor(new TypeReference<String>() {});
        JsonNode rootNode;
        WebLocation webLocation = null;
        try {
            rootNode = mapper.readTree(locationString);
            webLocation = mapLocationReader.readValue(rootNode);
            JsonNode geoLocationNode = rootNode.get("geoLocation");
            if (geoLocationNode != null) {
                Geolocation geolocation = geolocationReader.readValue(geoLocationNode);
                webLocation.setLat(geolocation.getLat());
                webLocation.setLng(geolocation.getLng());
            }
            webLocation.setPlaceId(locationId);
            JsonNode locationTypeNode = rootNode.get("locationType");
            if (locationTypeNode != null && locationTypeNode.get("type") != null) {
                webLocation.setAddressType(stringReader.readValue(locationTypeNode.get("type")));
            }
            if (rootNode.get("formattedAddress") != null) {
                webLocation.setLocation(stringReader.readValue(rootNode.get("formattedAddress")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return webLocation;
    }

    public PriceSummary getFeeForPriceMatch(GetPriceMatchRS getPriceMatchRS, String partnerId) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("partner-id", partnerId);

        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("pricingDTO", getPriceMatchRS);

        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(requestBody, headers);
        PriceSummary priceSummary = restTemplate.postForObject(restConfig.getDiscountServiceFeeUrl(), request, PriceSummary.class);

        return priceSummary;
    }
}
