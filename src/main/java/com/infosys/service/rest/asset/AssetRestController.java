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
import com.mysql.fabric.xmlrpc.base.Array;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class AssetRestController {

    @Autowired
    AssetDao assetDao;

    private static final Logger LOGGER = LoggerFactory.getLogger( AssetRestController.class );

    @RequestMapping( value = "/app/test/getCapital/{country}", method = RequestMethod.GET, produces = "application/json" )
    public @ResponseBody ResponseEntity<String> getAssetDetails( @PathVariable String country ) throws IOException {
        LOGGER.info( "0;getCapital rest method invoked" );

        LOGGER.info( "0;Country : {} ", country );
        Countries count = assetDao.getCapital( country );
        String response = "Capital of " + country + " is " + count.getCapital();
        LOGGER.info( "0;Capital : {} ", count.getCapital() );
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
        StringBuilder sb = new StringBuilder();
        for ( Cities city : cities ) {
            if ( sb.length() != 0 )
                sb.append( "," );
            sb.append( city.getCity() );
        }
        String response = "Cities of " + bean.getCountry() + " are " + sb;
        return new ResponseEntity<String>( response, HttpStatus.OK );
    }

}