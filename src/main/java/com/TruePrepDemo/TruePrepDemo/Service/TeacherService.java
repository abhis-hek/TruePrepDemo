package com.TruePrepDemo.TruePrepDemo.Service;

import com.TruePrepDemo.TruePrepDemo.Model.Teacher;
import com.TruePrepDemo.TruePrepDemo.Repo.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private JavaMailSender mailSender;

    public Teacher addTeacherWithGeneratedPassword(Teacher teacher) {
        teacher.setApproved(false); // Default to not approved

        // Generate a random password
        String rawPassword = generateRandomPassword();

        // Save the plain-text password directly
        teacher.setPassword(rawPassword);

        // Save the teacher to the database
        Teacher savedTeacher = teacherRepository.save(teacher);

        // Send the plain-text password to the teacher via email
        sendPasswordEmail(teacher, rawPassword);

        return savedTeacher;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public void deleteTeacher(String id) {
        teacherRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return teacherRepository.existsByEmail(email);
    }

    public Teacher approveTeacher(String id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        teacher.setApproved(true);
        teacherRepository.save(teacher);

        sendApprovalEmail(teacher); // Send email upon approval
        return teacher;
    }

    private void sendPasswordEmail(Teacher teacher, String password) {
        String subject = "Your TruePrep Account Details";
        String message = String.format(
                "Dear %s,\n\nYour account has been created. You can log in with the following credentials:\n\nEmail: %s\nPassword: %s\n\nPlease change your password after your first login.\n\nBest regards,\nTruePrep Team",
                teacher.getName(), teacher.getEmail(), password
        );

        // Ensure that SimpleMailMessage is used for plain text emails.
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(teacher.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        // Check if the email is actually being sent and check the logs for any errors.
        try {
            mailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }


    private void sendApprovalEmail(Teacher teacher) {
        String subject = "Your Account Has Been Approved";
        String message = String.format(
                "Dear %s,\n\nYour account has been approved. You can now log in with your credentials.\n\nBest regards,\nTruePrep Team",
                teacher.getName()
        );

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(teacher.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    private String generateRandomPassword() {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(charSet.length());
            password.append(charSet.charAt(index));
        }
        return password.toString();
    }
}
