package com.btvn.medicore.service;

import com.btvn.medicore.dto.request.PatientRequestDTO;
import com.btvn.medicore.dto.response.PatientResponseDTO;
import com.btvn.medicore.entity.Patient;
import com.btvn.medicore.exception.ResourceNotFoundException;
import com.btvn.medicore.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    // --- HÀM TRỢ GIÚP DÙNG CHUNG (TÁI SỬ DỤNG LẠI) ---
    private Patient getPatientOrThrow(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    public List<PatientResponseDTO> getAll() {
        return patientRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public PatientResponseDTO getById(Long id) {
        return toDTO(getPatientOrThrow(id));
    }

    public PatientResponseDTO create(PatientRequestDTO dto) {
        Patient patient = new Patient();
        mapDtoToEntity(dto, patient);
        return toDTO(patientRepository.save(patient));
    }

    public PatientResponseDTO update(Long id, PatientRequestDTO dto) {
        Patient patient = getPatientOrThrow(id);
        mapDtoToEntity(dto, patient);
        return toDTO(patientRepository.save(patient));
    }

    public void delete(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    private void mapDtoToEntity(PatientRequestDTO dto, Patient patient) {
        patient.setFullName(dto.getFullName());
        patient.setGender(dto.getGender());
        patient.setPhone(dto.getPhone());
        patient.setAddress(dto.getAddress());
        patient.setMedicalHistory(dto.getMedicalHistory());
    }

    private PatientResponseDTO toDTO(Patient patient) {
        return PatientResponseDTO.builder()
                .id(patient.getId())
                .fullName(patient.getFullName())
                .gender(patient.getGender())
                .phone(patient.getPhone())
                .address(patient.getAddress())
                .medicalHistory(patient.getMedicalHistory())
                .build();
    }
}