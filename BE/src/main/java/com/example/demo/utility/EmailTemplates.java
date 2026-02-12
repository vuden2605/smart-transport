package com.example.demo.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailTemplates {

	private static final String BASE_STYLE = """
        <style>
            * { margin: 0; padding: 0; box-sizing: border-box; }
            body {
                font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
                line-height: 1.6;
                color: #333;
                background-color: #f4f6f9;
                padding: 20px;
            }
            .email-container {
                max-width: 600px;
                margin: 0 auto;
                background: #ffffff;
                border-radius: 12px;
                overflow: hidden;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.07);
            }
            .header {
                background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
                color: white;
                padding: 40px 30px;
                text-align: center;
            }
            .header h1 {
                font-size: 28px;
                font-weight: 600;
                margin: 0;
            }
            .content {
                padding: 40px 30px;
            }
            .greeting {
                font-size: 18px;
                color: #333;
                margin-bottom: 20px;
            }
            .text {
                color: #555;
                margin-bottom: 20px;
                font-size: 15px;
            }
            .otp-box {
                background: linear-gradient(135deg, #f5f7fa 0%%, #c3cfe2 100%%);
                border: 2px dashed #667eea;
                border-radius: 10px;
                padding: 25px;
                text-align: center;
                margin: 30px 0;
            }
            .otp-label {
                font-size: 13px;
                color: #666;
                text-transform: uppercase;
                letter-spacing: 1px;
                margin-bottom: 10px;
            }
            .otp-code {
                font-size: 42px;
                font-weight: bold;
                color: #667eea;
                letter-spacing: 10px;
                font-family: 'Courier New', Courier, monospace;
            }
            .button {
                display: inline-block;
                padding: 14px 40px;
                background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
                color: white !important;
                text-decoration: none;
                border-radius: 8px;
                font-weight: 600;
                margin: 20px 0;
                transition: transform 0.2s;
            }
            .button:hover {
                transform: translateY(-2px);
            }
            .warning-box {
                background: #fff3cd;
                border-left: 4px solid #ffc107;
                padding: 15px;
                margin: 25px 0;
                border-radius: 4px;
            }
            .warning-box strong {
                color: #856404;
            }
            .info-box {
                background: #d1ecf1;
                border-left: 4px solid #17a2b8;
                padding: 15px;
                margin: 25px 0;
                border-radius: 4px;
                color: #0c5460;
            }
            .danger-box {
                background: #f8d7da;
                border-left: 4px solid #dc3545;
                padding: 15px;
                margin: 25px 0;
                border-radius: 4px;
                color: #721c24;
            }
            .footer {
                background: #f8f9fa;
                padding: 30px;
                text-align: center;
                border-top: 1px solid #e9ecef;
            }
            .footer p {
                color: #6c757d;
                font-size: 13px;
                margin: 5px 0;
            }
            .footer a {
                color: #667eea;
                text-decoration: none;
            }
            .divider {
                height: 1px;
                background: #e9ecef;
                margin: 30px 0;
            }
            ul {
                margin: 10px 0;
                padding-left: 20px;
            }
            ul li {
                margin: 8px 0;
                color: #555;
            }
        </style>
    """;

	/**
	 * 1. OTP Email Template
	 */
	public static String buildOtpTemplate(String otp, String userName,
	                                      String appName, int expiryMinutes) {
		return """
            <!DOCTYPE html>
            <html lang="vi">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Mã xác thực OTP</title>
                %s
            </head>
            <body>
                <div class="email-container">
                    <div class="header">
                        <h1>🔐 Xác Thực Tài Khoản</h1>
                    </div>
                    
                    <div class="content">
                        <p class="greeting">Xin chào <strong>%s</strong>,</p>
                        
                        <p class="text">
                            Cảm ơn bạn đã đăng ký tài khoản tại <strong>%s</strong>. 
                            Để hoàn tất quá trình đăng ký, vui lòng sử dụng mã OTP bên dưới:
                        </p>
                        
                        <div class="otp-box">
                            <div class="otp-label">Mã xác thực của bạn</div>
                            <div class="otp-code">%s</div>
                        </div>
                        
                        <div class="warning-box">
                            <strong>⚠️ Lưu ý quan trọng:</strong>
                            <ul>
                                <li>Mã OTP có hiệu lực trong <strong>%d phút</strong></li>
                                <li>Không chia sẻ mã này với bất kỳ ai</li>
                                <li>%s sẽ không bao giờ yêu cầu mã OTP qua điện thoại</li>
                                <li>Nếu bạn không yêu cầu mã này, vui lòng bỏ qua email</li>
                            </ul>
                        </div>
                    </div>
                    
                    <div class="footer">
                        <p><strong>%s</strong></p>
                        <p>Email này được gửi tự động, vui lòng không trả lời.</p>
                        <p>© %d %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
				BASE_STYLE,
				userName != null ? userName : "Bạn",
				appName,
				otp,
				expiryMinutes,
				appName,
				appName,
				LocalDateTime.now().getYear(),
				appName
		);
	}

	/**
	 * 2. Verification Link Template
	 */
	public static String buildVerificationLinkTemplate(String verificationLink,
	                                                   String userName,
	                                                   String appName,
	                                                   int expiryHours) {
		return """
            <!DOCTYPE html>
            <html lang="vi">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Xác thực Email</title>
                %s
            </head>
            <body>
                <div class="email-container">
                    <div class="header">
                        <h1>✉️ Xác Thực Email</h1>
                    </div>
                    
                    <div class="content">
                        <p class="greeting">Xin chào <strong>%s</strong>,</p>
                        
                        <p class="text">
                            Cảm ơn bạn đã đăng ký tài khoản tại <strong>%s</strong>. 
                            Chỉ còn một bước nữa để hoàn tất!
                        </p>
                        
                        <p class="text">
                            Vui lòng click vào nút bên dưới để xác thực địa chỉ email của bạn:
                        </p>
                        
                        <div style="text-align: center;">
                            <a href="%s" class="button">
                                ✅ Xác Thực Email Ngay
                            </a>
                        </div>
                        
                        <div class="divider"></div>
                        
                        <p class="text" style="font-size: 13px; color: #888;">
                            Nếu nút không hoạt động, copy và paste link sau vào trình duyệt:
                        </p>
                        <p style="font-size: 12px; color: #667eea; word-break: break-all;">
                            %s
                        </p>
                        
                        <div class="info-box">
                            <strong>ℹ️ Thông tin:</strong>
                            <ul>
                                <li>Link xác thực có hiệu lực trong <strong>%d giờ</strong></li>
                                <li>Chỉ sử dụng được <strong>1 lần</strong></li>
                            </ul>
                        </div>
                    </div>
                    
                    <div class="footer">
                        <p><strong>%s</strong></p>
                        <p>Nếu bạn không đăng ký tài khoản này, vui lòng bỏ qua email.</p>
                        <p>© %d %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
				BASE_STYLE,
				userName != null ? userName : "Bạn",
				appName,
				verificationLink,
				verificationLink,
				expiryHours,
				appName,
				LocalDateTime.now().getYear(),
				appName
		);
	}

	/**
	 * 3. Welcome Email Template
	 */
	public static String buildWelcomeTemplate(String userName, String appName, String dashboardUrl) {
		return """
            <!DOCTYPE html>
            <html lang="vi">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Chào mừng!</title>
                %s
            </head>
            <body>
                <div class="email-container">
                    <div class="header">
                        <h1>🎉 Chào Mừng Đến Với %s!</h1>
                    </div>
                    
                    <div class="content">
                        <p class="greeting">Xin chào <strong>%s</strong>,</p>
                        
                        <p class="text">
                            Chúc mừng! Tài khoản của bạn đã được kích hoạt thành công. 
                            Chào mừng bạn đến với cộng đồng <strong>%s</strong>! 🎊
                        </p>
                        
                        <div class="info-box">
                            <strong>🚀 Bắt đầu nào:</strong>
                            <ul>
                                <li>Khám phá dashboard của bạn</li>
                                <li>Cập nhật thông tin cá nhân</li>
                                <li>Tùy chỉnh cài đặt theo ý muốn</li>
                                <li>Bắt đầu sử dụng các tính năng</li>
                            </ul>
                        </div>
                        
                        <div style="text-align: center;">
                            <a href="%s" class="button">
                                🏠 Truy Cập Dashboard
                            </a>
                        </div>
                        
                        <div class="divider"></div>
                        
                        <p class="text">
                            Nếu bạn có bất kỳ câu hỏi nào, đừng ngần ngại liên hệ với chúng tôi. 
                            Chúng tôi luôn sẵn sàng hỗ trợ bạn! 💬
                        </p>
                    </div>
                    
                    <div class="footer">
                        <p><strong>%s Team</strong></p>
                        <p>© %d %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
				BASE_STYLE,
				appName,
				userName != null ? userName : "Bạn",
				appName,
				dashboardUrl,
				appName,
				LocalDateTime.now().getYear(),
				appName
		);
	}

	/**
	 * 4. Password Reset Template
	 */
	public static String buildPasswordResetTemplate(String resetLink, String userName,
	                                                String appName, int expiryHours) {
		return """
            <!DOCTYPE html>
            <html lang="vi">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Đặt lại mật khẩu</title>
                %s
            </head>
            <body>
                <div class="email-container">
                    <div class="header">
                        <h1>🔑 Đặt Lại Mật Khẩu</h1>
                    </div>
                    
                    <div class="content">
                        <p class="greeting">Xin chào <strong>%s</strong>,</p>
                        
                        <p class="text">
                            Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn tại <strong>%s</strong>.
                        </p>
                        
                        <p class="text">
                            Click vào nút bên dưới để tạo mật khẩu mới:
                        </p>
                        
                        <div style="text-align: center;">
                            <a href="%s" class="button">
                                🔐 Đặt Lại Mật Khẩu
                            </a>
                        </div>
                        
                        <div class="warning-box">
                            <strong>⚠️ Lưu ý bảo mật:</strong>
                            <ul>
                                <li>Link này có hiệu lực trong <strong>%d giờ</strong></li>
                                <li>Chỉ sử dụng được <strong>1 lần</strong></li>
                                <li><strong>Nếu bạn không yêu cầu đặt lại mật khẩu</strong>, vui lòng bỏ qua email này và đổi mật khẩu ngay</li>
                            </ul>
                        </div>
                        
                        <div class="divider"></div>
                        
                        <p class="text" style="font-size: 13px; color: #888;">
                            Link đầy đủ (nếu nút không hoạt động):
                        </p>
                        <p style="font-size: 12px; color: #667eea; word-break: break-all;">
                            %s
                        </p>
                    </div>
                    
                    <div class="footer">
                        <p><strong>%s Security Team</strong></p>
                        <p>© %d %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
				BASE_STYLE,
				userName != null ? userName : "Bạn",
				appName,
				resetLink,
				expiryHours,
				resetLink,
				appName,
				LocalDateTime.now().getYear(),
				appName
		);
	}

	/**
	 * 5. Password Changed Notification Template
	 */
	public static String buildPasswordChangedTemplate(String userName, String appName,
	                                                  String supportEmail, LocalDateTime changedAt) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		return """
            <!DOCTYPE html>
            <html lang="vi">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Mật khẩu đã thay đổi</title>
                %s
            </head>
            <body>
                <div class="email-container">
                    <div class="header">
                        <h1>✅ Mật Khẩu Đã Được Thay Đổi</h1>
                    </div>
                    
                    <div class="content">
                        <p class="greeting">Xin chào <strong>%s</strong>,</p>
                        
                        <p class="text">
                            Mật khẩu tài khoản <strong>%s</strong> của bạn đã được thay đổi thành công.
                        </p>
                        
                        <div class="info-box">
                            <strong>📋 Thông tin chi tiết:</strong>
                            <ul>
                                <li><strong>Thời gian:</strong> %s</li>
                                <li><strong>Tài khoản:</strong> %s</li>
                            </ul>
                        </div>
                        
                        <div class="danger-box">
                            <strong>⚠️ Không phải bạn thực hiện?</strong>
                            <p style="margin-top: 10px;">
                                Nếu bạn không thực hiện thay đổi này, tài khoản của bạn có thể đã bị xâm nhập. 
                                Vui lòng liên hệ ngay với chúng tôi qua email: 
                                <a href="mailto:%s" style="color: #721c24; font-weight: bold;">%s</a>
                            </p>
                        </div>
                    </div>
                    
                    <div class="footer">
                        <p><strong>%s Security Team</strong></p>
                        <p>© %d %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
				BASE_STYLE,
				userName != null ? userName : "Bạn",
				appName,
				changedAt.format(formatter),
				appName,
				supportEmail,
				supportEmail,
				appName,
				LocalDateTime.now().getYear(),
				appName
		);
	}

	/**
	 * 6. New Device Login Template
	 */
	public static String buildNewDeviceLoginTemplate(String userName, String appName,
	                                                 String device, String location,
	                                                 String ipAddress, LocalDateTime loginTime,
	                                                 String supportEmail) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		return """
            <!DOCTYPE html>
            <html lang="vi">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Đăng nhập từ thiết bị mới</title>
                %s
            </head>
            <body>
                <div class="email-container">
                    <div class="header">
                        <h1>🔔 Đăng Nhập Từ Thiết Bị Mới</h1>
                    </div>
                    
                    <div class="content">
                        <p class="greeting">Xin chào <strong>%s</strong>,</p>
                        
                        <p class="text">
                            Chúng tôi phát hiện tài khoản <strong>%s</strong> của bạn vừa đăng nhập từ một thiết bị mới.
                        </p>
                        
                        <div class="info-box">
                            <strong>📱 Thông tin đăng nhập:</strong>
                            <ul>
                                <li><strong>Thời gian:</strong> %s</li>
                                <li><strong>Thiết bị:</strong> %s</li>
                                <li><strong>Vị trí:</strong> %s</li>
                                <li><strong>Địa chỉ IP:</strong> %s</li>
                            </ul>
                        </div>
                        
                        <div class="warning-box">
                            <strong>✅ Nếu đây là bạn:</strong>
                            <p style="margin-top: 10px;">
                                Không cần làm gì cả. Bạn có thể bỏ qua email này.
                            </p>
                        </div>
                        
                        <div class="danger-box">
                            <strong>⚠️ Không phải bạn?</strong>
                            <p style="margin-top: 10px;">
                                Vui lòng đổi mật khẩu ngay lập tức và liên hệ với chúng tôi qua: 
                                <a href="mailto:%s" style="color: #721c24; font-weight: bold;">%s</a>
                            </p>
                        </div>
                    </div>
                    
                    <div class="footer">
                        <p><strong>%s Security Team</strong></p>
                        <p>© %d %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
				BASE_STYLE,
				userName != null ? userName : "Bạn",
				appName,
				loginTime.format(formatter),
				device,
				location,
				ipAddress,
				supportEmail,
				supportEmail,
				appName,
				LocalDateTime.now().getYear(),
				appName
		);
	}
}
