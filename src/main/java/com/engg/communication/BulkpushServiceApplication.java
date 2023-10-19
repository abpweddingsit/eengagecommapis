package com.engg.communication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class BulkpushServiceApplication {
	
	

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/eEngageCommunication");
		SpringApplication.run(BulkpushServiceApplication.class, args);
	}

}
