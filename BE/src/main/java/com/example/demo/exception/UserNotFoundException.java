package com.example.demo.exception;

import com.example.demo.exception.model.DomainCode;

public class UserNotFoundException extends AppException{
	public UserNotFoundException() {
		super(DomainCode.USER_NOT_FOUND);
	}
}
