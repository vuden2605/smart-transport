package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest {
	@NotBlank(message = "EMPTY_INPUT")
	private String accessToken;
}
