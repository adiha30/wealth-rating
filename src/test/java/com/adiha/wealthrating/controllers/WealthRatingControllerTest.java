package com.adiha.wealthrating.controllers;

import com.adiha.wealthrating.models.Person;
import com.adiha.wealthrating.models.RichState;
import com.adiha.wealthrating.models.entities.PersonEntity;
import com.adiha.wealthrating.services.WealthRatingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.adiha.wealthrating.TestUtils.getPerson;
import static com.adiha.wealthrating.TestUtils.getPersonEntity;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class WealthRatingControllerTest {

    /** System Under Test */
    @Autowired
    private WealthRatingController sut;

    @MockBean
    private WealthRatingService wealthRatingService;

    @Test
    @DisplayName("evaluateRichStatus returns valid response")
    void evaluateRichStatusReturnsValidResponse() {
        Person givenPerson = getPerson(1L);
        when(wealthRatingService.evaluateRichStatus(eq(givenPerson))).thenReturn(RichState.RICH);

        ResponseEntity<RichState> actualResponseEntity = sut.evaluateRichStatus(givenPerson);

        Assertions.assertAll(
                () -> assertWithMessage("response status code is not as expected")
                        .that(actualResponseEntity.getStatusCode())
                        .isEqualTo(HttpStatus.OK),
                () -> assertWithMessage("response body is not as expected")
                        .that(actualResponseEntity.getBody())
                        .isEqualTo(RichState.RICH)
        );
    }

    @Test
    @DisplayName("evaluateRichStatus returns bad request response when person is invalid")
    void evaluateRichStatusReturnsBadRequestResponseWhenPersonIsInvalid() {
        Person givenPerson = getPerson(1L);
        HttpClientErrorException badRequestException = HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "Bad Request", new HttpHeaders(), new byte[0], StandardCharsets.UTF_8);
        when(wealthRatingService.evaluateRichStatus(eq(givenPerson))).thenThrow(badRequestException);

        ResponseEntity<RichState> actualResponseEntity = sut.evaluateRichStatus(givenPerson);

        Assertions.assertAll(
                () -> assertWithMessage("response status code is not as expected")
                        .that(actualResponseEntity.getStatusCode())
                        .isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertWithMessage("response body is not as expected")
                        .that(actualResponseEntity.getBody())
                        .isNull()
        );
    }

    @Test
    @DisplayName("evaluateRichStatus returns error based off of exception")
    void evaluateRichStatusReturnsErrorBasedOffOfException() {
        Person givenPerson = getPerson(1L);
        HttpServerErrorException httpServerErrorException = new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        when(wealthRatingService.evaluateRichStatus(eq(givenPerson))).thenThrow(httpServerErrorException);

        ResponseEntity<RichState> actualResponseEntity = sut.evaluateRichStatus(givenPerson);

        Assertions.assertAll(
                () -> assertWithMessage("response status code is not as expected")
                        .that(actualResponseEntity.getStatusCode())
                        .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR),
                () -> assertWithMessage("response body is not as expected")
                        .that(actualResponseEntity.getBody())
                        .isNull()
        );
    }

    @Test
    @DisplayName("evaluateRichStatus returns internal server error when exception is not handled")
    void evaluateRichStatusReturnsInternalServerErrorWhenExceptionIsNotHandled() {
        Person givenPerson = getPerson(1L);
        RuntimeException runtimeException = new RuntimeException("Something went wrong");
        when(wealthRatingService.evaluateRichStatus(eq(givenPerson))).thenThrow(runtimeException);

        ResponseEntity<RichState> actualResponseEntity = sut.evaluateRichStatus(givenPerson);

        Assertions.assertAll(
                () -> assertWithMessage("response status code is not as expected")
                        .that(actualResponseEntity.getStatusCode())
                        .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR),
                () -> assertWithMessage("response body is not as expected")
                        .that(actualResponseEntity.getBody())
                        .isNull()
        );
    }

    @Test
    @DisplayName("getAllRich returns valid response")
    void getAllRichReturnsValidResponse() {
        List<PersonEntity> personEntities = List.of(getPersonEntity(1L), getPersonEntity(2L));
        when(wealthRatingService.getAllRich()).thenReturn(personEntities);

        ResponseEntity<List<PersonEntity>> actualResponseEntity = sut.getAllRich();

        Assertions.assertAll(
                () -> assertWithMessage("response status code is not as expected")
                        .that(actualResponseEntity.getStatusCode())
                        .isEqualTo(HttpStatus.OK),
                () -> assertWithMessage("response body is not as expected")
                        .that(actualResponseEntity.getBody())
                        .containsExactlyElementsIn(personEntities)
        );
    }

    @Test
    @DisplayName("getRichById returns valid response")
    void getRichByIdReturnsValidResponse() {
        PersonEntity personEntity = getPersonEntity(1L);
        when(wealthRatingService.getRichById(eq(1L))).thenReturn(personEntity);

        ResponseEntity<PersonEntity> actualResponseEntity = sut.getRichById(1L);

        Assertions.assertAll(
                () -> assertWithMessage("response status code is not as expected")
                        .that(actualResponseEntity.getStatusCode())
                        .isEqualTo(HttpStatus.OK),
                () -> assertWithMessage("response body is not as expected")
                        .that(actualResponseEntity.getBody())
                        .isEqualTo(personEntity)
        );
    }

    @Test
    @DisplayName("getRichById returns not found response when person is not found")
    void getRichByIdReturnsNotFoundResponseWhenPersonIsNotFound() {
        when(wealthRatingService.getRichById(eq(1L))).thenReturn(null);

        ResponseEntity<PersonEntity> actualResponseEntity = sut.getRichById(1L);

        Assertions.assertAll(
                () -> assertWithMessage("response status code is not as expected")
                        .that(actualResponseEntity.getStatusCode())
                        .isEqualTo(HttpStatus.NOT_FOUND),
                () -> assertWithMessage("response body is not as expected")
                        .that(actualResponseEntity.getBody())
                        .isNull()
        );
    }
}
