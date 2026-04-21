package com.cesde.microservice_character.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.cesde.microservice_character.enums.*;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "characters_rick")
public class CharacterRickAndMorty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(nullable = false, name = "species")
	private String species;
	
	@Column(nullable = false, name = "type")
	private String type;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
    @Column(name = "origin", nullable = false)
	private Integer origin;
	
    @Column(name = "location")
	private Integer location;
	
	@Column(nullable = false, name = "image")
	private String image;
	
	@Column(name = "character_url")
	private String url;
	
	@Column(nullable = true, name = "created_at")
	private LocalDateTime createdAt;
	
    @ElementCollection
    @CollectionTable(name = "character_episodes", joinColumns = @JoinColumn(name = "character_id"))
    @Column(name = "episode_id")
    private List<Integer> episodesIds;
    
    @PrePersist
	  protected void onCreate() {
	    this.createdAt = LocalDateTime.now();
	  }

}
