package com.adiha.wealthrating;

import com.adiha.wealthrating.models.FinancialInfo;
import com.adiha.wealthrating.models.Person;
import com.adiha.wealthrating.models.PersonalInfo;
import com.adiha.wealthrating.models.entities.PersonEntity;

import java.math.BigDecimal;

public class TestUtils {

    public static PersonEntity getPersonEntity(Long id) {
        return PersonEntity.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .fortune(BigDecimal.valueOf(1000000))
                .build();
    }

    public static Person getPerson(Long id) {
        return new Person(
                id,
                new PersonalInfo("John", "Doe", "New York"),
                new FinancialInfo(BigDecimal.valueOf(100), 5)
        );
    }
}
