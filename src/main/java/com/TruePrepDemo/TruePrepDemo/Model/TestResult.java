package com.TruePrepDemo.TruePrepDemo.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "testResults")
public class TestResult {

    @Id
    private String id;
    private String studentId;
    private int correctAnswers;
    private int incorrectAnswers;
    private int attempted;




}
