package com.placepass.utils.vendorproduct;

import org.junit.Assert;
import org.junit.Test;

public class ProductHashGeneratorTest {
	
	private int hashMultiplier = 7;

	private int hashAdder = 0;

	ProductHashGenerator generator = ProductHashGenerator.getInstance(hashMultiplier, hashAdder);

	String[] strings = {
            "ifonly2458",
            "getyourguide7101",
            "viator6670northend",
            "musement1622",
            "isango626231747",
            "getyourguide16853",
            "viator6324plim",
            "ifonly8549",
            "musement8593",
            "isango626231746",
            "getyourguide15344",
            "VIATOR2280AAHT",
            "VIATOR2280_AAHT",
            "SUNSET_ASTAR",
            "SUNSET_EC130",
            "SUNSET EC130"};

	@Test
	public void testPpHashDeHash() {

		for (int i = 0; i < strings.length; i++) {
			String hash = generator.generateHash(strings[i]);
			String dehash = generator.degenerateHash(hash);

			System.out.println("Plain Text: " + strings[i] + " Hash: " + hash + " DeHash: " + dehash);

			Assert.assertTrue(strings[i].equals(dehash));
		}
	}

	/**
	 * This is just a comparison done to confirm existing PP values can be
	 * dehashed.
	 * 
	 * Will only work for key combo hashMultiplier=7 and hashAdder=0.
	 */
	@Test
	public void testOriginalKeyPpHashDeHash() {
		
		// Original PP hashed values
		String[] originalHashedValues = {
				"ydeXJmESZu",
				"kWDmeKzkKyPWn707",
				"Ry4Dezggn0XezDrWXP",
				"QK6WQWXD7gEE",
				"y64XkegEgEL7nSn",
				"kWDmeKzkKyPW7guZL",
				"Ry4DezgLESlJyQ",
				"ydeXJmuZS1",
				"QK6WQWXDuZ1L",
				"y64XkegEgEL7nSg",
				"kWDmeKzkKyPW7ZLSS",
				"V28Hi3EEu088vH",
				"V28Hi3EEu0_88vH"			
				};

        for (int i = 0; i < originalHashedValues.length; i++) {
			String hash = generator.generateHash(strings[i]);
			String dehash = generator.degenerateHash(hash);

			System.out.println("Plain Text: " + strings[i] + " Hash: " + hash + " DeHash: " + dehash);

			Assert.assertTrue(originalHashedValues[i].equals(hash));
			Assert.assertTrue(strings[i].equals(dehash));
		}
	}

}
