package com.example.demo.service.geocoding;

import com.example.demo.connector.model.GeoCodingResponse;
import com.example.demo.enums.GeoCodingProviderType;

public interface IGeoCodingService {
	GeoCodingResponse forward(GeoCodingProviderType provider, String address);
	GeoCodingResponse reverse(GeoCodingProviderType provider, double lat, double lon);
}
