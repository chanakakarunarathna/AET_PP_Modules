package com.urbanadventures.connector.application.product;

import java.util.ArrayList;
import java.util.List;

import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.currency.AmountFormatter;
import com.urbanadventures.connector.application.common.UrbanAdventuresUtil;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.product.Availability;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductDetailsRS;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.Policy;
import com.placepass.connector.common.product.Price;
import com.placepass.connector.common.product.ProductDetails;
import com.placepass.connector.common.product.ProductOption;
import com.placepass.connector.common.product.ProductOptionGroup;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetPriceRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetTripInfoRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsGetTripInfoRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripAlmRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripAlmRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripAvailableDateRQ;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripAvailableDateRS;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripDeparture;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripFullInfo;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripPhoto;
import com.urbanadventures.connector.domain.urbanadventures.ClsTripPrice;

public class ProductTransformer {

    public static GetProductDetailsRS toGetProductDetailsRS(ClsGetTripInfoRS clsGetTripInfoRS) {
        // Start, binding values urbanadventures >><< placepass

        ClsTripFullInfo clsTripFullInfo = clsGetTripInfoRS.getTripInfo();
        GetProductDetailsRS getProductDetailsRS = new GetProductDetailsRS();

        ResultType resultType = new ResultType();
        resultType.setCode(clsGetTripInfoRS.getOpResult().getCode());
        resultType.setMessage(clsGetTripInfoRS.getOpResult().getMessage());
        getProductDetailsRS.setResultType(resultType);

        // if connector does not return error
        if (resultType.getCode() == 0) {

            ProductDetails productDetails = new ProductDetails();
            productDetails.setLanguageCode("en");
            productDetails.setTitle(clsTripFullInfo.getTripName());
            productDetails.setDescription(clsTripFullInfo.getIntroduction());

            // Binding image urls
            List<String> images = new ArrayList<>();
            List<ClsTripPhoto> clsTripPhotos = clsTripFullInfo.getPhoto();
            for (ClsTripPhoto clsTripPhoto : clsTripPhotos) {
                images.add(clsTripPhoto.getUrl());
            }
            productDetails.setImages(images);

            // Binding prices
            List<Price> prices = new ArrayList<>();
            Price adultPrice = new Price();
            adultPrice.setPriceType(PlacePassAgeBandType.ADULT.name());
            adultPrice.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
            adultPrice.setDescription("Adult price");
            adultPrice.setCurrencyCode(clsTripFullInfo.getCurrency());
            adultPrice.setRetailPrice(AmountFormatter.floatToFloatRounding(clsTripFullInfo.getAdultPrice()));
            adultPrice.setFinalPrice(AmountFormatter.floatToFloatRounding(clsTripFullInfo.getAdultPrice()));
            adultPrice.setMerchantPrice(AmountFormatter.floatToFloatRounding(clsTripFullInfo.getNetAdultPrice()));
            prices.add(adultPrice);

            Price childPrice = new Price();
            childPrice.setPriceType(PlacePassAgeBandType.CHILD.name());
            childPrice.setAgeBandId(PlacePassAgeBandType.CHILD.ageBandId);
            childPrice.setDescription("Children price");
            childPrice.setCurrencyCode(clsTripFullInfo.getCurrency());
            if (clsTripFullInfo.getChildrenPrice() != null) {
                childPrice.setRetailPrice(AmountFormatter.floatToFloatRounding(clsTripFullInfo.getChildrenPrice()));
                childPrice.setFinalPrice(AmountFormatter.floatToFloatRounding(clsTripFullInfo.getChildrenPrice()));
            }
            childPrice.setMerchantPrice(AmountFormatter.floatToFloatRounding(clsTripFullInfo.getNetChildPrice()));
            prices.add(childPrice);

            productDetails.setPrices(prices);

            // Binding inclusions
            productDetails.setInclusions(
                    UrbanAdventuresUtil.extractPTagTextDataToList(clsTripFullInfo.getInclusion().getValue()));

            // Binding exclusions
            productDetails.setExclusions(
                    UrbanAdventuresUtil.extractPTagTextDataToList(clsTripFullInfo.getExclusion().getValue()));

            // Binding highlights
            productDetails.setHiglights(
                    UrbanAdventuresUtil.extractLiTagTextDataToList(clsTripFullInfo.getHighLights().getValue()));

            // Binding durations
            List<String> durations = new ArrayList<>();
            durations.add(clsTripFullInfo.getDuration());
            productDetails.setDuration(durations);
            getProductDetailsRS.setProductDetails(productDetails);
        }
        return getProductDetailsRS;
    }

    public static GetAvailabilityRS toGetAvailabilityRS(ClsTripAvailableDateRS clsTripAvailableDateRS) {

        GetAvailabilityRS getAvailabilityRS = new GetAvailabilityRS();

        ResultType resultType = new ResultType();
        resultType.setCode(clsTripAvailableDateRS.getOpResult().getCode());
        resultType.setMessage(clsTripAvailableDateRS.getOpResult().getMessage());
        getAvailabilityRS.setResultType(resultType);

        // if connector does not return error
        if (resultType.getCode() == 0) {

            List<String> uaProductAvailableDates = clsTripAvailableDateRS.getDate();
            List<Availability> ppProductAvailableDates = new ArrayList<>();

            for (String uaProductAvailableDate : uaProductAvailableDates) {

                if (!uaProductAvailableDate.equals("")) {
                    Availability ppAvailability = new Availability();
                    ppAvailability.setDate(UrbanAdventuresUtil.uaDateToPpDate(uaProductAvailableDate));
                    ppProductAvailableDates.add(ppAvailability);
                }
            }

            getAvailabilityRS.setAvailability(ppProductAvailableDates);
        }
        return getAvailabilityRS;
    }

    public static ProductOption toProductOption(ClsTripFullInfo uaClsTripFullInfo, ClsTripDeparture uaTripDeparture,
            ClsTripAlmRS uaClsTripAlmRS, ClsTripPrice clsTripPrice) {

        ProductOption ppproductOption = new ProductOption();
        boolean childAllowed = false;

        ppproductOption.setProductOptionId(String.valueOf(uaTripDeparture.getId()));
        ppproductOption.setName(uaClsTripFullInfo.getTripName() + ": Departure-" + uaTripDeparture.getDepMin());
        ppproductOption.setStartTime(uaTripDeparture.getDepMin());
        ppproductOption.setEndTime(uaTripDeparture.getEndMin());
        ppproductOption.setType("PRODUCT OPTION");
        // description not supported

        ppproductOption.setAvailability(-1);
        ppproductOption.setIsHotelPickUpEligible(uaClsTripFullInfo.getHotelLocationRequired().getValue());

        // Adding prices.
        childAllowed = Float.compare(clsTripPrice.getPriceChild(), -1.0f) != 0;
        List<Price> prices = new ArrayList<Price>();

        Price adultPrice = new Price();
        adultPrice.setPriceGroupSortOrder(1);
        adultPrice.setPriceType(PlacePassAgeBandType.ADULT.name());
        adultPrice.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
        adultPrice.setDescription("Adult Prices");
        adultPrice.setCurrencyCode(clsTripPrice.getCurrency());
        adultPrice.setRetailPrice(AmountFormatter.floatToFloatRounding(clsTripPrice.getPriceAdult()));
        adultPrice.setFinalPrice(AmountFormatter.floatToFloatRounding(clsTripPrice.getPriceAdult()));
        adultPrice.setMerchantPrice(AmountFormatter.floatToFloatRounding(clsTripPrice.getNetPriceAdult()));

        if (uaClsTripFullInfo.getGroupMin() < 1) {
            adultPrice.setMinBuy(1);
        } else {
            adultPrice.setMinBuy(uaClsTripFullInfo.getGroupMin());
        }

        adultPrice.setMaxBuy(Math.min(uaClsTripFullInfo.getGroupMax(), uaClsTripAlmRS.getAlmNumber()));
        // ua doesn't return adult from to age
        adultPrice.setAgeFrom(-1);
        adultPrice.setAgeTo(-1);
        prices.add(adultPrice);

        if (childAllowed) {
            Price childPrice = new Price();
            childPrice.setPriceGroupSortOrder(1);
            childPrice.setPriceType(PlacePassAgeBandType.CHILD.name());
            childPrice.setAgeBandId(PlacePassAgeBandType.CHILD.ageBandId);
            childPrice.setDescription("Child Prices");
            childPrice.setCurrencyCode(clsTripPrice.getCurrency());
            childPrice.setRetailPrice(AmountFormatter.floatToFloatRounding(clsTripPrice.getPriceChild()));
            childPrice.setFinalPrice(AmountFormatter.floatToFloatRounding(clsTripPrice.getPriceChild()));
            childPrice.setMerchantPrice(AmountFormatter.floatToFloatRounding(clsTripPrice.getNetPriceChild()));
            childPrice.setMinBuy(0);
            childPrice.setMaxBuy(Math.min(uaClsTripFullInfo.getGroupMax(), uaClsTripAlmRS.getAlmNumber()));
            childPrice.setAgeFrom(uaClsTripFullInfo.getMinChildAge());
            childPrice.setAgeTo(uaClsTripFullInfo.getMaxChildAge());
            prices.add(childPrice);
        }
        ppproductOption.setPrices(prices);

        // Adding policy
        Policy policy = new Policy();
        policy.setChildAllowed(childAllowed);
        policy.setMinChildAge(uaClsTripFullInfo.getMinChildAge());
        policy.setMaxChildAge(uaClsTripFullInfo.getMaxChildAge());
        policy.setChildPolicyMsg(uaClsTripFullInfo.getChildPolicy().getValue());
        ppproductOption.setPolicy(policy);

        return ppproductOption;
    }

    public static ClsGetTripInfoRQ toClsGetTripInfoRQ(String productId) {

        ClsGetTripInfoRQ clsGetTripInfoRQ = new ClsGetTripInfoRQ();
        clsGetTripInfoRQ.setTripCode(productId);

        return clsGetTripInfoRQ;
    }

    public static ClsTripAvailableDateRQ toClsTripAvailableDateRQ(String productId, String startDate, String endDate) {

        ClsTripAvailableDateRQ uaClsTripAvailableDateRQ = new ClsTripAvailableDateRQ();
        uaClsTripAvailableDateRQ.setTripCode(productId);
        uaClsTripAvailableDateRQ.setStartDate(startDate);
        uaClsTripAvailableDateRQ.setEndDate(endDate);

        return uaClsTripAvailableDateRQ;
    }

    public static ClsTripAlmRQ toClsTripAlmRQ(String productId, String date, int uaTripDepartureId) {

        ClsTripAlmRQ uaClsTripAlmRQ = new ClsTripAlmRQ();
        uaClsTripAlmRQ.setDepDate(date);
        uaClsTripAlmRQ.setTripCode(productId);
        uaClsTripAlmRQ.setDepId(uaTripDepartureId);

        return uaClsTripAlmRQ;
    }

    public static ClsGetPriceRQ toClsGetPriceRQ(String productId, String date, int uaTripDepartureId) {

        ClsGetPriceRQ clsGetPriceRQ = new ClsGetPriceRQ();
        clsGetPriceRQ.setTripCode(productId);
        clsGetPriceRQ.setDepId(uaTripDepartureId);
        clsGetPriceRQ.setNumAdult(1);
        clsGetPriceRQ.setDepDate(date);

        return clsGetPriceRQ;
    }

    public static GetProductOptionsRS toGetProductOptionsRS(List<ProductOption> ppproductOptions) {

        GetProductOptionsRS getProductOptionsRS = new GetProductOptionsRS();
        ResultType resultType = new ResultType();
        ProductOptionGroup productOptionGroup = new ProductOptionGroup();

        productOptionGroup.setName("PARENT PRODUCT");
        productOptionGroup.setType("PARENT PRODUCT");
        productOptionGroup.setProductOptions(ppproductOptions);
        productOptionGroup.setProductOptionGroups(null);
        getProductOptionsRS.setProductOptionGroup(productOptionGroup);

        resultType.setCode(0);
        resultType.setMessage("");
        getProductOptionsRS.setResultType(resultType);

        return getProductOptionsRS;
    }
}
