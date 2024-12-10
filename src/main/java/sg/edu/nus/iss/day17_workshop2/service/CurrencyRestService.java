package sg.edu.nus.iss.day17_workshop2.service;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.iss.day17_workshop2.constant.Constant;
import sg.edu.nus.iss.day17_workshop2.model.Country;

@Service
public class CurrencyRestService {

    @Value("${api.key}") // inject from application properties
    private String apiKey;

    RestTemplate restTemplate = new RestTemplate();
    Map<String, Country> countries = new HashMap<>();  // Create a map to store the country objects

    // call the external API to load the countries
    public void fetchCountries() {

        // https://free.currconv.com/api/v7/countries?apiKey=59ab9859e27b8f5447e6
        // Build the URL to call the external API
        String query = Constant.getCountriesURL + "apiKey=" + apiKey;
        
        // Make the HTTP GET request and get the response as a String
        ResponseEntity<String> response = restTemplate.getForEntity(query, String.class);
        String responseBody = response.getBody();

        // Create a JSON-P reader to parse the string response
        JsonReader jsonReader = Json.createReader(new StringReader(responseBody));
        JsonObject root = jsonReader.readObject();

        // Get the 'results' object from the response
        JsonObject results = root.getJsonObject("results");

        // Iterate through the entries in 'results'
        for (Entry<String, JsonValue> resultsEntry : results.entrySet()) {
           
            // Extract the country abbreviation 
            String countryABV = resultsEntry.getKey();
            Country country = parseCountry(resultsEntry.getValue());

            // add country object to countries map
            countries.put(countryABV, country);

        }

    }

    private Country parseCountry(JsonValue value) {

        // Convert JsonValue to JsonObject
        JsonObject countryData = value.asJsonObject();

        // Extract fields from the JsonObject
        String countryCode = countryData.getString("alpha3", null);
        String currencyId = countryData.getString("currencyId", null);
        String currencyName = countryData.getString("currencyName", null);
        String currencySymbol = countryData.getString("currencySymbol", null);
        String countryId = countryData.getString("id", null);
        String countryName = countryData.getString("name", null);

        // create a country object with the extracted fields
        Country country = new Country(countryCode, currencyId, currencyName, currencySymbol, countryId, countryName);
        return country;

    }


    public Map<String, Country> getAllCountries() {
        return countries;
    }


    // call the external API to get the currency conversion rate
    public double getCurrencyConversionRate(String currencyFrom, String currencyTo) {

        // build the query
        String currencyFrom_currencyTo = currencyFrom + "_" + currencyTo;
        // String query = Constant.getConversionRateURL + "q=" + currencyFrom_currencyTo + "&compact=ultra" + "&apiKey=" + apiKey;

        String query = buildURL(currencyFrom, currencyTo);

        // Make the HTTP GET request and get the response as a String
        ResponseEntity<String> response = restTemplate.getForEntity(query, String.class);
        JsonReader jsonReader = Json.createReader(new StringReader(response.getBody()));
        JsonObject root = jsonReader.readObject();
        double conversionRate = root.getJsonNumber(currencyFrom_currencyTo).doubleValue();

        return conversionRate;

    }

    public String buildURL(String currencyFrom, String currencyTo) {

        if (currencyFrom == null || currencyTo == null || currencyFrom.isEmpty() || currencyTo.isEmpty()) {
            throw new IllegalArgumentException("CurrencyFrom and CurrencyTo must not be null or empty");
        }

        String url = UriComponentsBuilder
                    .fromUriString(Constant.getConversionRateURL)
                    .queryParam("q", String.format("%s_%s", currencyFrom, currencyTo))
                    .queryParam("compact", Constant.COMPACT_TYPE)
                    .queryParam("apiKey", apiKey)
                    .build()
                    .toUriString();
        return url;
    }
    
}
