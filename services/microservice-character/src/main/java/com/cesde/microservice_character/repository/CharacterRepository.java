package com.cesde.microservice_character.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cesde.microservice_character.entity.CharacterRickAndMorty;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterRickAndMorty, Integer> {

	public Page<CharacterRickAndMorty> findAll(Pageable pageable);
	
	public Optional<CharacterRickAndMorty> findById (Integer id);
	
}
