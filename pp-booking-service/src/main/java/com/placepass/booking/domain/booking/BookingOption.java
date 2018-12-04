package com.placepass.booking.domain.booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.placepass.booking.domain.product.Price;
import com.placepass.booking.domain.product.ProductDetails;

public class BookingOption {

    // Generated identifier (UUID)
    private String bookingOptionId;

    // plain vendor name (FIXME: can use the enum it self)
    private String vendor;

    // hashed value of vendor name and vendor product id
    private String productId;

    // plain vendor product id
    private String vendorProductId;

    // hashed value of vendor name and vendor product option id
    private String productOptionId;

    // plain vendor product option id
    private String vendorProductOptionId;

    private String title;

    private String imageUrl;

    private String description;

    private Voucher voucher;

    // TODO: We will need timeZone offset here.
    private LocalDate bookingDate;

    // TODO: Can a booking option have one price or multiple prices for age
    // band.
    // We may be able to flatten each age band in to its own booking option.
    //
    private List<Quantity> quantities;

    // Total for this booking option.
    private Total total;

    // We may have this at the cart level too.
    private List<BookingQuestion> bookingQuestions;

    private List<Traveler> traverlerDetails;

    private ProductDetails productDetails;

    private Map<String, String> metadata;

    private String startTime;

    private String endTime;

    private List<Price> prices;

    private PickupLocation pickupLocation;

    private Boolean isHotelPickUpEligible;

    private List<LoyaltyDetail> loyaltyDetails;

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

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
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

    public List<BookingQuestion> getBookingQuestions() {
        return bookingQuestions;
    }

    public void setBookingQuestions(List<BookingQuestion> bookingQuestions) {
        this.bookingQuestions = bookingQuestions;
    }

    public List<Traveler> getTraverlerDetails() {
        return traverlerDetails;
    }

    public void setTraverlerDetails(List<Traveler> traverlerDetails) {
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

    public List<LoyaltyDetail> getLoyaltyDetails() {
        return loyaltyDetails;
    }

    public void setLoyaltyDetails(List<LoyaltyDetail> loyaltyDetails) {
        this.loyaltyDetails = loyaltyDetails;
    }

    public String getLanguageOptionCode() {
        return languageOptionCode;
    }

    public void setLanguageOptionCode(String languageOptionCode) {
        this.languageOptionCode = languageOptionCode;
    }

}
