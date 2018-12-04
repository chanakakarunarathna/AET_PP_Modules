package com.viator.connector.application.product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.ageband.PlacePassPricingUnit;
import com.placepass.utils.currency.AmountFormatter;
import com.viator.connector.application.util.ViatorUtil;
import com.viator.connector.domain.placepass.algolia.product.AlgoliaProductAgeBand;
import com.viator.connector.domain.placepass.algolia.product.GetAlgoliaProductDetailsRS;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.product.Availability;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductDetailsRS;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRS;
import com.placepass.connector.common.product.Location;
import com.placepass.connector.common.product.Price;
import com.placepass.connector.common.product.ProductDetails;
import com.placepass.connector.common.product.ProductOption;
import com.placepass.connector.common.product.ProductOptionGroup;
import com.placepass.connector.common.product.Review;
import com.placepass.connector.common.product.Supplier;
import com.viator.connector.domain.viator.common.AgeBand;
import com.viator.connector.domain.viator.pricingmatrix.ViatorPricingMatrixRequest;
import com.viator.connector.domain.viator.pricingmatrix.ViatorPricingMatrixResAgeBandPrice;
import com.viator.connector.domain.viator.pricingmatrix.ViatorPricingMatrixResDate;
import com.viator.connector.domain.viator.pricingmatrix.ViatorPricingMatrixResInfo;
import com.viator.connector.domain.viator.pricingmatrix.ViatorPricingMatrixResPrice;
import com.viator.connector.domain.viator.pricingmatrix.ViatorPricingMatrixResPricingMatrix;
import com.viator.connector.domain.viator.pricingmatrix.ViatorPricingMatrixResTourGrade;
import com.viator.connector.domain.viator.pricingmatrix.ViatorPricingMatrixResponse;
import com.viator.connector.domain.viator.product.ViatorProductResInfo;
import com.viator.connector.domain.viator.product.ViatorProductResPhoto;
import com.viator.connector.domain.viator.product.ViatorProductResTourgrade;
import com.viator.connector.domain.viator.product.ViatorProductResponse;
import com.viator.connector.domain.viator.reviews.ViatorProductReviewsResInfo;
import com.viator.connector.domain.viator.reviews.ViatorProductReviewsResponse;
import com.viator.connector.domain.viator.tourgrades.ViatorTourgradesRequest;
import com.viator.connector.domain.viator.tourgrades.ViatorTourgradesResAgeBandsRequired;
import com.viator.connector.domain.viator.tourgrades.ViatorTourgradesResInfo;
import com.viator.connector.domain.viator.tourgrades.ViatorTourgradesResponse;

public class ProductTransformer {

    private static final String TOUR_NOT_AVAILABLE = "TOUR_NOT_AVAILABLE";

    private static final String PARENT_PRODUCT = "PARENT PRODUCT";

    private static final String BOOKING_OPTIONS_ARE_NOT_AVAILABLE_FOR_THIS_MONTH = "Booking Options are not available for this month.";

    private static final String BOOKING_OPTIONS_ARE_NOT_AVAILABLE_FOR_THIS_DATE = "Booking Options are not available for this date.";

    public static GetProductDetailsRS toProductDetails(ViatorProductResponse viatorProductResponse) {

        GetProductDetailsRS getProductDetailsRS = null;

        if (viatorProductResponse != null && viatorProductResponse.getSuccess()) {

            getProductDetailsRS = new GetProductDetailsRS();
            ViatorProductResInfo viatorProductResInfo = viatorProductResponse.getData();
            ProductDetails productDetails = new ProductDetails();

            ResultType resultType = ViatorUtil.getResultTypeObj(viatorProductResponse);
            getProductDetailsRS.setResultType(resultType);

            productDetails.setTitle(viatorProductResInfo.getTitle());
            productDetails.setDescription(viatorProductResInfo.getDescription());
            List<String> images = new ArrayList<>();
            List<ViatorProductResPhoto> viatorPhotos = viatorProductResInfo.getProductPhotos();
            for (ViatorProductResPhoto viatorPhoto : viatorPhotos) {
                images.add(viatorPhoto.getPhotoURL());
            }
            productDetails.setImages(images);
            // Prices: TODO Verify the mapping for prices;
            List<ViatorProductResTourgrade> viatorTourGrades = viatorProductResInfo.getTourGrades();
            List<Price> ppPrices = new ArrayList<>();
            for (ViatorProductResTourgrade viatorTourGrade : viatorTourGrades) {
                Price ppPrice = new Price();
                ppPrice.setPriceType(viatorTourGrade.getGradeCode());
                ppPrice.setCurrencyCode(viatorTourGrade.getCurrencyCode());
                ppPrice.setDescription(viatorTourGrade.getGradeDescription());
                ppPrice.setRetailPrice(AmountFormatter.floatToFloatRounding(viatorTourGrade.getPriceFrom()));
                ppPrice.setMerchantPrice(
                        AmountFormatter.floatToFloatRounding(viatorTourGrade.getMerchantNetPriceFrom()));
                ppPrices.add(ppPrice);
            }
            productDetails.setPrices(ppPrices);
            Supplier supplier = new Supplier();
            supplier.setTitle(viatorProductResInfo.getSupplierName());
            supplier.setDescription(viatorProductResInfo.getSupplierCode());
            productDetails.setSupplier(supplier);
            Location location = new Location();
            location.setCity(viatorProductResInfo.getCity());
            location.setCountry(viatorProductResInfo.getCountry()); // Do we set
            location.setDescription(viatorProductResInfo.getDeparturePoint());
            List<Location> locations = new ArrayList<>();
            locations.add(location);
            productDetails.setMeetingPoint(locations);
            List<String> durations = new ArrayList<>();
            durations.add(viatorProductResInfo.getDuration());
            productDetails.setDuration(durations);
            List<String> voucherType = new ArrayList<>();
            voucherType.add(viatorProductResInfo.getVoucherOption());
            productDetails.setVoucherTypes(voucherType);

            productDetails.setInclusions(viatorProductResInfo.getInclusions());
            productDetails.setExclusions(viatorProductResInfo.getExclusions());
            productDetails.setHiglights(viatorProductResInfo.getHighlights());
            productDetails.setReviewCount(viatorProductResInfo.getReviewCount());
            productDetails.setAverageRating(viatorProductResInfo.getRating());

            getProductDetailsRS.setProductDetails(productDetails);
        }
        return getProductDetailsRS;
    }

    public static ViatorPricingMatrixRequest toAvailabilityRequest(String productid, int month, int year) {

        // TAKE NOTE: This post request to viator returns only a month result.
        // If from and to date are different we get the availability for the
        // starting with the present day

        ViatorPricingMatrixRequest availabilityRequest = new ViatorPricingMatrixRequest();
        String currencyCode = "USD";
        availabilityRequest.setCurrencyCode(currencyCode);
        availabilityRequest.setProductCode(productid);
        availabilityRequest.setMonth(month);
        availabilityRequest.setYear(year);

        return availabilityRequest;
    }

    public static GetAvailabilityRS toProductAvailability(ViatorPricingMatrixResponse viatorPricingMatrixResponse) {

        ResultType resultType = ViatorUtil.getResultTypeObj(viatorPricingMatrixResponse);

        ViatorPricingMatrixResInfo viatorPricingMatrixResInfo = viatorPricingMatrixResponse.getData();
        List<Availability> ppViatorAvailableDates = new ArrayList<>();
        if (viatorPricingMatrixResponse.getSuccess()) {
            if (viatorPricingMatrixResInfo != null) {
                List<ViatorPricingMatrixResDate> viatorDates = viatorPricingMatrixResInfo.getDates();
                if (viatorDates != null) {
                    for (ViatorPricingMatrixResDate viatorAvailability : viatorDates) {

                        Availability availability = new Availability();
                        availability.setDate(viatorAvailability.getBookingDate());
                        availability.setSoldOut(false);

                        List<ViatorPricingMatrixResTourGrade> tourgrades = viatorAvailability.getTourGrades();
                        for (ViatorPricingMatrixResTourGrade tourgrade : tourgrades) {
                            for (ViatorPricingMatrixResPricingMatrix pricingMatrix : tourgrade.getPricingMatrix()) {
                                for (ViatorPricingMatrixResAgeBandPrice ageBandPrice : pricingMatrix
                                        .getAgeBandPrices()) {

                                    if (ageBandPrice.getBandId() == PlacePassAgeBandType.ADULT.ageBandId) {
                                        for (ViatorPricingMatrixResPrice price : ageBandPrice.getPrices()) {
                                            if (availability.getPrice() == null || availability.getPrice()
                                                    .getFinalPrice() > ViatorUtil.doubleToFloat(price.getPrice())) {
                                                Price priceObj = new Price();
                                                priceObj.setDescription("Adult Price");
                                                priceObj.setPriceType(PlacePassAgeBandType.ADULT.name());
                                                priceObj.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
                                                priceObj.setCurrencyCode(price.getCurrencyCode());
                                                priceObj.setRetailPrice(AmountFormatter.floatToFloatRounding(
                                                        ViatorUtil.doubleToFloat(price.getPrice())));
                                                priceObj.setFinalPrice(AmountFormatter.floatToFloatRounding(
                                                        ViatorUtil.doubleToFloat(price.getPrice())));
                                                priceObj.setMerchantPrice(AmountFormatter.floatToFloatRounding(
                                                        ViatorUtil.doubleToFloat(price.getMerchantNetPrice())));

                                                availability.setPrice(priceObj);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        ppViatorAvailableDates.add(availability);

                    }
                }
            }
        }
        GetAvailabilityRS getAvailabilityRS = new GetAvailabilityRS();
        getAvailabilityRS.setResultType(resultType);
        getAvailabilityRS.setAvailability(ppViatorAvailableDates);
        return getAvailabilityRS;
    }

    public static GetProductOptionsRS toProductOptions(ViatorPricingMatrixResponse viatorPricingMatrixResponse,
            ViatorTourgradesResponse viatorTourgradesResponse, String date,
            GetAlgoliaProductDetailsRS algoliaProductDetails) {

        GetProductOptionsRS getProductOptionsRS = new GetProductOptionsRS();
        ResultType resultType = ViatorUtil.getResultTypeObj(viatorPricingMatrixResponse);

        if (viatorTourgradesResponse.getErrorCodes() != null
                && viatorTourgradesResponse.getErrorCodes().contains(TOUR_NOT_AVAILABLE)) {
            resultType.setCode(1);
            resultType.setMessage(BOOKING_OPTIONS_ARE_NOT_AVAILABLE_FOR_THIS_DATE);

        } else if (viatorPricingMatrixResponse.getSuccess()) {
            if (viatorPricingMatrixResponse.getData().getDates() != null) {
                ProductOptionGroup productOptionGroup = new ProductOptionGroup();
                List<ProductOption> productOptions = new ArrayList<>();

                for (ViatorPricingMatrixResDate viatorPricingMatrixResDate : viatorPricingMatrixResponse.getData()
                        .getDates()) {

                    if (viatorPricingMatrixResDate.getBookingDate().equals(date)) {
                        for (ViatorPricingMatrixResTourGrade viatorPricingMatrixResTourGrade : viatorPricingMatrixResDate
                                .getTourGrades()) {
                            ProductOption ppProductOption = new ProductOption();
                            boolean optionAvailable = true;

                            for (ViatorTourgradesResInfo viatorTourgradesResInfo : viatorTourgradesResponse.getData()) {
                                String tourGradesGradeCode = viatorTourgradesResInfo.getGradeCode();
                                String pricingMatrixGradeCode = viatorPricingMatrixResTourGrade.getGradeCode();

                                String tourGradesDate = viatorTourgradesResInfo.getBookingDate();
                                String pricingMatrixDate = viatorPricingMatrixResDate.getBookingDate();

                                if (tourGradesGradeCode.equals(pricingMatrixGradeCode)
                                        && tourGradesDate.equals(pricingMatrixDate)) {

                                    if (!viatorTourgradesResInfo.getAvailable() &&
                                            viatorTourgradesResInfo.getUnavailableReason() != null && !viatorTourgradesResInfo.getUnavailableReason().equals("TRAVELLER_MISMATCH")) {
                                        optionAvailable = false;
                                    }

                                    ppProductOption.setName(viatorTourgradesResInfo.getGradeTitle());
                                    ppProductOption.setStartTime(viatorTourgradesResInfo.getGradeDepartureTime());
                                    ppProductOption.setEndTime("");
                                    ppProductOption.setDescription(viatorTourgradesResInfo.getGradeDescription());
                                    break;
                                }
                            }

                            if (optionAvailable) {
                                // process further only if available.

                                ppProductOption.setProductOptionId(viatorPricingMatrixResTourGrade.getGradeCode());
                                ppProductOption.setType("PRODUCT OPTION");

                                // TODO:later handle properly
                                ppProductOption.setAvailability(-1);
                                if (algoliaProductDetails != null) {
                                    ppProductOption
                                            .setIsHotelPickUpEligible(algoliaProductDetails.getIsHotelPickUpEligible());
                                }
                                // ppProductOption.setPolicy(policy);

                                List<Price> prices = new ArrayList<>();
                                for (ViatorPricingMatrixResPricingMatrix viatorPricingMatrixResPricingMatrix : viatorPricingMatrixResTourGrade
                                        .getPricingMatrix()) {
                                    if (!ppProductOption.getName().equals("DEFAULT")) {
                                        int minTotal = (viatorPricingMatrixResPricingMatrix.getAgeBandPrices().get(0).getMinimumCountRequired() != null ?
                                                viatorPricingMatrixResPricingMatrix.getAgeBandPrices().get(0).getMinimumCountRequired() : 0);
                                        int maxTotal = (viatorPricingMatrixResPricingMatrix.getAgeBandPrices().get(0).getMaximumCountRequired() != null ?
                                                viatorPricingMatrixResPricingMatrix.getAgeBandPrices().get(0).getMaximumCountRequired() : -1);
                                        if (minTotal > 1) {
                                            ppProductOption.setDescription(ppProductOption.getDescription() + ". Minimum purchase of " + minTotal + " tickets");
                                        }
                                        if (maxTotal != -1 && maxTotal > 0) {
                                            ppProductOption.setDescription(ppProductOption.getDescription() + ". Maximum purchase of " + maxTotal + (maxTotal == 1 ? " ticket" : " tickets"));
                                        }
                                    }
                                    boolean flatRatePricing = false;
                                    if (!viatorPricingMatrixResPricingMatrix.getPricingUnit().equals("per person")
                                            && viatorPricingMatrixResPricingMatrix.getAgeBandPrices() != null
                                            && !viatorPricingMatrixResPricingMatrix.getAgeBandPrices().isEmpty()
                                            && viatorPricingMatrixResPricingMatrix.getAgeBandPrices().get(0).getPrices().size() > 1) {
                                        flatRatePricing = true;
                                    }
                                    if (viatorPricingMatrixResPricingMatrix.getPricingUnit().equals("per person") || !flatRatePricing) {
                                        for (ViatorPricingMatrixResAgeBandPrice viatorPricingMatrixResAgeBandPrice : viatorPricingMatrixResPricingMatrix
                                                .getAgeBandPrices()) {
                                            Price price = new Price();
                                            price.setCurrencyCode(viatorPricingMatrixResAgeBandPrice.getPrices().get(0)
                                                    .getCurrencyCode());
                                            price.setFinalPrice(AmountFormatter.floatToFloatRounding(
                                                    ViatorUtil.doubleToFloat(viatorPricingMatrixResAgeBandPrice
                                                            .getPrices().get(0).getPrice())));
                                            price.setMerchantPrice(AmountFormatter.floatToFloatRounding(
                                                    ViatorUtil.doubleToFloat(viatorPricingMatrixResAgeBandPrice
                                                            .getPrices().get(0).getMerchantNetPrice())));
                                            price.setRetailPrice(AmountFormatter.floatToFloatRounding(
                                                    ViatorUtil.doubleToFloat(viatorPricingMatrixResAgeBandPrice
                                                            .getPrices().get(0).getPrice())));

                                            if (viatorPricingMatrixResAgeBandPrice
                                                    .getBandId() == PlacePassAgeBandType.ADULT.ageBandId) {
                                                price.setPriceType(PlacePassAgeBandType.ADULT.name());
                                                price.setDescription("Adult Price");
                                                price = getAgeBandLimitDescription(algoliaProductDetails, price);
                                                price.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
                                            } else if (viatorPricingMatrixResAgeBandPrice
                                                    .getBandId() == PlacePassAgeBandType.CHILD.ageBandId) {
                                                price.setPriceType(PlacePassAgeBandType.CHILD.name());
                                                price.setDescription("Child Price");
                                                price = getAgeBandLimitDescription(algoliaProductDetails, price);
                                                price.setAgeBandId(PlacePassAgeBandType.CHILD.ageBandId);
                                            } else if (viatorPricingMatrixResAgeBandPrice
                                                    .getBandId() == PlacePassAgeBandType.INFANT.ageBandId) {
                                                price.setPriceType(PlacePassAgeBandType.INFANT.name());
                                                price.setDescription("Infant Price");
                                                price = getAgeBandLimitDescription(algoliaProductDetails, price);
                                                price.setAgeBandId(PlacePassAgeBandType.INFANT.ageBandId);
                                            } else if (viatorPricingMatrixResAgeBandPrice
                                                    .getBandId() == PlacePassAgeBandType.SENIOR.ageBandId) {
                                                price.setPriceType(PlacePassAgeBandType.SENIOR.name());
                                                price.setDescription("Senior Price");
                                                price = getAgeBandLimitDescription(algoliaProductDetails, price);
                                                price.setAgeBandId(PlacePassAgeBandType.SENIOR.ageBandId);
                                            } else if (viatorPricingMatrixResAgeBandPrice
                                                    .getBandId() == PlacePassAgeBandType.YOUTH.ageBandId) {
                                                price.setPriceType(PlacePassAgeBandType.YOUTH.name());
                                                price.setDescription("YOUTH Price");
                                                price = getAgeBandLimitDescription(algoliaProductDetails, price);
                                                price.setAgeBandId(PlacePassAgeBandType.YOUTH.ageBandId);
                                            }
                                            price.setMinBuy(
                                                    viatorPricingMatrixResAgeBandPrice.getMinimumCountRequired());
                                            price.setMaxBuy(
                                                    viatorPricingMatrixResAgeBandPrice.getMaximumCountRequired() == null
                                                            ? -1 : viatorPricingMatrixResAgeBandPrice
                                                                    .getMaximumCountRequired());
                                            price.setPriceGroupSortOrder(
                                                    viatorPricingMatrixResPricingMatrix.getSortOrder());
                                            prices.add(price);
                                        }

                                    } else {

                                        for (ViatorPricingMatrixResAgeBandPrice viatorPricingMatrixResAgeBandPrice : viatorPricingMatrixResPricingMatrix
                                                .getAgeBandPrices()) {

                                            List<ViatorPricingMatrixResPrice> viatorPricingList = viatorPricingMatrixResAgeBandPrice.getPrices();
                                            //sorting to set max and min buy for each price
                                            viatorPricingList.sort(new Comparator<ViatorPricingMatrixResPrice>() {
                                                @Override
                                                public int compare(ViatorPricingMatrixResPrice o1, ViatorPricingMatrixResPrice o2) {
                                                    if (o1.getMinNoOfTravellersRequiredForPrice() > o2.getMinNoOfTravellersRequiredForPrice()) {
                                                        return -1;
                                                    } else {
                                                        return 1;
                                                    }
                                                }
                                            });

                                            Integer maxBuy = viatorPricingMatrixResAgeBandPrice.getMaximumCountRequired();

                                            Iterator<ViatorPricingMatrixResPrice> viatorPriceIterator = viatorPricingList.iterator();
                                            while (viatorPriceIterator.hasNext()) {
                                                ViatorPricingMatrixResPrice viatorPrice = viatorPriceIterator.next();
                                                Price price = new Price();
                                                price.setCurrencyCode(viatorPricingMatrixResAgeBandPrice.getPrices().get(0)
                                                        .getCurrencyCode());
                                                price.setFinalPrice(AmountFormatter.floatToFloatRounding(
                                                        ViatorUtil.doubleToFloat(viatorPricingMatrixResAgeBandPrice
                                                                .getPrices().get(0).getPrice())));
                                                price.setMerchantPrice(AmountFormatter.floatToFloatRounding(
                                                        ViatorUtil.doubleToFloat(viatorPricingMatrixResAgeBandPrice
                                                                .getPrices().get(0).getMerchantNetPrice())));
                                                price.setRetailPrice(AmountFormatter.floatToFloatRounding(
                                                        ViatorUtil.doubleToFloat(viatorPricingMatrixResAgeBandPrice
                                                                .getPrices().get(0).getPrice())));
                                                price.setPricingUnit(PlacePassPricingUnit.FLAT_RATE.name());
                                                //Viator should only have adult price in this case
                                                if (viatorPricingMatrixResAgeBandPrice
                                                        .getBandId() == PlacePassAgeBandType.ADULT.ageBandId) {
                                                    price.setPriceType(PlacePassAgeBandType.ADULT.name());
                                                    price.setDescription("Adult Price");
                                                    price = getAgeBandLimitDescription(algoliaProductDetails, price);
                                                    price.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
                                                } else {
                                                    continue;
                                                }

                                                //MinBuy will be the max of either min of this tourgrade option or this price
                                                price.setMinBuy(
                                                        Math.max(viatorPricingMatrixResAgeBandPrice.getMinimumCountRequired(),
                                                                viatorPrice.getMinNoOfTravellersRequiredForPrice()));
                                                price.setMaxBuy(
                                                        maxBuy == null ? -1 : maxBuy);
                                                price.setPriceGroupSortOrder(
                                                        viatorPrice.getSortOrder());
                                                maxBuy = (viatorPrice.getMinNoOfTravellersRequiredForPrice() > 1 ? viatorPrice.getMinNoOfTravellersRequiredForPrice() - 1 : 1);
                                                prices.add(price);
                                            }
                                        }
                                    }

                                }

                                ppProductOption.setPrices(prices);
                                productOptions.add(ppProductOption);
                            }
                        }

                    }
                }
                productOptionGroup.setProductOptionGroups(null);
                productOptionGroup.setName(PARENT_PRODUCT);
                productOptionGroup.setType(PARENT_PRODUCT);
                productOptionGroup.setProductOptions(productOptions);

                getProductOptionsRS.setProductOptionGroup(productOptionGroup);

                if (productOptions.size() < 1) {
                    resultType.setCode(1);
                    resultType.setMessage(BOOKING_OPTIONS_ARE_NOT_AVAILABLE_FOR_THIS_DATE);
                }
            } else {
                resultType.setCode(1);
                resultType.setMessage(BOOKING_OPTIONS_ARE_NOT_AVAILABLE_FOR_THIS_MONTH);
            }
        }

        getProductOptionsRS.setResultType(resultType);
        return getProductOptionsRS;
    }

    public static Price getAgeBandLimitDescription(GetAlgoliaProductDetailsRS algoliaProductDetails, Price price) {
        if (algoliaProductDetails != null && algoliaProductDetails.getEligibleAgeBands() != null) {
            for (AlgoliaProductAgeBand ageBand : algoliaProductDetails.getEligibleAgeBands()) {
                if (((price.getPriceType().equalsIgnoreCase(PlacePassAgeBandType.ADULT.name()))
                        && (ageBand.getType().equalsIgnoreCase(PlacePassAgeBandType.ADULT.name())))
                        || ((price.getPriceType().equalsIgnoreCase(PlacePassAgeBandType.CHILD.name()))
                                && (ageBand.getType().equalsIgnoreCase(PlacePassAgeBandType.CHILD.name())))
                        || ((price.getPriceType().equalsIgnoreCase(PlacePassAgeBandType.INFANT.name()))
                                && (ageBand.getType().equalsIgnoreCase(PlacePassAgeBandType.INFANT.name())))
                        || ((price.getPriceType().equals(PlacePassAgeBandType.SENIOR.name()))
                                && (ageBand.getType().equalsIgnoreCase(PlacePassAgeBandType.SENIOR.name())))
                        || ((price.getPriceType().equalsIgnoreCase(PlacePassAgeBandType.YOUTH.name()))
                                && (ageBand.getType().equalsIgnoreCase(PlacePassAgeBandType.YOUTH.name())))) {
                    price.setAgeFrom(ageBand.getMinAge());
                    price.setAgeTo(ageBand.getMaxAge());

                }
            }
        }
        return price;
    }

    public static GetProductReviewsRS toReviews(ViatorProductReviewsResponse viatorProductReviewsResponse) {

        GetProductReviewsRS getProductReviewsRS = new GetProductReviewsRS();
        ResultType resultType = ViatorUtil.getResultTypeObj(viatorProductReviewsResponse);
        getProductReviewsRS.setResultType(resultType);

        if (viatorProductReviewsResponse.getSuccess()) {
            List<Review> reviews = new ArrayList<>();

            for (ViatorProductReviewsResInfo productReview : viatorProductReviewsResponse.getData()) {
                Review review = new Review();
                review.setOwnerAvatarURL(productReview.getOwnerAvatarURL());
                review.setOwnerCountry(productReview.getOwnerCountry());
                review.setOwnerId(productReview.getOwnerId());
                review.setOwnerName(productReview.getOwnerName());
                review.setProductCode(productReview.getProductCode());
                review.setProductTitle(productReview.getProductTitle());
                review.setPublishedDate(productReview.getPublishedDate());
                review.setRating(productReview.getRating());
                review.setReview(productReview.getReview());
                review.setReviewId(productReview.getReviewId());
                review.setSubmissionDate(productReview.getSubmissionDate());
                review.setVendorFeedback(productReview.getViatorFeedback());
                review.setVendorNotes(productReview.getViatorNotes());
                review.setProductUrlName(productReview.getProductUrlName());

                reviews.add(review);

            }

            getProductReviewsRS.setReviews(reviews);
            getProductReviewsRS.setTotalReviewCount(viatorProductReviewsResponse.getTotalCount());
        }
        return getProductReviewsRS;
    }

    public static ViatorTourgradesRequest toTourgradesDefaultAgebandRequest(String productId, String date) {
        ViatorTourgradesRequest viatorTourgradesRequest = new ViatorTourgradesRequest();

        List<AgeBand> agebands = new ArrayList<>();
        AgeBand ageBand = new AgeBand();

        ageBand.setBandId(PlacePassAgeBandType.ADULT.ageBandId);
        ageBand.setCount(1);
        agebands.add(ageBand);

        viatorTourgradesRequest.setAgeBands(agebands);
        viatorTourgradesRequest.setBookingDate(date);
        viatorTourgradesRequest.setCurrencyCode("USD");
        viatorTourgradesRequest.setProductCode(productId);

        return viatorTourgradesRequest;
    }

    public static ViatorTourgradesRequest toTourgradesRequiredAgebandsRequest(String productId, String date,
            List<AgeBand> productRequiredAgebands) {
        ViatorTourgradesRequest viatorTourgradesRequest = new ViatorTourgradesRequest();

        viatorTourgradesRequest.setAgeBands(productRequiredAgebands);
        viatorTourgradesRequest.setBookingDate(date);
        viatorTourgradesRequest.setCurrencyCode("USD");
        viatorTourgradesRequest.setProductCode(productId);

        return viatorTourgradesRequest;
    }

    public static List<AgeBand> getProductRequiredAgeBands(List<ViatorTourgradesResAgeBandsRequired> list) {

        List<AgeBand> ageBands = new ArrayList<>();

        for (ViatorTourgradesResAgeBandsRequired viatorTourgradesResAgeBandsRequired : list) {

            AgeBand ageBand = new AgeBand();
            ageBand.setBandId(viatorTourgradesResAgeBandsRequired.getBandId());
            ageBand.setCount(viatorTourgradesResAgeBandsRequired.getMinimumCountRequired());

            ageBands.add(ageBand);

        }

        return ageBands;
    }

}
