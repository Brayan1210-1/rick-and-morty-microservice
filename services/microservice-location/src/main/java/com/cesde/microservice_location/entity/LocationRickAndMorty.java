package com.cesde.microservice_location.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationRickAndMorty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String type;
	
	@Column(nullable = false)
	private String dimension;
	
	
	@Column(name = "character_id")
	@ElementCollection
	@CollectionTable(name = "location_residents",
	joinColumns = @JoinColumn(name="location_id"))
	private List<Integer> residents;
	
	@Column
	private String url;
	
	@Column
	private LocalDateTime createdAt;
	
	@PrePersist
	public void setTime() {
		this.createdAt = LocalDateTime.now();
	}
}
