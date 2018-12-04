package com.gobe.connector.application.product;

import com.gobe.connector.application.util.GobeUtil;
import com.gobe.connector.domain.gobe.availability.GobeScheduleRS;
import com.gobe.connector.domain.gobe.availability.TimeSlot;
import com.gobe.connector.domain.gobe.price.GobePrice;
import com.gobe.connector.domain.gobe.product.GobeProduct;
import com.gobe.connector.domain.gobe.product.Variant;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.product.*;
import com.placepass.utils.ageband.PlacePassAgeBandType;
import com.placepass.utils.currency.AmountFormatter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created on 8/9/2017.
 */
public class ProductTransformer {

    public static GetAvailabilityRS toProductAvailability(GobeScheduleRS gobeScheduleRS, GobeProduct gobeProduct) {
        //This function is called only when GobeSchedule call return with 200, resultType is default to success
        ResultType resultType = new ResultType();
        resultType.setCode(0);
        resultType.setMessage("");

        //init gobe variants
        List<Variant> gobeVariants = new ArrayList<>();
        if (gobeProduct.getVariants() != null) {
            gobeVariants = gobeProduct.getVariants();
        }

        List<Availability> ppGobeAvailableDates = new ArrayList<>();

        if (GobeUtil.detectGobeAgeBandModel(gobeProduct)) {
            if (gobeScheduleRS.getTimeSlots() != null) {

                List<String> gobeDates = GobeUtil.getGobeAvailableDatesFromSchedule(gobeScheduleRS.getTimeSlots());

                if (gobeDates != null) {
                    for (String gobeDate : gobeDates) {

                        Availability availability = new Availability();
                        availability.setDate(gobeDate);
                        availability.setSoldOut(false);

                        //No pricing or booking option is added to availability due to Gobe API's model
                        ppGobeAvailableDates.add(availability);
                    }
                }

            }
        }

        GetAvailabilityRS getAvailabilityRS = new GetAvailabilityRS();
        getAvailabilityRS.setResultType(resultType);
        getAvailabilityRS.setAvailability(ppGobeAvailableDates);
        return getAvailabilityRS;
    }


    public static GetProductOptionsRS toProductOptions(List<TimeSlot> availableTimeSlots, List<GobePrice> gobePrices,
            GobeProduct gobeProduct, String date) {

        GetProductOptionsRS getProductOptionsRS = new GetProductOptionsRS();
        //This function is called only when GobeSchedule call return with 200, resultType is default to success
        ResultType resultType = new ResultType();
        resultType.setCode(0);
        resultType.setMessage("");
        ProductOptionGroup productOptionGroup = new ProductOptionGroup();
        List<ProductOption> productOptions = new ArrayList<>();

        //init gobe variants
        List<Variant> gobeVariants = new ArrayList<>();
        if (gobeProduct.getVariants() != null) {
            gobeVariants = gobeProduct.getVariants();
        }

        //sorting prices in asc ageBand order as :adult price by des minBuy, child price by des minBuy...
        //This help setting maxBuy value
        gobePrices.sort(new Comparator<GobePrice>() {
            @Override public int compare(GobePrice price1, GobePrice price2) {
                if (price1.getTourId().compareTo(price2.getTourId()) < 0) {
                    return -1;
                } else if (price1.getTourId().compareTo(price2.getTourId()) > 0) {
                    return 1;
                } else {
                    if (price1.getMinQuantity() < price2.getMinQuantity()) {
                        return 1;
                    } else if (price1.getMinQuantity() > price2.getMinQuantity()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        });

        //iterate and remove duplicated prices
        Iterator<GobePrice> gobePriceIterator = gobePrices.iterator();
        GobePrice temp = null;
        while (gobePriceIterator.hasNext()) {
            GobePrice currentPrice = gobePriceIterator.next();
            if (temp != null && temp.getTourId().equals(currentPrice.getTourId())
                    && temp.getMinQuantity() == currentPrice.getMinQuantity()) {
                gobePriceIterator.remove();
            } else {
                temp = currentPrice;
            }
        }

        /*******************************************************************************
         * What to do when the data follow our modal correctly
         * *****************************************************************************
         */
        if (GobeUtil.detectGobeAgeBandModel(gobeProduct)) {
            //Adding product options
            for (TimeSlot timeSlot : availableTimeSlots) {

                ProductOption ppProductOption = new ProductOption();

                String startTime = timeSlot.getStartTime()
                        .substring(timeSlot.getStartTime().indexOf("T") + 1, timeSlot.getStartTime().length() - 4);

                if (startTime.equals("11:11")) {
                    ppProductOption.setName("General Admission");
                    ppProductOption.setStartTime("General Admission");
                } else {
                    try {
                        ppProductOption.setName(GobeUtil.formatTimeToMeridiemTime(startTime));
                        ppProductOption.setStartTime(GobeUtil.formatTimeToMeridiemTime(startTime));
                    } catch (ParseException ex) {
                        ppProductOption.setName(startTime);
                        ppProductOption.setStartTime(startTime);
                    }
                }
                ppProductOption.setEndTime("");

                ppProductOption.setProductOptionId(timeSlot.getGroupId());
                ppProductOption.setType("PRODUCT OPTION");

                // TODO:later handle properly
                ppProductOption.setAvailability(-1);

                List<Price> prices = new ArrayList<>();

                //Gobe charge by variant number: variant can be age (adult, child,...) or vehicle (sedan, van...)...
                int adultMaxBuy = -1;
                int childMaxBuy = -1;
                int infantMaxBuy = -1;
                int seniorMaxBuy = -1;
                int youthMaxBuy = -1;

                List<Price> adultPrices = new ArrayList<>();
                List<Price> childPrices = new ArrayList<>();
                List<Price> infantPrices = new ArrayList<>();
                List<Price> seniorPrices = new ArrayList<>();
                List<Price> youthPrices = new ArrayList<>();

                for (Variant variant : gobeVariants) {
                    //Check if this price belong to this variant
                    for (GobePrice gobePrice : gobePrices) {
                        if (gobePrice.getMinQuantity() <= 9) {
                            if (variant.getCode().equals(gobePrice.getTourId())) {
                                DateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                                //Try checking if this price is still in the effective date. Skip if it isn't
                                if (gobePrice.getStartDate() != null && gobePrice.getEndDate() != null) {
                                    try {
                                        Date startEffectiveDate = dateTimeFormatter.parse(gobePrice.getStartDate());
                                        Date endEffectiveDate = dateTimeFormatter.parse(gobePrice.getEndDate());
                                        Date requestedDate = dateFormatter.parse(date);
                                        //if price is outdated, continue to the next price
                                        if (requestedDate.after(endEffectiveDate) || requestedDate.before(startEffectiveDate)) {
                                            continue;
                                        }
                                    } catch (ParseException e) {
                                        //should log here in case Gobe's dates is incorrectly parsed
                                        continue;
                                    }
                                }

                                //new gobe price with ageFrom and ageTo
                                Price price = GobeUtil.getGobeAgeFromGobeVariant(variant);

                                price.setCurrencyCode(gobePrice.getCurrency());
                                price.setFinalPrice(AmountFormatter.floatToFloatRounding(GobeUtil.doubleToFloat(Double.parseDouble(gobePrice.getPrice()))));
                                price.setMerchantPrice(AmountFormatter.floatToFloatRounding(GobeUtil.doubleToFloat(Double.parseDouble(gobePrice.getCost()))));
                                price.setRetailPrice(AmountFormatter.floatToFloatRounding(GobeUtil.doubleToFloat(Double.parseDouble(gobePrice.getPrice()))));
                                //price.setDescription(variant.getName());
                                price.setMinBuy(gobePrice.getMinQuantity());

                                String variantName = variant.getName().toLowerCase();

                                if (variantName.startsWith("adult")) {
                                    price.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
                                    price.setPriceType(PlacePassAgeBandType.ADULT.name());
                                    price.setDescription("Adult Price");
                                    price.setMaxBuy(adultMaxBuy);
                                    adultMaxBuy = price.getMinBuy() - 1;
                                    adultPrices.add(0,price);
                                } else if (variantName.startsWith("youth")) {
                                    price.setAgeBandId(PlacePassAgeBandType.YOUTH.ageBandId);
                                    price.setPriceType(PlacePassAgeBandType.YOUTH.name());
                                    price.setDescription("Youth Price");
                                    price.setMaxBuy(youthMaxBuy);
                                    youthMaxBuy = price.getMinBuy() - 1;
                                    youthPrices.add(0,price);
                                } else if (variantName.startsWith("child")) {
                                    price.setAgeBandId(PlacePassAgeBandType.CHILD.ageBandId);
                                    price.setPriceType(PlacePassAgeBandType.CHILD.name());
                                    price.setDescription("Child Price");
                                    price.setMaxBuy(childMaxBuy);
                                    childMaxBuy = price.getMinBuy() - 1;
                                    childPrices.add(0,price);
                                } else if (variantName.startsWith("infant")) {
                                    price.setAgeBandId(PlacePassAgeBandType.INFANT.ageBandId);
                                    price.setPriceType(PlacePassAgeBandType.INFANT.name());
                                    price.setDescription("Infant Price");
                                    price.setMaxBuy(infantMaxBuy);
                                    infantMaxBuy = price.getMinBuy() - 1;
                                    infantPrices.add(0,price);
                                } else if (variantName.startsWith("senior")) {
                                    price.setAgeBandId(PlacePassAgeBandType.SENIOR.ageBandId);
                                    price.setPriceType(PlacePassAgeBandType.SENIOR.name());
                                    price.setDescription("Senior Price");
                                    price.setMaxBuy(seniorMaxBuy);
                                    seniorMaxBuy = price.getMinBuy() - 1;
                                    seniorPrices.add(0,price);
                                } else {
                                    //consider adult (this is guest and traveller)
                                    price.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
                                    price.setPriceType(PlacePassAgeBandType.ADULT.name());
                                    if (variantName.startsWith("guest")) {
                                        price.setDescription("Guest Price");
                                    } else if (variantName.startsWith("traveler")) {
                                        price.setDescription("Traveler Price");
                                    } else {
                                        //won't reach here
                                        price.setDescription("Adult Price");
                                    }
                                    price.setMaxBuy(adultMaxBuy);
                                    adultMaxBuy = price.getMinBuy() - 1;
                                    adultPrices.add(0,price);
                                }
                            }
                        }
                    }
                }

                //Setting descriptions for min and max capacity if needed
                String gobeDescription = "";
                if (!adultPrices.isEmpty()) {
                    int minQuantity = adultPrices.get(0).getMinBuy();
                    String priceType = adultPrices.get(0).getDescription().toLowerCase();
                    if (minQuantity > 1) {
                        if (priceType.startsWith("adult")) {
                            gobeDescription += "Minimum purchase of " + minQuantity + " adults. ";
                        } else {
                            //traveller and guest
                            if (priceType.startsWith("traveler")) {
                                gobeDescription += "Minimum purchase of " + minQuantity + " travelers. ";
                            } else if (priceType.startsWith("guest")) {
                                gobeDescription += "Minimum purchase of " + minQuantity + " guests. ";
                            } else {
                                //do nothing not supported
                            }
                        }
                    }
                }
                if (!youthPrices.isEmpty()) {
                    int minQuantity = youthPrices.get(0).getMinBuy();
                    if (minQuantity > 1) {
                        gobeDescription += "Minimum purchase of " + minQuantity + " youth. ";
                    }
                }
                if (!childPrices.isEmpty()) {
                    int minQuantity = childPrices.get(0).getMinBuy();
                    if (minQuantity > 1) {
                        gobeDescription += "Minimum purchase of " + minQuantity + " children. ";
                    }
                }
                if (!infantPrices.isEmpty()) {
                    int minQuantity = infantPrices.get(0).getMinBuy();
                    if (minQuantity > 1) {
                        gobeDescription += "Minimum purchase of " + minQuantity + " infants. ";
                    }
                }
                if (!seniorPrices.isEmpty()) {
                    int minQuantity = seniorPrices.get(0).getMinBuy();
                    if (minQuantity > 1) {
                        gobeDescription += "Minimum purchase of " + minQuantity + " seniors. ";
                    }
                }

                if (gobeProduct.getMaximumCapacity() != null) {
                    int maxCapacity = Integer.parseInt(gobeProduct.getMaximumCapacity());
                    if (maxCapacity < 9) {
                        gobeDescription += "Maximum purchase of " + maxCapacity + (maxCapacity > 1 ? " tickets. " : " ticket. ");
                    }
                }
                if (gobeProduct.getMinimumCapacity() != null) {
                    int minCapacity = Integer.parseInt(gobeProduct.getMinimumCapacity());
                    if (minCapacity > 1) {
                        gobeDescription += "Minimum purchase of " + minCapacity +  " tickets. ";
                    }
                }
                //add the new description
                ppProductOption.setDescription(gobeDescription);


                //Adding all combinations
                int groupSortOrder = 1;

                //adding all combinations between adult, child, senior, infant
                for (int adultCounter = 0; adultCounter <= adultPrices.size(); adultCounter++) {
                    for (int seniorCounter = 0; seniorCounter <= seniorPrices.size(); seniorCounter++) {
                        for (int childCounter = 0; childCounter <= childPrices.size(); childCounter++) {
                            for (int infantCounter = 0; infantCounter <= infantPrices.size(); infantCounter++) {
                                for (int youthCounter = 0; youthCounter <= youthPrices.size(); youthCounter++) {

                                    if (adultCounter < adultPrices.size() || seniorCounter < seniorPrices.size()) {

                                        if ((infantCounter < infantPrices.size() || infantPrices.isEmpty()) && (
                                                youthCounter < youthPrices.size() || youthPrices.isEmpty())&& (
                                                childCounter < childPrices.size() || childPrices.isEmpty()) && (
                                                seniorCounter < seniorPrices.size() || seniorPrices.isEmpty()) && (
                                                adultCounter < adultPrices.size() || adultPrices.isEmpty())) {

                                            List<Price> tempList = new ArrayList<>();

                                            if (!adultPrices.isEmpty()) {
                                                Price adultPrice = new Price(adultPrices.get(adultCounter));
                                                adultPrice.setPriceGroupSortOrder(groupSortOrder);
                                                tempList.add(adultPrice);
                                            }
                                            if (!seniorPrices.isEmpty()) {
                                                Price seniorPrice = new Price(seniorPrices.get(seniorCounter));
                                                seniorPrice.setPriceGroupSortOrder(groupSortOrder);
                                                tempList.add(seniorPrice);
                                            }
                                            if (!youthPrices.isEmpty()) {
                                                Price youthPrice = new Price(youthPrices.get(youthCounter));
                                                youthPrice.setPriceGroupSortOrder(groupSortOrder);
                                                tempList.add(youthPrice);
                                            }
                                            if (!childPrices.isEmpty()) {
                                                Price childPrice = new Price(childPrices.get(childCounter));
                                                childPrice.setPriceGroupSortOrder(groupSortOrder);
                                                tempList.add(childPrice);
                                            }
                                            if (!infantPrices.isEmpty()) {
                                                Price infantPrice = new Price(infantPrices.get(infantCounter));
                                                infantPrice.setPriceGroupSortOrder(groupSortOrder);
                                                tempList.add(infantPrice);
                                            }

                                            //make sure the list has all the appropriate zero minbuy
                                            groupSortOrder = GobeUtil
                                                    .getCombinationWithZeroMinBuy(tempList, groupSortOrder);

                                            //adding the combinations to list
                                            prices.addAll(tempList);

                                            groupSortOrder++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                //sort just for easier to see
                prices.sort(new Comparator<Price>() {
                    @Override public int compare(Price price1, Price price2) {
                        if (price1.getPriceGroupSortOrder() > price2.getPriceGroupSortOrder()) {
                            return 1;
                        } else if (price1.getPriceGroupSortOrder() < price2.getPriceGroupSortOrder()) {
                            return -1;
                        } else {
                            if (price1.getAgeBandId() < price2.getAgeBandId()) {
                                return -1;
                            } else if (price1.getAgeBandId() > price2.getAgeBandId()) {
                                return 1;
                            } else {
                                if (price1.getMinBuy() > price2.getMinBuy()) {
                                    return 1;
                                } else if (price1.getMinBuy() < price2.getMinBuy()) {
                                    return -1;
                                } else {
                                    return 0;
                                }
                            }
                        }
                    }
                });

                ppProductOption.setPrices(prices);
                productOptions.add(ppProductOption);
            }
        }

        /*******************************************************************************
         * What to do when the data not follow our model
         * *****************************************************************************
         */
        /*
        else if (!followAgeBandModal && followAgeBandModalExtent) {
            //Adding product options
            for (TimeSlot timeSlot : availableTimeSlots) {
                for (Variant variant : gobeVariants) {

                    ProductOption ppProductOption = new ProductOption();

                    String startTime = timeSlot.getStartTime().substring(timeSlot.getStartTime().indexOf("T") + 1,
                            timeSlot.getStartTime().indexOf(":00Z"));

                    ppProductOption.setName(startTime + " - " + variant.getName());
                    ppProductOption.setStartTime(startTime);
                    ppProductOption.setEndTime("");

                    String descriptionString = "";
                    if (gobeProduct.getMaximumCapacity() != null) {
                        if (Integer.parseInt(gobeProduct.getMaximumCapacity()) < 9) {
                            descriptionString += "Maximum";
                        }
                    }
                    //Not sure if they have description
                    ppProductOption.setDescription(variant.getName());

                    ppProductOption.setProductOptionId(timeSlot.getGroupId());
                    ppProductOption.setType("PRODUCT OPTION");

                    // TODO:later handle properly
                    ppProductOption.setAvailability(-1);

                    List<Price> prices = new ArrayList<>();

                    //Gobe charge by variant number: variant can be age (adult, child,...) or vehicle (sedan, van...)...
                    int maxBuy = -1;

                    //Adding all combinations
                    int groupSortOrder = 1;

                    //set up zero base price case
                    Price zeroPrice = new Price();
                    zeroPrice.setMaxBuy(0);
                    zeroPrice.setMinBuy(0);
                    zeroPrice.setFinalPrice(0);
                    zeroPrice.setMerchantPrice(0);
                    zeroPrice.setRetailPrice(0);
                    zeroPrice.setPriceGroupSortOrder(groupSortOrder);
                    zeroPrice.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
                    zeroPrice.setPriceType(PlacePassAgeBandType.ADULT.name());
                    zeroPrice.setDescription(variant.getName());
                    prices.add(zeroPrice);
                    groupSortOrder++;

                    //Check if this price belong to this variant
                    for (GobePrice gobePrice : gobePrices) {
                        if (variant.getCode().equals(gobePrice.getTourId())) {
                            DateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                            //Try checking if this price is still in the effective date. Skip if it isn't
                            if (gobePrice.getStartDate() != null && gobePrice.getEndDate() != null) {
                                try {
                                    Date startEffectiveDate = dateTimeFormatter.parse(gobePrice.getStartDate());
                                    Date endEffectiveDate = dateTimeFormatter.parse(gobePrice.getEndDate());
                                    Date requestedDate = dateFormatter.parse(date);
                                    //if price is outdated, continue to the next price
                                    if (requestedDate.after(endEffectiveDate) || requestedDate
                                            .before(startEffectiveDate)) {
                                        continue;
                                    }
                                } catch (ParseException e) {
                                    //should log here in case Gobe's dates is incorrectly parsed
                                    continue;
                                }
                            }
                            Price price = new Price();

                            price.setCurrencyCode(gobePrice.getCurrency());
                            price.setFinalPrice(AmountFormatter.floatToFloatRounding(
                                    GobeUtil.doubleToFloat(Double.parseDouble(gobePrice.getPrice()))));
                            price.setMerchantPrice(AmountFormatter.floatToFloatRounding(
                                    GobeUtil.doubleToFloat(Double.parseDouble(gobePrice.getPrice()))));
                            price.setRetailPrice(AmountFormatter.floatToFloatRounding(
                                    GobeUtil.doubleToFloat(Double.parseDouble(gobePrice.getPrice()))));
                            price.setDescription(variant.getName());
                            price.setMinBuy(gobePrice.getMinQuantity());

                            price.setAgeBandId(PlacePassAgeBandType.ADULT.ageBandId);
                            price.setPriceType(PlacePassAgeBandType.ADULT.name());
                            price.setDescription(variant.getName());
                            price.setPriceGroupSortOrder(groupSortOrder);
                            price.setMaxBuy(maxBuy);
                            maxBuy = price.getMinBuy() - 1;
                            prices.add(price);
                            groupSortOrder++;
                        }
                    }

                    //sort just for easier to see
                    prices.sort(new Comparator<Price>() {
                        @Override public int compare(Price price1, Price price2) {
                            if (price1.getPriceGroupSortOrder() > price2.getPriceGroupSortOrder()) {
                                return 1;
                            } else if (price1.getPriceGroupSortOrder() < price2.getPriceGroupSortOrder()) {
                                return -1;
                            } else {
                                if (price1.getAgeBandId() < price2.getAgeBandId()) {
                                    return -1;
                                } else if (price1.getAgeBandId() > price2.getAgeBandId()) {
                                    return 1;
                                } else {
                                    if (price1.getMinBuy() > price2.getMinBuy()) {
                                        return 1;
                                    } else if (price1.getMinBuy() < price2.getMinBuy()) {
                                        return -1;
                                    } else {
                                        return 0;
                                    }
                                }
                            }
                        }
                    });

                    ppProductOption.setPrices(prices);
                    productOptions.add(ppProductOption);
                }
            }
        }
        */
        //Not follow any model and have repeated ageband
        else {
            //do nothing
        }

        productOptionGroup.setProductOptionGroups(null);
        productOptionGroup.setName("PARENT PRODUCT");
        productOptionGroup.setType("PARENT PRODUCT");
        productOptionGroup.setProductOptions(productOptions);

        getProductOptionsRS.setProductOptionGroup(productOptionGroup);

        if (productOptions.size() < 1) {
            resultType.setCode(1);
            resultType.setMessage("Booking Options are not available for this date.");
        }

        getProductOptionsRS.setResultType(resultType);
        return getProductOptionsRS;
    }
}
