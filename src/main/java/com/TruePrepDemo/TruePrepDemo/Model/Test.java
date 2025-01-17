package com.TruePrepDemo.TruePrepDemo.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Document(collection = "tests")
public class Test {

    @Id
    private String id;

    private String teacherName;

    private String section;

    private LocalDateTime createdDate;

    @Field(targetType = FieldType.ARRAY)
    private List<Question> questions; // Embedded list of questions

    private int duration; // Duration in minutes
    private LocalDateTime testDate; // Date and time of the test
    private List<String> topics; // List of topic names
    private List<String> studentIds; // List of student IDs assigned to this test
}
