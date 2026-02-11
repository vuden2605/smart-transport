package com.example.demo.service.authentication.impl;

import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.request.LogoutRequest;
import com.example.demo.dto.request.RefreshTokenRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidCredentialException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.authentication.IAuthenticationService;
import com.example.demo.service.authentication.JwtService;
import com.example.demo.service.authentication.TokenCacheService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

	UserRepository userRepository;

	JwtService jwtService;

	TokenCacheService tokenCacheService;

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(UserNotFoundException::new);
		if (!user.getPassword().equals(request.getPassword())) {
			throw new InvalidCredentialException();
		}
		String accessToken = jwtService.generateAccessToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		return AuthenticationResponse.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

	@Override
	public AuthenticationResponse refreshToken(RefreshTokenRequest request) {

		String refreshToken = request.getRefreshToken();

		Claims claims = jwtService.verifyToken(refreshToken);
		if(!"refresh_token".equals(claims.get("type", String.class))) {
			throw new JwtException("Invalid token type");
		}
		String acId = claims.get("acId", String.class);
		String refreshTokenId = claims.getId();

		Long userId = Long.parseLong(claims.getSubject());
		User user = userRepository.findById(userId)
				.orElseThrow(UserNotFoundException::new);

		String newAccessToken = jwtService.generateAccessToken(user);
		String newRefreshToken = jwtService.generateRefreshToken(user);

		tokenCacheService.invalidateTokens(
				acId,
				refreshTokenId,
				jwtService.authCacheTime(claims));
		return AuthenticationResponse.builder()
				.accessToken(newAccessToken)
				.refreshToken(newRefreshToken)
				.build();
	}

	public void logout(LogoutRequest request) {
		String accessToken = request.getAccessToken();
		Claims accessClaims = jwtService.verifyToken(accessToken);
		String acId = accessClaims.getId();
		String rfId = accessClaims.get("rfId", String.class);

		tokenCacheService.invalidateTokens(
				acId,
				rfId,
				jwtService.authCacheTime(accessClaims));
	}

}
