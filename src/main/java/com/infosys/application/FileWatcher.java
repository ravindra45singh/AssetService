package com.infosys.application;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.service.model.CountryDetails;

import java.nio.file.WatchKey;
import java.nio.file.WatchService;

@Component
public class FileWatcher {

    @Value( "${fileLocation.toMonitor}" )
    private static String fileLocation;

    @SuppressWarnings( "unchecked" )
    public static void watchDirectoryPath( Path path ) {
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
                System.out.println( "Watching: " + path );
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
                        String str = "E:/FileWatcher/" + newPath;
                        System.out.println( "New path created: " + newPath );
                        System.out.println( "New path created: " + str );
                        
                        countryDetails = objectMapper.readValue( new FileReader( str ), CountryDetails.class );
                        System.out.println( "CountryDetails: " + countryDetails.getCapital() );

                    }
                    /*
                     * else if (ENTRY_MODIFY == kind) { // A Path was modified
                     * Path newPath = ((WatchEvent<Path>) watchEvent).context();
                     * System.out.println("New path modified: " + newPath); }
                     * else if (ENTRY_DELETE == kind) { // A Path was deleted
                     * Path newPath = ((WatchEvent<Path>) watchEvent).context();
                     * System.out.println("New path deleted: " + newPath); }
                     */
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
