package com.btvn.medicore.security;

import com.btvn.medicore.entity.User;
import com.btvn.medicore.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Set;

@Service
public class JwtService {

    @Value("${jwt-secret}")
    private String SECRET;

    @Value("${jwt-expired}")
    private Long EXPIRE_TIME;

    @Value("${jwt-refresh-expired}")
    private Long REFRESH_TIME;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateAccessToken(
            User user
    ) {
        Set<String> roles =
                user.getRoles()
                        .stream()
                        .map(role ->
                                role.getRoleName()
                        )
                        .collect(
                                java.util.stream.Collectors.toSet()
                        );

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRE_TIME
                        )
                )
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(
            User user
    ) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + REFRESH_TIME
                        )
                )
                .signWith(key)
                .compact();
    }

    public String extractUsername(
            String token
    ) {
        return extractClaims(token)
                .getSubject();
    }

    public boolean isValid(
            String token
    ) {
        try {
            extractClaims(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new UnauthorizedException(
                    "Token expired"
            );
        }
    }

    private Claims extractClaims(
            String token
    ) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}