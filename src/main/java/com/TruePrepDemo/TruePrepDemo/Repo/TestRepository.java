package com.TruePrepDemo.TruePrepDemo.Repo;

import com.TruePrepDemo.TruePrepDemo.Model.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<Test, String> {
}
