package com.placepass.connector.bemyguest.application.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.placepass.connector.bemyguest.application.util.BeMyGuestUtil;
import com.placepass.connector.bemyguest.application.util.VendorErrorCode;
import com.placepass.connector.bemyguest.domain.bemyguest.availability.BmgAvailability;
import com.placepass.connector.bemyguest.domain.bemyguest.availability.BmgProductAvailability;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgCancellationPolicies;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgPhoto;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgPricesPerDate;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgProductDetail;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgProductDetailsRS;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgProductDetailsResInfo;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgProductTypes;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgTimeSlot;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.product.Availability;
import com.placepass.connector.common.product.CancellationRules;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductDetailsRS;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.Price;
import com.placepass.connector.common.product.ProductDetails;
import com.placepass.connector.common.product.ProductOption;
import com.placepass.connector.common.product.ProductOptionGroup;
import com.placepass.connector.common.product.Rules;
import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.currency.AmountFormatter;

public class ProductTransformer {

    public static Map<String,Object> toProductDetailsRequestParamMap(String productId) {
        Map<String,Object> urlVariables = new HashMap<>();
        UUID productUuid = UUID.fromString(productId);
        urlVariables.put("productuuid", productUuid);
        return urlVariables;
    }

    public static CancellationRules toCancellationRules(List<BmgCancellationPolicies> bmgCancellationPolicies) {
        CancellationRules cancellationRules = new CancellationRules();
        List<Rules> rules = new ArrayList<Rules>();

        if(bmgCancellationPolicies.isEmpty()) {
            Rules rule = new Rules();
            rule.setRefundMultiplier(0);
            rule.setMaxHoursInAdvance(0);
            rule.setMinHoursInAdvance(0);
            rules.add(rule);
        } else {
            for(BmgCancellationPolicies bmgCancelPolicy : bmgCancellationPolicies) {
                Rules rule = new Rules();
                rule.setMinHoursInAdvance(bmgCancelPolicy.getNumberOfDays() * 24);
                rule.setRefundMultiplier(BigDecimal.valueOf(bmgCancelPolicy.getRefundPercentage() * .01).floatValue());
                rules.add(rule);
            }
        }
        cancellationRules.setRules(rules);
        
        return cancellationRules;
    }

    public static GetProductDetailsRS toProductDetails(BmgProductDetailsRS beMyGuestProductDetailsRS) {
        GetProductDetailsRS getProductDetailsRS = new GetProductDetailsRS();
        BmgProductDetailsResInfo bmgProductDetailsResInfo = beMyGuestProductDetailsRS.getData();
        ProductDetails productDetails = new ProductDetails();

        // List of languages are coming. here we are considering the first element
        productDetails.setLanguageCode(bmgProductDetailsResInfo.getGuideLanguages().get(0).getName());
        productDetails.setTitle(bmgProductDetailsResInfo.getTitle());
        productDetails.setDescription(bmgProductDetailsResInfo.getDescription());

        List<String> images = new ArrayList<>();
        for (BmgPhoto photo : bmgProductDetailsResInfo.getPhotos()) {
            images.add(photo.getPaths().getOriginal());
        }
        productDetails.setImages(images);

        // productDetails.setVidoes(vidoes);
        productDetails
                .setInclusions(BeMyGuestUtil.getStringListFromString(bmgProductDetailsResInfo.getPriceIncludes()));
        productDetails
                .setExclusions(BeMyGuestUtil.getStringListFromString(bmgProductDetailsResInfo.getPriceExcludes()));
        productDetails.setHiglights(BeMyGuestUtil.getStringListFromString(bmgProductDetailsResInfo.getHighlights()));
        // productDetails.setSupplier(supplier);
        // productDetails.setActivitySnapshots(activitySnapshots);
        // productDetails.setMeetingPoint();
        // productDetails.setDropOffPoint(dropOffPoint);
        // productDetails.setVoucherTypes(voucherTypes);

        List<String> durations = new ArrayList<>();
        durations.add("durationDays:" + bmgProductDetailsResInfo.getProductTypes().get(0).getDurationDays());
        durations.add("durationHours:" + bmgProductDetailsResInfo.getProductTypes().get(0).getDurationHours());
        durations.add("durationMinutes:" + bmgProductDetailsResInfo.getProductTypes().get(0).getDurationMinutes());
        productDetails.setDuration(durations);

        List<Price> priceList = new ArrayList<>();
        Price price = new Price();
        price.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
        price.setCurrencyCode(bmgProductDetailsResInfo.getCurrency().getCode());
        price.setPriceType(PlacePassAgeBandType.ADULT.name());
        price.setDescription("Adult Price");
        price.setRetailPrice(AmountFormatter
                .floatToFloatRounding(BeMyGuestUtil.doubleToFloat(bmgProductDetailsResInfo.getBasePrice())));
        price.setFinalPrice(AmountFormatter
                .floatToFloatRounding(BeMyGuestUtil.doubleToFloat(bmgProductDetailsResInfo.getBasePrice())));
        price.setMerchantPrice(AmountFormatter
                .floatToFloatRounding(BeMyGuestUtil.doubleToFloat(bmgProductDetailsResInfo.getBasePrice())));
        priceList.add(price);
        productDetails.setPrices(priceList);

        productDetails.setReviewCount(bmgProductDetailsResInfo.getReviewCount());
        // productDetails.setAverageRating(averageRating);

        getProductDetailsRS.setProductDetails(productDetails);

        return getProductDetailsRS;
    }

    /**
     * private static Tour setTourById(String productId) { Tour tour = new Tour();
     * tour.setActivityID(Integer.parseInt(productId)); tour.setActivityCurrency("USD"); return tour; }
     *
     * public static ProductReviewsByIdRequest toProductReviewsRequest(String productid) { ProductReviewsByIdRequest
     * request = new ProductReviewsByIdRequest(); request.setActivityID(Integer.parseInt(productid));
     * request.setProcessType("ActivityReview"); return request; }
     *
     **/

    /*
     * public static GetAvailabilityRS toGetAvailabilityRS(BmgProductDetail bmgProductDetail, String month, String year)
     * {
     *
     * GetAvailabilityRS getAvailabilityRS = new GetAvailabilityRS(); ResultType resultType = new ResultType();
     *
     * if (bmgProductDetail != null) { resultType.setCode(0); resultType.setMessage("");
     *
     * List<Availability> availabilityList = new ArrayList<>(); // TODO: change this to support multiple productTypes
     * BmgProductTypes bmgProductType = bmgProductDetail.getProductTypes().get(0); Map<String, BmgPricesPerDate>
     * bmgPricesMap = bmgProductType.getPrices();
     *
     * for (String bmgPriceDate : bmgPricesMap.keySet()) {
     *
     * if (Integer.parseInt(month) == BeMyGuestUtil.getMonthfromString(bmgPriceDate) && Integer.parseInt(year) ==
     * BeMyGuestUtil.getYearfromString(bmgPriceDate)) {
     *
     * Availability availability = new Availability(); availability.setDate(bmgPriceDate);
     * availability.setSoldOut(false);
     *
     * Map<Integer, Double> adultRegularPriceMap = bmgPricesMap.get(bmgPriceDate).getRegular().getAdult(); for (Integer
     * adultRegularPriceKey : adultRegularPriceMap.keySet()) {
     *
     * // Take lowest price from regular adult price list if (availability.getPrice() == null ||
     * availability.getPrice().getFinalPrice() > BeMyGuestUtil
     * .doubleToFloat(adultRegularPriceMap.get(adultRegularPriceKey))) { Price price = new Price();
     * price.setPriceType(PlacePassAgeBandType.ADULT.name()); price.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
     * price.setDescription("Adult Price"); price.setCurrencyCode(bmgProductDetail.getCurrency().getCode());
     * price.setRetailPrice( BeMyGuestUtil.doubleToFloat(adultRegularPriceMap.get(adultRegularPriceKey)));
     * price.setFinalPrice( BeMyGuestUtil.doubleToFloat(adultRegularPriceMap.get(adultRegularPriceKey)));
     * price.setMerchantPrice( BeMyGuestUtil.doubleToFloat(adultRegularPriceMap.get(adultRegularPriceKey)));
     *
     * availability.setPrice(price); } }
     *
     * availabilityList.add(availability);
     *
     * }
     *
     * }
     *
     * getAvailabilityRS.setAvailability(availabilityList); } else {
     * resultType.setCode(VendorErrorCode.PRODUCT_NOT_FOUND.getId());
     * resultType.setMessage(VendorErrorCode.PRODUCT_NOT_FOUND.getMsg()); } getAvailabilityRS.setResultType(resultType);
     * return getAvailabilityRS; }
     */
    public static GetAvailabilityRS toGetAvailabilityRS(BmgProductAvailability bmgProductAvailability, String month,
            String year) {
        GetAvailabilityRS getAvailabilityRS = new GetAvailabilityRS();
        ResultType resultType = new ResultType();
        List<Availability> availabilityList = new ArrayList<>();

        if (bmgProductAvailability != null) {
            resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());

            for (BmgAvailability bmgAvailability : bmgProductAvailability.getAvailability()) {

                if (BeMyGuestUtil.getMonthfromString(bmgAvailability.getDate()) == Integer.parseInt(month)
                        && BeMyGuestUtil.getYearfromString(bmgAvailability.getDate()) == Integer.parseInt(year)) {
                    Availability availability = new Availability();
                    availability.setDate(bmgAvailability.getDate());

                    Price price = new Price();
                    price.setAgeBandId(bmgAvailability.getPrice().getAgeBandId());
                    price.setAgeFrom(bmgAvailability.getPrice().getAgeFrom());
                    price.setAgeTo(bmgAvailability.getPrice().getAgeTo());
                    price.setCurrencyCode(bmgAvailability.getPrice().getCurrencyCode());
                    price.setDescription(bmgAvailability.getPrice().getDescription());
                    price.setFinalPrice(bmgAvailability.getPrice().getFinalPrice());
                    price.setMaxBuy(bmgAvailability.getPrice().getMaxBuy());
                    price.setMerchantPrice(bmgAvailability.getPrice().getMerchantPrice());
                    price.setMinBuy(bmgAvailability.getPrice().getMinBuy());
                    price.setPriceGroupSortOrder(bmgAvailability.getPrice().getPriceGroupSortOrder());
                    price.setPriceType(bmgAvailability.getPrice().getPriceType());
                    price.setRetailPrice(bmgAvailability.getPrice().getRetailPrice());

                    availability.setPrice(price);
                    availability.setSoldOut(bmgAvailability.isSoldOut());

                    availabilityList.add(availability);

                }

            }

        } else {
            resultType.setCode(VendorErrorCode.PRODUCT_NOT_FOUND.getId());
            resultType.setMessage(VendorErrorCode.PRODUCT_NOT_FOUND.getMsg());
        }

        getAvailabilityRS.setAvailability(availabilityList);
        getAvailabilityRS.setResultType(resultType);
        return getAvailabilityRS;
    }

    public static GetProductOptionsRS toProductOptions(BmgProductDetail bmgProductDetail, String date) {

        GetProductOptionsRS getProductOptionsRS = new GetProductOptionsRS();
        ResultType resultType = new ResultType();
        ProductOptionGroup productOptionGroup = new ProductOptionGroup();

        List<ProductOption> productOptionsList = new ArrayList<>();

        if (bmgProductDetail != null) {
            resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());

            for (BmgProductTypes bmgProductType : bmgProductDetail.getProductTypes()) {
                List<Price> priceList = new ArrayList<>();
                Map<String, BmgPricesPerDate> pricesPerDateMap = bmgProductType.getPrices();

                for (String priceDate : pricesPerDateMap.keySet()) {
                    if (priceDate.equals(date)) {
                        if (!pricesPerDateMap.get(priceDate).getRegular().getAdult().isEmpty()
                                && pricesPerDateMap.get(priceDate).getRegular().getAdult() != null) {
                            Price adultPrice = new Price();
                            adultPrice.setPriceGroupSortOrder(1);
                            adultPrice.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
                            adultPrice.setPriceType(PlacePassAgeBandType.ADULT.name());
                            adultPrice.setCurrencyCode(bmgProductDetail.getCurrency().getCode());
                            adultPrice.setDescription("Adult Price");
                            adultPrice.setAgeFrom(bmgProductType.getMinAdultAge());
                            adultPrice.setAgeTo(bmgProductType.getMaxAdultAge());
                            adultPrice.setMinBuy(bmgProductDetail.getMinPax());
                            adultPrice.setMaxBuy(bmgProductDetail.getMaxPax());
                            // First element contains price for 1 person
                            adultPrice.setFinalPrice(BeMyGuestUtil
                                    .doubleToFloat(pricesPerDateMap.get(priceDate).getRegular().getAdult().get(1)));
                            adultPrice.setRetailPrice(BeMyGuestUtil
                                    .doubleToFloat(pricesPerDateMap.get(priceDate).getRegular().getAdult().get(1)));
                            adultPrice.setMerchantPrice(BeMyGuestUtil
                                    .doubleToFloat(pricesPerDateMap.get(priceDate).getRegular().getAdult().get(1)));

                            priceList.add(adultPrice);
                        }

                        if (bmgProductType.isAllowChildren()) {
                            Price childPrice = new Price();
                            childPrice.setPriceGroupSortOrder(1);
                            childPrice.setAgeBandId(PlacePassAgeBandType.CHILD.ageBandId);
                            childPrice.setPriceType(PlacePassAgeBandType.CHILD.name());
                            childPrice.setCurrencyCode(bmgProductDetail.getCurrency().getCode());
                            childPrice.setDescription("Child Price");
                            childPrice.setAgeFrom(bmgProductType.getMinChildAge());
                            childPrice.setAgeTo(bmgProductType.getMaxChildAge());
                            childPrice.setMinBuy(0);
                            childPrice.setMaxBuy(-1);
                            if (bmgProductType.isHasChildPrice()) {
                                // First element contains price for 1 person
                                childPrice.setFinalPrice(BeMyGuestUtil.doubleToFloat(
                                        pricesPerDateMap.get(priceDate).getRegular().getChildren().get(1)));
                                childPrice.setRetailPrice(BeMyGuestUtil.doubleToFloat(
                                        pricesPerDateMap.get(priceDate).getRegular().getChildren().get(1)));
                                childPrice.setMerchantPrice(BeMyGuestUtil.doubleToFloat(
                                        pricesPerDateMap.get(priceDate).getRegular().getChildren().get(1)));

                            } else {
                                childPrice.setFinalPrice(0.00f);
                                childPrice.setRetailPrice(0.00f);
                                childPrice.setMerchantPrice(0.00f);
                            }
                            priceList.add(childPrice);
                        }

                        if (bmgProductType.isAllowInfant()) {
                            Price infantPrice = new Price();
                            infantPrice.setPriceGroupSortOrder(1);
                            infantPrice.setAgeBandId(PlacePassAgeBandType.INFANT.ageBandId);
                            infantPrice.setPriceType(PlacePassAgeBandType.INFANT.name());
                            infantPrice.setCurrencyCode(bmgProductDetail.getCurrency().getCode());
                            infantPrice.setDescription("Infant Price");
                            infantPrice.setAgeFrom(bmgProductType.getMinInfantAge());
                            infantPrice.setAgeTo(bmgProductType.getMaxInfantAge());
                            infantPrice.setMinBuy(0);
                            infantPrice.setMaxBuy(-1);
                            infantPrice.setFinalPrice(0.00f);
                            infantPrice.setRetailPrice(0.00f);
                            infantPrice.setMerchantPrice(0.00f);

                            priceList.add(infantPrice);
                        }

                        break;
                    }
                }

                if (priceList != null && !priceList.isEmpty()) {

                    if (bmgProductType.getTimeSlots() != null && !bmgProductType.getTimeSlots().isEmpty()) {
                        for (BmgTimeSlot bmgTimeSlot : bmgProductType.getTimeSlots()) {
                            ProductOption productOption = new ProductOption();
                            productOption.setAvailability(-1);
                            productOption.setDescription(bmgProductType.getDescription());
                            productOption.setIsHotelPickUpEligible(bmgProductDetail.isHotelPickup());
                            productOption.setName(bmgProductType.getTitle() + " " + bmgTimeSlot.getStartTime() + "-"
                                    + bmgTimeSlot.getEndTime());
                            productOption.setPolicy(null);
                            productOption.setPrices(priceList);
                            productOption.setProductOptionId(bmgProductType.getUuid() + "&" + bmgTimeSlot.getUuid());
                            productOption.setStartTime(bmgTimeSlot.getStartTime());
                            productOption.setEndTime(bmgTimeSlot.getEndTime());
                            productOption.setType("PRODUCT OPTION");

                            productOptionsList.add(productOption);

                        }
                    } else {
                        ProductOption productOption = new ProductOption();
                        productOption.setAvailability(-1);
                        productOption.setDescription(bmgProductType.getDescription());
                        productOption.setIsHotelPickUpEligible(bmgProductDetail.isHotelPickup());
                        productOption.setName(bmgProductType.getTitle());
                        productOption.setPolicy(null);
                        productOption.setPrices(priceList);
                        productOption.setProductOptionId(bmgProductType.getUuid().toString());
                        // productOption.setStartTime(bmgTimeSlot.getStartTime());
                        // productOption.setEndTime(bmgTimeSlot.getEndTime());
                        productOption.setType("PRODUCT OPTION");

                        productOptionsList.add(productOption);
                    }
                }
            }

            if (productOptionsList != null && !productOptionsList.isEmpty()) {
                productOptionGroup.setProductOptionGroups(null);
                productOptionGroup.setName("PARENT PRODUCT");
                productOptionGroup.setType("PARENT PRODUCT");
                productOptionGroup.setProductOptions(productOptionsList);

                getProductOptionsRS.setProductOptionGroup(productOptionGroup);
            } else {
                resultType.setCode(VendorErrorCode.PRODUCT_OPTIONS_NOT_FOUND.getId());
                resultType.setMessage(VendorErrorCode.PRODUCT_OPTIONS_NOT_FOUND.getMsg());
            }

        } else {
            resultType.setCode(VendorErrorCode.PRODUCT_NOT_FOUND.getId());
            resultType.setMessage(VendorErrorCode.PRODUCT_NOT_FOUND.getMsg());
        }

        getProductOptionsRS.setResultType(resultType);
        return getProductOptionsRS;
    }
}
