package com.cesde.microservice_location.exception;

@SuppressWarnings("serial")
public class NotFound extends RuntimeException{
	
	public NotFound(String message) {
		super(message);
	}

}
