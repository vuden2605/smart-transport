package com.example.demo.controller;

import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.request.LogoutRequest;
import com.example.demo.dto.request.RefreshTokenRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.service.authentication.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final IAuthenticationService authenticationService;
	@PostMapping
	public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ApiResponse.<AuthenticationResponse>builder()
				.message("Authentication successful")
				.data(authenticationService.authenticate(request))
				.build();
	}
	@PostMapping("/refresh-token")
	public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
		return ApiResponse.<AuthenticationResponse>builder()
				.message("Token refreshed successfully")
				.data(authenticationService.refreshToken(request))
				.build();
	}
	@PostMapping("/logout")
	public ApiResponse<Void> logout(@RequestBody LogoutRequest request) {
		authenticationService.logout(request);
		return ApiResponse.<Void>builder()
				.message("Logout successful")
				.build();
	}
}
