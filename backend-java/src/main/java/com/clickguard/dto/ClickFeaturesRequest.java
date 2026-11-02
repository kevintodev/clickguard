package com.clickguard.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ClickFeaturesRequest {

    @NotNull
    @PositiveOrZero
    private Double clicksPerMinute;

    @NotNull
    @PositiveOrZero
    private Double amountVsMean;

    @NotNull
    @Min(1)
    private Integer uniqueIps;

    @NotNull
    @PositiveOrZero
    private Double secondsSinceLastClick;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double userAgentEntropy;

    public Double getClicksPerMinute() { return clicksPerMinute; }
    public void setClicksPerMinute(Double value) { this.clicksPerMinute = value; }

    public Double getAmountVsMean() { return amountVsMean; }
    public void setAmountVsMean(Double value) { this.amountVsMean = value; }

    public Integer getUniqueIps() { return uniqueIps; }
    public void setUniqueIps(Integer value) { this.uniqueIps = value; }

    public Double getSecondsSinceLastClick() { return secondsSinceLastClick; }
    public void setSecondsSinceLastClick(Double value) { this.secondsSinceLastClick = value; }

    public Double getUserAgentEntropy() { return userAgentEntropy; }
    public void setUserAgentEntropy(Double value) { this.userAgentEntropy = value; }
}