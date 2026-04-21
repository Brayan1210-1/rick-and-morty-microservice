package com.cesde.microservice_location.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cesde.microservice_location.DTO.tocharacter.LocationNameUrl;
import com.cesde.microservice_location.entity.LocationRickAndMorty;

@Repository
public interface LocationRepository extends JpaRepository<LocationRickAndMorty, Integer>{

	Page<LocationRickAndMorty> findAll(Pageable pageable);
	Optional<LocationNameUrl> findNameAndUrlById(Integer id);
}
