package com.example.demo.service.geocoding;

import com.example.demo.connector.model.LocationIQResponse;
import com.example.demo.enums.GeoCodingProviderType;

public interface IGeoCodingService {
	LocationIQResponse forward(GeoCodingProviderType provider, String address);
}
