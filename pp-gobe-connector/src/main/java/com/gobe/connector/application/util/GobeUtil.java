package com.gobe.connector.application.util;

import com.gobe.connector.domain.gobe.availability.TimeSlot;
import com.gobe.connector.domain.gobe.price.GobePrice;
import com.gobe.connector.domain.gobe.product.GobeProduct;
import com.gobe.connector.domain.gobe.product.Variant;
import com.placepass.connector.common.product.Price;
import com.placepass.connector.common.booking.BookingOption;
import com.placepass.connector.common.common.ResultType;
import com.placepass.utils.ageband.PlacePassAgeBandType;
import org.springframework.security.access.method.P;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GobeUtil {

    // return unique dates which the product has been scheduled from GobeSchedule call
    public static List<String> getGobeAvailableDatesFromSchedule(List<TimeSlot> schedule) {
        ArrayList<String> availableDates = new ArrayList<>();

        for (TimeSlot timeSlot : schedule) {
            // get the date from startTime String. Ex: 2017-06-15T10:00:00Z
            String startTimeDate = timeSlot.getStartTime().substring(0, timeSlot.getStartTime().indexOf("T"));
            if (!availableDates.contains(startTimeDate)) {
                availableDates.add(startTimeDate);
            }
        }

        return availableDates;
    }

    public static Float doubleToFloat(Double amount) {
        return new BigDecimal(String.valueOf(amount)).setScale(2, RoundingMode.CEILING).floatValue();
    }

    // return unique dates which the product has been scheduled from GobeSchedule call
    public static Price getGobeAgeFromGobeVariant(Variant gobeVariant) {
        Price price = new Price();

        if (gobeVariant.getName() != null) {
            int ageFrom = -1;
            int ageTo = -1;
            String variantName = gobeVariant.getName().toLowerCase();
            String temp = "";
            for (int i = 0; i < variantName.length(); i++) {
                if (Character.isDigit(variantName.charAt(i)) && ageFrom == -1) {
                    temp += variantName.charAt(i);
                }
                if (!Character.isDigit(variantName.charAt(i)) && ageFrom == -1 && !temp.equals("")) {
                    ageFrom = Integer.parseInt(temp);
                    temp = "";
                }
                if (Character.isDigit(variantName.charAt(i)) && ageFrom != -1 && ageTo == -1) {
                    temp += variantName.charAt(i);
                }
                if (!Character.isDigit(variantName.charAt(i)) && ageFrom != -1 && !temp.equals("") && ageTo == -1) {
                    ageTo = Integer.parseInt(temp);
                    break;
                }
            }

            if (ageFrom != -1 && ageTo != -1) {
                price.setAgeFrom(ageFrom);
                price.setAgeTo(ageTo);
            }
        }
        return price;
    }

    // take in the list of productoptionid prices and a quantity, return the price matches for that quantity
    public static float getGobePriceForQuantity(int quantity, String productOptionId, List<GobePrice> gobePrices) {
        // sort the prices by productOptionId and minquantity
        gobePrices.sort(new Comparator<GobePrice>() {
            @Override
            public int compare(GobePrice price1, GobePrice price2) {
                if (price1.getTourId().compareTo(price2.getTourId()) > 0) {
                    return 1;
                } else if (price1.getTourId().compareTo(price2.getTourId()) < 0) {
                    return -1;
                } else {
                    if (price1.getMinQuantity() > price2.getMinQuantity()) {
                        return 1;
                    } else if (price1.getMinQuantity() < price2.getMinQuantity()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        });

        Float price = 0f;

        for (GobePrice gobePrice : gobePrices) {
            if (gobePrice.getTourId().equals(productOptionId)) {
                if (quantity >= gobePrice.getMinQuantity()) {
                    price = Float.parseFloat(gobePrice.getPrice());
                }
            }
        }
        return price;
    }

    /*
     * //take in a list of prices, current group order and max capacity of product then return a list of combinations
     * //for the product that below the max capacity public static List<Price>
     * getPriceCombinationBelowCapacity(List<Price> prices, int maxCap, int groupSortOrder) { List<Price> result = new
     * ArrayList<>(); //only process if the cap is less than 9 if (maxCap >= 9) { return prices; } else { int
     * maxTotalQuantity = 0; int minTotalQuantity = 0; //check if user can buy more than maxCap with this combination
     * for (Price price : prices) { if (price.getMaxBuy() == -1) { maxTotalQuantity += 10; } else { maxTotalQuantity +=
     * price.getMaxBuy(); } minTotalQuantity += price.getMinBuy(); } //if user can not buy more than maxCap return if
     * (maxTotalQuantity <= maxCap) { return prices; } else if (minTotalQuantity >= maxCap) { if (minTotalQuantity ==
     * maxCap) { for (Price price: prices) { price.setMaxBuy(price.getMinBuy()); } return prices; } else { return new
     * ArrayList<>(); } } //else calculate more combinations else { int maxQuantity = maxCap - minTotalQuantity; for
     * (int quantity = 0; quantity <= maxQuantity; quantity++) {
     * 
     * } }
     * 
     * } return result; }
     * 
     * public static List<Price> getPriceCombinationBelowCapacityHelper(List<Price> prices, int remainingQuantity, int
     * groupSortOrder, int priceIndex) { List<Price> result = new ArrayList<>(); if (prices.size() > priceIndex) { for
     * (int i = 0; i <= remainingQuantity; i++) { if (prices.get(priceIndex).getMaxBuy() >= i) { Price price = new
     * Price(prices.get(priceIndex)); price.setMinBuy(i); price.setMaxBuy(i); if (prices.size() - 1 == priceIndex) {
     * price.setPriceGroupSortOrder(groupSortOrder++); } List<Price> listOfNextOptions =
     * getPriceCombinationBelowCapacityHelper(prices, remainingQuantity - i, groupSortOrder, priceIndex + 1); for (Price
     * nextOption: listOfNextOptions) { Price copyOrPrice = new Price(price);
     * copyOrPrice.setPriceGroupSortOrder(nextOption.getPriceGroupSortOrder()); } } } } return result; }
     */

    /**
     * A list of prices Adult/Child/Infant/Senior, if in a combination there is an adult and a senior, create 2
     * combinations one for zero adult minbuy (1 senior minBuy) and one for zero senior minbuy (1 adult minBuy) return
     * the next groupSortOrder
     */
    public static int getCombinationWithZeroMinBuy(List<Price> prices, int groupSortOrder) {
        boolean oneAdultFound = false;
        boolean oneSeniorFound = false;
        for (Price price : prices) {
            if (price.getAgeBandId() == PlacePassAgeBandType.ADULT.ageBandId && price.getMinBuy() == 1) {
                oneAdultFound = true;
                if (oneSeniorFound) {
                    price.setMinBuy(0);
                }
            }
            if (price.getAgeBandId() == PlacePassAgeBandType.SENIOR.ageBandId && price.getMinBuy() == 1) {
                oneSeniorFound = true;
                if (oneAdultFound) {
                    price.setMinBuy(0);
                }
            }
            if (price.getMinBuy() == 1 && price.getAgeBandId() != PlacePassAgeBandType.ADULT.ageBandId
                    && price.getAgeBandId() != PlacePassAgeBandType.SENIOR.ageBandId) {
                price.setMinBuy(0);
            }
        }
        if (oneAdultFound && oneSeniorFound) {
            groupSortOrder++;
            int listLength = prices.size();
            for (int i = 0; i < listLength; i++) {
                Price price = new Price(prices.get(i));
                price.setPriceGroupSortOrder(groupSortOrder);
                if (price.getAgeBandId() == PlacePassAgeBandType.ADULT.ageBandId
                        || price.getAgeBandId() == PlacePassAgeBandType.SENIOR.ageBandId) {
                    if (price.getMinBuy() == 1) {
                        price.setMinBuy(0);
                    } else {
                        price.setMinBuy(1);
                    }
                }
                prices.add(price);
            }
        }
        return groupSortOrder;
    }

    // TODO:later handle cases that doesn't follow our ageband
    // Check and return if the product doesn't follow our ageband modal
    // Our age band modal is (Adult, Child, Infant..)
    public static boolean detectGobeAgeBandModel(GobeProduct gobeProduct) {

        // follow our model, no repeat ageband
        boolean followAgeBandModal = true;
        // not follow our model, no repeat ageband
        boolean followAgeBandModalExtent = true;

        if (gobeProduct.getTravelerDetailsType() != null) {
            String filterBookingQuestionProducts = gobeProduct.getTravelerDetailsType().toUpperCase();
            if (filterBookingQuestionProducts.equals("ALL_GUESTS_INFO_PASSPORT")
                    || filterBookingQuestionProducts.equals("ALL_GUESTS_WEIGHT_AGE")) {
                followAgeBandModal = false;
                followAgeBandModalExtent = false;
            }
        }

        // init gobe variants
        List<Variant> gobeVariants = new ArrayList<>();
        if (gobeProduct.getVariants() != null) {
            gobeVariants = gobeProduct.getVariants();
        }

        boolean adultFound = false;
        boolean childFound = false;
        boolean infantFound = false;
        boolean seniorFound = false;
        boolean youthFound = false;
        boolean guestFound = false;
        boolean travellerFound = false;

        for (Variant variant : gobeVariants) {
            String variantName = variant.getName().toLowerCase();

            // not follow our model, but no repeat cases
            if (!variantName.startsWith("adult") && !variantName.startsWith("child")
                    && !variantName.startsWith("infant") && !variantName.startsWith("senior")
                    && !variantName.startsWith("youth")) {
                followAgeBandModal = false;
            }
            if (variantName.startsWith("adult")) {
                // repeated adult case
                if (adultFound) {
                    followAgeBandModal = false;
                    followAgeBandModalExtent = false;
                }
                adultFound = true;
            }
            if (variantName.startsWith("youth")) {
                // repeated youth case
                if (youthFound) {
                    followAgeBandModal = false;
                    followAgeBandModalExtent = false;
                }
                youthFound = true;
            }
            if (variantName.startsWith("child")) {
                // repeat child
                if (childFound) {
                    followAgeBandModal = false;
                    followAgeBandModalExtent = false;
                }
                childFound = true;
            }
            if (variantName.startsWith("infant")) {
                // repeat infant
                if (infantFound) {
                    followAgeBandModal = false;
                    followAgeBandModalExtent = false;
                }
                infantFound = true;
            }
            if (variantName.startsWith("senior")) {
                // repeat senior case
                if (seniorFound) {
                    followAgeBandModal = false;
                    followAgeBandModalExtent = false;
                }
                seniorFound = true;
            }
            if (variantName.startsWith("traveler")) {
                // repeated traveller case
                if (travellerFound || guestFound) {
                    followAgeBandModal = false;
                    followAgeBandModalExtent = false;
                }
                travellerFound = true;
            }
            if (variantName.startsWith("guest")) {
                // repeated guest case
                if (guestFound || travellerFound) {
                    followAgeBandModal = false;
                    followAgeBandModalExtent = false;
                }
                guestFound = true;
            }
        }

        /*
         * // case when follow (adult, senior, child...) but has some extra are not (observer, ...), // we will try to
         * remove those extra if (followAgeBandModalExtent && !followAgeBandModal && (adultFound || seniorFound)) {
         * Iterator<Variant> variantIterator = gobeVariants.iterator(); while (variantIterator.hasNext()) { String
         * variantName = variantIterator.next().getName().toLowerCase(); if (!variantName.startsWith("adult") &&
         * !variantName.startsWith("child") && !variantName .startsWith("infant") && !variantName.startsWith("senior"))
         * { variantIterator.remove(); } } followAgeBandModal = true; }
         */

        // case when there are only traveller or guest no repeat
        if (followAgeBandModalExtent && !followAgeBandModal && (travellerFound || guestFound)) {
            if (gobeVariants.size() == 1) {
                followAgeBandModal = true;
            }
        }

        return followAgeBandModal;
    }

    // return the status code base on Gobe order status
    public static int mappingGobeStatus(String orderStatus) {
        String status = orderStatus.toUpperCase();
        if ("CREATED,PROCESSING,CONFIRMED,CHECKED_VALID,ORDER_SPLIT_VALID,ON_VALIDATION,PAYMENT_AUTHORIZED,PRORATED_DISCOUNTS_CALCULATED,PAYMENT_AMOUNT_RESERVED,ORDER_BALANCE_SET,PAYMENT_CAPTURED,WAIT_FRAUD_MANUAL_CHECK,FRAUD_CHECKED,ORDER_SPLIT,PENDING_TOUR_OPERATOR_RESPONSE"
                .contains(status)) {
            // PENDING
            return 101;
        } else if ("COMPLETED".equals(status)) {
            // SUCCESS
            return 100;
        } else if ("ERROR,SUSPENDED,PROCESSING_ERROR,PAYMENT_NOT_AUTHORIZED,CHECKED_INVALID,ORDER_SPLIT_INVALID,PRORATED_DISCOUNTS_NOT_CALCULATED,PAYMENT_AMOUNT_NOT_RESERVED,PAYMENT_NOT_CAPTURED"
                .contains(status)) {
            // FAIL
            return 112;
        } else {
            return 112;
        }
    }


    //format 24 to 12 hour time
    public static String formatTimeToMeridiemTime(String activityPriceOptionDepartureTime) throws ParseException {

        String meridiamTime = null;

        SimpleDateFormat timeString = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formattedTime = new SimpleDateFormat("hh:mm a");

        meridiamTime = formattedTime.format(timeString.parse(activityPriceOptionDepartureTime));

        return meridiamTime;
    }
}
