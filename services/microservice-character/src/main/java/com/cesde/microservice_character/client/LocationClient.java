package com.cesde.microservice_character.client;

import com.cesde.microservice_character.DTO.Response.LocationBulkResponseDTO;
import com.cesde.microservice_character.DTO.Response.LocationDTO;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservice-location")
public interface LocationClient {

	@GetMapping("/api/location/info/{id}")
	LocationDTO getLocation(@PathVariable("id") Integer id);
	
	@GetMapping("/api/location/bulk")
    List<LocationBulkResponseDTO> getLocationsBulk(@RequestParam("ids") List<Integer> ids);
}
