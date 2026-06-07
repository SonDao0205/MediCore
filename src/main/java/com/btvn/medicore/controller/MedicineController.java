package com.btvn.medicore.controller;

import com.btvn.medicore.dto.request.MedicineRequestDTO;
import com.btvn.medicore.dto.response.ApiDataResponse;
import com.btvn.medicore.dto.response.MedicineResponseDTO;
import com.btvn.medicore.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<List<MedicineResponseDTO>>> getAll() {

        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách Medicine thành công!",
                medicineService.getAll(),
                HttpStatus.OK
        ),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<MedicineResponseDTO>> getById(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy Medicine bằng ID thành công!",
                medicineService.getById(id),
                HttpStatus.OK
        ),HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<MedicineResponseDTO>> create(
            @Valid @RequestBody
            MedicineRequestDTO dto
    ) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm Medicine thành công!",
                medicineService.create(dto),
                HttpStatus.CREATED
        ),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<MedicineResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody MedicineRequestDTO dto
    ) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật Medicine thành công!",
                medicineService.update(id,dto),
                HttpStatus.OK
        ),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<?>> delete(@PathVariable Long id) {

        medicineService.delete(id);
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Xoá Medicine thành công!",
                null,
                HttpStatus.NO_CONTENT
        ),HttpStatus.NO_CONTENT);
    }
}