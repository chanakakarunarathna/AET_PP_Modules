package com.placepass.connector.common.booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class BookingOption {

	private String bookingOptionId;

	private String vendor;

	private String productId;

	private String vendorProductId;

	private String productOptionId;

	private String vendorProductOptionId;

	private String title;

	private String imageUrl;

	private String description;

	private LocalDate bookingDate;

	private List<Quantity> quantities;

	private Total total;

	private List<BookingAnswer> bookingAnswers;

	private List<Traveler> traverlerDetails;

	private Map<String, String> metadata;

    private PickupLocation pickupLocation;

    private Boolean isHotelPickUpEligible;

    private String languageOptionCode;

	public String getBookingOptionId() {
		return bookingOptionId;
	}

	public void setBookingOptionId(String bookingOptionId) {
		this.bookingOptionId = bookingOptionId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Quantity> getQuantities() {
		return quantities;
	}

	public void setQuantities(List<Quantity> quantities) {
		this.quantities = quantities;
	}

	public Total getTotal() {
		return total;
	}

	public void setTotal(Total total) {
		this.total = total;
	}

	public List<BookingAnswer> getBookingAnswers() {
		return bookingAnswers;
	}

	public void setBookingAnswers(List<BookingAnswer> bookingAnswers) {
		this.bookingAnswers = bookingAnswers;
	}

	public List<Traveler> getTraverlerDetails() {
		return traverlerDetails;
	}

	public void setTraverlerDetails(List<Traveler> traverlerDetails) {
		this.traverlerDetails = traverlerDetails;
	}

	public String getVendorProductId() {
		return vendorProductId;
	}

	public void setVendorProductId(String vendorProductId) {
		this.vendorProductId = vendorProductId;
	}

	public String getProductOptionId() {
		return productOptionId;
	}

	public void setProductOptionId(String productOptionId) {
		this.productOptionId = productOptionId;
	}

	public String getVendorProductOptionId() {
		return vendorProductOptionId;
	}

	public void setVendorProductOptionId(String vendorProductOptionId) {
		this.vendorProductOptionId = vendorProductOptionId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

    public PickupLocation getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(PickupLocation pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Boolean getIsHotelPickUpEligible() {
        return isHotelPickUpEligible;
    }

    public void setIsHotelPickUpEligible(Boolean isHotelPickUpEligible) {
        this.isHotelPickUpEligible = isHotelPickUpEligible;
    }

    public String getLanguageOptionCode() {
        return languageOptionCode;
    }

    public void setLanguageOptionCode(String languageOptionCode) {
        this.languageOptionCode = languageOptionCode;
    }

}
