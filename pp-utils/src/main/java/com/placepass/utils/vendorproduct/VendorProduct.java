package com.placepass.utils.vendorproduct;

import org.apache.commons.lang3.StringUtils;

/**
 * Resolves an obfuscated product ID into {@link Vendor} and vendor product ID,
 * based on product-id-schema {@code <vendor_code_6_digit><product_ID>}.
 * 
 * @author wathsala.w
 *
 */
public class VendorProduct {

	private String encodedProductID;

	private String decodedProductID;

	private Vendor vendor;

	private String vendorProductID;

	/**
	 * Get {@link VendorProduct} instance.
	 * 
	 * @param encodedProductID
	 *            obfuscated product ID
	 * @param generator
	 *            {@link ProductHashGenerator} instance.
	 * @return {@link VendorProduct} instance.
	 * @throws IndexOutOfBoundsException
	 *             when decoded string based on <b>encodedProductId</b> is not
	 *             having expected length based on product-id-schema
	 *             {@code <vendor_code_6_digit><product_ID>}.
	 */
	public static VendorProduct getInstance(String encodedProductID, ProductHashGenerator generator) throws IndexOutOfBoundsException {

		return new VendorProduct(encodedProductID, generator);
	}

	/**
	 * Internal constructor.
	 * 
	 * @param encodedProductID
	 *            obfuscated product ID
	 * @param generator
	 *            {@link ProductHashGenerator} instance.
	 * @throws IndexOutOfBoundsException
	 */
	private VendorProduct(String encodedProductID, ProductHashGenerator generator) throws IndexOutOfBoundsException {

		this.encodedProductID = encodedProductID;

		String decodedProductID = generator.degenerateHash(encodedProductID);
		this.decodedProductID = decodedProductID;
		this.vendor = getVendor(decodedProductID);
		this.vendorProductID = getVendorProductId(decodedProductID);
	}

	/**
	 * Gets the {@link Vendor} based on decoded ProductID string.
	 * 
	 * @param decodedProductID
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	private Vendor getVendor(String decodedProductID) throws IndexOutOfBoundsException {

		if (StringUtils.isNoneBlank(decodedProductID)) {
			return Vendor.valueOf(decodedProductID.substring(0, ProductHashGenerator.VENDOR_KEY_LENGTH));
		}

		return null;
	}

	/**
	 * Gets the vendor product ID based on decoded ProductID string.
	 * 
	 * @param decodedProductID
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	private String getVendorProductId(String decodedProductID) throws IndexOutOfBoundsException {

		if (StringUtils.isNoneBlank(decodedProductID)) {
			return decodedProductID.substring(ProductHashGenerator.VENDOR_KEY_LENGTH, decodedProductID.length());
		}

		return null;
	}

	/**
	 * Gets the {@link Vendor} based on decoded ProductID string.
	 * 
	 * @return
	 */
	public Vendor getVendor() {
		return vendor;
	}

	/**
	 * Gets the vendor product ID based on decoded ProductID string.
	 * 
	 * @return
	 */
	public String getVendorProductID() {
		return vendorProductID;
	}

	/**
	 * Gets the original obfuscated product ID.
	 * 
	 * @return the encodedProductID
	 */
	public String getEncodedProductID() {
		return encodedProductID;
	}

	/**
	 * Gets the decoded ProductID string.
	 * 
	 * @return the decodedProductID
	 */
	public String getDecodedProductID() {
		return decodedProductID;
	}

}
