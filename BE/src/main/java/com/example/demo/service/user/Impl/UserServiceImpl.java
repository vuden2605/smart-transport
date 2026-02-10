package com.example.demo.service.user.Impl;

import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	@Override
	public UserResponse createUser(UserCreationRequest request) {
		User user = userMapper.toUser(request);
		User savedUser = userRepository.save(user);
		return userMapper.toUserResponse(savedUser);    
	}

	@Override
	public UserResponse getUserById(Long id) {

		User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
		return userMapper.toUserResponse(user);
	}
}
