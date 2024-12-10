package sg.edu.nus.iss.day17_workshop2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.day17_workshop2.model.Country;
import sg.edu.nus.iss.day17_workshop2.model.CountryDTO;
import sg.edu.nus.iss.day17_workshop2.service.CurrencyService;



@Controller
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;
    
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/converter")
    public String showCurrencyConverter(Model model) {
        
        Map<String, Country> countriesMap =currencyService.getAllCountries();
        List<Country> countries = new ArrayList<>(countriesMap.values());
        model.addAttribute("countries", countries);

        return "index";
    }

    @PostMapping("/converter")
    public String handleConversionRequest(
        @RequestParam("currencyFrom") String currencyFrom,
        @RequestParam("currencyTo") String currencyTo,
        @RequestParam("amount") double amount,
        Model model
    ) {

        double convertedAmount = 0;
        try {
            convertedAmount = currencyService.convertedAmount(currencyFrom, currencyTo, amount);
        } catch (Exception e) {
            model.addAttribute("error", "Currency conversion failed. Please try again later.");
        }
        // System.out.println("convertedAmount " + convertedAmount);

        Map<String, Country> countriesMap =currencyService.getAllCountries();
        List<Country> countries = new ArrayList<>(countriesMap.values());

        Optional<Country> countryFromDetails = countries.stream()
                                                .filter(c -> c.getCurrencyId().equals(currencyFrom))
                                                .findFirst();
        Country countryFrom = countryFromDetails.get();
        CountryDTO countryFromDTO = new CountryDTO(countryFrom.getCurrencyName(), countryFrom.getCurrencySymbol());

        Optional<Country> countryToDetails = countries.stream()
                                                        .filter(c -> c.getCurrencyId().equals(currencyTo))
                                                        .findFirst();
        Country countryTo = countryToDetails.get();
        CountryDTO countryToDTO = new CountryDTO(countryTo.getCurrencyName(), countryTo.getCurrencySymbol());

        model.addAttribute("countryFromDTO", countryFromDTO);
        model.addAttribute("amount", amount);
        model.addAttribute("countryToDTO", countryToDTO);
        model.addAttribute("convertedAmount", convertedAmount);

        return "result";
    }
        
    
}
