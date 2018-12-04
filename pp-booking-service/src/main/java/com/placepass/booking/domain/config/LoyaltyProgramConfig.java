package com.placepass.booking.domain.config;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "loyalty_program_config")
public class LoyaltyProgramConfig {

    @Id
    private String id;

    private String partnerId;

    private String progId;

    private String progDisplayName;

    private int pointsAwardRatio;

    private Map<String, String> metadata;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getProgId() {
        return progId;
    }

    public void setProgId(String progId) {
        this.progId = progId;
    }

    public String getProgDisplayName() {
        return progDisplayName;
    }

    public void setProgDisplayName(String progDisplayName) {
        this.progDisplayName = progDisplayName;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public int getPointsAwardRatio() {
        return pointsAwardRatio;
    }

    public void setPointsAwardRatio(int pointsAwardRatio) {
        this.pointsAwardRatio = pointsAwardRatio;
    }

}
