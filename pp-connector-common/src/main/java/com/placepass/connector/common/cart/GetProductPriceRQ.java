package com.placepass.connector.common.cart;

import java.time.LocalDate;
import java.util.List;

public class GetProductPriceRQ {

    private String productId;

    private String vendorProductId;

    private String productOptionId;

    private String vendorProductOptionId;

    private LocalDate bookingDate;

    private List<Quantity> quantities;

    private List<Price> prices;

    private boolean vendorValidation;

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

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public boolean isVendorValidation() {
        return vendorValidation;
    }

    public void setVendorValidation(boolean vendorValidation) {
        this.vendorValidation = vendorValidation;
    }

}
