package com.btvn.medicore.repository;

import com.btvn.medicore.entity.TokenSession;
import com.btvn.medicore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenSessionRepository
        extends JpaRepository<TokenSession, Long> {

    Optional<TokenSession>
    findByRefreshToken(String refreshToken);

    List<TokenSession>
    findByUser(User user);
}