package com.infosys.service.rest.asset;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class AssetRestController {
    
    @Value( "${iris.smdp.simulator.response.file}" )
    private String getPath;
    
    private static final Logger LOGGER = LoggerFactory.getLogger( AssetRestController.class );

    @RequestMapping( value = "/app/test/{location}", method = RequestMethod.GET, produces = "application/json" )
    public @ResponseBody ResponseEntity<String> getAssetDetails( @PathVariable String location ) throws IOException {

        
        System.out.println( "getPath ::" + getPath );
        System.out.println( "location ::" + location );
        LOGGER.info("0;getPath : {} ",getPath);
        LOGGER.info("0;location : {} ",location);

        return new ResponseEntity<String>( "Successfully Executed", HttpStatus.OK );
    }

    @RequestMapping( value = "/gsma/rsp2/es2plus/confirmOrder", method = RequestMethod.POST, produces = "application/json" )
    public ResponseEntity<String> loadAssetDetails( @RequestBody String request ) {
        return new ResponseEntity<String>( "", HttpStatus.OK );
    }

}