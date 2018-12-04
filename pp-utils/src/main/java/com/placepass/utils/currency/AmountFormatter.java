package com.placepass.utils.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Currency;

public class AmountFormatter {

    private AmountFormatter() {
    }

    /**
     * Gets the lowest unit.
     *
     * @param amount the amount
     * @param currencyCode the currency code
     * @return the lowest unit
     */
    public static int getLowestUnit(BigDecimal amount, String currencyCode) {

        // (amount.scale() > 2) type of check should be performed prior to
        // conversion (maybe using regex??) and log an
        // error and do not throw exception if regex validation fails (it's a
        // business rule we need to confirm with
        // placepass). For demo purposes, we can throw an exception.
        /* logger.debug("Calculating lowest unit for amount: {}, currency: {}", amount, currencyCode); */

        // FIXME - this might change depending on the currency code. (for the
        // moment we support only USD)
        if (amount.scale() > 2) {
            throw new RuntimeException("Provided amount is not supported : " + amount);
        }

        Currency currency = Currency.getInstance(currencyCode);
        int noOfDecimals = currency.getDefaultFractionDigits();
        BigDecimal multiplicand = new BigDecimal(10).pow(noOfDecimals);

        /* logger.debug("Calculated lowest unit amount: {}", lowestUnitAmt); */
        return amount.multiply(multiplicand).intValue();

    }

    /**
     * Format the amount to string.
     *
     * @param amount the amount
     * @param currencyCode the currency code
     * @return the string
     */
    public static String format(BigDecimal amount, String currencyCode) {

        /* logger.debug("Formatting amount : {}, currency: {}", amount, currencyCode); */

        Currency currency = Currency.getInstance(currencyCode);
        int noOfDecimals = currency.getDefaultFractionDigits();

        amount.setScale(noOfDecimals, BigDecimal.ROUND_UNNECESSARY);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(noOfDecimals);
        df.setMinimumFractionDigits(noOfDecimals);
        df.setGroupingUsed(false);

        /* logger.debug("Formatted amount: {}", formattedAmount); */
        return df.format(amount);

    }

    /**
     * Format the amount to string.
     *
     * @param amount the amount
     * @param currencyCode the currency code
     * @return the string
     */
    public static String format(float amount, String currencyCode) {

        /* logger.debug("Formatting amount : {}, currency: {}", amount, currencyCode); */

        Currency currency = Currency.getInstance(currencyCode);
        int noOfDecimals = currency.getDefaultFractionDigits();

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(noOfDecimals);
        df.setMinimumFractionDigits(noOfDecimals);
        df.setGroupingUsed(false);

        /* logger.debug("Formatted amount: {}", formattedAmount); */
        return df.format(amount);

    }

    /**
     * Convert String amount to Float amount.
     *
     * @param amount the amount
     * @return the Float
     */
    public static Float stringToFloatRounding(String amount) {

        BigDecimal bdAmount = new BigDecimal(amount).setScale(2, RoundingMode.CEILING);

        return bdAmount.floatValue();
    }
    
    /**
     * Convert String amount to Float amount, only for final total.
     *
     * @param amount the amount
     * @return the Float
     */
    public static Float stringToFloatRoundingFinalTotal(String amount) {

        BigDecimal bdAmount = new BigDecimal(amount).setScale(0, RoundingMode.CEILING);

        return bdAmount.floatValue();
    }

    /**
     * Convert String amount to Float amount.
     *
     * @param amount the amount
     * @return the Float
     */
    public static Float stringToFloat(String amount) {

        return Float.parseFloat(amount);
    }

    /**
     * Convert Big decimal amount to Float amount.
     *
     * @param amount the amount
     * @return the Float
     */
    public static Float bigDecimalToFloatRounding(BigDecimal amount) {

        return amount.setScale(2, RoundingMode.CEILING).floatValue();
    }
    
    /**
     * Convert Big decimal amount to Float amount, only for final total.
     *
     * @param amount the amount
     * @return the Float
     */
    public static Float bigDecimalToFloatRoundingFinalTotal(BigDecimal amount) {

        return amount.setScale(0, RoundingMode.CEILING).floatValue();
    }

    /**
     * Convert Big decimal amount to Float amount.
     *
     * @param amount the amount
     * @return the Float
     */
    public static Float bigDecimalToFloat(BigDecimal amount) {

        return amount.floatValue();
    }

    /**
     * Convert String amount to Float amount.
     *
     * @param amount the amount
     * @return the Float
     */
    public static BigDecimal floatToBigDecimal(Float amount) {
        return new BigDecimal(amount);
    }

    /**
     * Gets the lowest unit.
     *
     * @param amount the amount
     * @return the lowest unit
     */
    public static int getLowestUnit(float amount) {
        DecimalFormat decimal = new DecimalFormat("0.00");
        String val = decimal.format(amount);
        BigDecimal bdAmount = new BigDecimal(val);
        return bdAmount.multiply(new BigDecimal(100)).intValue();

    }

    /**
     * Float to float rounding. (This is a temporary method, until fixes are done at vendor connectors and
     * product services
     *
     * @param amount the amount
     * @return the float
     */
    public static Float floatToFloatRounding(float amount) {

        BigDecimal bdAmount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.CEILING);
        return bdAmount.floatValue();
    }
    
    /**
     * Float to float rounding. (This is a temporary method, until fixes are done at vendor connectors and
     * product services
     *
     * @param amount the amount
     * @return the float
     */
    public static Float floatToFloatRoundingFinalTotal(float amount) {

        BigDecimal bdAmount = BigDecimal.valueOf(amount).setScale(0, RoundingMode.CEILING);
        return bdAmount.floatValue();
    }

    /**
     * Get int value from float value
     * 
     * @param amount
     * @return the int
     */
    public static int floatToInt(float amount) {

        BigDecimal dbAmount = BigDecimal.valueOf(amount);
        return dbAmount.intValue();
    }

    /**
     * Gets the highest unit.
     *
     * @param amount the amount
     * @return the highest unit
     */
    public static float getHighestUnit(float amount) {

        BigDecimal bdAmount = BigDecimal.valueOf(amount);
        return bdAmount.divide(new BigDecimal(100)).floatValue();
    }
}
