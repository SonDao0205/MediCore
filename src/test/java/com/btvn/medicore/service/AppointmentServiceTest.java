package com.btvn.medicore.service;

import com.btvn.medicore.dto.request.AppointmentRequestDTO;
import com.btvn.medicore.dto.response.AppointmentResponseDTO;
import com.btvn.medicore.entity.*;
import com.btvn.medicore.entity.enumType.AppointmentStatus;
import com.btvn.medicore.exception.ResourceNotFoundException;
import com.btvn.medicore.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock private AppointmentRepository appointmentRepository;
    @Mock private DoctorRepository doctorRepository;
    @Mock private PatientRepository patientRepository;
    @Mock private UserRepository userRepository;
    @Mock private SecurityContext securityContext;
    @Mock private Authentication authentication;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createAppointment_Success() {
        String username = "patient1";
        when(authentication.getName()).thenReturn(username);

        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Patient patient = new Patient();
        patient.setFullName("Patient Name");
        when(patientRepository.findByUser(user)).thenReturn(Optional.of(patient));

        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFullName("Doctor Name");
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        AppointmentRequestDTO dto = new AppointmentRequestDTO();
        dto.setDoctorId(1L);
        dto.setAppointmentTime(LocalDateTime.now());

        Appointment appointment = new Appointment();
        appointment.setId(10L);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStatus(AppointmentStatus.BOOKED);
        appointment.setAppointmentTime(dto.getAppointmentTime());

        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        AppointmentResponseDTO result = appointmentService.createAppointment(dto);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Doctor Name", result.getDoctorName());
        assertEquals("Patient Name", result.getPatientName());
    }

    @Test
    void createAppointment_DoctorNotFound_ThrowException() {
        when(authentication.getName()).thenReturn("user");
        User user = new User();
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(patientRepository.findByUser(user)).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        AppointmentRequestDTO dto = new AppointmentRequestDTO();
        dto.setDoctorId(1L);

        assertThrows(ResourceNotFoundException.class, () -> appointmentService.createAppointment(dto));
    }
}