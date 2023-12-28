package com.adiha.wealthrating.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class FinancialInfo {
    @NotNull(message = "Cash cannot be null")
    private BigDecimal cash;

    private int numberOfAssets;

    public BigDecimal calculateFortune(BigDecimal singleAssetEvaluation) {
        BigDecimal assetsValue = BigDecimal.valueOf(numberOfAssets).multiply(singleAssetEvaluation);

        return cash.add(assetsValue);
    }
}
