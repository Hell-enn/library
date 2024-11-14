package com.library.library.email;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendNotificationEmail(String toAddress, String subject, String message) throws MessagingException;
}
