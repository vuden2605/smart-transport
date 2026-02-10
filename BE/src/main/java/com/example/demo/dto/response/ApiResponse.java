package com.example.demo.dto.response;

import com.example.demo.exception.model.DomainCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	@Builder.Default
	private Integer code = DomainCode.SUCCESS.getCode();

	@Builder.Default
	private String message = DomainCode.SUCCESS.getMessage();

	private T data;
}
