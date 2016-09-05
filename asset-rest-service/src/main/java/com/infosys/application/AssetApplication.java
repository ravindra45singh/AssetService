package com.infosys.application;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan( basePackages = { "com.infosys.service.rest.asset" } )
@PropertySource( "classpath:asset_rest_service.properties" )
public class AssetApplication {
    public static void main( String[] args ) {
        SpringApplication app = new SpringApplication( AssetApplication.class );
        app.setBannerMode( Banner.Mode.CONSOLE );
        app.run( args );
    }
}