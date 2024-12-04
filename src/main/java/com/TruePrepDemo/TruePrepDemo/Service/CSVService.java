package com.TruePrepDemo.TruePrepDemo.Service;

import com.TruePrepDemo.TruePrepDemo.Model.Question;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource; // Import Resource
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVService {

    @Value("classpath:technical_questions.csv") // Load file from resources folder
    private Resource resource;

    public List<Question> readQuestionsFromCSV() throws IOException {
        List<Question> questions = new ArrayList<>();
        try (Reader reader = new FileReader(resource.getFile())) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(reader);
            for (CSVRecord record : records) {
                Question question = new Question();
                question.setSection(record.get("Section")); // Add this line
                question.setQuestionText(record.get("Question"));
                question.setOptionA(record.get("Option A"));
                question.setOptionB(record.get("Option B"));
                question.setOptionC(record.get("Option C"));
                question.setOptionD(record.get("Option D"));
                question.setCorrectAnswer(record.get("Correct Answer"));
                questions.add(question);
            }

        }
        return questions;
    }
}
