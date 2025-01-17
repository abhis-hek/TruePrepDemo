package com.TruePrepDemo.TruePrepDemo.Repo;

import com.TruePrepDemo.TruePrepDemo.Model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByEmail(String email);
}
