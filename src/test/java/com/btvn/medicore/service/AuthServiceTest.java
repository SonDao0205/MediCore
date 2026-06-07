package com.btvn.medicore.service;

import com.btvn.medicore.dto.request.LoginRequest;
import com.btvn.medicore.dto.request.RegisterRequest;
import com.btvn.medicore.dto.response.AuthResponse;
import com.btvn.medicore.entity.*;
import com.btvn.medicore.exception.ResourceNotFoundException;
import com.btvn.medicore.repository.*;
import com.btvn.medicore.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private AuthenticationManager authenticationManager;
    @Mock private UserRepository userRepository;
    @Mock private TokenSessionRepository tokenRepository;
    @Mock private RevokedTokenRepository revokedTokenRepository;
    @Mock private JwtService jwtService;
    @Mock private RoleRepository roleRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private PatientRepository patientRepository;
    @Mock private SecurityContext securityContext;
    @Mock private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("newpatient");
        request.setPassword("password123");
        request.setFullName("Nguyen Van Patient");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        when(roleRepository.findByRoleName("ROLE_PATIENT")).thenReturn(Optional.of(new Role()));
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        assertDoesNotThrow(() -> authService.register(request));

        verify(userRepository, times(1)).save(any(User.class));
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void register_UsernameExists_ThrowException() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existinguser");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> authService.register(request));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_Success() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        User user = new User();
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(jwtService.generateAccessToken(user)).thenReturn("access_token");
        when(jwtService.generateRefreshToken(user)).thenReturn("refresh_token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("access_token", response.getAccessToken());
        assertEquals("refresh_token", response.getRefreshToken());
        verify(tokenRepository, times(1)).save(any(TokenSession.class));
    }
}