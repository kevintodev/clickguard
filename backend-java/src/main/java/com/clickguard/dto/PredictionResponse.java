package com.clickguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PredictionResponse {

    private Double score;

    @JsonProperty("is_anomaly")
    private Boolean isAnomaly;

    private String reason;

    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }

    public Boolean getIsAnomaly() {
        return isAnomaly;
    }
    public void setIsAnomaly(Boolean isAnomaly) {
        this.isAnomaly = isAnomaly;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

}