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
	USER_NOT_FOUND("User not found", 1001, HttpStatus.NOT_FOUND);
	private final String message;
	private final int code;
	private final HttpStatusCode httpStatus;
}
