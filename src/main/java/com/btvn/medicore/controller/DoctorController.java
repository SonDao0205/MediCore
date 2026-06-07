package com.btvn.medicore.controller;
import com.btvn.medicore.dto.request.DoctorRequestDTO;
import com.btvn.medicore.dto.response.ApiDataResponse;
import com.btvn.medicore.dto.response.DoctorResponseDTO;
import com.btvn.medicore.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<List<DoctorResponseDTO>>> getAll() {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách Doctor thành công!",
                doctorService.getAll(),
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<DoctorResponseDTO>> getById(@PathVariable Long id) {

        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy Doctor theo Id thành công!",
                doctorService.getById(id),
                HttpStatus.OK
        ),HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<DoctorResponseDTO>> create(@Valid @RequestBody DoctorRequestDTO dto) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm Doctor thành công!",
                doctorService.create(dto),
                HttpStatus.CREATED
        ), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<DoctorResponseDTO>> update( @PathVariable Long id, @Valid @RequestBody DoctorRequestDTO dto) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật Doctor thành công!",
                doctorService.update(id,dto),
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<?>> delete(
            @PathVariable Long id
    ) {
        doctorService.delete(id);
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Xoá Doctor thành công!",
                null,
                HttpStatus.NO_CONTENT
        ), HttpStatus.NO_CONTENT);
    }
}