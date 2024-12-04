package com.TruePrepDemo.TruePrepDemo.Repo;

import com.TruePrepDemo.TruePrepDemo.Model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findBySection(String section);
}
