package com.viator.connector.application.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.placepass.connector.common.common.ResultType;
import com.viator.connector.domain.viator.common.ViatorGenericResponse;

public class ViatorUtil {

    public static ResultType getResultTypeObj(ViatorGenericResponse response) {

        ResultType resultType = new ResultType();
        if (response.getSuccess()) {
            resultType.setCode(0);
            resultType.setMessage("");
        } else {
            resultType.setCode(1);
            String errorMessage = "";
            for (String errorMsg : response.getErrorMessageText()) {
                errorMessage = errorMsg + errorMessage;
            }
            resultType.setMessage(errorMessage);
        }
        return resultType;
    }

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
            // TODO:handle exception
        }
        return year;
    }

    public static Float doubleToFloat(Double amount) {
        return new BigDecimal(String.valueOf(amount)).setScale(2, RoundingMode.CEILING).floatValue();
    }

}
