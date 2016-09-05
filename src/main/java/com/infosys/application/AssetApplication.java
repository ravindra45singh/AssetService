package com.infosys.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.infosys.service.rest.asset.AssetRestController;

@SpringBootApplication
@ComponentScan(basePackages = { "com.infosys.service.rest.asset" })
@PropertySource("classpath:asset_rest_service.properties")
public class AssetApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(AssetRestController.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AssetApplication.class);
		app.setBannerMode(Banner.Mode.CONSOLE);
		app.run(args);
		System.out.println("Asset Application started");
		LOGGER.info("o;***** Asset Application started *****");
	}
}