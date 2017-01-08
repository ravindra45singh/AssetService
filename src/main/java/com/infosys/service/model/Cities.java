package com.infosys.service.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "cities" )
public class Cities implements Serializable {
    
    private static final long serialVersionUID = 4336453529360581815L;

    private int cityId;
    private String city;
    private Countries countryId;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public int getCityId() {
        return cityId;
    }

    public void setCityId( int cityId ) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity( String city ) {
        this.city = city;
    }

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn( name = "countryId", referencedColumnName = "countryId" )
    public Countries getCountryId() {
        return countryId;
    }

    public void setCountryId( Countries countryId ) {
        this.countryId = countryId;
    }

}
