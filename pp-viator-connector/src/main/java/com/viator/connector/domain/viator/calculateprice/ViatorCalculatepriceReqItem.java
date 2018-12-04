package com.viator.connector.domain.viator.calculateprice;

import java.util.List;

public class ViatorCalculatepriceReqItem {
    private Boolean specialReservation;

    private String travelDate;

    private String productCode;

    private String tourGradeCode;

    private List<ViatorCalculatepriceReqTraveller> travellers = null;

    public Boolean getSpecialReservation() {
        return specialReservation;
    }

    public void setSpecialReservation(Boolean specialReservation) {
        this.specialReservation = specialReservation;
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

    public List<ViatorCalculatepriceReqTraveller> getTravellers() {
        return travellers;
    }

    public void setTravellers(List<ViatorCalculatepriceReqTraveller> travellers) {
        this.travellers = travellers;
    }
}
