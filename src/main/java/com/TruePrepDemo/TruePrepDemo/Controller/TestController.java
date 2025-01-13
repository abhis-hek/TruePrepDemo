package com.TruePrepDemo.TruePrepDemo.Controller;

import com.TruePrepDemo.TruePrepDemo.Model.Question;
import com.TruePrepDemo.TruePrepDemo.Model.Test;
import com.TruePrepDemo.TruePrepDemo.Repo.QuestionRepository;
import com.TruePrepDemo.TruePrepDemo.Repo.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestRepository testRepository;

    // Create a test with random 10 questions from a given section
    @PostMapping("/create")
    public ResponseEntity<Test> createTest(@RequestParam String section, @RequestParam String teacherName) {
        // Fetch all questions from the specified section
        List<Question> questions = questionRepository.findBySection(section);

        if (questions.size() < 10) {
            return ResponseEntity.badRequest().body(null); // Ensure at least 10 questions are available
        }

        // Shuffle and pick the first 10 questions
        Collections.shuffle(questions);
        List<Question> selectedQuestions = questions.stream().limit(10).collect(Collectors.toList());

        // Create and save the test
        Test test = new Test();
        test.setId(UUID.randomUUID().toString());
        test.setTeacherName(teacherName);
        test.setSection(section);
        test.setCreatedDate(LocalDateTime.now());
        test.setQuestions(selectedQuestions);

        Test savedTest = testRepository.save(test);

        return ResponseEntity.ok(savedTest);
    }

    // Get all tests
    @GetMapping
    public ResponseEntity<List<Test>> getAllTests() {
        List<Test> tests = testRepository.findAll();
        return ResponseEntity.ok(tests);
    }

    // Get a specific test by ID
    @GetMapping("/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable String id) {
        return testRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
