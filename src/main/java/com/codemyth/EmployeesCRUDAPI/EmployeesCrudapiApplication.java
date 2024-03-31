package com.codemyth.EmployeesCRUDAPI;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(
  info = @Info(
	title = "Employees CRUD REST OPEN API",
	version = "1.0.0",
	description = "Employees CRUD REST OPEN API Documentation"
  ),
  servers = @Server(
    url = "http://localhost:8080"		  
  ) 
)
public class EmployeesCrudapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeesCrudapiApplication.class, args);
	}

}
