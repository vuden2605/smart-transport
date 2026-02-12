package com.example.demo.service.email;

public interface IEmailService {
	void sendOtpEmail(String toEmail, String otp, String userName);
	void sendWelcomeEmail(String toEmail, String userName);
	void sendPasswordResetEmail(String toEmail, String resetToken, String userName);
	void sendPasswordChangedEmail(String toEmail, String userName);
}
