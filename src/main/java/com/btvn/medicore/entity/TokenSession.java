package com.btvn.medicore.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "token_sessions")
@Getter
@Setter
public class TokenSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String refreshToken;

    private Boolean revoked;

    private Boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}