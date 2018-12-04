package com.placepass.connector.citydiscovery.domain.citydiscovery.xml;

public class ClsCancelBookingRS {

    private ClsCancelBookingInfoRS cancelBookingInfoRS;

    private ClsResultType resultType;

    public ClsCancelBookingInfoRS getCancelBookingInfoRS() {
        return cancelBookingInfoRS;
    }

    public void setCancelBookingInfoRS(ClsCancelBookingInfoRS cancelBookingInfoRS) {
        this.cancelBookingInfoRS = cancelBookingInfoRS;
    }

    public ClsResultType getResultType() {
        return resultType;
    }

    public void setResultType(ClsResultType resultType) {
        this.resultType = resultType;
    }

}
