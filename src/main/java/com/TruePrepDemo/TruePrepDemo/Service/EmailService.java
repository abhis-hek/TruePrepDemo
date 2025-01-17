package com.TruePrepDemo.TruePrepDemo.Service;

import com.TruePrepDemo.TruePrepDemo.Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Method to send notification for new test assignments
    public void sendTestNotification(List<Student> students, String testDetails) {
        for (Student student : students) {
            sendEmail(student.getEmail(), "New Test Assigned", testDetails);
        }
    }

    // Method to send a welcome email after successful student signup
    public void sendWelcomeEmail(String toEmail) {
        String subject = "Welcome to TruePrep!";
        String text = "Dear Student,\n\nWelcome to TruePrep! We're excited to have you on board.\n\nBest regards,\nTruePrep Team";
        sendEmail(toEmail, subject, text);
    }

    // Common method to send an email
    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        try {
            mailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
