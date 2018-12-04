package com.placepass.connector.common.product;

import java.util.List;

import com.placepass.connector.common.common.BaseRS;

public class GetAvailabilityRS extends BaseRS {

    private List<Availability> availability;

    public List<Availability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Availability> availability) {
        this.availability = availability;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GetAvailabilityRS [" + (availability != null ? "availability=" + availability : "") + "]";
    }

}
