package com.example.demo.controller;

import com.example.demo.dto.request.ResendOtpRequest;
import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.VerifyUserRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
	private final IUserService userService;
	@PostMapping
	public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
		UserResponse userResponse = userService.createUser(request);
		return ApiResponse.<UserResponse>builder()
				.message("User created successfully")
				.data(userResponse)
				.build();
	}
	@PostMapping("/verify")
	public ApiResponse<UserResponse> verifyUser(@RequestBody @Valid VerifyUserRequest request) {
		UserResponse userResponse = userService.verifyUserByEmail(request);
		return ApiResponse.<UserResponse>builder()
				.message("User verified successfully")
				.data(userResponse)
				.build();
	}
	@PostMapping("/resend-otp")
	public ApiResponse<Void> resendOtp(@RequestBody @Valid ResendOtpRequest request) {
		userService.resendOtp(request);
		return ApiResponse.<Void>builder()
				.message("OTP resent successfully")
				.build();
	}
	@GetMapping("/{id}")
	public ApiResponse<UserResponse> getUserById(@PathVariable("id") Long id) {
		UserResponse userResponse = userService.getUserById(id);
		return ApiResponse.<UserResponse>builder()
				.message("User retrieved successfully")
				.data(userResponse)
				.build();
	}
}
