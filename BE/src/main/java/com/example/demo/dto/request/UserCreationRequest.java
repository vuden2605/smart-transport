package com.example.demo.dto.request;

import com.example.demo.config.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreationRequest {
	@NotBlank(message = "EMPTY_INPUT")
	private String name;

	@NotBlank(message = "EMPTY_INPUT")
	//@Pattern(regexp = Constants.EMAIL_PATTERN, message = "INVALID_EMAIL")
	private String email;

	@NotBlank(message = "EMPTY_INPUT")
	private String phone;

	@NotBlank(message = "EMPTY_INPUT")
	@Size(min = 6, message = "PASSWORD_TOO_SHORT")
	private String password;

}
