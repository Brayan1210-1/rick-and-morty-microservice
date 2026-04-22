package com.cesde.microservice_character.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cesde.microservice_character.repository.CharacterRepository;
import com.cesde.microservice_character.DTO.Response.CharacterResponseDTO;
import com.cesde.microservice_character.DTO.Response.InfoPageDTO;
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
	    
	    Page<CharacterRickAndMorty> charactersPage = characterRepo.findAll(pageable);
	    
	    List<CharacterResponseDTO> results = charactersPage.getContent()
	            .stream()
	            .map(entity -> {
	                CharacterResponseDTO dto = characterMapper.toResponse(entity);
	                
	                dto.setOrigin(this.buildLocation(entity.getOrigin()));
	                dto.setLocation(this.buildLocation(entity.getLocation()));
	                dto.setEpisode(this.buildEpisodes(entity.getEpisodesIds()));
	                
	                return dto;
	            })
	            .toList();
	    
	    InfoPageDTO info = new InfoPageDTO();
	    info.setCount(charactersPage.getTotalElements());
	    info.setPages(charactersPage.getTotalPages());
	    
	    String baseUrl = "http://localhost:8081/api/character?page=";
	    info.setNext(charactersPage.hasNext() ? baseUrl + (page + 1) : null);
	    info.setPrev(charactersPage.hasPrevious() ? baseUrl + (page - 1) : null);

	    PageResponseDTO<CharacterResponseDTO> response = new PageResponseDTO<>();
	    response.setInfo(info);
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
	
	

}
