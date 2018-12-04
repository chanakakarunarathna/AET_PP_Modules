package com.placepass.encryption.text;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Creates a masked string using the provided mask character. The {@link Segment}s define the areas of the string to be
 * masked. For example the string "0123456789abcdef", the segments [1, 2], [5, 3] & [-3, 2] with the masking
 * character '*', will produce "1**45***90abc**f". The negative values given for the segment start or length considers the
 * number of characters from the end.
 *
 * The {@link #ensureMinMaskCharacters(int)} & {@link #ensureMaxMaskCharacters(int)} methods can be used to expand or
 * reduce the number of masking characters displayed in the formatted string.
 */
public class MaskFormatter {
    private char maskCharacter;
    private Segment[] segments;
    private int minMaskCharacterLimit;
    private int maxMaskCharacterLimit;

    /**
     * Creates a {@link MaskFormatter} instance with the given mask character and the masking segments of a string.
     * @param maskCharacter Masking character to be used.
     * @param segments Defines the segments/sections that the masking is applied to.
     */
    public MaskFormatter(char maskCharacter, Segment... segments) {
        this.maskCharacter = maskCharacter;
        this.segments = segments;
    }

    /**
     * Ensures at least the {@code minMaskCharacterLimit} masking characters are included in the final masked string.
     * @param minMaskCharacterLimit Minimum masking characters that would be displayed in the final masked string.
     */
    public void ensureMinMaskCharacters(int minMaskCharacterLimit) {
        if (maxMaskCharacterLimit > 0 && minMaskCharacterLimit > maxMaskCharacterLimit)
            throw new IllegalArgumentException("minMaskCharacterLimit cannot be greater than maxMaskCharacterLimit");
        this.minMaskCharacterLimit = minMaskCharacterLimit;
    }

    /**
     * Ensures that the maximum masking characters are not exceeding beyond the {@code minMaskCharacterLimit}.
     * @param maxMaskCharacterLimit Maximum masking characters that would be displayed in the final masked string.
     */
    public void ensureMaxMaskCharacters(int maxMaskCharacterLimit) {
        if (minMaskCharacterLimit > 0 && minMaskCharacterLimit > maxMaskCharacterLimit)
            throw new IllegalArgumentException("minMaskCharacterLimit cannot be greater than maxMaskCharacterLimit");
        this.maxMaskCharacterLimit = maxMaskCharacterLimit;
    }

    /**
     * Generates the masked string for the provided string.
     * @param value String to be masked.
     * @return Masked string value.
     */
    public String format(String value) {
        StringBuilder stringBuilder = new StringBuilder(value);
        for (Segment segment : segments) {
            int startPos = segment.start >= 0 ? segment.start : stringBuilder.length() + segment.start;
            int length = segment.length > 0 ? segment.length : stringBuilder.length() - startPos + segment.length;
            for (int i = 0; i < length; i++) {
                stringBuilder.setCharAt(startPos + i, maskCharacter);
            }
        }

        // reduce or expand mask characters
        if (minMaskCharacterLimit > 0 || maxMaskCharacterLimit > 0 ) {
            StringBuffer maskedString = new StringBuffer();
            Pattern pattern = Pattern.compile("(\\Q" + maskCharacter + "\\E)+");
            Matcher matcher = pattern.matcher(stringBuilder);
            while (matcher.find()) {
                char[] maskFillChars = null;
                String maskedStringMatch = matcher.group();
                int maskedCharsCount = maskedStringMatch.length();
                if (maskedCharsCount < minMaskCharacterLimit) {
                    maskFillChars = new char[minMaskCharacterLimit];
                }
                if (maxMaskCharacterLimit > 0 && maskedCharsCount > maxMaskCharacterLimit) {
                    maskFillChars = new char[maxMaskCharacterLimit];
                }

                if (maskFillChars != null) {
                    Arrays.fill(maskFillChars, maskCharacter);
                    maskedStringMatch = new String(maskFillChars);
                }
                matcher.appendReplacement(maskedString, maskedStringMatch);
            }
            matcher.appendTail(maskedString);
            return maskedString.toString();
        }
        return stringBuilder.toString();
    }

    /**
     * Defines the segments/sections that the masking is applied to.
     */
    public static class Segment {
        private int start;
        private int length;

        /**
         * Defines a segment that starts from the given {@code start} index to the end of the string. {@code start}
         * index can be given a negative value, which will count the indexes from the end of the string.
         * @param start Starting position of the string.
         */
        public Segment(int start) {
            this.start = start;
        }

        /**
         * Defines a segment that starts from the given {@code start} index and ends after {@code length} characters.
         * {@code start} index and {@code length} can be given a negative values, which will count the from the end of
         * the string.
         * @param start Starting position of the string.
         * @param length segment length.
         */
        public Segment(int start, int length) {
            this.start = start;
            this.length = length;
        }
    }
}
