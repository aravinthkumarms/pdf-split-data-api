package com.aravinth.pdfsplitter.pdfsplitdatadapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PdfSplitDataApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfSplitDataApiApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(){
		return  new RestTemplate();
	}


}
