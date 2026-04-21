package com.cesde.microservice_location.DTO;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LocationRequestDTO {

	@NotBlank(message = "nombre obligatorio")
	private String name;
	@NotBlank(message = "tipo obligatorio")
	private String type;
	@NotBlank(message = "dimension obligatoria")
	private String dimension;
	
	private List<@Min(value = 1,
		message = "El id no puede ser negativo o 0" )
	Integer> residentsIds;
}
