package com.TruePrepDemo.TruePrepDemo.Controller;

import com.TruePrepDemo.TruePrepDemo.Model.Student;
import com.TruePrepDemo.TruePrepDemo.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:3000") // Update with your Next.js app URL
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Signup functionality remains the same
    @PostMapping("/signup")
    public ResponseEntity<Student> signup(@RequestBody Student student) {
        Student savedStudent = studentService.saveStudent(student);
        return ResponseEntity.ok(savedStudent);
    }

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
