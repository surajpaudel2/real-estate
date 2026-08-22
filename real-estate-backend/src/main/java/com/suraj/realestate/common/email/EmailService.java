package com.suraj.realestate.common.email;

/**
 * Low-level "send an email" capability. Used directly by AuthService
 * (OTP is critical-path) and indirectly by EmailNotificationSender
 * (booking/payment events, best-effort).
 */
public interface EmailService {

    void sendHtmlEmail(String to, String subject, String templateName, java.util.Map<String, Object> templateVariables);
}