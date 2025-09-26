package com.marketplace.paymentservice.model;

public class BillingAddress {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    // Constructors
    public BillingAddress() {}

    public BillingAddress(String street, String city, String country) {
        this.street = street;
        this.city = city;
        this.country = country;
    }

    // Getters and Setters
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}