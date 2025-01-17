package com.TruePrepDemo.TruePrepDemo.Controller;

import com.TruePrepDemo.TruePrepDemo.Model.Student;
import com.TruePrepDemo.TruePrepDemo.Model.Question;
import com.TruePrepDemo.TruePrepDemo.Model.Test;
import com.TruePrepDemo.TruePrepDemo.Repo.StudentRepository;
import com.TruePrepDemo.TruePrepDemo.Repo.QuestionRepository;
import com.TruePrepDemo.TruePrepDemo.Repo.TestRepository;
import com.TruePrepDemo.TruePrepDemo.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    public ResponseEntity<?> createTest(
            @RequestParam String section,
            @RequestParam String teacherName,
            @RequestParam int questionLimit,
            @RequestParam int duration,
            @RequestParam String testDate,
            @RequestParam(required = false) List<String> topics) {

        // Default topics to an empty list if null
        if (topics == null) {
            topics = Collections.emptyList();
        }

        // Validate inputs
        if (questionLimit <= 0 || duration <= 0) {
            return ResponseEntity.badRequest()
                    .body("Question limit and duration must be greater than 0.");
        }

        try {
            LocalDateTime.parse(testDate); // Validate date format
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest()
                    .body("Invalid testDate format. Use ISO-8601 format (e.g., 2025-01-14T10:15:30).");
        }

        // Fetch all questions from the specified section
        List<Question> questions = questionRepository.findBySection(section);

        if (questions.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("No questions available for the specified section.");
        }

        if (questions.size() < questionLimit) {
            return ResponseEntity.badRequest()
                    .body("Not enough questions available for the specified question limit.");
        }

        // Shuffle and pick the required number of questions
        Collections.shuffle(questions);
        List<Question> selectedQuestions = questions.stream()
                .limit(questionLimit)
                .collect(Collectors.toList());

        // Create and save the test
        Test test = new Test();
        test.setId(UUID.randomUUID().toString());
        test.setTeacherName(teacherName);
        test.setSection(section);
        test.setCreatedDate(LocalDateTime.now());
        test.setDuration(duration);
        test.setTestDate(LocalDateTime.parse(testDate));
        test.setTopics(topics);
        test.setQuestions(selectedQuestions);

        // Fetch all students and associate them with the test
        List<Student> students = studentRepository.findAll();
        test.setStudentIds(students.stream()
                .map(Student::getId)
                .collect(Collectors.toList()));

        Test savedTest = testRepository.save(test);

        // Prepare test details message
        String testDetails = "A new test has been assigned. \nDetails:\n" +
                "Section: " + section + "\n" +
                "Teacher Name: " + teacherName + "\n" +
                "Number of Questions: " + questionLimit + "\n" +
                "Duration: " + duration + " minutes\n" +
                "Test Date: " + testDate + "\n" +
                "Topics: " + String.join(", ", topics);

        // Send email notifications to all students
        emailService.sendTestNotification(students, testDetails);

        return ResponseEntity.ok(savedTest);
    }
    @GetMapping("/{id}/questions")
    public ResponseEntity<List<Question>> getQuestionsByTestId(@PathVariable String id) {
        return testRepository.findById(id)
                .map(test -> ResponseEntity.ok(test.getQuestions()))
                .orElse(ResponseEntity.status(404).body(Collections.emptyList()));
    }

    @GetMapping("/teacher/{teacherName}")
    public ResponseEntity<List<Test>> getTestsByTeacher(@PathVariable String teacherName) {
        List<Test> tests = testRepository.findByTeacherName(teacherName);
        return ResponseEntity.ok(tests);
    }


    @GetMapping
    public ResponseEntity<List<Test>> getAllTests() {
        List<Test> tests = testRepository.findAll();
        return ResponseEntity.ok(tests);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable String id) {
        return testRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
