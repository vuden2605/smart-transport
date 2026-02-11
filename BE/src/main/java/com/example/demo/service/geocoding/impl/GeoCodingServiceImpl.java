package com.example.demo.service.geocoding.impl;

import com.example.demo.connector.model.GeoCodingResponse;
import com.example.demo.enums.GeoCodingProviderType;
import com.example.demo.service.geocoding.IGeoCodingService;
import com.example.demo.service.geocoding.factory.GeoCodingFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeoCodingServiceImpl implements IGeoCodingService {
	private final GeoCodingFactory factory;
	public GeoCodingResponse forward(GeoCodingProviderType provider, String address) {
		return factory.getProvider(provider).forward(address);
	}
	public GeoCodingResponse reverse(GeoCodingProviderType provider, double lat, double lon) {
		return factory.getProvider(provider).reverse(lat, lon);
	}
}
