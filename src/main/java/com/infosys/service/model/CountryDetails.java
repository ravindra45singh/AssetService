package com.infosys.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class CountryDetails implements Serializable {

    private static final long serialVersionUID = 4336453529360581815L;

    private String country;
    private String capital;
    private String[] cities;

    public String getCountry() {
        return country;
    }

    public void setCountry( String country ) {
        this.country = country;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital( String capital ) {
        this.capital = capital;
    }

    public String[] getCities() {
        return cities;
    }

    public void setCities( String[] cities ) {
        this.cities = cities;
    }

}
