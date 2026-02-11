package com.example.demo.exception;

import com.example.demo.exception.model.DomainCode;

public class EmailAlreadyRegisteredException extends AppException{
	public EmailAlreadyRegisteredException() {
		super(DomainCode.EMAIL_ALREADY_REGISTERED);
	}
}
