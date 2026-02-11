package com.example.demo.service.geocoding.provider;

import com.example.demo.connector.model.GeoCodingResponse;
import com.example.demo.enums.GeoCodingProviderType;

public interface IGeoCodingProvider {
	GeoCodingProviderType getProviderType();
	GeoCodingResponse forward(String address);
	GeoCodingResponse reverse(double lat, double lon);
}
