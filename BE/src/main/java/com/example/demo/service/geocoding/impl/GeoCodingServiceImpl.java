package com.example.demo.service.geocoding.impl;

import com.example.demo.connector.model.LocationIQResponse;
import com.example.demo.enums.GeoCodingProviderType;
import com.example.demo.service.geocoding.IGeoCodingService;
import com.example.demo.service.geocoding.factory.GeoCodingFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GeoCodingServiceImpl implements IGeoCodingService {
	private final GeoCodingFactory factory;
	public LocationIQResponse forward(GeoCodingProviderType provider, String address) {
		return factory.getProvider(provider).forward(address);
	}
}
