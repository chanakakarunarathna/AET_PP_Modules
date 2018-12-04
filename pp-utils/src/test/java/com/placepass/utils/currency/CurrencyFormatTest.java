package com.placepass.utils.currency;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class CurrencyFormatTest {

    /**
     * 
     * Country code : DJF / Country Name : Djibouti
     * 
     */
    @Test
    public void testFloatCurrencyToStringFormatZeroDigits() {

        assertEquals("12", AmountFormatter.format(12, "DJF"));

    }

    @Test
    public void testFloatCurrencyToStringFormatZeroDigitsExampleTwo() {

        assertEquals("12", AmountFormatter.format(12.12f, "DJF"));

    }

    /**
     * 
     * Country code : USD / Country Name : United States
     * 
     */
    @Test
    public void testFloatCurrencyToStringFormatTwoDigits() {

        assertEquals("12.34", AmountFormatter.format(12.34f, "USD"));

    }

    @Test
    public void testFloatCurrencyToStringFormatTwoDigitsExampleTwo() {

        assertEquals("12.30", AmountFormatter.format(12.3f, "USD"));

    }

    /**
     * 
     * Country code : BHD / Country Name : Bahrain
     * 
     */
    @Test
    public void testFloatCurrencyToStringFormatThreeDigits() {

        assertEquals("12.345", AmountFormatter.format(12.345f, "BHD"));

    }

    @Test
    public void testFloatCurrencyToStringFormatThreeDigitsExampleTwo() {

        assertEquals("12.300", AmountFormatter.format(12.3f, "BHD"));

    }

    /**
     * 
     * Country code : CLF / Country Name : Chile
     * 
     */
    @Test
    public void testFloatCurrencyToStringFormatFourDigits() {

        assertEquals("12.3456", AmountFormatter.format(12.3456f, "CLF"));

    }

    @Test
    public void testFloatCurrencyToStringFormatFourDigitsExampleTwo() {

        assertEquals("12.3000", AmountFormatter.format(12.3f, "CLF"));

    }

    /**
     * 
     * Country code : DJF / Country Name : Djibouti
     * 
     */
    @Test
    public void testBigDecimalCurrencyToStringFormatZeroDigits() {

        assertEquals("12", AmountFormatter.format(new BigDecimal("12"), "DJF"));

    }

    /**
     * 
     * Country code : USD / Country Name : United States
     * 
     */
    @Test
    public void testBigDecimalCurrencyToStringFormatTwoDigits() {

        assertEquals("12.34", AmountFormatter.format(new BigDecimal("12.34"), "USD"));

    }

    @Test
    public void testBigDecimalCurrencyToStringFormatTwoDigitsExampleTwo() {

        assertEquals("12.30", AmountFormatter.format(new BigDecimal("12.3"), "USD"));

    }

    /**
     * 
     * Country code : BHD / Country Name : Bahrain
     * 
     */
    @Test
    public void testBigDecimalCurrencyToStringFormatThreeDigits() {

        assertEquals("12.345", AmountFormatter.format(new BigDecimal("12.345"), "BHD"));

    }

    @Test
    public void testBigDecimalCurrencyToStringFormatThreeDigitsExampleTwo() {

        assertEquals("12.300", AmountFormatter.format(new BigDecimal("12.3"), "BHD"));

    }

    /**
     * 
     * Country code : CLF / Country Name : Chile
     * 
     */
    @Test
    public void testBigDecimalCurrencyToStringFormatFourDigits() {

        assertEquals("12.3456", AmountFormatter.format(new BigDecimal("12.3456"), "CLF"));

    }

    @Test
    public void testBigDecimalCurrencyToStringFormatFourDigitsExampleTwo() {

        assertEquals("12.3000", AmountFormatter.format(new BigDecimal("12.3"), "CLF"));

    }

    @Test
    public void testFloatToFloatRounding() {

        assertEquals(new Float(12.35), AmountFormatter.floatToFloatRounding(12.345f));

    }

    @Test
    public void testBigDecimalToFloatRoundingFinalTotalExampleOne() {

        assertEquals(new Float(11), AmountFormatter.bigDecimalToFloatRoundingFinalTotal(new BigDecimal(10.12)));

    }

    @Test
    public void testStringToFloatRoundingFinalTotalExampleOne() {

        assertEquals(new Float(11), AmountFormatter.stringToFloatRoundingFinalTotal("10.12"));

    }

    @Test
    public void testFloatToFloatRoundingFinalTotalExampleOne() {

        assertEquals(new Float(11), AmountFormatter.floatToFloatRoundingFinalTotal(10.12f));

    }

    @Test
    public void testOneGetLowestUnitFloatValue() {

        assertEquals(1012, AmountFormatter.getLowestUnit(10.12f));

    }

    @Test
    public void testTwoGetLowestUnitFloatValue() {

        assertEquals(1399, AmountFormatter.getLowestUnit(13.99f));

    }

    /**
     * Get the int value from float value / Expected result - 13
     * 
     */
    @Test
    public void testFloatToInt() {

        assertEquals(13, AmountFormatter.floatToInt(13.55f));

    }

    /**
     * Expected result - 4.5
     */
    @Test
    public void testGetHighestUnit() {

        assertEquals(4.5, AmountFormatter.getHighestUnit(450f), 0);

    }

}
