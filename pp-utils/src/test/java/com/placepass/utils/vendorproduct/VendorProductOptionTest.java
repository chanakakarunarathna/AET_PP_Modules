package com.placepass.utils.vendorproduct;

import org.junit.Assert;
import org.junit.Test;

public class VendorProductOptionTest {
	
	private int hashMultiplier = 7;

	private int hashAdder = 0;

	ProductHashGenerator generator = ProductHashGenerator.getInstance(hashMultiplier, hashAdder);

	String[] strings = {
            "MUSEME2458",
            "MUSEME7101",
            "VIATOR6670northend",
            "MUSEME1622",
            "GETYGU626231747",
            "GETYGU16853",
            "VIATOR6324plim",
            "IFONLY2458",
            "ISANGO626231746",
            "URBANA15003",
            "HADOUT35978",
            "PROEXP33233",
            "TIQETS1111",
            "VIREAL14562",
            "TKTMST67452"};
	

	@Test
	public void testVendorProductOptionInstances() {

		for (int i = 0; i < strings.length; i++) {
			String hash = generator.generateHash(strings[i]);
			String dehash = generator.degenerateHash(hash);

			System.out.println("Plain Text: " + strings[i] + " Hash: " + hash + " DeHash: " + dehash);

			Assert.assertTrue(strings[i].equals(dehash));

			VendorProductOption vc = VendorProductOption.getInstance(hash, generator);
			System.out.println("EncodedProductOptionID: " + vc.getEncodedProductOptionID() + " DecodedProductOptionID: " + vc.getDecodedProductOptionID() + " Vendor: "
					+ vc.getVendor().name() + " VendorProductOptionID: "
					+ vc.getVendorProductOptionID());

			Assert.assertTrue("before encoding productOption id and VendorProduct's productOption id string should match (which is after decoding)",
					strings[i].equals(vc.getDecodedProductOptionID()));
			Assert.assertTrue("As per naming convension vendor code should be 6 characters", vc.getVendor().name().length() == 6);
			Assert.assertTrue(strings[i].contains(vc.getVendorProductOptionID()));
		}
	}

}
