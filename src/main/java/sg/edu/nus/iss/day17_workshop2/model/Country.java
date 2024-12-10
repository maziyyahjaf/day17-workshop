package sg.edu.nus.iss.day17_workshop2.model;

public class Country {
    private String countryCode;
    private String currencyId;
    private String currencyName;
    private String currencySymbol;
    private String countryId;
    private String countryName;
 
    public Country() {
    }

    public Country(String countryCode, String currencyId, String currencyName, String currencySymbol, String countryId, String countryName) {
        this.countryCode = countryCode;
        this.currencyId = currencyId;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.countryId = countryId;
        this.countryName = countryName;
    }





    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public String getCurrencyId() {
        return currencyId;
    }
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }
    public String getCurrencyName() {
        return currencyName;
    }
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
    public String getCountryId() {
        return countryId;
    }
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    


    


}
