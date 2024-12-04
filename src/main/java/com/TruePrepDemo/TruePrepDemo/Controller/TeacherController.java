package com.TruePrepDemo.TruePrepDemo.Controller;

import com.TruePrepDemo.TruePrepDemo.Model.Teacher;
import com.TruePrepDemo.TruePrepDemo.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "http://localhost:3000")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping
    public ResponseEntity<?> addTeacher(@RequestBody Teacher teacher) {
        if (teacherService.existsByEmail(teacher.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }

        Teacher savedTeacher = teacherService.addTeacherWithGeneratedPassword(teacher);
        return ResponseEntity.ok(savedTeacher);
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable String id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Teacher> approveTeacher(@PathVariable String id) {
        Teacher approvedTeacher = teacherService.approveTeacher(id);
        return ResponseEntity.ok(approvedTeacher);
    }
}
