package com.adiha.wealthrating.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class Person {
    @Id
    @Positive(message = "Id must be positive")
    private long id;

    @Valid
    private PersonalInfo personalInfo;

    @Valid
    private FinancialInfo financialInfo;

    public BigDecimal calculateFortune(BigDecimal singleAssetEvaluation) {
        return financialInfo.calculateFortune(singleAssetEvaluation);
    }
}
