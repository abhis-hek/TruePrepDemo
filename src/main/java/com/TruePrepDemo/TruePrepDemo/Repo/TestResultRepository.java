package com.TruePrepDemo.TruePrepDemo.Repo;

import com.TruePrepDemo.TruePrepDemo.Model.TestResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TestResultRepository extends MongoRepository<TestResult, String> {
    List<TestResult> findByStudentId(String studentId);
}
