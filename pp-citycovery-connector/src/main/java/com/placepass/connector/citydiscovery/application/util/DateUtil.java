package com.placepass.connector.citydiscovery.application.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.placepass.connector.citydiscovery.domain.citydiscovery.activity.ActivityPriceDays;

public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public DateUtil() {
    }

    public static List<Date> getDatesBetween(Date startDate, Date endDate) {

        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }

    public static List<Date> getAvailableDates(int month, int year, Date activityStartDate, Date activityEndDate,
            List<Integer> availableDays, List<String> blockoutDates) {

        List<Date> availableDates = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        Date today = new Date();
        calendar.setTime(today);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        today = calendar.getTime();

        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endDate = calendar.getTime();

        if (endDate.before(today)) {
            return null;
        }

        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate)) {
            Date result = calendar.getTime();

            if (result.before(activityStartDate) || result.after(activityEndDate) || result.before(today)) {
                calendar.add(Calendar.DATE, 1);

                continue;

            }

            if (availableDays != null && availableDays.contains(calendar.get(Calendar.DAY_OF_WEEK))) {
                if (!matchedWithBlackoutDates(blockoutDates, result)) {
                    availableDates.add(result);
                }

            }
            calendar.add(Calendar.DATE, 1);
        }

        logger.info("Available dates" + availableDates);

        return availableDates;

    }

    static boolean matchedWithBlackoutDates(List<String> blackoutDates, Date date) {

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dt.format(date);

        for (String blackoutDate : blackoutDates) {

            if (dateStr.equals(blackoutDate)) {
                return true;
            }

            String[] componentsOfBlackoutDate = blackoutDate.split("-");
            String[] componentsOfDate = dateStr.split("-");

            if (componentsOfBlackoutDate.length != 3) {
                continue;
            }

            if ((componentsOfBlackoutDate[0].equals("%") || (componentsOfBlackoutDate[0].equals(componentsOfDate[0])))
                    && (componentsOfBlackoutDate[1].equals("%")
                            || (componentsOfBlackoutDate[1].equals(componentsOfDate[1])))
                    && (componentsOfBlackoutDate[2].equals("%")
                            || (componentsOfBlackoutDate[2].equals(componentsOfDate[2])))) {

                return true;

            }

        }

        return false;
    }

    public static List<Integer> getAvailableWeekdays(ActivityPriceDays activityPriceDays) {

        List<Integer> availableWeekDays = new ArrayList<>();

        if (activityPriceDays.isSunday())
            availableWeekDays.add(1);
        if (activityPriceDays.isMonday())
            availableWeekDays.add(2);
        if (activityPriceDays.isTuesday())
            availableWeekDays.add(3);
        if (activityPriceDays.isWednesday())
            availableWeekDays.add(4);
        if (activityPriceDays.isThursday())
            availableWeekDays.add(5);
        if (activityPriceDays.isFriday())
            availableWeekDays.add(6);
        if (activityPriceDays.isSaturday())
            availableWeekDays.add(7);

        return availableWeekDays;
    }

    public static List<String> getBlackOutDates(String activityBlockOutdates) {
        
        //V1 supports only blockOutDates before | pipe line mark
        String formattedBlockoutDatesString = activityBlockOutdates.split("\\|")[0];
        String blockOutdateStr = formattedBlockoutDatesString.replace(" ", "");
        List<String> blackOutDates = Arrays.asList(blockOutdateStr.split(";"));
        
        return blackOutDates;
    }

    public static String toFormattedDate(String strDate) {

        Date date = null;
        String formattedDate = null;
        SimpleDateFormat dfOne = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dfTwo = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aa");

        try {

            date = dfTwo.parse(strDate);
            formattedDate = dfOne.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;

    }

    public static Calendar toStringdateToCalanderDate(String stringDate) {

        Date date = null;
        Calendar calendar = null;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aa");

        try {

            date = sdf.parse(stringDate);
            calendar = Calendar.getInstance();
            calendar.setTime(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;

    }

}
