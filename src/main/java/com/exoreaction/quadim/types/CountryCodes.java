package com.exoreaction.quadim.types;

import java.util.HashMap;
import java.util.Locale;

public class CountryCodes {

    private HashMap<String, String> countries = new HashMap<String, String>();
    private HashMap<String, String> countries_no = new HashMap<String, String>();
    private HashMap<String, String> codes = new HashMap<String, String>();


    public CountryCodes() {
        String[] countryCodes = Locale.getISOCountries();

        for (String cc : countryCodes) {
            // country name , country code map
            countries.put(new Locale("", cc).getDisplayCountry().toLowerCase(), cc.toUpperCase());
            codes.put(cc.toUpperCase(), new Locale("", cc).getDisplayCountry());
        }

    }

    public String getCodeFromCountry(String country) {
        return countries.get(country.toLowerCase());
    }

    public String getCountryFromCode(String code) {
        return codes.get(code.toUpperCase());
    }

}
