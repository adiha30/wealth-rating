package com.adiha.wealthrating.controllers;

import com.adiha.wealthrating.models.Person;
import com.adiha.wealthrating.models.RichState;
import com.adiha.wealthrating.models.entities.PersonEntity;
import com.adiha.wealthrating.services.WealthRatingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.adiha.wealthrating.TestUtils.getPerson;
import static com.adiha.wealthrating.TestUtils.getPersonEntity;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WealthRatingController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
public class WealthRatingControllerTest {

    /**
     * System Under Test
     */
    @Autowired
    private MockMvc sut;

    @MockBean
    private WealthRatingService wealthRatingService;


    @Test
    @DisplayName("evaluateRichStatus returns valid response")
    void testValid() throws Exception {
        Person givenPerson = getPerson(1L);

        when(wealthRatingService.evaluateRichStatus(any())).thenReturn(RichState.RICH);

        sut.perform(MockMvcRequestBuilders
                        .post("/api/v1/wealth-rating/check")
                        .content(asJsonString(givenPerson))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(addQuotesTo(RichState.RICH.name()))));
    }

    @Test
    @DisplayName("evaluateRichStatus returns bad request response when person is invalid")
    void testInvalid() throws Exception {
        Person givenPerson = getPerson(1L);
        HttpClientErrorException badRequestException = HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "Bad Request", new HttpHeaders(), new byte[0], StandardCharsets.UTF_8);
        when(wealthRatingService.evaluateRichStatus(any())).thenThrow(badRequestException);

        sut.perform(MockMvcRequestBuilders
                        .post("/api/v1/wealth-rating/check")
                        .content(asJsonString(givenPerson))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(equalTo("")));
    }

    @Test
    @DisplayName("evaluateRichStatus returns error based off of exception")
    void testError() throws Exception {
        Person givenPerson = getPerson(1L);
        HttpClientErrorException httpServerErrorException = HttpClientErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", new HttpHeaders(), new byte[0], StandardCharsets.UTF_8);
        when(wealthRatingService.evaluateRichStatus(any())).thenThrow(httpServerErrorException);

        sut.perform(MockMvcRequestBuilders
                        .post("/api/v1/wealth-rating/check")
                        .content(asJsonString(givenPerson))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("")));
    }

    @Test
    @DisplayName("evaluateRichStatus returns internal server error when exception is not handled")
    void evaluateRichStatusReturnsInternalServerErrorWhenExceptionIsNotHandled() throws Exception {
        Person givenPerson = getPerson(1L);
        RuntimeException runtimeException = new RuntimeException("Something went wrong");
        when(wealthRatingService.evaluateRichStatus(any())).thenThrow(runtimeException);

        sut.perform(MockMvcRequestBuilders
                        .post("/api/v1/wealth-rating/check")
                        .content(asJsonString(givenPerson))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("")));
    }

    @Test
    @DisplayName("getAllRich returns valid response")
    void getAllRichReturnsValidResponse() throws Exception {
        List<PersonEntity> personEntities = List.of(getPersonEntity(1L), getPersonEntity(2L));
        when(wealthRatingService.getAllRich()).thenReturn(personEntities);

        sut.perform(MockMvcRequestBuilders
                        .get("/api/v1/wealth-rating/rich/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(asJsonString(personEntities))));
    }

    @Test
    @DisplayName("getRichById returns valid response")
    void getRichByIdReturnsValidResponse() throws Exception {
        PersonEntity personEntity = getPersonEntity(1L);
        when(wealthRatingService.getRichById(eq(1L))).thenReturn(personEntity);

        sut.perform(MockMvcRequestBuilders
                        .get("/api/v1/wealth-rating/rich/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(asJsonString(personEntity))));
    }

    @Test
    @DisplayName("getRichById returns not found response when person is not found")
    void getRichByIdReturnsNotFoundResponseWhenPersonIsNotFound() throws Exception {
        when(wealthRatingService.getRichById(eq(1L))).thenReturn(null);

        sut.perform(MockMvcRequestBuilders
                        .get("/api/v1/wealth-rating/rich/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("")));
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String addQuotesTo(String value) {
        return "\"" + value + "\"";
    }
}
