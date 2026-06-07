package com.btvn.medicore.service;

import com.btvn.medicore.dto.request.MedicineRequestDTO;
import com.btvn.medicore.dto.response.MedicineResponseDTO;
import com.btvn.medicore.entity.Medicine;
import com.btvn.medicore.exception.ResourceNotFoundException;
import com.btvn.medicore.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;

    // --- HÀM TRỢ GIÚP DÙNG CHUNG (TÁI SỬ DỤNG LẠI) ---
    private Medicine getMedicineOrThrow(Long id) {
        return medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + id));
    }

    public List<MedicineResponseDTO> getAll() {
        return medicineRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public MedicineResponseDTO getById(Long id) {
        return toDTO(getMedicineOrThrow(id));
    }

    public MedicineResponseDTO create(MedicineRequestDTO dto) {
        Medicine medicine = new Medicine();
        mapDtoToEntity(dto, medicine);
        return toDTO(medicineRepository.save(medicine));
    }

    public MedicineResponseDTO update(Long id, MedicineRequestDTO dto) {
        Medicine medicine = getMedicineOrThrow(id);
        mapDtoToEntity(dto, medicine);
        return toDTO(medicineRepository.save(medicine));
    }

    public void delete(Long id) {
        if (!medicineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Medicine not found with id: " + id);
        }
        medicineRepository.deleteById(id);
    }

    private void mapDtoToEntity(MedicineRequestDTO dto, Medicine medicine) {
        medicine.setName(dto.getName());
        medicine.setDescription(dto.getDescription());
        medicine.setPrice(dto.getPrice());
        medicine.setStock(dto.getStock());
    }

    private MedicineResponseDTO toDTO(Medicine medicine) {
        return MedicineResponseDTO.builder()
                .id(medicine.getId())
                .name(medicine.getName())
                .description(medicine.getDescription())
                .price(medicine.getPrice())
                .stock(medicine.getStock())
                .build();
    }
}