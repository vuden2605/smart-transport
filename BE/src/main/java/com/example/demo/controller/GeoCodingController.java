package com.example.demo.controller;

import com.example.demo.connector.model.GeoCodingResponse;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.enums.GeoCodingProviderType;
import com.example.demo.service.geocoding.IGeoCodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/geocoding")
public class GeoCodingController {
	private final IGeoCodingService geoCodingService;
	@GetMapping("/forward")
	public ApiResponse<GeoCodingResponse> forward(
			@RequestParam String address,
			@RequestParam(defaultValue = "LOCATION_IQ") GeoCodingProviderType providerType
			) {
		return ApiResponse.<GeoCodingResponse>builder()
				.data(geoCodingService.forward(providerType, address))
				.build();

	}
	@GetMapping("/reverse")
	public ApiResponse<GeoCodingResponse> reverse(
			@RequestParam double lat,
			@RequestParam double lon,
			@RequestParam(defaultValue = "LOCATION_IQ") GeoCodingProviderType providerType
	) {
		return ApiResponse.<GeoCodingResponse>builder()
				.data(geoCodingService.reverse(providerType, lat, lon))
				.build();
	}
}
