package com.example.demo.connector.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationIQResponse {
	private String lat;

	private String lon;

	@JsonProperty("display_name")
	private String displayName;
}
