package com.sbsc.auth.app.controller.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String username;
    private String password;
}