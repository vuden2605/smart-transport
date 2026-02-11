package com.example.demo.service.geocoding.provider;

import com.example.demo.config.geocoding.LocationIQProperties;
import com.example.demo.connector.LocationIQClient;
import com.example.demo.connector.model.LocationIQResponse;
import com.example.demo.enums.GeoCodingProviderType;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class LocationIQProvider implements IGeoCodingProvider {
	LocationIQClient locationIQClient;
	LocationIQProperties properties;

	@Override
	public GeoCodingProviderType getProviderType() {
		return GeoCodingProviderType.LOCATION_IQ;
	}

	@Override
	public LocationIQResponse forward(String address) {
		List<LocationIQResponse> responses =
				locationIQClient.forwardGeocoding(
						properties.getApiKey(),
						address,
						"json"
				);

		if (responses.isEmpty()) {
			throw new RuntimeException("Address not found");
		}

		return responses.getFirst();
	}

	@Override
	public LocationIQResponse reverse(double lat, double lon) {
		return locationIQClient.reverseGeocoding(
				properties.getApiKey(),
				lat,
				lon,
				"json"
		);
	}
}
