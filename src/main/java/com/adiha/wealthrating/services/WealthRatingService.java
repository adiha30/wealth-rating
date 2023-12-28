package com.adiha.wealthrating.services;

import com.adiha.wealthrating.models.Person;
import com.adiha.wealthrating.models.RichState;
import com.adiha.wealthrating.models.entities.PersonEntity;
import com.adiha.wealthrating.repository.RichRepository;
import com.adiha.wealthrating.utils.PersonEntityConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.adiha.wealthrating.utils.Constants.ASSET_EVALUATION_API;
import static com.adiha.wealthrating.utils.Constants.WEALTH_THRESHOLD_API;

@Service
@RequiredArgsConstructor
public class WealthRatingService {
    private final Logger logger = LoggerFactory.getLogger(WealthRatingService.class);

    private final RichRepository richRepository;
    private final RestTemplate restTemplate;

    public RichState evaluateRichStatus(Person person) {
        try {
            BigDecimal singleAssetEvaluationByCity = getEvaluationByCity(person.getPersonalInfo().getCity());
            BigDecimal wealthThreshold = getWealthThreshold();

            BigDecimal personFortune = person.calculateFortune(singleAssetEvaluationByCity);

            if (personFortune.compareTo(wealthThreshold) >= 0) {
                savePersonToDB(person, personFortune);

                logger.info("{} was found rich and saved to DB", person.getPersonalInfo().getFullName());

                return RichState.RICH;
            }

            logger.info("{} was found not rich", person.getPersonalInfo().getFullName());

            return RichState.NOT_RICH;
        } catch (Exception e) {
            return RichState.NOT_RICH;
        }
    }

    private void savePersonToDB(Person person, BigDecimal personFortune) {
        PersonEntity personEntity = PersonEntityConverter.convertToEntity(person, personFortune);
        richRepository.save(personEntity);
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

    public List<PersonEntity> getAllRich() {
        return richRepository.findAll();
    }

    public PersonEntity getRichById(long id) {
        return richRepository.findById(id)
                .orElse(null);
    }
}
