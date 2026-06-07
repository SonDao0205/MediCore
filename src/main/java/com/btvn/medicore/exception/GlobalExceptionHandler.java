package com.btvn.medicore.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        log.error("NOT FOUND : {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildError(404, "NOT_FOUND", ex.getMessage(), request));
    }

    // Bắt lỗi Token không hợp lệ hoặc hết hạn do hệ thống tự định nghĩa
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            UnauthorizedException ex,
            HttpServletRequest request
    ) {
        log.error("UNAUTHORIZED : {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(buildError(401, "UNAUTHORIZED", ex.getMessage(), request));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException ex,
            HttpServletRequest request
    ) {
        log.error("AUTHENTICATION FAILED : {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(buildError(401, "UNAUTHORIZED", "Bad credentials hoặc tài khoản không hợp lệ", request));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(
            ForbiddenException ex,
            HttpServletRequest request
    ) {
        log.error("FORBIDDEN : {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(buildError(403, "FORBIDDEN", ex.getMessage(), request));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDenied(
            AccessDeniedException ex,
            HttpServletRequest request
    ) {
        log.error("ACCESS DENIED : {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(buildError(403, "ACCESS_DENIED", ex.getMessage(), request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String msg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Validation Error");

        log.error("VALIDATION ERROR : {}", msg);
        return ResponseEntity
                .badRequest()
                .body(buildError(400, "BAD_REQUEST", msg, request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> global(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error("SYSTEM ERROR", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(500, "SERVER_ERROR", "Internal Server Error: " + ex.getMessage(), request));
    }

    private ErrorResponse buildError(
            Integer status,
            String error,
            String message,
            HttpServletRequest request
    ) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .error(error)
                .message(message)
                .path(request.getRequestURI())
                .build();
    }
}