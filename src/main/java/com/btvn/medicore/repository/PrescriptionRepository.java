package com.btvn.medicore.repository;
import com.btvn.medicore.entity.Patient;
import com.btvn.medicore.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository
        extends JpaRepository<Prescription, Long> {

    List<Prescription>
    findByPatient(Patient patient);
}