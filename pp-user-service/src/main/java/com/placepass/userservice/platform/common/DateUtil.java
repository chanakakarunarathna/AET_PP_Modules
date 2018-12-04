package com.placepass.userservice.platform.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;

/**
 * The Class DateUtil.
 */
public class DateUtil {

    /** The Constant DATE_FORMAT. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /** The Constant DATE_PATTERN. */
    private static final String DATE_PATTERN = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

    /** The logger. */
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * Convert to date.
     *
     * @param dateText the date text
     * @return the date
     */
    public static Date convertToDate(String dateText) {

        Date date = null;

        if (StringUtils.hasText(dateText)) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            try {
                date = simpleDateFormat.parse(dateText);
            } catch (Exception e) {
                throw new IllegalArgumentException("Date Format is not valid.(yyyy-mm-dd)", e);
            }
        }

        return date;
    }

    /**
     * Convert to date text.
     *
     * @param date the date
     * @return the string
     */
    public static String convertToDateText(Date date) {

        if (date == null) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateText = simpleDateFormat.format(date);
        return dateText;
    }

    
    /**
     * Validate date of birth.
     *
     * @param dateOfBirth the date of birth
     * @return the date
     */
    public static Date validateDateOfBirth(String dateOfBirth) {

        // Checking date format.(Should be yyyy-mm-dd)
        if (!isValidDateFormat(dateOfBirth)) {
            logger.error("Error occurred due to, invalid date format.Date format should be yyyy-mm-dd");
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_DATE_FORMAT.toString(),
                    PlacePassExceptionCodes.INVALID_DATE_FORMAT.getDescription());
        }

        Calendar calDOB = Calendar.getInstance();
        Calendar calToday = Calendar.getInstance();

        calDOB.setTime(convertToDate(dateOfBirth));

        Date dob = calDOB.getTime();
        Date today = calToday.getTime();

        if (!dob.before(today)) {

            logger.error("Error occurred due to, invalid date date of birth ");
            throw new BadRequestException(PlacePassExceptionCodes.INVALID_DATE_OF_BIRTH.toString(),
                    PlacePassExceptionCodes.INVALID_DATE_OF_BIRTH.getDescription());
        }

        return dob;
    }

    public static boolean isValidDateFormat(String dateOfBirth) {

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(DATE_PATTERN);
        matcher = pattern.matcher(dateOfBirth);
        return matcher.matches();

    }

}
