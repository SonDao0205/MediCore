package com.btvn.medicore.repository;
import com.btvn.medicore.entity.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokedTokenRepository
        extends JpaRepository<RevokedToken, Long> {

    boolean existsByTokenValue(
            String tokenValue
    );
}