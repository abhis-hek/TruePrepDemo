package com.TruePrepDemo.TruePrepDemo.Repo;

import com.TruePrepDemo.TruePrepDemo.Model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {
    boolean existsByEmail(String email);
}
