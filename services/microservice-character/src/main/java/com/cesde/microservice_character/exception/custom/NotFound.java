package com.cesde.microservice_character.exception.custom;

@SuppressWarnings("serial")
public class NotFound extends RuntimeException {

	public NotFound (String message) {
		super(message);
	}
}
