package com.btvn.medicore.service;

import com.btvn.medicore.dto.response.PatientResponseDTO;
import com.btvn.medicore.entity.Patient;
import com.btvn.medicore.exception.ResourceNotFoundException;
import com.btvn.medicore.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock private PatientRepository patientRepository;
    @InjectMocks private PatientService patientService;

    @Test
    void getById_Success() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFullName("Nguyen Van A");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        PatientResponseDTO result = patientService.getById(1L);

        assertNotNull(result);
        assertEquals("Nguyen Van A", result.getFullName());
    }
}