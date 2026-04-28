package com.scholartrack.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your ScholarTrack Login Verification Code");
        message.setText("Hello,\n\n" +
                "Your one-time password (OTP) for ScholarTrack login is: " + otp + "\n" +
                "This code is valid for 5 minutes.\n\n" +
                "If you did not request this code, please ignore this email.\n\n" +
                "Best regards,\n" +
                "The ScholarTrack Team");
        
        try {
            mailSender.send(message);
            System.out.println("📧 OTP Email sent successfully to " + toEmail);
        } catch (Exception e) {
            System.err.println("❌ Failed to send OTP email to " + toEmail + ": " + e.getMessage());
        }
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        try {
            mailSender.send(message);
            System.out.println("📧 Notification Email sent successfully to " + to);
        } catch (Exception e) {
            System.err.println("❌ Failed to send notification email to " + to + ": " + e.getMessage());
        }
    }
}
