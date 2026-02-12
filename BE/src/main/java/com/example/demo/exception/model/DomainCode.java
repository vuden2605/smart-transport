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
	EMAIL_ERROR("Email service error", 1010, HttpStatus.SERVICE_UNAVAILABLE),
	TOKEN_EXPIRED("Token has expired", 1006, HttpStatus.UNAUTHORIZED),
	INVALID_OTP("Invalid OTP", 1007, HttpStatus.BAD_REQUEST),
	OTP_EXPIRED("OTP has expired", 1008, HttpStatus.BAD_REQUEST),
	USER_ALREADY_VERIFIED("User already verified", 1009, HttpStatus.BAD_REQUEST),
	USER_NOT_VERIFIED("User not verified", 1011, HttpStatus.FORBIDDEN),
	INVALID_INPUT("Invalid input", 1012, HttpStatus.BAD_REQUEST);
	private final String message;
	private final int code;
	private final HttpStatusCode httpStatus;
}
