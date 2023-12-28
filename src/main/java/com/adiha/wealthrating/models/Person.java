package com.adiha.wealthrating.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "ID must be positive")
    private Long id;

    @Valid
    private PersonalInfo personalInfo;

    @Valid
    private FinancialInfo financialInfo;

    public BigDecimal calculateFortune(BigDecimal singleAssetEvaluation) {
        return financialInfo.calculateFortune(singleAssetEvaluation);
    }
}
