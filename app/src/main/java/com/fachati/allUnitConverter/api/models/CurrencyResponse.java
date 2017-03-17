

package com.fachati.allUnitConverter.api.models;


public class CurrencyResponse {

    private String base;
    private String date;
    private Rates rates;

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

    public Rates getRates() {
        return rates;
    }
}
