package com.placepass.booking.application.cart.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.placepass.booking.application.booking.dto.ProductDetailsDTO;
import com.placepass.booking.application.booking.dto.VoucherDTO;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "BookingOption")
public class BookingOptionDTO {

    @NotEmpty(message = "Product id is required")
    private String productId;

    @NotEmpty(message = "Product option id is required")
    private String productOptionId;

    @Valid
    @NotNull(message = "Quantities are required")
    @NotEmpty(message = "Quantities can not be empty")
    private List<QuantityDTO> quantities;

    /* Date format coming as yyyy-MM-dd */
    @NotEmpty(message = "Booking date is required")
    @Pattern(regexp = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$", message = "Invalid booking date(yyyy-mm-dd)")
    private String bookingDate;

    private String title;

    private String description;

    private List<TravelerDTO> traverlerDetails;

    private TotalDTO total;

    private List<BookingQuestionDTO> bookingQuestions;

    private String startTime;

    private String endTime;

    private VoucherDTO voucher;

    private List<PriceDTO> prices;

    private PickupLocationDTO pickupLocation;

    private Boolean isHotelPickUpEligible;

    private List<LoyaltyDetailDTO> loyaltyDetails;

    private List<PriceBreakDownDTO> priceBreakDowns;

    private String languageOptionCode;

    private ProductDetailsDTO productDetails;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(String productOptionId) {
        this.productOptionId = productOptionId;
    }

    public List<QuantityDTO> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<QuantityDTO> quantities) {
        this.quantities = quantities;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
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

    public List<TravelerDTO> getTraverlerDetails() {
        return traverlerDetails;
    }

    public void setTraverlerDetails(List<TravelerDTO> traverlerDetails) {
        this.traverlerDetails = traverlerDetails;
    }

    public TotalDTO getTotal() {
        return total;
    }

    public void setTotal(TotalDTO total) {
        this.total = total;
    }

    public List<BookingQuestionDTO> getBookingQuestions() {
        return bookingQuestions;
    }

    public void setBookingQuestions(List<BookingQuestionDTO> bookingQuestions) {
        this.bookingQuestions = bookingQuestions;
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

    public VoucherDTO getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherDTO voucher) {
        this.voucher = voucher;
    }

    public List<PriceDTO> getPrices() {
        return prices;
    }

    public void setPrices(List<PriceDTO> prices) {
        this.prices = prices;
    }

    public PickupLocationDTO getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(PickupLocationDTO pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public List<PriceBreakDownDTO> getPriceBreakDowns() {
        return priceBreakDowns;
    }

    public void setPriceBreakDowns(List<PriceBreakDownDTO> priceBreakDowns) {
        this.priceBreakDowns = priceBreakDowns;
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

    public ProductDetailsDTO getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetailsDTO productDetails) {
        this.productDetails = productDetails;
    }

}
