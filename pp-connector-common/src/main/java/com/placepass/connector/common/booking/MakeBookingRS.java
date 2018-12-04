package com.placepass.connector.common.booking;

import java.util.List;

import com.placepass.connector.common.common.BaseRS;
import com.placepass.connector.common.product.CancellationRules;

public class MakeBookingRS extends BaseRS {

    private String bookingId;

    private String referenceNumber;

    private Float totalAmount;

    private String currency;

    private Voucher voucher;

    private List<BookingItem> bookingItems;

    private CancellationRules cancellationRules;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public List<BookingItem> getBookingItems() {
        return bookingItems;
    }

    public void setBookingItems(List<BookingItem> bookingItems) {
        this.bookingItems = bookingItems;
    }

    public CancellationRules getCancellationRules() {
        return cancellationRules;
    }

    public void setCancellationRules(CancellationRules cancellationRules) {
        this.cancellationRules = cancellationRules;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MakeBookingRS [" + (bookingId != null ? "bookingId=" + bookingId + ", " : "")
                + (referenceNumber != null ? "referenceNumber=" + referenceNumber + ", " : "")
                + (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "")
                + (currency != null ? "currency=" + currency + ", " : "")
                + (voucher != null ? "voucher=" + voucher + ", " : "")
                + (bookingItems != null ? "bookingItems=" + bookingItems : "") + "]";
    }

}
