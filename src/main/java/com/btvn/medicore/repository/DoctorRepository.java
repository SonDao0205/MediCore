package com.btvn.medicore.repository;
import com.btvn.medicore.entity.Doctor;
import com.btvn.medicore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository
        extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByUser(User user);
}