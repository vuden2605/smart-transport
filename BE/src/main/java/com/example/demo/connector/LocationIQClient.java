package com.example.demo.connector;

import com.example.demo.connector.model.LocationIQResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
		name = "location-iq",
		url = "${feign.client.config.location-iq.baseUrl}"
)
public interface LocationIQClient {
	@GetMapping("/search.php")
	List<LocationIQResponse> forwardGeocoding(
			@RequestParam("key") String apiKey,
			@RequestParam("q") String address,
			@RequestParam("format") String format
	);
	@GetMapping("/reverse.php")
	LocationIQResponse reverseGeocoding(
			@RequestParam("key") String apiKey,
			@RequestParam("lat") double lat,
			@RequestParam("lon") double lon,
			@RequestParam("format") String format
	);

}
