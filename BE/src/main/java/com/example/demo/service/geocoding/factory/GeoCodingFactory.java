package com.example.demo.service.geocoding.factory;

import com.example.demo.enums.GeoCodingProviderType;
import com.example.demo.exception.ProviderNotFoundException;
import com.example.demo.service.geocoding.provider.IGeoCodingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GeoCodingFactory {
	private final Map<GeoCodingProviderType, IGeoCodingProvider> providers;
	public GeoCodingFactory(List<IGeoCodingProvider> providers) {
		this.providers = providers.stream()
				.collect(
						java.util.stream.Collectors.toMap(
								IGeoCodingProvider::getProviderType,
								provider -> provider
						)
				);
	}
	public IGeoCodingProvider getProvider(GeoCodingProviderType providerName) {
		IGeoCodingProvider provider = providers.get(providerName);
		if (provider == null) {
			throw new ProviderNotFoundException();
		}
		return provider;
	}
}
