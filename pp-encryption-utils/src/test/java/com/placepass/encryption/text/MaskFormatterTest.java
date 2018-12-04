package com.placepass.encryption.text;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MaskFormatterTest {
    @Test
    public void formatString() {
        String sourceStr = "0123456789abcdefghijklmnopqrstuvwxyz";
        String expectedMaskedStr = "01***56789abcdefghijklmnopqrstuvw***";
        MaskFormatter maskFormatter = new MaskFormatter('*', new MaskFormatter.Segment(2, 3), new MaskFormatter
                .Segment(-3));
        String maskedStr = maskFormatter.format(sourceStr);
        assertNotNull("maskedStr cannot be null", maskedStr);
        assertEquals("maskedStr is not equals to expectedMask", expectedMaskedStr, maskedStr);
    }

    @Test
    public void formatStringEnsureMin() {
        String sourceStr = "0123456789abcdefghijklmnopqrstuvwxyz";
        String expectedMaskedStr = "01****3456789abcdefghijklmnopqrstuvw****";
        MaskFormatter maskFormatter = new MaskFormatter('*', new MaskFormatter.Segment(2, 1), new MaskFormatter
                .Segment(-3));
        maskFormatter.ensureMinMaskCharacters(4);
        String maskedStr = maskFormatter.format(sourceStr);
        assertNotNull("maskedStr cannot be null", maskedStr);
        assertEquals("maskedStr is not equals to expectedMask", expectedMaskedStr, maskedStr);
    }

    @Test
    public void formatStringEnsureMax() {
        String sourceStr = "0123456789abcdefghijklmnopqrstuvwxyz";
        String expectedMaskedStr = "01*3456789abcdefghijklmnopqrstuvw**";
        MaskFormatter maskFormatter = new MaskFormatter('*', new MaskFormatter.Segment(2, 1), new MaskFormatter
                .Segment(-3));
        maskFormatter.ensureMaxMaskCharacters(2);
        String maskedStr = maskFormatter.format(sourceStr);
        assertNotNull("maskedStr cannot be null", maskedStr);
        assertEquals("maskedStr is not equals to expectedMask", expectedMaskedStr, maskedStr);
    }

    @Test
    public void formatStringEnsureMinMax() {
        String sourceStr = "0123456789abcdefghijklmnopqrstuvwxyz";
        String expectedMaskedStr = "01***3456789abcdefghijklmnopqrstuvw***";
        MaskFormatter maskFormatter = new MaskFormatter('*', new MaskFormatter.Segment(2, 1), new MaskFormatter
                .Segment(-3));
        maskFormatter.ensureMinMaskCharacters(3);
        maskFormatter.ensureMaxMaskCharacters(4);
        String maskedStr = maskFormatter.format(sourceStr);
        assertNotNull("maskedStr cannot be null", maskedStr);
        assertEquals("maskedStr is not equals to expectedMask", expectedMaskedStr, maskedStr);
    }
}
