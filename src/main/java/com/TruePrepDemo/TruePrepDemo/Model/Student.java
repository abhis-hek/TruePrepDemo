package com.TruePrepDemo.TruePrepDemo.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document("student")
public class Student {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean approved; // New field for approval status
}
