package com.btvn.medicore.service;

import com.btvn.medicore.dto.request.DoctorRequestDTO;
import com.btvn.medicore.dto.response.DoctorResponseDTO;
import com.btvn.medicore.entity.Doctor;
import com.btvn.medicore.exception.ResourceNotFoundException;
import com.btvn.medicore.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    // --- HÀM TRỢ GIÚP DÙNG CHUNG (TÁI SỬ DỤNG LẠI) ---
    private Doctor getDoctorOrThrow(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
    }

    public List<DoctorResponseDTO> getAll() {
        return doctorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public DoctorResponseDTO getById(Long id) {
        return toDTO(getDoctorOrThrow(id));
    }

    public DoctorResponseDTO create(DoctorRequestDTO dto) {
        Doctor doctor = new Doctor();
        mapDtoToEntity(dto, doctor);
        return toDTO(doctorRepository.save(doctor));
    }

    public DoctorResponseDTO update(Long id, DoctorRequestDTO dto) {
        Doctor doctor = getDoctorOrThrow(id);
        mapDtoToEntity(dto, doctor);
        return toDTO(doctorRepository.save(doctor));
    }

    public void delete(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    private void mapDtoToEntity(DoctorRequestDTO dto, Doctor doctor) {
        doctor.setFullName(dto.getFullName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setPhone(dto.getPhone());
        doctor.setEmail(dto.getEmail());
        doctor.setRoomNumber(dto.getRoomNumber());
    }

    private DoctorResponseDTO toDTO(Doctor doctor) {
        return DoctorResponseDTO.builder()
                .id(doctor.getId())
                .fullName(doctor.getFullName())
                .specialization(doctor.getSpecialization())
                .phone(doctor.getPhone())
                .email(doctor.getEmail())
                .roomNumber(doctor.getRoomNumber())
                .build();
    }
}