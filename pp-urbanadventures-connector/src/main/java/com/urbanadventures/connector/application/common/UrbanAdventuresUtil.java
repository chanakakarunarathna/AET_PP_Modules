package com.urbanadventures.connector.application.common;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrbanAdventuresUtil {
    public static List<String> extractLiTagTextDataToList(String textWithLiTag) {

        List<String> listOfTexts = new ArrayList<>();
        String trimmedText = textWithLiTag.replaceAll("\r", "").replaceAll("\n", "").replaceAll("<ul>", "")
                .replaceAll("</ul>", "").trim();
        Pattern patternForTextWithLiTag = Pattern.compile("<li>(.+?)</li>");
        Matcher forTextWithLiTag = patternForTextWithLiTag.matcher(trimmedText);
        while (forTextWithLiTag.find()) {
            String textWithoutLiTag = forTextWithLiTag.group().replaceAll("<li>", "").replaceAll("</li>", "").trim();
            listOfTexts.add(textWithoutLiTag);
        }
        return listOfTexts;
    }

    public static List<String> extractPTagTextDataToList(String textWithPTag) {

        List<String> listOfTexts = new ArrayList<>();
        String trimmedText = textWithPTag.replaceAll("\r", "").replaceAll("\n", "").trim();
        Pattern patternForTextWithPTag = Pattern.compile("<p>(.+?)</p>");
        Matcher forTextWithPTag = patternForTextWithPTag.matcher(trimmedText);
        while (forTextWithPTag.find()) {
            String textWithoutPTag = forTextWithPTag.group().replaceAll("<p>", "").replaceAll("</p>", "").trim();
            listOfTexts.add(textWithoutPTag);
        }
        return listOfTexts;
    }

    public static String getFormattedDate(LocalDate localDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = localDate.format(formatter);
        return date;
    }

    public static String uaDateToPpDate(String uaDate) {

        DateTimeFormatter uaFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        DateTimeFormatter ppFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate date = LocalDate.parse(uaDate, uaFormatter);
        String ppDate = date.format(ppFormatter);

        return ppDate;
    }

    public static int getLastDayOfMonth(String date) {
        int lastDay = 0;
        try {
            Date parsedfromDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(parsedfromDate);
            lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
        }
        return lastDay;
    }

    public static BigDecimal getFormattedPrice(Float Price) {
        BigDecimal formattedPrice = new BigDecimal(0);

        try {

            formattedPrice = new BigDecimal(Price).setScale(2, BigDecimal.ROUND_HALF_UP);

        } catch (Exception e) {

        }
        return formattedPrice;

    }
}
