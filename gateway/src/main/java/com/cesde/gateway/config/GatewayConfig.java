package com.cesde.gateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("characters-route", r -> r.path("/api/character/**")
                        .uri("lb://microservice-character"))
                .route("locations-route", r -> r.path("/api/location/**")
                        .uri("lb://microservice-location"))
                .build();
    }
}
