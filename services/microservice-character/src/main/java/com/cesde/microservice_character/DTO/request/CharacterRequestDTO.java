package com.cesde.microservice_character.DTO.request;

import java.util.List;

import com.cesde.microservice_character.enums.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterRequestDTO {
	@NotBlank
    private String name;
    private Status status;
    private String species;
    private String type;
    private Gender gender; 
    @Min(value = 1, message = "Los id no pueden ser negativos o cero")
    private Integer originId;
    @Min(value = 1, message = "Los id no pueden ser negativos o cero")
    private Integer locationId;
    private String image;
    
    private List<@Min( value = 1, message = "Los id no pueden ser negativos o cero")
    Integer> episodesIds;
}
