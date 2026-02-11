package com.example.demo.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.frontend-url")
public class FrontendUrlProperties {
	List<String> urls = new ArrayList<>();
}
