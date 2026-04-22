package com.cesde.microservice_character.client;

import com.cesde.microservice_character.DTO.Response.LocationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-location")
public interface LocationClient {

	@GetMapping("/api/location/info/{id}")
	LocationDTO getLocation(@PathVariable("id") Integer id);
	
}
