package com.adiha.wealthrating.utils;

import com.adiha.wealthrating.models.Person;
import com.adiha.wealthrating.models.entities.PersonEntity;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PersonEntityConverter {

    public static PersonEntity convertToEntity(Person person, BigDecimal fortune) {
        return PersonEntity.builder().
                id(person.getId()).
                firstName(person.getPersonalInfo().getFirstName()).
                lastName(person.getPersonalInfo().getLastName()).
                fortune(fortune)
                .build();
    }
}
