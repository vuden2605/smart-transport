package com.example.demo.config.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SecurityConfig {
	CustomJwtDecoder customJwtDecoder;
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	FrontendUrlProperties frontendUrlProperties;
	SecurityProperties securityProperties;



	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
		String [] getPatterns = securityProperties.getMethods().getGet() != null
				? securityProperties.getMethods().getGet().toArray(new String[0]) : new String [0];

		String [] postPatterns = securityProperties.getMethods().getPost() != null
				? securityProperties.getMethods().getPost().toArray(new String[0]) : new String [0];
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()));
		httpSecurity.authorizeHttpRequests(request -> request
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.requestMatchers(HttpMethod.GET, getPatterns).permitAll()
				.requestMatchers(HttpMethod.POST, postPatterns).permitAll()
				.anyRequest().authenticated()
		);
		httpSecurity.oauth2ResourceServer(oauth2 -> oauth2
				.jwt(jwt -> jwt
						.decoder(customJwtDecoder)
						.jwtAuthenticationConverter(jwtAuthenticationConverter())
				)
				.authenticationEntryPoint(jwtAuthenticationEntryPoint));
		return httpSecurity.build();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(frontendUrlProperties.getUrls());
		configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS","PATCH"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
