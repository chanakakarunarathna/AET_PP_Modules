package com.placepass.utils.vendorproduct;

import org.junit.Assert;
import org.junit.Test;

public class VendorProductTest {
	
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
            "TKTMST67452",
            "VIATOR2280SUN",
            "VIATOR3857NYCNIA",
            "VIATOR2396AMNH",
            "URBANAMSKUA",
            "GOBEEETR-CAB-MAF-PHIL-EN-1006"};

	@Test
	public void testVendorProductInstances() {

		for (int i = 0; i < strings.length; i++) {
			String hash = generator.generateHash(strings[i]);
			String dehash = generator.degenerateHash(hash);

			System.out.println("Plain Text: " + strings[i] + " Hash: " + hash + " DeHash: " + dehash);

			Assert.assertTrue(strings[i].equals(dehash));

			VendorProduct vc = VendorProduct.getInstance(hash, generator);
			System.out.println("EncodedProductID: " + vc.getEncodedProductID() + " DecodedProductID: " + vc.getDecodedProductID() + " Vendor: "
					+ vc.getVendor().name() + " VendorProductID: "
					+ vc.getVendorProductID());

			Assert.assertTrue("before encoding product id and VendorProduct's product id string should match (which is after decoding)",
					strings[i].equals(vc.getDecodedProductID()));
			Assert.assertTrue("As per naming convension vendor code should be 6 characters", vc.getVendor().name().length() == 6);
			Assert.assertTrue(strings[i].contains(vc.getVendorProductID()));
		}
	}

    @Test
    public void testReverse() {

        String[] strings = {"V28Hi3Zn7LpZZ"};

        for (int i = 0; i < strings.length; i++) {
            String dehash = generator.degenerateHash(strings[i]);
            String hash = generator.generateHash(dehash);

            System.out.println("Plain Text: " + strings[i] + " Hash: " + strings[i] + " DeHash: " + dehash);

            Assert.assertTrue(strings[i].equals(hash));

            VendorProduct vc = VendorProduct.getInstance(strings[i], generator);
            System.out.println(
                    "EncodedProductID: " + vc.getEncodedProductID() + " DecodedProductID: " + vc.getDecodedProductID()
                            + " Vendor: " + vc.getVendor().name() + " VendorProductID: " + vc.getVendorProductID());

            Assert.assertTrue(
                    "before encoding product id and VendorProduct's product id string should match (which is after decoding)",
                    strings[i].equals(vc.getEncodedProductID()));
            Assert.assertTrue("As per naming convension vendor code should be 6 characters",
                    vc.getVendor().name().length() == 6);
            Assert.assertTrue(dehash.contains(vc.getVendorProductID()));
        }
    }

}
