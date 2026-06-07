package com.btvn.medicore.security;

import com.btvn.medicore.exception.ErrorResponse;
import jakarta.servlet.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

public class JwtAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException ex
    ) throws IOException {
        response.setStatus(401);
        response.setContentType(
                "application/json"
        );

        ErrorResponse error =
                ErrorResponse.builder()
                        .timestamp(
                                LocalDateTime.now()
                        )
                        .status(401)
                        .error("UNAUTHORIZED")
                        .message(
                                "Authentication required"
                        )
                        .path(
                                request.getRequestURI()
                        )
                        .build();

        new ObjectMapper()
                .writeValue(
                        response.getOutputStream(),
                        error
                );
    }
}