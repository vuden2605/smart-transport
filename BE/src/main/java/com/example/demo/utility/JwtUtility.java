package com.example.demo.utility;

import com.example.demo.entity.User;
import com.example.demo.service.authentication.TokenCacheService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUtility {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.access-time}")
	private long accessTime;

	@Value("${jwt.refresh-time}")
	private long refreshTime;
	private final TokenCacheService tokenCacheService;

	public SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	public String generateAccessToken(User user) {
		String jti = UUID.randomUUID().toString();
		String rfId = UUID.randomUUID().toString();
		return Jwts.builder()
				.setId(jti)
				.setSubject(user.getId().toString())
				.claim("name", user.getName())
				.claim("email", user.getEmail())
				.claim("rfId", rfId)
				.claim("type", "access_token")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + accessTime))
				.signWith(getSecretKey())
				.compact();
	}
	public String generateRefreshToken(User user) {
		String jti = UUID.randomUUID().toString();
		String acId = UUID.randomUUID().toString();
		return Jwts.builder()
				.setId(jti)
				.setSubject(user.getId().toString())
				.claim("name", user.getName())
				.claim("email", user.getEmail())
				.claim("acId", acId)
				.claim("type", "refresh_token")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + refreshTime))
				.signWith(getSecretKey())
				.compact();
	}
	public Claims verifyToken(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(getSecretKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		String tokenId = claims.getId();
		if (tokenCacheService.isTokenInvalidated(tokenId)) {
			log.info("Token with ID {} has been invalidated", tokenId);
			throw new JwtException("Token has been invalidated");
		}
		return claims;
	}

	public LocalDateTime authCacheTime(Claims claims) {
		LocalDateTime expiration = claims.getExpiration().toInstant()
				.atZone(java.time.ZoneId.systemDefault())
				.toLocalDateTime();
		return expiration.plusSeconds(refreshTime - accessTime);
	}
}
