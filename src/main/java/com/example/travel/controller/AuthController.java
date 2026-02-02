package com.example.travel.controller;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.travel.model.User;
import com.example.travel.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserRepository repo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository repo) {
        this.repo = repo;
    }

    // ================= REGISTER =================
    @PostMapping("/register")
    public User register(@RequestBody User user) {

        // Check duplicate username
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Check duplicate email
        if (repo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // 🔒 SECURITY FIXES
        user.setId(null); // let Mongo generate ID
        user.setRole("USER"); // force USER role
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return repo.save(user);
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User request) {

        User user = repo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // ✅ Return safe response (no password)
        return Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "fullName", user.getFullName(),
                "role", user.getRole()
        );
    }
}
