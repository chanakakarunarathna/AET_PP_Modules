package com.placepass.connector.citydiscovery.application.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.util.StringUtils;

import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsActivityCancellationPolicy;
import com.placepass.connector.common.product.CancellationRules;
import com.placepass.connector.common.product.Language;
import com.placepass.connector.common.product.Rules;

public class CityDiscoveryUtil {

    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");

    private static final String MILITARY_TIME_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    static final Comparator<ClsActivityCancellationPolicy> CANCELLATION_RULES_COMPARATOR = new Comparator<ClsActivityCancellationPolicy>() {

        public int compare(ClsActivityCancellationPolicy cp1, ClsActivityCancellationPolicy cp2) {
            return cp1.getDay().compareTo(cp2.getDay());
        }
    };

    public static String getMonthFromString(String date) {

        String month = null;

        try {
            Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(parsedDate);
            month = String.valueOf(c1.get(Calendar.MONTH) + 1);
        } catch (Exception e) {
            // TODO:handle exception
        }
        return month;
    }

    public static String getYearFromString(String date) {

        String year = null;

        try {
            Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(parsedDate);
            year = String.valueOf(c1.get(Calendar.YEAR));
        } catch (Exception e) {
            // TODO:handle exception
        }
        return year;
    }

    public static String getMonthFromDate(Date date) {

        String month = null;

        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            month = String.valueOf(c1.get(Calendar.MONTH) + 1);
        } catch (Exception e) {
            // TODO:handle exception
        }
        return month;
    }

    public static String getYearFromDate(Date date) {

        String year = null;

        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            year = String.valueOf(c1.get(Calendar.YEAR));
        } catch (Exception e) {
        }
        return year;
    }

    public static Float doubleToFloat(Double amount) {
        return new BigDecimal(String.valueOf(amount)).setScale(2, RoundingMode.CEILING).floatValue();
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(String date) {

        GregorianCalendar c = new GregorianCalendar();

        Date parsedDate = null;
        XMLGregorianCalendar xmlGregorianCalendar = null;

        try {

            parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            c.setTime(parsedDate);
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        return xmlGregorianCalendar;
    }

    public static String convertXmlGregorianToString(XMLGregorianCalendar xc) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gCalendar = xc.toGregorianCalendar();

        // Converted to date object
        Date date = gCalendar.getTime();

        // Formatted to String value
        String dateString = df.format(date);

        return dateString;
    }

    public static String convertDateToString(Date date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = df.format(date);

        return dateString;
    }

    public static List<Language> getLanguages(String activityLanguages) {

        List<Language> languages = new ArrayList<>();

        List<String> langList = Arrays.asList(activityLanguages.split(";"));

        for (String lang : langList) {

            Language language = new Language();
            language.setCode(lang);
            language.setName(lang);

            if (StringUtils.hasText(lang)) {
                languages.add(language);
            }
        }

        return languages;
    }

    public static String convertLocalDateToString(LocalDate localDate)
            throws ParseException, DatatypeConfigurationException {

        String formattedStringDate = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formattedStringDate = localDate.format(formatter);

        return formattedStringDate;
    }

    /**
     *
     * @param pageNumber
     * @param hitsPerPage
     * @param list
     * @return the paginated list
     */
    public static <T> List<T> paginate(int pageNumber, int hitsPerPage, List<T> list) {

        // Assuming that the page number starts from 0 onwards.
        int fromIndex = pageNumber * hitsPerPage;
        int toIndex = fromIndex + hitsPerPage;

        if (toIndex > list.size()) {
            toIndex = list.size();
        }

        if (fromIndex > toIndex) {
            fromIndex = toIndex;
        }

        return list.subList(fromIndex, toIndex);
    }

    public static List<String> getActivityPriceOptionDepatuerTimes(String activityPriceOptionDepartureTime) {

        List<String> activityPriceOptionDepatuerTimes = new ArrayList<>();

        List<String> depatuerTimes = Arrays.asList(activityPriceOptionDepartureTime.split(";"));

        for (String time : depatuerTimes) {
            activityPriceOptionDepatuerTimes.add(time.trim());
        }

        return activityPriceOptionDepatuerTimes;
    }

    public static String[] getProductTypeAndDepatuerTime(String uuids) {
        return uuids.contains("&") ? uuids.split("&") : new String[] {};
    }

    public static int getMonthfromDateTimeString(String dateTimeString) {
        String dateString = dateTimeString.split(" ")[0];
        LocalDate localDate = LocalDate.parse(dateString, DATE_FORMATTER);
        return localDate.getMonthValue();
    }

    public static int getYearfromDateTimeString(String dateTimeString) {
        String dateString = dateTimeString.split(" ")[0];
        LocalDate localDate = LocalDate.parse(dateString, DATE_FORMATTER);
        return localDate.getYear();
    }

    public static boolean isPreviousDate(String dateTimeString) {

        boolean isPreviousDate = false;
        LocalDate currentLocalDate = LocalDate.now();

        String dateString = dateTimeString.split(" ")[0];
        LocalDate localDate = LocalDate.parse(dateString, DATE_FORMATTER);

        if (localDate.isBefore(currentLocalDate)) {
            isPreviousDate = true;
        }

        return isPreviousDate;
    }

    public static CancellationRules createCancellationRules(
            List<ClsActivityCancellationPolicy> activityCancellationPolicies) {
        CancellationRules cancellationRules = new CancellationRules();
        List<Rules> rules = new ArrayList<Rules>();
        // here system sort CancellationPolicies DECS way
        Collections.sort(activityCancellationPolicies, Collections.reverseOrder(CANCELLATION_RULES_COMPARATOR));

        if (activityCancellationPolicies.isEmpty()) {
            Rules rule = new Rules();
            rule.setRefundMultiplier(0);
            rule.setMaxHoursInAdvance(0);
            rule.setMinHoursInAdvance(0);
            rules.add(rule);
        } else {

            int maxHoursInAdvance = 0;

            for (ClsActivityCancellationPolicy activityCancellationPolicy : activityCancellationPolicies) {

                Rules rule = new Rules();
                rule.setMinHoursInAdvance(activityCancellationPolicy.getDay() * 24);

                if (maxHoursInAdvance == 0) {
                    rule.setMaxHoursInAdvance(null);
                } else {
                    rule.setMaxHoursInAdvance(maxHoursInAdvance);
                }
                rule.setRefundMultiplier(
                        BigDecimal.valueOf(1 - (activityCancellationPolicy.getPercentage() * .01)).floatValue());

                rules.add(rule);

                maxHoursInAdvance = (activityCancellationPolicy.getDay() * 24 - 1);

            }
        }

        cancellationRules.setRules(rules);

        return cancellationRules;
    }

    public static boolean validateMilitaryTimeFormat(String time) {

        Pattern pattern = Pattern.compile(MILITARY_TIME_PATTERN);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();

    }

    public static String militaryTimeToMeridiemTime(String activityPriceOptionDepartureTime) throws ParseException {

        String meridiamTime = null;

        SimpleDateFormat militaryTime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formattedMilitaryTime = new SimpleDateFormat("hh:mm a");

        meridiamTime = formattedMilitaryTime.format(militaryTime.parse(activityPriceOptionDepartureTime));

        return meridiamTime;
    }


}