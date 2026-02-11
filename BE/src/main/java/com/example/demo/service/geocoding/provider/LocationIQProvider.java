package com.example.demo.service.geocoding.provider;

import com.example.demo.config.geocoding.LocationIQProperties;
import com.example.demo.connector.LocationIQClient;
import com.example.demo.connector.model.GeoCodingResponse;
import com.example.demo.enums.GeoCodingProviderType;
import com.example.demo.exception.AddressNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
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
	@Cacheable(value = "locationCache", key = "#address", unless = "#result == null")
	public GeoCodingResponse forward(String address) {
		List<GeoCodingResponse> responses =
				locationIQClient.forwardGeocoding(
						properties.getApiKey(),
						address,
						"json"
				);

		if (responses.isEmpty()) {
			throw new AddressNotFoundException();
		}

		return responses.getFirst();
	}

	@Override
	@Cacheable(value = "locationCache", key = "#lat + ',' + #lon", unless = "#result == null")
	public GeoCodingResponse reverse(double lat, double lon) {
		GeoCodingResponse response = locationIQClient.reverseGeocoding(
				properties.getApiKey(),
				lat,
				lon,
				"json"
		);

		if (response == null) {
			throw new AddressNotFoundException();
		}
		return response;
	}
}
