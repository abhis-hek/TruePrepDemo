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

    // Getters and Setters
    @Id
    private String id;

    private String teacherName;

    private String section;

    private LocalDateTime createdDate;

    @Field(targetType = FieldType.ARRAY)
    private List<Question> questions; // Embedded list of questions

}
