package com.adiha.wealthrating.controllers;

import com.adiha.wealthrating.services.WealthRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wealth-rating")
@RequiredArgsConstructor
public class WealthRatingController {
    private final WealthRatingService wealthRatingService;
}
