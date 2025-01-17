package com.TruePrepDemo.TruePrepDemo.Controller;

import com.TruePrepDemo.TruePrepDemo.Model.TestResult;
import com.TruePrepDemo.TruePrepDemo.Repo.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-results")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class StudentTestController {

    @Autowired
    private TestResultRepository repository;

    // Save a test result
    @PostMapping
    public ResponseEntity<TestResult> saveTestResult(@RequestBody TestResult testResult) {
        try {
            TestResult savedTestResult = repository.save(testResult);
            return new ResponseEntity<>(savedTestResult, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all test results
    @GetMapping
    public ResponseEntity<List<TestResult>> getAllResults() {
        try {
            List<TestResult> results = repository.findAll();
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get test results by student ID
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<TestResult>> getResultsByStudent(@PathVariable String studentId) {
        try {
            // Assuming the repository has a method to find results by student ID.
            List<TestResult> results = repository.findByStudentId(studentId);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
