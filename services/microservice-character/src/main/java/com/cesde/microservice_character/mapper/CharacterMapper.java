package com.cesde.microservice_character.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cesde.microservice_character.DTO.Response.CharacterResponseDTO;
import com.cesde.microservice_character.DTO.request.CharacterRequestDTO;
import com.cesde.microservice_character.entity.CharacterRickAndMorty;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "url", ignore = true)
    @Mapping(target = "origin", source = "originId")
    @Mapping(target = "location", source = "locationId")
    CharacterRickAndMorty toEntity(CharacterRequestDTO request);

   
    @Mapping(target = "origin", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "episode", ignore = true)
    @Mapping(target = "created", source = "createdAt")
    CharacterResponseDTO toResponse(CharacterRickAndMorty entity);
}