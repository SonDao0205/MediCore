package com.btvn.medicore.repository;
import com.btvn.medicore.entity.Patient;
import com.btvn.medicore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository
        extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUser(User user);
}