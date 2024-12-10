package sg.edu.nus.iss.day17_workshop2.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.day17_workshop2.model.Country;
import sg.edu.nus.iss.day17_workshop2.service.CurrencyRestService;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyRestController {

    private final CurrencyRestService currencyRestService;

    public CurrencyRestController(CurrencyRestService currencyRestService) {
        this.currencyRestService = currencyRestService;
    }
    

    @GetMapping("")
    public ResponseEntity<Map<String, Country>> getAllCountries() {
        Map<String, Country> countries = new HashMap<>();
        countries = currencyRestService.getAllCountries();
        
        return ResponseEntity.ok().body(countries);
    }

    @GetMapping("/rate")
    public ResponseEntity<Double> getConversionrate(@RequestParam("currencyFrom") String currencyFrom, 
                                                    @RequestParam("currencyTo") String currencyTo)  {
        double conversionRate = currencyRestService.getCurrencyConversionRate(currencyFrom, currencyTo);

        return ResponseEntity.ok().body(conversionRate);
    }
}
