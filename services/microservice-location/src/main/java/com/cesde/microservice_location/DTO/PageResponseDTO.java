package com.cesde.microservice_location.DTO;

import java.util.List;

import com.cesde.microservice_location.DTO.InfoPageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO<T> {
	 private InfoPageDTO info;
	    private List<T> results;
}