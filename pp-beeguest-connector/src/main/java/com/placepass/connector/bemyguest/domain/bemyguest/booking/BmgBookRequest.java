package com.placepass.connector.bemyguest.domain.bemyguest.booking;

import java.util.List;

public class BmgBookRequest {

    private String salutation;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String message;

    private String productTypeUuid;

    private int pax;

    private int children;

    private String timeSlotUuid;

    private List<BmgAddOn> addons;

    private String currencyCode;

    private String hotelName;

    private String hotelAddress;

    private String arrivalDate;

    private String partnerReference;

    private boolean usePromotion;

    private String flightNumber;

    private String flightDateTime;

    private String flightDestination;

    private boolean isTest;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProductTypeUuid() {
        return productTypeUuid;
    }

    public void setProductTypeUuid(String productTypeUuid) {
        this.productTypeUuid = productTypeUuid;
    }

    public int getPax() {
        return pax;
    }

    public void setPax(int pax) {
        this.pax = pax;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public String getTimeSlotUuid() {
        return timeSlotUuid;
    }

    public void setTimeSlotUuid(String timeSlotUuid) {
        this.timeSlotUuid = timeSlotUuid;
    }

    public List<BmgAddOn> getAddons() {
        return addons;
    }

    public void setAddons(List<BmgAddOn> addons) {
        this.addons = addons;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getPartnerReference() {
        return partnerReference;
    }

    public void setPartnerReference(String partnerReference) {
        this.partnerReference = partnerReference;
    }

    public boolean isUsePromotion() {
        return usePromotion;
    }

    public void setUsePromotion(boolean usePromotion) {
        this.usePromotion = usePromotion;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightDateTime() {
        return flightDateTime;
    }

    public void setFlightDateTime(String flightDateTime) {
        this.flightDateTime = flightDateTime;
    }

    public String getFlightDestination() {
        return flightDestination;
    }

    public void setFlightDestination(String flightDestination) {
        this.flightDestination = flightDestination;
    }

    public boolean getIsTest() {
        return isTest;
    }

    public void setIsTest(boolean isTest) {
        this.isTest = isTest;
    }

}
