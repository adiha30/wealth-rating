package com.adiha.wealthrating.controllers;

import com.adiha.wealthrating.models.Person;
import com.adiha.wealthrating.models.RichState;
import com.adiha.wealthrating.services.WealthRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/api/v1/wealth-rating")
@RequiredArgsConstructor
public class WealthRatingController {

    private final WealthRatingService wealthRatingService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/check-rich")
    public ResponseEntity<RichState> checkRich(@Validated Person person) {
        try {
            RichState richState = wealthRatingService.evaluateRichStatus(person);

            return ResponseEntity.ok().body(richState);
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RichState.NOT_RICH);
        } catch (HttpClientErrorException.BadRequest e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RichState.NOT_RICH);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(RichState.NOT_RICH);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RichState.NOT_RICH);
        }
    }

    @GetMapping("/rich/all")
    public ResponseEntity<String> getAllRich() {
        return ResponseEntity.ok().body("OK");
    }

    @GetMapping("/rich/{id}")
    public ResponseEntity<String> getRichById(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok().body("OK");
    }
}
