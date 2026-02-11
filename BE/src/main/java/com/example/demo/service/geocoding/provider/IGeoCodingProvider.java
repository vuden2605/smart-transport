package com.example.demo.service.geocoding.provider;

import com.example.demo.connector.model.LocationIQResponse;
import com.example.demo.enums.GeoCodingProviderType;

public interface IGeoCodingProvider {
	GeoCodingProviderType getProviderType();
	LocationIQResponse forward(String address);
	LocationIQResponse reverse(double lat, double lon);
}
