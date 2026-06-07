package com.btvn.medicore.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "revoked_tokens")
@Getter
@Setter
public class RevokedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1200)
    private String tokenValue;

    private LocalDateTime revokedAt;
}