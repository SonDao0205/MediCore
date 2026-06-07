package com.btvn.medicore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private LocalDate dateOfBirth;

    private String gender;

    private String phone;

    private String address;

    @Column(length = 3000)
    private String medicalHistory;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}