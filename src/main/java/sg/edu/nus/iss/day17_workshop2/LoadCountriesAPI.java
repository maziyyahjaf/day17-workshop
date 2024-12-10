package sg.edu.nus.iss.day17_workshop2;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import sg.edu.nus.iss.day17_workshop2.service.CurrencyRestService;

@Component
public class LoadCountriesAPI {

    private final CurrencyRestService currencyRestService;
    
    public LoadCountriesAPI(CurrencyRestService currencyRestService) {
        this.currencyRestService = currencyRestService;
    }

    @PostConstruct
    public void init() {
        // make the api call to load the countries
        currencyRestService.fetchCountries();
    }
}
