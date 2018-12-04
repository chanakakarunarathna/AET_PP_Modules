package com.placepass.product.domain.product;

import com.placepass.utils.vendorproduct.Vendor;

public class Product {

	// TODO : Locale - Currency, Date Time,

	// This is a hash of Vendor:VendorProductCode
	// TODO: Finalize with PlacePass on exact hashing.
	private String productId;

	// TODO: Call this vendorProductCode - verify with PlacePass?
	private String vendorProductId;

	private Vendor vendor;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getVendorProductId() {
		return vendorProductId;
	}

	public void setVendorProductId(String vendorProductId) {
		this.vendorProductId = vendorProductId;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	

}
