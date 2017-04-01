package com.infosys.service.rest.asset;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.service.dao.AssetDao;
import com.infosys.service.model.Cities;
import com.infosys.service.model.Countries;
import com.infosys.service.model.CountryDetails;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class AssetRestController {

    @Autowired
    AssetDao assetDao;

    private static final Logger LOGGER = LoggerFactory.getLogger( AssetRestController.class );

    String response;

    @RequestMapping( value = "/app/test/getCapital/{country}", method = RequestMethod.GET, produces = "application/json" )
    public @ResponseBody ResponseEntity<String> getAssetDetails( @PathVariable String country ) throws IOException {
        LOGGER.info( "1;getCapital rest method invoked" );
        LOGGER.info( "2;Country in the getCapital request is : {} ", country );

        Countries count = assetDao.getCapital( country );
        if ( count != null )
            response = "Capital of " + country + " is " + count.getCapital();
        else
            response = "Capital of " + country + " is not in database ";

        LOGGER.info( "6;response of getCapital is : {} ", response );
        return new ResponseEntity<String>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/app/test/getCities", method = RequestMethod.POST, consumes = "application/json", produces = "application/json" )
    public ResponseEntity<String> loadAssetDetails( @RequestBody String request )
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info( "0;getCities method called" );

        Countries bean = new Countries();
        bean = objectMapper.readValue( request, Countries.class );
        LOGGER.info( "0;Countries in request is : {} ", bean.getCountry() );

        List<Cities> cities = assetDao.getCities( bean.getCountry() );
        if ( cities != null && cities.size() > 0 ) {
            StringBuilder sb = new StringBuilder();
            for ( Cities city : cities ) {
                if ( sb.length() != 0 )
                    sb.append( "," );
                sb.append( city.getCity() );
            }
            response = "Cities of " + bean.getCountry() + " are " + sb;
        } else {
            response = "Cities of " + bean.getCountry() + " is not in database ";
        }

        LOGGER.info( "0;response : {} ", response );
        return new ResponseEntity<String>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/app/test/saveFile", method = RequestMethod.POST, consumes = "application/json", produces = "application/json" )
    public ResponseEntity<String> saveFile( @RequestBody String request )
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info( "0;saveFile method called" );

        CountryDetails countryDetails = objectMapper.readValue( request, CountryDetails.class );

        if ( countryDetails != null ) {
            System.out.println( "isCountryExist: " + assetDao.isCountryExist( countryDetails.getCountry() ) );
            if ( !assetDao.isCountryExist( countryDetails.getCountry() ) ) {
                Countries count = new Countries();

                count.setCountry( countryDetails.getCountry() );
                count.setCapital( countryDetails.getCapital() );

                Set<Cities> cities = new HashSet<>();
                Cities cit;
                for ( String st : countryDetails.getCities() ) {
                    cit = new Cities();
                    cit.setCity( st );
                    cit.setCountryId( count );
                    assetDao.saveCities( cit );
                    cities.add( cit );
                    cit = null;
                }
                count.setCities( cities );
                assetDao.saveCountry( count );
                response = "Country  " + countryDetails.getCountry() + " and their cities saved successfully in database ";

            } else {
                response = "Country  " + countryDetails.getCountry() + " already exist in database ";
            }
        }

        LOGGER.info( "0;response : {} ", response );
        return new ResponseEntity<String>( response, HttpStatus.OK );
    }

}