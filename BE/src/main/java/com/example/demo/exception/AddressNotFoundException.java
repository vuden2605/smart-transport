package com.example.demo.exception;

import com.example.demo.exception.model.DomainCode;

public class AddressNotFoundException extends AppException{
	public AddressNotFoundException() {
		super(DomainCode.ADDRESS_NOT_FOUND);
	}
}
