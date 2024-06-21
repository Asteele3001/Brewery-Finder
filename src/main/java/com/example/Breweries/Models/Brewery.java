package com.example.Breweries.Models;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class Brewery {
    private int id;
    private String breweryName;
    private String streetAddress;
    private String city;
    private String stateAbbreviation;
    private String zipCode;
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBreweryName() {
        return breweryName;
    }

    public void setBreweryName(String breweryName) {
        this.breweryName = breweryName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Brewery(int id, String breweryName, String streetAddress, String city, String stateAbbreviation, String zipCode, String phoneNumber) {
        this.id = id;
        this.breweryName = breweryName;
        this.streetAddress = streetAddress;
        this.city = city;
        this.stateAbbreviation = stateAbbreviation;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
    }

    public Brewery() {

    }
}
