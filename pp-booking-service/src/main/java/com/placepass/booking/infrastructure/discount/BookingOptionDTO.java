package com.placepass.booking.infrastructure.discount;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.placepass.booking.domain.product.Price;
import com.placepass.booking.domain.product.ProductDetails;

public class BookingOptionDTO {

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

    private List<QuantityDTO> quantities;

    private TotalDTO total;

    private List<TravelerDTO> traverlerDetails;

    private ProductDetails productDetails;

    private Map<String, String> metadata;

    private String startTime;

    private String endTime;

    private List<Price> prices;

    private PickupLocationDTO pickupLocation;

    private Boolean isHotelPickUpEligible;

    private List<LoyaltyDetailDTO> loyaltyDetails;

    private String languageOptionCode;

    public String getBookingOptionId() {
        return bookingOptionId;
    }

    public void setBookingOptionId(String bookingOptionId) {
        this.bookingOptionId = bookingOptionId;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public List<QuantityDTO> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<QuantityDTO> quantities) {
        this.quantities = quantities;
    }

    public TotalDTO getTotal() {
        return total;
    }

    public void setTotal(TotalDTO total) {
        this.total = total;
    }

    public List<TravelerDTO> getTraverlerDetails() {
        return traverlerDetails;
    }

    public void setTraverlerDetails(List<TravelerDTO> traverlerDetails) {
        this.traverlerDetails = traverlerDetails;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public PickupLocationDTO getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(PickupLocationDTO pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Boolean getIsHotelPickUpEligible() {
        return isHotelPickUpEligible;
    }

    public void setIsHotelPickUpEligible(Boolean isHotelPickUpEligible) {
        this.isHotelPickUpEligible = isHotelPickUpEligible;
    }

    public List<LoyaltyDetailDTO> getLoyaltyDetails() {
        return loyaltyDetails;
    }

    public void setLoyaltyDetails(List<LoyaltyDetailDTO> loyaltyDetails) {
        this.loyaltyDetails = loyaltyDetails;
    }

    public String getLanguageOptionCode() {
        return languageOptionCode;
    }

    public void setLanguageOptionCode(String languageOptionCode) {
        this.languageOptionCode = languageOptionCode;
    }

}
