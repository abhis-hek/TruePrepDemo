package com.TruePrepDemo.TruePrepDemo.Service;

import com.TruePrepDemo.TruePrepDemo.Model.Admin;
import com.TruePrepDemo.TruePrepDemo.Repo.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Login functionality
    public Optional<Admin> login(String email, String password) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            // Compare the password (Ensure to use proper password hashing in production)
            if (admin.getPassword().equals(password)) {
                return Optional.of(admin);
            }
        }
        return Optional.empty(); // Return empty if no match or invalid password
    }
}
