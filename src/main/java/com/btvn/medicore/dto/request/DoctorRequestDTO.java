package com.btvn.medicore.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorRequestDTO {

    @NotBlank
    private String fullName;

    @NotBlank
    private String specialization;

    @NotBlank
    private String phone;

    @NotBlank
    private String email;

    private String roomNumber;
}