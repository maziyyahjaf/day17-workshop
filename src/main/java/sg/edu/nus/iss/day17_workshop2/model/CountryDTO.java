package sg.edu.nus.iss.day17_workshop2.model;

public class CountryDTO {

    private String currencyName;
    private String currencySymbol;

    
    public CountryDTO() {
    }

    
    public CountryDTO(String currencyName, String currencySymbol) {
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
    }


    public String getCurrencyName() {
        return currencyName;
    }
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
    public String getCurrencySymbol() {
        return currencySymbol;
    }
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    
    
}
