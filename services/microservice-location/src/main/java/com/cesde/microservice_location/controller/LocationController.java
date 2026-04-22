package com.cesde.microservice_location.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cesde.microservice_location.DTO.LocationRequestDTO;
import com.cesde.microservice_location.DTO.LocationResponseDTO;
import com.cesde.microservice_location.DTO.PageResponseDTO;
import com.cesde.microservice_location.DTO.tocharacter.LocationBulkProjection;
import com.cesde.microservice_location.DTO.tocharacter.LocationNameUrlDTO;
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
	 
	 @GetMapping("/{id}")
	 public ResponseEntity<LocationResponseDTO> findById(@PathVariable Integer id){
		 return ResponseEntity.ok(locationService.findById(id));
	 }
	 
	 @GetMapping
	 public ResponseEntity<PageResponseDTO<LocationResponseDTO>> getAllLocations(
	         @RequestParam(defaultValue = "0") int page,
	         @RequestParam(defaultValue = "5") int size) {
	     
	     return ResponseEntity.ok(locationService.findAll(page, size));
	 }
	 
	 @GetMapping("/bulk")
	 public List<LocationBulkProjection> getLocationsBulks (@RequestParam List<Integer> ids){
		 return locationService.findAllByIds(ids);
	 }
	 
	 @GetMapping("/info/{id}")
	 public ResponseEntity<LocationNameUrlDTO> getNameAndUrl(@PathVariable Integer id){
		 
		 return ResponseEntity.ok(locationService.getUrlAndName(id));
	 }
	 
}
