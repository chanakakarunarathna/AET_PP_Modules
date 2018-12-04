package com.gobe.connector.domain.gobe.availability;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeSlot {
    private String groupId;

    private String startTime;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}