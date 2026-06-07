package com.btvn.medicore.service;

import com.btvn.medicore.dto.request.AppointmentRequestDTO;
import com.btvn.medicore.dto.response.AppointmentResponseDTO;
import com.btvn.medicore.entity.Appointment;
import com.btvn.medicore.entity.Doctor;
import com.btvn.medicore.entity.Patient;
import com.btvn.medicore.entity.User;
import com.btvn.medicore.entity.enumType.AppointmentStatus;
import com.btvn.medicore.exception.ResourceNotFoundException;
import com.btvn.medicore.repository.AppointmentRepository;
import com.btvn.medicore.repository.DoctorRepository;
import com.btvn.medicore.repository.PatientRepository;
import com.btvn.medicore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    private User getCurrentUserOrThrow() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }

    private Patient getCurrentPatientOrThrow(User user) {
        return patientRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Patient profile not found for user: " + user.getUsername()));
    }

    private Doctor getCurrentDoctorOrThrow(User user) {
        return doctorRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor profile not found for user: " + user.getUsername()));
    }

    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
        User user = getCurrentUserOrThrow();
        Patient patient = getCurrentPatientOrThrow(user);

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + dto.getDoctorId()));

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setStatus(AppointmentStatus.BOOKED);

        return toDTO(appointmentRepository.save(appointment));
    }

    public List<AppointmentResponseDTO> myAppointments() {
        User user = getCurrentUserOrThrow();
        Patient patient = getCurrentPatientOrThrow(user);

        return appointmentRepository.findByPatient(patient)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<AppointmentResponseDTO> doctorTodayAppointments() {
        User user = getCurrentUserOrThrow();
        Doctor doctor = getCurrentDoctorOrThrow(user);

        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        return appointmentRepository.findByDoctorAndAppointmentTimeBetween(doctor, start, end)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private AppointmentResponseDTO toDTO(Appointment appointment) {
        return AppointmentResponseDTO.builder()
                .id(appointment.getId())
                .doctorName(appointment.getDoctor().getFullName())
                .patientName(appointment.getPatient().getFullName())
                .appointmentTime(appointment.getAppointmentTime())
                .status(appointment.getStatus().name())
                .build();
    }
}