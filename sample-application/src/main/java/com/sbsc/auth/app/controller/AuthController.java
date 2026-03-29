package com.sbsc.auth.app.controller;

import com.sbsc.auth.app.controller.dto.LoginRequest;
import com.sbsc.auth.app.service.AuthService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request.getUsername(), request.getPassword());
    }
}
