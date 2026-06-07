package com.btvn.medicore.controller;

import com.btvn.medicore.dto.request.LoginRequest;
import com.btvn.medicore.dto.request.RefreshRequest;
import com.btvn.medicore.dto.request.RegisterRequest;
import com.btvn.medicore.dto.response.ApiDataResponse;
import com.btvn.medicore.dto.response.AuthResponse;
import com.btvn.medicore.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiDataResponse<AuthResponse>>  login(@RequestBody LoginRequest request) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Đăng nhập thành công!",
                authService.login(request),
                HttpStatus.OK
        ),HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiDataResponse<?>> register(
            @RequestBody RegisterRequest request
    ) {

        authService.register(request);

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        true,
                        "Đăng ký thành công",
                        null,
                        HttpStatus.CREATED
                ),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiDataResponse<AuthResponse>> refresh(@RequestBody RefreshRequest request) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Refresh token thành công!",
                authService.refresh(request),
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiDataResponse<?>> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        authService.logout(token);
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Đăng xuất thành công!",
                null,
                HttpStatus.OK
        ),HttpStatus.OK);
    }
}