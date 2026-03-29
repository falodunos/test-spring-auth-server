package com.sbsc.auth.app.controller;

import com.sbsc.auth.security.jwt.JwtService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JwtTestController {

    private final JwtService jwtService;

    public JwtTestController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/api/public/token")
    public String generateToken() {
        return jwtService.generateToken(
                1L,
                "testuser",
                List.of("ROLE_USER")
        );
    }
}
