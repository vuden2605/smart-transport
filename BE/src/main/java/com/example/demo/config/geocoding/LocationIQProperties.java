package com.example.demo.config.geocoding;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "feign.client.config.location-iq")
public class LocationIQProperties {
	private String baseUrl;
	private String apiKey;
	private Duration connectTimeout;
}
