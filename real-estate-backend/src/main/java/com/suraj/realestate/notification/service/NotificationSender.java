package com.suraj.realestate.notification.service;

import java.util.Map;

/**
 * Extension point for notification channels. AuthService and future
 * booking/payment listeners depend on this, never a concrete channel.
 */
public interface NotificationSender {
    void send(String recipient, String subject, String templateName, Map<String, Object> templateVariables);
}