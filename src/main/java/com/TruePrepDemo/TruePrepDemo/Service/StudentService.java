package com.TruePrepDemo.TruePrepDemo.Service;

import com.TruePrepDemo.TruePrepDemo.Model.Student;
import com.TruePrepDemo.TruePrepDemo.Repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student saveStudent(Student student) {
        // Set the default approval status to false for new students
        student.setApproved(false);
        return studentRepository.save(student);
    }

    public Optional<Student> findByEmail(String email) {
        return Optional.ofNullable(studentRepository.findByEmail(email));
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
}
