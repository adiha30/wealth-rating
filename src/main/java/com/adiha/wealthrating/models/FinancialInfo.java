package com.adiha.wealthrating.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Represents financial information of an individual, including cash, number of assets, and methods for fortune calculation.
 */
@AllArgsConstructor
@Getter
public class FinancialInfo {

    /**
     * Cash amount owned by the individual.
     */
    @NotNull(message = "Cash cannot be null")
    private BigDecimal cash;

    /**
     * Number of assets owned by the individual.
     */
    private int numberOfAssets;

    /**
     * Calculates the total fortune of the individual based on the provided evaluation of a single asset.
     *
     * @param singleAssetEvaluation The evaluation value of a single asset.
     * @return The calculated fortune of the individual.
     */
    public BigDecimal calculateFortune(BigDecimal singleAssetEvaluation) {
        BigDecimal assetsValue = BigDecimal.valueOf(numberOfAssets).multiply(singleAssetEvaluation);

        return cash.add(assetsValue);
    }
}
