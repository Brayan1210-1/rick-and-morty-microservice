package com.cesde.microservice_location.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = NotFound.class)
	public ResponseEntity<ErrorResponse> notFound(NotFound notFound){
		
		ErrorResponse error = new ErrorResponse(
				HttpStatus.NOT_FOUND.value(),
				notFound.getMessage(),
				LocalDateTime.now()
				);
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
}
