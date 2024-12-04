package com.TruePrepDemo.TruePrepDemo.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("teacher")
public class Teacher {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String subject;
    private boolean approved;
}
