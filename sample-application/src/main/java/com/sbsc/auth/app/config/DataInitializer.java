package com.sbsc.auth.app.config;

import com.sbsc.auth.app.domain.User;
import com.sbsc.auth.app.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            System.out.println("Initializing admin data ...");
            if (repo.findByUsername("admin").isEmpty()) {
                User user = new User();
                user.setUsername("admin");
                user.setPassword(encoder.encode("password"));
                user.setRoles(List.of("ROLE_ADMIN"));

                repo.save(user);
            }

            System.out.println("Initializing user data...");
            if (repo.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(encoder.encode("password"));
                user.setRoles(List.of("ROLE_USER"));

                repo.save(user);
            }
        };
    }
}
