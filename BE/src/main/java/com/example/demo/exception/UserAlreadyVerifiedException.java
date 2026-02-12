package com.example.demo.exception;

import com.example.demo.exception.model.DomainCode;

public class UserAlreadyVerifiedException extends AppException {
	public UserAlreadyVerifiedException() {
		super(DomainCode.USER_ALREADY_VERIFIED);
	}
}
