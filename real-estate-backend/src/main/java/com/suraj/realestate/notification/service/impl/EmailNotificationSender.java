package com.suraj.realestate.notification.service.impl;

import com.suraj.realestate.common.email.EmailService;
import com.suraj.realestate.notification.service.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/** Email channel implementation of NotificationSender. */
@Service
@RequiredArgsConstructor
public class EmailNotificationSender implements NotificationSender {

    private final EmailService emailService;

    @Override
    public void send(String recipient, String subject, String templateName, Map<String, Object> templateVariables) {
        emailService.sendHtmlEmail(recipient, subject, templateName, templateVariables);
    }
}