package com.placepass.connector.bemyguest.domain.bemyguest.bookingcheck;

import java.time.LocalDate;
import java.util.List;

public class BeMyGuestCheckBookingResInfo {

    private String uuid;

    private String code;

    private float totalAmount;

    private String currencyCode;

    private String currencyUuid;

    private float totalAmountRequestCurrency;

    private String requestCurrencyCode;

    private String requestCurrencyUuid;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private LocalDate arrivalDate;

    private String salutation;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private int guests;

    private int children;

    private String seniors;

    private String partnerReference;

    private LocalDate confirmationEmailSentAt;

    private List<String> confirmationEmailFiles;

    private LocalDate cancellationRequestAt;

    private LocalDate refundDate;

    private float refundAmount;

    private String refundTransaction;

    private String status;

    private String productTypeTitle;

    private String productTypeTitleTranslated;

    private String productTypeUuid;

    private List<String> amountBreakdown;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyUuid() {
        return currencyUuid;
    }

    public void setCurrencyUuid(String currencyUuid) {
        this.currencyUuid = currencyUuid;
    }

    public float getTotalAmountRequestCurrency() {
        return totalAmountRequestCurrency;
    }

    public void setTotalAmountRequestCurrency(float totalAmountRequestCurrency) {
        this.totalAmountRequestCurrency = totalAmountRequestCurrency;
    }

    public String getRequestCurrencyCode() {
        return requestCurrencyCode;
    }

    public void setRequestCurrencyCode(String requestCurrencyCode) {
        this.requestCurrencyCode = requestCurrencyCode;
    }

    public String getRequestCurrencyUuid() {
        return requestCurrencyUuid;
    }

    public void setRequestCurrencyUuid(String requestCurrencyUuid) {
        this.requestCurrencyUuid = requestCurrencyUuid;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public String getSeniors() {
        return seniors;
    }

    public void setSeniors(String seniors) {
        this.seniors = seniors;
    }

    public String getPartnerReference() {
        return partnerReference;
    }

    public void setPartnerReference(String partnerReference) {
        this.partnerReference = partnerReference;
    }

    public LocalDate getConfirmationEmailSentAt() {
        return confirmationEmailSentAt;
    }

    public void setConfirmationEmailSentAt(LocalDate confirmationEmailSentAt) {
        this.confirmationEmailSentAt = confirmationEmailSentAt;
    }

    public List<String> getConfirmationEmailFiles() {
        return confirmationEmailFiles;
    }

    public void setConfirmationEmailFiles(List<String> confirmationEmailFiles) {
        this.confirmationEmailFiles = confirmationEmailFiles;
    }

    public LocalDate getCancellationRequestAt() {
        return cancellationRequestAt;
    }

    public void setCancellationRequestAt(LocalDate cancellationRequestAt) {
        this.cancellationRequestAt = cancellationRequestAt;
    }

    public LocalDate getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(LocalDate refundDate) {
        this.refundDate = refundDate;
    }

    public float getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(float refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundTransaction() {
        return refundTransaction;
    }

    public void setRefundTransaction(String refundTransaction) {
        this.refundTransaction = refundTransaction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductTypeTitle() {
        return productTypeTitle;
    }

    public void setProductTypeTitle(String productTypeTitle) {
        this.productTypeTitle = productTypeTitle;
    }

    public String getProductTypeTitleTranslated() {
        return productTypeTitleTranslated;
    }

    public void setProductTypeTitleTranslated(String productTypeTitleTranslated) {
        this.productTypeTitleTranslated = productTypeTitleTranslated;
    }

    public String getProductTypeUuid() {
        return productTypeUuid;
    }

    public void setProductTypeUuid(String productTypeUuid) {
        this.productTypeUuid = productTypeUuid;
    }

    public List<String> getAmountBreakdown() {
        return amountBreakdown;
    }

    public void setAmountBreakdown(List<String> amountBreakdown) {
        this.amountBreakdown = amountBreakdown;
    }
}
