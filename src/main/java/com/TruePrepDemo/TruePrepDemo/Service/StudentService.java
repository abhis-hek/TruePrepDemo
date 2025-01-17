package com.TruePrepDemo.TruePrepDemo.Service;

import com.TruePrepDemo.TruePrepDemo.Model.Student;
import com.TruePrepDemo.TruePrepDemo.Repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;


    @Autowired
    private EmailService emailService; // Inject EmailService

    // Save student and send welcome email
    public Student saveStudent(Student student) {
        // Set the default approval status to false for new students
        student.setApproved(false);
        Student savedStudent = studentRepository.save(student);

        // Send a welcome email after the student is successfully saved
        emailService.sendWelcomeEmail(savedStudent.getEmail()); // Send email using the student's email

        return savedStudent;
    }

    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student updateApprovalStatus(String id, boolean approved) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        student.setApproved(approved);
        return studentRepository.save(student);
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }

    // Login functionality
    public Optional<Student> login(String email, String password) {
        Optional<Student> studentOptional = studentRepository.findByEmail(email);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            // Compare the password (Ensure to use proper password hashing in production)
            if (student.getPassword().equals(password)) {
                return Optional.of(student);
            }
        }
        return Optional.empty(); // Return empty if no match or invalid password
    }
}