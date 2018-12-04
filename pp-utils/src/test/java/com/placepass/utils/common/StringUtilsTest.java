package com.placepass.utils.common;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void descriptionEqualToTrimLengthTest() {
		
		String trimmedString = StringUtils.trimString("11111", 5);
		Assert.assertEquals("11111", trimmedString);
	}
	
	@Test
	public void descriptionGreaterThanTrimLength() {
		
		String trimmedString = StringUtils.trimString("1111122222", 7);
		Assert.assertEquals("1111122", trimmedString);
	}
	
	@Test
	public void descriptionLessThanTrimLength() {
		
		String test = "1111";
		String trimmedString = StringUtils.trimString(test, 7);
		Assert.assertEquals(test, trimmedString);
	}
}
