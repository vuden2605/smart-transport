package com.example.demo.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
@AllArgsConstructor
public enum DomainCode {
	SUCCESS("Success", 9999, HttpStatus.OK),
	INTERNAL_SERVER_ERROR("Internal server error", 1000, HttpStatus.INTERNAL_SERVER_ERROR),
	USER_NOT_FOUND("User not found", 1001, HttpStatus.NOT_FOUND),
	INVALID_CREDENTIALS("Invalid credentials", 1002, HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED("Unauthorized access", 1003, HttpStatus.FORBIDDEN),
	EMAIL_ALREADY_REGISTERED("Email already registered", 1004, HttpStatus.CONFLICT),
	EMPTY_INPUT("Input cannot be empty", 1005, HttpStatus.BAD_REQUEST),
	TOKEN_EXPIRED("Token has expired", 1006, HttpStatus.UNAUTHORIZED),
	PROVIDER_NOT_FOUND("Geocoding provider not found", 2001, HttpStatus.BAD_REQUEST),
	ADDRESS_NOT_FOUND("Address not found", 2002, HttpStatus.NOT_FOUND);
	private final String message;
	private final int code;
	private final HttpStatusCode httpStatus;
}
