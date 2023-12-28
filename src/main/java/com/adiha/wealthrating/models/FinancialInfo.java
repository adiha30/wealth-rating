package com.adiha.wealthrating.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class FinancialInfo {
    private BigDecimal cash;
    private int numberOfAssets;
}
