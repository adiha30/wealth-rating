package com.adiha.wealthrating.utils;

import com.adiha.wealthrating.models.Person;
import com.adiha.wealthrating.models.entities.PersonEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static com.adiha.wealthrating.TestUtils.getPerson;
import static com.google.common.truth.Truth.assertWithMessage;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class PersonEntityConverterTest {

    public static final BigDecimal ARBITRARY_FORTUNE = BigDecimal.ONE;

    @Test
    @DisplayName("convertToEntity returns valid PersonEntity")
    void convertToEntityReturnsValidPersonEntity() {
        Person personToConvert = getPerson(1L);

        PersonEntity actualPersonEntity = PersonEntityConverter.convertToEntity(personToConvert, ARBITRARY_FORTUNE);

        Assertions.assertAll(
                () -> assertWithMessage("id is not as expected")
                        .that(actualPersonEntity.getId())
                        .isEqualTo(personToConvert.getId()),
                () -> assertWithMessage("firstName is not as expected")
                        .that(actualPersonEntity.getFirstName())
                        .isEqualTo(personToConvert.getPersonalInfo().getFirstName()),
                () -> assertWithMessage("lastName is not as expected")
                        .that(actualPersonEntity.getLastName())
                        .isEqualTo(personToConvert.getPersonalInfo().getLastName()),
                () -> assertWithMessage("fortune is not as expected")
                        .that(actualPersonEntity.getFortune())
                        .isEqualTo(ARBITRARY_FORTUNE)
        );
    }
}
