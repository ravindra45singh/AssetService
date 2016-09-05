package com.infosys.service.rest.asset;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class AssetRestController {
    
    @Value( "${iris.smdp.simulator.response.file}" )
    private String smdpSimulatorResponseFilePath;
    
    //private static final Logger LOGGER = LoggerFactory.getLogger( AssetRestController.class );

    @RequestMapping( value = "/app/csvloader/{fileLocation}", method = RequestMethod.GET, produces = "application/json" )
    public @ResponseBody ResponseEntity<String> getAssetDetails( @PathVariable String fileLocation ) throws IOException {

        //LOGGER.info( "0; fileLocation : ", fileLocation );
        System.out.println( "fileLocation ::" + fileLocation );
        String location = fileLocation + ".csv";
        System.out.println( "location ::" + location );
        System.out.println( "smdpSimulatorResponseFilePath ::" + smdpSimulatorResponseFilePath );
        BufferedReader reader = new BufferedReader( new FileReader( smdpSimulatorResponseFilePath  ) );
        String line = null;
        Scanner scanner = null;
        int index = 0;
        List<CSVTaskBean> list = new ArrayList<>();
        while ( (line = reader.readLine()) != null ) {
            CSVTaskBean csv = new CSVTaskBean();
            scanner = new Scanner( line );
            scanner.useDelimiter( "," );
            while ( scanner.hasNext() ) {
                String data = scanner.next();
                if ( index == 0 )
                    csv.setcId( data );
                else if ( index == 1 )
                    csv.setaID( data );
                else if ( index == 2 )
                    csv.setaURL( data );
                else if ( index == 3 )
                    csv.setwURL( data );
                else
                    System.out.println( "invalid data::" + data );
                index++;
            }
            index = 0;
            list.add( csv );
        }
        reader.close();

        System.out.println( list );

        return new ResponseEntity<String>( "Successfully parsed CSV", HttpStatus.OK );
    }

    @RequestMapping( value = "/gsma/rsp2/es2plus/confirmOrder", method = RequestMethod.POST, produces = "application/json" )
    public ResponseEntity<String> loadAssetDetails( @RequestBody String request ) {
        return new ResponseEntity<String>( "", HttpStatus.OK );
    }

}