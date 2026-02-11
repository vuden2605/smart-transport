package com.example.demo.exception;


import com.example.demo.exception.model.DomainCode;

public class TokenExpiredException extends AppException {

	public TokenExpiredException() {
		super(DomainCode.TOKEN_EXPIRED);
	}
}
