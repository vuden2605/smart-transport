package com.example.demo.service.user.Impl;

import com.example.demo.dto.request.ResendOtpRequest;
import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.VerifyUserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.*;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.email.IEmailService;
import com.example.demo.service.user.IUserService;
import com.example.demo.utility.OtpGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
	UserRepository userRepository;
	UserMapper userMapper;
	PasswordEncoder passwordEncoder;
	CacheManager cacheManager;
	IEmailService emailService;
	@Override
	public UserResponse createUser(UserCreationRequest request) {
		if(userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new EmailAlreadyRegisteredException();
		}
		User user = userMapper.toUser(request);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		User savedUser = userRepository.save(user);
		String otp = OtpGenerator.generateOtp();
		emailService.sendOtpEmail(savedUser.getEmail(), otp, savedUser.getName());
		Cache cache = cacheManager.getCache("otp.codes");
		if (cache != null) {
			cache.put(savedUser.getEmail(), otp);
		}
		return userMapper.toUserResponse(savedUser);    
	}

	public UserResponse verifyUserByEmail(VerifyUserRequest request) {
		String email = request.getEmail();
		String otp = request.getOtp();
		Cache cache = cacheManager.getCache("otp.codes");
		if(cache == null) {
			throw new IllegalStateException("OTP cache not available");
		}
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
		if (user.getIsVerified()) {
			throw new UserAlreadyVerifiedException();
		}

		Cache.ValueWrapper valueWrapper = cache.get(email);
		if (valueWrapper == null) {
			throw new OtpExpiredException();
		}
		String cachedOtp = (String) valueWrapper.get();
		if (!request.getOtp().equals(cachedOtp)) {
			throw new InvalidOtpException();
		}
		cache.evict(request.getEmail());
		user.setIsVerified(true);
		userRepository.save(user);
		return userMapper.toUserResponse(user);
	}
	public void resendOtp(ResendOtpRequest request) {
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow(UserNotFoundException::new);
		if (user.getIsVerified()) {
			throw new UserAlreadyVerifiedException();
		}
		String otp = OtpGenerator.generateOtp();
		emailService.sendOtpEmail(user.getEmail(), otp, user.getName());
		Cache cache = cacheManager.getCache("otp.codes");
		if (cache != null) {
			cache.put(user.getEmail(), otp);
		}
	}
	@Override
	public UserResponse getUserById(Long id) {

		User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
		return userMapper.toUserResponse(user);
	}
}
