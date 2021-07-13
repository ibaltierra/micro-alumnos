package com.formaciondbi.microservicios.app.alumnos.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class AlumnosControllerExceptionHandler {

	@ExceptionHandler(AlumnoError.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity resourceBadRequestException(AlumnoError ex, WebRequest request) {
		ErrorResponse message = new ErrorResponse();
		message.setMessage(ex.getMessage());
	    message.setStatus(HttpStatus.BAD_REQUEST.toString());
	    return ResponseEntity.badRequest().body(message);
	}
}
