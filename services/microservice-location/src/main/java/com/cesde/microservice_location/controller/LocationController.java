package com.cesde.microservice_location.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cesde.microservice_location.DTO.LocationRequestDTO;
import com.cesde.microservice_location.DTO.LocationResponseDTO;
import com.cesde.microservice_location.service.LocationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/location")
public class LocationController {

	 private final LocationService locationService;
	 
	 @PostMapping("/create")
	 public ResponseEntity<LocationResponseDTO> createLocation (@RequestBody @Valid LocationRequestDTO locationDTO){
		 
		 return ResponseEntity.ok(locationService.createLocation(locationDTO));
	 }
	 
	 
}
