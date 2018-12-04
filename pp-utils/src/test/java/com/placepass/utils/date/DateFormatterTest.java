package com.placepass.utils.date;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.Test;

public class DateFormatterTest {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private LocalDate getLocalDate(String dateStr) {
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        return localDate;
    }

    private Date getDate(String dateStr) {
        Date date = null;

        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Test
    public void testGetDate() {

        String dateStr = "2017-10-01";

        LocalDate localDate = getLocalDate(dateStr);
        Date date = getDate(dateStr);

        assertEquals(date, DateFormatter.getDate(localDate));
    }

    /**
     * Expected hours : 24
     */
    @Test
    public void testGetHoursBetweenDates() {

        Instant toTime = this.getDate("2017-10-10").toInstant();
        Instant fromTime = this.getDate("2017-10-09").toInstant();

        int hours = DateFormatter.getHoursBetweenDates(toTime, fromTime, BigDecimal.ROUND_DOWN);

        assertEquals(24, hours);
    }
    
    /**
     * Actual hours - 24.5 
     * Expected hours - 24
     */
    @Test
    public void testGetHoursWhenMinutesEqualToThirty() {

        Instant toTime = this.getDate("2017-10-10").toInstant();
        Instant fromTime = this.getDate("2017-10-09").toInstant();

        toTime = toTime.plus(30, ChronoUnit.MINUTES);

        int hours = DateFormatter.getHoursBetweenDates(toTime, fromTime, BigDecimal.ROUND_DOWN);

        assertEquals(24, hours);
    }
    
    /**
     * Actual hours - 24.58 
     * Expected hours - 24
     */
    @Test
    public void testGetHoursWhenMinutesGreaterThanThirty() {

        Instant toTime = this.getDate("2017-10-10").toInstant();
        Instant fromTime = this.getDate("2017-10-09").toInstant();

        toTime = toTime.plus(35, ChronoUnit.MINUTES);

        int hours = DateFormatter.getHoursBetweenDates(toTime, fromTime, BigDecimal.ROUND_DOWN);

        assertEquals(24, hours);
    }
    
    /**
     * Actual hours - 23.31 
     * Expected hours - 23
     */
    @Test
    public void testGetHoursWhenMinutesLessThanThirty() {

        Instant toTime = this.getDate("2017-10-10").toInstant();
        Instant fromTime = this.getDate("2017-10-09").toInstant();

        toTime = toTime.minus(29, ChronoUnit.MINUTES);

        int hours = DateFormatter.getHoursBetweenDates(toTime, fromTime, BigDecimal.ROUND_DOWN);

        assertEquals(23, hours);
    }

}
