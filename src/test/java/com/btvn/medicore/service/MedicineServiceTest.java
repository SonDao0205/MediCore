package com.btvn.medicore.service;

import com.btvn.medicore.dto.response.MedicineResponseDTO;
import com.btvn.medicore.entity.Medicine;
import com.btvn.medicore.exception.ResourceNotFoundException;
import com.btvn.medicore.repository.MedicineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicineServiceTest {

    @Mock private MedicineRepository medicineRepository;
    @InjectMocks private MedicineService medicineService;

    @Test
    void getById_Success() {
        Medicine medicine = new Medicine();
        medicine.setId(1L);
        medicine.setName("Paracetamol");

        when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));

        MedicineResponseDTO result = medicineService.getById(1L);

        assertNotNull(result);
        assertEquals("Paracetamol", result.getName());
    }
}