package com.example.demo.service.email.impl;


import com.example.demo.exception.EmailErrorException;
import com.example.demo.service.email.IEmailService;
import com.example.demo.utility.EmailTemplates;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Value("${spring.application.name:SmartTransport}")
	private String appName;

	@Value("${app.frontend.url:http://localhost:3000}")
	private String frontendUrl;

	@Value("${spring.mail.username:support@example.com}")
	private String supportEmail;

	@Async
	public void sendOtpEmail(String toEmail, String otp, String userName) {
		try {
			String subject = String.format("[%s] Mã xác thực tài khoản", appName);
			String htmlContent = EmailTemplates.buildOtpTemplate(
					otp,
					userName,
					appName,
					10 // minutes
			);

			sendHtmlEmail(toEmail, subject, htmlContent);
			log.info("✅ OTP email sent to: {}", toEmail);

		} catch (Exception e) {
			log.error("❌ Failed to send OTP email to: {}", toEmail, e);
			throw new EmailErrorException();
		}
	}

	@Async
	public void sendWelcomeEmail(String toEmail, String userName) {
		try {
			String subject = String.format("Chào mừng đến với %s! 🎉", appName);
			String htmlContent = EmailTemplates.buildWelcomeTemplate(
					userName,
					appName,
					frontendUrl
			);

			sendHtmlEmail(toEmail, subject, htmlContent);
			log.info("✅ Welcome email sent to: {}", toEmail);

		} catch (Exception e) {
			log.info("❌ Failed to send welcome email to: {}", toEmail, e);

		}
	}

	@Async
	public void sendPasswordResetEmail(String toEmail, String resetToken, String userName) {
		try {
			String resetLink = frontendUrl + "/reset-password?token=" + resetToken;
			String subject = String.format("[%s] Yêu cầu đặt lại mật khẩu", appName);
			String htmlContent = EmailTemplates.buildPasswordResetTemplate(
					resetLink,
					userName,
					appName,
					1 // hour
			);

			sendHtmlEmail(toEmail, subject, htmlContent);
			log.info("✅ Password reset email sent to: {}", toEmail);

		} catch (Exception e) {
			log.info("❌ Failed to send password reset email to: {}", toEmail, e);
			throw new EmailErrorException();
		}
	}

	@Async
	public void sendPasswordChangedEmail(String toEmail, String userName) {
		try {
			String subject = String.format("[%s] Mật khẩu đã được thay đổi", appName);
			String htmlContent = EmailTemplates.buildPasswordChangedTemplate(
					userName,
					appName,
					supportEmail,
					LocalDateTime.now()
			);

			sendHtmlEmail(toEmail, subject, htmlContent);
			log.info("✅ Password changed notification sent to: {}", toEmail);

		} catch (Exception e) {
			log.info("❌ Failed to send password changed email to: {}", toEmail, e);
		}
	}


	private void sendHtmlEmail(String to, String subject, String htmlContent) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom(fromEmail);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlContent, true);

			mailSender.send(message);

		} catch (MessagingException e) {
			log.info("❌ Failed to send HTML email to: {}", to, e);
			throw new EmailErrorException();
		}
	}

	public void sendSimpleEmail(String to, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromEmail);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);

			mailSender.send(message);
			log.info("✅ Simple email sent to: {}", to);

		} catch (Exception e) {
			log.info("❌ Failed to send simple email to: {}", to, e);
			throw new EmailErrorException();
		}
	}
}
