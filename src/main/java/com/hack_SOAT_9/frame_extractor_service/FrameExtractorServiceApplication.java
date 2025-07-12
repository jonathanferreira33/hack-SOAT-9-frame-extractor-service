package com.hack_SOAT_9.frame_extractor_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FrameExtractorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrameExtractorServiceApplication.class, args);
	}

}
