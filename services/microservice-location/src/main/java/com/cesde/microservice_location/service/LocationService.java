package com.cesde.microservice_location.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cesde.microservice_location.DTO.LocationRequestDTO;
import com.cesde.microservice_location.DTO.LocationResponseDTO;
import com.cesde.microservice_location.entity.LocationRickAndMorty;
import com.cesde.microservice_location.mapper.LocationMapper;
import com.cesde.microservice_location.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationRepository locationRepo;
	private final LocationMapper locationMapper;
	
	
	public LocationResponseDTO createLocation(LocationRequestDTO locationDTO) {
		
		LocationRickAndMorty entity = locationMapper.toEntity(locationDTO);
		locationRepo.save(entity);
		
		entity.setUrl("http://localhost:8082/api/v1/locations/" + entity.getId());
		entity = locationRepo.save(entity);
		
		LocationResponseDTO response = locationMapper.toResponse(entity);
		response.setResidents(this.buildResidentsUrls(entity.getResidents()));
		
		return response;
	}
	
	private List<String> buildResidentsUrls(List<Integer> residentIds) {
        if (residentIds == null || residentIds.isEmpty()) {
            return Collections.emptyList();
        }
        return residentIds.stream()
                .map(id -> "http://localhost:9000/api/v1/characters/" + id)
                .toList();
    }
}
