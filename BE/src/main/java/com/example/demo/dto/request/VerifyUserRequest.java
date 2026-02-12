package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerifyUserRequest {
	@NotBlank(message = "EMPTY_INPUT")
	private String email;

	@NotBlank(message = "EMPTY_INPUT")
	private String otp;
}
