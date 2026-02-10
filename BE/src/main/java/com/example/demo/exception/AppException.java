package com.example.demo.exception;

import com.example.demo.exception.model.DomainCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException{
	private DomainCode domainCode;
	public AppException(DomainCode domainCode) {
		super(domainCode.getMessage());
		this.domainCode = domainCode;
	}
}
