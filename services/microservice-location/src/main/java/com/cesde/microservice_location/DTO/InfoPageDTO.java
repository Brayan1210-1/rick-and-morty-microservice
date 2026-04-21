package com.cesde.microservice_location.DTO;

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