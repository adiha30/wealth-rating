package com.adiha.wealthrating.utils;

import com.adiha.wealthrating.models.Person;
import com.adiha.wealthrating.models.entities.PersonEntity;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

/**
 * Utility class for converting a {@link Person} object to a {@link PersonEntity} object.
 */
@UtilityClass
public class PersonEntityConverter {

    /**
     * Converts a {@link Person} object to a {@link PersonEntity} object with the specified fortune.
     *
     * @param person  The {@link Person} object to convert.
     * @param fortune The fortune value to set in the resulting {@link PersonEntity}.
     * @return A new {@link PersonEntity} object representing the converted person.
     */
    public static PersonEntity convertToEntity(Person person, BigDecimal fortune) {
        return PersonEntity.builder()
                .id(person.getId())
                .firstName(person.getPersonalInfo().getFirstName())
                .lastName(person.getPersonalInfo().getLastName())
                .fortune(fortune)
                .build();
    }
}
