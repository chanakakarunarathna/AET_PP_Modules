package com.viator.connector.domain.viator.book;

import java.util.List;

public class ViatorBookReqItem {
    private ViatorBookReqPartnerItemDetail partnerItemDetail;

    private String hotelId;

    private Object pickupPoint;

    private String travelDate;

    private String productCode;

    private String tourGradeCode;

    private String languageOptionCode;

    private List<ViatorBookReqQuestionAnswer> bookingQuestionAnswers = null;

    private String specialRequirements;

    private List<ViatorBookReqTraveller> travellers = null;

    public ViatorBookReqPartnerItemDetail getPartnerItemDetail() {
        return partnerItemDetail;
    }

    public void setPartnerItemDetail(ViatorBookReqPartnerItemDetail partnerItemDetail) {
        this.partnerItemDetail = partnerItemDetail;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public Object getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(Object pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTourGradeCode() {
        return tourGradeCode;
    }

    public void setTourGradeCode(String tourGradeCode) {
        this.tourGradeCode = tourGradeCode;
    }

    public String getLanguageOptionCode() {
        return languageOptionCode;
    }

    public void setLanguageOptionCode(String languageOptionCode) {
        this.languageOptionCode = languageOptionCode;
    }

    public List<ViatorBookReqQuestionAnswer> getBookingQuestionAnswers() {
        return bookingQuestionAnswers;
    }

    public void setBookingQuestionAnswers(List<ViatorBookReqQuestionAnswer> bookingQuestionAnswers) {
        this.bookingQuestionAnswers = bookingQuestionAnswers;
    }

    public String getSpecialRequirements() {
        return specialRequirements;
    }

    public void setSpecialRequirements(String specialRequirements) {
        this.specialRequirements = specialRequirements;
    }

    public List<ViatorBookReqTraveller> getTravellers() {
        return travellers;
    }

    public void setTravellers(List<ViatorBookReqTraveller> travellers) {
        this.travellers = travellers;
    }

}
