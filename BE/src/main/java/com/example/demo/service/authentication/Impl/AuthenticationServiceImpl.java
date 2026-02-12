package com.example.demo.service.authentication.Impl;

import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.request.LogoutRequest;
import com.example.demo.dto.request.RefreshTokenRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidCredentialException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserNotVerifiedException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.authentication.IAuthenticationService;
import com.example.demo.utility.JwtUtility;
import com.example.demo.service.authentication.TokenCacheService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

	UserRepository userRepository;

	JwtUtility jwtUtility;

	TokenCacheService tokenCacheService;

	PasswordEncoder passwordEncoder;

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(UserNotFoundException::new);
		if (!user.getIsVerified()) {
			throw new UserNotVerifiedException();
		}

		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new InvalidCredentialException();
		}

		String accessToken = jwtUtility.generateAccessToken(user);
		String refreshToken = jwtUtility.generateRefreshToken(user);
		return AuthenticationResponse.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

	@Override
	public AuthenticationResponse refreshToken(RefreshTokenRequest request) {

		String refreshToken = request.getRefreshToken();

		Claims claims = jwtUtility.verifyToken(refreshToken);
		if(!"refresh_token".equals(claims.get("type", String.class))) {
			throw new JwtException("Invalid token type");
		}
		String acId = claims.get("acId", String.class);
		String refreshTokenId = claims.getId();

		Long userId = Long.parseLong(claims.getSubject());
		User user = userRepository.findById(userId)
				.orElseThrow(UserNotFoundException::new);

		String newAccessToken = jwtUtility.generateAccessToken(user);
		String newRefreshToken = jwtUtility.generateRefreshToken(user);

		tokenCacheService.invalidateTokens(
				acId,
				refreshTokenId,
				jwtUtility.authCacheTime(claims));
		return AuthenticationResponse.builder()
				.accessToken(newAccessToken)
				.refreshToken(newRefreshToken)
				.build();
	}

	public void logout(LogoutRequest request) {
		String accessToken = request.getAccessToken();
		Claims accessClaims = jwtUtility.verifyToken(accessToken);
		String acId = accessClaims.getId();
		String rfId = accessClaims.get("rfId", String.class);

		tokenCacheService.invalidateTokens(
				acId,
				rfId,
				jwtUtility.authCacheTime(accessClaims));
	}

}
