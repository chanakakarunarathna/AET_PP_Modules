package com.placepass.product.infrastructure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.placepass.exutil.NotFoundException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.product.application.hoteldetails.dto.GetHotelDetailsRS;
import com.placepass.product.application.hoteldetails.dto.HotelDetail;
import com.placepass.product.application.productdetails.dto.GetProductDetailsRS;
import com.placepass.product.application.productdetails.dto.LocationIndex;
import com.placepass.product.application.productdetails.dto.ProductCatalog;
import com.placepass.product.application.productdetails.dto.ProductDetail;
import com.placepass.product.application.productdetails.dto.WebLocation;

public final class AlgoliaSearchHelper {

    public static ProductCatalog getProductCatalog(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        ProductCatalog productCatalog = null;
        try {
            productCatalog = mapper.readValue(jsonString, ProductCatalog.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productCatalog;
    }

    public static GetProductDetailsRS merge(ProductDetail detail, ProductCatalog productCatalog,
            GetProductDetailsRS getProductDetailsRS, String partnerId) {
        ModelMapper modelMapper = new ModelMapper();
        getProductDetailsRS = modelMapper.map(detail, GetProductDetailsRS.class);
        getProductDetailsRS.setCategory(productCatalog.getCategory());
        getProductDetailsRS.setTriedAndTrueGuarantee(productCatalog.getTriedAndTrueGuarantee());
        getProductDetailsRS.setRewardPoints(productCatalog.getRewardPoints());
        if (detail.getProductDetailDeeplink()) {
            getProductDetailsRS.setProductUrl(productCatalog.getProductUrl());
        }
        if(productCatalog.getPartnerProperties()!=null){
            if(productCatalog.getPartnerProperties().get(partnerId)!=null){
                if(productCatalog.getPartnerProperties().get(partnerId).size()>0){
                    getProductDetailsRS.setLoyaltyDetails(productCatalog.getPartnerProperties().get(partnerId));
                }
            }
        }
        return getProductDetailsRS;
    }


    public static GetHotelDetailsRS getHotelDetails(String response) {
        GetHotelDetailsRS hotelDetailsRS = new GetHotelDetailsRS();
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(new TypeReference<List<HotelDetail>>() {
        });
        JsonNode rootNode;
        List<HotelDetail> hotels = null;
        try {
            rootNode = mapper.readTree(response);
            JsonNode resultsNode = rootNode.get("hits");
            hotels = reader.readValue(resultsNode);
            if (hotels.size() == 0) {
                throw new NotFoundException(PlacePassExceptionCodes.HOTEL_PICKUP_UNAVAILABLE.toString(),
                        PlacePassExceptionCodes.HOTEL_PICKUP_UNAVAILABLE.getDescription());
            }
            hotelDetailsRS.setHotelDetails(hotels);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hotelDetailsRS;
    }

}