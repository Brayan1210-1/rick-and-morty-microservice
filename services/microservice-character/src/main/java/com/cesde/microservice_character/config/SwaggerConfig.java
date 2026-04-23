package com.cesde.microservice_character.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	@Bean
	OpenAPI usuarioApiOpenAPI() {
	    return new OpenAPI()
	      .info(new Info()
	        .title("Microservico de personajes ")
	        .description("Por favor que esto sirva")
	        .version("0.0.1")
	      )
	      .servers(List.of(
	        new Server()
	          .url("http://localhost:8081")
	          .description("Servidor de characters")
	        )
	      );
	}

}
