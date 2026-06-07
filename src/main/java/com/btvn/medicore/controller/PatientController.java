package com.btvn.medicore.controller;

import com.btvn.medicore.dto.request.PatientRequestDTO;
import com.btvn.medicore.dto.response.ApiDataResponse;
import com.btvn.medicore.dto.response.PatientResponseDTO;
import com.btvn.medicore.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<List<PatientResponseDTO>>> getAll() {

        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách Patient thành công!",
                patientService.getAll(),
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<PatientResponseDTO>> getById(@PathVariable Long id) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy Patient bằng ID thành công!",
                patientService.getById(id),
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<PatientResponseDTO>> create(@Valid @RequestBody PatientRequestDTO dto) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm Patient thành công!",
                patientService.create(dto),
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<PatientResponseDTO>> update( @PathVariable Long id, @Valid @RequestBody PatientRequestDTO dto) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật Patient thành công!",
                patientService.update(id,dto),
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiDataResponse<?>> delete(
            @PathVariable Long id
    ) {

        patientService.delete(id);

        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Xoá Patient thành công!",
                null,
                HttpStatus.NO_CONTENT
        ), HttpStatus.NO_CONTENT);
    }
}