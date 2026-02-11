package com.example.demo.exception.ExceptionHandler;

import com.example.demo.dto.response.ApiResponse;
import com.example.demo.exception.AppException;
import com.example.demo.exception.model.DomainCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleGeneralException(Exception ex) {
		DomainCode domainCode = DomainCode.INTERNAL_SERVER_ERROR;
		ApiResponse<?> response = ApiResponse.builder()
				.code(domainCode.getCode())
				.message(ex.getMessage())
				.build();
		return ResponseEntity.status(domainCode.getHttpStatus()).body(response);
	}
	@ExceptionHandler(AppException.class)
	public ResponseEntity<ApiResponse<?>> handleAppException(AppException ex) {
		DomainCode domainCode = ex.getDomainCode();
		ApiResponse<?> response = ApiResponse.builder()
				.code(domainCode.getCode())
				.message(domainCode.getMessage())
				.build();
		return ResponseEntity.status(domainCode.getHttpStatus()).body(response);
	}
}
