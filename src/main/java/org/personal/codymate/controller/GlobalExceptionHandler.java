package org.personal.codymate.controller;

import org.personal.codymate.domain.InvalidRequestException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalExceptionHandler {

	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<ErrorResponse> handleException(InvalidRequestException ex, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		ErrorResponse errorResponse = new ErrorResponse(
			status.value(),
			status.getReasonPhrase(),
			ex.getMessage(),
			request.getRequestURI()
		);

		return ResponseEntity.status(status).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		ErrorResponse errorResponse = new ErrorResponse(
			status.value(),
			status.getReasonPhrase(),
			ex.getMessage(),
			request.getRequestURI()
		);

		return ResponseEntity.status(status).body(errorResponse);
	}


	record ErrorResponse(int status, String error, String message, String path) {}

}
