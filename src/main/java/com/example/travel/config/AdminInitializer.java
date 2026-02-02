package com.example.travel.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.travel.model.User;
import com.example.travel.repository.UserRepository;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner initAdmin(UserRepository repo) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {

                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole("ADMIN");
                admin.setEmail("admin@travel.com");
                admin.setFullName("System Admin");

                repo.save(admin);

                System.out.println("✅ ADMIN user created");
            } else {
                System.out.println("ℹ️ ADMIN already exists");
            }
        };
    }
}
