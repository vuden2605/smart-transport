package com.example.demo.config.security;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app.authenticate-ignored")
public class SecurityProperties {
	private Methods methods = new Methods();
	@Getter
	@Setter
	public static class Methods {
		private List<String> post;
		private List<String> get;
	}
}
