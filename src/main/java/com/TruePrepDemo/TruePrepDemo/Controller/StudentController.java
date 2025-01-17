package com.TruePrepDemo.TruePrepDemo.Controller;

import com.TruePrepDemo.TruePrepDemo.Model.Student;
import com.TruePrepDemo.TruePrepDemo.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:3000") // Update with your Next.js app URL
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Signup functionality remains the same
    // Signup functionality with email existence check
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Student student) {
        // Check if the email already exists
        if (studentService.findByEmail(student.getEmail()).isPresent()) {
            return ResponseEntity.status(409) // Conflict status code
                    .body("Email already exists. Please use a different email.");
        }

        // Proceed with saving the student
        Student savedStudent = studentService.saveStudent(student);
        return ResponseEntity.ok(savedStudent);
    }


    // Login functionality for students
    @PostMapping("/login")
    public ResponseEntity<Student> login(@RequestBody Student loginRequest) {
        Optional<Student> studentOptional = studentService.login(loginRequest.getEmail(), loginRequest.getPassword());

        if (studentOptional.isPresent()) {
            return ResponseEntity.ok(studentOptional.get()); // Successful login
        }
        return ResponseEntity.status(401).body(null); // Unauthorized if credentials are invalid
    }

    // Fetch student by email
    @GetMapping("/email/{email}")
    public ResponseEntity<Student> findByEmail(@PathVariable String email) {
        return studentService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Fetch all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // Approve a student
    @PutMapping("/{id}/approve")
    public ResponseEntity<Student> approveStudent(@PathVariable String id) {
        Student updatedStudent = studentService.updateApprovalStatus(id, true);
        return ResponseEntity.ok(updatedStudent);
    }

    // Reject a student
    @PutMapping("/{id}/reject")
    public ResponseEntity<Student> rejectStudent(@PathVariable String id) {
        Student updatedStudent = studentService.updateApprovalStatus(id, false);
        return ResponseEntity.ok(updatedStudent);
    }

    // Delete a student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
