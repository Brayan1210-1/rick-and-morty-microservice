package com.cesde.microservice_character.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cesde.microservice_character.repository.CharacterRepository;
import com.cesde.microservice_character.DTO.Response.CharacterResponseDTO;
import com.cesde.microservice_character.DTO.Response.InfoPageDTO;
import com.cesde.microservice_character.DTO.Response.LocationBulkResponseDTO;
import com.cesde.microservice_character.DTO.Response.LocationDTO;
import com.cesde.microservice_character.DTO.Response.PageResponseDTO;
import com.cesde.microservice_character.DTO.request.CharacterRequestDTO;
import com.cesde.microservice_character.client.LocationClient;
import com.cesde.microservice_character.entity.CharacterRickAndMorty;
import com.cesde.microservice_character.exception.custom.NotFound;
import com.cesde.microservice_character.mapper.CharacterMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CharacterService {

	private final CharacterRepository characterRepo;
	private final CharacterMapper characterMapper;
	private final LocationClient locationClient;
	
	public PageResponseDTO<CharacterResponseDTO> findAll (int page, int size){
		
	    Pageable pageable = PageRequest.of(page, size);
	    
	    Page<CharacterRickAndMorty> charPage = characterRepo.findAll(pageable);
	    List<CharacterRickAndMorty> entities = charPage.getContent();

	    Set<Integer> ids = collectLocationIds(entities);
	    Map<Integer, LocationDTO> locationMap = fetchLocationsMap(ids);

	  
	    List<CharacterResponseDTO> results = entities.stream()
	            .map(entity -> {
	                CharacterResponseDTO dto = characterMapper.toResponse(entity);
	                // Buscamos en el mapa, si no está, usamos tu buildLocation(id) de siempre
	                dto.setOrigin(locationMap.getOrDefault(entity.getOrigin(), this.buildLocation(entity.getOrigin())));
	                dto.setLocation(locationMap.getOrDefault(entity.getLocation(), this.buildLocation(entity.getLocation())));
	                
	                // Los episodios siguen igual por ahora (placeholders)
	                dto.setEpisode(this.buildEpisodes(entity.getEpisodesIds()));
	                return dto;
	            }).toList();

	    PageResponseDTO<CharacterResponseDTO> response = new PageResponseDTO<>();
	    response.setInfo(buildInfo(charPage, page));
	    response.setResults(results);

	    return response;
	    
	}

	public CharacterResponseDTO createCharacter(CharacterRequestDTO request) {
      
        CharacterRickAndMorty entity = characterMapper.toEntity(request);
       
        entity = characterRepo.save(entity);
        
        entity.setUrl("http://localhost:8081/api/character/" + entity.getId());
        
        entity = characterRepo.save(entity);
        
        CharacterResponseDTO response = characterMapper.toResponse(entity);
        
        response.setOrigin(this.buildLocation(entity.getOrigin()));
        response.setLocation(this.buildLocation(entity.getLocation()));
        response.setEpisode(this.buildEpisodes(entity.getEpisodesIds()));
        return response;
    }
	
	public CharacterResponseDTO findById(Integer id) {
		
		CharacterRickAndMorty entity = characterRepo.findById(id).
				orElseThrow(() -> new NotFound("No existe personaje con ese id"));
		
		CharacterResponseDTO response = characterMapper.toResponse(entity);
		
		response.setOrigin(this.buildLocation(entity.getOrigin()));
		response.setLocation(this.buildLocation(entity.getLocation()));
		response.setEpisode(this.buildEpisodes(entity.getEpisodesIds()));
		
		return response;
	}
	
	private LocationDTO buildLocation(Integer locationId) {
        if (locationId == null) return null;
        
       try {
    	   
    	   return locationClient.getLocation(locationId);
       } catch (NotFound e){
    	   LocationDTO placeholder = new LocationDTO();
           placeholder.setName("No se pudo obtener la localizacion (ID: " + locationId + ")");
           return placeholder;
       }
    }
	
	private List<String> buildEpisodes(List<Integer> episodeIds) {
        if (episodeIds == null || episodeIds.isEmpty()) return List.of();
        
        return episodeIds.stream()
                .map(id -> "http://localhost:9000/api/v1/episodes/" + id)
                .toList();
    }
	
	private Set<Integer> collectLocationIds(List<CharacterRickAndMorty> characters) {
	    Set<Integer> ids = new HashSet<>();
	    for (CharacterRickAndMorty c : characters) {
	        if (c.getOrigin() != null) ids.add(c.getOrigin());
	        if (c.getLocation() != null) ids.add(c.getLocation());
	    }
	    return ids;
	}
	
	private InfoPageDTO buildInfo(Page<CharacterRickAndMorty> page, int currentPage) {
	    InfoPageDTO info = new InfoPageDTO();
	    info.setCount(page.getTotalElements());
	    info.setPages(page.getTotalPages());
	    
	    String baseUrl = "http://localhost:8081/api/character?page=";
	    info.setNext(page.hasNext() ? baseUrl + (currentPage + 1) : null);
	    info.setPrev(page.hasPrevious() ? baseUrl + (currentPage - 1) : null);
	    return info;
	}
	
	//metodo auxiliar para el findAll y no matar el ms-location
	private Map<Integer, LocationDTO> fetchLocationsMap(Collection<Integer> ids) {
	    if (ids == null || ids.isEmpty()) return Collections.emptyMap();

	    try {
	    	
	        List<LocationBulkResponseDTO> bulkResponse = locationClient.getLocationsBulk(new ArrayList<>(ids));

	        return bulkResponse.stream().collect(Collectors.toMap(
	            LocationBulkResponseDTO::getId,
	            res -> {
	                LocationDTO dto = new LocationDTO();
	                dto.setName(res.getName());
	                dto.setUrl(res.getUrl());
	                return dto;
	            }
	        ));
	    } catch (Exception e) {
	      
	        return Collections.emptyMap();
	    }
	}
	
	

}
