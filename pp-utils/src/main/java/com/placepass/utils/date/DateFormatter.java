package com.placepass.utils.date;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateFormatter {

    public static Date getDate(LocalDate localDate) {

        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public static int getHoursBetweenDates(Instant fromDateTime, Instant toDateTime, int bigDecimalRoundMode) {

        long minutesBetweenDates = ChronoUnit.MINUTES.between(toDateTime, fromDateTime);

        BigDecimal bdMinutes = new BigDecimal(minutesBetweenDates);
        BigDecimal bdDevisor = new BigDecimal("60");
        BigDecimal bdResult = bdMinutes.divide(bdDevisor, 0, bigDecimalRoundMode);
        int hours = bdResult.intValue();

        return hours;
    }

}
