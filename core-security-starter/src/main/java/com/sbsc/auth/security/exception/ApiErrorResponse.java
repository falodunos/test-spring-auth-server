package com.sbsc.auth.security.exception;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ApiErrorResponse {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    @Getter
    private final String traceId;

    public ApiErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.traceId = UUID.randomUUID().toString();
    }

}