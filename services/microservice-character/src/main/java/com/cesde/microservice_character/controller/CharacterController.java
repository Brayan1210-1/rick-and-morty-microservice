package com.cesde.microservice_character.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cesde.microservice_character.DTO.Response.CharacterResponseDTO;
import com.cesde.microservice_character.DTO.Response.PageResponseDTO;
import com.cesde.microservice_character.DTO.request.CharacterRequestDTO;
import com.cesde.microservice_character.service.CharacterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/character")
@RequiredArgsConstructor
public class CharacterController {

	private final CharacterService characterServ;
	
	@GetMapping
	public ResponseEntity<PageResponseDTO<CharacterResponseDTO>> allCharacters(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size) {
	    return ResponseEntity.ok(characterServ.findAll(page, size));
	}
	
	@PostMapping("/create")
	public ResponseEntity<CharacterResponseDTO> createCharacter(@RequestBody CharacterRequestDTO character){
		return ResponseEntity.ok(characterServ.createCharacter(character));
	}
}

