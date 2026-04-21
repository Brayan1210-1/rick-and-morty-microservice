package com.cesde.microservice_character.DTO.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterResponseDTO {

	
	private Integer id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private LocationDTO origin;
    private LocationDTO location;
    private String image;
    private List<String> episode; 
    private String url;
    private String created;
}
