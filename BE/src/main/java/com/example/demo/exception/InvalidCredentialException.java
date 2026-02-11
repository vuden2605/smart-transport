package com.example.demo.exception;

import com.example.demo.exception.model.DomainCode;

public class InvalidCredentialException extends AppException{
	public InvalidCredentialException() {
		super(DomainCode.INVALID_CREDENTIALS);
	}
}
