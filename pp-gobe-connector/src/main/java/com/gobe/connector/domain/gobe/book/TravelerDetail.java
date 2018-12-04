package com.gobe.connector.domain.gobe.book;

import java.util.List;

/**
 * Created on 8/7/2017.
 */
public class TravelerDetail {

    private String groupId;

    private List<TravelerDetailsForGroup> travelerDetailsForGroup;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<TravelerDetailsForGroup> getTravelerDetailsForGroup() {
        return travelerDetailsForGroup;
    }

    public void setTravelerDetailsForGroup(List<TravelerDetailsForGroup> travelerDetailsForGroup) {
        this.travelerDetailsForGroup = travelerDetailsForGroup;
    }
}
