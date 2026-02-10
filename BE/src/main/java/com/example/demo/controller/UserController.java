package com.example.demo.controller;

import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
	private final IUserService userService;
	@PostMapping
	public ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest request) {
		UserResponse userResponse = userService.createUser(request);
		return ApiResponse.<UserResponse>builder()
				.message("User created successfully")
				.data(userResponse)
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
