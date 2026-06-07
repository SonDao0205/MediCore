package com.btvn.medicore.repository;

import com.btvn.medicore.entity.Appointment;
import com.btvn.medicore.entity.Doctor;
import com.btvn.medicore.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    List<Appointment>
    findByDoctor(Doctor doctor);

    List<Appointment>
    findByPatient(Patient patient);

    List<Appointment>
    findByDoctorAndAppointmentTimeBetween(
            Doctor doctor,
            LocalDateTime start,
            LocalDateTime end
    );
}