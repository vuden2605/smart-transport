package com.example.demo.exception;

import com.example.demo.exception.model.DomainCode;

public class OtpExpiredException extends AppException{
	public OtpExpiredException() {
		super(DomainCode.OTP_EXPIRED);
	}
}
