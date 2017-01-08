package com.infosys.service.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table( name = "countries" )
@JsonInclude( Include.NON_NULL )
public class Countries implements Serializable {

    private static final long serialVersionUID = 4336453529360581815L;

    private int countryId;
    private String country;
    private String capital;
    private Set<Cities> cities;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public int getCountryId() {
        return countryId;
    }

    public void setCountryId( int countryId ) {
        this.countryId = countryId;
    }

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

    @OneToMany( mappedBy = "countryId",targetEntity = Cities.class, cascade = CascadeType.ALL )
    public Set<Cities> getCities() {
        return cities;
    }

    public void setCities( Set<Cities> cities ) {
        this.cities = cities;
    }
}
