package com.example.demo.utility;

import java.security.SecureRandom;

public class OtpGenerator {

	private static final SecureRandom random = new SecureRandom();

	public static String generateNumericOtp(int length) {
		StringBuilder otp = new StringBuilder();
		for (int i = 0; i < length; i++) {
			otp.append(random.nextInt(10));
		}
		return otp.toString();
	}

	public static String generateOtp() {
		return generateNumericOtp(6);
	}
}
