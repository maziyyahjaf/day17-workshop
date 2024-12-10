package sg.edu.nus.iss.day17_workshop2.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.iss.day17_workshop2.constant.Constant;
import sg.edu.nus.iss.day17_workshop2.model.Country;

@Service
public class CurrencyService {

    RestTemplate restTemplate = new RestTemplate();

    public Map<String, Country> getAllCountries() {
        
        Map<String, Country> countries = new HashMap<>();

        RequestEntity<Void> req = RequestEntity.get(Constant.localURL).build();
        ResponseEntity<Map<String, Country>> response = restTemplate.exchange(req, new ParameterizedTypeReference<Map<String,Country>>() {});

        countries = response.getBody();

        return countries;

    }

    // what are you returning??
    // i need to return the amount you want to convert (original)
    // i need to return the converted amount using the conversion rate

    public double convertedAmount (String currencyFrom, String currencyTo, double amount) {

        // build the request
        String url = UriComponentsBuilder
                    .fromUriString(Constant.localRateURL)
                    .queryParam("currencyFrom", currencyFrom)
                    .queryParam("currencyTo", currencyTo)
                    .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();
        ResponseEntity<Double> response = restTemplate.exchange(req, new ParameterizedTypeReference<Double>() {});

        double conversionRate = response.getBody();
        double convertedAmount = amount * conversionRate;

        return convertedAmount;
        
    }
    
}
