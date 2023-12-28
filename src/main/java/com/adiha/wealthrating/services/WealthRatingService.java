package com.adiha.wealthrating.services;

import com.adiha.wealthrating.models.Person;
import com.adiha.wealthrating.models.RichState;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.adiha.wealthrating.utils.Constants.ASSET_EVALUATION_API;
import static com.adiha.wealthrating.utils.Constants.WEALTH_THRESHOLD_API;

@Service
@RequiredArgsConstructor
public class WealthRatingService {

    private final RestTemplate restTemplate;

    public RichState evaluateRichStatus(Person person) {
        try {
            BigDecimal singleAssetEvaluationByCity = getEvaluationByCity(person.getPersonalInfo().getCity());
            BigDecimal wealthThreshold = getWealthThreshold();

            BigDecimal personFortune = person.calculateFortune(singleAssetEvaluationByCity);

            if (personFortune.compareTo(wealthThreshold) >= 0) {
                //TODO save to DB - Only id, firstname, lastname, fortune

                return RichState.RICH;
            }

            return RichState.NOT_RICH;
        } catch (Exception e) {
            return RichState.NOT_RICH;
        }
    }

    private BigDecimal getEvaluationByCity(String city) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("city", city);

        ResponseEntity<BigDecimal> responseEntity = restTemplate.getForEntity(ASSET_EVALUATION_API, BigDecimal.class, urlVariables);

        return responseEntity.getBody();
    }

    private BigDecimal getWealthThreshold() {
        ResponseEntity<BigDecimal> responseEntity = restTemplate.getForEntity(WEALTH_THRESHOLD_API, BigDecimal.class);

        return responseEntity.getBody();
    }
}
