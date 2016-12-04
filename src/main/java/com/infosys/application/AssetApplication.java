package com.infosys.application;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories( basePackages = { "com.infosys.service.dao" } )
@ComponentScan( basePackages = { "com.infosys.service.rest.asset", "com.infosys.service.config", "com.infosys.service.dao",
        "com.infosys.service.model" } )
@PropertySource( "classpath:asset_rest_service.properties" )
public class AssetApplication {

    @Autowired
    static FileWatcher fileWatcher;

    private static final Logger LOGGER = LoggerFactory.getLogger( AssetApplication.class );

    public static void main( String[] args ) {
        SpringApplication app = new SpringApplication( AssetApplication.class );
        app.setBannerMode( Banner.Mode.CONSOLE );
        app.run( args );
        System.out.println( "Asset Application started" );
        LOGGER.info( "o;***** Asset Application started *****" );
        Path folder = Paths.get( "E:/FileWatcher" );
        System.out.println( "folder : " + folder.getFileName() );
        FileWatcher.watchDirectoryPath( folder );
    }
}