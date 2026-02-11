package com.example.demo.service.geocoding.provider;

import com.example.demo.connector.model.LocationIQResponse;
import com.example.demo.enums.GeoCodingProviderType;
import org.springframework.stereotype.Service;

@Service
public class GoogleProvider implements IGeoCodingProvider{


	@Override
	public GeoCodingProviderType getProviderType() {
		return GeoCodingProviderType.GOOGLE;
	}

	@Override
	public LocationIQResponse forward(String address) {
		return null;
	}

	@Override
	public LocationIQResponse reverse(double lat, double lon) {
		return null;
	}
}
