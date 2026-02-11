package com.example.demo.config.security;

import com.example.demo.dto.response.ApiResponse;
import com.example.demo.exception.model.DomainCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.flywaydb.core.api.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, IOException, IOException {
		DomainCode domainCode = DomainCode.UNAUTHORIZED;
		response.setStatus(domainCode.getHttpStatus().value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ApiResponse<?> responseBody = ApiResponse.<String>builder()
				.code(domainCode.getCode())
				.message(domainCode.getMessage())
				.build();
		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(objectMapper.writeValueAsString(responseBody));
		response.flushBuffer();
	}
}
