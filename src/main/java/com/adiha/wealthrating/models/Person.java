package com.adiha.wealthrating.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Person {
    private long id;
    private PersonalInfo personalInfo;
    private FinancialInfo financialInfo;
}
