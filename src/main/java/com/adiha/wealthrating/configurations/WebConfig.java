package com.adiha.wealthrating.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * This is a configuration class for the application.
 */
@Configuration
public class WebConfig {

    /**
     * The returned RestTemplate object is used to send HTTP requests. It simplifies communication with HTTP servers, and enforces RESTful principles.
     * @return a new instance of RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}