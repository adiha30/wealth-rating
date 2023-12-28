package com.adiha.wealthrating.services;

import com.adiha.wealthrating.models.Person;
import com.adiha.wealthrating.models.RichState;
import com.adiha.wealthrating.models.entities.PersonEntity;
import com.adiha.wealthrating.repository.RichRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.adiha.wealthrating.TestUtils.getPerson;
import static com.adiha.wealthrating.TestUtils.getPersonEntity;
import static com.adiha.wealthrating.utils.Constants.ASSET_EVALUATION_API;
import static com.adiha.wealthrating.utils.Constants.WEALTH_THRESHOLD_API;
import static com.google.common.truth.Truth.assertWithMessage;
import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class WealthRatingServiceTest {

    private static final int EVALUATION_OF_ASSET = 10;
    public static final int LOW_THRESHOLD = 1;
    private static final long HIGH_THRESHOLD = 10000000;

    /**
     * System Under Test
     */
    @Autowired
    private WealthRatingService sut;

    @MockBean
    private RichRepository richRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    @DisplayName("Testing evaluateRichStatus returning RICH when person is rich")
    void testEvaluateRichStatusReturnRich() {
        Person givenPerson = getPerson(1L);
        when(restTemplate.getForEntity(eq(ASSET_EVALUATION_API), eq(BigDecimal.class), eq(Map.of("city", givenPerson.getPersonalInfo().getCity()))))
                .thenReturn(ResponseEntity.ok(BigDecimal.valueOf(EVALUATION_OF_ASSET)));
        when(restTemplate.getForEntity(eq(WEALTH_THRESHOLD_API), eq(BigDecimal.class)))
                .thenReturn(ResponseEntity.ok(BigDecimal.valueOf(LOW_THRESHOLD)));
        when(richRepository.save(any(PersonEntity.class))).thenReturn(getPersonEntity(1L));


        RichState actualState = sut.evaluateRichStatus(givenPerson);

        Assertions.assertAll(
                () -> assertWithMessage("returned person entity is null")
                        .that(actualState)
                        .isNotNull(),
                () -> assertWithMessage("returned person entity is not equal to expected")
                        .that(actualState)
                        .isEqualTo(RichState.RICH)
        );
    }

    @Test
    @DisplayName("Testing evaluateRichStatus returning NOT_RICH when person is not rich")
    void testEvaluateRichStatusReturnNotRich() {
        Person givenPerson = getPerson(1L);
        when(restTemplate.getForEntity(eq(ASSET_EVALUATION_API), eq(BigDecimal.class), eq(Map.of("city", givenPerson.getPersonalInfo().getCity()))))
                .thenReturn(ResponseEntity.ok(BigDecimal.valueOf(EVALUATION_OF_ASSET)));
        when(restTemplate.getForEntity(eq(WEALTH_THRESHOLD_API), eq(BigDecimal.class)))
                .thenReturn(ResponseEntity.ok(BigDecimal.valueOf(HIGH_THRESHOLD)));

        RichState actualState = sut.evaluateRichStatus(givenPerson);

        Assertions.assertAll(
                () -> assertWithMessage("returned person entity is null")
                        .that(actualState)
                        .isNotNull(),
                () -> assertWithMessage("returned person entity is not equal to expected")
                        .that(actualState)
                        .isEqualTo(RichState.NOT_RICH)
        );
    }

    @Test
    @DisplayName("Testing getAllRich returning valid number of rich people")
    void testGetAllRichReturnValid() {
        List<PersonEntity> expectedRichPeople = List.of(
                getPersonEntity(1L),
                getPersonEntity(2L),
                getPersonEntity(3L));
        when(richRepository.findAll()).thenReturn(expectedRichPeople);

        List<PersonEntity> actualRichPeople = sut.getAllRich();

        Assertions.assertAll(
                () -> assertWithMessage("returned list of rich people is null")
                        .that(actualRichPeople)
                        .isNotNull(),
                () -> assertWithMessage("returned list of rich people is empty")
                        .that(actualRichPeople)
                        .isNotEmpty(),
                () -> assertWithMessage("returned list of rich people is not equal to expected")
                        .that(actualRichPeople)
                        .containsExactlyElementsIn(expectedRichPeople)
        );
    }

    @Test
    @DisplayName("Testing getAllRich returning empty list when no rich people found")
    void testGetAllRichReturnEmpty() {
        List<PersonEntity> expectedRichPeople = emptyList();
        when(richRepository.findAll()).thenReturn(expectedRichPeople);

        List<PersonEntity> actualRichPeople = sut.getAllRich();

        Assertions.assertAll(
                () -> assertWithMessage("returned list of rich people is null")
                        .that(actualRichPeople)
                        .isNotNull(),
                () -> assertWithMessage("returned list of rich people is not empty")
                        .that(actualRichPeople)
                        .isEmpty()
        );
    }

    @Test
    @DisplayName("Testing getRichById returning valid rich person")
    void testGetRichByIdReturnValid() {
        PersonEntity expectedRichPerson = getPersonEntity(1L);
        when(richRepository.findById(any())).thenReturn(Optional.of(expectedRichPerson));

        PersonEntity actualRichPerson = sut.getRichById(1L);

        Assertions.assertAll(
                () -> assertWithMessage("returned rich person is null")
                        .that(actualRichPerson)
                        .isNotNull(),
                () -> assertWithMessage("returned rich person is not equal to expected")
                        .that(actualRichPerson)
                        .isEqualTo(expectedRichPerson)
        );
    }

    @Test
    @DisplayName("Testing getRichById returning null when no rich person found")
    void testGetRichByIdReturnNull() {
        when(richRepository.findById(any())).thenReturn(Optional.empty());

        PersonEntity actualRichPerson = sut.getRichById(1L);

        Assertions.assertAll(
                () -> assertWithMessage("returned rich person is not null")
                        .that(actualRichPerson)
                        .isNull()
        );
    }
}
