/*package com.infosys.application;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.service.dao.AssetDao;
import com.infosys.service.model.Cities;
import com.infosys.service.model.Countries;
import com.infosys.service.model.CountryDetails;

import java.nio.file.WatchKey;
import java.nio.file.WatchService;

@Component
public class FileWatcher implements ApplicationListener<ContextRefreshedEvent> {

    @Value( "${fileLocation.toMonitor}" )
    private String fileLocation;

    @Autowired
    AssetDao assetDao;

    @SuppressWarnings( "unchecked" )
    @Override
    public void onApplicationEvent( ContextRefreshedEvent event ) {

        Path path = Paths.get( fileLocation );

        // Sanity check - Check if path is a folder
        try {
            Boolean isFolder = (Boolean) Files.getAttribute( path, "basic:isDirectory", NOFOLLOW_LINKS );
            if ( !isFolder ) {
                throw new IllegalArgumentException( "Path: " + path + " is not a folder" );
            }
        } catch ( IOException ioe ) {
            // Folder does not exists
            ioe.printStackTrace();
        }

        System.out.println( "Watching path: " + path );

        // We obtain the file system of the Path
        FileSystem fs = path.getFileSystem();

        // We create the new WatchService using the new try() block
        try ( WatchService service = fs.newWatchService()) {

            // We register the path to the service
            // We watch for creation events
            path.register( service, ENTRY_CREATE, ENTRY_MODIFY );

            // Start the infinite polling loop
            WatchKey key = null;
            while ( true ) {
                key = service.take();

                // Dequeueing events
                Kind<?> kind = null;
                CountryDetails countryDetails = null;
                ObjectMapper objectMapper = new ObjectMapper();
                for ( WatchEvent<?> watchEvent : key.pollEvents() ) {
                    // Get the type of the event
                    kind = watchEvent.kind();
                    if ( OVERFLOW == kind ) {
                        continue; // loop
                    } else if ( ENTRY_CREATE == kind ) {
                        // A new Path was created
                        Path newPath = ((WatchEvent<Path>) watchEvent).context();
                        String str = fileLocation + newPath;
                        System.out.println( "New path created: " + newPath );

                        countryDetails = objectMapper.readValue( new FileReader( str ), CountryDetails.class );
                        if ( countryDetails != null ) {
                            System.out.println( "isCountryExist: " + assetDao.isCountryExist( countryDetails.getCountry() ) );
                            if ( !assetDao.isCountryExist( countryDetails.getCountry() ) ) {
                                Countries count = new Countries();

                                count.setCountry( countryDetails.getCountry() );
                                count.setCapital( countryDetails.getCapital() );
                                assetDao.saveCountry( count );

                                Cities cit;
                                for ( String st : countryDetails.getCities() ) {
                                    cit = new Cities();
                                    cit.setCity( st );
                                    cit.setCountryId( count );
                                    assetDao.saveCities( cit );
                                    cit = null;

                                }

                            }
                        }

                    }
                    
                      else if (ENTRY_MODIFY == kind) { // A Path was modified
                     * Path newPath = ((WatchEvent<Path>) watchEvent).context();
                     * System.out.println("New path modified: " + newPath); }
                     * else if (ENTRY_DELETE == kind) { // A Path was deleted
                     * Path newPath = ((WatchEvent<Path>) watchEvent).context();
                     * System.out.println("New path deleted: " + newPath); }
                     
                }

                if ( !key.reset() ) {
                    break; // loop
                }
            }

        } catch ( IOException ioe ) {
            ioe.printStackTrace();
        } catch ( InterruptedException ie ) {
            ie.printStackTrace();
        }
    }

}
*/