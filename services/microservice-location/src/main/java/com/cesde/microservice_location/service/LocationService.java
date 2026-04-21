package com.cesde.microservice_location.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cesde.microservice_location.DTO.InfoPageDTO;
import com.cesde.microservice_location.DTO.LocationRequestDTO;
import com.cesde.microservice_location.DTO.LocationResponseDTO;
import com.cesde.microservice_location.DTO.PageResponseDTO;
import com.cesde.microservice_location.entity.LocationRickAndMorty;
import com.cesde.microservice_location.mapper.LocationMapper;
import com.cesde.microservice_location.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationRepository locationRepo;
	private final LocationMapper locationMapper;
	
	public PageResponseDTO<LocationResponseDTO> findAll (int page, int size){
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<LocationRickAndMorty> locationsPage = locationRepo.findAll(pageable);
		
		List<LocationResponseDTO> results = locationsPage.getContent().stream()
	            .map(entity -> {
	                LocationResponseDTO dto = locationMapper.toResponse(entity);
	                
	                dto.setResidents(this.buildResidentsUrls(entity.getResidents()));
	                
	                return dto;
	            })
	            .toList();
		
		InfoPageDTO info = new InfoPageDTO();
	    info.setCount(locationsPage.getTotalElements());
	    info.setPages(locationsPage.getTotalPages());
	    
	    String baseUrl = "http://localhost:8082/api/locations?page=";
	    info.setNext(locationsPage.hasNext() ? baseUrl + (page + 1) : null);
	    info.setPrev(locationsPage.hasPrevious() ? baseUrl + (page - 1) : null);
	    
	    PageResponseDTO<LocationResponseDTO> response = new PageResponseDTO<>();
	    response.setInfo(info);
	    response.setResults(results);
		
		return response;
	}
	
	
	public LocationResponseDTO createLocation(LocationRequestDTO locationDTO) {
		
		LocationRickAndMorty entity = locationMapper.toEntity(locationDTO);
		locationRepo.save(entity);
		
		entity.setUrl("http://localhost:8082/api/locations/" + entity.getId());
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
                .map(id -> "http://localhost:9000/api/characters/" + id)
                .toList();
    }
}
