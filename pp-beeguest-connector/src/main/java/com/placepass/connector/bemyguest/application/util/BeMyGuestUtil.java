package com.placepass.connector.bemyguest.application.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class BeMyGuestUtil {

    public static int getMonthfromString(String dateString) {
        LocalDate localDate = LocalDate.parse(dateString);
        return localDate.getMonthValue();
    }

    public static int getYearfromString(String dateString) {
        LocalDate localDate = LocalDate.parse(dateString);
        return localDate.getYear();
    }

    public static Float doubleToFloat(Double amount) {
        return new BigDecimal(String.valueOf(amount)).setScale(2, RoundingMode.CEILING).floatValue();
    }

    public static List<String> getStringListFromString(String str) {
        String strArray[] = str.split("\n");
        List<String> strList = Arrays.asList(strArray);
        return strList;
    }

    public static String[] getProductTypeAndTimeSlotUUIDs(String uuids) {
        return uuids.contains("&") ? uuids.split("&") : new String[] {};
    }

}
