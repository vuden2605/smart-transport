package com.example.demo.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class Constants {
	@NoArgsConstructor(access = AccessLevel.NONE)
	public static final class CacheNames {
		private static final String USER = "user.";
		private static final String AUTH = "auth.";
		public static final String USER_DETAILS = USER + "details";
		public static final String INVALIDATED_TOKENS = AUTH + "invalidatedTokens";
		public static final String OTP = AUTH + "otp";
	}
	public static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
}
