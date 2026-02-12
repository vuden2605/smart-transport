package com.example.demo.exception;

import com.example.demo.exception.model.DomainCode;

public class UserNotVerifiedException extends AppException{
	public UserNotVerifiedException() {
		super(DomainCode.USER_NOT_VERIFIED);
	}
}
