package com.example.demo.service.authentication;

import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.request.LogoutRequest;
import com.example.demo.dto.request.RefreshTokenRequest;
import com.example.demo.dto.response.AuthenticationResponse;

public interface IAuthenticationService {
	AuthenticationResponse authenticate(AuthenticationRequest request);
	AuthenticationResponse refreshToken(RefreshTokenRequest request);
	void logout(LogoutRequest request);
}
