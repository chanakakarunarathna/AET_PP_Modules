package com.placepass.utils.ageband;

public enum PlacePassAgeBandType {

    ADULT(1),

    CHILD(2),

    INFANT(3),

    YOUTH(4),

    SENIOR(5);

    public final int ageBandId;

    PlacePassAgeBandType(int ageBandId) {
        this.ageBandId = ageBandId;
    }

    public int getAgeBandId() {
        return ageBandId;
    }

}
