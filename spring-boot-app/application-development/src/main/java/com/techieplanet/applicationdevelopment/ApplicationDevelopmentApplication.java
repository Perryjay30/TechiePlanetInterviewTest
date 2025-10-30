package com.techieplanet.applicationdevelopment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition(info = @Info(title = "Scores API", version = "v1"))
@SpringBootApplication
public class ApplicationDevelopmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationDevelopmentApplication.class, args);
	}

}
