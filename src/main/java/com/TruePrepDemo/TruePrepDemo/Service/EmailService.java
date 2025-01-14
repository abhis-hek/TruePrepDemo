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

    public void sendTestNotification(List<Student> students, String testDetails) {
        for (Student student : students) {
            sendEmail(student.getEmail(), "New Test Assigned", testDetails);
        }
    }

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
