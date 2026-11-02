package com.clickguard.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ClickRequest {

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    @NotNull
    @PositiveOrZero
    private Double amount;

    @Valid
    @NotNull
    private ClickFeaturesRequest features;

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public ClickFeaturesRequest getFeatures() { return features; }
    public void setFeatures(ClickFeaturesRequest features) { this.features = features; }
}