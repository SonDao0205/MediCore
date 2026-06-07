package com.btvn.medicore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doctors")
@Getter
@Setter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String specialization;

    private String phone;

    private String email;

    private String roomNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}