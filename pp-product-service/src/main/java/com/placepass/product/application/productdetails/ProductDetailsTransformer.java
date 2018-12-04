package com.placepass.product.application.productdetails;

import java.util.ArrayList;
import java.util.List;

import com.placepass.product.application.productdetails.dto.GetProductDetailsRS;
import com.placepass.product.application.productdetails.dto.LocationDTO;
import com.placepass.product.application.productdetails.dto.PriceDTO;
import com.placepass.product.application.productdetails.dto.SupplierDTO;
import com.placepass.product.domain.product.Location;
import com.placepass.product.domain.product.Price;
import com.placepass.product.domain.product.ProductDetails;
import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.currency.AmountFormatter;

public class ProductDetailsTransformer {

    public static GetProductDetailsRS toGetProductDetailsRS(ProductDetails productDetails) {
        List<LocationDTO> dropOffPoint = new ArrayList<>();
        List<LocationDTO> meetingPoint = new ArrayList<>();
        List<PriceDTO> prices = new ArrayList<>();
        SupplierDTO supplier = new SupplierDTO();
        LocationDTO supplierLocation = new LocationDTO();

        GetProductDetailsRS getProductDetailsRS = new GetProductDetailsRS();
        if (productDetails.getActivitySnapshots() != null) {
            getProductDetailsRS.setActivitySnapshots(productDetails.getActivitySnapshots());
        }
        getProductDetailsRS.setAvgRating(productDetails.getAverageRating());
        getProductDetailsRS.setDescription(productDetails.getDescription());
        if (productDetails.getDropOffPoint() != null) {
            getProductDetailsRS.setDropOffPoint(toLocationDTOList(productDetails, dropOffPoint));
        }
        // getProductDetailsRS.setDuration(productDetails.getDuration()); TODO: Change later
        getProductDetailsRS.setExclusions(productDetails.getExclusions());
        getProductDetailsRS.setHighlights(productDetails.getHiglights());
        getProductDetailsRS.setImages(productDetails.getImages());
        getProductDetailsRS.setInclusions(productDetails.getInclusions());
        getProductDetailsRS.setLanguageCode(productDetails.getLanguageCode());
        if (productDetails.getMeetingPoint() != null) {
            getProductDetailsRS.setMeetingPoint(toLocationDTOList(productDetails, meetingPoint));
        }
        for (Price price : productDetails.getPrices()) {
            PriceDTO priceDTO = new PriceDTO();
            priceDTO.setCurrencyCode(price.getCurrencyCode());
            priceDTO.setDescription(price.getDescription());
            priceDTO.setPriceType(price.getPriceType());
            if (PlacePassAgeBandType.ADULT.name().equals(price.getPriceType())) {
                priceDTO.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
            } else if (PlacePassAgeBandType.SENIOR.name().equals(price.getPriceType())) {
                priceDTO.setAgeBandId(PlacePassAgeBandType.SENIOR.ageBandId);
            } else if (PlacePassAgeBandType.CHILD.name().equals(price.getPriceType())) {
                priceDTO.setAgeBandId(PlacePassAgeBandType.CHILD.ageBandId);
            } else if (PlacePassAgeBandType.INFANT.name().equals(price.getPriceType())) {
                priceDTO.setAgeBandId(PlacePassAgeBandType.INFANT.ageBandId);
            }else if (PlacePassAgeBandType.YOUTH.name().equals(price.getPriceType())) {
                priceDTO.setAgeBandId(PlacePassAgeBandType.YOUTH.ageBandId);
            }
            priceDTO.setRetailPrice(price.getRetailPrice());
            priceDTO.setFinalPrice(price.getFinalPrice());
            priceDTO.setRoundedFinalPrice(AmountFormatter.floatToFloatRoundingFinalTotal(price.getFinalPrice()));
            prices.add(priceDTO);
        }

        getProductDetailsRS.setPrices(prices);
        getProductDetailsRS.setReviewCount(productDetails.getReviewCount());

        if (productDetails.getSupplier() != null) {
            supplier.setDescription(productDetails.getSupplier().getDescription());
            supplier.setEmail(productDetails.getSupplier().getEmail());
            supplier.setLocation(toLocationDTO(productDetails, supplierLocation));
            supplier.setPhoneNumber(productDetails.getSupplier().getPhoneNumber());
            supplier.setTitle(productDetails.getSupplier().getTitle());

            getProductDetailsRS.setSupplier(supplier);
        }
        getProductDetailsRS.setTitle(productDetails.getTitle());
        getProductDetailsRS.setVideos(productDetails.getVidoes());

        if (productDetails.getVoucherTypes() != null) {
            getProductDetailsRS.setVoucherTypes(productDetails.getVoucherTypes());
        }
        return getProductDetailsRS;

    }

    public static LocationDTO toLocationDTO(ProductDetails productDetails, LocationDTO locationDTO) {

        locationDTO.setCity(productDetails.getSupplier().getLocation().getCity());
        locationDTO.setCountry(productDetails.getSupplier().getLocation().getCountry());
        locationDTO.setDescription(productDetails.getSupplier().getLocation().getDescription());
        locationDTO.setExtendedAttributes(productDetails.getSupplier().getLocation().getExtendedAttributes());
        locationDTO.setLatitude(productDetails.getSupplier().getLocation().getLatitude());
        locationDTO.setLongitude(productDetails.getSupplier().getLocation().getLongitude());
        locationDTO.setPostalCode(productDetails.getSupplier().getLocation().getPostalCode());
        locationDTO.setState(productDetails.getSupplier().getLocation().getState());
        locationDTO.setStreet1(productDetails.getSupplier().getLocation().getStreet1());
        locationDTO.setStreet2(productDetails.getSupplier().getLocation().getStreet2());
        locationDTO.setTitle(productDetails.getSupplier().getLocation().getTitle());
        locationDTO.setWebSite(productDetails.getSupplier().getLocation().getWebSite());

        return locationDTO;
    }

    public static List<LocationDTO> toLocationDTOList(ProductDetails productDetails,
            List<LocationDTO> locationDTOList) {
        for (Location location : productDetails.getMeetingPoint()) {
            LocationDTO locationDTO = new LocationDTO();
            locationDTO.setCity(location.getCity());
            locationDTO.setCountry(location.getCountry());
            locationDTO.setDescription(location.getDescription());
            locationDTO.setExtendedAttributes(location.getExtendedAttributes());
            locationDTO.setLatitude(location.getLatitude());
            locationDTO.setLongitude(location.getLongitude());
            locationDTO.setPostalCode(location.getPostalCode());
            locationDTO.setState(location.getState());
            locationDTO.setStreet1(location.getStreet1());
            locationDTO.setStreet2(location.getStreet2());
            locationDTO.setTitle(location.getTitle());
            locationDTO.setWebSite(location.getWebSite());

            locationDTOList.add(locationDTO);
        }
        return locationDTOList;
    }
}
