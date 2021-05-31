package com.example.jerusalemnews;

public class CountrywiseModel {
    private String country;
    private String confirmed;
    private String active;
    private String deceased;
    private String newConfirmed;
    private String newDeceased;
    private String recovered;
    private String tests;
    private String flag;

    public CountrywiseModel(String country, String confirmed, String active, String deceased, String newConfirmed, String newDeceased, String recovered, String tests, String flag) {
        this.country = country;
        this.confirmed = confirmed;
        this.active = active;
        this.deceased = deceased;
        this.newConfirmed = newConfirmed;
        this.newDeceased = newDeceased;
        this.recovered = recovered;
        this.tests = tests;
        this.flag = flag;
    }

    public String getCountry() {
        return country;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public String getActive() {
        return active;
    }

    public String getDeceased() {
        return deceased;
    }

    public String getNewConfirmed() {
        return newConfirmed;
    }

    public String getNewDeceased() {
        return newDeceased;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getTests() {
        return tests;
    }

    public String getFlag() {
        return flag;
    }
}
