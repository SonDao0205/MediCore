package com.btvn.medicore.service;

import com.btvn.medicore.dto.request.DoctorRequestDTO;
import com.btvn.medicore.dto.response.DoctorResponseDTO;
import com.btvn.medicore.entity.Doctor;
import com.btvn.medicore.exception.ResourceNotFoundException;
import com.btvn.medicore.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock private DoctorRepository doctorRepository;
    @InjectMocks private DoctorService doctorService;

    @Test
    void getById_Success() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFullName("Dr. Robert");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        DoctorResponseDTO result = doctorService.getById(1L);

        assertNotNull(result);
        assertEquals("Dr. Robert", result.getFullName());
    }

    @Test
    void getById_NotFound_ThrowException() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> doctorService.getById(1L));
    }
}