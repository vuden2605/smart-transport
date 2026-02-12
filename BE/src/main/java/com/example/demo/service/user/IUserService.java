package com.example.demo.service.user;

import com.example.demo.dto.request.ResendOtpRequest;
import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.VerifyUserRequest;
import com.example.demo.dto.response.UserResponse;

public interface IUserService {
	UserResponse createUser(UserCreationRequest request);
	UserResponse getUserById(Long id);
	UserResponse verifyUserByEmail(VerifyUserRequest request);
	void resendOtp(ResendOtpRequest request);
}
