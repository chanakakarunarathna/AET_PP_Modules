package com.placepass.utils.common;

import java.nio.charset.StandardCharsets;

public class StringUtils {

	public static String trimString(String description, int length) {

		String trimmedString = description;
		if (description.length() > length) {
			trimmedString = description.substring(0, length);
		}
		return trimmedString;
	}
	
    public static String getUTF8ConvertedContent(String content) {

        byte[] productTitleByteArray = content.getBytes(StandardCharsets.UTF_8);
        return new String(productTitleByteArray);
    }

}
