package com.example.demo.exception;

import com.example.demo.exception.model.DomainCode;

public class EmailErrorException extends AppException{
	public EmailErrorException() {
		super(DomainCode.EMAIL_ERROR);
	}
}
