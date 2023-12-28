package com.adiha.wealthrating.controllers;

import com.adiha.wealthrating.models.Person;
import com.adiha.wealthrating.models.RichState;
import com.adiha.wealthrating.models.entities.PersonEntity;
import com.adiha.wealthrating.services.WealthRatingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

/**
 * Controller class for handling wealth rating-related RESTful API endpoints.
 */
@RestController
@RequestMapping("/api/v1/wealth-rating")
@RequiredArgsConstructor
public class WealthRatingController {
    private final Logger logger = LoggerFactory.getLogger(WealthRatingController.class);

    private final WealthRatingService wealthRatingService;

    /**
     * Endpoint to evaluate the rich status of a person based on their financial information.
     *
     * @param person The person to be evaluated.
     * @return ResponseEntity with the evaluated RichState.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/check")
    public ResponseEntity<RichState> evaluateRichStatus(@Validated @RequestBody Person person) {
        try {
            RichState richState = wealthRatingService.evaluateRichStatus(person);

            logger.info("{} was evaluated successfully", person.getPersonalInfo().getFullName());

            return ResponseEntity.ok().body(richState);
        } catch (HttpClientErrorException.BadRequest e) {
            logger.warn("Person was sent with invalid data, or request was faulty");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.warn("Received a HTTP server error");

            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            logger.error("Something went wrong, please try again later");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to retrieve a list of all rich individuals.
     *
     * @return ResponseEntity with a list of rich individuals.
     */
    @GetMapping("/rich/all")
    public ResponseEntity<List<PersonEntity>> getAllRich() {
        return ResponseEntity.ok().body(wealthRatingService.getAllRich());
    }

    /**
     * Endpoint to retrieve information about a rich individual based on their ID.
     *
     * @param id The ID of the rich individual.
     * @return ResponseEntity with information about the rich individual.
     */
    @GetMapping("/rich/{id}")
    public ResponseEntity<PersonEntity> getRichById(@PathVariable(value = "id") long id) {
        PersonEntity richPerson = wealthRatingService.getRichById(id);

        if (richPerson == null) {
            logger.warn("Person with id {} was not found", id);

            return ResponseEntity.notFound().build();
        }

        logger.info("Person with id {} was found", id);

        return ResponseEntity.ok().body(richPerson);
    }
}
