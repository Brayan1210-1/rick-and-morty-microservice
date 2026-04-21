package com.cesde.microservice_location.mapper;
import com.cesde.microservice_location.entity.*;
import com.cesde.microservice_location.DTO.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "url", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "residents", source = "residentsIds")
	LocationRickAndMorty toEntity (LocationRequestDTO locationDTO);

	@Mapping(target = "residents", ignore = true)
	@Mapping(target = "created", source = "createdAt")
	LocationResponseDTO toResponse(LocationRickAndMorty locationE) ;
}
