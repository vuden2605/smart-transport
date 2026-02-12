package com.example.demo.exception;

import com.example.demo.exception.model.DomainCode;

public class InvalidOtpException extends AppException{

	public InvalidOtpException() {
		super(DomainCode.INVALID_OTP);
	}
}
