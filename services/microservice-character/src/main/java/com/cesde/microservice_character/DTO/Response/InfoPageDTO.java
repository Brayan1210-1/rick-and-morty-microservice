package com.cesde.microservice_character.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoPageDTO {

	 private long count;
	    private int pages;
	    private String next;
	    private String prev;
}
