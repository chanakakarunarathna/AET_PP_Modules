package com.placepass.utils.vendorproduct;

import org.apache.commons.lang3.StringUtils;

/**
 * Resolves an obfuscated productOption ID into {@link Vendor} and vendor
 * product option ID, based on product-option-id-schema
 * {@code <vendor_code_6_digit><productOption_ID>}.
 * 
 */
public class VendorProductOption {

	private String encodedProductOptionID;

	private String decodedProductOptionID;

	private Vendor vendor;

	private String vendorProductOptionID;

	/**
	 * Get {@link VendorProductOption} instance.
	 * 
	 * @param encodedProductOptionID
	 *            obfuscated productOption ID
	 * @param generator
	 *            {@link ProductHashGenerator} instance.
	 * @return {@link VendorProductOption} instance.
	 * @throws IndexOutOfBoundsException
	 *             when decoded string based on <b>encodedProductOptionId</b> is
	 *             not having expected length based on product-option-id-schema
	 *             {@code <vendor_code_6_digit><productOption_ID>}.
	 */
	public static VendorProductOption getInstance(String encodedProductOptionID, ProductHashGenerator generator)
			throws IndexOutOfBoundsException {

		return new VendorProductOption(encodedProductOptionID, generator);
	}

	/**
	 * Internal constructor.
	 * 
	 * @param encodedProductOptionID
	 *            obfuscated productOption ID
	 * @param generator
	 *            {@link ProductHashGenerator} instance.
	 * @throws IndexOutOfBoundsException
	 */
	private VendorProductOption(String encodedProductOptionID, ProductHashGenerator generator)
			throws IndexOutOfBoundsException {

		this.encodedProductOptionID = encodedProductOptionID;

		String decodedProductOptionID = generator.degenerateHash(encodedProductOptionID);
		this.decodedProductOptionID = decodedProductOptionID;
		this.vendor = getVendor(decodedProductOptionID);
		this.vendorProductOptionID = getVendorProductOptionId(decodedProductOptionID);
	}

	/**
	 * Gets the {@link Vendor} based on decoded ProductOptionID string.
	 * 
	 * @param decodedProductOptionID
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	private Vendor getVendor(String decodedProductOptionID) throws IndexOutOfBoundsException {

		if (StringUtils.isNoneBlank(decodedProductOptionID)) {
			return Vendor.valueOf(decodedProductOptionID.substring(0, ProductHashGenerator.VENDOR_KEY_LENGTH));
		}

		return null;
	}

	/**
	 * Gets the vendor productOption ID based on decoded ProductOptionID string.
	 * 
	 * @param decodedProductOptionID
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	private String getVendorProductOptionId(String decodedProductOptionID) throws IndexOutOfBoundsException {

		if (StringUtils.isNoneBlank(decodedProductOptionID)) {
			return decodedProductOptionID.substring(ProductHashGenerator.VENDOR_KEY_LENGTH,
					decodedProductOptionID.length());
		}

		return null;
	}

	/**
	 * Gets the {@link Vendor} based on decoded ProductOptionID string.
	 * 
	 * @return
	 */
	public Vendor getVendor() {
		return vendor;
	}

	/**
	 * Gets the vendor productOption ID based on decoded ProductOptionID string.
	 * 
	 * @return
	 */
	public String getVendorProductOptionID() {
		return vendorProductOptionID;
	}

	/**
	 * Gets the original obfuscated productOption ID.
	 * 
	 * @return the encodedProductOptionID
	 */
	public String getEncodedProductOptionID() {
		return encodedProductOptionID;
	}

	/**
	 * Gets the decoded ProductOptionID string.
	 * 
	 * @return the decodedProductOptionID
	 */
	public String getDecodedProductOptionID() {
		return decodedProductOptionID;
	}

}
