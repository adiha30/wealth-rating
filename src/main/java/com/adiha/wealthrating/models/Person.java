package com.adiha.wealthrating.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Represents a person with personal and financial information.
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Person {

    /**
     * Unique identifier for the person.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "ID must be positive")
    private Long id;

    /**
     * Personal information of the person.
     */
    @Valid
    private PersonalInfo personalInfo;

    /**
     * Financial information of the person.
     */
    @Valid
    private FinancialInfo financialInfo;

    /**
     * Calculates the total fortune of the person based on the provided evaluation of a single asset.
     * References the calculateFortune method in FinancialInfo for easier access.
     *
     * @param singleAssetEvaluation The evaluation value of a single asset.
     * @return The calculated fortune of the person.
     */
    public BigDecimal calculateFortune(BigDecimal singleAssetEvaluation) {
        return financialInfo.calculateFortune(singleAssetEvaluation);
    }
}
