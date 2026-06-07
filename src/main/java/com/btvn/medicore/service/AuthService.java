package com.btvn.medicore.service;

import com.btvn.medicore.dto.request.LoginRequest;
import com.btvn.medicore.dto.request.RefreshRequest;
import com.btvn.medicore.dto.request.RegisterRequest;
import com.btvn.medicore.dto.response.AuthResponse;
import com.btvn.medicore.entity.*;
import com.btvn.medicore.exception.ResourceNotFoundException;
import com.btvn.medicore.exception.UnauthorizedException;
import com.btvn.medicore.repository.*;
import com.btvn.medicore.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenSessionRepository tokenRepository;
    private final RevokedTokenRepository revokedTokenRepository;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;

    private User getUserByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Role patientRole = roleRepository
                .findByRoleName("ROLE_PATIENT")
                .orElseThrow(() -> new ResourceNotFoundException("ROLE_PATIENT not found"));

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setEmail(request.getEmail());
        user.setEnabled(true);
        user.setRoles(Set.of(patientRole));

        userRepository.save(user);

        Patient patient = new Patient();

        patient.setFullName(request.getFullName());
        patient.setUser(user);

        patientRepository.save(patient);

        log.info("New patient account registered: {}", user.getUsername());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = getUserByUsernameOrThrow(request.getUsername());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        TokenSession session = new TokenSession();
        session.setRefreshToken(refreshToken);
        session.setExpired(false);
        session.setRevoked(false);
        session.setUser(user);

        tokenRepository.save(session);
        log.info("User {} login success", user.getUsername());

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refresh(RefreshRequest request) {
        TokenSession session = tokenRepository.findByRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        if (session.getRevoked() || session.getExpired()) {
            throw new UnauthorizedException("Refresh token has been expired or revoked");
        }

        String accessToken = jwtService.generateAccessToken(session.getUser());
        return new AuthResponse(accessToken, request.getRefreshToken());
    }

    @Transactional
    public void logout(String accessToken) {
        String username = getCurrentUsername();
        User user = getUserByUsernameOrThrow(username);

        tokenRepository.findByUser(user)
                .forEach(token -> {
                    token.setRevoked(true);
                    token.setExpired(true);
                });

        RevokedToken revoked = new RevokedToken();
        revoked.setTokenValue(accessToken);
        revoked.setRevokedAt(LocalDateTime.now());
        revokedTokenRepository.save(revoked);

        log.info("User {} logout successfully", username);
    }
}