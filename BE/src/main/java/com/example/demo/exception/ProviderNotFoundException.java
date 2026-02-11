package com.example.demo.exception;

import com.example.demo.exception.model.DomainCode;

public class ProviderNotFoundException extends AppException{
	public ProviderNotFoundException() {
		super(DomainCode.PROVIDER_NOT_FOUND);
	}
}
