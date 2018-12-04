package com.placepass.connector.citydiscovery.application.product;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.placepass.connector.citydiscovery.domain.citydiscovery.activity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.placepass.connector.citydiscovery.application.exception.InvalidDateException;
import com.placepass.connector.citydiscovery.application.exception.ProductNotAvailableException;
import com.placepass.connector.citydiscovery.application.util.CityDiscoveryUtil;
import com.placepass.connector.citydiscovery.application.util.DateUtil;
import com.placepass.connector.citydiscovery.application.util.VendorErrorCode;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsProductDetailsRQ;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsProductDetailsRQ.Tour;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsProductDetailsRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsReviewRS;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsReviewsInfoRS.ReviewList.Review;
import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsReviewsRQ;
import com.placepass.connector.citydiscovery.infrastructure.RestClient;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.product.Availability;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductDetailsRS;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRS;
import com.placepass.connector.common.product.Language;
import com.placepass.connector.common.product.Price;
import com.placepass.connector.common.product.ProductOption;
import com.placepass.connector.common.product.ProductOptionGroup;
import com.placepass.utils.ageband.PlacePassAgeBandType;

public class ProductTransformer {

    private final static Logger logger = LoggerFactory.getLogger(RestClient.class);

    private static final String PROCESS_TYPE_ACTIVITY_DETAILS = "ActivityDetails";

    private static final String PROCESS_TYPE__ACTIVITY_REVIEWS = "ActivityReview";

    public static ClsProductDetailsRQ toProductDetailsRequest(String productId) {
        ClsProductDetailsRQ request = new ClsProductDetailsRQ();
        request.setProcessType(PROCESS_TYPE_ACTIVITY_DETAILS);
        request.setTour(setTourById(productId));
        return request;
    }

    public static GetProductDetailsRS toProductDetails(ClsProductDetailsRS cityDiscoveryResponse) {
        GetProductDetailsRS getProductDetailsRS = null;
        return getProductDetailsRS;
    }

    private static Tour setTourById(String productId) {
        Tour tour = new Tour();
        tour.setActivityID(Integer.parseInt(productId));
        tour.setActivityCurrency("USD");
        return tour;
    }

    public static ClsReviewsRQ toProductReviewsRequest(String productid) {
        ClsReviewsRQ request = new ClsReviewsRQ();
        request.setActivityID(Integer.parseInt(productid));
        request.setProcessType(PROCESS_TYPE__ACTIVITY_REVIEWS);
        return request;
    }

    // OLD version
    /*
     * public static GetAvailabilityRS toAvailabilityDetails(List<Date> availableDates) {
     * 
     * GetAvailabilityRS availabilityRS = new GetAvailabilityRS(); ResultType resultType = new ResultType();
     * List<Availability> availabilities = new ArrayList<>(); DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
     * 
     * if (availableDates != null && !availableDates.isEmpty()) { for (Date date : availableDates) {
     * 
     * Availability avail = new Availability();
     * 
     * avail.setDate(df.format(date)); avail.setPrice(null); avail.setSoldOut(false);
     * 
     * availabilities.add(avail);
     * 
     * } }
     * 
     * availabilityRS.setAvailability(availabilities);
     * 
     * resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
     * resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg()); availabilityRS.setResultType(resultType);
     * 
     * return availabilityRS; }
     */

    public static GetAvailabilityRS toAvailabilityDetails(ActivityAvailability activityAvailability, String month,
            String year) {

        GetAvailabilityRS availabilityRS = new GetAvailabilityRS();
        ResultType resultType = new ResultType();

        List<Availability> availabilities = new ArrayList<>();
        List<ActivitySingleAvailability> activityDates = activityAvailability.getAvailability();

        if (activityDates != null && !activityDates.isEmpty()) {

            for (ActivitySingleAvailability activitySingleAvailability : activityDates) {

                if (CityDiscoveryUtil.getMonthfromDateTimeString(activitySingleAvailability.getDate()) == Integer
                        .parseInt(month)
                        && CityDiscoveryUtil.getYearfromDateTimeString(activitySingleAvailability.getDate()) == Integer
                                .parseInt(year)
                        && !CityDiscoveryUtil.isPreviousDate(activitySingleAvailability.getDate())) {

                    Availability availability = new Availability();
                    Price price = new Price();

                    price.setAgeBandId(activitySingleAvailability.getPrice().getAgeBandId());
                    price.setAgeFrom(activitySingleAvailability.getPrice().getAgeFrom());
                    price.setAgeTo(activitySingleAvailability.getPrice().getAgeTo());
                    price.setCurrencyCode(activitySingleAvailability.getPrice().getCurrencyCode());
                    price.setDescription(activitySingleAvailability.getPrice().getDescription());
                    price.setFinalPrice(activitySingleAvailability.getPrice().getFinalPrice());
                    price.setMaxBuy(activitySingleAvailability.getPrice().getMaxBuy());
                    price.setMinBuy(activitySingleAvailability.getPrice().getMinBuy());
                    price.setMerchantPrice(activitySingleAvailability.getPrice().getMerchantPrice());
                    price.setPriceGroupSortOrder(activitySingleAvailability.getPrice().getPriceGroupSortOrder());
                    price.setPriceType(activitySingleAvailability.getPrice().getPriceType());
                    price.setRetailPrice(activitySingleAvailability.getPrice().getRetailPrice());

                    availability.setDate(DateUtil.toFormattedDate(activitySingleAvailability.getDate()));
                    availability.setPrice(price);
                    availability.setSoldOut(activitySingleAvailability.isSoldOut());

                    availabilities.add(availability);
                }
            }

            availabilityRS.setAvailability(availabilities);

            resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());
            availabilityRS.setResultType(resultType);
        }

        return availabilityRS;
    }

    public static GetProductReviewsRS toReviews(ClsReviewRS reviewsResponse, String productId) {

        GetProductReviewsRS getProductReviewsRS = new GetProductReviewsRS();
        ResultType resultType = new ResultType();

        if (reviewsResponse.getResultType().getErrorCode() == 0) {

            List<Review> reviews = reviewsResponse.getReviewsInfoRS().getReviewList().getReview();
            List<com.placepass.connector.common.product.Review> reviewList = new ArrayList<com.placepass.connector.common.product.Review>();

            if (reviews != null && !reviews.isEmpty()) {
                for (Review reviewItr : reviews) {

                    com.placepass.connector.common.product.Review review = new com.placepass.connector.common.product.Review();
                    review.setOwnerCountry(reviewItr.getCountry());
                    review.setOwnerName(reviewItr.getOwner());
                    review.setReviewId(reviewItr.getID());
                    review.setProductCode(productId);
                    review.setRating(reviewItr.getRate());
                    review.setReview(reviewItr.getComment());
                    review.setPublishedDate(reviewItr.getDate().toXMLFormat());
                    review.setSubmissionDate(reviewItr.getDate().toXMLFormat());
                    reviewList.add(review);
                }
            }
            // Since city discovery does not support pagination need to handle it manually.
            if (reviews.size() > 0)
                getProductReviewsRS.setTotalReviewCount(reviews.size());
            else
                getProductReviewsRS.setTotalReviewCount(0);
            getProductReviewsRS.setReviews(reviewList);

            resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
            resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());
            getProductReviewsRS.setResultType(resultType);

        } else {

            resultType.setCode(reviewsResponse.getResultType().getErrorCode());
            resultType.setMessage(reviewsResponse.getResultType().getErrorMessage());
            getProductReviewsRS.setResultType(resultType);

        }

        return getProductReviewsRS;
    }

    public static GetProductOptionsRS toProductOptions(ActivityDisplay activityDisplay, String date)
            throws ProductNotAvailableException, InvalidDateException {

        GetProductOptionsRS productOptionsRS = new GetProductOptionsRS();
        ProductOptionGroup productOptionGroup = new ProductOptionGroup();
        List<ProductOption> productOptions = new ArrayList<>();
        ResultType resultType = new ResultType();

        List<Language> languages = CityDiscoveryUtil.getLanguages(activityDisplay.getActivityContentLanguages());
        List<String> blockoutDates = Arrays.asList(activityDisplay.getActivityBlockOutdates().split(";"));

        String year = CityDiscoveryUtil.getYearFromString(date);
        String month = CityDiscoveryUtil.getMonthFromString(date);

        List<Integer> activityPriceDays = null;
        List<Date> availableDates = null;
        Date priceDateBegins = null;
        Date priceDateEnds = null;

        for (ActivityPrice activityPrice : activityDisplay.getActivityPrices()) {

            priceDateBegins = activityPrice.getActivityPriceDateBegins();
            priceDateEnds = activityPrice.getActivityPriceDateEnds();

            activityPriceDays = DateUtil.getAvailableWeekdays(activityPrice.getActivityPriceDays());
            availableDates = DateUtil.getAvailableDates((Integer.parseInt(month) - 1), Integer.parseInt(year),
                    priceDateBegins, priceDateEnds, activityPriceDays, blockoutDates);

            if (availableDates == null) {
                throw new ProductNotAvailableException(
                        VendorErrorCode.PRODUCT_OPTIONS_NOT_FOUND_FOR_GIVEN_DATE.toString());
            }

            for (Date availableDate : availableDates) {

                String formattedAvailableDate = CityDiscoveryUtil.convertDateToString(availableDate);

                if (date.equals(formattedAvailableDate)) {

                    List<String> activityPriceOptionDepartureTimes = CityDiscoveryUtil
                            .getActivityPriceOptionDepatuerTimes(activityPrice.getActivityPriceOptionDepartureTime());

                    if (activityPriceOptionDepartureTimes.isEmpty()) {

                        ProductOption productOption = new ProductOption();
                        productOption.setProductOptionId(String.valueOf(activityPrice.getId()));
                        productOption.setName(activityPrice.getActivityPriceOption());
                        productOption.setType("PRODUCT OPTION");
                        productOption.setAvailability(-1);
                        productOption.setEndTime("");
                        productOption.setPolicy(null);
                        productOption.setStartTime("");
                        productOption.setPrices(ProductTransformer.getActivityPrices(activityPrice));
                        productOption.setDescription("");
                        productOptions.add(productOption);

                    } else {

                        for (String activityPriceOptionDepartureTime : activityPriceOptionDepartureTimes) {

                            ProductOption productOption = new ProductOption();
                            
                            productOption.setType("PRODUCT OPTION");
                            productOption.setAvailability(-1);
                            productOption.setProductOptionId(
                                    String.valueOf(activityPrice.getId()) + "&" + activityPriceOptionDepartureTime);

                            if (CityDiscoveryUtil.validateMilitaryTimeFormat(activityPriceOptionDepartureTime)) {
                                try {
                                    productOption.setStartTime(
                                            CityDiscoveryUtil.militaryTimeToMeridiemTime(activityPriceOptionDepartureTime));
                                    productOption.setName(activityPrice.getActivityPriceOption() + "-" + CityDiscoveryUtil
                                            .militaryTimeToMeridiemTime(activityPriceOptionDepartureTime));
                                    productOption
                                            .setDescription(activityPrice.getActivityPriceOption() + "," + CityDiscoveryUtil
                                                    .militaryTimeToMeridiemTime(activityPriceOptionDepartureTime));
                                } catch (ParseException e) {
                                    throw new InvalidDateException(VendorErrorCode.INVALID_MILITARY_TIME_FOUND.getMsg());
                                }
                            } else {
                                productOption.setStartTime(activityPriceOptionDepartureTime);
                                productOption.setName(activityPrice.getActivityPriceOption() + "-"
                                        + activityPriceOptionDepartureTime);
                                productOption.setDescription(activityPrice.getActivityPriceOption() + ","
                                        + activityPriceOptionDepartureTime);
                            }

                            productOption.setEndTime("");
                            productOption.setPolicy(null);
                            productOption.setPrices(ProductTransformer.getActivityPrices(activityPrice));

                            productOptions.add(productOption);
                        }

                    }

                }
            }
        }
        productOptionGroup.setName("PARENT PRODUCT");
        productOptionGroup.setType("PARENT PRODUCT");
        productOptionGroup.setDescription("");
        productOptionGroup.setTime("");
        productOptionGroup.setLanguages(languages);
        productOptionGroup.setProductOptions(productOptions);
        productOptionGroup.setProductOptionGroups(null);
        productOptionsRS.setProductOptionGroup(productOptionGroup);

        resultType.setCode(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getId());
        resultType.setMessage(VendorErrorCode.VENDOR_SUCCESS_MESSAGE.getMsg());

        if (productOptions.isEmpty()) {
            resultType.setCode(1);
            resultType.setMessage(VendorErrorCode.PRODUCT_OPTIONS_NOT_FOUND_FOR_GIVEN_DATE.getMsg());
        }

        productOptionsRS.setResultType(resultType);
        return productOptionsRS;
    }

    public static List<Price> getActivityPrices(ActivityPrice activityPrice) {

        List<Price> prices = new ArrayList<>();

        Price childPrice = new Price();
        Price adultPrice = new Price();
        Price infantPrice = new Price();

        adultPrice.setPriceGroupSortOrder(1);
        adultPrice.setAgeBandId(PlacePassAgeBandType.ADULT.getAgeBandId());
        adultPrice.setAgeFrom(activityPrice.getActivityChildMaxAge() + 1);
        adultPrice.setAgeTo(-1);
        adultPrice.setCurrencyCode(activityPrice.getActivityPriceCurrency());
        adultPrice.setDescription("Adult Price");
        adultPrice.setFinalPrice(activityPrice.getActivityPriceAdultUSD().floatValue());
        if (activityPrice.getActivityPriceGroupMaxPax() == 0) {
            adultPrice.setMaxBuy(-1);
        } else {
            adultPrice.setMaxBuy(activityPrice.getActivityPriceGroupMaxPax());
        }
        adultPrice.setMinBuy(activityPrice.getActivityPriceGroupMinPax());
        adultPrice.setMerchantPrice(activityPrice.getActivityPriceAdultNetUSD().floatValue());
        adultPrice.setPriceType(PlacePassAgeBandType.ADULT.name());
        adultPrice.setRetailPrice(activityPrice.getActivityPriceAdultBeforePromoCodeUSD().floatValue());

        prices.add(adultPrice);

        if (activityPrice.getActivityChildAllowed() != null && activityPrice.getActivityChildAllowed().equals("Y")) {
            childPrice.setPriceGroupSortOrder(1);
            childPrice.setAgeBandId(PlacePassAgeBandType.CHILD.getAgeBandId());
            childPrice.setAgeFrom(activityPrice.getActivityInfantMaxAge() + 1);
            childPrice.setAgeTo(activityPrice.getActivityChildMaxAge());
            childPrice.setCurrencyCode(activityPrice.getActivityPriceCurrency());
            childPrice.setDescription("Child Price");
            childPrice.setFinalPrice(activityPrice.getActivityPriceChildUSD().floatValue());
            childPrice.setMaxBuy(-1);
            childPrice.setMinBuy(0);
            childPrice.setMerchantPrice(activityPrice.getActivityPriceChildNetUSD().floatValue());
            childPrice.setPriceType(PlacePassAgeBandType.CHILD.name());
            childPrice.setRetailPrice(activityPrice.getActivityPriceChildBeforePromoCodeUSD().floatValue());
            prices.add(childPrice);
        }

        if (activityPrice.getActivityInfantAllowed() != null && activityPrice.getActivityInfantAllowed().equals("Y")) {
            infantPrice.setPriceGroupSortOrder(1);
            infantPrice.setAgeBandId(PlacePassAgeBandType.INFANT.getAgeBandId());
            infantPrice.setAgeFrom(0);
            infantPrice.setAgeTo(activityPrice.getActivityInfantMaxAge());
            infantPrice.setCurrencyCode(activityPrice.getActivityPriceCurrency());
            infantPrice.setDescription("Infant Price");
            infantPrice.setFinalPrice(0);
            infantPrice.setMaxBuy(-1);
            infantPrice.setMinBuy(0);
            infantPrice.setMerchantPrice(0);
            infantPrice.setPriceType(PlacePassAgeBandType.INFANT.name());
            infantPrice.setRetailPrice(0);
            prices.add(infantPrice);
        }


        return prices;
    }

    public static ActivityDisplay updateAcitivityWithLivePricing(ActivityDisplay activityDisplay, ClsProductDetailsRS productDetailsRS) {
        for (ActivityPrice activityPrice:  activityDisplay.getActivityPrices()) {
            for (com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsProductDetailsInfoRS.TourInformationItems.Tour.ActivityPriceId activityPriceId : productDetailsRS.getProductDetailsInfoRS().getTourInformationItems().getTour().getActivityPrices()) {
                if (activityPriceId.getID() != null && activityPrice.getId() == activityPriceId.getID()) {

                    activityPrice.setActivityPriceAdult(activityPriceId.getActivityPriceAdult().doubleValue());
                    activityPrice.setActivityPriceAdultUSD(activityPriceId.getActivityPriceAdultUSD().doubleValue());
                    activityPrice.setActivityPriceAdultNet(activityPriceId.getActivityPriceAdultNet().doubleValue());
                    activityPrice.setActivityPriceAdultNetUSD(activityPriceId.getActivityPriceAdultNetUSD().doubleValue());
                    activityPrice.setActivityPriceAdultBeforePromoCode(activityPriceId.getActivityPriceAdultBeforePromoCode().doubleValue());
                    activityPrice.setActivityPriceAdultBeforePromoCodeUSD(activityPriceId.getActivityPriceAdultBeforePromoCodeUSD().doubleValue());

                    activityPrice.setActivityPriceChild(activityPriceId.getActivityPriceChild().doubleValue());
                    activityPrice.setActivityPriceChildUSD(activityPriceId.getActivityPriceChildUSD().doubleValue());
                    activityPrice.setActivityPriceChildNet(activityPriceId.getActivityPriceChildNet().doubleValue());
                    activityPrice.setActivityPriceChildNetUSD(activityPriceId.getActivityPriceChildNetUSD().doubleValue());
                    activityPrice.setActivityPriceChildBeforePromoCode(activityPriceId.getActivityPriceChildBeforePromoCode().doubleValue());
                    activityPrice.setActivityPriceChildBeforePromoCodeUSD(activityPriceId.getActivityPriceChildBeforePromoCodeUSD().doubleValue());
                }
            }
        }

        return activityDisplay;
    }

}
