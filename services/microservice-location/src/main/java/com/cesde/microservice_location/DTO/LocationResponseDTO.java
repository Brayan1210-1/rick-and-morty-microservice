package com.cesde.microservice_location.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponseDTO {

	private Integer id;
	private String name;
	private String type;
	private String dimension;
	private List<String> residents;
	private String url;
	private String created;
	
}
