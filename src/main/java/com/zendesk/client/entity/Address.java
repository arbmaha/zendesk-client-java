package com.zendesk.client.entity;
/**
 * This entity stores information about
 * {@link Address}.
 *
 * @author Olga Savin
 */
public class Address {

    private String country;

    private String city;

    private String line1;

    private String state;

    private String postal_code;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", line1='" + line1 + '\'' +
                ", state='" + state + '\'' +
                ", postal_code='" + postal_code + '\'' +
                '}';
    }
}
