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
import com.cesde.microservice_location.DTO.tocharacter.LocationBulkProjection;
import com.cesde.microservice_location.DTO.tocharacter.LocationNameUrl;
import com.cesde.microservice_location.DTO.tocharacter.LocationNameUrlDTO;
import com.cesde.microservice_location.entity.LocationRickAndMorty;
import com.cesde.microservice_location.exception.NotFound;
import com.cesde.microservice_location.mapper.LocationMapper;
import com.cesde.microservice_location.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationRepository locationRepo;
	private final LocationMapper locationMapper;
	
	public LocationNameUrlDTO getUrlAndName(Integer id) {
		LocationNameUrl locationInfo = locationRepo.findNameAndUrlById(id)
				.orElseThrow(() -> new NotFound("Localizacion no encontrada"));
		
		return new LocationNameUrlDTO(locationInfo.getName(), locationInfo.getUrl());
	}
	
	public LocationResponseDTO findById(Integer id) {
		
		LocationRickAndMorty entity = locationRepo.findById(id)
				.orElseThrow(() -> new NotFound("Localizacion no encontrada con el id: " + id));
		
		LocationResponseDTO response = locationMapper.toResponse(entity);
		response.setResidents(this.buildResidentsUrls(entity.getResidents()));
		
		return response;
	}
	
	public List<LocationBulkProjection> findAllByIds(List<Integer> ids){
		if (ids == null || ids.isEmpty()) {
	        return Collections.emptyList();
	    }
		
		return locationRepo.findAllByIdIn(ids);
	}
	
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
	    
	    String baseUrl = "http://localhost:8080/api/locations?page=";
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
		
		entity.setUrl("http://localhost:8080/api/location/" + entity.getId());
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
                .map(id -> "http://localhost:8080/api/characters/" + id)
                .toList();
    }
}
